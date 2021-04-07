package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.ElementToken;
import hr.fer.oprpp1.custom.scripting.lexer.ElementType;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Razred <code>SmartScriptParser</code> predstavlja parser za dokument
 * 
 * @author Dorian Kablar
 *
 */
public class SmartScriptParser {

	private String documentBody;
	private DocumentNode documentNode;
	private ObjectStack stack = new ObjectStack();
	private SmartScriptLexer lexer;
	
	/**
	 * Stvara novi <code>SmartScriptParser</code> s parametrom document body
	 * 
	 * @param documentBody tekst koji treba isparsirati
	 */
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
		this.lexer = new SmartScriptLexer(this.documentBody);
		// ako se uhvati iznimka iz Lexera, pakiramo ju u SmartScriptParserException
		try {
			this.documentNode = parse();
		} catch(LexerException ex) {
			throw new SmartScriptParserException(ex);
		}
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li <code>ElementType</code> trenutnog tokena jednak zadanom 
	 * 
	 * @param type zadani <code>ElementType</code>
	 * @return true, ako su jednaki, false inače
	 */
	private boolean isElementType(ElementType type) {
		return (this.lexer.getElementToken().getType() == type);
	}
	
	/**
	 * Glavna metoda razreda. Ona parsira zadani text, i vraća izgenerirano stablo
	 * 
	 * @return generirano stablo zadano texta
	 */
	public DocumentNode parse() {
		DocumentNode result = new DocumentNode();
		this.stack.push(result);
		
		while(true) {
			this.lexer.nextElement();
			// došli smo do kraja dokumenta
			if(isElementType(ElementType.EOF)) {
				this.lexer.setState(SmartScriptLexerState.TEXT);
				break;
			}
			
			if(this.lexer.getState() == SmartScriptLexerState.TEXT) { // TEXT način rada
				if(this.lexer.getElementToken().getType() == ElementType.STRING) {
					ElementString string = new ElementString(this.lexer.getElementToken().getValue().toString());
					TextNode textNode = new TextNode(string.asText());
					Node node = (Node) this.stack.pop();
					node.addChildNode(textNode);
					this.stack.push(node);
					continue;
				}
				// promjena načina rada Parsera
				if(this.lexer.getElementToken().getType() == ElementType.START_TAG) {
					this.lexer.setState(SmartScriptLexerState.TAG);
					continue;
				}
				throw new SmartScriptParserException("Neočekivani ElementType. U TEXT načinu rada, može biti samo ElementString");
			} else { // TAG način rada
				// promjena načina rada
				if(lexer.getElementToken().getType() == ElementType.END_TAG) {
					this.lexer.setState(SmartScriptLexerState.TEXT);
					continue;
				}
				if(this.lexer.getElementToken().getType() != ElementType.VARIABLE)
					throw new SmartScriptParserException("TAG mora početi s VARIABLE");
				// ulazak u obradu FOR petlje
				if(this.lexer.getElementToken().getValue().toString().toUpperCase().equals("FOR")) {
					this.lexer.nextElement();
					ForLoopNode forLoopNode = parseForLoop();
					Node node = (Node) this.stack.pop();
					node.addChildNode(forLoopNode);
					this.stack.push(node);
					this.stack.push(forLoopNode);
					continue;
				// ulazak u obradu ECHO naredbe
				} else if(lexer.getElementToken().getValue().toString().toUpperCase().equals("=")) {
					this.lexer.nextElement();
					EchoNode echoNode = parseEcho();
					Node node = (Node) this.stack.pop();
					node.addChildNode(echoNode);
					this.stack.push(node);
					this.lexer.setState(SmartScriptLexerState.TEXT);
					continue;
				// kraj FOR petlje
				} else if(lexer.getElementToken().getValue().toString().toUpperCase().equals("END")) {
					this.stack.pop();
					if(this.stack.isEmpty())
						throw new SmartScriptParserException("U dokumentu se nalazi previše END tagova");
					continue;
				}
				throw new SmartScriptParserException("Nedozvoljeno ime TAG-a");
			}
		}
		try {
			result = (DocumentNode) this.stack.pop();
		} catch(ClassCastException ex) {
			throw new SmartScriptParserException("Jedan od TAGova nije zatvoren.");
		}
		return result;
	}
	
	/**
	 * Pomoćna metoda. Služi za parsiranje FOR taga
	 * 
	 * @return Obrađeni FOR tag
	 */
	private ForLoopNode parseForLoop() {
		if(this.lexer.getElementToken().getType() != ElementType.VARIABLE)
			throw new SmartScriptParserException("Prvi argument u FOR petlji mora biti VARIABLE");
		ElementVariable variable = new ElementVariable(this.lexer.getElementToken().getValue().toString());
		Element startExpression = getElementInForLoop(this.lexer.nextElement());
		Element endExpression = getElementInForLoop(this.lexer.nextElement());
		this.lexer.nextElement();
		if(this.lexer.getState() != SmartScriptLexerState.TAG) {
			return new ForLoopNode(variable, startExpression, endExpression, null);
		}
		Element stepExpression = getElementInForLoop(this.lexer.getElementToken());
		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
	}
	
	/**
	 * Pomoćna metoda. Služi za parsiranje ECHO taga
	 * 
	 * @return Obrađeni ECHO tag
	 */
	private EchoNode parseEcho() {
		ArrayIndexedCollection tmp = new ArrayIndexedCollection();
		while(true) {
			if(this.lexer.getElementToken().getType() == ElementType.END_TAG)
				break;
			tmp.add(getElementInEcho(this.lexer.getElementToken()));
			this.lexer.nextElement();
		}
		Element[] res = new Element[tmp.size()];
		for(int i = 0; i < tmp.size(); i++) {
			if(tmp.get(i) instanceof Element)
				res[i] = (Element) tmp.get(i);
		}
		return new EchoNode(res);
	}
	
	/**
	 * Pomoćna metoda. Na temelju zadanog tokena određuje tip Elementa u FOR tagu i vraća ga
	 * 
	 * @param token zadani token
	 * @return element u FOR tagu
	 */
	private Element getElementInForLoop(ElementToken token) {
		switch(token.getType()) {
		case VARIABLE: {
			ElementVariable startExpression = new ElementVariable(token.getValue().toString());
			return startExpression;
		}
		case DOUBLE_CONSTANT: {
			ElementConstantDouble startExpression = new ElementConstantDouble((double) token.getValue());
			return startExpression;
		}
		case INTEGER_CONSTANT: {
			ElementConstantInteger startExpression = new ElementConstantInteger((int) token.getValue());
			return startExpression;
		}
		case STRING: {
			ElementString startExpression = new ElementString(token.getValue().toString());
			return startExpression;
		}
		default:
			throw new SmartScriptParserException("startExpression u FOR petlji može biti NUMBER, STRING ili VARIABLE");
		}
	}
	
	/**
	 * Pomoćna metoda. Na temelju zadanog tokena određuje tip elementa u ECHO tagu i vraća ga
	 * 
	 * @param token zadani token
	 * @return element u ECHO tagu
	 */
	private Element getElementInEcho(ElementToken token) {
		switch(token.getType()) {
		case DOUBLE_CONSTANT: {
			ElementConstantDouble element = new ElementConstantDouble((double) token.getValue());
			return element;
		}
		case FUNCTION: {
			ElementFunction element = new ElementFunction(token.getValue().toString());
			return element;
		}
		case INTEGER_CONSTANT:{
			ElementConstantInteger element = new ElementConstantInteger((int) token.getValue());
			return element;
		}
		case OPERATOR: {
			ElementOperator element = new ElementOperator(token.getValue().toString());
			return element;
		}
		case STRING: {
			ElementString element = new ElementString(token.getValue().toString());
			return element;
		}
		case VARIABLE: {
			ElementVariable element = new ElementVariable(token.getValue().toString());
			return element;
		}
		default:
			throw new SmartScriptParserException("U Echo naredbi ne može biti EOF");
		}
	}
	
	/**
	 * Metoda vraća generativno stablo stablo programa
	 * 
	 * @return stablo programa
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
}

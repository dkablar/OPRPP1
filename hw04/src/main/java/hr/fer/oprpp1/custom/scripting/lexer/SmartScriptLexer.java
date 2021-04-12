package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Razred <code>SmartScriptLexer</code> predstavlja leksički analizator
 * 
 * @author Dorian Kablar
 *
 */
public class SmartScriptLexer {

	private char[] data; // ulazni tekst
	private ElementToken token; // trenutni token
	private int currentIndex; // index prvog neodređenog znaka
	private SmartScriptLexerState lexerState = SmartScriptLexerState.TEXT; // način rada lexera
	
	/**
	 * Stvara novi <code>SmartScriptLexer</code> koji prima novi tekst koji se treba tokenizirati, tj. analizirati
	 * 
	 * @param text tekst koji treba tokenizirati
	 */
	public SmartScriptLexer(String text) {
		if(text == null) 
			throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Metoda generira i vraća sljedeći token, tj. sljedeću leksičku jedinku
	 * 
	 * @return sljedeći token
	 * @throws LexerException ako je došlo do pogreške
	 */
	public ElementToken nextElement() {
		if(this.currentIndex > this.data.length)
			throw new LexerException("Index je veći od veličine polja.");
		String text = new String();
		String number = new String();
		String function = new String();
		String operator = new String();
		String variable = new String();
		
		while(this.currentIndex < this.data.length) {
			
			if(this.lexerState == SmartScriptLexerState.TEXT) { // "text" ponašanje
				// uvijet za kraj TEXT načina ponašanja
				if(this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$' && 
						this.data[this.currentIndex - 1] != '\\') {
					if(text.length() != 0) {
						this.token = new ElementToken(ElementType.STRING, text);
						return this.token;
					}
					this.token = new ElementToken(ElementType.START_TAG, "{$");
					//this.setState(SmartScriptLexerState.TAG);
					this.currentIndex += 2;
					return this.token;
				}
				// obrada za ElementString
				if(this.data[this.currentIndex] == '\\' && (this.data[this.currentIndex + 1] == '{' || 
						this.data[this.currentIndex + 1] == '\\')) {
					text += this.data[++this.currentIndex];
					this.currentIndex++;
					continue;
				} else if(this.data[this.currentIndex] == '\\')
					throw new LexerException("Tekst nije ispravan. Ilegalan \\ znak");
				text += this.data[this.currentIndex++];
				if(this.currentIndex == this.data.length)
					break;
				continue;
			} else { // TAG način rada
				
				// preskakanje praznina
				if(this.data[this.currentIndex] == ' ' || this.data[this.currentIndex] == '\n' || 
						this.data[this.currentIndex] == '\r' || this.data[this.currentIndex] == '\t')
					this.currentIndex++;
				
				// obrada za ElementString
				if(this.data[this.currentIndex] == '"') {
					this.currentIndex++;
					while(true) {
						if(this.data[this.currentIndex] == '"') {
							this.currentIndex++;
							this.token = new ElementToken(ElementType.STRING, text);
							return this.token;
						}
						if(this.data[this.currentIndex] == '\\' && (this.data[this.currentIndex + 1] == '"' || 
								this.data[this.currentIndex + 1] == '\\')) {
							text += this.data[++this.currentIndex];
							this.currentIndex++;
						} else if(this.data[this.currentIndex] == '\\' && (this.data[this.currentIndex + 1] == 'n' || 
								this.data[this.currentIndex + 1] == 'r' || this.data[this.currentIndex + 1] == 't' )) {
							switch(this.data[this.currentIndex + 1]) {
							case 'n': {
								text += "\n";
								this.currentIndex += 2;
								break;
								}
							case 'r': {
								text += "\r";
								this.currentIndex += 2;
								break;
								}
							case 't': {
								text += "\t";
								this.currentIndex += 2;
								break;
								}
							}
						} else if(this.data[this.currentIndex] == '\\')
							throw new LexerException("Neispravan escape.");
						text += this.data[this.currentIndex++];
					}
				}
				
				// obrada za ElementVariable
				if(this.data[this.currentIndex] == '=') {
					this.currentIndex++;
					this.token = new ElementToken(ElementType.VARIABLE, "=");
					return this.token;
				} else if(Character.isLetter(this.data[this.currentIndex])) {
					int start = this.currentIndex++;
					while(this.currentIndex < this.data.length && (Character.isLetterOrDigit(this.data[this.currentIndex]) ||
							this.data[this.currentIndex] == '_')) {
						this.currentIndex++;
					}
					int end = this.currentIndex;
					variable = new String(this.data, start, end - start);
					this.token = new ElementToken(ElementType.VARIABLE, variable);
					return this.token;
				}
				
				// obrada za ElementOperator
				if(this.data[this.currentIndex] == '+' || this.data[this.currentIndex] == '*' || this.data[this.currentIndex] == '/' 
						|| (this.data[this.currentIndex] == '-' && !Character.isDigit(this.data[this.currentIndex + 1]))) {
					operator += this.data[this.currentIndex];
					this.currentIndex++;
					this.token = new ElementToken(ElementType.OPERATOR, operator);
					return this.token;
				}
				
				// obrada za ElementFunction
				if(this.data[this.currentIndex] == '@') {
					this.currentIndex++;
					if(!Character.isLetter(this.data[this.currentIndex]))
						throw new LexerException("Funkcija je krivo napisana.");
					function += this.data[this.currentIndex++];
					while(Character.isLetterOrDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '_') {
						function += this.data[this.currentIndex];
						this.currentIndex++;
					}
					if(this.data[this.currentIndex] != ' ' && this.data[this.currentIndex] != '\n' && 
						this.data[this.currentIndex] != '\r' && this.data[this.currentIndex] != '\t')
						throw new LexerException("Funkcija je krivo napisana.");
					this.token = new ElementToken(ElementType.FUNCTION, function);
					return this.token;
				}
				
				// obrada za ElementConstantDouble i ElementConstantInteger
				if(Character.isDigit(this.data[this.currentIndex]) || (this.data[this.currentIndex] == '-' && 
						Character.isDigit(this.data[this.currentIndex + 1]))) {
					number += this.data[this.currentIndex++];
					while(true) {
						if(Character.isDigit(this.data[this.currentIndex])) {
							number += this.data[this.currentIndex++];
							continue;
						}
						
						if(this.data[this.currentIndex] == '.' && Character.isDigit(this.data[this.currentIndex + 1]) && 
								Character.isDigit(this.data[this.currentIndex - 1])) {
							number += ".";
							this.currentIndex++;
							continue;
						}
					
						if(!Character.isDigit(this.data[this.currentIndex])) {
							if(number.contains(".")) {
								this.token = new ElementToken(ElementType.DOUBLE_CONSTANT, Double.parseDouble(number));
								return this.token;
							} else {
								this.token = new ElementToken(ElementType.INTEGER_CONSTANT, Integer.parseInt(number));
								return this.token;
							}
						}
					}
				}
				
				// uvijet za kraj TAG načina rada
				if(this.data[this.currentIndex] == '$' && this.data[this.currentIndex + 1] == '}') {
					this.token = new ElementToken(ElementType.END_TAG, "$}");
					this.currentIndex += 2;
					//this.setState(SmartScriptLexerState.TEXT);
					return this.token;
				}
			}
		}
		
		if(text.length() != 0) {
			this.token = new ElementToken(ElementType.STRING, text);
			return this.token;
		}
		
		if(this.currentIndex == this.data.length) {
			this.token = new ElementToken(ElementType.EOF, null);
			this.currentIndex++;
			return this.token;
		}
		
		return this.token;
	}
	
	/**
	 * Metoda vraća zadnji generirani token. Može se pozivati više puta.
	 * Ne pokreće generiranje sljedećeg tokena
	 * 
	 * @return zadnji generirani token
	 */
	public ElementToken getElementToken() {
		return this.token;
	}
	
	/**
	 * Metoda koja postavlja načina rada leksičkog analizatora
	 * 
	 * @param state način rada leksičkog analizatora
	 * @throws SmartScriptParserException ako se stanje želi postaviti na null
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null)
			throw new SmartScriptParserException("Stanje lexera ne može biti null.");
		this.lexerState = state;
	}
	
	/**
	 * Pomoćna metoda, dohvaća trenutni način rada Lexera
	 * 
	 * @return trenutni način rada lexera
	 */
	public SmartScriptLexerState getState() {
		return this.lexerState;
	}
}

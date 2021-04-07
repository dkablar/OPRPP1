package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Razred <code>ForLoopNode</code> predstavlja strukturu jedne for petlje.
 * Nasljeđuje razred <code>Node</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Stvara novi <code>ForLoopNode</code> s parametrima variable, startExpression, endExpression i stepExpression
	 * 
	 * @param variable varijabla u for petlji
	 * @param startExpression početni izraz for petlje
	 * @param endExpression uvijet prolaska for petlje
	 * @param stepExpression izraz napredovanja for petlje
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Getter, dohvaća varijablu
	 * 
	 * @return varijabla
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Getter, dohvaća početni izraz for petlje
	 * 
	 * @return početni izraz for petlje
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	 /**
	  * Getter, vraća uvijet prolaska for petlje
	  * 
	  * @return uvijet prolaska for petlje
	  */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * Getter, vraća izraz napredovanja for petlje
	 * 
	 * @return izraz napredovanja for petlje
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(this.getVariable().asText() + " ").append(this.getStartExpression().asText() + " ")
		.append(this.getEndExpression().asText() + " ").append(this.getStepExpression() == null ? "" : 
			this.getStepExpression().asText() + " ").append("$}");
		for(int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append("{$ END $}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if(!this.variable.asText().equals(other.variable.asText()))
			return false;
		if(!this.startExpression.asText().equals(other.startExpression.asText()))
			return false;
		if(!this.endExpression.asText().equals(other.endExpression.asText()))
			return false;
		if(this.stepExpression == null && other.stepExpression == null)
			return true;
		if(this.stepExpression != null && other.stepExpression != null && 
				this.stepExpression.asText().equals(other.stepExpression.asText()))
			return true;
		return false;
	}

}

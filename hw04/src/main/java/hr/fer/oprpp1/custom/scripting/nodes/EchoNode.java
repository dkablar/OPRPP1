package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Razred <code>EchoNode</code> predstavlja naredbu koja dinamički generira neki tekstualni ispis.
 * Nasljeđuje razred <code>Node</code>
 * 
 * @author Dorian Kablar
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	/**
	 * Stvara novi <code>EchoNode</code> s parametrom elems
	 * 
	 * @param elements elementi tekstualnog ispisa
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Getter, dohvaća elemente tekstualnog ispisa
	 * 
	 * @return elementi tekstualnog ispisa
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for(int i = 0; i < this.elements.length; i++) {
			sb.append(this.elements[i].asText() + " ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		for(int i = 0; i < this.elements.length; i++) {
			if(!this.elements[i].asText().equals(other.elements[i].asText()))
				return false;
		}
		return true;
	}
}

package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Razred <code>TextNode</code> predstavlja dio tekstualnih podataka.
 * Nasljeđuje razred <code>Node</code>
 * 
 * @author Dorian Kablar
 *
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Stvara novi <code>TextNode</code> sa parametrom text
	 * 
	 * @param text niz znakova
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Getter, vraća niz znakova koji predstavljaju varijablu text iz TextNode-a
	 * 
	 * @return niz znakova
	 */
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		return this.getText();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		if(!this.getText().equals(other.getText()))
			return false;
		return true;
	}
}

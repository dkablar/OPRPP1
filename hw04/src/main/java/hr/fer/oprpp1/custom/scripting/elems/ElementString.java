package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred <code>ElementString</code> predstavlja znakovni niz, 
 * nasljeđuje razred <code>Element</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ElementString extends Element {

	private String value;
	
	/**
	 * Stvara novi <code>ElementString</code> s parametrom value
	 * 
	 * @param value znakovni niz
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Getter, dohvaća znakovni niz
	 * 
	 * @return znakovni niz
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Metoda vraća znakovni niz
	 * 
	 * @return znakovni niz
	 */
	@Override
	public String asText() {
		return this.value;
	}
}

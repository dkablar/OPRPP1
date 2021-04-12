package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred <code>ElementFunction</code> predstavlja programske funkcije 
 * nasljeđuje razred <code>Element</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * Stvara novi <code>ElementFunction</code> s parametrom value
	 * 
	 * @param name ime funkcije
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Getter, dohvaća ime funkcije
	 * 
	 * @return ime funkcije
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Metoda vraća ime funkcije
	 * 
	 * @return ime funkcije
	 */
	@Override
	public String asText() {
		return this.getName();
	}
}

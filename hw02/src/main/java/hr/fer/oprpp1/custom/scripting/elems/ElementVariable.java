package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred <code>ElementVariable</code> predstavlja varijablu, nasljeđuje razred <code>Element</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	/**
	 * Stvara novi <code>ElementVariable</code> s parametrom name
	 * 
	 * @param name ime varijable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Getter, dohvaća ime varijable
	 * 
	 * @return ime varijable
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Metoda vraća ime varijable kao String
	 * 
	 * @return ime varijable
	 */
	@Override
	public String asText() {
		return this.getName();
	}
}

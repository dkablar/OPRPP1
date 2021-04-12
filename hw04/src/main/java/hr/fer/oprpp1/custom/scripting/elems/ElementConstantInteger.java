package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred <code>ElementConstantInteger</code> predstavlja cjelobrojnu konstantu, 
 * nasljeđuje razred <code>Element</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Stvara novi <code>ElementConstantInteger</code> s parametrom value
	 * 
	 * @param value vrijednost cjelobrojne konstante
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Getter, dohvaća vrijednost cjelobrojne konstante
	 * 
	 * @return vrijednost cjelobrojne konstante
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Metoda vraća vrijednost cjelobrojne konstante u tipu String
	 * 
	 * @return vrijednost cjelobrojne konstante
	 */
	@Override
	public String asText() {
		return String.valueOf(this.getValue());
	}

}

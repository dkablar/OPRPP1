package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred <code>ElementConstantDouble</code> predstavlja realnu konstantu, 
 * nasljeđuje razred <code>Element</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	/**
	 * Stvara novi <code>ElementConstantDouble</code> s parametrom value
	 * 
	 * @param value vrijednost cjelobrojne konstante
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter, dohvaća vrijednost realne konstante
	 * 
	 * @return vrijednost realne konstante
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Metoda vraća vrijednost realne konstante u tipu String
	 * 
	 * @return vrijednost realne konstante
	 */
	@Override
	public String asText() {
		return String.valueOf(this.getValue());
	}
	
}

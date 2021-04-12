package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element{

	private String symbol;
	
	/**
	 * Stvara novi <code>ElementOperator</code> s parametrom value
	 * 
	 * @param symbol simbol, operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Getter, dohvaća operator
	 * 
	 * @return operator
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Metoda vraća operator
	 * 
	 * @return operator
	 */
	@Override
	public String asText() {
		return this.getSymbol();
	}
}

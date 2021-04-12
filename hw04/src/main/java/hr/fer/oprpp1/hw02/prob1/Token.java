package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred <code>Token</code> predstavlja leksičku jedniku koja se koristi u procesima jezičnog procesora
 * 
 * @author Dorian Kablar
 *
 */
public class Token {

	private TokenType type;
	private Object value;
	
	/**
	 * Stvara novi <code>Token</code> s parametrima type i value
	 * 
	 * @param type kategorija leksičke jednike
	 * @param value znak, ili niz znakova, koji predstavlja leksičku jedinku
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Metoda vraća niz znakova koji predstavlja leksičku jedniku
	 * 
	 * @return niz znakova koji predstavlja leksičku jedinku
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Metoda vraća kategoriju leksičke jedinke
	 * 
	 * @return kategorija leksičke jedinke
	 */
	public TokenType getType() {
		return this.type;
	}
}

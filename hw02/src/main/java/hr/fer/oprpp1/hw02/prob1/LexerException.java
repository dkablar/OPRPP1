package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred <code>LexerException</code> predstavlja iznimku koja se javlja kada
 * dođe do pogreške u radu leksičkog analizatora
 * 
 * @author Dorian Kablar
 *
 */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * stvara novi <code>LexerException</code>
	 */
	public LexerException() {}
	
	/**
	 * stvara novi <code>LexerException</code> čiji je parametar message
	 * 
	 * @param message poruka iznimke
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * stvara novi <code>LexerException</code> čiji je parametar cause
	 * 
	 * @param cause iznimka
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * stvara novi <code>LexerException</code> čiji su parametri message i cause
	 * 
	 * @param message poruka iznimke
	 * @param cause iznimka
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}

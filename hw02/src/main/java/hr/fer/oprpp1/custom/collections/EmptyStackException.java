package hr.fer.oprpp1.custom.collections;

/**
 * Razred <code>EmptyStackException</code> predstavlja iznimku koja se javlja kada
 * korisnik želi pristupiti praznom stogu
 * 
 * @author Dorian Kablar
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * stvara novi <code>EmptyStackException</code>
	 */
	public EmptyStackException() {}
	
	/**
	 * stvara novi <code>EmptyStackException</code> čiji je parametar message
	 * 
	 * @param message poruka iznimke
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * stvara novi <code>EmptyStackException</code> čiji je parametar cause
	 * 
	 * @param cause iznimka
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * stvara novi <code>EmptyStackException</code> čiji su parametri message i cause
	 * 
	 * @param message poruka iznimke
	 * @param cause iznimka
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}

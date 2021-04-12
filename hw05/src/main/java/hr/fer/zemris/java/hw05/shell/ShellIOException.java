package hr.fer.zemris.java.hw05.shell;

/**
 * Razred <code>ShellIOException</code> predstavlja iznimku koja se javlja kada dođe do greške u radu
 * s shell-om
 * 
 * @author Dorian Kablar
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * stvara novi <code>ShellIOException</code>
	 */
	public ShellIOException() {}
	
	/**
	 * stvara novi <code>ShellIOException</code> čiji je parametar message
	 * 
	 * @param message poruka iznimke
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * stvara novi <code>ShellIOException</code> čiji je parametar cause
	 * 
	 * @param cause iznimka
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * stvara novi <code>ShellIOException</code> čiji su parametri message i cause
	 * 
	 * @param message poruka iznimke
	 * @param cause iznimka
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

}

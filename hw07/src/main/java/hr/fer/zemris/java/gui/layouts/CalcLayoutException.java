package hr.fer.zemris.java.gui.layouts;

/**
 * Razred <code>CalcLayoutException</code> predstavlja iznmku koja se može pojaviti kod stvaranja prikaza kalkulatora
 * 
 * @author Dorian Kablar
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * stvara novi <code>CalcLayoutException</code>
	 */
	public CalcLayoutException() {}
	
	/**
	 * stvara novi <code>CalcLayoutException</code> čiji je parametar message
	 * 
	 * @param message poruka iznimke
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * stvara novi <code>CalcLayoutException</code> čiji je parametar cause
	 * 
	 * @param cause iznimka
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * stvara novi <code>CalcLayoutException</code> čiji su parametri message i cause
	 * 
	 * @param message poruka iznimke
	 * @param cause iznimka
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

}

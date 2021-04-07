package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Razred <code>SmartScriptParserException</code> predstavlja iznimku koja se javlja kada
 * dođe do pogreške u radu parsera
 * 
 * @author Dorian Kablar
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * stvara novi <code>SmartScriptParserException</code>
	 */
	public SmartScriptParserException() {}
	
	/**
	 * stvara novi <code>SmartScriptParserException</code> čiji je parametar message
	 * 
	 * @param message poruka iznimke
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * stvara novi <code>SmartScriptParserException</code> čiji je parametar cause
	 * 
	 * @param cause iznimka
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * stvara novi <code>SmartScriptParserException</code> čiji su parametri message i cause
	 * 
	 * @param message poruka iznimke
	 * @param cause iznimka
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}

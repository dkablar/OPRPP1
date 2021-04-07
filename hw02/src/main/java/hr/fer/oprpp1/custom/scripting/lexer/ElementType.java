package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeracija <code>ElementType</code> označava tip elemenata u lexeru
 * 
 * @author Dorian Kablar
 *
 */
public enum ElementType {

	// varijabla
	VARIABLE,
	// String
	STRING,
	// operator
	OPERATOR,
	// funkcija
	FUNCTION,
	// cjelobrojna konstanta
	INTEGER_CONSTANT,
	// realna konstanta
	DOUBLE_CONSTANT,
	// kraj dokumenta
	EOF,
	//početak TAGa
	START_TAG,
	//kraj TAGa
	END_TAG
}

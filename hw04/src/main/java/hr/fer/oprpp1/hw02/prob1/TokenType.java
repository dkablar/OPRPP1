package hr.fer.oprpp1.hw02.prob1;

/**
 * Enum <code>TokenType</code> navodi sve kategorije leksičkih jedinki
 * 
 * @author Dorian Kablar
 *
 */
public enum TokenType {
	// end of file
	EOF, 
	// riječ, string
	WORD, 
	// broj
	NUMBER,
	// simbol, sve osim riječi, brojeva i escape znakova
	SYMBOL
}

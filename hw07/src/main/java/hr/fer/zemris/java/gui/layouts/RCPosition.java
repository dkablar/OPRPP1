package hr.fer.zemris.java.gui.layouts;

/**
 * Razred <code>RCPosition</code> predstavlja poziciju komponente u razmještaju kalkulatora
 * 
 * @author Dorian Kablar
 *
 */
public class RCPosition {

	private int row;
	private int column;
	
	/**
	 * Konstruktor, stvara novi <code>RCPosition</code>
	 * 
	 * @param row redak komponente
	 * @param column stupac komponente
	 * @throws CalcLayoutException ako je zadani redak veći od 5, stupac veći od 7
	 */
	public RCPosition(int row, int column) {
		if(row < 1 || row > 5)
			throw new CalcLayoutException("The row argument can be between 1 and 5");
		if(column < 1 || column > 7)
			throw new CalcLayoutException("The column argument can be between 1 and 7");
		if(row == 1 && (column > 1 && column < 6))
			throw new CalcLayoutException("If the row argument is 1, the column argument can be 1, 6 or 7");
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter, vraća redak komponente
	 * 
	 * @return redak komponente
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Getter, vraća stupac komponente
	 * 
	 * @return stupac komponente
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Metoda koja iz zadanog teksta stvara novi <code>RCPosition</code>
	 * 
	 * @param text tekst koji treba pretvoriti u <code>RCPosition</code>
	 * @return <code>RCPosition</code> dobiven na temelju pretvorbe teksta
	 */
	public static RCPosition parse(String text) {
		String[] args = text.split(",");
		if(args.length != 2)
			throw new IllegalArgumentException("Invalid arguments given.");
		return new RCPosition(Integer.valueOf(args[0].trim()), Integer.valueOf(args[1].trim()));
	}
}

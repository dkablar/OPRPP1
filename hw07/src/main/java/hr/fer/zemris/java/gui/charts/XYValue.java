package hr.fer.zemris.java.gui.charts;

/**
 * Razred <code>XYValue</code> predstavlja jedan par vrijedsnoti koje se prikazuju na histogramu
 * 
 * @author Dorian Kablar
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	/**
	 * Konstruktor, stvara novi <code>XYValue</code>
	 * 
	 * @param x x komponenta vrijednosti
	 * @param y y komponenta vrijednosti
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter, vraća x komponentu
	 * 
	 * @return x komponenta
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter, vraća y komponentu
	 * 
	 * @return y komponenta
	 */
	public int getY() {
		return y;
	}
}

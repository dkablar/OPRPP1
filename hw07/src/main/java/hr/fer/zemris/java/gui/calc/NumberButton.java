package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Razred <code>NumberButton</code> predstavlja gumbe koji imaju funkcionalnost brojeva, kad se klikne, 
 * na ekranu se pokaže pripadajući broj
 * 
 * @author Dorian Kablar
 *
 */
public class NumberButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor, stvara novi <code>NumberButton</code>
	 * 
	 * @param text tekst koji će se prikazati na gumbu
	 */
	public NumberButton(String text) {
		this.setText(text);
		this.setFont(this.getFont().deriveFont(30l));
		this.setBackground(Color.CYAN);
	}
}

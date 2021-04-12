package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Razred <code>BinaryOperatorButton</code> modelira gumbe koji imaju funkcionalnost binarnog operatora
 * 
 * @author Dorian Kablar
 *
 */
public class BinaryOperatorButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DoubleBinaryOperator op;
	private DoubleBinaryOperator op2;
	private String text;
	private String text2;
	
	/**
	 * Konstruktor, stvara novi <code>BinaryOperatorButton</code>
	 * 
	 * @param op binarni operator
	 * @param op2 inverzni binarni operator
	 * @param text tekst koji se treba pojaviti na gumbu
	 * @param text2 inverzni tekst koji se treba pojaviti na gumbu
	 */
	public BinaryOperatorButton(DoubleBinaryOperator op, DoubleBinaryOperator op2, String text, String text2) {
		this.text = text;
		this.text2 = text2;
		this.setText(this.text);
		this.setBackground(Color.CYAN);
		this.op = op;
		this.op2 = op2;
	}
	
	/**
	 * Getter, vraća zapamćeni operator
	 * 
	 * @return binarni operator
	 */
	public DoubleBinaryOperator getOperator() {
		return this.op;
	}
	
	/**
	 * Metoda koja mijenja operator, ovisno o stanju gumba "Inv"
	 */
	public void changeOperation() {
		String tmp = this.text;
		this.text = this.text2;
		this.text2 = tmp;
		this.setText(this.text);
		DoubleBinaryOperator tmp2 = this.op;
		this.op = this.op2;
		this.op2 = tmp2;
	}
}

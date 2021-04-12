package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Razred <code>Calculator</code> predstavlja implementaciju kalkulatora
 * 
 * @author Dorian kablar
 *
 */
public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CalcModel calcModel;
	private Stack<Double> stack;
	
	/**
	 * Konstruktor, stvara novi kalkulator
	 */
	public Calculator() {
		this.calcModel = new CalcModelImpl();
		this.stack = new Stack<>();
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	/**
	 * Privatna metoda koja incijalizira GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		JLabel label = new JLabel();
		label.setBackground(Color.YELLOW);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(label.getFont().deriveFont(30l));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label.setText(this.calcModel.toString());
		
		CalcValueListener clv = model -> {
			label.setText(model.toString());
		};
		
		this.calcModel.addCalcValueListener(clv);
		
		cp.add(label, new RCPosition(1,1));
		
		ActionListener numberButtonAction = e -> {
			NumberButton b = (NumberButton)e.getSource();
			this.calcModel.insertDigit(Integer.valueOf(b.getText()));
		};
		
		int num = 0;
		for(int i = 5; i > 1; i--) {
			for(int j = 3; j < 6 ; j++) {
				NumberButton button = new NumberButton(String.valueOf(num));
				button.addActionListener(numberButtonAction);
				cp.add(button, new RCPosition(i,j));
				num++;
				if(i == 5)
					break;
			}
		}
		
		ActionListener equalityButtonAction = e -> {
			DoubleBinaryOperator op = this.calcModel.getPendingBinaryOperation();
			if(op != null && this.calcModel.isActiveOperandSet()) {
				this.calcModel.setValue(op.applyAsDouble(this.calcModel.getActiveOperand(), this.calcModel.getValue()));
			}
			this.calcModel.clearActiveOperand();
		};
		
		JButton equalityButton = new JButton("=");
		equalityButton.setBackground(Color.CYAN);
		equalityButton.addActionListener(equalityButtonAction);
		cp.add(equalityButton, new RCPosition(1,6));
		
		JButton clearButton = new JButton("clr");
		clearButton.setBackground(Color.CYAN);
		ActionListener clearButtonAction = e -> {
			this.calcModel.clear();
			label.setText(this.calcModel.toString());
		};
		clearButton.addActionListener(clearButtonAction);
		cp.add(clearButton, new RCPosition(1,7));
		
		JButton resetButton = new JButton("res");
		resetButton.setBackground(Color.CYAN);
		ActionListener resetButtonAction = e -> this.calcModel.clearAll();
		resetButton.addActionListener(resetButtonAction);
		cp.add(resetButton, new RCPosition(2,7));
		
		JButton swapButton = new JButton("+/-");
		swapButton.setBackground(Color.CYAN);
		ActionListener swapButtonAction = e -> this.calcModel.swapSign();
		swapButton.addActionListener(swapButtonAction);
		cp.add(swapButton, new RCPosition(5, 4));
		
		JButton decimalPointButton = new JButton(".");
		decimalPointButton.setBackground(Color.CYAN);
		ActionListener decimalPointAction = e -> this.calcModel.insertDecimalPoint();
		decimalPointButton.addActionListener(decimalPointAction);
		cp.add(decimalPointButton, new RCPosition(5,5));
		
		JButton pushButton = new JButton("push");
		pushButton.setBackground(Color.CYAN);
		ActionListener pushAction = e -> this.stack.push(this.calcModel.getValue());
		pushButton.addActionListener(pushAction);
		cp.add(pushButton, new RCPosition(3,7));
		
		JButton popButton = new JButton("pop");
		popButton.setBackground(Color.CYAN);
		ActionListener popAction = e -> this.calcModel.setValue(this.stack.pop());
		popButton.addActionListener(popAction);
		cp.add(popButton, new RCPosition(4,7));
		
		ActionListener binaryOperatorAction = e -> {
			BinaryOperatorButton b = (BinaryOperatorButton)e.getSource();
			DoubleBinaryOperator op = this.calcModel.getPendingBinaryOperation();
			if(op != null && this.calcModel.isActiveOperandSet()) {
				this.calcModel.setValue(op.applyAsDouble(this.calcModel.getActiveOperand(), this.calcModel.getValue()));
			}
			
			this.calcModel.setActiveOperand(this.calcModel.getValue());
			this.calcModel.clear();
			this.calcModel.freezeValue(String.valueOf(this.calcModel.getActiveOperand()));
			this.calcModel.setPendingBinaryOperation(b.getOperator());
		};
		
		BinaryOperatorButton plusButton = new BinaryOperatorButton((left, right) -> left + right, null, "+", null);
		plusButton.addActionListener(binaryOperatorAction);
		cp.add(plusButton, new RCPosition(5,6));
		
		BinaryOperatorButton minusButton = new BinaryOperatorButton((left, right) -> left - right, null, "-", null);
		minusButton.addActionListener(binaryOperatorAction);
		cp.add(minusButton, new RCPosition(4,6));
		
		BinaryOperatorButton multiplyButton = new BinaryOperatorButton((left, right) -> left * right, null, "*", null);
		multiplyButton.addActionListener(binaryOperatorAction);
		cp.add(multiplyButton, new RCPosition(3,6));
		
		BinaryOperatorButton divideButton = new BinaryOperatorButton((left, right) -> left / right, null, "/", null);
		divideButton.addActionListener(binaryOperatorAction);
		cp.add(divideButton, new RCPosition(2,6));
		
		JCheckBox inv = new JCheckBox("Inv");
		cp.add(inv, new RCPosition(5,7));
		
		BinaryOperatorButton potencijaButton = new BinaryOperatorButton((left, right) -> Math.pow(left, right), (left, right) -> Math.pow(left, 1/right),
				"x^n", "x^(1/n)");
		potencijaButton.addActionListener(binaryOperatorAction);
		cp.add(potencijaButton, new RCPosition(5,1));
		
		ActionListener unaryAction = e -> {
			UnaryOperatorButton b = (UnaryOperatorButton) e.getSource();
			DoubleUnaryOperator op = b.getOperator();
			this.calcModel.setValue(op.applyAsDouble(calcModel.getValue()));
		};
		
		UnaryOperatorButton sinus = new UnaryOperatorButton(Math::sin, Math::asin,
				"sin", "arcsin");
		sinus.addActionListener(unaryAction);
		UnaryOperatorButton cosinus = new UnaryOperatorButton(Math::cos, Math::acos,
				"cos", "arccos");
		cosinus.addActionListener(unaryAction);
		UnaryOperatorButton tangens = new UnaryOperatorButton(Math::tan, Math::atan,
				"tan", "arctan");
		tangens.addActionListener(unaryAction);
		UnaryOperatorButton cotangens = new UnaryOperatorButton(operand -> 1. / Math.tan(operand), operand -> Math.atan(Math.tan(operand)),
				"ctg", "arcctg");
		cotangens.addActionListener(unaryAction);
		UnaryOperatorButton inverseButton = new UnaryOperatorButton(operand -> 1. / operand, operand -> 1. / operand, "1/x", "1/x");
		inverseButton.addActionListener(unaryAction);
		UnaryOperatorButton logarithm = new UnaryOperatorButton(operand -> Math.log10(operand), operand -> Math.pow(10., operand),
				"log", "10^x");
		logarithm.addActionListener(unaryAction);
		UnaryOperatorButton lnButton = new UnaryOperatorButton(operand -> Math.log(operand), operand -> Math.pow(Math.E, operand),
				"ln", "e^x");
		lnButton.addActionListener(unaryAction);
		
		cp.add(sinus, new RCPosition(2,2));
		cp.add(cosinus, new RCPosition(3,2));
		cp.add(tangens, new RCPosition(4,2));
		cp.add(cotangens, new RCPosition(5,2));
		cp.add(inverseButton, new RCPosition(2,1));
		cp.add(logarithm, new RCPosition(3,1));
		cp.add(lnButton, new RCPosition(4,1));
		
		inv.addActionListener(e -> {
			sinus.changeOperation();
			cosinus.changeOperation();
			tangens.changeOperation();
			cotangens.changeOperation();
			logarithm.changeOperation();
			lnButton.changeOperation();
			potencijaButton.changeOperation();
			});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}

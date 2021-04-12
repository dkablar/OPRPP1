package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred <code>CalcModelImpl</code> predstavlja implementaciju modela kalkulatora
 * 
 * @author Dorian Kablar
 *
 */
public class CalcModelImpl implements CalcModel {
	
	private boolean isEditable;
	private boolean isPositive;
	private String inputValue;
	private double numericValue;
	private String frozenValue;
	private DoubleBinaryOperator pendingOperation;
	private OptionalDouble activeOperand;
	private List<CalcValueListener> listenerList;
	
	/**
	 * Konstruktor, stvara novi <code>CalcModelImpl</code>
	 */
	public CalcModelImpl() {
		this.listenerList = new ArrayList<>();
		this.inputValue = new String();
		this.numericValue = 0.;
		this.frozenValue = null;
		this.isEditable = true;
		this.isPositive = true;
		this.pendingOperation = null;
		this.activeOperand = OptionalDouble.empty();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listenerList.remove(l);
	}
	
	@Override
	public String toString() {
		if(this.hasFrozenValue() && this.frozenValue.length() > 0)
			return this.isPositive ? this.frozenValue : "-" + this.frozenValue;
		return this.isPositive ? "0" : "-0";
	}

	@Override
	public double getValue() {
		return this.numericValue;
	}

	@Override
	public void setValue(double value) {
		this.numericValue = value;
		this.inputValue = String.valueOf(value);
		this.isEditable = false;
		this.freezeValue(this.inputValue);
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public boolean isEditable() {
		return this.isEditable;
	}

	@Override
	public void clear() {
		this.inputValue = new String();
		this.numericValue = 0.;
		this.isEditable = true;
		this.freezeValue(this.inputValue);
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void clearAll() {
		this.inputValue = new String();
		this.numericValue = 0.;
		this.activeOperand = OptionalDouble.empty();
		this.pendingOperation = null;
		this.freezeValue(this.inputValue);
		this.isEditable = true;
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!this.isEditable)
			throw new CalculatorInputException("The value is not editable.");
		this.isPositive = this.isPositive ? false : true;
		this.numericValue *= -1;
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!this.isEditable || this.inputValue.contains(".") || this.inputValue.length() == 0)
			throw new CalculatorInputException("Value already has a decimal point.");
		this.inputValue += ".";
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!this.isEditable)
			throw new CalculatorInputException("Model is not editable.");
		
		String tmp = (this.inputValue.startsWith("0") && !this.inputValue.contains(".") && digit == 0) 
				? this.inputValue : this.inputValue + digit;
		
		try {
			this.numericValue = this.isPositive ? Double.valueOf(tmp) : Double.valueOf(tmp) * -1;
		} catch (NumberFormatException ex) {
			throw new CalculatorInputException("The variable is not numeric, it cannot be parsed.");
		}
		
		if(this.numericValue > Double.MAX_VALUE)
			throw new CalculatorInputException("Number is too big.");
		this.inputValue = tmp;
		this.freezeValue(this.inputValue);
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public boolean isActiveOperandSet() {
		return this.activeOperand.isPresent();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!this.activeOperand.isPresent())
			throw new IllegalStateException("Active operand is not set.");
		return this.activeOperand.getAsDouble();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = OptionalDouble.empty();
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void freezeValue(String value) {
		int i = 0;
		while(i+1 < value.length() && value.charAt(i+1) != '.')
			if(value.charAt(i) == '0')
				value = value.substring(i+1);
			else
				break;
		this.frozenValue = value;
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public boolean hasFrozenValue() {
		return this.frozenValue != null;
	}

}

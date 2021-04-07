package hr.fer.oprpp1.hw01.demo;

import hr.fer.oprpp1.hw01.ComplexNumber;

/**
 * Razred <code>ComplexDemo</code> služi kao demonstracija rada razreda <code>ComplexNumber</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ComplexDemo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		System.out.println(c1);
		ComplexNumber c2 = ComplexNumber.parse("i");
		System.out.println(c2);
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
}

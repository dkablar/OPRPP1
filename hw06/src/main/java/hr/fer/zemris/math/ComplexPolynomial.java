package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>ComplexPolynomial</code>  predstavlja polinom kompleksnih brojeva u obliku
 * f(z) = zn*z^n + zn-1*z^n-1 + ... + z1*z + z0, gdje su svi z-ovi kompleksni brojevi
 * 
 * @author Dorian Kablar
 *
 */
public class ComplexPolynomial {

	private final List<Complex> list = new ArrayList<>();
	
	/**
	 * Konstruktor, stvara novi <code>ComplexPolynomial</code> s argumentom factors
	 * 
	 * @param factors lista kompleksnih brojeva koje tvor polinom
	 */
	public ComplexPolynomial(Complex ...factors) {
		for(Complex complex : factors) {
			list.add(complex);
		}
	}
	
	/**
	 * Metoda koja vraća stupanj trenutnog polinoma
	 * 
	 * @return stupanj trenutnog polinoma
	 */
	public short order() {
		return this.list.size() < 1 ? 0 : (short) (this.list.size()-1);
	}
	
	/**
	 * Metoda koja množi trenutni polinom sa zadanim
	 * 
	 * @param p polinom s kojim će se pomnožiti trenutni polinom
	 * @return polinom koji predstavlja umnožak dva polinoma
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] arr = new Complex[this.list.size() + p.list.size() - 1];
		
		for(int i = 0; i < arr.length; i++)
			arr[i] = Complex.ZERO;
		
		for(int i = 0; i < this.list.size(); i++) {
			for(int j = 0; j < p.list.size(); j++) {
				arr[i+j] = arr[i+j].add(this.list.get(i).multiply(p.list.get(j)));
			}
		}
		
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * Metoda koja računa derivaciju trenutnog polinoma po z
	 * 
	 * @return polinom koji predstavlja derivaciju trenutnog polinoma po z
	 */
	public ComplexPolynomial derive() {
		Complex[] arr = new Complex[this.list.size()-1];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = new Complex(this.list.get(i+1).getRe()*(i+1), this.list.get(i+1).getIm()*(i+1));
		}
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * Metoda koja za zadani kompleksni broj z računa vrijednost polinoma
	 * 
	 * @param z kompleksni broj za koji se računa vrijednost polinoma
	 * @return kompleksni broj koji predstavlja vrijednost trenutnog polinoma u z
	 */
	public Complex apply(Complex z) {
		if(this.list.size() == 0)
			throw new IllegalArgumentException("Polinom nije definiran.");
		Complex result = list.get(0);
		for(int i = 1; i < this.list.size(); i++) {
			result = result.add(list.get(i).multiply(z.power(i)));
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = this.list.size()-1; i >= 0; i--) {
			if(i > 0)
				sb.append("(" + this.list.get(i).toString() + ")*z^" + i + "+");
			else 
				sb.append("(" + this.list.get(i).toString() + ")");
		}
		return sb.toString();
	}
}

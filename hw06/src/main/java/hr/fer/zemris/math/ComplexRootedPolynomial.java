package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>ComplexRootedPolynomial</code> predstavlja polinom kompleksnih brojeva u obliku
 * f(z) = z0*(z-z1)*(z-z2)*...*(z-zn), gdje su svi z-ovi kompleksni brojevi
 * 
 * @author Dorian Kablar
 *
 */
public class ComplexRootedPolynomial {

	private final Complex constant;
	private final List<Complex> list = new ArrayList<>();
	
	/**
	 * Konstruktor, stvara novi <code>ComplexRootedPolynomial</code> s argumentima constant i roots
	 * 
	 * @param constant konstanta, z0
	 * @param roots lista nultočaka polinoma
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ...roots) {
		this.constant = constant;
		for(Complex complex : roots) {
			list.add(complex);
		}
	}
	
	/**
	 * Metoda koja, za zadani z, računa vrijednost polinoma
	 * 
	 * @param z kompleksni broj za koji se računa vrijednost polinoma
	 * @return kompleksni broj koji predstavlja vrijednost polinoma
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();
		result = result.add(this.constant);
		for(Complex complex : list) {
			result = result.multiply(z.sub(complex));
		}
		return result;
	}
	
	/**
	 * Metoda koja <code>ComplexRootedPolynomial</code> pretvara u <code>ComplexPolynomial</code> oblik
	 * 
	 * @return <code>ComplexPolynomial</code> oblik trenutnog polinoma
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] arr = new ComplexPolynomial[this.list.size()];
		ComplexPolynomial res = new ComplexPolynomial(this.constant);
		
		for(int i = 0; i < this.list.size(); i++) {
			arr[i] = new ComplexPolynomial(this.list.get(i), Complex.ONE);
		}
		
		for(int i = 0; i < arr.length; i++) {
			res = res.multiply(arr[i]);
		}
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + this.constant.toString() + ")");
		for(Complex complex : list) {
			sb.append("*(z-(").append(complex.toString().length() == 0 ? "0.0+i0.0" : complex.toString()).append("))");
		}
		return sb.toString();
	}
	
	/**
	 * Metoda koja nalazi index najbliže nultočke za zadani kompleksni broj z unutar zadanog treshold-a.
	 * Ako takva nultočka ne postoji, vraća se -1
	 * 
	 * @param z kompleksni broj za koji se traži najbliža nultočka
	 * @param treshold granica unutar koje mora biti razlika izmežu nultočke i z-a
	 * @return index najbliže nultočke, -1 ako takve nema
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double distance = 0, tmp;
		int index = 0;
		if(this.list.size() == 0)
			return -1;
		
		distance = Math.sqrt(Math.pow(this.list.get(0).getRe() - z.getRe(), 2.) + Math.pow(this.list.get(0).getIm() - z.getIm(), 2.));
		for(int i = 1; i < list.size(); i++) {
			tmp = Math.sqrt(Math.pow(this.list.get(i).getRe() - z.getRe(), 2.) + Math.pow(this.list.get(i).getIm() - z.getIm(), 2.));
			if (tmp < distance) {
				distance = tmp;
				index = i;
			}
		}
		
		return distance <= treshold ? index : -1;
	}
}

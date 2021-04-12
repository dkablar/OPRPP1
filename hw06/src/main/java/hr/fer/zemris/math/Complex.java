package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>Complex</code> predstavlja kompleksni broj i nudi razne aritmetičke operacije nad njim
 * 
 * @author Dorian Kablar
 *
 */
public class Complex {

	private final double re;
	private final double im;
	
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Prazni konstruktor
	 */
	public Complex() {
		this.re = 0.0;
		this.im = 0.0;
	}
	
	/**
	 * Konstruktor, stvara novi <code>Complex</code> sa zadanim realnim i imaginarnim dijelom
	 * 
	 * @param re realni dio
	 * @param im imaginarni dio
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter, dohvaća realni dio kompleksnog broja
	 * 
	 * @return realni dio
	 */
	public double getRe() {
		return this.re;
	}
	
	/**
	 * Getter, vraća imaginarni dio kompleksnog broja
	 * 
	 * @return imaginarni dio
	 */
	public double getIm() {
		return this.im;
	}
	
	/**
	 * Metoda koja računa modul kompleksnog broja
	 * 
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.re, 2.) + Math.pow(this.im, 2.));
	}
	
	/**
	 * Metoda koja množi trenutni kompleksni broj sa zadanim
	 * 
	 * @param c kompleksni broj s kojim se množi
	 * @return umnožak
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.im * c.re + this.re * c.im);
	}
	
	/**
	 * Metoda koja djeli trenutni kompleksni broj sa zadanim
	 * 
	 * @param c kompleksni broj s kojim se dijeli
	 * @return količnik
	 */
	public Complex divide(Complex c) {
		return new Complex((this.re * c.re + this.im * c.im) / (Math.pow(c.re, 2) + Math.pow(c.im, 2)),
				(this.im * c.re - this.re * c.im) / (Math.pow(c.re, 2) + Math.pow(c.im, 2)));
	}
	
	/**
	 * Metoda koja zbraja trenutni kompleksni broj sa zadanim
	 * 
	 * @param c kompleksni broj s kojim se zbraja
	 * @return zbroj
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Metoda koja od trenutnog kompleksnog broja oduzima zadani
	 * 
	 * @param c kompleksni broj koji se oduzima od trenutnog
	 * @return razlika
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Metoda koja vraća trenutni kompleksni broj pomnožen s -1
	 * 
	 * @return rezultat množenja s -1
	 */
	public Complex negate() {
		return new Complex(-1.*this.re, -1.*this.im);
	}
	
	/**
	 * Metoda koja potencira trenutni kompleksni broj na zadanu potenciju
	 * 
	 * @param n potencija na koju treba potencirati
	 * @return potencirani kompleksni broj
	 * @throws IllegalArgumentException ako je potencija negativna
	 */
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("Potencija mora biti >= 0.");
		
		double magnitude = this.module();
		double angle = Math.atan2(this.im, this.re);
		
		return new Complex(Math.pow(magnitude, n) * Math.cos(n*angle),
				Math.pow(magnitude, n) * Math.sin(n*angle));
	}
	
	/**
	 * Metoda koja računa n-ti korijen kompleksnog broja
	 * 
	 * @param n potencija korijena
	 * @return lista korijenova, od 1. do n-tog
	 * @throws IllegalArgumentException ako je n < 1
	 */
	public List<Complex> root(int n) {
		if(n <= 0) throw new IllegalArgumentException("Potencija korijena mora biti > 0.");
		
		double magnitude = this.module();
		double angle = Math.atan2(this.im, this.re);
		List<Complex> result = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			result.add(new Complex(Math.pow(magnitude, 1.0 / n) * Math.cos((angle + 2*i*Math.PI) / n),
					Math.pow(magnitude, 1.0 / n) * Math.sin((angle + 2*i*Math.PI) / n)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.re != 0.0)
			sb.append(this.re);
		if(this.im != 1.0 & this.im != -1.0) {
			if(this.im < 0.0 && this.re != 0.0)
				sb.append("-i").append(this.im*-1);
			else if(this.im < 0.0)
				sb.append("-i").append(this.im*-1);
			else if(this.im > 0.0 && this.re != 0.0)
				sb.append("+").append("i").append(this.im);
			else if(this.im > 0.0)
				sb.append("i").append(this.im);
		} else {
			if(this.re != 0.0)
				sb.append(this.im > 0.0 ? "+" : "-").append("i");
			else
				sb.append(this.im > 0.0 ? "" : "-").append("i");
		}
		return sb.toString();
	}
}

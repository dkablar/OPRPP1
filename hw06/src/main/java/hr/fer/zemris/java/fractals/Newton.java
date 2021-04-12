package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Razred <code>Newton</code> iscrtava prikaz fraktala temeljenog na Newton-Raphson iteraciji
 * 
 * @author Dorian Kablar
 *
 */
public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> roots = new ArrayList<>();
		
		int index = 1;
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("Root " + index++ + "> ");
			String line = sc.nextLine().trim();
			if(line.equals("done"))
				break;
			
			if(line.length() == 0)
				throw new IllegalArgumentException("Neispravan argument.");
			
			String tmp = new String();
			double re, im;
			
			if(line.startsWith("i")) {
				if(line.length() == 1) {
					roots.add(Complex.IM);
					continue;
				}
				else {
					tmp = line.substring(1);
					roots.add(new Complex(0, Double.valueOf(tmp)));
					continue;
				}
			} else if(line.startsWith("-i")) {
				if(line.length() == 2) {
					roots.add(Complex.IM_NEG);
					continue;
				}
				else {
					tmp = line.substring(2);
					roots.add(new Complex(0, -1 * Double.valueOf(tmp)));
					continue;
				}
			}
			
			if(!line.contains("i")) {
				roots.add(new Complex(Double.valueOf(line), 0.));
				continue;
			} else {
				if(line.lastIndexOf('i') == line.length()-1)
					line += "1";
				
				String[] tmpArr = line.split("i");
				if(tmpArr.length != 2)
					throw new IllegalArgumentException("Neispravan format broja.");
				if(tmpArr[0].trim().endsWith("-")) {
					tmpArr[0] = tmpArr[0].trim();
					re = Double.valueOf(tmpArr[0].substring(0, tmpArr[0].length()-1));
					im = -1. * Double.valueOf(tmpArr[1].trim());
					roots.add(new Complex(re, im));
					continue;
				} else if(tmpArr[0].trim().endsWith("+")) {
					tmpArr[0] = tmpArr[0].trim();
					re = Double.valueOf(tmpArr[0].substring(0, tmpArr[0].length()-1));
					im = Double.valueOf(tmpArr[1].trim());
					roots.add(new Complex(re, im));
					continue;
				}
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] arr = roots.toArray(new Complex[roots.size()]);
		FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE, arr)));
	}
	
	/**
	 * Razred <code>NewtonProducer</code> "stvara" sliku fraktala. Za svaku točku na ekranu računa u koliko će koraka kompleksni broj koji ta točka
	 * predstavlja početi divergirati, i na temelju tog broja ju boja određenom bojom
	 * 
	 * @author Dorian Kablar
	 *
	 */
	public static class NewtonProducer implements IFractalProducer {

		private ComplexRootedPolynomial roots;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;
		
		/**
		 * Konstruktor, stvara novi <code>NewtonProducer</code> s argumentom roots
		 * 
		 * @param roots Polinom oblika z0*(z-z1)*(z-z2)*...*(z-zn)
		 */
		public NewtonProducer(ComplexRootedPolynomial roots) {
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
			this.derived = polynomial.derive();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1.0) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					int iters = 0;
					double module = 0.;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(module > 0.001 && iters < m);
					int index = this.roots.indexOfClosestRootFor(zn, 0.002);
					data[offset] = (short) (index+1);
					offset++;
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
		
	}
}

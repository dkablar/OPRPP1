package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Razred <code>NewtonParallel</code> iscrtava prikaz fraktala temeljenog na Newton-Raphson iteraciji, i za to koristi višedretvenost
 * 
 * @author Dorian Kablar
 *
 */
public class NewtonParallel {

	public static void main(String[] args) {
	
		if(args.length > 4)
			throw new IllegalArgumentException("Neispravan broj argumenata.");
		
		System.out.println("Welcome to Newton-Raphson iteration based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int noOfThreads = -1, noOfTracks = -1;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].trim().startsWith("--workers=")) {
				if(noOfThreads != -1)
					throw new IllegalArgumentException("Neispravni argumenti.");
				noOfThreads = Integer.valueOf(args[i].substring("--workers=".length()));
			} else if(args[i].trim().startsWith("--tracks=")) {
				if(noOfTracks != -1)
					throw new IllegalArgumentException("Neispravni argumenti.");
				noOfTracks = Integer.valueOf(args[i].substring("--tracks=".length()));
			} else if(args[i].trim().equals("-w")) {
				if(noOfThreads != -1 || i+1 >= args.length)
					throw new IllegalArgumentException("Neispravni argumenti.");
				noOfThreads = Integer.valueOf(args[i+1].trim());
			} else if(args[i].trim().equals("-t")) {
				if(noOfTracks != -1 || i+1 >= args.length)
					throw new IllegalArgumentException("Neispravni argumenti.");
				noOfTracks = Integer.valueOf(args[i+1].trim());
			}
		}
		
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
		FractalViewer.show(new NewtonParallelProducer(noOfThreads, noOfTracks, new ComplexRootedPolynomial(Complex.ONE, arr)));
	}
	
	/**
	 * Razred <code>PosaoIzracuna</code> računa prikaz fraktala
	 * 
	 * @author Dorian Kablar
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial roots;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial roots) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
			this.derived = polynomial.derive();
		}
		
		@Override
		public void run() {
			int offset = yMin * width;
			for(int y = yMin; y < yMax + 1; y++) {
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
		}
	}
	
	/**
	 * Razred <code>NewtonParallelProducer</code> "stvara" sliku fraktala. Za svaku točku na ekranu računa u koliko će koraka kompleksni broj koji ta točka
	 * predstavlja početi divergirati, i na temelju tog broja ju boja određenom bojom. Taj posao delegira dretvama
	 * 
	 * @author Dorian Kablar
	 *
	 */
	public static class NewtonParallelProducer implements IFractalProducer {

		private int noOfThreads;
		private int noOfTracks;
		private ComplexRootedPolynomial roots;
		private ComplexPolynomial polynomial;
		
		public NewtonParallelProducer(int noOfThreads, int noOfTracks, ComplexRootedPolynomial roots) {
			this.noOfThreads = noOfThreads == -1 ? Runtime.getRuntime().availableProcessors() : noOfThreads;
			this.noOfTracks = noOfTracks == -1 ? 4 * Runtime.getRuntime().availableProcessors() : noOfTracks;
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
		}
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = noOfTracks > height ? height : noOfTracks;
			int brojYPoTraci = height / brojTraka;
			
			System.out.println("Number of threads: " + noOfThreads + ", number of tracks: " + brojTraka);
			System.out.println("Zapocinjem izracun");
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[noOfThreads];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, roots);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
			
	}
		
}

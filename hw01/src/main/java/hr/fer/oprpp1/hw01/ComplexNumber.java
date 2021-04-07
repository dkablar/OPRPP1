package hr.fer.oprpp1.hw01;

/**
 * Razred <code>ComplexNumber</code> predstavlja kompleksni broj i 
 * omogućava rad s kompleknim brojevima
 * 
 * @author Dorian Kablar
 *
 */
public class ComplexNumber {

	private final double real;
	private final double imaginary;
	
	/**
	 * Stvara novi <code>ComplexNumber</code> čije su komponente real i imaginary
	 * 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Metoda stvara novi kompleksni broj koji će imati samo realni dio
	 * 
	 * @param real realni broj
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Metoda stvara novi kompleksni broj koji će imati samo imaginarni dio
	 * 
	 * @param imaginary
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	
	/**
	 * Metoda stvara novi kompleksni broj na temelju elemenata trigonometrijskog prikaza
	 * 
	 * @param magnitude magnituda kompleksnog broja
	 * @param angle kut
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Metoda stvara novi kompleksni broj na temelju zadanog stringa
	 * @param s string u kojem je zapis kompleksnog broja
	 * @return kompleksni broj koji je dobiven parsiranjem stringa
	 */
	public static ComplexNumber parse(String s) {
		char[] number = s.toCharArray();
		double real = 0.0, imaginary = 0.0;
		String tmp = "";
		
		int i = 0;
		while(i < number.length) {
			while(i < number.length && number[i] != '+' && number[i] != '-') {
				if(number[i] == 'i' && i < number.length - 1) 
					throw new IllegalArgumentException("Broj je neispravan.");
				if(number[i] != 'i')
					tmp += number[i]; 
				else 
					break;
				if(i < number.length)
					i++;
				if(i == number.length && number[number.length - 1] != 'i') {
					i--;
					break;
				}
			}
			if(i < number.length) {
				if((number[i] == 'i' && i < number.length - 1))
					throw new IllegalArgumentException("Broj je neispravan.");
				if(i+1 < number.length && (number[i+1] == '+' || number[i+1] == '-'))
					throw new IllegalArgumentException("Broj je neispravan.");
			}
			if(i == 0 && (number[i] == '+' || number[i] == '-'))
				tmp += number[i];
			if(i < number.length) {
				if(number[i] == 'i') {
					if(tmp.equals("+")) {
						imaginary = 1.0;
					} else if(tmp.equals("-")) {
						imaginary = -1.0;
					} else if (i == 0) {
						imaginary = 1.0;
					} else {
						imaginary = Double.parseDouble(tmp);
					}
				} else {
					if(i != 0 || number.length == 1) {
						real = Double.parseDouble(tmp);
						tmp = new String();
						tmp += number[i];
					}
				}
			}
			if(i < number.length) i++;
		}
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Metoda vraća realni dio kompleksnog broja
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Metoda vraća imaginarni dio kompleksnog broja
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * Metoda vraća magnitudu trigonometrijskog prikaza kompleksnog broja
	 * 
	 * @return magnituda kompleksnog broja
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}
	
	/**
	 * Metoda vraća kut trigonometrijskog prikaza kompleksnog broja
	 * 
	 * @return kut kompleksnog broja
	 */
	public double getAngle() {
		return Math.atan2(this.imaginary, this.real);
	}
	
	/**
	 * Metoda zbraja dva kompleksn broja
	 * 
	 * @param c kompleksni broj koji treba zbrojiti
	 * @return zbroj
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Metoda oduzima dva kompleksna broja
	 * 
	 * @param c kompleksni broj koji treba oduzeti
	 * @return razlika
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Metoda množi dva kompleksna broja
	 * 
	 * @param c kompleksni broj s kojim treba množiti
	 * @return umnožak
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.imaginary * c.real + this.real * c.imaginary);
	}
	
	/**
	 * Metoda dijeli dva kompleksna broja
	 * 
	 * @param c kompleksni broj s kojim treba dijeliti
	 * @return količnik
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c.getReal() == 0.0 && c.getImaginary() == 0.0) throw new IllegalArgumentException();
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) /
				(Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)),
				(this.imaginary * c.real - this.real * c.imaginary) /
				(Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)));
	}
	
	/**
	 * Metoda koja računa n-tu potenciju kompleksnog broja
	 * 
	 * @param n potencija koju treba izračunati
	 * @return n-ta potencija kompleksnog broja
	 */
	public ComplexNumber power(int n) {
		if(n < 0) throw new IllegalArgumentException("Potencija mora biti >= 0.");
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		
		return new ComplexNumber(Math.pow(magnitude, n) * Math.cos(n*angle),
				Math.pow(magnitude, n) * Math.sin(n*angle));
	}
	
	/**
	 * Metoda računa n-te korijene kompleksnog broja
	 * 
	 * @param n potencija korijena koju treba računati
	 * @return polje n-tih korijena kompleksnog broja
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) throw new IllegalArgumentException();
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		ComplexNumber[] result = new ComplexNumber[n];
		
		for(int i = 0; i < n; i++) {
			result[i] = new ComplexNumber(Math.pow(magnitude, 1.0 / n) * Math.cos((angle + 2*i*Math.PI) / n)
					, Math.pow(magnitude, 1.0 / n) * Math.sin((angle + 2*i*Math.PI) / n));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.getReal() != 0.0)
			sb.append(this.getReal());
		if(this.getImaginary() != 1.0 & this.getImaginary() != -1.0) {
			if(this.getImaginary() < 0.0 && this.getReal() != 0.0)
				sb.append(this.getImaginary()).append("i");
			else if(this.getImaginary() < 0.0)
				sb.append(this.getImaginary()).append("i");
			else if(this.getImaginary() > 0.0 && this.getReal() != 0.0)
				sb.append("+").append(this.getImaginary()).append("i");
			else if(this.getImaginary() > 0.0)
				sb.append(this.getImaginary()).append("i");
		} else {
			if(this.getReal() != 0.0)
				sb.append(this.getImaginary() > 0.0 ? "+" : "-").append("i");
			else
				sb.append(this.getImaginary() > 0.0 ? "" : "-").append("i");
		}
		return sb.toString();
	}
}

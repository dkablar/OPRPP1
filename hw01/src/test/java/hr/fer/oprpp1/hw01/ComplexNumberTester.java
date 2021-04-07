package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTester {

	@Test
	public void testFromRealMethod() {
		double real = Math.random() * 10;
		ComplexNumber testNumber = ComplexNumber.fromReal(real);
		
		// broj real i realni dio testNumber-a bi trebali biti jednaki
		assertTrue(real == testNumber.getReal());
		// 0.0 i imaginarni dio testNumber-a bi trebali biti jednaki
		assertTrue(testNumber.getImaginary() == 0.0);
	}
	
	@Test
	public void testFromImaginaryMethod() {
		double imaginary = Math.random() * 10;
		ComplexNumber testNumber = ComplexNumber.fromImaginary(imaginary);
		
		// broj imaginary i imaginarni dio testNumber-a bi trebali biti jednaki
		assertTrue(imaginary == testNumber.getImaginary());
		// 0.0 i realni dio testNumber-a bi trebali biti jednaki
		assertTrue(testNumber.getReal() == 0.0);
	}
	
	@Test
	public void testFromMagnitudeAndAngleMethod() {
		double magnitude = Math.random() * 9;
		ComplexNumber testNumber = ComplexNumber.fromMagnitudeAndAngle(magnitude, Math.PI/2);
		
		// realni dio testNumbera bi trebao biti jednak broju po formuli magnitude*cos(PI/2)
		assertTrue(testNumber.getReal() == magnitude*Math.cos(Math.PI/2));
		// imaginarni dio testNumber bi trebao biti jednak broju po formuli magnitude*sin(PI/2)
		assertTrue(testNumber.getImaginary() == magnitude*Math.sin(Math.PI/2));
	}
	
	@Test
	public void testParseMethod() {
		// ovi kompleksni brojevi bi se trebali normalno stvoriti
		ComplexNumber test1 = ComplexNumber.parse("-i");
		ComplexNumber test2 = ComplexNumber.parse("1");
		ComplexNumber test3 = ComplexNumber.parse("1+i");
		ComplexNumber test4 = ComplexNumber.parse("-1-i");
		
		assertTrue(-1.0 == test1.getImaginary());
		assertTrue(1.0 == test2.getReal());
		assertTrue(1.0 == test3.getReal() && 1.0 == test3.getImaginary());
		assertTrue(-1.0 == test4.getReal() && -1.0 == test4.getImaginary());
		
		//sljedeća dva primjera metode parse bi trebala izazvati IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber test5 = ComplexNumber.parse("2+3i5i");
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber test6 = ComplexNumber.parse("2+-35i");
		});
	}
	
	@Test
	public void testGetRealMethod() {
		double real = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(real, 0.0);
		
		// real i realni dio test1 bi trebali biti jednaki
		assertTrue(test1.getReal() == real);
	}
	
	@Test
	public void testGetImaginaryMethod() {
		double imaginary = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(0.0, imaginary);
		
		// real i realni dio test1 bi trebali biti jednaki
		assertTrue(test1.getImaginary() == imaginary);
	}
	
	@Test
	public void testGetMagnitudeMethod() {
		double real = Math.random() * 10;
		double imaginary = Math.random() * 10;
		
		ComplexNumber test = new ComplexNumber(real, imaginary);
		
		// magnituda kompleksnog broja test i broj dobiven 
		// po formuli sqrt(real^2 + imaginary^2) bi trebali biti jednaki
		assertTrue(test.getMagnitude() == Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2)));
	}
	
	@Test
	public void testGetAngleMethod() {
		double real = Math.random() * 10;
		double imaginary = Math.random() * 10;
		
		ComplexNumber test = new ComplexNumber(real, imaginary);
		
		//kut kompleksnog broja i broj dobiven po formuli
		//arctan(imaginary/real) bi trebali biti jednaki
		assertTrue(test.getAngle() == Math.atan2(imaginary, real));
	}
	
	@Test
	public void testAddMethod() {
		double real1 = Math.random() * 10;
		double imaginary1 = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(real1, imaginary1);
		
		double real2 = Math.random() * 10;
		double imaginary2 = Math.random() * 10;
		ComplexNumber test2 = new ComplexNumber(real2, imaginary2);
		
		ComplexNumber result = test1.add(test2);
		
		// zbroj bi trebao biti jednak zbroju realnih i zbroju imaginarnih dijelova
		assertTrue(result.getReal() == test1.getReal() + test2.getReal());
		assertTrue(result.getImaginary() == test1.getImaginary() + test2.getImaginary());
	}
	
	@Test
	public void testSubMethod() {
		double real1 = Math.random() * 10;
		double imaginary1 = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(real1, imaginary1);
		
		double real2 = Math.random() * 10;
		double imaginary2 = Math.random() * 10;
		ComplexNumber test2 = new ComplexNumber(real2, imaginary2);
		
		ComplexNumber result = test1.sub(test2);
		
		// razlika bi trebala biti jednaka razlici realnih i razlici imaginarnih dijelova
		assertTrue(result.getReal() == test1.getReal() - test2.getReal());
		assertTrue(result.getImaginary() == test1.getImaginary() - test2.getImaginary());
	}
	
	@Test
	public void testMulMethod() {
		double real1 = Math.random() * 10;
		double imaginary1 = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(real1, imaginary1);
		
		double real2 = Math.random() * 10;
		double imaginary2 = Math.random() * 10;
		ComplexNumber test2 = new ComplexNumber(real2, imaginary2);
		
		ComplexNumber result = test1.mul(test2);
		
		// umnožak kompleksnih brojeva bi trebao biti jednak rezultatima po formulama
		// real = real1*real2 - imaginary1*imaginary2
		// imaginary = real1*imaginary2 + imaginary1*real2
		assertTrue(result.getReal() == test1.getReal()*test2.getReal() - test1.getImaginary()*test2.getImaginary());
		assertTrue(result.getImaginary() == test1.getReal()*test2.getImaginary() + test1.getImaginary()*test2.getReal());
	}
	
	@Test
	public void testDivMethod() {
		double real1 = Math.random() * 10;
		double imaginary1 = Math.random() * 10;
		ComplexNumber test1 = new ComplexNumber(real1, imaginary1);
		
		double real2 = Math.random() * 10;
		double imaginary2 = Math.random() * 10;
		ComplexNumber test2 = new ComplexNumber(real2, imaginary2);
		
		ComplexNumber result = test1.div(test2);
		
		// količnik kompleksnih brojeva bi trebao biti jednak rezultatima po formulama
		// real = (real1*real2 + imaginary1*imaginary2)/(real2^2 + imaginary2^2)
		// imaginary = (imaginary1*real2 - real1*imaginary2)/(real2^2 + imaginary2^2)
		assertTrue(result.getReal() == (real1*real2 + imaginary1*imaginary2) /
				(Math.pow(real2, 2) + Math.pow(imaginary2, 2)));
		assertTrue(result.getImaginary() == (imaginary1*real2 - real1*imaginary2) /
				(Math.pow(real2, 2) + Math.pow(imaginary2, 2)));
	}
	
	@Test
	public void testPowerMethod() {
		double real = Math.random() * 10;
		double imaginary = Math.random() * 10;
		ComplexNumber test = new ComplexNumber(real, imaginary);
		
		int n = (int) Math.random() * 10;
		double magnitude = test.getMagnitude();
		double angle = test.getAngle();
		
		ComplexNumber result = test.power(n);
		
		// rezultat potenciranja kompleksnog broja na n-tu potenciji će biti jednako po formuli
		// real = magnitude^n * cos(n*angle)
		// imaginary = magnitude^n * sin(n*angle)
		assertTrue(result.getReal() == Math.pow(magnitude, n) * Math.cos(n*angle));
		assertTrue(result.getImaginary() == Math.pow(magnitude, n) * Math.sin(n*angle));
		
		// ako je zadan n < 0, metoda izaziva IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber res = test.power(-1);
		});
	}
	
	@Test
	public void testRootMethod() {
		double real = Math.random() * 10;
		double imaginary = Math.random() * 10;
		ComplexNumber test = new ComplexNumber(real, imaginary);
		
		int n = (int) Math.random() * 10 + 1;
		double magnitude = test.getMagnitude();
		double angle = test.getAngle();
		
		ComplexNumber[] result = test.root(n);
		
		for(int i = 0; i < result.length; i++) {
			// rezultat n-tog korijenovanja kompleksnog broja bi trebao biti po formulama
			// real = magnitude^(1/n) * (cos((angle + 2*PI*k) / n))
			// imaginary = magnitude^(1/n) * (sin((angle + 2*PI*k) / n))
			// k = 0,1,...,(n-1)
			assertTrue(result[i].getReal() == Math.pow(magnitude, 1/n) * Math.cos((angle + 2*Math.PI*i) / n));
			assertTrue(result[i].getImaginary() == Math.pow(magnitude, 1/n) * Math.sin((angle + 2*Math.PI*i) / n));
		}
		
		// ako je zadan n <= 0, metoda izaziva IllegalArgumenException
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber[] res = test.root(0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber[] res = test.root(-5);
		});
	}
	
	@Test
	public void testToStringMethod() {
		// ovako sam pisao da izbjegnem broj 1 u imaginarnom dijelu, to ću posebno testirati
		double real = Math.random() * 10 + 2;
		double imaginary = Math.random() * 10 + 2;
		ComplexNumber test1 = new ComplexNumber(real, imaginary);
		String result = real + "+" + imaginary + "i";
		assertTrue(test1.toString().equals(result));
		
		// slučaj s 1 kao imaginarna vrijednost
		ComplexNumber test2 = new ComplexNumber(1.0, 1.0);
		assertTrue(test2.toString().equals("1.0+i"));
		
		// slučaj s -1 kao imaginarna vrijednost
		ComplexNumber test3 = new ComplexNumber(-1.0, -1.0);
		assertTrue(test3.toString().equals("-1.0-i"));
		
		// slučaj sa samo realnim dijelom
		ComplexNumber test4 = new ComplexNumber(real, 0.0);
		String result2 = real + "";
		assertTrue(test4.toString().equals(result2));
		
		// slučaj sa samo imaginarnim dijelom
		ComplexNumber test5 = new ComplexNumber(0.0, imaginary);
		String result3 = imaginary + "i";
		assertTrue(test5.toString().equals(result3));
	}
}

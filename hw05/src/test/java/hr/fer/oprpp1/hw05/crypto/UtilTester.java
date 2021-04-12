package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UtilTester {

	@Test
	public void testHextobyteMethod() {
		byte[] arr = Util.hextobyte("01aE22");
		
		assertTrue(arr.length == 3);
		assertTrue(arr[0] == 1);
		assertTrue(arr[1] == -82);
		assertTrue(arr[2] == 34);
		
		byte[] arr2 = Util.hextobyte("199C45bB");
		assertTrue(arr2.length == 4);
		assertTrue(arr2[0] == 25);
		assertTrue(arr2[1] == -100);
		assertTrue(arr2[2] == 69);
		assertTrue(arr2[3] == -69);
		
		byte[] arr3 = Util.hextobyte("80");
		assertTrue(arr3.length == 1);
		assertTrue(arr3[0] == -128);
	}
	
	@Test
	public void testHextobyteMethodShouldThrow() {
		// duljina niza je neparna
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("12345");
		});
		
		// S nije heksadekadski znak
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("1S3454");
		});
	}
	
	@Test
	public void testBytetohexMethod() {
		assertTrue(Util.bytetohex(new byte[] {1, -82, 34}).equals("01ae22"));
		assertTrue(Util.bytetohex(new byte[] {2, -83, 35}).equals("02ad23"));
		assertTrue(Util.bytetohex(new byte[] {15, -81, 40}).equals("0faf28"));
		assertTrue(Util.bytetohex(new byte[] {25, -100, 69, -69}).equals("199c45bb"));
		assertTrue(Util.bytetohex(new byte[] {-128}).equals("80"));
	}
}

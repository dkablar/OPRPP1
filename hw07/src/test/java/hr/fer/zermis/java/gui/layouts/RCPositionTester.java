package hr.fer.zermis.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.text.Position;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class RCPositionTester {

	@Test
	public void widthExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(6, 4);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(0, 4);
		});
	}
	
	@Test
	public void heightExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(3, 8);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(3, 0);
		});
	}
	
	@Test
	public void heightAndWidthExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(1, 4);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = RCPosition.parse("1, 4");
		});
	}
}

package hr.fer.zermis.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class LayoutImplTester {

	@Test
	public void DimensionTester1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertTrue(dim.height == 158);
		assertTrue(dim.width == 152);
	}
	
	@Test
	public void DimensionTester2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertTrue(dim.height == 158);
		assertTrue(dim.width == 152);
	}
	
	@Test
	public void sameComponentTwiceTesterShouldThrow() {
		assertThrows(CalcLayoutException.class, () -> {
			JPanel p = new JPanel(new CalcLayout(2));
			JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
			JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
			p.add(l1, new RCPosition(1,1));
			p.add(l2, new RCPosition(3,3));
			p.add(new JLabel(""), new RCPosition(3,3));
		});
	}
}

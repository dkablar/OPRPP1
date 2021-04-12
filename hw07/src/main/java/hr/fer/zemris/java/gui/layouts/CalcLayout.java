package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred <code>CalcLayout</code> modelira razmještaj komponenata na kalkulatoru
 * 
 * @author Dorian Kablar
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	private int margin;
	private Map<Component, RCPosition> constraintMap;
	
	/**
	 * Konstruktor, stvara novi <code>CalcLayout</code> bez razmaka između komponenti
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Konstruktor, stvara novi <code>CalcLayout</code>
	 * 
	 * @param margin razmak između komponenti
	 */
	public CalcLayout(int margin) {
		super();
		this.margin = margin;
		this.constraintMap = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.constraintMap.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, 2);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, 1);
	}

	@Override
	public void layoutContainer(Container parent) {
		int noOfComponents = parent.getComponentCount();
		Insets insets = parent.getInsets();
		
		Dimension dimension = parent.getSize();
		double totalWidth = dimension.getWidth() - insets.left - insets.right;
		double totalHeight = dimension.getHeight() - insets.top - insets.bottom;
		
		double totalCellWidth = (totalWidth - 6*this.margin) / 7.;
		double totalCellHeight = (totalHeight - 4*this.margin) / 5.;
		
		for(int i = 0; i < noOfComponents; i++) {
			Component component = parent.getComponent(i);
			RCPosition position = this.constraintMap.get(component);
			
			if(position != null) {
				if(position.getColumn() == 1 && position.getRow() == 1) {
					double x = insets.left;
					double y = insets.top;
					double w = totalCellWidth*5 + 4*this.margin;
					double h = totalCellHeight;
					component.setBounds((int) x, (int) y, (int) w, (int) h);
				} else {
					double x = insets.left + (position.getColumn()-1.)*(totalCellWidth + this.margin);
					double y = insets.top + (position.getRow()-1.)*(totalCellHeight + this.margin);
					double w = totalCellWidth;
					double h = totalCellHeight;
					component.setBounds((int) x, (int) y, (int) w, (int) h);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null || comp == null)
			throw new NullPointerException("Arguments should not be null.");
		if(!(constraints instanceof RCPosition || constraints instanceof String))
			throw new IllegalArgumentException("Invalid argument type. String or RCPosition allowed.");
		
		RCPosition position;
		
		if(constraints instanceof String)
			position = RCPosition.parse((String) constraints);
		else
			position = (RCPosition) constraints;
		
		for(RCPosition p : this.constraintMap.values()) {
			if(p.getRow() == position.getRow() && p.getColumn() == position.getColumn())
				throw new CalcLayoutException("The component with given restriction already exists.");
		}
		
		this.constraintMap.put(comp, position);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, 3);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	private Dimension getLayoutSize(Container parent, int type) {
		Dimension largestCellDimension = getCellSize(parent, type);
		Insets insets = parent.getInsets();
		
		largestCellDimension.width = 7*largestCellDimension.width + 6*this.margin + insets.left + insets.right;
		largestCellDimension.height = 5*largestCellDimension.height + 4*this.margin + insets.top + insets.bottom;
		
		return largestCellDimension;
	}
	
	private Dimension getCellSize(Container parent, int type) {
		int noOfComponents = parent.getComponentCount();
		Dimension dimension = new Dimension(0, 0);
		
		for(int i = 0; i < noOfComponents; i++) {
			Component component = parent.getComponent(i);
			RCPosition position = this.constraintMap.get(component);
			
			if(component != null && position != null) {
				Dimension size;
				switch(type) {
				case 1 ->  {
					size = component.getMinimumSize();
					if(position.getRow() == 1 && position.getColumn() == 1) {
						dimension.width = Math.max(dimension.width, (size.width - 4*this.margin) / 5);
						dimension.height = Math.max(dimension.height, size.height);
					} else {
						dimension.width = Math.max(dimension.width, size.width);
						dimension.height = Math.max(dimension.height, size.height);
					}
				}
				case 2 -> {
					size = component.getPreferredSize();
					if(position.getRow() == 1 && position.getColumn() == 1) {
						dimension.width = Math.max(dimension.width, (size.width - 4*this.margin) / 5);
						dimension.height = Math.max(dimension.height, size.height);
					} else {
						dimension.width = Math.max(dimension.width, size.width);
						dimension.height = Math.max(dimension.height, size.height);
					}
				}
				case 3 -> {
					size = component.getMaximumSize();
					if(position.getRow() == 1 && position.getColumn() == 1) {
						dimension.width = Math.max(dimension.width, (size.width - 4*this.margin) / 5);
						dimension.height = Math.max(dimension.height, size.height);
					} else {
						dimension.width = Math.max(dimension.width, size.width);
						dimension.height = Math.max(dimension.height, size.height);
					}
				}
				}
			}
		}
		
		return dimension;
	}
}

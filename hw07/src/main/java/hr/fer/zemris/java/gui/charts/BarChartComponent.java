package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Razred <code>BarChartComponent</code> predstavlja komponentu koja prikazuje histogram
 * 
 * @author Dorian Kablar
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BarChart barChart;
	
	/**
	 * Konstruktor, stvara novi <code>BarChartComponent</code>
	 * 
	 * @param barChart podaci za histogram
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		int size = this.barChart.getYmax() - this.barChart.getYmin();
		
		FontMetrics fm = g.getFontMetrics();
		int xw = fm.stringWidth(barChart.getxDescription());
		int xh = fm.getAscent();
		g.drawString(barChart.getxDescription(), ins.left + (dim.width - xw)/2, dim.height - xh);
		
		g.drawLine(ins.left+3*fm.getAscent(),
        		dim.height - 3*fm.getAscent(),
        		ins.left+3*fm.getAscent(),
        		dim.height - 4*fm.getAscent() - size*(this.barChart.getYmax()-this.barChart.getYmin()));
        g.drawLine(ins.left+ 3*fm.getAscent(),
        		dim.height - 3*fm.getAscent(),
        		dim.width- fm.getDescent(),
        		dim.height - 3*fm.getAscent());
        
        int i = 0;
        for(XYValue v : this.barChart.getList()) {
        	g.draw3DRect(ins.left+ 3*fm.getAscent() + (dim.width-4*fm.getAscent()) / this.barChart.getList().size()*i,
        			dim.height - 3*fm.getAscent() - v.getY()*(this.barChart.getYmax()-this.barChart.getYmin()),
        			(dim.width-4*fm.getAscent()) / this.barChart.getList().size(),
        			v.getY()*(this.barChart.getYmax()-this.barChart.getYmin()), true);
        	if(isOpaque()) {
        		g.setColor(getBackground());
        		g.fill3DRect(ins.left+ 3*fm.getAscent() + (dim.width-4*fm.getAscent()) / this.barChart.getList().size()*i,
        			dim.height - 3*fm.getAscent() - v.getY()*(this.barChart.getYmax()-this.barChart.getYmin()),
        			(dim.width-4*fm.getAscent()) / this.barChart.getList().size(),
        			v.getY()*(this.barChart.getYmax()-this.barChart.getYmin()), true);
        	}

        	g.setColor(Color.BLACK);
        	g.drawString(String.valueOf(this.barChart.getList().get(i).getX()),
        			(ins.left + fm.getAscent() + (dim.width / this.barChart.getList().size()*(i+1)) - dim.width/this.barChart.getList().size()/2),
        			dim.height - 2*fm.getAscent());
        	i++;
        }
        
        int offset = this.barChart.getOffset();
        for(int j = this.barChart.getYmin(); j <= this.barChart.getYmax(); j+=offset) {
        	g.drawString(String.valueOf(j), (int)(ins.left+1.5*fm.getAscent()), dim.height - 12 - (j+1)*(this.barChart.getYmax()-this.barChart.getYmin()));
        }
        
		Graphics2D g2d = (Graphics2D) g;
         
        // rotates the coordinate by 90 degree counterclockwise
        AffineTransform at = new AffineTransform();
        at.rotate(- Math.PI / 2);
   
        g2d.setTransform(at);
        int yw = fm.stringWidth(barChart.getyDescription());
		int yh = fm.getAscent();
        g2d.drawString(barChart.getyDescription(), -((dim.height+yw)/2), ins.bottom + yh);
	}
}
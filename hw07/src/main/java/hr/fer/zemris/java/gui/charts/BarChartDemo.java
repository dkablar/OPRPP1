package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred <code>BarChartDemo</code> predstavlja prikaz histograma
 * 
 * @author Dorian Kablar
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BarChart chart;
	private String text;

	/**
	 * Konstruktor, stvara novi <code>BarChartDemo</code>
	 * 
	 * @param chart podaci bitni za histogram
	 * @param text put datoteke iz koje dolaze podatci
	 */
	public BarChartDemo(BarChart chart, String text) {
		super();
		this.chart = chart;
		this.text = text;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500,200);
		setTitle("BarChart");
		initGUI();
	}
	
	/**
	 * Metoda koja inicijalizira GUI
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		BarChartComponent barChart = new BarChartComponent(this.chart);
		barChart.setOpaque(true);
		barChart.setBackground(Color.ORANGE);
		
		getContentPane().add(new JLabel(text), BorderLayout.PAGE_START);
		getContentPane().add(barChart, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		
		File f = new File(args[0]);
		List<XYValue> list = new ArrayList<>();
		BarChart comp;
		
		try {
			Scanner sc = new Scanner(f);
			String xDescription = sc.nextLine().trim();
			String yDescription = sc.nextLine().trim();
			String data = sc.nextLine().trim();
			String ymin = sc.nextLine().trim();
			String ymax = sc.nextLine().trim();
			String offset = sc.nextLine().trim();
			if(sc.hasNext())
				if(sc.hasNext())
					throw new IllegalArgumentException("File has too many rows.");
			
			String[] arr = data.split(" ");
			for(String s : arr)
				if(s.length() > 0)
					list.add(new XYValue(Integer.valueOf(s.substring(0, s.indexOf(","))), 
							Integer.valueOf(s.substring(s.indexOf(",")+1)))); 
							
			comp = new BarChart(list, xDescription, yDescription, Integer.valueOf(ymin), Integer.valueOf(ymax), Integer.valueOf(offset));
			if(sc.hasNext())
				throw new IllegalArgumentException("File has too many rows.");
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					BarChartDemo barChartDemo = new BarChartDemo(comp, args[0]);
					barChartDemo.setVisible(true);
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

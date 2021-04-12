package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Razred <code>BarChart</code> predstavlja histogram, tj. sve podatke potrebne da bi se histogram mogao nacrtati
 * 
 * @author Dorian Kablar
 *
 */
public class BarChart {

	private List<XYValue> list;
	private String xDescription;
	private String yDescription;
	private int ymin;
	private int ymax;
	private int offset;
	
	/**
	 * Konstruktor, stvara novi <code>BarChart</code>
	 * 
	 * @param list lista podataka koji se prikazuju na histogramu
	 * @param xDescription opis x osi histograma
	 * @param yDescription opis y osi histograma
	 * @param ymin minimalna y vrijednost
	 * @param ymax maksimalna y vrijednost
	 * @param offset razlika između dvije y vrijednosti
	 */
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int ymin, int ymax, int offset) {
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.ymin = ymin;
		this.ymax = ymax;
		this.offset = offset;
		
		if((this.ymax - this.ymin) % this.offset != 0)
			while((this.ymax - this.ymin) % this.offset != 0)
				this.offset++;
		
		for(XYValue value : this.list)
			if(this.ymin > value.getY())
				throw new IllegalArgumentException("There are y values smaller than ymin in the list.");
	}

	/**
	 * Getter, vraća listu podataka
	 * 
	 * @return lista podataka
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Getter, vraća opis osi x
	 * 
	 * @return opis osi x
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter, vraća opis osi y
	 * 
	 * @return opis osi y
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter, vraća minimalnu y vrijednost
	 * 
	 * @return minimalna y vrijednost
	 */
	public int getYmin() {
		return ymin;
	}

	/**
	 * Getter, vraća maksmimalnu y vrijednost
	 * 
	 * @return maksimalna y vrijednost
	 */
	public int getYmax() {
		return ymax;
	}

	/**
	 * Getter, vraća razliku između dva susjedna y
	 * 
	 * @return razlika između dva susjedna y
	 */
	public int getOffset() {
		return offset;
	}
}

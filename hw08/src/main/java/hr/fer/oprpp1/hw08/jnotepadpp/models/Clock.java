package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Razred <code>Clock</code> predstavlja grafički prikaz trenutnog vremena
 * 
 * @author Dorian Kablar
 *
 */
public class Clock extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	volatile String time;
	volatile boolean toStop = false;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * Konstruktor, stvara novi <code>Clock</code>
	 */
	public Clock() {
		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		updateTime();
		
		Thread t = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(500);
				} catch(Exception ex) {}
				if(toStop) break;
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Pomoćna metoda koja ažurira grafički prikaz vremena
	 */
	private void updateTime() {
		time = formatter.format(new Date(System.currentTimeMillis()));
		this.setText(time);
	}
	
	/**
	 * Metoda koja zaustavlja dretvu sata
	 */
	public void stop() {
		toStop = true;
	}
}

package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred <code>PrimDemo</code> predstavlja prikaz liste prostih brojeva
 * 
 * @author Dorian Kablar
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor, stvara novi <code>PrimDemo</code>
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Moj prvi prozor!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Metoda koja inicijalizira GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel primList = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(primList);
		JList<Integer> list2 = new JList<>(primList);
		
		
		JButton nextButton = new JButton("Sljedeći");
		nextButton.addActionListener(e -> primList.next());
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(nextButton, BorderLayout.PAGE_END);
		
	}
	
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			PrimDemo frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}

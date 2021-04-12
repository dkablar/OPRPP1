package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sučelje <code>SingleDocumentModel</code> predstavlja podršku za rad s jednim dokumentom
 * 
 * @author Dorian Kablar
 *
 */
public interface SingleDocumentModel {

	/**
	 * Metoda koja vraća element u kojem se nalazi sadržaj dokumenta
	 * 
	 * @return grafički element s sadržajem dokumenta
	 */
	JTextArea getTextComponent();
	
	/**
	 * Metoda koja vraća mjesto u datotečnom sustavu na kojem se nalazi dokument
	 * 
	 * @return mjesto u datotečnom sustavu
	 */
	Path getFilePath();
	
	/**
	 * Metoda koja ažurira mjesto u datotečnom sustavu za zadani dokument
	 * 
	 * @param path novo mjesto
	 */
	void setFilePath(Path path);
	
	/**
	 * Metoda koja provjerava status datoteke, tj. modificiranost
	 * 
	 * @return status datoteke
	 */
	boolean isModified();
	
	/**
	 * Metoda koja mijenja modificiranost datoteke
	 * 
	 * @param modified status datoteke
	 */
	void setModified(boolean modified);
	
	/**
	 * Metoda koja dodaje promatrača na dokument u listu
	 * 
	 * @param l promatrač
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Metoda koja iz liste miče zadanog promatrača na dokument
	 * 
	 * @param l promatrač
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}

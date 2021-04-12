package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

/**
 * Sučelje <code>MultipleDocumentModel</code> predstavlja podršku za rad s više dokumenata
 * 
 * @author Dorian Kablar
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Metoda koja stvara novi dokument
	 * 
	 * @return novi dokument
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Metoda koja vraća trenutni dokument
	 * 
	 * @return trenutni dokument
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Metoda koja dohvaća dokument iz zadanog mjesta u datotečnom sustavu
	 * 
	 * @param path mjesto u datotečnom sustavu
	 * @return dohvaćeni dokument
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Metoda koja sprema dokument na zadano mjesto u datotečnom sustavu
	 * 
	 * @param model dokument koji treba spremiti
	 * @param newPath mjesto u datotečnom sustavu na kojeg se treba spremiti
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Metoda koja zatvara zadani dokument
	 * 
	 * @param model dokument koji treba zatvoriti
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Metoda koja dodaje novi promatrač na sustav dokumenata
	 * 
	 * @param l promatrač
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Metoda koja briše zadani promatrač na sustav dokumenata
	 * 
	 * @param l promatrač
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Metoda koja dohvaća broj dokumenta koji su trenutno otvoreni
	 * 
	 * @return broj dokumenata
	 */
	int getNumberOfDocuments();
	
	/**
	 * Metoda koja dohvaća dokument sa zadanog indexa iz liste
	 * 
	 * @param index index
	 * @return dokument sa zadanog mjesta u listi
	 */
	SingleDocumentModel getDocument(int index);
}

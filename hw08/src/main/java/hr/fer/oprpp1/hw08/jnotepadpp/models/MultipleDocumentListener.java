package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * Sučelje <code>MultipleDocumentListener</code> predstavlja promatrača na rad s više dokumenata
 * 
 * @author Dorian Kablar
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Metoda koja ažurira sve potrebne elemente prilikom promjene trenutnog dokumenta
	 * 
	 * @param previousModel prethodni "trenutni" element
	 * @param currentModel novi "trenutni" element
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Metoda koja ažurira sve potrebne elemente prilikom dodavanja dokumenta
	 * 
	 * @param model dokument koji će se dodati
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Metoda koja ažurira sve potrebne elemente prilikom micanja dokumenta
	 * 
	 * @param model dokument koji se mora maknuti
	 */
	void documentRemoved(SingleDocumentModel model);
}

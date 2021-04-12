package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * Sučelje <code>SingleDocumentListener</code> predstavlja promatrača na rad s jednim dokumentom
 * 
 * @author Dorian Kablar
 *
 */
public interface SingleDocumentListener {

	/**
	 * Metoda koja ažurira sve potrebne elemente nakon promjene u dokumentu
	 * 
	 * @param model dokument nad kojim se vrše promjene
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Metoda koja ažurira sve potrebne elemente nakon promjene mjesta u datotečnom sustavu
	 * 
	 * @param model dokument nad kojim se vrše promjene
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}

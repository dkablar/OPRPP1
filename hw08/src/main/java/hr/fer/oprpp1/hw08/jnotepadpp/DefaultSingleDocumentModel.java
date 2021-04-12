package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Razred <code>DefaultSingleDocumentModel</code> predstavlja implementaciju sučelja </code>SingleDocumentModel</code>,
 * to je implementacija podrške za rad s jednim dokumentom
 * 
 * @author Dorian Kablar
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private boolean modified;
	private JTextArea editor;
	private Path path;
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Konstruktor, stvara novi <code>DefaultSingleDocumentModel</code>
	 * 
	 * @param textContent sadržaj dokumenta
	 * @param path mjesto dokumenta u datotečnom sustavu
	 */
	public DefaultSingleDocumentModel(String textContent, Path path) {
		this.editor = new JTextArea(textContent);
		this.path = path;
		this.listeners = new ArrayList<>();
		this.modified = false;
		
		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				DefaultSingleDocumentModel.this.setModified(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				DefaultSingleDocumentModel.this.setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				DefaultSingleDocumentModel.this.setModified(true);		
			}
			
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return this.editor;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		
		for(SingleDocumentListener l : listeners)
			l.documentFilePathUpdated(this);
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for(SingleDocumentListener l : listeners)
			l.documentModifyStatusUpdated(this);
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}

}

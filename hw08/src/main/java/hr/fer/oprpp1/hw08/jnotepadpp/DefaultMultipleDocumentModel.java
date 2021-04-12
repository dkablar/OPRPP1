package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Razred <code>DefaultMultipleDocumentModel</code> predstavlja implementaciju sučelja </code>MultipleDocumentModel</code>, 
 * to je implementacija rada s više dokumenata
 * 
 * @author Dorian Kablar
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MultipleDocumentListener> listeners;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	
	/**
	 * Konstruktor, stvara novi <code>DefaultMultipleDocumentModel</code>
	 */
	public DefaultMultipleDocumentModel() {
		this.listeners = new ArrayList<>();
		this.documents = new ArrayList<>();
		this.current = null;
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int index = pane.getSelectedIndex();
				if(index == -1)
					return;
				for(MultipleDocumentListener l : DefaultMultipleDocumentModel.this.listeners) {
					l.currentDocumentChanged(current, DefaultMultipleDocumentModel.this.getDocument(index));
				}
				
				DefaultMultipleDocumentModel.this.current = DefaultMultipleDocumentModel.this.getDocument(index);
			}
			
		});
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel("", null);
		//model.addSingleDocumentListener(sdl);
		
		this.documents.add(model);
		
		for(MultipleDocumentListener l : this.listeners) {
			l.documentAdded(model);
		}
		
		this.current = getDocument(this.documents.size()-1);
		this.current.setModified(false);
		
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		int index = 0;
		for(SingleDocumentModel m : this.documents) {
			if(m.getFilePath() == null) {
				index++;
				continue;
			}
			if(m.getFilePath().equals(path)) {
				for(MultipleDocumentListener l : DefaultMultipleDocumentModel.this.listeners) {
					l.currentDocumentChanged(current, m);
				}
				this.current = m;
				this.setSelectedIndex(index);
				return m;
			}
			index++;
		}
		
		SingleDocumentModel model;
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
			String text = new String(bytes, StandardCharsets.UTF_8);
			model = new DefaultSingleDocumentModel(text, path);
		} catch(Exception ex) {
			return null;
		}
		
		this.documents.add(model);
		
		for(MultipleDocumentListener l : this.listeners) {
			l.documentAdded(model);
		}
		
		this.current = this.getDocument(this.getNumberOfDocuments()-1);
		this.current.setModified(false);
		
		return model;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath==null) {
			newPath = model.getFilePath();
		}
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, podatci);
			model.setFilePath(newPath);
			
			for(MultipleDocumentListener l : listeners) {
				l.currentDocumentChanged(getCurrentDocument(), model);
			}
			
			this.current = model;
			this.current.setModified(false);
		} catch (IOException e1) {
			System.out.println("Greška pri snimanju.");
			return;
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		this.documents.remove(model);
		this.removeTabAt(this.getSelectedIndex());
		this.current = this.documents.size() == 0 ? null : this.getDocument(this.getNumberOfDocuments()-1);
		for(MultipleDocumentListener l : this.listeners) {
			l.documentRemoved(model);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<>() {

			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < DefaultMultipleDocumentModel.this.getNumberOfDocuments();
			}

			@Override
			public SingleDocumentModel next() {
				return DefaultMultipleDocumentModel.this.getDocument(index++);
			}
			
			@Override
			public void remove() {
				DefaultMultipleDocumentModel.this.closeDocument(DefaultMultipleDocumentModel.this.getDocument(--index));
			}
		};
	}

}

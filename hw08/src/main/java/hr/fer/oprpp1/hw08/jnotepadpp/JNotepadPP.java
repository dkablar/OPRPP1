package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.Clock;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Razred <code>JNotepadPP</code> predstavlja uređivač teksta
 * 
 * @author Dorian Kablar
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private DefaultMultipleDocumentModel model;
	private String copyPaste;
	private JPanel statusBar;
	private Clock clock;
	
	/**
	 * Konstruktor, stvara instancu <code>JNotepadPP</code>
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setSize(600,600);
		
		initGUI();
		
		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closing();
			}
		};
		
		this.addWindowListener(wl);
	}
	
	/**
	 * Pomoćna metoda, inicijalizira elemente koji čine <code>JNotepadPP</code>, stvara grafičko korisničko sučelje
	 */
	private void initGUI() {
		
		model = new DefaultMultipleDocumentModel();
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(previousModel == null && currentModel == null) {
					JOptionPane.showMessageDialog(JNotepadPP.this,
							"Error while changing the current document",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(previousModel != null) {
					previousModel.removeSingleDocumentListener(sdl);
					previousModel.getTextComponent().removeCaretListener(cl);
				}
				if(currentModel != null) {
					currentModel.addSingleDocumentListener(sdl);
					currentModel.getTextComponent().addCaretListener(cl);
				}
				
				String text = currentModel.getFilePath() == null 
						? "(unnnamed) - " 
								: (currentModel.getFilePath().toString() + " - ");
				JNotepadPP.this.setTitle(text + "JNotepad++");
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				boolean isNull = model.getFilePath() == null;
				String text = isNull ? "(unnnamed) - " : (model.getFilePath().toString() + " - ");
				JNotepadPP.this.setTitle(text + "JNotepad++");
				JNotepadPP.this.model.addTab(!isNull ? model.getFilePath().getFileName().toString() : "(unnamed)",
						model.getTextComponent());
				JNotepadPP.this.model.setSelectedIndex(JNotepadPP.this.model.getNumberOfDocuments()-1);
				JNotepadPP.this.model.setToolTipTextAt(JNotepadPP.this.model.getSelectedIndex(),
						!isNull ? model.getFilePath().toString() :"(unnamed)");
				model.addSingleDocumentListener(sdl);
				model.getTextComponent().addCaretListener(cl);
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.removeSingleDocumentListener(sdl);
				model.getTextComponent().removeCaretListener(cl);
				int index = JNotepadPP.this.model.getSelectedIndex();
				if(index == -1) {
					JNotepadPP.this.setTitle("JNotepad++");
					return;
				}
				String text = JNotepadPP.this.model.getDocument(index).getFilePath() == null 
						? "(unnnamed) - " 
								: (JNotepadPP.this.model.getDocument(index).getFilePath().toString() + " - ");
				JNotepadPP.this.setTitle(text + "JNotepad++");
			}
			
		});
		
		this.statusBar = new JPanel();
		this.statusBar.setLayout(new GridLayout(0,3));
		this.statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		
		JLabel length = new JLabel("Length:0");
		length.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.statusBar.add(length);
		
		JLabel lnColSel = new JLabel("Ln:0 Col:0 Sel:0");
		lnColSel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.statusBar.add(lnColSel);
		
		this.clock = new Clock();
		this.statusBar.add(clock);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(model), BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
	}
	
	/**
	 * Promatrač promjene pozicije u datoteci, kod svake promjene ažurira potrebne elemente sučelja
	 */
	private CaretListener cl = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextComponent tc = (JTextComponent) e.getSource();
			int pos = tc.getCaretPosition();
			Document doc = tc.getDocument();
			Element root = doc.getDefaultRootElement();
			int row = root.getElementIndex(pos);
			int col = pos - root.getElement(row).getStartOffset();
			int selection = Math.abs(e.getDot() - e.getMark());
			
			if(selection == 0) {
				JNotepadPP.this.toggleCaseAction.setEnabled(false);
				JNotepadPP.this.toUpperCaseAction.setEnabled(false);
				JNotepadPP.this.toLowerCaseAction.setEnabled(false);
			} else {
				JNotepadPP.this.toggleCaseAction.setEnabled(true);
				JNotepadPP.this.toUpperCaseAction.setEnabled(true);
				JNotepadPP.this.toLowerCaseAction.setEnabled(true);
			}
			
			JLabel len = (JLabel) JNotepadPP.this.statusBar.getComponent(0);
			len.setText("Length:" + doc.getLength());
			
			JLabel lcs = (JLabel) JNotepadPP.this.statusBar.getComponent(1);
			lcs.setText("Ln:" + (row+1) + " Col:" + (col+1) + " Sel:" + selection);
		}
	};
	
	/**
	 * Promatrač promjena u datoteci. Prilikom promjene ažurira potrebne elemente sučelja
	 */
	private SingleDocumentListener sdl = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			InputStream is;
			if(model.isModified()) {
				is = this.getClass().getResourceAsStream("icons/redCircle.png");
			} else {
				is = this.getClass().getResourceAsStream("icons/greenCircle.png");
			}
			
			if(is == null) {
				System.out.println("Image not loaded.");
			}
			
			try {
				byte[] bytes = is.readAllBytes();
				is.close();
				
				ImageIcon image = new ImageIcon(bytes);
				Image img = image.getImage(); // transform it 
				Image newimg = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH); // scale it the smooth way  
				
				JNotepadPP.this.model.setIconAt(JNotepadPP.this.model.getSelectedIndex(),
						new ImageIcon(newimg));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			JNotepadPP.this.model.setTitleAt(JNotepadPP.this.model.getSelectedIndex(),
					model.getFilePath().getFileName().toString());
			JNotepadPP.this.model.setToolTipTextAt(JNotepadPP.this.model.getSelectedIndex(),
					model.getFilePath().toString());
		}
		
	};
	
	/**
	 * Akcija otvaranja dokumenta iz datotečnog sustava
	 */
	private LocalizableAction openDocumentAction = new LocalizableAction("open", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("open"));
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION)
				return;
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			SingleDocumentModel current = model.loadDocument(filePath);
			if(current == null) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("errorLoading") + " " + fileName.getAbsolutePath() + ".",
						flp.getString("error") + "!",
						JOptionPane.ERROR_MESSAGE);
			};
		}
		
	};
	
	/*
	 * Akcija stvaranja novog dokumenta
	 */
	private LocalizableAction newDocumentAction = new LocalizableAction("new", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
		
	};
	
	/**
	 * Akcija spremanja dokumenta na unaprijed poznato mjesto u datotečnom sustavu
	 */
	private LocalizableAction saveAction = new LocalizableAction("save", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getNumberOfDocuments() == 0)
				return;
			SingleDocumentModel m = model.getCurrentDocument();
			if(m.getFilePath() == null) {
				saveAsAction.actionPerformed(e);
			} else {
				model.saveDocument(m, m.getFilePath());
			}
		}
		
	};
	
	/**
	 * Akcija spremanja dokumenta na željeno mjesto u datotečnom sustavu
	 */
	private LocalizableAction saveAsAction = new LocalizableAction("saveAs", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getNumberOfDocuments() == 0)
				return;
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveAs"));
			if(fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("document") + " " + 
						(JNotepadPP.this.model.getCurrentDocument().getFilePath() == null ? "(unnamed)" :
							JNotepadPP.this.model.getCurrentDocument().getFilePath().getFileName()) + " " + flp.getString("notSaved"), 
						flp.getString("warning") + "!", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Path filePath =  fc.getSelectedFile().toPath();
			if(Files.exists(filePath) && !filePath.equals(JNotepadPP.this.model.getCurrentDocument().getFilePath())) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("document") + " " + filePath.getFileName().toString() + " "  + flp.getString("alreadyExists"),
						flp.getString("error") + "!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			model.saveDocument(model.getCurrentDocument(), filePath);
		}
	};
	
	/**
	 * Akcija zatvaranja dokumenta
	 */
	private LocalizableAction closeDocumentAction = new LocalizableAction("close", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getCurrentDocument() == null)
				return;
			if(JNotepadPP.this.model.getCurrentDocument().isModified()) {
				boolean isNull = JNotepadPP.this.model.getCurrentDocument().getFilePath() == null;
				String[] options = {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
				int result = JOptionPane.showOptionDialog(JNotepadPP.this,
						flp.getString("document") + " " + (isNull ? "(unnamed) " : JNotepadPP.this.model.getCurrentDocument().getFilePath().getFileName() + " ") 
						+ flp.getString("wantToSave"), 
						flp.getString("warning") + "!",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, null);
				switch(result) {
				case JOptionPane.CANCEL_OPTION:
					return;
				case JOptionPane.YES_OPTION:
					Path filePath = JNotepadPP.this.model.getCurrentDocument().getFilePath();
					if(filePath == null) {
						JFileChooser fc = new JFileChooser();
						fc.setDialogTitle(flp.getString("saveAs"));
						if(fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(
									JNotepadPP.this, 
									flp.getString("document") + " " + (isNull ? "(unnamed) " : JNotepadPP.this.model.getCurrentDocument().getFilePath().getFileName() + " ")
									+ flp.getString("notSaved"), 
									flp.getString("warning") + "!", 
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						filePath =  fc.getSelectedFile().toPath();
					}
					model.saveDocument(model.getCurrentDocument(), filePath);
				case JOptionPane.NO_OPTION:
					model.closeDocument(model.getCurrentDocument());
					return;
				}
			}
			model.closeDocument(model.getCurrentDocument());
		}
		
	};
	
	/**
	 * Akcija zatvaranja <code>JNotepadPP</code>
	 */
	private LocalizableAction exitAction = new LocalizableAction("exit", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			closing();
		}
		
	};
	
	/**
	 * Akcija koja računa statističke podatke o dokumentu
	 */
	private LocalizableAction statisticsAction = new LocalizableAction("statistics", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int noOfChars;
		private int noOfNonBlank = 0;
		private int noOfLines = 1;
		private String text;
		private Element element;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getCurrentDocument() == null)
				return;
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			element = doc.getDefaultRootElement();
			noOfChars = doc.getLength();
			noOfLines = element.getElementCount();
			try {
				text = doc.getText(0, noOfChars);
				for(int i = 0; i < noOfChars; i++) {
					if(Character.isLetterOrDigit(text.charAt(i)))
						noOfNonBlank++;
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			boolean isNull = JNotepadPP.this.model.getCurrentDocument().getFilePath() == null;
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("document") + " " 
					+ (isNull ? "(unnamed)" : JNotepadPP.this.model.getCurrentDocument().getFilePath().getFileName().toString())
					+ " " + flp.getString("has") + " " + noOfChars + " "+ flp.getString("chars") + ", "
					+ noOfNonBlank + " " + flp.getString("nonBlank") + " "
					+ noOfLines + " " + flp.getString("lines") + ".",
					flp.getString("statistics"), 1);
			noOfNonBlank = 0;
		}
		
	};
	
	/**
	 * Akcija koja kopira označeni dio teksta iz dokumenta
	 */
	private LocalizableAction copyAction = new LocalizableAction("copy", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getCurrentDocument() == null)
				return;
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot()
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
				try {
					copyPaste = doc.getText(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
		}
		
	};
	
	/**
	 * Akcija koja briše označeni dio teksta iz dokumenta
	 */
	private LocalizableAction cutAction = new LocalizableAction("cut", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getCurrentDocument() == null)
				return;
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot()
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	/**
	 * Akcija koja lijepi kopirani tekst na zadano mjesto u dokumentu
	 */
	private LocalizableAction pasteAction = new LocalizableAction("paste", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(JNotepadPP.this.model.getCurrentDocument() == null)
				return;
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot()
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			
			try {
				doc.remove(offset, len);
				doc.insertString(offset, copyPaste, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	/**
	 * Akcija koja sva označena slova pretvara u mala
	 */
	private LocalizableAction toLowerCaseAction = new LocalizableAction("toLowerCase", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot() 
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int offset = 0;
			offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text, 2);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
		
	};
	
	/**
	 * Akcija koja sva označena slova pretvara u velika
	 */
	private LocalizableAction toUpperCaseAction = new LocalizableAction("toUpperCase", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot() 
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int offset = 0;
			offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text, 1);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};
	
	/**
	 * Akcija koja rotira veličinu svih označenih slova
	 */
	private LocalizableAction toggleCaseAction = new LocalizableAction("changeCase", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot() 
					- JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			int offset = 0;
			offset = Math.min(JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot(),
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text, 0);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};
	
	/**
	 * Pomoćna metoda koja ostvaruje promjenu veličine slova
	 * 
	 * @param text tekst kojem treba promijeniti veličinu slova
	 * @param type tip promjene, 0 - zamjena svih slova, 1 - u velika slova, 2 - u mala slova
	 * @return promijenjeni tekst
	 */
	private String changeCase(String text, int type) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			switch(type) {
			case 0:
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
				continue;
			case 1:
				znakovi[i] = Character.toUpperCase(c);
				continue;
			case 2:
				znakovi[i] = Character.toLowerCase(c);
				continue;
			}
		}
		return new String(znakovi);
	}
	
	/**
	 * Akcija koja označene retke u dokumentu sortira uzlazno
	 */
	private LocalizableAction ascendingSort = new LocalizableAction("ascending", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortingHelper(true);
		}
		
	};
	
	/**
	 * Akcija koja označene elemente u dokumentu sortira silazno
	 */
	private LocalizableAction descendingSort = new LocalizableAction("descending", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortingHelper(false);
		}
		
	};
	
	/**
	 * Pomoćna metoda koja pomaže u sortiranju
	 * 
	 * @param ascending tip sortiranja: true - uzlazno, false - silazno
	 */
	private void sortingHelper(boolean ascending) {
		List<String> list = new ArrayList<>();
		Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
		Element element = doc.getDefaultRootElement();
		int dot = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getDot();
		int end = element.getElementIndex(dot);
		int mark = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getCaret().getMark();
		int start = element.getElementIndex(mark);
		
		if(start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		
		try {
			int removeStartOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineStartOffset(start);
			int removeEndOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineEndOffset(end);
		
			for(int i = start; i <= end; i++) {
				int startOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineStartOffset(i);
				int endOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineEndOffset(i);
				String text = doc.getText(startOffset, endOffset-startOffset);
				list.add(text);
			}
		
			sort(ascending, list, flp.getString("language"));
		
			String text = "";
			for(String s : list) {
				text += s;
			}
		
			doc.remove(removeStartOffset, removeEndOffset-removeStartOffset);
			doc.insertString(removeStartOffset, text, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Pomoćna metoda koja ostvaruje sortiranje
	 * 
	 * @param ascending tip sortiranja: true - uzlazno, false - silazno
	 * @param list lista Stringova koje treba sortirati
	 * @param language jezik po kojem se sortira
	 */
	private void sort(boolean ascending, List<String> list, String language) {
		Collections.sort(list, (s1,s2) -> {
			Locale locale = new Locale(language);
			Collator collator = Collator.getInstance(locale);
			return collator.compare(s1, s2);
		});
		if(!ascending)
			Collections.reverse(list);
	}
	
	/**
	 * Akcija koja briše sve neunikatne retke iz dokumenta
	 */
	private LocalizableAction unique = new LocalizableAction("unique", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> lines = new ArrayList<>();
			Document doc = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getDocument();
			
			for(int i = 0;
					i < JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineCount() &&
					JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineCount() > 1;
					i++) {
				try {
					int startOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineStartOffset(i);
					int endOffset = JNotepadPP.this.model.getCurrentDocument().getTextComponent().getLineEndOffset(i);
					String text = doc.getText(startOffset, endOffset-startOffset);
					if(lines.contains(text)) {
						doc.remove(startOffset, endOffset-startOffset);
						i--;
					} else {
						lines.add(text);
					}
				} catch(BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	};
	
	/**
	 * Metoda koja inicijalizira akcije
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing document from disk.");
		
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new document.");
		
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Used to save the document.");
		
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK |
				InputEvent.ALT_DOWN_MASK,
				false));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Used to save the document to any path.");
		
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Closes the current document.");
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes the JNotepad++");
		
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statisticsAction.putValue(Action.SHORT_DESCRIPTION, "Gives the statistical information for the document.");
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copies the selected portion of text.");
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Deletes the selected portion of text.");
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Pastes the copied text into selected position.");
		
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, "Switches the selected text to uppercase.");
		toUpperCaseAction.setEnabled(false);
		
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "Switches the selected text to lowercase.");
		toLowerCaseAction.setEnabled(false);
		
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION, "Switches the cases for every letter in document.");
		toggleCaseAction.setEnabled(false);
		
		ascendingSort.putValue(Action.SHORT_DESCRIPTION, "Sorts the lines in document in ascending order.");
		
		descendingSort.putValue(Action.SHORT_DESCRIPTION, "Sorts the lines in document in descending order.");
		
		unique.putValue(Action.SHORT_DESCRIPTION, "Deletes the repeating lines in the document.");
	}
	
	/**
	 * Metoda koja inicijalizira menije u GUI-ju
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(statisticsAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);
		
		JMenu changeCaseMenu =  new JMenu("Change case");
		toolsMenu.add(changeCaseMenu);
		
		changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(toggleCaseAction));
		
		JMenu sortMenu = new JMenu("Sort");
		toolsMenu.add(sortMenu);
		
		sortMenu.add(new JMenuItem(ascendingSort));
		sortMenu.add(new JMenuItem(descendingSort));
		
		toolsMenu.add(new JMenuItem(unique));
		
		ActionListener languageListener = e -> {
			JMenuItem item = (JMenuItem) e.getSource();
			LocalizationProvider.getInstance().setLanguage(item.getText());
			flp.fire();
		};
		
		JMenu languageMenu = new JMenu("Languages");
		JMenuItem en = new JMenuItem("en");
		en.addActionListener(languageListener);
		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(languageListener);
		JMenuItem de = new JMenuItem("de");
		de.addActionListener(languageListener);
		
		languageMenu.add(en);
		languageMenu.add(hr);
		languageMenu.add(de);
		
		menuBar.add(languageMenu);
		this.setJMenuBar(menuBar);
		
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				languageMenu.setText(flp.getString("languages"));
				toolsMenu.setText(flp.getString("tools"));
				editMenu.setText(flp.getString("edit"));
				fileMenu.setText(flp.getString("file"));
				sortMenu.setText(flp.getString("sort"));
				changeCaseMenu.setText(flp.getString("changeCase"));
			}
			
		});
	}
	
	/**
	 * Metoda koja stvara traku s alatima
	 */
	private void createToolbars() {
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(newDocumentAction));
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(saveAction));
		toolbar.add(new JButton(saveAsAction));
		toolbar.add(new JButton(closeDocumentAction));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(exitAction));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(statisticsAction));
		toolbar.add(new JButton(copyAction));
		toolbar.add(new JButton(cutAction));
		toolbar.add(new JButton(pasteAction));
		
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Metoda koja se poziva kada se želi zatvoriti <code>JNotepadPP</code>
	 */
	private void closing() {
		Iterator<SingleDocumentModel> iterator = JNotepadPP.this.model.iterator();
		
		while(iterator.hasNext()) {
			SingleDocumentModel m = iterator.next();
			if(m.isModified()) {
				boolean isNull = m.getFilePath() == null;
				String[] options = {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
				int result = JOptionPane.showOptionDialog(JNotepadPP.this,
						flp.getString("document") + " " + (isNull ? "(unnamed) " : m.getFilePath().getFileName() + " ") + flp.getString("wantToSave")
						, flp.getString("warning") + "!",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, null);
				switch(result) {
				case JOptionPane.CANCEL_OPTION:
					return;
				case JOptionPane.YES_OPTION:
					Path filePath = m.getFilePath();
					if(filePath == null) {
						JFileChooser fc = new JFileChooser();
						fc.setDialogTitle(flp.getString("saveAs"));
						if(fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(
									JNotepadPP.this, 
									flp.getString("docNotSaved"), 
									flp.getString("warning") + "!", 
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						filePath =  fc.getSelectedFile().toPath();
					}
					model.saveDocument(m, filePath);
					iterator.remove();
				case JOptionPane.NO_OPTION:
					iterator.remove();
					continue;
				}
			}
		}
		
		clock.stop();
		dispose();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("en");
				new JNotepadPP().setVisible(true);
			}
		});
	}
}

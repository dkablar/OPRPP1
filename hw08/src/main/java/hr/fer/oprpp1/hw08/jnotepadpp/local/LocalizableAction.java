package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider prov;
	private ILocalizationListener listener;
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		this.listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String text = LocalizableAction.this.prov.getString(LocalizableAction.this.key);
				LocalizableAction.this.putValue(Action.NAME, text);
			}
			
		};
		
		this.prov.addLocalizationListener(listener);
		
		String text = prov.getString(this.key);
		this.putValue(Action.NAME, text);
	}

}

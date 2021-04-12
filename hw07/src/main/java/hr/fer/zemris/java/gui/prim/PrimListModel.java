package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred <code>PrimListModel</code> predstavlja model liste prostih brojeva
 * 
 * @author Dorian Kablar
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private List<Integer> primList = new ArrayList<>();
	private List<ListDataListener> listenerList = new ArrayList<>();
	
	public PrimListModel() {
		primList.add(1);
	}

	@Override
	public int getSize() {
		return this.primList.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return this.primList.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		this.listenerList.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		this.listenerList.remove(l);
	}
	
	/**
	 * Metoda koja računa sljedeći prosti broj i dodaje ga u listu
	 */
	public void next() {
		int pos = this.primList.size();
		int current = this.primList.get(primList.size() - 1);
		boolean isPrim = false;
		while(!isPrim) {
			current++;
			int i = 2;
			for(; i < current; i++) {
				if(current % i == 0)
					break;
			}
			if(i == current)
				isPrim = true;
		}
		
		this.primList.add(current);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : this.listenerList) {
			l.intervalAdded(event);
		}
	}
}

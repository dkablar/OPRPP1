package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Razred <code>Node</code> predstavlja korijenski razred za sve čvorove
 * 
 * @author Dorian Kablar
 *
 */
public class Node {
	
	private ArrayIndexedCollection collection;
	
	/**
	 * Default konstruktor
	 */
	public Node() {
		this.collection = null;
	}
	
	/**
	 * Metoda dodaje čvor u kolekciju
	 * 
	 * @param child čvor koji treba dodati u kolekciju
	 */
	public void addChildNode(Node child) {
		if(this.collection == null)
			this.collection = new ArrayIndexedCollection();
		this.collection.add(child);
	}
	
	/**
	 * Metoda vraća broj čvorova koji su direktna "djeca"
	 * 
	 * @return
	 */
	public int numberOfChildren() {
		if(this.collection == null) return 0;
		return this.collection.size();
	}

	/**
	 * Vraća Node koji se nalazi na zadanom indexu kolekcije
	 * 
	 * @param index index Node-a
	 * @return Node, dijete na zadanom indexu
	 */
	public Node getChild(int index) {
		return (Node) this.collection.get(index);
	}
}

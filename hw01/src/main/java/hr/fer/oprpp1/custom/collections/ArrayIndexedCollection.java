package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
/**
 * Razred <code>ArrayIndexedCollection</code> predstavlja kolekciju elemenata implementiranu kao polje
 * 
 * @author Dorian Kablar
 *
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private Object[] elements;
	
	/**
	 * stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
	 */
	public ArrayIndexedCollection() {
		super();
		this.size = 0;
		this.elements = new Object[16];
	}
	
	/**
	 * stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
	 * 
	 * @param initialCapacity inicijalna veličina kolekcije
	 * @throws IllegalArgumentException ako je initialCapacity manji od 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if(initialCapacity < 1) 
			throw new IllegalArgumentException("Kolekcija ne može imati manje od jednog elementa.");
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
	 * 
	 * @param other kolekcija čiji se elementi kopiraju u novostvorenu kolekciju
	 * @throws NullPointerException ako je predana prazna kolekcija
	 */
	public ArrayIndexedCollection(Collection other) {
		this();
		
		if(other == null) throw new NullPointerException();
		Object[] o = other.toArray();
		for(int i = 0, j = o.length; i < j; i++) {
			this.elements[i] = o[i];
			this.size++;
		}
	}
	
	/**
	 * stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
	 * 
	 * @param initialCapacity inicijalna veličina kolekcije
	 * @param other kolekcija čiji se elementi kopiraju u novostvorenu kolekciju
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection other) {
		this(initialCapacity > other.size() ? initialCapacity : other.size());
		
		Object[] o = other.toArray();
		for(int i = 0, j = o.length; i < j; i++) {
			this.elements[i] = o[i];
			this.size++;
		}
	}

	@Override
	int size() {
		return this.size;
	}

	@Override
	boolean isEmpty() {
		return super.isEmpty();
	}
	
	@Override
	void add(Object value) {
		if(value == null) throw new NullPointerException();
		if(this.size == this.elements.length) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length*2);
		}
		this.elements[this.size++] = value;
	}

	@Override
	boolean contains(Object value) {
		for(int i = 0, j = this.size; i < j; i++) {
			if(this.elements[i].equals(value))
				return true;
		}
		return false;
	}

	@Override
	boolean remove(Object value) {
		int i = 0;
		for(int j = this.size; i < j; i++) {
			if(this.elements[i].equals(value)) {
				for(; i < j - 1; i++) {
					this.elements[i] = this.elements[i+1];
				}
				this.elements[j-1] = null;
				this.size--;
				return true;
			}
		}
		return false;
		
	}

	@Override
	Object[] toArray() {
		Object[] result = new Object[this.size()];
		for(int i = 0, j = this.size(); i < j; i++) {
			result[i] = this.elements[i];
		}
		return result;
	}

	@Override
	void forEach(Processor processor) {
		for(int i = 0, j = this.size; i < j; i++) {
			processor.process(this.elements[i]);
		}
	}

	@Override
	void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	void clear() {
		for(int i = 0; this.size > 0; i++, this.size--) {
			this.elements[i] = null;
		}
	}
	
	/**
	 * metoda dohvaća objekt koji se nalazi na zadanom indexu
	 * 
	 * @param index index elementa koji će se dohvatiti
	 * @return element na zadanom indexu
	 * @throws IndexOutOfBoundsException ako je zadan index izvan polja
	 */
	Object get(int index) {
		if(index > this.size - 1 || index < 0) throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * metoda ubacuje jedan element u kolekciju na zadani index
	 * 
	 * @param value element koji se dodaje u kolekciju
	 * @param position index na koji se dodaje novi element
	 * @throws NullPointerException ako je predan null za dodavanje u kolekciju
	 * @throws IdexOutOfBoundsException ako je zadan index izvan polja
	 */
	void insert(Object value, int position) {
		if(value == null) throw new NullPointerException();
		if(position > this.size || position < 0) throw new IndexOutOfBoundsException();
		if(this.size == this.elements.length) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length*2);
		}
		for(int i = this.size, j = position; i > j; i--) {
			this.elements[i] = this.elements[i-1];
		}
		this.elements[position] = value;
		this.size++;
	}
	
	/**
	 * Metoda vraća poziciju zadanog elementa u polju
	 * 
	 * @param value objekt čiju poziciju treba odrediti
	 * @return pozicija(index) zadanog objekta, ako se on nalazi u polju, -1 inače
	 */
	int indexOf(Object value) {
		for(int i = 0, j = this.size(); i < j; i++) {
			if(this.elements[i].equals(value)) return i;
		}
		return -1;
	}
	
	/**
	 * metoda iz kolekcije izbacuje element sa zadanog indexa
	 * 
	 * @param index index elementa kojeg treba ukloniti
	 * @throws IndexOutOfBoundsException ako je zadan index izvan polja
	 */
	void remove(int index) {
		if(index < 0 || index > this.size-1) throw new IndexOutOfBoundsException();
		for(int i = index, j = this.size - 1; i < j; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[this.size-1] = null;
		this.size--;
	}
	
}

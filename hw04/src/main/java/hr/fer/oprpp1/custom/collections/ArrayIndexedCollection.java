package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
/**
 * Razred <code>ArrayIndexedCollection</code> predstavlja kolekciju elemenata implementiranu kao polje,
 * implementira sučelje <code>Collection</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ArrayIndexedCollection implements List {

	private int size;
	/**
	 * polje koje predstavlja elemente kolekcije
	 */
	private Object[] elements;
	private long modificationCount = 0;
	
	/**
	 * Stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
	 */
	public ArrayIndexedCollection() {
		super();
		this.size = 0;
		this.elements = new Object[16];
	}
	
	/**
	 * Stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
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
	 * Stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
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
	 * Stvara novi <code>ArrayIndexedCollection</code> čije su komponente size i elements
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

	/**
	 * Metoda vraća broj objekata u ovoj kolekciji
	 * 
	 * @return broj podataka u kolekciji
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Metoda dodaje objekt u kolekciju
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException();
		if(this.size == this.elements.length) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length*2);
		}
		this.elements[this.size++] = value;
		this.modificationCount++;
	}

	/**
	 * Metoda provjerava nalazi li se zadani objekt u kolekciji
	 * 
	 * @param value zadani objekt koji se traži u kolekciji
	 * @return <code>true</code> ako se parametar nalazi u kolekciji, <code>false</code> inače
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0, j = this.size; i < j; i++) {
			if(this.elements[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Metoda koja briše zadani objekt iz kolekcije, ako se taj objekt u njoj nalazi
	 * 
	 * @param value objekt kojeg treba obrisati iz kolekcije
	 * @return <code>true</code> ako se zadani objekt nalazi u kolekciji, <code>false</code> inače
	 */
	@Override
	public boolean remove(Object value) {
		int i = 0;
		for(int j = this.size; i < j; i++) {
			if(this.elements[i].equals(value)) {
				for(; i < j - 1; i++) {
					this.elements[i] = this.elements[i+1];
				}
				this.elements[j-1] = null;
				this.size--;
				this.modificationCount++;
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda stvara novo polje, i puni ga s objektima iz kolekcije
	 * 
	 * @return novo polje popunjeno svim objektima kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size()];
		for(int i = 0, j = this.size(); i < j; i++) {
			result[i] = this.elements[i];
		}
		return result;
	}

	/**
	 * Metoda briše sve elemente iz kolekcije
	 */
	@Override
	public void clear() {
		for(int i = 0; this.size > 0; i++, this.size--) {
			this.elements[i] = null;
		}
		this.modificationCount++;
	}
	
	/**
	 * Metoda dohvaća objekt koji se nalazi na zadanom indexu
	 * 
	 * @param index index elementa koji će se dohvatiti
	 * @return element na zadanom indexu
	 * @throws IndexOutOfBoundsException ako je zadan index izvan polja
	 */
	@Override
	public Object get(int index) {
		if(index > this.size - 1 || index < 0) throw new IndexOutOfBoundsException();
		return this.elements[index];
	}
	
	/**
	 * Metoda ubacuje jedan element u kolekciju na zadani index
	 * 
	 * @param value element koji se dodaje u kolekciju
	 * @param position index na koji se dodaje novi element
	 * @throws NullPointerException ako je predan null za dodavanje u kolekciju
	 * @throws IdexOutOfBoundsException ako je zadan index izvan polja
	 */
	@Override
	public void insert(Object value, int position) {
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
		this.modificationCount++;
	}
	
	/**
	 * Metoda vraća poziciju zadanog elementa u polju
	 * 
	 * @param value objekt čiju poziciju treba odrediti
	 * @return pozicija(index) zadanog objekta, ako se on nalazi u polju, -1 inače
	 */
	@Override
	public int indexOf(Object value) {
		for(int i = 0, j = this.size(); i < j; i++) {
			if(this.elements[i].equals(value)) return i;
		}
		return -1;
	}
	
	/**
	 * Metoda iz kolekcije izbacuje element sa zadanog indexa
	 * 
	 * @param index index elementa kojeg treba ukloniti
	 * @throws IndexOutOfBoundsException ako je zadan index izvan polja
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index > this.size-1) throw new IndexOutOfBoundsException();
		for(int i = index, j = this.size - 1; i < j; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[this.size-1] = null;
		this.size--;
		this.modificationCount++;
	}
	
	/**
	 * Razred <code>ArrayIndexedElementsGetter</code> predstavlja implementaciju sučelja <code>ElementsGetter</code>
	 * i omogućava dohavaćanje, jednog po jednog, elementa kolekcije
	 * 
	 * @author Dorian Kablar
	 *
	 */
	private static class ArrayIndexedElementsGetter implements ElementsGetter {

		private ArrayIndexedCollection collection;
		private int index = 0;
		private long savedModificationCount;
		
		/**
		 * Stvara novi <code>ArrayIndexedElementsGetter</code> čiji je parametar 
		 * <code>ArrayIndexedCollection</code> kolekcija
		 * 
		 * @param col kolekcije iz koje će se moći dohvaćati elementi
		 */
		private ArrayIndexedElementsGetter(ArrayIndexedCollection collection) {
			this.collection = collection;
			this.savedModificationCount = collection.modificationCount;
		}
		
		/**
		 * Metoda koja provjerava postoji li sljedeći element u kolekciji
		 * 
		 * @return <code>true</code> ako postoji sljedeći element u kolekciji, <code>false</code> inače
		 * @throws ConcurrentModificationException ako je korisnik mijenjao kolekciju
		 */
		@Override
		public boolean hasNextElement() {
			if(this.savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			
			if(this.index < this.collection.size())
				return true;
			return false;
		}

		/**
		 * Metoda koja dohvaća sljedeći element u kolekciji
		 * 
		 * @return sljedeći element iz kolekcije
		 * @throws NoSuchElementException ako nema više elemenata u kolekciji
		 * @throws ConcurrentModificationException ako je korisnik mijenjao kolekciju
		 */
		@Override
		public Object getNextElement() {
			if(this.savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			if(this.index >= this.collection.size())
				throw new NoSuchElementException();
			
			this.index++;
			return this.collection.get(index - 1);
		}
		
	}

	/**
	 * Metoda koja stvara objekt koji će dohvaćati elemente kolekcije
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayIndexedElementsGetter(this);
	}

	@Override
	public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter elementsGetter = col.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			Object value = elementsGetter.getNextElement();
			if(tester.test(value))
				this.add(value);
		}
	}
	
}

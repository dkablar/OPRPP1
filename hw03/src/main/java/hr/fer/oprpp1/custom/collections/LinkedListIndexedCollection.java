package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred <code>LinkedListIndexedCollection</code> predstavlja kolekciju elemenata implementiranu 
 * kao dvostruko povezanu listu, implementira sučelje <code>Collection</code>
 * 
 * @author Dorian Kablar
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * Razred <code>ListNode</code> predstavlja jedan čvor liste
	 * 
	 * @author Dorian Kablar
	 *
	 */
	private static class ListNode<T> {
		private ListNode<T> previous;
		private ListNode<T> next;
		private T value;
		
		/**
		 * stvara novi <code>ListNode</code> čiji su elementi previous, next, value
		 * 
		 * @param previous referenca na prethodni element liste
		 * @param next referenca na sljedeći član liste
		 * @param value vrijednost elementa
		 */
		private ListNode(ListNode<T> previous, ListNode<T> next, T value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
		public T getValue() {
			return this.value;
		}
	}
	
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount = 0;
	
	/**
	 * stvara novi <code>LinkedListIndexedCollection</code> čiji su elementi size, first i last
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = new ListNode<>(null, null, null);
		this.last = new ListNode<>(null, null, null);
	}
	
	/**
	 * stvara novi <code>LinkedListIndexedCollection</code> u koji kopira zadanu kolekciju
	 * @param other kolekcija koju treba kopirati 
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		this();
		addAll(other);
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
	 * Metoda koja dodaje objekt u kolekciju
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	@Override
	public void add(T value) {
		if(value == null) throw new NullPointerException();
		ListNode<T> newElement = new ListNode<>(this.last, null, value);
		if(this.last.value == null) {
			this.first = newElement;
		} else {
			this.last.next = newElement;
		}
		this.last = newElement;
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Metoda koja provjerava nalazi li se zadani objekt u kolekciji
	 * 
	 * @param value zadani objekt koji se traži u kolekciji
	 * @return <code>true</code> ako se parametar nalazi u kolekciji, <code>false</code> inače
	 */
	@Override
	public boolean contains(Object value) {
		ListNode<T> tmp = this.first;
		for(int i = 0, j = this.size(); i < j; i++, tmp = tmp.next) {
			if(tmp.value.equals(value)) return true;
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
	public boolean remove(T value) {
		ListNode<T> tmp = this.first;
		for(int i = 0, j = this.size(); i < j && !tmp.value.equals(value); i++, tmp = tmp.next) {
		}
		if(tmp == null) return false;
		if(tmp.equals(this.first) && tmp.equals(this.last)) { // izbrisan je zadnji element liste
			this.first = null;
			this.last = null;
		} else if(tmp.equals(this.first)) { // brisanje elementa s početka liste
			this.first = tmp.next;
			tmp.next.previous = null;
		} else if(tmp.equals(this.last)) { // brisanje elementa s kraja liste
			this.last = tmp.previous;
			tmp.previous.next = null;
		} else { // element koji ima sljedećeg i prethodnog
			tmp.next.previous = tmp.previous;
			tmp.previous.next = tmp.next;
		}
		this.size--;
		this.modificationCount++;
		return true;
	}

	/**
	 * Metoda koja stvara novo polje, i puni ga s objektima iz kolekcije
	 * 
	 * @return novo polje popunjeno svim objektima kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size()];
		ListNode<T> tmp = this.first;
		for(int i = 0, j = this.size(); i < j; i++, tmp = tmp.next) {
			result[i] = tmp.value;
		}
		return result;
	}

	/**
	 * Metoda koja treba obrisati sve elemente iz kolekcije
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.modificationCount++;
		this.size = 0;
	}
	
	/**
	 * Metoda dohvaća element na zadanom indexu
	 * 
	 * @param index index elementa kojeg treba dohvatiti
	 * @return element koji je trebalo dohvatiti
	 */
	@Override
	public T get(int index) {
		if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
		
		ListNode<T> tmpFirst = this.first, tmpLast = this.last;
		int i = 0, j = this.size() - 1;
		for(; i != index && j != index; i++, j--) {
			tmpFirst = tmpFirst.next;
			tmpLast = tmpLast.previous;
		}
		
		if(i==index) return tmpFirst.value;
		return tmpLast.value;
	}
	
	/**
	 * Metoda ubacuje element na zadanu poziciju u listi, elementi iza te pozicije
	 * se pomiču udesno
	 * 
	 * @param value element kojeg treba dodati u listu
	 * @param position pozicija na koju treba dodati element
	 */
	@Override
	public void insert(T value, int position) {
		if(position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if(value == null) throw new NullPointerException();
		
		if(position == this.size()) { // dodajemo na kraj liste
			this.add(value);
		} else if(position == 0) { // dodajemo na početak liste
			ListNode<T> newElement = new ListNode<>(null, this.first, value);
			this.first.previous = newElement;
			this.first = newElement;
			this.size++;
		} else { // dodajemo negdje između
			ListNode<T> tmp = this.first;
			for(int i = 0; i < this.size(); i++, tmp = tmp.next) {
				if(i == position) {
					ListNode<T> newElement = new ListNode<>(tmp.previous, tmp, value);
					tmp.previous.next = newElement;
					tmp.previous = newElement;
				}
			}
			this.size++;
			this.modificationCount++;
		}
	}
	
	/**
	 * Metoda vraća poziciju zadanog elementa u kolekciji
	 * 
	 * @param value element čija se pozicija traži
	 * @return index elementa, ako se on nalazi u kolkeciji, -1 inače
	 */
	@Override
	public int indexOf(T value) {
		ListNode<T> tmp = this.first;
		for(int i = 0, j = this.size(); i < j; i++, tmp = tmp.next) {
			if(tmp.value.equals(value)) return i;
		}
		return -1;
	}
	
	/**
	 * Metoda briše element na zadanom indexu iz kolekcije
	 * 
	 * @param index index elementa kojeg treba obrisati
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index > this.size() - 1) throw new IndexOutOfBoundsException();
		ListNode<T> tmp = this.first;
		for(int i = 0; i < index - 1; i++, tmp = tmp.next);
		tmp.next.previous = tmp.previous;
		if(index != 0)
			tmp.previous.next = tmp.next;
		this.size--;
		this.modificationCount++;
	}

	/**
	 * Razred <code>LinkedListIndexedElementsGetter</code> predstavlja implementaciju sučelja <code>ElementsGetter</code>
	 * i omogućava dohavaćanje, jednog po jednog, elementa kolekcije
	 * 
	 * @author Dorian Kablar
	 *
	 */
	private static class LinkedListIndexedElementsGetter<T> implements ElementsGetter<T> {

		private LinkedListIndexedCollection<T> collection;
		private ListNode<T> current;
		private long savedModificationCount;
		
		/**
		 * Stvara novi <code>ArrayIndexedElementsGetter</code> čiji je parametar 
		 * <code>ArrayIndexedCollection</code> kolekcija
		 * 
		 * @param col kolekcije iz koje će se moći dohvaćati elementi
		 */
		private LinkedListIndexedElementsGetter(LinkedListIndexedCollection<T> collection) {
			this.collection = collection;
			this.current = collection.first;
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
			return this.current != null;
		}

		/**
		 * Metoda koja dohvaća sljedeći element u kolekciji
		 * 
		 * @return sljedeći element iz kolekcije
		 * @throws NoSuchElementException ako nema više elemenata u kolekciji
		 * @throws ConcurrentModificationException ako je korisnik mijenjao kolekciju
		 */
		@Override
		public T getNextElement() {
			if(this.savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			if(!this.hasNextElement())
				throw new NoSuchElementException();

			ListNode<T> tmp = current;
			this.current = tmp.next;
			return tmp.getValue();
		}
		
	}
	
	/**
	 * Metoda koja stvara objekt koji će dohvaćati elemente kolekcije
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LinkedListIndexedElementsGetter<>(this);
	}
	
	@Override
	public void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> elementsGetter = col.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			T value = elementsGetter.getNextElement();
			if(tester.test(value))
				this.add(value);
		}
	}
}

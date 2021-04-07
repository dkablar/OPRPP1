package hr.fer.oprpp1.custom.collections;

/**
 * Razred <code>LinkedListIndexedCollection</code> predstavlja kolekciju elemenata implementiranu 
 * kao dvostruko povezanu listu
 * 
 * @author Dorian Kablar
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Razred <code>ListNode</code> predstavlja jedan čvor liste
	 * 
	 * @author Dorian Kablar
	 *
	 */
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
		
		/**
		 * stvara novi <code>ListNode</code> čiji su elementi previous, next, value
		 * 
		 * @param previous referenca na prethodni element liste
		 * @param next referenca na sljedeći član liste
		 * @param value vrijednost elementa
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * stvara novi <code>LinkedListIndexedCollection</code> čiji su elementi size, first i last
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = new ListNode(null, null, null);
		this.last = new ListNode(null, null, null);
	}
	
	/**
	 * stvara novi <code>LinkedListIndexedCollection</code> u koji kopira zadanu kolekciju
	 * @param other kolekcija koju treba kopirati 
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
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
		ListNode newElement = new ListNode(this.last, null, value);
		if(this.last.value == null) {
			this.first = newElement;
		} else {
			this.last.next = newElement;
		}
		this.last = newElement;
		this.size++;
	}

	@Override
	boolean contains(Object value) {
		ListNode tmp = this.first;
		for(int i = 0, j = this.size(); i < j; i++, tmp = tmp.next) {
			if(tmp.value.equals(value)) return true;
		}
		return false;
	}

	@Override
	boolean remove(Object value) {
		ListNode tmp = this.first;
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
		return true;
	}

	@Override
	Object[] toArray() {
		Object[] result = new Object[this.size()];
		ListNode tmp = this.first;
		for(int i = 0, j = this.size(); i < j; i++, tmp = tmp.next) {
			result[i] = tmp.value;
		}
		return result;
	}

	@Override
	void forEach(Processor processor) {
		for(ListNode tmp = this.first; tmp != null; tmp = tmp.next) {
			processor.process(tmp.value);
		}
	}

	@Override
	void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	void clear() {
		this.first = this.last = null;
		this.size = 0;
	}
	
	/**
	 * Metoda dohvaća element na zadanom indexu
	 * 
	 * @param index index elementa kojeg treba dohvatiti
	 * @return element koji je trebalo dohvatiti
	 */
	Object get(int index) {
		if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
		
		ListNode tmpFirst = this.first, tmpLast = this.last;
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
	void insert(Object value, int position) {
		if(position < 0 || position > this.size()) throw new IndexOutOfBoundsException();
		if(value == null) throw new NullPointerException();
		
		if(position == this.size()) { // dodajemo na kraj liste
			this.add(value);
		} else if(position == 0) { // dodajemo na početak liste
			ListNode newElement = new ListNode(null, this.first, value);
			this.first.previous = newElement;
			this.first = newElement;
			this.size++;
		} else { // dodajemo negdje između
			ListNode tmp = this.first;
			for(int i = 0; i < this.size(); i++, tmp = tmp.next) {
				if(i == position) {
					ListNode newElement = new ListNode(tmp.previous, tmp, value);
					tmp.previous.next = newElement;
					tmp.previous = newElement;
				}
			}
			this.size++;
		}
	}
	
	/**
	 * metoda vraća poziciju zadanog elementa u kolekciji
	 * 
	 * @param value element čija se pozicija traži
	 * @return index elementa, ako se on nalazi u kolkeciji, -1 inače
	 */
	int indexOf(Object value) {
		ListNode tmp = this.first;
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
	void remove(int index) {
		if(index < 0 || index > this.size() - 1) throw new IndexOutOfBoundsException();
		ListNode tmp = this.first;
		for(int i = 0; i < index - 1; i++, tmp = tmp.next);
		tmp.next.previous = tmp.previous;
		if(index != 0)
			tmp.previous.next = tmp.next;
		this.size--;
	}
}

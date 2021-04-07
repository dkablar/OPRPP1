package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred <code>SimpleHashTable</code> predstavlja jednostavniju implementaciju HashMape
 * 
 * @author Dorian Kablar
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	private int size;
	/**
	 * Polje referenci na liste elemenata
	 */
	private TableEntry<K, V>[] table;
	private int modificationCount;
	
	/**
	 * Razred <code>TableEntry</code> predstavlja jedan unos u <code>SimpleHashtable</code>
	 * 
	 * @author Dorian Kablar
	 *
	 * @param <K> tip ključa
	 * @param <V> tip vrijednost
	 */
	public static class TableEntry<K, V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Konstruktor, stvara novi <code>TableEntry</code> s parametrima key, value i next
		 * 
		 * @param key ključ
		 * @param value vrijednost
		 * @param next referenca na sljedeći <code>TableEntry</code> u listi
		 * @throws NullPointerException ako je zadani ključ null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null)
				throw new NullPointerException("Ključ ne može biti null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Getter, dohvaća key
		 * 
		 * @return ključ
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Getter, dohvaća value
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Setter, postavlja value na novu vrijednost
		 * 
		 * @param value nova vrijednost na koju treba postaviti value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return this.getKey().toString() + "=" + this.getValue().toString();
		}
	}
	
	/**
	 * Konstruktor, stvara novi <code>SimpleHashtable</code> i internu tablicu postavlja na veličinu 16
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.size = 0;
		this.table = new TableEntry[16];
		this.modificationCount = 0;
	}
	
	/**
	 * Konstruktor, stvara novi <code>SimpleHashtable</code> i internu tablicu postavlja na veličinu koja je prva
	 * potencija broja dva veća ili jednaka parametru capacity
	 * 
	 * @param capacity željena veličina interne tablice
	 * @throws IllegalArgumentException ako je zadani kapacitet tablice manji od 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1)
			throw new IllegalArgumentException("Kapacitet tablice ne može biti manji od 1.");
		
		this.size = 0;
		
		int i = 2;
		for(; i < capacity; i *= 2);
		this.table = new TableEntry[i];
		this.modificationCount = 0;
	}
	
	/**
	 * Metoda koja provjerava postoji li u tablici vrijednost sa zadanim ključem, te ako postoji, 
	 * mijenja vrijednost sa zadanom, inače stvara novi <code>TableEntry</code> sa zadanim ključem
	 * i vrijednosti te ih stavlja u tablicu
	 * 
	 * @param key ključ koji treba potražiti u tablici
	 * @param value vrijednost koju treba staviti u tablicu
	 * @return stara vrijednost na zadanom ključu u tablici, ako taj element postoji, null inače
	 * @throws IllegalArgumentException ako je zadani ključ null
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new IllegalArgumentException("Ključ ne može biti null.");
		
		int index = Math.abs(key.hashCode() % this.table.length);
		
		if(this.table[index] == null) {
			if(((double)(this.size + 1) / (double)this.table.length) >= 0.75) {
				doubleTheTable(this.toArray());
				V result = this.put(key, value);
				return result;
			}
			this.table[index] = new TableEntry<>(key, value, null);
			this.size++;
			this.modificationCount++;
			return null;
		}
		
		TableEntry<K, V> head = this.table[index];
		
		while(this.table[index].next != null && !(this.table[index].getKey().equals(key))) {
				this.table[index] = this.table[index].next;
		}
		
		if(this.table[index].getKey().equals(key)) {
			V result = this.table[index].getValue();
			this.table[index].setValue(value);
			this.table[index] = head;
			return result;
		}
		
		if(((double)(this.size + 1) / (double)this.table.length) >= 0.75) {
			doubleTheTable(this.toArray());
			V result = this.put(key, value);
			return result;
		}
		
		this.table[index].next = new TableEntry<>(key, value, null);
		this.table[index] = head;
		this.size++;
		this.modificationCount++;
		return null;
	}
	
	/**
	 * Metoda koja dohvaća vrijednost na zadanom ključu, ako se zadani ključ nalazi u tablici
	 * 
	 * @param key ključ koji se traži u tablici
	 * @return vrijednost elementa za zadani ključ, null inače
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		
		int index = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> tmp = this.table[index];
		
		for(; tmp != null && !(tmp.getKey().equals(key)); tmp = tmp.next);
		
		if(tmp != null) {
			V result = tmp.getValue();
			return result;
		}
		
		return null;
	}
	
	/**
	 * Metoda dohvaća broj elemenata u tablici
	 * 
	 * @return broj elemenata u tablici
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Metoda provjerava nalazi li se u tablici zadani ključ
	 * 
	 * @param key ključ koji treba potražiti u tablici
	 * @return <code>true</code> ako se zadani ključ nalazi u tablici, <code>false</code> inače
	 */
	public boolean containsKey(Object key) {
		if(key == null)
			return false;
		
		int index = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> tmp = this.table[index];
		
		for(; tmp != null && !(tmp.getKey().equals(key)); tmp = tmp.next);
		
		if(tmp != null)
			return true;
		return false;
	}
	
	/**
	 * Metoda provjerava nalazi li se u tablici zadana vrijednost
	 * 
	 * @param value vrijednost koja se treba potražiti u tablici
	 * @return <code>true</code> ako se zadana vrijednost nalazi u tablici, <code>false</code> inače
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> tmp = this.table[i];
			for(; tmp != null && !(tmp.getValue().equals(value)); tmp = tmp.next);
			
			if(tmp != null)
				return true;
		}
		return false;
	}
	
	/**
	 * Metoda koja iz tablice "briše" element sa zadanim ključem, ako takav postoji
	 * 
	 * @param key ključ koji se "traži" u tablici
	 * @return vrijednost izbrisanog elementa, ili se vrijednost sa zadanim ključem ne nalazi u tablici
	 */
	public V remove(Object key) {
		if(key == null)
			return null;
		
		int index = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> head = this.table[index];
		
		if(this.table[index].getKey().equals(key)) {
			V result = this.table[index].getValue();
			this.table[index] = this.table[index].next;
			this.size--;
			this.modificationCount++;
			return result;
		}
		
		while(this.table[index] != null && !(this.table[index].next.getKey().equals(key)))
			this.table[index] = this.table[index].next;
		
		if(this.table[index] != null) {
			V result = this.table[index].next.getValue();
			this.table[index].next = this.table[index].next.next;
			this.size--;
			this.table[index] = head;
			this.modificationCount++;
			return result;
		}
		
		this.table[index] = head;
		return null;
	}
	
	/**
	 * Metoda koja ispituje je li tablica prazna
	 * 
	 * @return <code>true</code> ako je tablica prazna, <code>false</code> inače
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int k = 0;
		
		for(int i = 0, j = this.table.length; i < j; i++) {
			for(TableEntry<K, V> tmp = this.table[i]; tmp != null; tmp = tmp.next) {
				sb.append(tmp.toString()).append(k < this.size - 1 ? ", " : "");
				k++;
			}
		}
		sb.append("]\n");
		return sb.toString();
	}
	
	/**
	 * Metoda koja stvara novo polje i u njega stavlja sve elemente tablice
	 * 
	 * @return novo polje koje sadrži sve elemente tablice
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] result = (TableEntry<K, V>[]) new TableEntry[this.size];
		int index = 0;
		
		for(int i = 0, j = this.table.length; i < j; i++) {
			for(TableEntry<K, V> tmp = this.table[i]; tmp != null; tmp = tmp.next) {
				result[index] = tmp;
				index++;
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja briše sve elemente iz tablice
	 */
	public void clear() {
		for(int i = 0, j = this.table.length; i < j; i++) {
			for(TableEntry<K, V> tmp = this.table[i]; tmp != null; tmp = tmp.next) {
				this.remove(tmp.getKey());
			}
		}
	}
	
	/**
	 * Metoda koja udvostručuje kapacitet tablice
	 * 
	 * @param array udvostručena "tablica", tj. polje u kojem se spremaju reference na liste elemenata
	 */
	@SuppressWarnings("unchecked")
	private void doubleTheTable(TableEntry<K, V>[] array) {
		System.out.println(array.length);
		for(int i = 0; i < array.length; i++)
			System.out.println(array[i]);
		this.table = new TableEntry[this.table.length * 2];
		this.size = 0;
		for(int i = 0; i < array.length; i++) {
			this.put(array[i].getKey(), array[i].getValue());
		}
		return;
	}
	
	/**
	 * Metoda koja stvara iterator koji iterira elemente tablice
	 * 
	 * @return iterator tablice
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}
	
	/**
	 * Razred <code>IteratorImpl</code> predstavlja implementaciju iteratora za razred <code>SimpleHashtable</code>
	 * 
	 * @author Dorian Kablar
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		private int index = 0; // pomoćna varijabla koja služi za brojanje elemenata koji su iterirani
		private K key; // pomoćna varijabla koja služi za metodu remove
		private int i = 0; // pomoćna varijabla, služi kao pomoć u određivanju slota elementa
		private TableEntry<K, V> current; // referenca na trenutni element
		private int modificationCount; // varijabla koja sprema trenutni broj modifikacija
		
		/**
		 * Konstruktor, stvara novi <code>IteratorImpl</code> s parametrom current
		 * 
		 * @param current referenca na prvi element u tablici
		 */
		private IteratorImpl(SimpleHashtable<K, V> current) {
			this.current = current.table[0];
			this.modificationCount = current.modificationCount;
		}

		/**
		 * Metoda provjerava ima li još elemenata u tablici
		 * 
		 * @return <code>true</code> ako ima još elemenata u tablici, <code>false</code> inače
		 * @throws ConcurrentModificationException ako je korisnik mijenjao sadržaj tablice
		 */
		@Override
		public boolean hasNext() {
			if(this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Tablica je mijenjana bez dozvole.");
			return this.index < SimpleHashtable.this.size();
		}

		/**
		 * Metoda prelazi na sljedeći element tablice, te vraća trenutni element tablice
		 * 
		 * @return trenutni element u tablici
		 * @throws ConcurrentModificationException ako je korisnik mijenjao sadržaj tablice
		 * @throws NoSuchElementException ako više nema elemenata u tablici
		 */
		@Override
		public TableEntry<K, V> next() {
			if(this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Tablica je mijenjana bez dozvole.");
			
			if(this.current == null) {
				while(this.current == null) {
					this.current = SimpleHashtable.this.table[++i];
				}
			}
			
			TableEntry<K, V> data = this.current;
			this.key = data.getKey();
			if(this.index == SimpleHashtable.this.size())
				throw new NoSuchElementException("Nema više elemenata u tablici.");
			if(++this.index == SimpleHashtable.this.size())
				return data;
			this.current = this.current.next;
			return data;
		}
		
		/**
		 * Metoda koja iz tablice "briše" element na kojem se iterator nalazi
		 * 
		 * @throws ConcurrentModificationException ako je korisnik mijenjao sadržaj tablice
		 * @throws IllegalStateException ako se metoda pozove dvaput za isti element
		 */
		@Override
		public void remove() {
			if(this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Tablica je mijenjana bez dozvole.");
			
			if(!(SimpleHashtable.this.containsKey(this.key)))
				throw new IllegalStateException("Element u trenutnoj iteraciji je već izbrisan.");
			SimpleHashtable.this.remove(this.key);
			this.modificationCount++;
		}
		
	}
}

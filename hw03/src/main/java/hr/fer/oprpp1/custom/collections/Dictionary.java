package hr.fer.oprpp1.custom.collections;

/**
 * Razred <code>Dictionary</code> predstavlja kolekciju na principu ključ-vrijednost. Jednostavnija implementacija
 * Javine Map kolekcije
 * 
 * @author Dorian Kablar
 *
 * @param <K> ključ
 * @param <V> vrijednost
 */
public class Dictionary<K, V> {

	private ArrayIndexedCollection<Record<K, V>> collection;
	
	/**
	 * Konstruktor, stvara novi <code>Dicitionary</code> i inicijalizira kolekciju
	 */
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Razred <code>Record</code>, predstavlja jedan zapis u rječniku
	 * 
	 * @author Dorian Kablar
	 *
	 * @param <T> ključ
	 * @param <E> vrijednost
	 */
	private static class Record<K, V> {
		private K key;
		private V value;
		
		/**
		 * Konstruktor, stvara novi <code>Record</code> s parametrima key i value
		 * 
		 * @param key ključ
		 * @param value vrijednost
		 */
		public Record(K key, V value) {
			if(key == null)
				throw new NullPointerException("Ključ ne može biti null.");
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter, vraća ključ
		 *  
		 * @return ključ
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Getter, vraća vrijednost
		 * 
		 * @return vrijednost
		 */
		public V getValue() {
			return this.value;
		}
	}
	
	/**
	 * Metoda koja ispituje je li <code>Dictionary</code> prazan
	 * 
	 * @return <code>true</code> ako je <code>Dictionary</code> prazan, <code>false</code> inače
	 */
	public boolean isEmpty() {
		return this.collection.isEmpty();
	}
	
	/**
	 * Metoda koja vraća broj elemenata u <code>Dictionary</code>
	 * 
	 * @return broj elemenata u kolekciji
	 */
	public int size() {
		return this.collection.size();
	}
	
	/**
	 * Metoda ispražnjava <code>Dictionary</code>
	 */
	public void clear() {
		this.collection.clear();
	}
	
	/**
	 * Metoda ubacuje <code>value</code> u element s ključem <code>key</code> i vraća staru vrijednost sa tog ključa
	 * 
	 * @param key ključ
	 * @param value nova vrijednost
	 * @return value ako stara vrijednost nije bila <code>null</code>, <code>null</code> inače
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException("Ključ ne može biti null.");
		
		V result;
		for(int i = 0, j = this.collection.size(); i < j; i++) {
			if(this.collection.get(i).getKey().equals(key)) {
				result = this.collection.get(i).getValue();
				this.collection.remove(i);
				this.collection.insert(new Record<>(key, value), i);
				return result;
			}
		}
		
		this.collection.add(new Record<>(key, value));
		return null;
	}
	
	/**
	 * Metoda dohvaća vrijednost s ključem <code>key</code> iz <code>Dictionary</code>
	 * 
	 * @param key ključ po kojem se pretražuje <code>Dictionary</code>
	 * @return vrijednost, ako ista nije <code>null</code>, <code>null</code> inače
	 */
	public V get(Object key) {
		if(key == null)
			throw new NullPointerException("Ključ ne može biti null.");
		
		for(int i = 0, j = this.collection.size(); i < j; i++) {
			if(this.collection.get(i).getKey().equals(key)) {
				return this.collection.get(i).getValue();
			}
		}
		return null;
	}
	
	/**
	 * Metoda briše element s ključem <code>key</code> iz <code>Dictionary</code> i vraća obrisanu vrijednost
	 * 
	 * @param key ključ čiji se element treba izbrisati iz <code>Dictionary</code>
	 * @return obrisana vrijednost, ako ista nije <code>null</code>, <code>null</code> inače
	 */
	public V remove(K key) {
		if(key == null)
			throw new NullPointerException("Ključ ne može biti null.");
		
		for(int i = 0, j = this.collection.size(); i < j; i++) {
			if(this.collection.get(i).getKey().equals(key)) {
				V result = this.collection.get(i).getValue();
				this.collection.remove(i);
				if(result != null)
					return result;
				return null;
			}
		}
		return null;
	}
}

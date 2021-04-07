package hr.fer.oprpp1.custom.collections;

/**
 * Razred <code>Collection</code> predstavlja neku općenitu kolekciju objekata
 * 
 * @author Dorian Kablar
 *
 */
public class Collection {
	
	protected Collection() {
	}
	
	/**
	 * metoda vraća broj objekata u ovoj kolekciji
	 * 
	 * @return broj podataka u kolekciji
	 */
	int size() {
		return 0;
	}
	
	/**
	 * metoda koja provjerava je li kolekcija prazna
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, u suprotnom <code>false</code>
	 */
	boolean isEmpty() {
		if(this.size() == 0) return true;
		return false;
	}
	
	/**
	 * metoda dodaje objekt u kolekciju
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	void add(Object value) {
	}
	
	/**
	 * metoda provjerava nalazi li se zadani objekt u kolekciji
	 * 
	 * @param value zadani objekt koji se traži u kolekciji
	 * @return <code>true</code> ako se parametar nalazi u kolekciji, <code>false</code> inače
	 */
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * metoda koja briše zadani objekt iz kolekcije, ako se taj objekt u njoj nalazi
	 * 
	 * @param value objekt kojeg treba obrisati iz kolekcije
	 * @return <code>true</code> ako se zadani objekt nalazi u kolekciji, <code>false</code> inače
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * metoda stvara novo polje, i puni ga s objektima iz kolekcije
	 * 
	 * @return novo polje popunjeno svim objektima kolekcije
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * metoda zove <code>processor.process(.)</code> za svaki element kolekcije
	 * 
	 * @param processor <code>Processor</code> koji će obrađivati elemente kolekcije
	 */
	void forEach(Processor processor) {
	}
	
	/**
	 * metoda u trenutnu kolekciju dodaje sve elemente zadane kolekcije
	 * 
	 * @param other kolekcija čije elemente treba dodati u trenutnu kolekciju
	 */
	void addAll(Collection other) {
		
		class localProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		localProcessor lp = new localProcessor();
		other.forEach(lp);
	}
	
	/**
	 * Metoda briše sve elemente iz kolekcije
	 */
	void clear() {
	}
}

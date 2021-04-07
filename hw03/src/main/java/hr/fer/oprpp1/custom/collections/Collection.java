package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje <code>Collection</code> predstavlja neku općenitu kolekciju objekata
 * 
 * @author Dorian Kablar
 *
 */
public interface Collection<T> {
	
	/**
	 * Apstraktna metoda vraća broj objekata u ovoj kolekciji
	 * 
	 * @return broj podataka u kolekciji
	 */
	int size();
	
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, u suprotnom <code>false</code>
	 */
	default boolean isEmpty() {
		if(this.size() == 0) return true;
		return false;
	}
	
	/**
	 * Apstraktna metoda koja dodaje objekt u kolekciju
	 * 
	 * @param value objekt koji treba dodati u kolekciju
	 */
	void add(T value);
	
	/**
	 * Apstraktna metoda koja provjerava nalazi li se zadani objekt u kolekciji
	 * 
	 * @param value zadani objekt koji se traži u kolekciji
	 * @return <code>true</code> ako se parametar nalazi u kolekciji, <code>false</code> inače
	 */
	boolean contains(Object value);
	
	/**
	 * Apstraktna metoda koja briše zadani objekt iz kolekcije, ako se taj objekt u njoj nalazi
	 * 
	 * @param value objekt kojeg treba obrisati iz kolekcije
	 * @return <code>true</code> ako se zadani objekt nalazi u kolekciji, <code>false</code> inače
	 */
	boolean remove(T value);
	
	/**
	 * Apstraktna metoda koja stvara novo polje, i puni ga s objektima iz kolekcije
	 * 
	 * @return novo polje popunjeno svim objektima kolekcije
	 */
	Object[] toArray();
	
	/**
	 * Apstraktna metoda koja zove <code>processor.process(.)</code> za svaki element kolekcije
	 * 
	 * @param processor <code>Processor</code> koji će obrađivati elemente kolekcije
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> elementsGetter = this.createElementsGetter();
		while(elementsGetter.hasNextElement()) {
			processor.process(elementsGetter.getNextElement());
		}
	}
	
	/**
	 * Metoda u trenutnu kolekciju dodaje sve elemente zadane kolekcije
	 * 
	 * @param other kolekcija čije elemente treba dodati u trenutnu kolekciju
	 */
	default void addAll(Collection<? extends T> other) {
		
		/**
		 * Razred <code>LocalProcessor</code> služi za definiranje procesora koji, ovisno o implementaciji, 
		 * na neki način obrađivati podatke. Implemetnira sučelje <code>Processor</code>
		 * 
		 * @author Dorian Kablar
		 *
		 */
		class LocalProcessor implements Processor<T>{
			
			@Override
			public void process(T value) {
				add(value);
			}

		}
		
		LocalProcessor lp = new LocalProcessor();
		other.forEach(lp);
	}
	
	/**
	 * Apstraktna metoda koja treba obrisati sve elemente iz kolekcije
	 */
	void clear();
	
	/**
	 * Apstraktna metoda koja stvara objekt koji će dohvaćati elemente kolekcije
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Apstraktna metoda koja u kolekciju dodaje sve objekte koji zadovoljavaju uvjete Testera
	 * 
	 * @param col kolekcija čije elemente treba ispitati i dodati u novu kolekciju
	 * @param tester "ispitivač", ispituje objekte iz kolekcije
	 */
	void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester);
}

package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje <code>ElementsGetter</code> koje predstavlja objekt koji može redom 
 * dohvaćati elemente kolekcije
 * 
 * @author Dorian Kablar
 *
 */
public interface ElementsGetter<T> {

	/**
	 * Apstraktna metoda koja provjerava postoji li sljedeći element u kolekciji
	 * 
	 * @return <code>true</code> ako postoji sljedeći element u kolekciji, <code>false</code> inače
	 */
	boolean hasNextElement();
	
	/**
	 * Apstraktna metoda koja dohvaća sljedeći element u kolekciji
	 * 
	 * @return sljedeći element iz kolekcije
	 * @throws NoSuchElementException ako nema više elemenata u kolekciji
	 */
	T getNextElement();
	
	/**
	 * Apstraktna metoda koja će za sve preostale elemente u kolekciji pozvati zadani Processor
	 * 
	 * @param p Processor koji će obraditi sve preostale elemente u kolekciji
	 */
	default void processRemaining(Processor<? super T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}

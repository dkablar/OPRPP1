package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje <code>Tester</code> modelira objekte koji prime neki objekt te ispitaju je li taj objekt
 * prihvatljiv ili ne
 * 
 * @author Dorian Kablar
 *
 */
public interface Tester<T> {
	
	/**
	 * Apstraktna metoda koja provjeravava je li objekt prihvatljiv ili ne
	 * 
	 * @param obj objekt za kojeg treba provjeriti prihvatljivost
	 * @return <code>true</code> ako je objekt prihvatljiv, <code>false</code> inače
	 */
	boolean test(T obj);

}

package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {

	/**
	 * Apstraktna metoda koja dohvaća element na zadanom indexu
	 * 
	 * @param index index elementa kojeg treba dohvatiti
	 * @return element koji je trebalo dohvatiti
	 */
	Object get(int index);
	
	/**
	 * Apstraktna metoda koja ubacuje element na zadanu poziciju u listi, elementi iza te pozicije
	 * se pomiču udesno
	 * 
	 * @param value element kojeg treba dodati u listu
	 * @param position pozicija na koju treba dodati element
	 */
	void insert(Object value, int position);
	
	/**
	 * Apstraktna metoda koja vraća poziciju zadanog elementa u kolekciji
	 * 
	 * @param value element čija se pozicija traži
	 * @return index elementa, ako se on nalazi u kolkeciji, -1 inače
	 */
	int indexOf(Object value);
	
	/**
	 * Apstraktna metoda koja briše element na zadanom indexu iz kolekcije
	 * 
	 * @param index index elementa kojeg treba obrisati
	 */
	void remove(int index);
}

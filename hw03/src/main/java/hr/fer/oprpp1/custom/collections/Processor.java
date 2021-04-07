package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje <code>Processor</code> omogućava obradu objekata
 * 
 * <p> Razred se koristi tako da se nad određenim objektom pozove njegova metoda
 * <code>process</code> koja obrađuje zadani objekt. </p>
 * 
 * @author Dorian Kablar
 *
 */
public interface Processor<T> {

	/**
	 * Apstraktna metoda koja prima objekt, te ga obrađuje
	 * 
	 * @param value objekt kojeg treba obraditi
	 */
	void process(T value);
}

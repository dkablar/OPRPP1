package hr.fer.oprpp1.custom.collections;

/**
 * Razred <code>ObjectStack</code> predstavlja kolekciju implementiranu kao stog.
 * Kao pomoć koristi klasu <code>ArrayIndexedCollection</code>
 * 
 * @author Dorian Kablar
 *
 */
public class ObjectStack<T> {

	private ArrayIndexedCollection<T> stack;
	
	/**
	 * Stvara novi <code>ObjectStack</code> čija je komponenta stack
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Metoda provjerava je li stog prazan
	 * 
	 * @return true ako je stog prazan, false inače
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Metoda vraća broj elemenata na stogu
	 * 
	 * @return broj elemenata na stogu
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Metoda stavlja element na vrh stoga
	 * 
	 * @param value element koju treba staviti na vrh stoga
	 * @throws ako je predan null
	 */
	public void push(T value) {
		if(value == null) throw new NullPointerException();
		stack.add(value);
	}
	
	/**
	 * Metoda briše i vraća element s vrha stoga
	 * 
	 * @return element koji je bio na vrhu stoga
	 */
	public T pop() {
		if(stack.size() == 0) throw new EmptyStackException("Stog je prazan!");
		T result = (T) stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return result;
	}
	
	/**
	 * Metoda vraća element s vrha stoga, ali ga ne briše
	 * 
	 * @return
	 */
	public T peek() {
		if(stack.size() == 0) throw new EmptyStackException("Stog je prazan!");
		return (T) stack.get(stack.size() - 1);
	}
	
	/**
	 * Metoda briše sve elemente sa stoga
	 */
	public void clear() {
		stack.clear();
	}
}

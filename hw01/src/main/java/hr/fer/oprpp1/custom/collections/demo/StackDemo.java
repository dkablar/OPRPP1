package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred <code>StackDemo</code> služi kao demonstracja razreda <code>ObjectStack</code>
 * 
 * @author Dorian Kablar
 *
 */
public class StackDemo {

	public static void main(String[] args) {
		
		if(args.length != 1) throw new IllegalArgumentException();
		String[] array = args[0].split(" ");
		
		ObjectStack stack = new ObjectStack();
		
		for(String s: array) {
			if(!s.equals("+") && !s.equals("-") && !s.equals("*") && !s.equals("/") && !s.equals("%")) {
				stack.push(s);
			} else {
				int p1 = Integer.parseInt(stack.pop().toString());
				int p2 = Integer.parseInt(stack.pop().toString());
				if(s.equals("+"))
					stack.push(p1 + p2);
				else if(s.equals("-"))
					stack.push(p2 - p1);
				else if(s.equals("*"))
					stack.push(p1 * p2);
				else if(s.equals("/")) {
					if(p1 == 0) break;
					stack.push(p2 / p1);
				} else
					stack.push(p2 % p1);
			}
		}
		if(stack.size() != 1)
			System.err.println("Izraz nije ispravno zadan.");
		else
			System.out.println("Expression evaluates to " + stack.pop());
	}
}

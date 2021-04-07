package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTester {

	@Test
	public void testLinkedListIndexedConstructorWithoutArguments() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		// metoda size bi trebala vratiti 0
		assertTrue(collection.size() == 0);
	}
	
	@Test
	public void testLinkedListIndexedCollectionWithOtherCollectionArgument() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("Kobe Bryant");
		other.add(24);
		other.add(8);
		other.add("Mamba");
		other.add("Michael Jordan");
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(other);
		// metoda size bi trebala vratiti 0
		assertTrue(collection.size() == 5);
	}
	
	@Test
	public void testSizeMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		// metoda size bi kao rezultat trebala vratiti 0
		assertTrue(collection.size() == 0);
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		// metoda size bi kao rezultat trebala vratiti 4
		assertTrue(collection.size() == 4);
	}
	
	@Test
	public void testIsEmptyMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		// metoda isEmpty bi kao rezultat trebala vratiti true
		assertTrue(collection.isEmpty());
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		//metoda isEmpty bi kao rezultat trebala vratiti false
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testAddMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		// metoda add bi trebala dodati element "Kobe Bryant" u kolekciju
		collection.add("Kobe Bryant");
		assertTrue(collection.get(0).equals("Kobe Bryant"));
		collection.add("Mamba");
		// metoda add bi trebala povećati veličinu kolekcije
		assertTrue(collection.size() == 2);
	}
	
	@Test
	public void testAddMethodShouldThrowNullPointerException() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(NullPointerException.class, () -> collection.add(null));
	}
	
	@Test
	public void testContainsMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		// metoda contains bi trebala vratiti true za element "Kobe Bryant"
		assertTrue(collection.contains("Kobe Bryant"));
		// metoda contains bi trebala vratiti false za element "LeBron James"
		assertFalse(collection.contains("LeBron James"));
	}
	
	@Test
	public void testRemoveObjectMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("LeBron James");
		// metoda size bi trebala vratiti 5
		assertTrue(collection.size() == 5);
		// metoda remove bi za argument 24 trebala vratiti true
		assertTrue(collection.remove((Object) 24));
		// metoda size bi trebala vratiti 4
		assertTrue(collection.size() == 4);
		Object[] expected = new Object[] {"Kobe Bryant", 8, "Mamba", "LeBron James"};
		// polje collection.toArray() i excepted bi trebali biti jednaki
		assertArrayEquals(collection.toArray(), expected);
	}
	
	@Test
	public void testToArrayMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("LeBron James");
		Object[] expected = new Object[] {"Kobe Bryant", 24, 8, "Mamba", "LeBron James"};
		// polje collection.toArray() i excepted bi trebali biti jednaki
		assertArrayEquals(collection.toArray(), expected);
	}
	
	@Test
	public void testForEachMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(15);
		collection.add(24);
		collection.add(8);
		collection.add(4);
		collection.add(6);
		
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		
		class localProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				other.add(value);
			}
		}
		
		localProcessor lp = new localProcessor();
		// metoda forEach bi svaki element iz collection trebala dodati u other
		collection.forEach(lp);
		
		// polja bi trebala biti jednaka
		assertArrayEquals(collection.toArray(), other.toArray());
	}
	
	@Test
	public void testAddAllMethod() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		// metoda addAll bi u kolekciju other trebala dodati sve elemente iz kolekcije collection
		other.addAll(collection);
		
		assertArrayEquals(collection.toArray(), other.toArray());
	}
	
	@Test
	public void testClearMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		// metoda size bi trebala vratiti 5
		assertTrue(collection.size() == 5);
		// metoda clear bi trebala isprazniti kolekciju
		collection.clear();
		// metoda size bi trebala vratiti 0
		assertTrue(collection.size() == 0);
	}
	
	@Test
	public void testGetMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		// metoda get bi trebala vratiti "Kobe Bryant"
		assertTrue(collection.get(0).equals("Kobe Bryant"));
		// metoda get bi trebala vratiti 8
		assertTrue(collection.get(2).equals(8));
		// metoda get bi trebala vratiti "Michael Jordan
		assertTrue(collection.get(4).equals("Michael Jordan"));
	}
	
	@Test
	public void testGetMethodShouldThrowIndexOutOFBoundsException() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(5));
	}
	
	@Test
	public void testInsertMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		// metoda size trebala bi vratiti 5
		assertTrue(collection.size() == 5);
		// metoda get za index 4 bi trebala vratiti "Michael Jordan"
		assertTrue(collection.get(4).equals("Michael Jordan"));
		
		collection.insert("Dirk Nowitzki", 4);
		
		// metoda size trebala bi vratiti 6
		assertTrue(collection.size() == 6);
		
		// metoda get za index 5 bi trebala vratiti "Michael Jordan"
		assertTrue(collection.get(5).equals("Michael Jordan"));
	}
	
	@Test
	public void testInsertMethodShouldThrowNullPointerException() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(NullPointerException.class, () -> collection.insert(null, 4));
	}
	
	@Test
	public void testInsertMethodShouldThrowIndexOutOfBoundsException() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("5", 16));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("5", -5));
	}
	
	@Test
	public void testIndexOfMethod() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		// metoda indexOf bi trebala vratiti 3
		assertTrue(collection.indexOf("Mamba") == 3);
		// metoda indexOf bi trebala vratiti -1
		assertTrue(collection.indexOf(null) == -1);
	}
	
	@Test
	public void testRemoveMethodIntArgument() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		collection.remove(4);
		// metoda size bi trebala vratiti 4
		assertTrue(collection.size() == 4);
	}
	
	@Test
	public void testRemoveMethodIntArgumentShouldThrowIndexOutOfBoundsException() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		// metoda remove za index 6 bi trebala izazvati IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(6));
		
		// metoda remove za index 6 bi trebala izazvati IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-15));
	}
}

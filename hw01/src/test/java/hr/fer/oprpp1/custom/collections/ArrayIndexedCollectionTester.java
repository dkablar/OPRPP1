package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTester {

	@Test
	public void testConstructorWithoutArguments() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		// veličina kolekcije bi trebala biti 0
		assertTrue(0 == collection.size());
		// kolekcija bi trebala biti prazna
		assertTrue(collection.isEmpty());
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);;
		// veličina kolekcije bi trebala biti 3
		assertTrue(collection.size() == 3);
		// kolekcija ne bi trebala biti prazna
		assertFalse(collection.isEmpty());
	}
	

	@Test
	public void testConstructorWithCapacityArgument() {
		int capacity = (int) Math.random() * 10 + 1;
		ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity);
		// veličina kolekcije bi trebala biti 0
		assertTrue(0 == collection.size());
		// kolekcija bi trebala biti prazna
		assertTrue(collection.isEmpty());
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);;
		// veličina kolekcije bi trebala biti 3
		assertTrue(collection.size() == 3);
		// kolekcija ne bi trebala biti prazna
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testConstructorWithCapacityArgumentShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection(-1);
		});
	}
	
	@Test
	public void testConstructorWithOtherCollectionArgument() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("Kobe Bryant");
		other.add(24);
		other.add(8);
		other.add("Mamba");
		ArrayIndexedCollection collection = new ArrayIndexedCollection(other);
		// veličina kolekcije bi trebala biti 0
		assertTrue(collection.size() == other.size());
		// kolekcija ne bi trebala biti prazna
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testConstructorWithOtherCollectionArgumentShouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection(null);
		});
	}
	
	@Test
	public void testConstructorWithCapacityAndOtherCollectionArguments() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		int capacity = (int) Math.random() * 10 + 1;
		other.add("Kobe Bryant");
		other.add(24);
		other.add(8);
		other.add("Mamba");
		other.add("Mentality");
		ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity, other);
		// other and collection should be the same size
		assertTrue(collection.size() == other.size());
		// kolekcija ne bi trebala biti prazna
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testSizeMethod() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		// metoda add bi trebala dodati element "Kobe Bryant" u kolekciju
		collection.add("Kobe Bryant");
		assertTrue(collection.get(0).equals("Kobe Bryant"));
		collection.add("Mamba");
		// metoda add bi trebala povećati veličinu kolekcije
		assertTrue(collection.size() == 2);
	}
	
	@Test
	public void testAddMethodShouldThrowNullPointerException() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(NullPointerException.class, () -> collection.add(null));
	}
	
	@Test
	public void testContainsMethod() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(15);
		collection.add(24);
		collection.add(8);
		collection.add(4);
		collection.add(6);
		
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
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
		
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		// metoda addAll bi u kolekciju other trebala dodati sve elemente iz kolekcije collection
		other.addAll(collection);
		
		assertArrayEquals(collection.toArray(), other.toArray());
	}
	
	@Test
	public void testClearMethod() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(5));
	}
	
	@Test
	public void testInsertMethod() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Kobe Bryant");
		collection.add(24);
		collection.add(8);
		collection.add("Mamba");
		collection.add("Michael Jordan");
		
		assertThrows(NullPointerException.class, () -> collection.insert(null, 4));
	}
	
	@Test
	public void testInsertMethodShouldThrowIndexOutOfBoundsException() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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

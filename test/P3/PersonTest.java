package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testPerson() {
		Person newperson =new Person("newperson");
		assertTrue(newperson.toString().equals("newperson"));
	}											



	@Test
	public void testaddFrom() {
		Person newPerson =new Person("Bob");
		Person anotherPerson = new Person("John");
		assertTrue(newPerson.addFrom(anotherPerson));
		assertTrue(anotherPerson.addFrom(newPerson));

	}

	@Test
	public void testgetFrom() {
		Person newPerson =new Person("Carl");
		Person anotherPerson = new Person("Dann");
		newPerson.addFrom(anotherPerson);
		anotherPerson.addFrom(newPerson);
		assertTrue(newPerson.equals(anotherPerson.getFrom()));

	}

	@Test
	public void testToStringPerson() {
		Person newPerson =new Person("Richard");
		String s ="Richard";
		assertTrue(s.equals(newPerson.toString()));

	}

	@Test
	public void testEqualsPerson() {
		Person newPerson =new Person("Tank");
		Person anotherPerson = new Person("Linda");
		assertTrue(anotherPerson.equals(anotherPerson));

	}
//
}

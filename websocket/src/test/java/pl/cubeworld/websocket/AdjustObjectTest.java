package pl.cubeworld.websocket;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import pl.cubeworld.websocket.AdjustObject;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;

import static org.hamcrest.Matchers.*;

public class AdjustObjectTest {

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnPerson() {
		//given
		List<Type> objects = new ArrayList<Type>();
		Collections.addAll(objects, Book.class, Person.class);
		String json = "{\"name\":\"Pawe¸ W«grzyÄski\",\"age\":23,\"surname\"=\"aaa\"}";
		
		Person expectedPerson = new Person();
		expectedPerson.setAge(23);
		expectedPerson.setName("Pawe¸ W«grzyÄski");
		
		//when
		AdjustObject adjuster = new AdjustObject(objects);
		
		Person person = (Person)adjuster.parse(json);
		
		//then
		assertThat(person, is(expectedPerson));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnBook() {
		//given
		List<Type> objects = new ArrayList<Type>();
		Collections.addAll(objects, Book.class, Person.class);
		String json = "{\"author\":\"Antoin Expiery\",\"title\":\"Little Princes\"}";
		
		Book expectedBook = new Book();
		expectedBook.setAuthor("Antoin Expiery");
		expectedBook.setTitle("Little Princes");
		
		//when
		AdjustObject adjuster = new AdjustObject(objects);
		Book book = (Book)adjuster.parse(json);
		
		//then
		assertThat(book, is(expectedBook));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnPersonRevert() {
		//given
		List<Type> objects = new ArrayList<Type>();
		Collections.addAll(objects, Person.class, Book.class);
		String json = "{\"name\":\"Pawe¸ W«grzyÄski\",\"age\":23,\"surname\"=\"aaa\"}";
		
		Person expectedPerson = new Person();
		expectedPerson.setAge(23);
		expectedPerson.setName("Pawe¸ W«grzyÄski");
		
		//when
		AdjustObject adjuster = new AdjustObject(objects);
		
		Person person = (Person)adjuster.parse(json);
		
		//then
		assertThat(person, is(expectedPerson));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnBookRevert() {
		//given
		List<Type> objects = new ArrayList<Type>();
		Collections.addAll(objects, Person.class, Book.class);
		String json = "{\"author\":\"Antoin Expiery\",\"title\":\"Little Princes\"}";
		
		Book expectedBook = new Book();
		expectedBook.setAuthor("Antoin Expiery");
		expectedBook.setTitle("Little Princes");
		
		//when
		AdjustObject adjuster = new AdjustObject(objects);
		Book book = (Book)adjuster.parse(json);
		
		//then
		assertThat(book, is(expectedBook));
	}
	
	@Ignore
	@Test
	public void gsonTest() throws Exception {
		Person person = new Person();
		person.setAge(23);
		person.setName("Pawe¸ W«grzyÄski");
		
		Gson gson = new Gson();
		String json = gson.toJson(person);
		
		System.out.println(json);
	}
	
	@Ignore
	@Test
	public void gsonToObjectTest() throws Exception {
		//given
		String json = "{\"name\":\"Pawe¸ W«grzyÄski\",\"age\":23}";
		
		Person expectedPerson = new Person();
		expectedPerson.setAge(23);
		expectedPerson.setName("Pawe¸ W«grzyÄski");
		
		//when
		Gson gson = new Gson();
		Person person = gson.fromJson(json, Person.class);
		
		//then
		assertThat(person, is(expectedPerson));
	}

}

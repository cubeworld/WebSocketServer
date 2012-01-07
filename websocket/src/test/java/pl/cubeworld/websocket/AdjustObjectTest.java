package pl.cubeworld.websocket;

import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;
import pl.cubeworld.websocket.entityResolver.AutoEntityResolver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AdjustObjectTest {

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnPerson() {
        //given
        List<Type> objects = new ArrayList<Type>();
        Collections.addAll(objects, Book.class, Person.class);
        String json = "{\"name\":\"Paweł Węgrzyński\",\"age\":23,\"surname\"=\"aaa\"}";

        Person expectedPerson = new Person();
        expectedPerson.setAge(23);
        expectedPerson.setName("Paweł Węgrzyński");

        //when
        AutoEntityResolver adjuster = new AutoEntityResolver(objects);

        Person person = (Person) adjuster.parse(json);

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
        AutoEntityResolver adjuster = new AutoEntityResolver(objects);
        Book book = (Book) adjuster.parse(json);

        //then
        assertThat(book, is(expectedBook));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnPersonRevert() {
        //given
        List<Type> objects = new ArrayList<Type>();
        Collections.addAll(objects, Person.class, Book.class);
        String json = "{\"name\":\"Pawe� W�grzy�ski\",\"age\":23,\"surname\"=\"aaa\"}";

        Person expectedPerson = new Person();
        expectedPerson.setAge(23);
        expectedPerson.setName("Pawe� W�grzy�ski");

        //when
        AutoEntityResolver adjuster = new AutoEntityResolver(objects);

        Person person = (Person) adjuster.parse(json);

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
        AutoEntityResolver adjuster = new AutoEntityResolver(objects);
        Book book = (Book) adjuster.parse(json);

        //then
        assertThat(book, is(expectedBook));
    }

    @Ignore
    @Test
    public void gsonTest() throws Exception {
        Person person = new Person();
        person.setAge(23);
        person.setName("Pawe� W�grzy�ski");

        Gson gson = new Gson();
        String json = gson.toJson(person);

        System.out.println(json);
    }

    @Ignore
    @Test
    public void gsonToObjectTest() throws Exception {
        //given
        String json = "{\"name\":\"Pawe� W�grzy�ski\",\"age\":23}";

        Person expectedPerson = new Person();
        expectedPerson.setAge(23);
        expectedPerson.setName("Pawe� W�grzy�ski");

        //when
        Gson gson = new Gson();
        Person person = gson.fromJson(json, Person.class);

        //then
        assertThat(person, is(expectedPerson));
    }

}

package pl.cubeworld.websocket.controllers;

import org.junit.Test;
import pl.cubeworld.websocket.annotation.Book;
import pl.cubeworld.websocket.annotation.Person;
import pl.cubeworld.websocket.entityResolver.ClassEntityResolver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ClassEntityResolverTest {

    @Test
    public void shouldReturnBook() {
        //given
        List<Type> entities = new ArrayList<Type>();
        entities.add(Book.class);
        entities.add(Person.class);

        String json = "{\"id\":\"Book\",\"data\":{\"author\":\"Author\",\"title\":\"Title\"}}";

        //when
        ClassEntityResolver resolver = new ClassEntityResolver(entities);
        Object parse = resolver.parse(json);

        //then
        assertThat(parse, instanceOf(Book.class));
        Book book = (Book) parse;
        assertThat(book.getAuthor(), equalTo("Author"));
        assertThat(book.getTitle(), equalTo("Title"));
    }
}

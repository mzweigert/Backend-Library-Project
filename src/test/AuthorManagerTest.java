package test;

import main.domain.Author;
import main.service.AuthorManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class AuthorManagerTest
{
    AuthorManager authorManager = new AuthorManager();
    Author author;


    @Test
    public void checkConnection()
    {
        assertNotNull(authorManager.getConnection());
    }
    @Test
    public void checkClearingAuthors()
    {

        assertEquals(authorManager.getAllAuthors().size(), 0);
    }

    @Test
    public void checkAddingAuthor()
    {

        assertEquals(authorManager.addAuthor(new Author("Andrzej", "Strzelba")), 1);
    }
    @Test
    public void checkDeletingAuthor()
    {

        authorManager.addAuthor(new Author("Mateusz", "Strzelba"));
        author = authorManager.getAllAuthors().get(0);
        assertEquals(authorManager.deleteAuthor(author) , 1);

    }
    @Test
    public void checkUpdatingAuthor()
    {
        authorManager.addAuthor(new Author("Mateusz", "Maklowicz"));
        author = authorManager.getAllAuthors().get(0);
        author.setName("Robert");

        assertEquals(authorManager.updateAuthor(author), 1);
        assertEquals(authorManager.getAllAuthors().get(0).getName(), author.getName());
    }

    @Test
    public void checkGettingAuthorBySurname()
    {
        List<Author> authors = new ArrayList<Author>();
        Author author = new Author("Siwy", "Lewy"); // Obiekt author bêdzie mial zapisane surname jako LEWY/ tym sie posluzymy do wydobycia autorow z nazwiskiem LEWY
        authorManager.addAuthor(author);
        authorManager.addAuthor(new Author("Czarny", "Lewy"));
        authorManager.addAuthor(new Author("Czerwony", "Lewy"));
        authorManager.addAuthor(new Author("Czerwony", "Czerwony"));


        authors = authorManager.getAuthorBySurname(author);

        assertEquals(authors.size(), 3);

        for(int i= 0; i<authors.size() ; i++)
            assertEquals(authors.get(i).getSurname(), "Lewy");

    }

    @Test
    public void checkGettingAuthorById()
    {

        Author authorFromDataBaseById;
        authorManager.addAuthor(new Author("Mariusz", "Buzianocnik"));
        author = authorManager.getAllAuthors().get(0);

        authorFromDataBaseById = authorManager.getAuthorById(author);

        assertEquals(author.getIdAuthor(), authorFromDataBaseById.getIdAuthor());
        assertEquals(author.getName(), authorFromDataBaseById.getName());
        assertEquals(author.getSurname(), authorFromDataBaseById.getSurname());
    }


    @After
    public void clearAll()
    {
        authorManager.clearAuthors();
    }
}

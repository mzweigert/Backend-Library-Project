package test;

import main.domain.Author;
import main.domain.Book;
import main.domain.BooksAuthors;
import main.service.AuthorManager;

import main.service.BookManager;
import main.service.BooksAuthorsManager;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class AuthorManagerTest
{
    AuthorManager authorManager = new AuthorManager();

    BookManager bookManager = new BookManager();
    BooksAuthorsManager booksAuthorsManager = new BooksAuthorsManager();

    BooksAuthors booksAuthors;
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
        author = new Author("Andrzej", "Strzelba");
        assertEquals(authorManager.addAuthor(author), 1);
        assertEquals(authorManager.getAllAuthors().size(), 1);
    }
    @Test
    public void checkDeletingAuthor()
    {
        author = new Author("Mateusz", "Strzelba");
        authorManager.addAuthor(author);
        author = authorManager.getAllAuthors().get(0);
        assertEquals(authorManager.deleteAuthor(author) , 1);
        assertFalse(authorManager.getAllAuthors().contains(author));

    }
    @Test
    public void checkUpdatingAuthor()
    {
        authorManager.addAuthor(new Author("Mateusz", "Maklowicz"));
        author = authorManager.getAllAuthors().get(0);
        author.setName("Robert");
        author.setSurname("Maklowiczowski");

        assertEquals(authorManager.updateAuthor(author), 1);
        assertEquals(authorManager.getAllAuthors().get(0).getName(), author.getName());
    }

    @Test
    public void checkGettingAuthorBooks()
    {

        booksAuthors = new BooksAuthors();

        authorManager.addAuthor(new Author("Mateusz", "Strzelba"));
        authorManager.addAuthor(new Author("Witek", "Witkowski"));

        bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("fajna", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("nie fajna", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("slaba", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("srednia", Date.valueOf("2000-01-01"), 1));


        //Dodawanie wierszy do tabeli BookAuthors
        booksAuthors.setIdAuthor(authorManager.getAllAuthors().get(0).getIdAuthor());
        booksAuthors.setIdBook(bookManager.getAllBooks().get(0).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);
        booksAuthors.setIdBook(bookManager.getAllBooks().get(1).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);
        booksAuthors.setIdBook(bookManager.getAllBooks().get(2).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);
        booksAuthors.setIdAuthor(authorManager.getAllAuthors().get(1).getIdAuthor());
        booksAuthors.setIdBook(bookManager.getAllBooks().get(2).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);
        booksAuthors.setIdBook(bookManager.getAllBooks().get(3).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);
        booksAuthors.setIdBook(bookManager.getAllBooks().get(4).getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);


        // TESTOWANIE GLOWNE
        int idBookFromGetBooksAuthor,
                idBookFromGetBooksAuthorsByIdAuthor;

        author = authorManager.getAllAuthors().get(0);
        //Testowanie polega na sprawdzaniu czy idBook pobranych ksi�zek danego autora z funkcji GetBooksAuthor jest taka sama jak pobranych bezposrednio z tabeli BookAuthors dla danego autora
        for(int i = 0 ; i<booksAuthorsManager.getBooksAuthorsByIdAuthor(author).size() ; i++)
        {
            idBookFromGetBooksAuthor = authorManager.getAuthorBooks(author).get(i).getIdBook();
            idBookFromGetBooksAuthorsByIdAuthor= booksAuthorsManager.getBooksAuthorsByIdAuthor(author).get(i).getIdBook();
            assertEquals(idBookFromGetBooksAuthor, idBookFromGetBooksAuthorsByIdAuthor);
        }
        assertEquals(authorManager.getAuthorBooks(author).size(), 3);


        //DLA DRUGIEGO AUTORA
        author = authorManager.getAllAuthors().get(1);
        for(int i = 0 ; i<booksAuthorsManager.getBooksAuthorsByIdAuthor(author).size() ; i++)
        {
            idBookFromGetBooksAuthor = authorManager.getAuthorBooks(author).get(i).getIdBook();
            idBookFromGetBooksAuthorsByIdAuthor= booksAuthorsManager.getBooksAuthorsByIdAuthor(author).get(i).getIdBook();
            assertEquals(idBookFromGetBooksAuthor, idBookFromGetBooksAuthorsByIdAuthor);
        }
        assertEquals(authorManager.getAuthorBooks(author).size(), 3);

        booksAuthorsManager.clearBooksAuthors();

    }
    @Test
    public void checkGettingAuthorBySurname()
    {
        List<Author> authors = new ArrayList<Author>();
        Author author = new Author("Siwy", "Lewy"); // Obiekt author b�dzie mial zapisane surname jako LEWY/ tym sie posluzymy do wydobycia autorow z nazwiskiem LEWY
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

    @Before
    public void clearAll()
    {
        authorManager.clearAuthors();
    }
}

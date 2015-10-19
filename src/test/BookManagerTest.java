package test;

import main.domain.Book;
import main.service.BookManager;
import org.junit.After;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by MATEUSZ on 2015-10-19.
 */
public class BookManagerTest
{
    BookManager bookManager = new BookManager();
    Book book;


    @Test
    public void checkConnection()
    {
        assertNotNull(bookManager.getConnection());
    }
    @Test
    public void checkClearingBooks()
    {
        assertEquals(bookManager.getAllBooks().size(), 0);
    }

    @Test
    public void checkAddingBook()
    {
        assertEquals(bookManager.addBook(new Book("Ferdydurke", Date.valueOf("1969-02-30"), 188)), 1);
    }
    @Test
    public void checkDeletingBook()
    {

        bookManager.addBook(new Book("Ksiazeczka fajna bardzo", Date.valueOf("2011-01-02"), 122));
        book = bookManager.getAllBooks().get(0);
        assertEquals(bookManager.deleteBook(book) , 1);

    }
    @Test
    public void checkUpdatingBook()
    {

        bookManager.addBook(new Book("Folwark Zwierzecy", Date.valueOf("1993-03-05"), 155));
        book = bookManager.getAllBooks().get(0);
        book.setTitle("1984");
        book.setRelaseDate(Date.valueOf("1949-06-08"));

        assertEquals(bookManager.updateBook(book), 1);
        assertEquals(bookManager.getAllBooks().get(0).getTitle(), "1984");
        assertEquals(bookManager.getAllBooks().get(0).getRelaseDate(), Date.valueOf("1949-06-08"));

    }

    @Test
    public void checkGettingBookByTitle()
    {
        List<Book> books = new ArrayList<Book>();


        bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-07-21"), 5));
        bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-03-11"), 325));
        bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-01-21"), 22));
        bookManager.addBook(new Book("JAKAS KSIAZKA", Date.valueOf("2011-03-02"), 1555));


        book = new Book();
        book.setTitle("Android programowanie");
        books = bookManager.getBookByTitle(book);
        assertEquals(books.size(), 3);

        for(int i= 0; i<books.size() ; i++)
            assertEquals(books.get(i).getTitle(), "Android programowanie");


    }

    @Test
    public void checkGettingBookById()
    {

        Book bookFromDataBase;
        bookManager.addBook(new Book("Pan Tadeusz", Date.valueOf("2000-05-06"), 155));
        bookManager.addBook(new Book("Pan T", Date.valueOf("2004-05-04"), 152));
        bookManager.addBook(new Book("Pan Tadek", Date.valueOf("2015-05-06"), 15));

        book = bookManager.getAllBooks().get(2);

        bookFromDataBase = bookManager.getBookById(book);

        assertEquals(book.getIdBook(), bookFromDataBase.getIdBook());
        assertEquals(book.getTitle(), "Pan Tadek");
        assertEquals(book.getRelaseDate(), Date.valueOf("2015-05-06"));
        assertEquals(book.getRelase(), 15);

    }


    @After
    public void clearAll()
    {
        bookManager.clearBooks();
    }
}

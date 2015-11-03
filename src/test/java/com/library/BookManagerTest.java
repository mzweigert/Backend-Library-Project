package com.library;


import com.library.domain.*;
import com.library.service.*;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by MATEUSZ on 2015-10-19.
 */
public class BookManagerTest
{
    BookManager bookManager = new BookManager();
    AuthorManager authorManager = new AuthorManager();
    ReaderManager readerManager = new ReaderManager();
    HiringManager hiringManager = new HiringManager();
    BooksAuthorsManager booksAuthorsManager = new BooksAuthorsManager();
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
        book.setRelase(5);

        assertEquals(bookManager.updateBook(book), 1);
        assertEquals(bookManager.getAllBooks().get(0).getTitle(), "1984");
        assertEquals(bookManager.getAllBooks().get(0).getRelaseDate(), Date.valueOf("1949-06-08"));
        assertEquals(bookManager.getAllBooks().get(0).getRelase(), 5);

    }

    @Test
    public void checkGettingBookAuthors()
    {
        BooksAuthors booksAuthors = new BooksAuthors();

        bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("Blok Ekipa", Date.valueOf("2000-01-01"), 1));

        authorManager.addAuthor(new Author("Mateusz", "Strzelba"));
        authorManager.addAuthor(new Author("Witek", "Witkowski"));
        authorManager.addAuthor(new Author("Wojtek", "Smieszek"));
        authorManager.addAuthor(new Author("Kasia", "Kasikowska"));
        authorManager.addAuthor(new Author("Spejson", "Spejson"));
        authorManager.addAuthor(new Author("Walo", "Walo"));
        authorManager.addAuthor(new Author("Wojtas", "Polonez"));


        //Dodawanie wierszy do tabeli
        booksAuthors.setIdBook(bookManager.getAllBooks().get(0).getIdBook());
        for(int i=0 ;i<4; i++)
        {
            booksAuthors.setIdAuthor(authorManager.getAllAuthors().get(i).getIdAuthor());
            booksAuthorsManager.addBooksAuthors(booksAuthors);
        }

        //tu dla drugiej ksiazki
        booksAuthors.setIdBook(bookManager.getAllBooks().get(1).getIdBook());
        for(int i=3 ;i<7; i++)
        {
            booksAuthors.setIdAuthor(authorManager.getAllAuthors().get(i).getIdAuthor());
            booksAuthorsManager.addBooksAuthors(booksAuthors);
        }


        // TESTOWANIE GLOWNE
        int idAuthorFromGettingBookAuthors,
                idAuthorFromGetBooksAuthorsByIdBook;

        book = bookManager.getAllBooks().get(0);
        //Testowanie polega na sprawdzaniu czy idAuthor pobranych autorow danych ksiazek z funkcji GetAuthorBooks jest taka sama jak pobranych bezposrednio z tabeli BookAuthors dla danej Ksiazki
        for(int i = 0 ; i<booksAuthorsManager.getBooksAuthorsByIdBook(book).size() ; i++)
        {
            idAuthorFromGettingBookAuthors= bookManager.getBookAuthors(book).get(i).getIdAuthor();
            idAuthorFromGetBooksAuthorsByIdBook= booksAuthorsManager.getBooksAuthorsByIdBook(book).get(i).getIdAuthor();
            assertEquals(idAuthorFromGettingBookAuthors, idAuthorFromGetBooksAuthorsByIdBook);
        }
        assertEquals(bookManager.getBookAuthors(book).size(), 4);


        //DLA DRUGIEj KSIAZKi
        book = bookManager.getAllBooks().get(1);
        //Testowanie polega na sprawdzaniu czy idAuthor pobranych autorow danych ksiazek z funkcji GetAuthorBooks jest taka sama jak pobranych bezposrednio z tabeli BookAuthors dla danej Ksiazki
        for(int i = 0 ; i<booksAuthorsManager.getBooksAuthorsByIdBook(book).size() ; i++)
        {
            idAuthorFromGettingBookAuthors = bookManager.getBookAuthors(book).get(i).getIdAuthor();
            idAuthorFromGetBooksAuthorsByIdBook= booksAuthorsManager.getBooksAuthorsByIdBook(book).get(i).getIdAuthor();
            assertEquals(idAuthorFromGettingBookAuthors, idAuthorFromGetBooksAuthorsByIdBook);
        }
        assertEquals(bookManager.getBookAuthors(book).size(), 4);


    }
    @Test
    public void checkGettingBookReaders()
    {
        Hiring hiring = new Hiring();

        bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("Blok Ekipa", Date.valueOf("2000-01-01"), 1));

        readerManager.addReader(new Reader("Mateusz", "Strzelba", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Witek", "Witkowski", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Wojtek", "Smieszek", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Kasia", "Kasikowska", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Spejson", "Miesnie", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Walo", "Mozg", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Wojtas", "Polonez", Date.valueOf("2011-01-01"), 100));


        //Dodawanie rekordow do tabeli Hiring
        hiring.setHireDate(Date.valueOf("2015-02-01"));
        hiring.setIdBook(bookManager.getAllBooks().get(0).getIdBook());
        for(int i = 0; i< 4; i++)
        {
            hiring.setIdReader(readerManager.getAllReaders().get(i).getIdReader());
            hiringManager.addHiring(hiring);
        }

        ///tu dodawanie rekordow dla drugiej ksiazki (Blok Ekipa)
        hiring.setIdBook(bookManager.getAllBooks().get(1).getIdBook());
        for(int i = 3; i< 7; i++)
        {
            hiring.setIdReader(readerManager.getAllReaders().get(i).getIdReader());
            hiringManager.addHiring(hiring);
        }

        // TESTOWANIE GLOWNE
        int idReaderFromGettingBookReaders,
                idReaderFromGetHiringByIdBook;

        book = bookManager.getAllBooks().get(0);
        //Testowanie polega na sprawdzaniu czy idReader pobranych czytelnikow danej Ksiazki z funkcji BookReader jest taka sama jak pobranych bezposrednio z tabeli Hiring dla danej Ksiazki
        for(int i = 0 ; i<hiringManager.getHiringsByIdBook(book).size() ; i++)
        {
            idReaderFromGettingBookReaders = bookManager.getBookReaders(book).get(i).getIdReader();
            idReaderFromGetHiringByIdBook= hiringManager.getHiringsByIdBook(book).get(i).getIdReader();
            assertEquals(idReaderFromGettingBookReaders, idReaderFromGetHiringByIdBook);
        }
        assertEquals(bookManager.getBookReaders(book).size(), 4);


        //DLA DRUGIEj KSIAZKi
        book = bookManager.getAllBooks().get(1);
        for(int i = 0 ; i<hiringManager.getHiringsByIdBook(book).size() ; i++)
        {
            idReaderFromGettingBookReaders = bookManager.getBookReaders(book).get(i).getIdReader();
            idReaderFromGetHiringByIdBook= hiringManager.getHiringsByIdBook(book).get(i).getIdReader();
            assertEquals(idReaderFromGettingBookReaders, idReaderFromGetHiringByIdBook);
        }
        assertEquals(bookManager.getBookReaders(book).size(), 4);


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


    @Before
    public void clearAll()
    {
        hiringManager.clearHirings();
        booksAuthorsManager.clearBooksAuthors();
        bookManager.clearBooks();
    }
}


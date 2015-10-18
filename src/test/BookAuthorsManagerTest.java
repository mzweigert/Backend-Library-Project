package test;

import main.domain.Author;
import main.domain.Book;
import main.domain.BookAuthors;
import main.service.AuthorManager;

import main.service.BookAuthorsManager;
import main.service.BookManager;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class BookAuthorsManagerTest
{
    AuthorManager authorManager = new AuthorManager();
    BookManager bookManager = new BookManager();
    BookAuthorsManager bookAuthorsManager = new BookAuthorsManager();

    Author author;
    Book book;
    BookAuthors bookAuthors;

    @Test
    public void checkConnection()
    {
        assertNotNull(bookAuthorsManager.getConnection());
    }

    /* @Test
     public void checkClearingBookAuthors()
     {
         bookAuthorsManager.clearBookAuthors();
         assertEquals(bookAuthorsManager.getAllBookAuthors().size(), 0);
     }*/

    /*@Test
    public void checkAddingBookAuthors()
    {
        bookAuthorsManager.clearBookAuthors();

        authorManager.clearAuthors();
        bookManager.clearBooks();

        assertEquals(authorManager.addAuthor(new Author("Mateusz", "Wajcheprzeloz")) , 1);
        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);



        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        bookAuthors = new BookAuthors(author.getIdAuthor(), book.getIdBook());


        assertEquals(bookAuthorsManager.addBookAuthors(bookAuthors), 1);
        assertEquals(bookAuthorsManager.getAllBookAuthors().get(0).getIdAuthor(), bookAuthors.getIdAuthor());
        assertEquals(bookAuthorsManager.getAllBookAuthors().get(0).getIdBook(), bookAuthors.getIdBook());




    }
    @Test
    public void checkDeletingBookAuthors()
    {
        bookAuthorsManager.clearBookAuthors();

        authorManager.clearAuthors();
        bookManager.clearBooks();

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);


        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        bookAuthors = new BookAuthors(author.getIdAuthor(), book.getIdBook());
        bookAuthorsManager.addBookAuthors(bookAuthors);

        assertEquals(bookAuthorsManager.deleteBookAuthors(bookAuthors), 1);

    }
    @Test
    public void checkUpdatingBookAuthors()
    {
        bookAuthorsManager.clearBookAuthors();
        authorManager.clearAuthors();
        bookManager.clearBooks();

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Nawet ok", Date.valueOf("2015-03-03"), 12)), 1);
        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        bookAuthors = new BookAuthors(author.getIdAuthor(), book.getIdBook());
        assertEquals(bookAuthorsManager.addBookAuthors(bookAuthors), 1); // Dodajemy rekord Autor: "Marny Koles" / Ksiazka: "Taka Sobie"



        //UPDATE'ujemy rekord "Marny koles" i "Taka Sobie" przypisujac mu ksiazke "Nawet ok"
        int countUpdated = bookAuthorsManager.updateBookAuthors(bookAuthors, bookAuthors.getIdAuthor(), bookManager.getAllBooks().get(1).getIdBook());
        assertEquals(countUpdated, 1);
        bookAuthors = bookAuthorsManager.getAllBookAuthors().get(0);
        assertNotEquals(book.getIdBook(), bookAuthors.getIdBook()); // CHECK, THAT idBook FROM bookAuthor is other than idBook from old book obj. IF TRUE - RECORD IS UPDATED

    }
*/
    @Test
    public void checkGettingBookAuthors()
    {
        bookAuthorsManager.clearBookAuthors();
        authorManager.clearAuthors();
        bookManager.clearBooks();

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1); //Sprawdzamy czy Autor poprawnie siê dodaje
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1); //Sprawdzamy czy Ksi¹zka poprawnie siê dodaje
        int idAuthor = authorManager.getAllAuthors().get(0).getIdAuthor();
        int idBook  = bookManager.getAllBooks().get(0).getIdBook();


        assertEquals(bookAuthorsManager.addBookAuthors(new BookAuthors(idAuthor, idBook)), 1); // Dodajemy rekord do tabeli bookAuthor

        bookAuthors =  bookAuthorsManager.getBookAuthors(idAuthor, idBook); // pobieramy rekord z bazy o idAutor Marnego Kolesia
                                                                                                 //  i idBook Takiej sobie ksi¹zki

        assertEquals(bookAuthors.getIdAuthor() , idAuthor); //Sprawdzanie idAuthor
        assertEquals(bookAuthors.getIdBook() , idBook);       //Sprawdzanie idBook



    }

    @Test
    public void checkGettingBookAuthorsByIdAuthor()
    {
        bookAuthorsManager.clearBookAuthors();
        authorManager.clearAuthors();
        bookManager.clearBooks();
        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Kiepska", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 1)) , 1);


         int idAuthor = authorManager.getAllAuthors().get(0).getIdAuthor();


        //TWORZENIE NOWYCH REKORDOW Z AUTOREM Marny Koles i Przypisywanie do niego wszystkich 4 ksiazek
        for(int i=0;i<4; i++)
            bookAuthorsManager.addBookAuthors(new BookAuthors(idAuthor, bookManager.getAllBooks().get(i).getIdBook()));

        //Sprawdzamy czy ilosc wierszy jest rowna 4 (tyle ile przypisalismy ksiazek do Marnego Kolesia)
        assertEquals(bookAuthorsManager.getBookAuthorsByIdAuthor(idAuthor).size(), 4);

        //Sprawdzamy czy idAuthor z obiektu author jest takie same jak idAuthor rekordu pobranego z bazy
        assertEquals(bookAuthorsManager.getAllBookAuthors().get(0).getIdAuthor(), idAuthor);



    }

    @Test
    public void checkGettingBookAuthorsByIdBook()
    {
        bookAuthorsManager.clearBookAuthors();
        authorManager.clearAuthors();
        bookManager.clearBooks();
        assertEquals(authorManager.addAuthor(new Author("Lubie", "Placki")) , 1);
        assertEquals(authorManager.addAuthor(new Author("dobry", "kotlet")) , 1);
        assertEquals(authorManager.addAuthor(new Author("Mateusz", "Zwege")) , 1);
        assertEquals(authorManager.addAuthor(new Author("Nie znam", "typa")) , 1);
        assertEquals(authorManager.addAuthor(new Author("kto", "to?")) , 1);

        assertEquals(bookManager.addBook(new Book("Spoko Ksiazka", Date.valueOf("2015-01-01"), 1)) , 1);

        int idBook = bookManager.getAllBooks().get(0).getIdBook();

        //TWORZENIE NOWYCH REKORDOW Z KSIAZKA "Spoko Ksiazka" i Przypisywanie do niej wszystkich 5 ksiazek
        for(int i=0;i<5; i++)
            bookAuthorsManager.addBookAuthors(new BookAuthors(authorManager.getAllAuthors().get(i).getIdAuthor(), idBook));

        //Sprawdzamy czy ilosc wierszy jest rowna 4 (tyle ile przypisalismy autorow do jednej SpokoKsiazki)

        assertEquals(bookAuthorsManager.getBookAuthorsByIdBook(idBook).size(), 5);

        //Sprawdzamy czy idBook  jest takie same jak idBook rekordu pobranego z bazy
        assertEquals(bookAuthorsManager.getAllBookAuthors().get(0).getIdBook(), idBook);



    }

}

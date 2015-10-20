package test;

import main.domain.Author;
import main.domain.Book;
import main.domain.BooksAuthors;
import main.service.AuthorManager;

import main.service.BooksAuthorsManager;
import main.service.BookManager;
import org.junit.After;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class BooksAuthorsManagerTest
{
    AuthorManager authorManager = new AuthorManager();
    BookManager bookManager = new BookManager();
    BooksAuthorsManager booksAuthorsManager = new BooksAuthorsManager();

    Author author;
    Book book;
    BooksAuthors booksAuthors;


    @Test
    public void checkConnection()
    {
        assertNotNull(booksAuthorsManager.getConnection());
    }

     @Test
     public void checkClearingBooksAuthors()
     {

         assertEquals(booksAuthorsManager.getAllBooksAuthors().size(), 0);
     }

    @Test
    public void checkAddingBooksAuthors()
    {

        assertEquals(authorManager.addAuthor(new Author("Mateusz", "Wajcheprzeloz")) , 1);
        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);



        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        booksAuthors = new BooksAuthors(author.getIdAuthor(), book.getIdBook());


        assertEquals(booksAuthorsManager.addBooksAuthors(booksAuthors), 1);
        assertEquals(booksAuthorsManager.getAllBooksAuthors().get(0).getIdAuthor(), booksAuthors.getIdAuthor());
        assertEquals(booksAuthorsManager.getAllBooksAuthors().get(0).getIdBook(), booksAuthors.getIdBook());




    }
    @Test
    public void checkDeletingBooksAuthors()
    {

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);


        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        booksAuthors = new BooksAuthors(author.getIdAuthor(), book.getIdBook());
        booksAuthorsManager.addBooksAuthors(booksAuthors);

        booksAuthors = booksAuthorsManager.getAllBooksAuthors().get(0); // pobieramy znowu rekord w celu wydobycia idBooksAuthors ktorego wygenerowala baza danych

        assertEquals(booksAuthorsManager.deleteBooksAuthors(booksAuthors), 1);

    }
    @Test
    public void checkUpdatingBooksAuthors()
    {

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(authorManager.addAuthor(new Author("Lolek", "Lolkowski")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Nawet ok", Date.valueOf("2015-03-03"), 12)), 1);

        author = authorManager.getAllAuthors().get(0);
        book = bookManager.getAllBooks().get(0);
        booksAuthors = new BooksAuthors(author.getIdAuthor(), book.getIdBook());
        assertEquals(booksAuthorsManager.addBooksAuthors(booksAuthors), 1); // Dodajemy rekord Autor: "Marny Koles" / Ksiazka: "Taka Sobie"



        booksAuthors = booksAuthorsManager.getAllBooksAuthors().get(0); // pobieramy rekord z tabeli BookAuthor

        booksAuthors.setIdBook(bookManager.getAllBooks().get(1).getIdBook()); // tutaj zmieniamy idBook z id ksiazki Taka Sobie na idBook Nawet ok
        booksAuthors.setIdAuthor(authorManager.getAllAuthors().get(1).getIdAuthor());


        //UPDATE'ujemy rekord "Marny koles" i "Taka Sobie" przypisujac mu ksiazke "Nawet ok" i autora Lolek Lolkowski
        assertEquals(booksAuthorsManager.updateBooksAuthors(booksAuthors), 1);

        booksAuthors = booksAuthorsManager.getAllBooksAuthors().get(0); // pobieramy rekord w celu weryfikacji
        assertNotEquals(book.getIdBook(), booksAuthors.getIdBook()); // sprawdzamy czy idBook pobranego wiersza jest takie samo jak
                                                                    // idBook ksi¹zki Nawet ok
        assertNotEquals(author.getIdAuthor(), booksAuthors.getIdAuthor());// a tu sprawdzamy idAuthora

    }

    @Test
    public void checkGettingBooksAuthorsById()
    {
        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1); //Sprawdzamy czy Autor poprawnie siê dodaje
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1); //Sprawdzamy czy Ksi¹zka poprawnie siê dodaje
        int idAuthor = authorManager.getAllAuthors().get(0).getIdAuthor();
        int idBook  = bookManager.getAllBooks().get(0).getIdBook();


        booksAuthors  = new BooksAuthors(idAuthor, idBook);
        assertEquals(booksAuthorsManager.addBooksAuthors(booksAuthors), 1); // Dodajemy rekord do tabeli bookAuthor
        booksAuthors.setIdBooksAuthors(booksAuthorsManager.getAllBooksAuthors().get(0).getIdBooksAuthors()); //pobieramy rekord ktory dodalismy w celu uzyskania ID
                                                                                                        // ( ktore wygenerowal sql, poniewaz tabela posiada Identity
                                                                                                        // przy id ), nastepnie ustawiamy id w naszym obiekcie

        booksAuthors =  booksAuthorsManager.getBooksAuthorsById(booksAuthors); //Pobieramy rekord z bazy

        assertEquals(booksAuthors.getIdAuthor() , idAuthor); //Sprawdzanie idAuthor
        assertEquals(booksAuthors.getIdBook() , idBook);       //Sprawdzanie idBook



    }

    @Test
    public void checkGettingBooksAuthorsByIdAuthor()
    {

        assertEquals(authorManager.addAuthor(new Author("Marny", "Koles")) , 1);
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Kiepska", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 1)) , 1);


        //pobranie idAuthor z 1 rekordu z tabeli Author ktory bedzie przypisany do 4 ksiazek w tabeli BooksAuthors
        author = authorManager.getAllAuthors().get(0);


        //TWORZENIE NOWYCH REKORDOW Z AUTOREM Marny Koles i Przypisywanie do niego wszystkich 4 ksiazek
        for(int i=0;i<4; i++)
            booksAuthorsManager.addBooksAuthors(new BooksAuthors(author.getIdAuthor(), bookManager.getAllBooks().get(i).getIdBook()));


        //Sprawdzamy czy ilosc wierszy jest rowna 4 (tyle ile przypisalismy ksiazek do Marnego Kolesia)
        assertEquals(booksAuthorsManager.getBooksAuthorsByIdAuthor(author).size(), 4);

        //Sprawdzamy czy idAuthor z obiektu author jest takie same jak idAuthor rekordu pobranego z bazy
        assertEquals(booksAuthorsManager.getAllBooksAuthors().get(0).getIdAuthor(), author.getIdAuthor());



    }

    @Test
    public void checkGettingBooksAuthorsByIdBook()
    {


        assertEquals(authorManager.addAuthor(new Author("Lubie", "Placki")) , 1);
        assertEquals(authorManager.addAuthor(new Author("dobry", "kotlet")) , 1);
        assertEquals(authorManager.addAuthor(new Author("Mateusz", "Zwege")) , 1);
        assertEquals(authorManager.addAuthor(new Author("Nie znam", "typa")) , 1);
        assertEquals(authorManager.addAuthor(new Author("kto", "to?")) , 1);

        assertEquals(bookManager.addBook(new Book("Spoko Ksiazka", Date.valueOf("2015-01-01"), 1)) , 1);

        book = bookManager.getAllBooks().get(0);


        //TWORZENIE NOWYCH REKORDOW Z KSIAZKA "Spoko Ksiazka" i Przypisywanie do niej wszystkich 5 Autorow
        for(int i=0;i<5; i++)
            booksAuthorsManager.addBooksAuthors(new BooksAuthors(authorManager.getAllAuthors().get(i).getIdAuthor(), book.getIdBook()));

        //Sprawdzamy czy ilosc wierszy jest rowna 5 (tyle ile przypisalismy autorow do jednej SpokoKsiazki)
        assertEquals(booksAuthorsManager.getBooksAuthorsByIdBook(book).size(), 5);


        //Sprawdzamy czy idBook  jest takie same jak idBook rekordu pobranego z bazy
        assertEquals(booksAuthorsManager.getAllBooksAuthors().get(0).getIdBook(), book.getIdBook());

    }


    @After
    public void clearAll()
    {
        booksAuthorsManager.clearBooksAuthors();
        authorManager.clearAuthors();
        bookManager.clearBooks();
    }

}

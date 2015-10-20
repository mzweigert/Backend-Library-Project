package test;


import main.domain.Book;
import main.domain.Reader;
import main.domain.Hiring;
import main.service.BookManager;

import main.service.HiringManager;

import main.service.ReaderManager;
import org.junit.After;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class HiringManagerTest
{

    BookManager bookManager = new BookManager();
    ReaderManager readerManager = new ReaderManager();
    HiringManager hiringManager = new HiringManager();

    Book book;
    Reader reader;
    Hiring hiring;


    @Test
    public void checkConnection()
    {
        assertNotNull(hiringManager.getConnection());
    }

    @Test
    public void checkClearingHiring()
    {
        hiringManager.clearHirings();
        assertEquals(hiringManager.getAllHirings().size(), 0);
    }

    @Test
    public void checkAddingHiring()
    {

        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(readerManager.addReader(new Reader("Mateusz", "Zweigert", Date.valueOf("2011-01-01"), 11)) , 1);


        reader = readerManager.getAllReaders().get(0);
        book = bookManager.getAllBooks().get(0);
        hiring = new Hiring(book.getIdBook(), reader.getIdReader(), Date.valueOf("2015-10-12"));

        //dodajemy rekord do bazy
        assertEquals(hiringManager.addHiring(hiring), 1);


        // pobieramy rekord i sprawdzamy czy jego wartosci sa zgodne z wartosciami obiektow dzieki ktoremu dodalismy ten rekord
        assertEquals(hiringManager.getAllHirings().get(0).getIdBook(), book.getIdBook());
        assertEquals(hiringManager.getAllHirings().get(0).getIdReader(), reader.getIdReader());
        assertEquals(hiringManager.getAllHirings().get(0).getHireDate(), hiring.getHireDate());



    }
    @Test
    public void checkDeletingHiring()
    {
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000)) , 1);

        book = bookManager.getAllBooks().get(0);
        reader = readerManager.getAllReaders().get(0);
        hiring = new Hiring(book.getIdBook(), reader.getIdReader(), Date.valueOf("2015-09-09"));

        //dodajemy rekord do bazy
        assertEquals(hiringManager.addHiring(hiring), 1);

        //sprawdzamy czy sie dodal, po dodaniu lista wszystkich rekordow w tabeli Hiring powinna wynosic 1
        assertEquals(hiringManager.getAllHirings().size(), 1);

        //pobieramy rekord hiring z bazy
        hiring = hiringManager.getAllHirings().get(0);

        //sprawdzamy czy funkcja zwroci 1 w przypadku powodzenia usuniecia
        assertEquals(hiringManager.deleteHiring(hiring), 1);

        //po usuniecia ilosc rekordow w tabeli Hiring powinna wynosic 0
        assertEquals(hiringManager.getAllHirings().size(), 0);


    }
    @Test
    public void checkUpdatingHiring()
    {
        ///////////////////////////////////////////////////////////////////
        // DODAWANIE REKORDOW BOOK I READER , ORAZ DODANIE 1 REKORDU HIRING KTOREGO BEDZIEMY UPDATE'OWAC
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 263)) , 1);
        assertEquals(bookManager.addBook(new Book("Kiepska", Date.valueOf("2015-01-01"), 236)) , 1);
        assertEquals(bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 234)) , 1);

        assertEquals(readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000)) , 1);

        book = bookManager.getAllBooks().get(0);
        reader = readerManager.getAllReaders().get(0);
        hiring = new Hiring(book.getIdBook(), reader.getIdReader(), Date.valueOf("2015-09-09"));
        assertEquals(hiringManager.addHiring(hiring), 1);
        //////////////////////////////////////////////////////////////////////

        //pobieramy rekord ktoremu chcemy cos zmienic
        hiring = hiringManager.getAllHirings().get(0);
        // poki co nasz rekord zapisany w hiring ma pole idBook ksi¹zki o tytule "Taka sobie".
        //Zmienmy mu zatem idKsiazki "Kiepska"

        int idBook = bookManager.getAllBooks().get(1).getIdBook();
        hiring.setIdBook(idBook);
        // Update'ujemy hiring ze zmienionym idBook
        assertEquals(hiringManager.updateHiring(hiring), 1);
        //sprawdzamy czy idBook rekordu bezposrednio pobranego z bazy jest roowne idBook ksiazki "Kiepska"
        assertEquals(hiringManager.getAllHirings().get(0).getIdBook(), idBook);

        // mozemy tez update'owac date wypozyczenia
        hiring.setHireDate(Date.valueOf("2002-01-01"));
        assertEquals(hiringManager.updateHiring(hiring), 1);
        //sprawdzamy czy data wypozyczenia sie zmienila
        assertEquals(hiringManager.getAllHirings().get(0).getHireDate(), Date.valueOf("2002-01-01"));
    }

    @Test
    public void checkGettingHiringById()
    {
        Hiring hiringFromDataBaseById;
        ///////////////////////////////////////////////////////////////////
        // DODAWANIE REKORDuBOOK I READER , ORAZ DODANIE 1 REKORDU HIRING z ktorego bedziemy korzystac
        assertEquals(bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 234)) , 1);
        assertEquals(readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000)) , 1);

        book = bookManager.getAllBooks().get(0);
        reader = readerManager.getAllReaders().get(0);
        hiring = new Hiring(book.getIdBook(), reader.getIdReader(), Date.valueOf("2015-09-09"));
        assertEquals(hiringManager.addHiring(hiring), 1); // Dodajemy rekord do tabeli bookbook
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //pobieramy rekord hiring z bazy ktory dodalismy
        hiring = hiringManager.getAllHirings().get(0);

        //ponownie go pobieramy tym razem przez odwolanie sie do jego id i przypisujemy do zmiennej w celu pozniejszego porownania ich wartosci
        hiringFromDataBaseById = hiringManager.getHiringById(hiring);

        assertEquals(hiring.getIdHiring(), hiringFromDataBaseById.getIdHiring());
        assertEquals(hiring.getIdBook(), hiringFromDataBaseById.getIdBook());
        assertEquals(hiring.getIdReader(), hiringFromDataBaseById.getIdReader());
        assertEquals(hiring.getHireDate(), hiringFromDataBaseById.getHireDate());
    }

    @Test
    public void checkGettingHiringByIdReader()
    {

        //DODAJEMY KSIAZKI, DUZO KSIAZEK
        assertEquals(bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Kiepska", Date.valueOf("2015-01-01"), 1)) , 1);
        assertEquals(bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 1)) , 1);

        //DODAJEMY CZYTELNIKOW
            assertEquals(readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000)) , 1);
            assertEquals(readerManager.addReader(new Reader("Kotlet", "Schabowy", Date.valueOf("2015-10-20"), 1000)) , 1);


        //Dodajemy 3 ksiazk
        for(int i=0;i<2; i++)
        {
            book = bookManager.getAllBooks().get(i);
            String data = "2011-0"+(i+1)+"-01";

            //przypisujemy do czytelnika Andrzeja Kolesia, 2 pierwsze ksiazki w tabali Hirings
            hiringManager.addHiring(new Hiring(book.getIdBook(), readerManager.getAllReaders().get(0).getIdReader(), Date.valueOf(data.toString())));
        }
        for(int i=2;i<4; i++)
        {
            book = bookManager.getAllBooks().get(i);
            String data = "2011-0"+(i+1)+"-01";
            //przypisujemy do czytelnika Kotleta schabowego, 3 i 4 ksiazke w tabali Hirings
            hiringManager.addHiring(new Hiring(book.getIdBook(), readerManager.getAllReaders().get(1).getIdReader(), Date.valueOf(data.toString())));
        }

        // w zmiennej reader pobieramy czytelnika Andrzeja Kolesia
        reader = readerManager.getAllReaders().get(0);
        //powinno zwrocic nam 2 wiersze, czyli tyle ksiazek ile jest przypissanych do Andrzeja Kolesia
        assertEquals(hiringManager.getHiringsByIdReader(reader).size(), 2);

        reader = readerManager.getAllReaders().get(1);
        //powinno zwrocic nam 2 wiersze, czyli tyle ksiazek ile jest przypissanych do Kotleta Schabowego
        assertEquals(hiringManager.getHiringsByIdReader(reader).size(), 2);


    }

    @Test
    public void checkGettingHiringByIdBook()
    {

        assertEquals(readerManager.addReader(new Reader("Duzy", "czlowiek", Date.valueOf("2015-10-20"), 2000)) , 1);
        assertEquals(readerManager.addReader(new Reader("Maly", "czlowiek", Date.valueOf("2015-10-20"), 1000)) , 1);
        assertEquals(readerManager.addReader(new Reader("Ogromny", "Schaboszczak", Date.valueOf("2015-10-20"), 1000)) , 1);


        assertEquals(bookManager.addBook(new Book("Jak polubic kluski slaskie?", Date.valueOf("2015-01-01"), 1)) , 1);

        book = bookManager.getAllBooks().get(0);


        //TWORZENIE NOWYCH REKORDOW Z KSIAZKA "Jak polubic kluski slaskie?" i Przypisywanie do niej wszystkich 3 czytelnikow
        for(int i=0;i<3; i++)
            hiringManager.addHiring(new Hiring(book.getIdBook(), readerManager.getAllReaders().get(i).getIdReader(), Date.valueOf("2015-10-20") ));

        //Sprawdzamy czy ilosc czytelnikow przypisanych do ksiazki jak polubic kluski slaskie jest rowna 3
        assertEquals(hiringManager.getHiringsByIdBook(book).size(), 3);

        //Sprawdzamy czy idBook  jest takie same jak idBook rekordu pobranego z bazy
        assertEquals(hiringManager.getAllHirings().get(0).getIdBook(), book.getIdBook());

    }


    @After
    public void clearAll()
    {
        hiringManager.clearHirings();
        bookManager.clearBooks();
        readerManager.clearReaders();
    }

}

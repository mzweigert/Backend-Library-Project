package com.library;

import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;
import com.library.service.BookManager;
import com.library.service.HiringManager;
import com.library.service.ReaderManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class ReaderManagerTest
{
    ReaderManager readerManager = new ReaderManager();
    BookManager bookManager = new BookManager();
    HiringManager hiringManager = new HiringManager();
    Reader reader;


    @Test
    public void checkConnection()
    {
        assertNotNull(readerManager.getConnection());
    }
    @Test
    public void checkClearingReaders()
    {

        readerManager.clearReaders();
        assertEquals(readerManager.getAllReaders().size(), 0);
    }

    @Test
    public void checkAddingReader()
    {

        assertEquals(readerManager.addReader(new Reader("Andrzej", "Strzelba", Date.valueOf("2013-05-21"), 200)), 1);
        assertEquals(readerManager.getAllReaders().size(), 1);
    }
    @Test
    public void checkDeletingReader()
    {

        assertEquals(readerManager.addReader(new Reader("Mateusz", "Strzelba", Date.valueOf("2001-01-02"),111)), 1);;
        reader = readerManager.getAllReaders().get(0);
        assertEquals(readerManager.getAllReaders().size(), 1);
        assertEquals(readerManager.deleteReader(reader) , 1);
        assertEquals(readerManager.getAllReaders().size(), 0);

    }
    @Test
    public void checkUpdatingReader()
    {
        readerManager.addReader(new Reader("Mateusz", "Maklowicz", Date.valueOf("2015-06-14"), 1500));
        reader = readerManager.getAllReaders().get(0);
        reader.setName("Robert");
        reader.setJoinDate(Date.valueOf("2011-01-11"));
        reader.setExtraPoints(100);


        assertEquals(readerManager.updateReader(reader), 1);
        assertEquals(readerManager.getAllReaders().get(0).getName(), "Robert");
        assertEquals(readerManager.getAllReaders().get(0).getJoinDate(), Date.valueOf("2011-01-11"));
        assertEquals(readerManager.getAllReaders().get(0).getExtraPoints(), 100);

    }

    @Test
    public void checkGettingReaderBooks()
    {
        Hiring hiring = new Hiring();

        readerManager.addReader(new Reader("Spejson", "Miesnie", Date.valueOf("2011-01-01"), 100));
        readerManager.addReader(new Reader("Walo", "Mozg", Date.valueOf("2011-01-01"), 100));

        bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("Blok Ekipa", Date.valueOf("2000-01-01"), 1));
        bookManager.addBook(new Book("Pan Tadeusz", Date.valueOf("2000-05-06"), 155));
        bookManager.addBook(new Book("Dziady", Date.valueOf("2004-05-04"), 152));
        bookManager.addBook(new Book("Aloes i inne rzeczy", Date.valueOf("2015-05-06"), 15));
        bookManager.addBook(new Book("Powstanie styczniowe", Date.valueOf("2015-05-06"), 15));


        //Dodawanie rekordow do tabeli Hiring
        hiring.setHireDate(Date.valueOf("2015-02-01"));
        hiring.setIdReader(readerManager.getAllReaders().get(0).getIdReader());
        for(int i = 0; i< 5; i++)
        {
            hiring.setIdBook(bookManager.getAllBooks().get(i).getIdBook());
            hiringManager.addHiring(hiring);
        }

        ///tu dodawanie rekordow dla drugiego Czytelnika Walo Mozg
        hiring.setIdReader(readerManager.getAllReaders().get(1).getIdReader());
        for(int i = 5; i>1; i--)
        {
            hiring.setIdBook(bookManager.getAllBooks().get(i).getIdBook());
            hiringManager.addHiring(hiring);
        }

        // TESTOWANIE GLOWNE
        int idBookFromGettingReaderBooks,
                idBookFromGetHiringsByIdReader;

        reader = readerManager.getAllReaders().get(0);
        //Testowanie polega na sprawdzaniu czy idBook pobranych ksiazek przypisanych do czytelnika z funkcji getReaderBooks
        // jest jest takie samo jak idBook pobranych bezposrednio z tabeli Hiring
        for(int i = 0 ; i<hiringManager.getHiringsByIdReader(reader).size() ; i++)
        {
            idBookFromGettingReaderBooks = readerManager.getReaderBooks(reader).get(i).getIdBook();
            idBookFromGetHiringsByIdReader= hiringManager.getHiringsByIdReader(reader).get(i).getIdBook();
            assertEquals(idBookFromGettingReaderBooks, idBookFromGetHiringsByIdReader);
        }
        assertEquals(readerManager.getReaderBooks(reader).size(), 5);


        //DLA DRUGIEGO CZYTELNIKA
        reader = readerManager.getAllReaders().get(1);
        for(int i = 0 ; i<hiringManager.getHiringsByIdReader(reader).size() ; i++)
        {
            idBookFromGettingReaderBooks = readerManager.getReaderBooks(reader).get(i).getIdBook();
            idBookFromGetHiringsByIdReader= hiringManager.getHiringsByIdReader(reader).get(i).getIdBook();
            assertEquals(idBookFromGettingReaderBooks, idBookFromGetHiringsByIdReader);
        }
        assertEquals(readerManager.getReaderBooks(reader).size(), 4);

        hiringManager.clearHirings();


    }

    @Test
    public void checkGettingReaderBySurname()
    {
        List<Reader> readers = new ArrayList<Reader>();
        reader = new Reader("Schabowy", "Kotlet", Date.valueOf("2005-09-09"), 1); // Obiekt Reader bedzie mial zapisane surname jako LEWY/ tym sie posluzymy do wydobycia autorow z nazwiskiem LEWY
        readerManager.addReader(reader);

        readerManager.addReader(new Reader("Meielony", "Kotlet", Date.valueOf("2005-09-09"), 1));
        readerManager.addReader(new Reader("Czerwony", "Lewy", Date.valueOf("2005-09-09"), 1));
        readerManager.addReader(new Reader("Karkowka", "Krwista", Date.valueOf("2005-09-09"), 1));


        readers = readerManager.getReadersBySurname(reader);

        assertEquals(readers.size(), 2);

        for(int i= 0; i<readers.size() ; i++)
            assertEquals(readers.get(i).getSurname(), "Kotlet");

    }

    @Test
    public void checkGettingReaderById()
    {

        Reader readerFromDataBaseById;
        readerManager.addReader(new Reader("Mariusz", "Buzianocnik", Date.valueOf("2005-09-09"), 1));
        reader = readerManager.getAllReaders().get(0);

        readerFromDataBaseById = readerManager.getReaderById(reader);

        assertEquals(reader.getIdReader(), readerFromDataBaseById.getIdReader());
        assertEquals(reader.getName(), readerFromDataBaseById.getName());
        assertEquals(reader.getSurname(), readerFromDataBaseById.getSurname());
    }


    @Before
    public void clearAll()
    {
        hiringManager.clearHirings();
        readerManager.clearReaders();
    }
}



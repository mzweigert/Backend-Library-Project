package test;

import main.domain.Reader;
import main.service.ReaderManager;

import org.junit.After;
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
    Reader reader;


    @Test
    public void checkConnection()
    {
        assertNotNull(readerManager.getConnection());
    }
    @Test
    public void checkClearingReaders()
    {

        assertEquals(readerManager.getAllReaders().size(), 0);
    }

    @Test
    public void checkAddingReader()
    {

        assertEquals(readerManager.addReader(new Reader("Andrzej", "Strzelba", Date.valueOf("2013-05-21"), 200)), 1);
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
    public void checkGettingReaderBySurname()
    {
        List<Reader> readers = new ArrayList<Reader>();
        reader = new Reader("Schabowy", "Kotlet", Date.valueOf("2005-09-09"), 1); // Obiekt Reader bêdzie mial zapisane surname jako LEWY/ tym sie posluzymy do wydobycia autorow z nazwiskiem LEWY
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


    @After
    public void clearAll()
    {
        readerManager.clearReaders();
    }
}

package main;

import main.domain.Book;
import main.domain.Reader;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface ReaderDAO
{
    List<Reader> getAllReaders();
    Reader getReaderById(Reader reader);
    List<Reader> getReadersBySurname(Reader reader);
    List<Book> getReaderBooks(Reader reader);
    int updateReader(Reader reader);
    int deleteReader(Reader reader);
    int addReader(Reader reader);

}

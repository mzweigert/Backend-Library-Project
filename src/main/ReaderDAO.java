package main;

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
    boolean updateReader(Reader reader);
    boolean deleteReader(Reader reader);
    boolean addReader(Reader reader);

}

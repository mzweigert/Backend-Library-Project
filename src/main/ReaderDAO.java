package main;

import main.domain.Reader;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface ReaderDAO
{
    List<Reader> getAllReaders();
    Reader getReaderById(int idReader);
    List<Reader> getReadersBySurname(String surname);
    boolean updateReader(Reader reader);
    boolean deleteReader(Reader reader);
    boolean addReader(Reader reader);

}

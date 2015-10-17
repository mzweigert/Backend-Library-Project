package main;

import main.domain.Reader;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface ReaderDAO
{
    int addReader(Reader reader);
    List<Reader> getAllReaders();

}

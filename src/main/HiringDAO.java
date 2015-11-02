package main;

import main.domain.Book;
import main.domain.Hiring;
import main.domain.Reader;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface HiringDAO
{
    List<Hiring> getAllHirings();
    Hiring getHiringById(Hiring hiring);
    List<Hiring> getHiringsByIdReader(Reader reader);
    List<Hiring> getHiringsByIdBook(Book book);
    int updateHiring(Hiring hiring);
    int deleteHiring(Hiring hiring);
    int addHiring(Hiring hiring);

}

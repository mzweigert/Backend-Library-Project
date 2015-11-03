package com.library;


import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;

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

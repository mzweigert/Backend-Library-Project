package main;


import main.domain.BookAuthors;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookAuthorsDAO
{

    int addBookAuthors(BookAuthors bookAuthors);

    List<BookAuthors> getAllBookAuthors();

}

package main;


import main.domain.Author;
import main.domain.Book;
import main.domain.BookAuthors;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookAuthorsDAO
{


    List<BookAuthors> getAllBookAuthors();
    List<BookAuthors> getBookAuthorsByIdAuthor(BookAuthors bookAuthors);
    List<BookAuthors> getBookAuthorsByIdBook(BookAuthors bookAuthors);
    BookAuthors getBookAuthors(BookAuthors bookAuthors);

    int updateBookAuthors(BookAuthors bookAuthors);
    int deleteBookAuthors(BookAuthors bookAuthors);

    int addBookAuthors(BookAuthors bookAuthors);





}

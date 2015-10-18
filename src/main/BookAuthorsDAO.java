package main;


import main.domain.Author;
import main.domain.BookAuthors;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookAuthorsDAO
{


    List<BookAuthors> getAllBookAuthors();
    List<BookAuthors> getBookAuthorsByIdAuthor(int idAuthor);
    List<BookAuthors> getBookAuthorsByIdBook(int idBook);
    BookAuthors getBookAuthors(int idAuthor, int idBook);

    int updateBookAuthors(BookAuthors bookAuthors, int idAuthor, int idBook);
    int deleteBookAuthors(BookAuthors bookAuthors);

    int addBookAuthors(BookAuthors bookAuthors);





}

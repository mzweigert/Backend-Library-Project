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

    boolean updateBookAuthors(BookAuthors bookAuthors);
    boolean deleteBookAuthors(BookAuthors bookAuthors);

    boolean addBookAuthors(BookAuthors bookAuthors);





}

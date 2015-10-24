package main;

import main.domain.Author;
import main.domain.Book;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface AuthorDAO
{
    List<Author> getAllAuthors();
    Author getAuthorById(Author author);
    List<Author> getAuthorBySurname(Author author);
    List<Book> getBooksAuthor(Author author);
    int updateAuthor(Author author);
    int deleteAuthor(Author author);
    int addAuthor(Author author);

}

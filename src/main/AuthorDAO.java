package main;

import main.domain.Author;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface AuthorDAO
{
    List<Author> getAllAuthors();
    Author getAuthorById(int idAuthor);
    List<Author> getAuthorBySurname(String surname);
    int updateAuthor(Author author);
    int deleteAuthor(Author author);
    int addAuthor(Author author);

}

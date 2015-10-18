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
    boolean updateAuthor(Author author);
    boolean deleteAuthor(Author author);
    boolean addAuthor(Author author);


}

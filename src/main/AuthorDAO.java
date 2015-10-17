package main;

import main.domain.Author;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface AuthorDAO
{
    void clearAuthors();

    public int addAuthor(Author author);

    public List<Author> getAllAuthors();

}

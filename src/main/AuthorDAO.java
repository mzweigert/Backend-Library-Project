package main;

import main.domain.Author;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface AuthorDAO
{

    int addAuthor(Author author);
    List<Author> getAllAuthors();

}

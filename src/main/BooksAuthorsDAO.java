package main;


import main.domain.Author;
import main.domain.Book;
import main.domain.BooksAuthors;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BooksAuthorsDAO
{


    List<BooksAuthors> getAllBooksAuthors();
    List<BooksAuthors> getBooksAuthorsByIdAuthor(Author author);
    List<BooksAuthors> getBooksAuthorsByIdBook(Book book);
    BooksAuthors getBooksAuthorsById(BooksAuthors booksAuthors);
    int updateBooksAuthors(BooksAuthors booksAuthors);
    int deleteBooksAuthors(BooksAuthors booksAuthors);
    int addBooksAuthors(BooksAuthors booksAuthors);

}

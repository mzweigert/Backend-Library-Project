package main;

import main.domain.Book;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface BookDAO
{
    void clearBooks();

    public int addBook(Book book);

    public List<Book> getAllBooks();


}

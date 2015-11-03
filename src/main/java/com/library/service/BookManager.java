package com.library.service;



import com.library.BookDAO;
import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BookManager implements BookDAO
{

    private Connection connection;

    private String url = "jdbc:jtds:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableBook = "CREATE TABLE  Book ( idBook INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1), title VARCHAR(70) NOT NULL, relase_date Date not null, relase VARCHAR(15) not null);";

    private PreparedStatement getAllBooksStmt;
    private PreparedStatement getBookByIdStmt;
    private PreparedStatement getBookByTitleStmt;
    private PreparedStatement updateBookStmt;
    private PreparedStatement deleteBookStmt;
    private PreparedStatement addBookStmt;
    private PreparedStatement getBookAuthorsStmt;
    private PreparedStatement getBookReadersStmt;

    private PreparedStatement deleteAllBooksStmt;

    private Statement statement;

    public BookManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("Book".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableBook);

            getAllBooksStmt = connection.prepareStatement("SELECT idBook, title, relase_date, relase FROM Book");
            getBookByIdStmt = connection.prepareStatement("SELECT * FROM Book WHERE idBook = ?");
            getBookByTitleStmt = connection.prepareStatement("SELECT * FROM Book WHERE title = ?");
            updateBookStmt = connection.prepareStatement("UPDATE Book SET title = ?, relase_date = ?, relase = ? WHERE idBook = ?");
            deleteBookStmt = connection.prepareStatement("DELETE Book WHERE idBook = ? ");
            addBookStmt = connection.prepareStatement("INSERT INTO Book (title, relase_date, relase) VALUES (?, ?, ?)");
            getBookAuthorsStmt = connection.prepareStatement("Select Author.idAuthor, Author.name, Author.surname from Author "+
                                                             "inner join BooksAuthors on Author.idAuthor=BooksAuthors.idAuthor Where BooksAuthors.idBook = ?;");

            getBookReadersStmt = connection.prepareStatement("Select Reader.idReader, Reader.name, Reader.surname, Reader.join_date, Reader.extra_points from Reader "+
                                                             "inner join Hiring on Reader.idReader = Hiring.idReader where Hiring.idBook = ? ;");


            deleteAllBooksStmt = connection.prepareStatement("DELETE FROM Book");


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }


    @Override
    public List<Book> getAllBooks()
    {
        List<Book> books = new ArrayList<Book>();
        Book book;
        try
        {
            ResultSet rs = getAllBooksStmt.executeQuery();

            while (rs.next())
            {
                book = new Book(rs.getString("title"), rs.getDate("relase_date"), rs.getInt("relase"));
                book.setIdBook(rs.getInt("idBook"));
                books.add(book);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return books;
    }
    @Override
    public List<Author> getBookAuthors(Book book)
    {
        List<Author> authorsBook = new ArrayList<Author>();
        Author author;
        try
        {
            getBookAuthorsStmt.setInt(1, book.getIdBook());
            ResultSet rs = getBookAuthorsStmt.executeQuery();

            while (rs.next())
            {
                author = new Author(rs.getString("name"), rs.getString("surname"));
                author.setIdAuthor(rs.getInt("idAuthor"));
                authorsBook.add(author);
            }

            return authorsBook;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<Reader> getBookReaders(Book book)
    {
        List<Reader> bookReaders = new ArrayList<Reader>();
        Reader reader;
        try
        {
            getBookReadersStmt.setInt(1, book.getIdBook());
            ResultSet rs = getBookReadersStmt.executeQuery();

            while (rs.next())
            {
                reader = new Reader(rs.getString("name"), rs.getString("surname"), rs.getDate("join_date"), rs.getInt("extra_points"));
                reader.setIdReader(rs.getInt("idReader"));
                bookReaders.add(reader);
            }

            return bookReaders;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Book getBookById(Book book)
    {

        try
        {
            getBookByIdStmt.setInt(1, book.getIdBook());
            ResultSet rs = getBookByIdStmt.executeQuery();

            while (rs.next())
            {
                book = new Book(rs.getString("title"), rs.getDate("relase_date"), rs.getInt("relase"));
                book.setIdBook(rs.getInt("idBook"));
                return book;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Book> getBookByTitle(Book book)
    {
        List<Book> booksByTitle = new ArrayList<Book>();

        try
        {
            getBookByTitleStmt.setString(1, book.getTitle());
            ResultSet rs = getBookByTitleStmt.executeQuery();

            while (rs.next())
            {
                book = new Book(rs.getString("title"), rs.getDate("relase_date"), rs.getInt("relase"));
                book.setIdBook(rs.getInt("idBook"));
                booksByTitle.add(book);
            }

            return booksByTitle;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int updateBook(Book book)
    {
        int count = 0;
        try
        {
            updateBookStmt.setString(1, book.getTitle());
            updateBookStmt.setDate(2, book.getRelaseDate());
            updateBookStmt.setInt(3, book.getRelase());
            updateBookStmt.setInt(4, book.getIdBook());

            count = updateBookStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteBook(Book book)
    {
        int count = 0;
        try
        {
            deleteBookStmt.setInt(1, book.getIdBook());
            count = deleteBookStmt.executeUpdate();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return count;
    }
    @Override
    public int addBook(Book Book)
    {
        int count = 0;
        try
        {
            addBookStmt.setString(1, Book.getTitle());
            addBookStmt.setDate(2, Book.getRelaseDate());
            addBookStmt.setInt(3, Book.getRelase());


            count = addBookStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void clearBooks()
    {
        try
        {
            deleteAllBooksStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}

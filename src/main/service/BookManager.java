package main.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.domain.Book;

public class BookManager
{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/mzweigert";

    private String createTableBook = "CREATE TABLE Book(idBook bigint GENERATED BY DEFAULT AS IDENTITY, title varchar(20), relase_date Date, relase int)";

    private PreparedStatement addBookStmt;
    private PreparedStatement deleteAllBooksStmt;
    private PreparedStatement getAllBooksStmt;

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

            addBookStmt = connection.prepareStatement("INSERT INTO Book (title, relase_date, relase) VALUES (?, ?, ?)");
            deleteAllBooksStmt = connection.prepareStatement("DELETE FROM Book");
            getAllBooksStmt = connection.prepareStatement("SELECT idBook, title, relase_date, relase FROM Book");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    Connection getConnection()
    {
        return connection;
    }

    void clearBooks()
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

    public List<Book> getAllBooks()
    {
        List<Book> books = new ArrayList<Book>();

        try
        {
            ResultSet rs = getAllBooksStmt.executeQuery();

            while (rs.next())
            {
                Book book = new Book(rs.getInt("idBook"), rs.getString("title"),  rs.getDate("relase_date"), rs.getInt("relase"));
                books.add(book);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return books;
    }

}
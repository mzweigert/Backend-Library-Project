package com.library.service;


import com.library.AuthorDAO;
import com.library.domain.Author;
import com.library.domain.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class AuthorManager implements AuthorDAO
{

    private Connection connection;

    private String url = "jdbc:jtds:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableAuthor = "CREATE TABLE  Author (idAuthor INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1), name VARCHAR(70) NOT NULL, surname VARCHAR(30) NOT NULL);";


    private PreparedStatement getAllAuthorsStmt;
    private PreparedStatement getAuthorByIdStmt;
    private PreparedStatement getAuthorBySurnameStmt;
    private PreparedStatement updateAuthorStmt;
    private PreparedStatement deleteAuthorStmt;
    private PreparedStatement addAuthorStmt;
    private PreparedStatement getAuthorBooksStmt;
    private PreparedStatement deleteAllAuthorsStmt;



    private Statement statement;

    public AuthorManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("Author".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableAuthor);

            getAllAuthorsStmt = connection.prepareStatement("SELECT idAuthor, name, surname FROM Author");

            getAuthorByIdStmt = connection.prepareStatement("SELECT * FROM Author WHERE idAuthor = ?");
            getAuthorBySurnameStmt = connection.prepareStatement("SELECT * FROM Author WHERE surname = ?");
            updateAuthorStmt = connection.prepareStatement("UPDATE Author SET name=?, surname=? WHERE idAuthor = ?");
            deleteAuthorStmt = connection.prepareStatement("DELETE Author WHERE idAuthor = ?");
            addAuthorStmt = connection.prepareStatement("INSERT INTO Author (name, surname) VALUES (?, ?)");
            getAuthorBooksStmt = connection.prepareStatement("Select Book.idBook, Book.title, Book.relase_date, Book.relase from Book "+
                                                             "inner join BooksAuthors on Book.idBook = BooksAuthors.idBook "+
                                                             "where BooksAuthors.idAuthor = ? ;");
            deleteAllAuthorsStmt = connection.prepareStatement("DELETE FROM Author");

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
    public List<Author> getAllAuthors()
    {
        List<Author> authors = new ArrayList<Author>();
        Author author;
        try
        {
            ResultSet rs = getAllAuthorsStmt.executeQuery();

            while (rs.next())
            {
                author = new Author( rs.getString("name"), rs.getString("surname"));
                author.setIdAuthor(rs.getInt("idAuthor"));
                authors.add(author);

            }
            return authors;

        }
        catch (SQLException e)
        {

            e.printStackTrace();
            return null;
        }

    }
    @Override
    public  List<Book> getAuthorBooks(Author author)
    {
        List<Book> booksAuthor = new ArrayList<Book>();
        Book book;
        try
        {
            getAuthorBooksStmt.setInt(1, author.getIdAuthor());
            ResultSet rs = getAuthorBooksStmt.executeQuery();

            while (rs.next())
            {
                book = new Book(rs.getString("title"), rs.getDate("relase_date"), rs.getInt("relase"));
                book.setIdBook(rs.getInt("idBook"));
                booksAuthor.add(book);
            }

            return booksAuthor;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Author getAuthorById(Author author)
    {

        try
        {
            getAuthorByIdStmt.setInt(1, author.getIdAuthor());
            ResultSet rs = getAuthorByIdStmt.executeQuery();

            while (rs.next())
            {
                author = new Author(rs.getString("name"), rs.getString("surname"));
                author.setIdAuthor(rs.getInt("idAuthor"));
                return author;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<Author> getAuthorBySurname(Author author)
    {
        List<Author> authorsBySurname = new ArrayList<Author>();

        try
        {
            getAuthorBySurnameStmt.setString(1, author.getSurname());
            ResultSet rs = getAuthorBySurnameStmt.executeQuery();

            while (rs.next())
            {
                author = new Author(rs.getString("name"), rs.getString("surname"));
                author.setIdAuthor(rs.getInt("idAuthor"));
                authorsBySurname.add(author);
            }

            return authorsBySurname;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public int updateAuthor(Author author)
    {
        int count = 0;
        try
        {
            updateAuthorStmt.setString(1, author.getName());
            updateAuthorStmt.setString(2, author.getSurname());
            updateAuthorStmt.setInt(3, author.getIdAuthor());

            count = updateAuthorStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int deleteAuthor(Author author)
    {
        int count = 0;
        try
        {
            deleteAuthorStmt.setInt(1, author.getIdAuthor());
            count = deleteAuthorStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return count;
    }
    @Override
    public int addAuthor(Author author)
    {
        int count = 0;
        try
        {
            addAuthorStmt.setString(1, author.getName());
            addAuthorStmt.setString(2, author.getSurname());

            count = addAuthorStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
        return count;
    }
    public void clearAuthors()
    {
        try
        {
            deleteAllAuthorsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}

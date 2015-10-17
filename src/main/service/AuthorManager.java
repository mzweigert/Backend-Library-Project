package main.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.domain.Author;

public class AuthorManager
{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/mzweigert";

    private String createTableAuthor = "CREATE TABLE Author(idAuthor bigint GENERATED BY DEFAULT AS IDENTITY, name varchar(20), surname varchar(20))";

    private PreparedStatement addAuthorStmt;
    private PreparedStatement deleteAllAuthorsStmt;
    private PreparedStatement getAllAuthorsStmt;

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

            addAuthorStmt = connection.prepareStatement("INSERT INTO Author (name, surname) VALUES (?, ?)");
            deleteAllAuthorsStmt = connection.prepareStatement("DELETE FROM Author");
            getAllAuthorsStmt = connection.prepareStatement("SELECT idAuthor, name, surname FROM Author");

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

    void clearAuthors()
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

    public List<Author> getAllAuthors()
    {
        List<Author> authors = new ArrayList<Author>();

        try
        {
            ResultSet rs = getAllAuthorsStmt.executeQuery();

            while (rs.next())
            {
                Author author= new Author(rs.getInt("idAuthor"), rs.getString("name"), rs.getString("surname"));
                authors.add(author);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return authors;
    }

}
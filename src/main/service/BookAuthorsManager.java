package main.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.BookAuthorsDAO;
import main.domain.BookAuthors;

public class BookAuthorsManager implements BookAuthorsDAO
{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/mzweigert";

    private String createTableBookAuthors= "CREATE TABLE BookAuthors(FOREIGN KEY REFERENCES Author(idAuthor),"+
                                                               "FOREIGN KEY REFERENCES Book(idBook),"+
                                                               "Primary Key (idAuthor, idBook))";

    private PreparedStatement addBookAuthorstmt;
    private PreparedStatement deleteAllBookAuthorsStmt;
    private PreparedStatement getAllBookAuthorsStmt;

    private Statement statement;

    public BookAuthorsManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("BookAuthors".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableBookAuthors);

            addBookAuthorstmt = connection.prepareStatement("INSERT INTO BookAuthors(idAuthor, idBook) VALUES (?, ?)");
            deleteAllBookAuthorsStmt = connection.prepareStatement("DELETE FROM BookAuthors");
            getAllBookAuthorsStmt = connection.prepareStatement("SELECT  idAuthor, idBook FROM BookAuthors");

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

    void clearBookAuthors()
    {
        try
        {
            deleteAllBookAuthorsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int addBookAuthors(BookAuthors bookAuthors)
    {
        int count = 0;
        try
        {
            addBookAuthorstmt.setInt(1, bookAuthors.getIdAuthor());
            addBookAuthorstmt.setInt(2, bookAuthors.getIdBook());

            count = addBookAuthorstmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public List<BookAuthors> getAllBookAuthors()
    {
        List<BookAuthors> bookAuthors = new ArrayList<BookAuthors>();

        try
        {
            ResultSet rs = getAllBookAuthorsStmt.executeQuery();

            while (rs.next())
            {
                BookAuthors ba= new BookAuthors(rs.getInt("idAuthor"), rs.getInt("idBook"));
                        bookAuthors.add(ba);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return bookAuthors;
    }

}
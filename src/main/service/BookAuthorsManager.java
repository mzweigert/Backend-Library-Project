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
import main.domain.Author;
import main.domain.BookAuthors;

public class BookAuthorsManager implements BookAuthorsDAO
{

    private Connection connection;

    private String url = "jdbc:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableBookAuthors= "CREATE TABLE BookAuthors(FOREIGN KEY REFERENCES Author(idAuthor),"+
                                                               "FOREIGN KEY REFERENCES Book(idBook),"+
                                                               "Primary Key (idAuthor, idBook))";
    private PreparedStatement getAllBookAuthorsStmt;
    private PreparedStatement getBookAuthorsByIdAuthorStmt;
    private PreparedStatement getBookAuthorsByIdBookStmt;
    private PreparedStatement getBookAuthorsStmt;
    private PreparedStatement updateBookAuthorsStmt;
    private PreparedStatement deleteBookAuthorsStmt;
    private PreparedStatement addBookAuthorsStmt;

    private PreparedStatement deleteAllBookAuthorsStmt;


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

            getAllBookAuthorsStmt = connection.prepareStatement("SELECT  idAuthor, idBook FROM BookAuthors");
            getBookAuthorsByIdAuthorStmt = connection.prepareStatement("SELECT * FROM BookAuthors WHERE idAuthor = ?");
            getBookAuthorsByIdBookStmt = connection.prepareStatement("SELECT * FROM BookAuthors WHERE idBook = ?");
            getBookAuthorsStmt = connection.prepareStatement("SELECT * FROM BookAuthors WHERE idAuthor = ? AND idBook = ?");
            updateBookAuthorsStmt = connection.prepareStatement("UPDATE BookAuthors SET idAuthor = ?, idBook = ? WHERE idAuthor = ? AND idBook = ?");
            deleteBookAuthorsStmt = connection.prepareStatement("DELETE BookAuthors WHERE idAuthor = ? AND idBook = ?");

            addBookAuthorsStmt = connection.prepareStatement("INSERT INTO BookAuthors(idAuthor, idBook) VALUES (?, ?)");
            deleteAllBookAuthorsStmt = connection.prepareStatement("DELETE FROM BookAuthors");

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

    @Override
    public List<BookAuthors>  getBookAuthorsByIdAuthor(int idAuthor)
    {
        List<BookAuthors> bookAuthorsByIdAuthor = new ArrayList<BookAuthors>();

        try
        {
            getBookAuthorsByIdAuthorStmt.setInt(1, idAuthor);
            ResultSet rs = getBookAuthorsByIdAuthorStmt.executeQuery();

            while (rs.next())
            {
                bookAuthorsByIdAuthor.add(new BookAuthors(rs.getInt("idAuthor"), rs.getInt("idBook")));
            }

            return bookAuthorsByIdAuthor;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<BookAuthors> getBookAuthorsByIdBook(int idBook)
    {
        List<BookAuthors> bookAuthorsByIdBook = new ArrayList<BookAuthors>();

        try
        {
            getBookAuthorsByIdBookStmt.setInt(1, idBook);
            ResultSet rs = getBookAuthorsByIdBookStmt.executeQuery();

            while (rs.next())
            {
                bookAuthorsByIdBook.add(new BookAuthors(rs.getInt("idAuthor"), rs.getInt("idBook")));
            }

            return bookAuthorsByIdBook;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BookAuthors getBookAuthors(int idAuthor, int idBook)
    {
        try
        {
            getBookAuthorsStmt.setInt(1, idAuthor);
            getBookAuthorsStmt.setInt(2, idBook);

            ResultSet rs = getBookAuthorsStmt.executeQuery();

            while (rs.next())
            {
                return new BookAuthors( rs.getInt("idAuthor"), rs.getInt("idBook"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateBookAuthors(BookAuthors bookAuthors, int idAutor, int idBook)
    {
        int count = 0;
        try
        {
            updateBookAuthorsStmt.setInt(1, idAutor);
            updateBookAuthorsStmt.setInt(2, idBook);

            updateBookAuthorsStmt.setInt(3, bookAuthors.getIdAuthor());
            updateBookAuthorsStmt.setInt(4, bookAuthors.getIdBook());

            count = updateBookAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int deleteBookAuthors(BookAuthors bookAuthors)
    {
        int count = 0;
        try
        {
            deleteBookAuthorsStmt.setInt(1, bookAuthors.getIdAuthor());
            deleteBookAuthorsStmt.setInt(2, bookAuthors.getIdBook());

            count = deleteBookAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    public int addBookAuthors(BookAuthors bookAuthors)
    {
        int count = 0;
        try
        {
            addBookAuthorsStmt.setInt(1, bookAuthors.getIdAuthor());
            addBookAuthorsStmt.setInt(2, bookAuthors.getIdBook());

            count = addBookAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void clearBookAuthors()
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

}
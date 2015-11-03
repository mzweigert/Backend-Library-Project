package com.library.service;



import com.library.BooksAuthorsDAO;
import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.BooksAuthors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BooksAuthorsManager implements BooksAuthorsDAO
{

    private Connection connection;

    private String url = "jdbc:jtds:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableBooksAuthors= "CREATE TABLE BooksAuthors( " +
            "idBooksAuthors INTEGER NOT NULL identity(1,1) PRIMARY KEY, " +
            "idAuthor INTEGER FOREIGN KEY REFERENCES Author(idAuthor), " +
            "idBook INTEGER FOREIGN KEY REFERENCES Book(idBook));";

    private PreparedStatement getAllBooksAuthorsStmt;
    private PreparedStatement getBooksAuthorsByIdAuthorStmt;
    private PreparedStatement getBooksAuthorsByIdBookStmt;
    private PreparedStatement getBooksAuthorsByIdStmt;
    private PreparedStatement updateBooksAuthorsStmt;
    private PreparedStatement deleteBooksAuthorsStmt;
    private PreparedStatement addBooksAuthorsStmt;

    private PreparedStatement deleteAllBooksAuthorsStmt;


    private Statement statement;

    public BooksAuthorsManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("BooksAuthors".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableBooksAuthors);

            getAllBooksAuthorsStmt = connection.prepareStatement("SELECT  idBooksAuthors, idAuthor, idBook FROM BooksAuthors");
            getBooksAuthorsByIdAuthorStmt = connection.prepareStatement("SELECT * FROM BooksAuthors WHERE idAuthor = ?");
            getBooksAuthorsByIdBookStmt = connection.prepareStatement("SELECT * FROM BooksAuthors WHERE idBook = ?");
            getBooksAuthorsByIdStmt = connection.prepareStatement("SELECT * FROM BooksAuthors WHERE idBooksAuthors = ?");
            updateBooksAuthorsStmt = connection.prepareStatement("UPDATE BooksAuthors SET idAuthor = ?, idBook = ? WHERE idBooksAuthors = ?");
            deleteBooksAuthorsStmt = connection.prepareStatement("DELETE BooksAuthors WHERE idBooksAuthors = ?");

            addBooksAuthorsStmt = connection.prepareStatement("INSERT INTO BooksAuthors(idAuthor, idBook) VALUES (?, ?)");
            deleteAllBooksAuthorsStmt = connection.prepareStatement("DELETE FROM BooksAuthors");

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
    public List<BooksAuthors> getAllBooksAuthors()
    {
        List<BooksAuthors> booksAuthors = new ArrayList<BooksAuthors>();

        try
        {
            ResultSet rs = getAllBooksAuthorsStmt.executeQuery();

            while (rs.next())
            {
                BooksAuthors ba = new BooksAuthors(rs.getInt("idAuthor"), rs.getInt("idBook"));
                ba.setIdBooksAuthors(rs.getInt("idBooksAuthors"));
                booksAuthors.add(ba);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return booksAuthors;
    }

    @Override
    public List<BooksAuthors>  getBooksAuthorsByIdAuthor(Author author)
    {
        List<BooksAuthors> booksAuthorsByIdAuthor = new ArrayList<BooksAuthors>();
        BooksAuthors booksAuthors;
        try
        {
            getBooksAuthorsByIdAuthorStmt.setInt(1, author.getIdAuthor());
            ResultSet rs = getBooksAuthorsByIdAuthorStmt.executeQuery();

            while (rs.next())
            {
                booksAuthors = new BooksAuthors(rs.getInt("idAuthor"), rs.getInt("idBook"));
                booksAuthors.setIdBooksAuthors(rs.getInt("idBooksAuthors"));
                booksAuthorsByIdAuthor.add(booksAuthors);
            }

            return booksAuthorsByIdAuthor;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<BooksAuthors> getBooksAuthorsByIdBook(Book book)
    {
        List<BooksAuthors> booksAuthorsByIdBook = new ArrayList<BooksAuthors>();
        BooksAuthors booksAuthors;
        try
        {
            getBooksAuthorsByIdBookStmt.setInt(1, book.getIdBook());
            ResultSet rs = getBooksAuthorsByIdBookStmt.executeQuery();

            while (rs.next())
            {
                booksAuthors = new BooksAuthors(rs.getInt("idAuthor"), rs.getInt("idBook"));
                booksAuthors.setIdBooksAuthors(rs.getInt("idBooksAuthors"));
                booksAuthorsByIdBook.add(booksAuthors);
            }

            return booksAuthorsByIdBook;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BooksAuthors getBooksAuthorsById(BooksAuthors booksAuthors)
    {
        try
        {
            getBooksAuthorsByIdStmt.setInt(1, booksAuthors.getIdBooksAuthors());

            ResultSet rs = getBooksAuthorsByIdStmt.executeQuery();

            while (rs.next())
            {
                booksAuthors = new BooksAuthors(rs.getInt("idAuthor"), rs.getInt("idBook"));
                booksAuthors.setIdBooksAuthors(rs.getInt("idBooksAuthors"));
                return  booksAuthors;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateBooksAuthors(BooksAuthors booksAuthors)
    {
        int count = 0;
        try
        {
            updateBooksAuthorsStmt.setInt(1, booksAuthors.getIdAuthor());
            updateBooksAuthorsStmt.setInt(2, booksAuthors.getIdBook());
            updateBooksAuthorsStmt.setInt(3, booksAuthors.getIdBooksAuthors());
            count = updateBooksAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int deleteBooksAuthors(BooksAuthors booksAuthors)
    {
        int count = 0;
        try
        {
            deleteBooksAuthorsStmt.setInt(1, booksAuthors.getIdBooksAuthors());

            count = deleteBooksAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
    @Override
    public int addBooksAuthors(BooksAuthors booksAuthors)
    {
        int count = 0;
        try
        {
            addBooksAuthorsStmt.setInt(1, booksAuthors.getIdAuthor());
            addBooksAuthorsStmt.setInt(2, booksAuthors.getIdBook());

            count = addBooksAuthorsStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void clearBooksAuthors()
    {
        try
        {
            deleteAllBooksAuthorsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}

package com.library.service;



import com.library.ReaderDAO;
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



public class ReaderManager implements ReaderDAO
{

    private Connection connection;

    private String url = "jdbc:jtds:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableReader = "CREATE TABLE  Reader (idReader INTEGER NOT NULL PRIMARY KEY IDENTITY(1,1), name VARCHAR(25) NOT NULL, surname VARCHAR(25) NOT NULL, join_date date NOT NULL, extra_points integer default 0);";
    private PreparedStatement getAllReadersStmt;
    private PreparedStatement getReaderByIdStmt;
    private PreparedStatement getReaderBySurnameStmt;
    private PreparedStatement updateReaderStmt;
    private PreparedStatement deleteReaderStmt;
    private PreparedStatement addReaderStmt;
    private PreparedStatement getReaderBooksStmt;
    private PreparedStatement deleteAllReadersStmt;

    private Statement statement;

    public ReaderManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("Reader".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableReader);

            getAllReadersStmt = connection.prepareStatement("SELECT idReader, name, surname, join_date, extra_points FROM Reader");
            getReaderByIdStmt = connection.prepareStatement("SELECT * FROM Reader WHERE idReader = ?");
            getReaderBySurnameStmt = connection.prepareStatement("SELECT * FROM Reader WHERE surname = ? ");
            updateReaderStmt = connection.prepareStatement("UPDATE Reader SET name = ?, surname = ?, join_date = ? , extra_points = ? WHERE idReader = ?");
            deleteReaderStmt = connection.prepareStatement("DELETE Reader WHERE idReader = ?");
            addReaderStmt = connection.prepareStatement("INSERT INTO Reader (name, surname, join_date, extra_points) VALUES (?, ?, ?, ?)");
            getReaderBooksStmt = connection.prepareStatement("Select Book.idBook, Book.title, Book.relase_date, Book.relase from Book " +
                                                             "inner join Hiring on Book.idBook=Hiring.idBook where Hiring.idReader = ?");

            deleteAllReadersStmt = connection.prepareStatement("DELETE FROM Reader");



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
    public List<Reader> getAllReaders()
    {
        List<Reader> readers = new ArrayList<Reader>();
        Reader reader;
        try
        {
            ResultSet rs = getAllReadersStmt.executeQuery();

            while (rs.next())
            {
                reader = new Reader( rs.getString("name"), rs.getString("surname"), rs.getDate("join_date"), rs.getInt("extra_points"));
                reader.setIdReader(rs.getInt("idReader"));
                readers.add(reader);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return readers;
    }
    @Override
    public Reader getReaderById(Reader reader)
    {

        try
        {
            getReaderByIdStmt.setInt(1, reader.getIdReader());
            ResultSet rs = getReaderByIdStmt.executeQuery();

            while (rs.next())
            {
                reader = new Reader(rs.getString("name"), rs.getString("surname"), rs.getDate("join_date"),rs.getInt("extra_points"));
                reader.setIdReader(rs.getInt("idReader"));
                return reader;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reader> getReadersBySurname(Reader reader)
    {
        List<Reader> readersBySurname = new ArrayList<Reader>();

        try
        {
            getReaderBySurnameStmt.setString(1, reader.getSurname());
            ResultSet rs = getReaderBySurnameStmt.executeQuery();

            while (rs.next())
            {
                reader = new Reader(rs.getString("name"), rs.getString("surname"), rs.getDate("join_date"),rs.getInt("extra_points"));
                reader.setIdReader(rs.getInt("idReader"));
                readersBySurname.add(reader);
            }

            return readersBySurname;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public  List<Book> getReaderBooks(Reader reader)
    {
        List<Book> readerBooks = new ArrayList<Book>();
        Book book;
        try
        {
            getReaderBooksStmt.setInt(1, reader.getIdReader());
            ResultSet rs = getReaderBooksStmt.executeQuery();

            while (rs.next())
            {
                book = new Book(rs.getString("title"), rs.getDate("relase_date"), rs.getInt("relase"));
                book.setIdBook(rs.getInt("idBook"));
                readerBooks.add(book);
            }

            return readerBooks;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateReader(Reader reader)
    {
        int count = 0;
        try
        {
            updateReaderStmt.setString(1, reader.getName());
            updateReaderStmt.setString(2, reader.getSurname());
            updateReaderStmt.setDate(3, reader.getJoinDate());
            updateReaderStmt.setInt(4, reader.getExtraPoints());
            updateReaderStmt.setInt(5, reader.getIdReader());

            count = updateReaderStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteReader(Reader reader)
    {
        int count = 0;
        try
        {
            deleteReaderStmt.setInt(1, reader.getIdReader());
            count = deleteReaderStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return count;
    }
    @Override
    public int addReader(Reader Reader)
    {
        int count = 0;
        try
        {
            addReaderStmt.setString(1, Reader.getName());
            addReaderStmt.setString(2, Reader.getSurname());
            addReaderStmt.setDate(3, Reader.getJoinDate());
            addReaderStmt.setInt(4, Reader.getExtraPoints());

            count = addReaderStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void clearReaders()
    {
        try
        {
            deleteAllReadersStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}

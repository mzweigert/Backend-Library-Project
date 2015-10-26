package main.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.HiringDAO;
import main.domain.Author;
import main.domain.Book;
import main.domain.Hiring;
import main.domain.Reader;
import org.junit.Before;

public class HiringManager implements HiringDAO
{

    private Connection connection;

    private String url = "jdbc:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableHiring = "CREATE TABLE  Hiring (idHiring INTEGER NOT NULL IDENTITY(1,1) PRIMARY KEY , idBook INTEGER NOT NULL REFERENCES Book(idBook), idReader INTEGER NOT NULL REFERENCES Reader(idReader), hire_date DATE NOT NULL );";

    private PreparedStatement getAllHiringsStmt;
    private PreparedStatement getHiringByIdStmt;
    private PreparedStatement getHiringsByIdBookStmt;
    private PreparedStatement getHiringsByIdReaderStmt;
    private PreparedStatement updateHiringStmt;
    private PreparedStatement deleteHiringStmt;
    private PreparedStatement addHiringStmt;

    private PreparedStatement deleteAllHiringsStmt;


    private Statement statement;

    public HiringManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("Hiring".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableHiring);

            getAllHiringsStmt = connection.prepareStatement("SELECT idHiring, idBook, idReader, hire_date FROM Hiring");
            getHiringByIdStmt = connection.prepareStatement("SELECT * FROM Hiring WHERE idHiring = ?");
            getHiringsByIdBookStmt = connection.prepareStatement("SELECT * FROM Hiring WHERE idBook = ?");
            getHiringsByIdReaderStmt = connection.prepareStatement("SELECT * FROM Hiring WHERE idReader = ?");
            updateHiringStmt = connection.prepareStatement("UPDATE Hiring SET idBook=?, idReader=?, hire_date=? WHERE idHiring = ?");
            deleteHiringStmt = connection.prepareStatement("DELETE Hiring WHERE idHiring = ?");
            addHiringStmt = connection.prepareStatement("INSERT INTO Hiring (idBook, idReader, hire_date) VALUES (?, ?, ?)");
            deleteAllHiringsStmt = connection.prepareStatement("DELETE FROM Hiring");

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
    public List<Hiring> getAllHirings()
    {
        List<Hiring> hirings = new ArrayList<Hiring>();
        Hiring hiring;
        try
        {
            ResultSet rs = getAllHiringsStmt.executeQuery();

            while (rs.next())
            {
                hiring = new Hiring( rs.getInt("idBook"),  rs.getInt("idReader"), rs.getDate("hire_date"));
                hiring.setIdHiring(rs.getInt("idHiring"));
                hirings.add(hiring);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hirings;
    }

    @Override
    public Hiring getHiringById(Hiring hiring)
    {

        try
        {
            getHiringByIdStmt.setInt(1, hiring.getIdHiring());
            ResultSet rs = getHiringByIdStmt.executeQuery();

            while (rs.next())
            {
                hiring =  new Hiring( rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date"));
                hiring.setIdHiring(rs.getInt("idHiring"));
                return  hiring;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Hiring> getHiringsByIdReader(Reader reader)
    {
        Hiring hiring;
        List<Hiring> hiringsByIdReader = new ArrayList<Hiring>();
        try
        {
            getHiringsByIdReaderStmt.setInt(1, reader.getIdReader());
            ResultSet rs = getHiringsByIdReaderStmt.executeQuery();

            while (rs.next())
            {
                hiring = new Hiring(rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date"));
                hiring.setIdHiring(rs.getInt("idHiring"));
                hiringsByIdReader.add(hiring);
            }

            return hiringsByIdReader;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Hiring> getHiringsByIdBook(Book book)
    {
        List<Hiring> hiringsByIdBook = new ArrayList<Hiring>();
        Hiring hiring;
        try
        {
            getHiringsByIdBookStmt.setInt(1, book.getIdBook());
            ResultSet rs = getHiringsByIdBookStmt.executeQuery();

            while (rs.next())
            {
                hiring = new Hiring(rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date"));
                hiring.setIdHiring(rs.getInt("idHiring"));
                hiringsByIdBook.add(hiring);
            }

            return hiringsByIdBook;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateHiring(Hiring hiring)
    {
        int count = 0;
        try
        {
            updateHiringStmt.setInt(1, hiring.getIdBook());
            updateHiringStmt.setInt(2, hiring.getIdReader());
            updateHiringStmt.setDate(3, hiring.getHireDate());

            updateHiringStmt.setInt(4, hiring.getIdHiring());

            count = updateHiringStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteHiring(Hiring hiring)
    {
        int count = 0;
        try
        {
            deleteHiringStmt.setInt(1, hiring.getIdHiring());
            count = deleteHiringStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return count;
    }

    public int addHiring(Hiring hiring)
    {
        int count = 0;
        try
        {
            addHiringStmt.setInt(1, hiring.getIdBook());
            addHiringStmt.setInt(2, hiring.getIdReader());
            addHiringStmt.setDate(3, hiring.getHireDate());


           count = addHiringStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public void clearHirings()
    {
        try
        {
            deleteAllHiringsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
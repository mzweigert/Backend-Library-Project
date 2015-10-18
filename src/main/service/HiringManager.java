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
import main.domain.Hiring;

public class HiringManager implements HiringDAO
{

    private Connection connection;

    private String url = "jdbc:sqlserver://eos.inf.ug.edu.pl;" + "databaseName=mzweigert" + ";user=mzweigert" + ";password=224667";

    private String createTableHiring = "CREATE TABLE Hiring(idHiring bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"+
                                                          "FOREIGN KEY REFERENCES Book(idBook),"+
                                                          "FOREIGN KEY REFERENCES Reader(idReader),"+
                                                          "hire_date Date)";

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
            updateHiringStmt = connection.prepareStatement("UPDATE Hiring SET idBook=?, idReader=?, hire_date WHERE idHiring = ?");
            deleteHiringStmt = connection.prepareStatement("DELETE Hiring WHERE idHiring = ?");
            addHiringStmt = connection.prepareStatement("INSERT INTO Hiring (idBook, idReader, hire_date) VALUES (?, ?, ?)");
            deleteAllHiringsStmt = connection.prepareStatement("DELETE FROM Hiring");

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
    public List<Hiring> getAllHirings()
    {
        List<Hiring> hirings = new ArrayList<Hiring>();

        try
        {
            ResultSet rs = getAllHiringsStmt.executeQuery();

            while (rs.next())
            {
                Hiring hiring = new Hiring(rs.getInt("idHiring"), rs.getInt("idBook"),  rs.getInt("idReader"), rs.getDate("hire_date"));
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
    public Hiring getHiringById(int idHiring)
    {
        try
        {
            getHiringByIdStmt.setInt(1, idHiring);
            ResultSet rs = getHiringByIdStmt.executeQuery();

            while (rs.next())
            {
                return new Hiring(rs.getInt("idHiring"), rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Hiring> getHiringsByIdReader(int idReader)
    {
        List<Hiring> hiringsByIdReader = new ArrayList<Hiring>();

        try
        {
            getHiringsByIdReaderStmt.setInt(1, idReader);
            ResultSet rs = getHiringsByIdReaderStmt.executeQuery();

            while (rs.next())
            {
                hiringsByIdReader.add(new Hiring(rs.getInt("idHiring"), rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date")));
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
    public List<Hiring> getHiringsByIdBook(int idBook)
    {
        List<Hiring> hiringsByIdBook = new ArrayList<Hiring>();

        try
        {
            getHiringsByIdBookStmt.setInt(1, idBook);
            ResultSet rs = getHiringsByIdBookStmt.executeQuery();

            while (rs.next())
            {
                hiringsByIdBook.add(new Hiring(rs.getInt("idHiring"), rs.getInt("idBook"), rs.getInt("idReader"), rs.getDate("hire_date")));
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
    public boolean updateHiring(Hiring hiring)
    {
        try
        {
            updateHiringStmt.setInt(1, hiring.getIdBook());
            updateHiringStmt.setInt(2, hiring.getIdReader());
            updateHiringStmt.setDate(3, hiring.getHireDate());

            updateHiringStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteHiring(Hiring hiring)
    {
        try
        {
            deleteAllHiringsStmt.setInt(1, hiring.getIdHiring());
            deleteAllHiringsStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addHiring(Hiring hiring)
    {

        try
        {
            addHiringStmt.setInt(1, hiring.getIdBook());
            addHiringStmt.setInt(2, hiring.getIdReader());
            addHiringStmt.setDate(3, hiring.getHireDate());


            addHiringStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    void clearHirings()
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
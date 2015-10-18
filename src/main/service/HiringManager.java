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
import main.domain.Hiring;

public class HiringManager implements HiringDAO
{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/mzweigert";

    private String createTableHiring = "CREATE TABLE Hiring(idHiring bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"+
                                                          "FOREIGN KEY REFERENCES Book(idBook),"+
                                                          "FOREIGN KEY REFERENCES Reader(idReader),"+
                                                          "Hiring_date Date)";

    private PreparedStatement addHiringStmt;
    private PreparedStatement deleteAllHiringsStmt;
    private PreparedStatement getAllHiringsStmt;

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

            addHiringStmt = connection.prepareStatement("INSERT INTO Hiring (idBook, idReader, Hiring_date) VALUES (?, ?, ?)");
            deleteAllHiringsStmt = connection.prepareStatement("DELETE FROM Hiring");
            getAllHiringsStmt = connection.prepareStatement("SELECT idHiring, idBook, idReader, Hiring_date FROM Hiring");

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

    public int addHiring(Hiring hiring)
    {
        int count = 0;
        try
        {
            addHiringStmt.setInt(1, hiring.getIdBook());
            addHiringStmt.setInt(2, hiring.getIdReader());
            addHiringStmt.setDate(3, hiring.getOrderDate());


            count = addHiringStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public List<Hiring> getAllHirings()
    {
        List<Hiring> hirings = new ArrayList<Hiring>();

        try
        {
            ResultSet rs = getAllHiringsStmt.executeQuery();

            while (rs.next())
            {
                Hiring hiring = new Hiring(rs.getInt("idHiring"), rs.getInt("idBook"),  rs.getInt("idReader"), rs.getDate("order_date"));
                hirings.add(hiring);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hirings;
    }

}
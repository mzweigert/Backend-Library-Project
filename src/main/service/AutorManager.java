package main.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.domain.Autor;

public class AutorManager
{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/mzweigert";

    private String createTableAutor = "CREATE TABLE Autor(idAutor bigint GENERATED BY DEFAULT AS IDENTITY, imie varchar(20), nazwisko varchar(20))";

    private PreparedStatement addAutorStmt;
    private PreparedStatement deleteAllAutorsStmt;
    private PreparedStatement getAllAutorsStmt;

    private Statement statement;

    public AutorManager()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next())
            {
                if ("Autor".equalsIgnoreCase(rs.getString("TABLE_NAME")))
                {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                statement.executeUpdate(createTableAutor);

            addAutorStmt = connection.prepareStatement("INSERT INTO Autor (imie, nazwisko) VALUES (?, ?)");
            deleteAllAutorsStmt = connection.prepareStatement("DELETE FROM Autor");
            getAllAutorsStmt = connection.prepareStatement("SELECT idAutor, imie, nazwisko FROM Autor");

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

    void clearAutors()
    {
        try
        {
            deleteAllAutorsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int addAutor(Autor autor)
    {
        int count = 0;
        try
        {
            addAutorStmt.setString(1, autor.getImie());
            addAutorStmt.setString(2, autor.getNazwisko());

            count = addAutorStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public List<Autor> getAllAutors()
    {
        List<Autor> Autors = new ArrayList<Autor>();

        try
        {
            ResultSet rs = getAllAutorsStmt.executeQuery();

            while (rs.next())
            {
                Autor autor = new Autor(rs.getInt("idAutor"), rs.getString("imie"), rs.getString("nazwisko"));
                Autors.add(autor);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Autors;
    }

}
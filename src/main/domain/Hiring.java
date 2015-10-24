package main.domain;

import java.io.Serializable;
import java.sql.Date;

public class Hiring implements Serializable
{

    private int idHiring;
    private int idBook, idReader;
    private Date hireDate;

    public Hiring()
    {

    }

    public Hiring(int idBook, int idReader, Date hireDate)
    {
        super();
        this.idBook = idBook;
        this.idReader = idReader;
        this.hireDate = hireDate;
    }



    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdReader() {
        return idReader;
    }

    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public int getIdHiring() {
        return idHiring;
    }

    public void setIdHiring(int idHiring) {
        this.idHiring = idHiring;
    }

}

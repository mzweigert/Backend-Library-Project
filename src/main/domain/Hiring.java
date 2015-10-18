package main.domain;

import java.io.Serializable;
import java.sql.Date;

public class Hiring implements Serializable
{

    private int idHiring;
    private int idBook, idReader;
    private Date orderDate;


    public Hiring(int idHiring, int idBook, int idReader, Date orderDate)
    {
        this.idHiring = idHiring;
        this.idBook = idBook;
        this.idReader = idReader;
        this.orderDate = orderDate;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getIdHiring() {
        return idHiring;
    }

    public void setIdHiring(int idHiring) {
        this.idHiring = idHiring;
    }

}

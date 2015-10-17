package main.domain;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable
{

	private int idOrder;
	private int idBook, idReader;
	private Date orderDate;

	
	public Order(int idOrder, int idBook, int idReader, Date orderDate)
	{
		this.idOrder = idOrder;
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

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

}

package main.domain;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable
{
	
	private int idBook;
	private String title;
	private Date relaseDate;
	private int relase;

	public Book()
	{

	}
	public Book(int idBook, String title, Date relaseDate, int relase)
	{
		this.setIdBook(idBook);
		this.title= title;
		this.relaseDate = relaseDate;
		this.relase = relase;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRelaseDate() {
		return relaseDate;
	}

	public void setRelaseDate(Date relaseDate) {
		this.relaseDate = relaseDate;
	}

	public int getRelase() {
		return relase;
	}

	public void setRelase(int relase) {
		this.relase = relase;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}


}

package main.domain;

import java.sql.Date;

public class Book
{
	
	private int idBook;
	private String title;
	private Date relaseDate;
	private int relase;

	public Book()
	{

	}
	public Book(int idBook, String Title, Date dataWydania, int wydanie)
	{
		this.setIdBook(idBook);
		this.title= title;
		this.relaseDate = relaseDate;
		this.relase = relase;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String Title) {
		this.title = title;
	}

	public Date getDataWydania() {
		return relaseDate;
	}

	public void setDataWydania(Date dataWydania) {
		this.relaseDate = relaseDate;
	}

	public int getWydanie() {
		return relase;
	}

	public void setWydanie(int wydanie) {
		this.relase = relase;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}


}

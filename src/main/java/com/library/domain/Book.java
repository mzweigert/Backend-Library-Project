package com.library.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Book implements Serializable
{
	
	private int idBook;
	private String title;
	private Date relaseDate;
	private int relase;
	private List<Reader> readers;
	private List<Author> authors;

	public Book()
	{

	}
	public Book(String title, Date relaseDate, int relase)
	{
		super();
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

	public List<Reader> getReaders() {
		return readers;
	}

	public void setReaders(List<Reader> readers) {
		this.readers = readers;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}


}

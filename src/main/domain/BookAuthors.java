package main.domain;

import java.io.Serializable;

public class BookAuthors implements Serializable
{

	private int idBook, idAuthor ;

	public BookAuthors()
	{

	}
	public BookAuthors(int idBook, int idAuthor)
	{
		
		this.idBook = idBook;
		this.idAuthor = idAuthor;
	}


	public int getIdAuthor() {
		return idAuthor;
	}

	public void setIdAuthor(int idAuthor) {
		this.idAuthor = idAuthor;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
}

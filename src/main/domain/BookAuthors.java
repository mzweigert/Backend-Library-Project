package main.domain;

import java.io.Serializable;

public class BookAuthors implements Serializable
{

	private int idAuthor, idBook;

	public BookAuthors()
	{

	}
	public BookAuthors(int idAuthor, int idBook)
	{
		this.idAuthor = idAuthor;
		this.idBook = idBook;
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

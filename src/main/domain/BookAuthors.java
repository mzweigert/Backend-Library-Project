package main.domain;

public class BookAuthors
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

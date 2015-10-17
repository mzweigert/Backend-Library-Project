package main.domain;

public class Author
{
    private int idAuthor;
    private String name, surname;

    public Author()
    {

    }

    public Author(int idAuthor, String name, String surname)
    {
        this.idAuthor = idAuthor;
        this.name = name;
        this.surname = surname;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public int getIdAuthor()
    {
        return idAuthor;
    }

    public void setIdAuthor(int idAutor)
    {
        this.idAuthor = idAuthor;
    }
}

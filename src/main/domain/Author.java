package main.domain;

public class Author
{
    private int idAutor;
    private String name, surname;

    public Author()
    {

    }

    public Author(int idAutor, String name, String surname)
    {
        this.idAutor = idAutor;
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

    public int getIdAutor()
    {
        return idAutor;
    }

    public void setIdAutor(int idAutor)
    {
        this.idAutor = idAutor;
    }
}

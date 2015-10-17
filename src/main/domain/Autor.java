package main.domain;

public class Autor
{
    private int idAutor;
    private String imie, nazwisko;

    public Autor()
    {

    }

    public Autor(int idAutor, String imie, String nazwisko)
    {
        this.idAutor = idAutor;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }
    public String getImie()
    {
        return imie;
    }

    public void setImie(String imie)
    {
        this.imie = imie;
    }

    public String getNazwisko()
    {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko)
    {
        this.nazwisko = nazwisko;
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

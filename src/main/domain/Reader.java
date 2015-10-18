package main.domain;

import java.io.Serializable;
import java.sql.Date;

public class Reader implements Serializable
{
	private int idReader;
	private String name,surname;
	private java.sql.Date joinDate;
	private int extraPoints;

	public Reader()
	{

	}
	public Reader(String name, String surname, Date joinDate, int extraPoints)
	{

		this.name = name;
		this.surname = surname;
		this.joinDate = joinDate;
		this.extraPoints = extraPoints;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public int getExtraPoints() {
		return extraPoints;
	}

	public void setExtraPoints(int extraPoints) {
		this.extraPoints = extraPoints;
	}

	public int getIdReader() {
		return idReader;
	}

	public void setIdReader(int idReader) {
		this.idReader = idReader;
	}
    
}

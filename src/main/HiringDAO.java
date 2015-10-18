package main;

import main.domain.Hiring;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface HiringDAO
{
    List<Hiring> getAllHirings();
    Hiring getHiringById(int idHiring);
    List<Hiring> getHiringsByIdReader(int idReader);
    List<Hiring> getHiringsByIdBook(int idBook);
    boolean updateHiring(Hiring hiring);
    boolean deleteHiring(Hiring hiring);
    boolean addHiring(Hiring hiring);

}

package main;

import main.domain.Hiring;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface HiringDAO
{
    List<Hiring> getAllHirings();
    Hiring getHiringById(Hiring hiring);
    List<Hiring> getHiringsByIdReader(Hiring hiring);
    List<Hiring> getHiringsByIdBook(Hiring hiring);
    boolean updateHiring(Hiring hiring);
    boolean deleteHiring(Hiring hiring);
    boolean addHiring(Hiring hiring);

}

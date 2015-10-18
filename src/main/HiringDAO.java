package main;

import main.domain.Hiring;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface HiringDAO
{

    int addHiring(Hiring hiring);
    List<Hiring> getAllHirings();

}

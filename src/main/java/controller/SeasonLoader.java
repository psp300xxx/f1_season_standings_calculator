package controller;

import model.Season;

public interface SeasonLoader {

    void setDelegate(SeasonLoaderDelegate delegate);

    void loadSeason(int year);

}

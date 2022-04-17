package controller;

import model.Season;

public interface SeasonLoaderDelegate {

    void seasonLoaded(SeasonLoader loader, Season season);

    void seasonLoadingFailed(SeasonLoader loader, Exception exception);

}

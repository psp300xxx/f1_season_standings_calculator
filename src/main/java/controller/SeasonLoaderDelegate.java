package controller;

import model.Season;

import java.io.PrintWriter;

public interface SeasonLoaderDelegate {

    PrintWriter getPrintWriter();

    void setPrintWriter(PrintWriter newPrintWriter);

    void seasonLoaded(SeasonLoader loader, Season season);

    void seasonLoadingFailed(SeasonLoader loader, Exception exception);

}

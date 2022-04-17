package controller;

public class SeasonLoaderThread extends Thread {

    private SeasonLoader seasonLoader;
    private SeasonLoaderDelegate delegate;
    private int year;

    public SeasonLoaderThread(SeasonLoader seasonLoader, SeasonLoaderDelegate delegate, int year){
        this.seasonLoader = seasonLoader;
        seasonLoader.setDelegate(delegate);
        this.year = year;
    }

    @Override
    public void run() {
        super.run();
        seasonLoader.loadSeason(year);
    }
}

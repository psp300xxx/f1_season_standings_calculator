import controller.*;
import model.*;

public class Main {

    private static Season season;

    public static class SeasonLoaderDelegateImpl implements SeasonLoaderDelegate {

        @Override
        public void seasonLoaded(SeasonLoader loader, Season season) {
            Main.season = season;
            Standings standings = new StandingsImpl();
            PointSystemCalculator pointSystemCalculator = new PointSystemCalculatorImpl();
            int driversOnGrid = season.driversOnGrid();
            if(driversOnGrid>26){
                driversOnGrid = 26;
            }
            PointSystem newPointSystem = pointSystemCalculator.newPointSystem(driversOnGrid, new PointSystem2010().getRatio());
            standings.setPointSystem(newPointSystem);
            standings.computeSeason(season);
            System.out.println(newPointSystem.pointsPosition());
            System.out.println(standings.standings(true));
        }

        @Override
        public void seasonLoadingFailed(SeasonLoader loader, Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        int year = Integer.parseInt(args[0]);
        SeasonLoaderThread seasonLoaderThread = new SeasonLoaderThread(new SeasonLoaderImpl(), new SeasonLoaderDelegateImpl(), year);
        seasonLoaderThread.start();
    }
}

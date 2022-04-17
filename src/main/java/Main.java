import controller.*;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Main {

    public static final int MIN_ARGS_LEN = 1;

    public static final String COMMON_TARGET_DIRECTORY = "F1_TARGET";

    public static final int FIRST_F1_YEAR = 1950;

    private static Season season;

    public static class SeasonLoaderDelegateImpl implements SeasonLoaderDelegate {

        private PrintWriter printWriter = new PrintWriter(System.out, true);
        @Override
        public PrintWriter getPrintWriter() {
            return this.printWriter;
        }

        @Override
        public void setPrintWriter(PrintWriter newPrintWriter) {
            this.printWriter = newPrintWriter;
        }

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
            getPrintWriter().write(newPointSystem.pointsPosition());
            getPrintWriter().write(standings.standings(true));
            getPrintWriter().flush();
        }

        @Override
        public void seasonLoadingFailed(SeasonLoader loader, Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static class NotF1YearException extends Exception{
        private int year;

        public NotF1YearException(int year){
            this.year = year;
        }

        @Override
        public String getMessage() {
            return String.format("%d is not ad F1 year", this.year);
        }
    }

    private static int parseInput(String input) throws NumberFormatException, NotF1YearException{
        int year = Integer.parseInt(input);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(year<FIRST_F1_YEAR || year>currentYear){
            throw new NotF1YearException(year);
        }
        return year;
    }

    private static void executeInput(String input, File output){
        try{
            int year = parseInput(input);
            SeasonLoaderDelegateImpl newDelegate = new SeasonLoaderDelegateImpl();
            if(output!=null){
                PrintWriter filePrintWriter = new PrintWriter(output);
                newDelegate.setPrintWriter(filePrintWriter);
            }
            SeasonLoaderThread seasonLoaderThread = new SeasonLoaderThread(new SeasonLoaderImpl(), newDelegate, year);
            seasonLoaderThread.start();
        }catch (NumberFormatException  exception){
            System.err.println(String.format("'%s' is not a number", input));
        }
        catch (NotF1YearException exception){
            System.err.println(exception.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        if(args.length<MIN_ARGS_LEN){
            System.err.println("Usage: java -jar f1_season.jar [YEAR]");
            System.exit(0);
        }
        File newDir = new File(COMMON_TARGET_DIRECTORY);
        if(newDir.exists() && !newDir.isDirectory()){
            System.err.println(String.format("'%s' already exists and it is a file", COMMON_TARGET_DIRECTORY));
            System.exit(0);
        }
        if(!newDir.exists()){
            newDir.mkdirs();
        }
        for( int i =0  ; i<args.length; i++ ){
            String currentOutputFile = COMMON_TARGET_DIRECTORY + File.separator+ String.format("./%s.txt", args[i]);
            executeInput(args[i], new File(currentOutputFile));
        }
    }
}

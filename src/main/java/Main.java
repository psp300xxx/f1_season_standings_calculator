import controller.*;
import model.*;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Main {

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
            System.out.println(String.format("Operating on '%s', destination file='%s'", input, output.getCanonicalPath()));
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Options options = new Options();

        Option input = new Option("y", "years", true, "Years to compute");
        input.setArgs(-2);
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(false);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        String commonDirectory = cmd.getOptionValue("output");
        File commonDir = new File(commonDirectory);
        if(!commonDir.exists()){
            commonDir.mkdirs();
        }
        else if(commonDir.isFile()){
            System.err.println(String.format("Directory provided (%s) exists as file", commonDirectory));
            System.exit(1);
        }
        for( String option : cmd.getOptionValues("years")){
            String currentOutputFile = commonDirectory + File.separator + String.format("./%s.txt", option);
            executeInput(option, new File(currentOutputFile));
        }
    }
}

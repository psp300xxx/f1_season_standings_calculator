package controller;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SeasonLoaderImpl implements SeasonLoader {

    protected SeasonLoaderDelegate delegate;
    private JSONCallManager jsonCallManager = new JSONCallManagerPlainJava() ;
    private static final String API_SOURCE = "http://ergast.com/api/f1/%d/%d/results.json";

    private static String getAPIForRace(int season, int round){
        return String.format(API_SOURCE, season, round);
    }


    public void setDelegate(SeasonLoaderDelegate delegate) {
        this.delegate = delegate;
    }

    public void loadSeason(int year) {
        if(delegate==null){
            System.err.println(String.format("Season %d not loaded due to missing delegate", year));
            return;
        }
        Season season = new Season();
        int currentRound = 1;
        boolean seasonFinished = false;
        while( !seasonFinished ){
            String raceEndpoint = getAPIForRace(year, currentRound);
            try {
                StringBuilder raceBuilder = jsonCallManager.get(raceEndpoint);
                JSONObject jsonObject = new JSONObject(raceBuilder.toString());
                Race newRace = jsonToRace(jsonObject);
                currentRound++;
                if(newRace == null){
                    this.delegate.seasonLoaded(this, season);
                    return;
                }
                for( Driver newDriver : newRace.getDrivers() ){
                    season.addDriver(newDriver);
                }
                season.addRace(newRace);
            } catch (JSONDownloadException e) {
                this.delegate.seasonLoadingFailed(this, e);
                return;
            }
        }
    }


    private Race jsonToRace( JSONObject raceJson ){
        Race race = new Race();
        JSONObject mrData = raceJson.getJSONObject("MRData");
        JSONObject raceTable = mrData.getJSONObject("RaceTable");
        JSONArray races = raceTable.getJSONArray("Races");
        if(races.length()==0){
            return null;
        }
        JSONObject raceResult = races.getJSONObject(0);
        race.setName( raceResult.getString("raceName") );
        JSONArray results = raceResult.getJSONArray("Results");
        for( int i = 0 ; i<results.length(); i++ ){
            JSONObject result = results.getJSONObject(i);
            DriverResult driverResult = jsonToResult(result);
            race.addResult(driverResult);
        }
        return race;
    }

    private DriverResult jsonToResult( JSONObject resultJson ){
        DriverResult driverResult = new DriverResult();
        JSONObject driverJSON = resultJson.getJSONObject("Driver");
        Driver driver = jsonToDriver(driverJSON);
        driverResult.setDriver(driver);
        String positionText = resultJson.getString("positionText");
        Position position = new Position();
        if( !isInteger(positionText) ){
            position.setRetired();
        }
        else{
            position.setPositionText(positionText);
        }
        driverResult.setPosition( position );
        return driverResult;
    }

    private static Driver jsonToDriver(JSONObject driverJson){
        CompleteDriver driver = new CompleteDriver( driverJson.getString("driverId") );
        driver.setBirthDate(driverJson.getString("dateOfBirth"));
        driver.setNationality(driverJson.getString("nationality"));
        driver.setName(driverJson.getString("givenName"));
        driver.setSurname(driverJson.getString("familyName"));
        return driver;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}

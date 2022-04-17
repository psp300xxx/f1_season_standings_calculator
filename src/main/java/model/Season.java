package model;

import com.google.common.collect.Sets;

import java.util.*;

public final class Season {
    private Set<Driver> drivers;
    private List<Race> raceList;

    public void addDriver(Driver driver){
        if( this.drivers==null ){
            this.drivers = new HashSet<>();
        }
        this.drivers.add(driver);
    }

    public void addRace(Race race){
        if( this.raceList==null ){
            this.raceList = new ArrayList<Race>();
        }
        this.raceList.add(race);
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public List<Race> getRaceList() {
        return Collections.unmodifiableList(raceList);
    }

    public void printAllResults(){
        for( Race race : raceList ){
            printRaceResult(race);
            System.out.println();
        }
    }

    public int driversOnGrid(){
        int max = raceList.get(0).getResult().size();
        for(  int i =1 ; i<raceList.size(); i++  ){
             int currentDrivers = raceList.get(i).getResult().size();
             if( currentDrivers>max ){
                 max = currentDrivers;
             }
        }
        return max;
    }



    private void printRaceResult(Race race){
        System.out.println(race.getName());
        for( DriverResult driverResult : race.getResult() ){
            System.out.println( String.format("%s: P%s", driverResult.getDriver(), driverResult.getPosition().getPositionText()) );
        }
    }

}

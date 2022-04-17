package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandingsImpl implements Standings {

    private List<DriverSeasonResult> driverSeasonResultList;
    private PointSystem pointSystem = new PointSystem2010();

    @Override
    public String standings(boolean reverse) {
        StringBuilder sb = new StringBuilder();
        driverSeasonResultList.sort(new DriverSeasonComparator(reverse));
        for( DriverSeasonResult driverSeasonResult: driverSeasonResultList ){
            String currentPos = String.format( "%s - %d\n", driverSeasonResult.getDriver(), driverSeasonResult.getPoints() );
            sb.append(currentPos);
        }
        return sb.toString();
    }

    @Override
    public void setPointSystem(PointSystem pointSystem) {
        this.pointSystem = pointSystem;
    }

    public DriverSeasonResult containsDriver(Driver driver){
        for( DriverSeasonResult d : driverSeasonResultList ){
            if(driver.equals(d.getDriver())){
                return d;
            }
        }
        return null;
    }

    @Override
    public void addRace(Race race) {
        if(driverSeasonResultList==null){
            driverSeasonResultList = new ArrayList<>();
        }
        for(DriverResult driverResult : race.getResult() ){
            int newPoints = pointSystem.getPointPosition( driverResult.getPosition().getPosition() );
            DriverSeasonResult driverSeasonResult = null;
            if( (driverSeasonResult = containsDriver(driverResult.getDriver())) != null){
                driverSeasonResult.addPoints(newPoints);
                driverSeasonResult.addRacePosition(driverResult.getPosition().getPosition());
                continue;
            }
            driverSeasonResult = new DriverSeasonResult();
            driverSeasonResult.setDriver( driverResult.getDriver() );
            driverSeasonResult.addPoints(newPoints);
            driverSeasonResult.addRacePosition(driverResult.getPosition().getPosition());
            this.driverSeasonResultList.add(driverSeasonResult);
        }
    }

    @Override
    public void computeSeason(Season season) {
        for ( Race race : season.getRaceList() ){
            addRace(race);
        }
    }
}

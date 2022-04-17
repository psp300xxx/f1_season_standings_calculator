package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class DriverSeasonResult {
    private Driver driver;
    private int points = 0;
    private List<Integer> racePositions;

    public Driver getDriver() {
        return driver;
    }

    public int getPoints() {
        return points;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public void addRacePosition(int racePosition){
        if( racePositions==null ){
            racePositions = new ArrayList<>();
        }
        racePositions.add(racePosition);
    }

    public List<Integer> getRacePositions() {
        return Collections.unmodifiableList(racePositions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverSeasonResult that = (DriverSeasonResult) o;
        return Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver);
    }
}

package model;

public interface Standings {

    String standings(boolean reverse);

    void setPointSystem(PointSystem pointSystem);

    void addRace(Race race);

    void computeSeason(Season season);

}

package controller;

import model.PointSystem;

import java.util.List;

public class PointSystemCalculatorImpl implements PointSystemCalculator {

    @Override
    public PointSystem newPointSystem(int drivers,List<Double> ratio) {
        BuildPointSystem pointSystem = new BuildPointSystem();
        int currentPosition = drivers;
        int currentAssignedPoints = 1;
        while( currentPosition > ratio.size() ){
            pointSystem.putPair(currentPosition,currentAssignedPoints);
            currentAssignedPoints++;
            currentPosition--;
        }
        for( int i = ratio.size()-1 ; i>=0;  i-- ){
            double currentRatio = ratio.get(i);
            currentAssignedPoints = (int)(currentRatio*currentAssignedPoints);
            pointSystem.putPair(currentPosition--, currentAssignedPoints);
        }
        return pointSystem;
    }
}

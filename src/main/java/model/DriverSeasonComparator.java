package model;

import java.util.Comparator;

public class DriverSeasonComparator implements Comparator<DriverSeasonResult> {
    private boolean reverse = false;

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public DriverSeasonComparator(boolean reverse){
        this.reverse = reverse;
    }

    private int realCompare(DriverSeasonResult o1, DriverSeasonResult o2){
        int result = o1.getPoints() - o2.getPoints();
        if(result!=0){
            return result;
        }
        int startingPosition = 1;
        int o1Number = -1;
        int o2Number = -1;
        while( o1Number!=0 && o2Number!=0 && startingPosition<20 ){
            o1Number = numberOfFinal(startingPosition, o1);
            o2Number = numberOfFinal(startingPosition, o2);
            if( o1Number!=o2Number ){
                return o1Number-o2Number;
            }
            startingPosition++;
        }
        return 0;
    }

    @Override
    public int compare(DriverSeasonResult o1, DriverSeasonResult o2) {
        int result = realCompare(o1, o2);
        if( reverse ){
            return result*-1;
        }
        return result;
    }

    private int numberOfFinal(int position, DriverSeasonResult driverSeasonResult){
        int number = 0;
        for( int racePos : driverSeasonResult.getRacePositions() ){
            if( position==racePos ){
                number++;
            }
        }
        return number;
    }
}

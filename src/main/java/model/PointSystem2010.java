package model;

import java.util.ArrayList;
import java.util.List;

public class PointSystem2010 implements PointSystem{
    private int [] points = {25,18,15,12,10,8,6,4,2,1};
    @Override
    public int getPointPosition(int position) {
        if( position<=0 || position>=11 ){
            return 0;
        }
        return points[position-1];
    }

    @Override
    public List<Double> getRatio() {
        List<Double> ratio = new ArrayList<>();
        for( int i = 0 ; i<points.length-1; i++ ){
            double currentRatio = (double) points[i]/(double)points[i+1];
            ratio.add(currentRatio);
        }
        return ratio;
    }

    @Override
    public String pointsPosition() {
        StringBuilder sb = new StringBuilder();
        for( int i = 0 ; i<points.length; i++ ){
            sb.append(String.format("P%d - %d pts \n", i+1, getPointPosition(i+1)));
        }
        return sb.toString();
    }
}

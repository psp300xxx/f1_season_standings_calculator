package model;

import java.util.Map;

public abstract class GeneralPointSystem implements PointSystem {
    protected Map<Integer, Integer> map;


    @Override
    public int getPointPosition(int position) {
        if(!map.containsKey(position)){
            return 0;
        }
        return map.get(position);
    }

    @Override
    public String pointsPosition() {
        StringBuilder sb = new StringBuilder();
        for( int i = 1 ; i<=map.size(); i++ ){
            sb.append(String.format("P%d - %d pts \n", i, getPointPosition(i)));
        }
        return sb.toString();
    }
}

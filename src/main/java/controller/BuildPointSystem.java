package controller;

import model.GeneralPointSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildPointSystem extends GeneralPointSystem {

    public void putPair(int position, int points){
        if( this.map == null ){
            this.map = new HashMap<>();
        }
        this.map.put(position, points);
    }

    @Override
    public List<Double> getRatio() {
        List<Double> ratio = new ArrayList<>();
        int size = map.size();
        for( int i = 1 ; i<size; i++ ){
            double curr = (double)map.get(i)/ (double)map.get(i+1);
            ratio.add(curr);
        }
        return ratio;
    }
}

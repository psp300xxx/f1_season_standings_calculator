package controller;

import model.PointSystem;

import java.util.List;

public interface PointSystemCalculator {

    PointSystem newPointSystem(int drivers,List<Double> ratio);
}

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Race {
    private String name;
    private List<DriverResult> result;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Driver> getDrivers(){
        if( result == null ){
            return Collections.emptyList();
        }
        return result.stream().map((x)->x.getDriver()).collect(Collectors.toList());
    }

    public List<DriverResult> getResult() {
        return Collections.unmodifiableList(result);
    }

    public void addResult(DriverResult driverResult){
        if( result == null ){
            result = new ArrayList<>();
        }
        result.add(driverResult);
    }
}

package model;

public final class Position {

    public final String RETIRED_POS_VALUE = "DNF";

    private String positionText;

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public void setRetired(){
        this.positionText = RETIRED_POS_VALUE;
    }

    public String getPositionText() {
        return positionText;
    }

    public boolean isRetired(){
        return positionText.equals(RETIRED_POS_VALUE);
    }

    public int getPosition(){
        if( isRetired() ){
            return -1;
        }
        return Integer.parseInt(positionText);
    }
}

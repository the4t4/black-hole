package assignment2;

import java.awt.Color;

public class Field {
    private boolean isSpaceship;
    private boolean isBlackHole;
    private Color color;

    public Field() {
        isSpaceship = false;
        isBlackHole = false;
        color = null;
    }

    public boolean isSpaceship() {
        return isSpaceship;
    }
    
    public void setSpaceship(boolean b){
        this.isSpaceship = b;
    }
    
    public boolean isBlackHole(){
        return isBlackHole;
    }
    
    public void setBlackHole(){
        this.isBlackHole = true;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color c){
        this.color = c;
    }   
    
}

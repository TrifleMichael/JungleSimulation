package Utilities;

import javafx.util.Duration;

public class Settings {

    float starvationPerCycle = 1;
    float plantNutrition = 50;
    int timePerFrame = 30;

    int resX = 1000;
    int resY = 400;

    int optionsSection = 100;
    int initialFood = 200;
    int initialAnimalNumber = 40;
    int scale = 10;

    int XCells = 100;
    int YCells = 30;


    Vector2d jungleLowerLeft = new Vector2d(40, 10);
    Vector2d jungleUpperRight = new Vector2d(50, 20);

    public void setTimePerFrame(int time) {
        timePerFrame = time;
    }

    public void setMapHeight(int height) {
        YCells = height;
        resY = height * scale + optionsSection;
    }

    public void setMapWidth(int width) {
        XCells = width;
        if(width*scale > 1000)
            resX = width * scale;
        else
            resX = 1000;
    }

    public void setStartingEnergy(int energy) {
        initialFood = energy;
    }

    public void setMoveEnergy(int moveEnergy) {
        starvationPerCycle = moveEnergy;
    }

    public void setPlantNutrition(int plantNutrition) {
        this.plantNutrition = plantNutrition;
    }

    public void setSavannaToJungleRatio(float ratio) throws IllegalArgumentException {
        if(ratio <= 1) {
            throw new IllegalArgumentException("Ratio cannot be smaller or equal to 1, while given ratio: "+ratio);
        }
        float start = (ratio-1)/2/ratio;
        float end = 1 - start;

        float x1 = start*XCells;
        float y1 = start*YCells;

        float x2 = end*XCells;
        float y2 = end*YCells;

        jungleLowerLeft = new Vector2d((int)x1, (int)y1);
        jungleUpperRight = new Vector2d((int)x2, (int)y2);
    }




    public Vector2d getUpperRightBound() {
        return new Vector2d(XCells-1, YCells-1);
    }

    public int getInitialAnimalNumber() {
        return initialAnimalNumber;
    }

    public Vector2d getLowerLeftBound() {
        return new Vector2d(0, 0);
    }

    public int getXCells() {
        return XCells;
    }

    public int getYCells() {
        return YCells;
    }

    public int getInitialFood() {
        return initialFood;
    }

    public float getStarvationPerCycle() {return starvationPerCycle;}

    public float getPlantNutrition() {return plantNutrition;}

    public float getMinEnergyToBreed() {return initialFood/2;}

    public Vector2d getUpperRightCorner() {return new Vector2d(XCells-1, YCells-1);}

    public Vector2d getJungleLowerLeft() {return jungleLowerLeft;}

    public Vector2d getJungleUpperRight() {return jungleUpperRight;}

    public Duration getFrameLength() {
        return Duration.millis(timePerFrame);
    }


    public int getXRes() {return resX;}

    public int getYRes() {return resY;}

    public int getOptionsSectionSize() {
        return optionsSection;
    }

    public int getScale() {
        return scale;
    }


    public int getJungleSize() {
        int x = jungleUpperRight.x - jungleLowerLeft.x;
        int y = jungleUpperRight.y - jungleLowerLeft.y;
        return x*y;
    }

}

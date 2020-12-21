package Maps;

import Utilities.Vector2d;
import Animals.Grass;

import java.util.concurrent.ThreadLocalRandom;

public class Savanna extends AbstractGrassMap {
    final Vector2d jungleLowerLeft;
    final Vector2d jungleUpperRight;



    public Savanna(GeneralMap generalMap) {
        this.generalMap = generalMap;
        lowerLeftBoundary = generalMap.getSettings().getLowerLeftBound();
        upperRightBoundary = generalMap.getSettings().getUpperRightCorner();
        jungleLowerLeft = generalMap.getSettings().getJungleLowerLeft();
        jungleUpperRight = generalMap.getSettings().getJungleUpperRight();
    }

    public int getNumberOfGrass() {
        return grassMap.size();
    }

    @Override
    public boolean isInBounds(Vector2d position) {
        if(lowerLeftBoundary.x <= position.x && position.x <= upperRightBoundary.x) {
            if(lowerLeftBoundary.y <= position.y && position.y <= upperRightBoundary.y) {
                if(!isInJungleBounds(position.x, position.y))
                    return true;
            }
        }
        return false;
    }

    public boolean isInJungleBounds(int x, int y) {
        if (jungleLowerLeft.x <= x && x <= jungleUpperRight.x) {
            if (jungleLowerLeft.y <= y && y <= jungleUpperRight.y) {
                return true;
            }
        }
        return false;
    }


    public boolean place(Grass grass) {
        if(!isInBounds(grass.getPosition())) {
            throw new IllegalArgumentException("Grass placed out of bounds at "+grass.getPosition()+" map bound is "+lowerLeftBoundary+" and "+upperRightBoundary);
        }

        if(isOccupied(grass.getPosition()) || isInJungleBounds(grass.getPosition().x, grass.getPosition().y)) {
            return false;
        }

        else {
            this.grassMap.put(grass.getPosition(), grass);
            return true;
        }
    }



    private Vector2d nextInSavanna(Vector2d position) {
        if (isInBounds(new Vector2d(position.x+1, position.y))) { // Move to right
            return new Vector2d(position.x+1, position.y);
        }
        else if (position.x+1 < upperRightBoundary.x ) {
            return new Vector2d(jungleUpperRight.x+1, position.y); // If not past right border then skip jungle
        }
        else if (isInBounds(new Vector2d(lowerLeftBoundary.x, position.y+1))) { // Else go to next y
            return new Vector2d(lowerLeftBoundary.x, position.y+1);
        }
        else {
            return null; // Went past entire savanna
        }
    }

    @Override
    public void addPlants() {
        int occupied = countOccupiedCells();
        int savannaSize = (upperRightBoundary.x-lowerLeftBoundary.x+1)*(upperRightBoundary.y-lowerLeftBoundary.y+1);
        savannaSize -= (jungleUpperRight.x-jungleLowerLeft.x+1)*(jungleUpperRight.y-jungleLowerLeft.y+1);

        if(savannaSize - occupied == 0) return;

        int random = ThreadLocalRandom.current().nextInt(0,savannaSize-occupied) - 1;
        Vector2d position = new Vector2d(lowerLeftBoundary.x, lowerLeftBoundary.y);



        // Going past random amounts of not occupied fields
        while(random >= 0) {
            if(!generalMap.isOccupied(position))
                random--;
            position = nextInSavanna(position);
        }

        while(position != null && isOccupied(position)) {
            position = nextInSavanna(position);
        }

        if (position != null) // jungle is full
            generalMap.placeGrass(new Grass(position));
    }

    private int countOccupiedCells() {
        int n = 0;
        Vector2d position = new Vector2d(lowerLeftBoundary.x, lowerLeftBoundary.y);
        while(position != null) {
            if (generalMap.isOccupied(position))
                n++;
            position = nextInSavanna(position);
        }
        return n;
    }
}

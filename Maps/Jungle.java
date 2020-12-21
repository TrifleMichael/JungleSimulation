package Maps;

import Utilities.Vector2d;
import Animals.Grass;

import java.util.concurrent.ThreadLocalRandom;

public class Jungle extends AbstractGrassMap {

    int jungleSize;

    public Jungle(GeneralMap generalMap) {
        this.generalMap = generalMap;
        this.lowerLeftBoundary = generalMap.getSettings().getJungleLowerLeft();
        this.upperRightBoundary = generalMap.getSettings().getJungleUpperRight();
        jungleSize = (upperRightBoundary.x-lowerLeftBoundary.x+1) * (upperRightBoundary.y-lowerLeftBoundary.y+1);
    }

    public int getNumberOfGrass() {
        return grassMap.size();
    }



    @Override
    public boolean place(Grass grass) {
        if (!isInBounds(grass.getPosition())) {
            throw new IllegalArgumentException("Grass placed out of bounds at "+grass.getPosition());
        }

        if (!isOccupied(grass.getPosition())) {
            grassMap.put(grass.getPosition(), grass);
            return true;
        }
        else return false;
    }

    private Vector2d nextInJungle(Vector2d position) {
        if (isInBounds(new Vector2d(position.x+1, position.y))) {
            return new Vector2d(position.x+1, position.y);
        }
        else if (isInBounds(new Vector2d(lowerLeftBoundary.x, position.y+1))) {
            return new Vector2d(lowerLeftBoundary.x, position.y+1);
        }
        else {
            return null;
        }
    }

    @Override
    public void addPlants() {
        int occupied = countOccupiedCells();
        if(jungleSize - occupied == 0) return;

        int random = ThreadLocalRandom.current().nextInt(0,jungleSize-occupied) - 1;
        Vector2d position = new Vector2d(lowerLeftBoundary.x, lowerLeftBoundary.y);



        // Going past random amounts of not occupied fields
        while(random >= 0) {
            if(!generalMap.isOccupied(position))
                random--;
            position = nextInJungle(position);
        }

        while(position != null && isOccupied(position)) {
            position = nextInJungle(position);
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
            position = nextInJungle(position);
        }
        return n;
    }

    public boolean isInBounds(Vector2d position) {
        if (lowerLeftBoundary.x <= position.x && position.x <= upperRightBoundary.x)
            if (lowerLeftBoundary.y <= position.y && position.y <= upperRightBoundary.y)
                return true;
        return false;
    }
}

package Maps;

import Utilities.Vector2d;
import Animals.Grass;

import java.util.HashMap;

public abstract class AbstractGrassMap {

    protected GeneralMap generalMap;
    protected HashMap<Vector2d, Grass> grassMap = new HashMap<>();


    protected Vector2d upperRightBoundary;
    protected Vector2d lowerLeftBoundary;


    public void deconstruct() {
        grassMap = null;
        generalMap = null;
    }

    public int getGrassNumber() {
        return grassMap.size();
    }

    public abstract boolean isInBounds(Vector2d position);


    public boolean isOccupied(Vector2d position) {
        return this.grassMap.containsKey(position);
    }


    public abstract boolean place(Grass grass);

    public abstract void addPlants();



    public void removePlantFrom(Vector2d position) {
        if (!this.grassMap.containsKey(position)) {
            throw new IllegalArgumentException("Tried to remove grass that does not exist from "+position);
        }
        this.grassMap.remove(position);
    }

    public void remove(Grass grass) {
        if (!this.grassMap.containsKey(grass.getPosition())) {
            throw new IllegalArgumentException("Tried to remove grass that does not exist from "+grass.getPosition());
        }
        this.grassMap.remove(grass.getPosition());
    }
}

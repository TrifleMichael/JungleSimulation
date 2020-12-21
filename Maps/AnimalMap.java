package Maps;
import Animals.Animal;
import Utilities.Vector2d;
import Utilities.IPositionChangeObserver;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// Map used to keep track of animals.
// Contains a list of animal cells, each Vector2d has a unique AnimalCell if there is any animal at that vector
public class AnimalMap implements IPositionChangeObserver {
    HashMap<Vector2d, AnimalCell> field = new HashMap<>();
    HashMap<Vector2d, AnimalCell> temporaryField = new HashMap<>();
    ArrayList<Animal> waitingToBeBorn = new ArrayList<>();
    GeneralMap generalMap;

    final Vector2d upperRightBoundary;
    final Vector2d lowerLeftBoundary = new Vector2d(0,0);


    public HashMap<Vector2d, AnimalCell> getAnimalCellMap() {
        return field;
    }

    public AnimalMap(GeneralMap generalMap) {
        this.generalMap = generalMap;
        upperRightBoundary = generalMap.getSettings().getUpperRightCorner();
    }

    public void deconstruct() {
        field = null;
        temporaryField = null;
        waitingToBeBorn = null;
        generalMap = null;
    }

    // Used for initial map creation
    public void placeAnimalAtRandom(int numOfAnimals) {
        int x;
        int y;
        int i = 0;
        while (i < numOfAnimals) {
            x = ThreadLocalRandom.current().nextInt(0, generalMap.getSettings().getXCells());
            y = ThreadLocalRandom.current().nextInt(0, generalMap.getSettings().getYCells());
            if(place(new Animal(this, new Vector2d(x, y), generalMap.getSettings().getInitialFood()))) {
                i++;
            }
        }
    }

    public Animal getAnimalAt(Vector2d position) {
        return field.get(position).getFirst();
    }

    public GeneralMap getGeneralMap() {
        return generalMap;
    }

    public int getAnimalNumber() {
        int n = 0;
        for(AnimalCell AC : field.values()) {
            n += AC.getList().size();
        }
        return n;
    }

    public void addToBirthQueue(Animal animal) {
        waitingToBeBorn.add(animal);
    }

    private void placeJustBornAnimals() {
        for(Animal animal : waitingToBeBorn) {
            while(isOccupied(animal.getPosition())) {
                animal.setPosition(closestNotOccupied(animal.getPosition()));
                // Above code is used when multiple births occur at the same place
            }
            place(animal);
        }
        waitingToBeBorn.clear();
    }


    public boolean isInBounds(Vector2d position) {
        if (lowerLeftBoundary.x <= position.x && position.x <= upperRightBoundary.x)
            if (lowerLeftBoundary.y <= position.y && position.y <= upperRightBoundary.y)
                return true;
        return false;
    }



    public boolean isOccupied(Vector2d position) {
        return this.field.containsKey(position);
    }


    public boolean place(Animal animal) {
        if(!isInBounds(animal.getPosition())) {
            throw new IllegalArgumentException("Animal placed out of bounds at "+animal.getPosition()+" map bound is "+lowerLeftBoundary+" and "+upperRightBoundary);
        }

        if (isOccupied(animal.getPosition()))
            return false;

        generalMap.getAnimalAndGrassObserver().animalBorn(animal);
        animal.addObserver(generalMap.getAnimalAndGrassObserver());

        AnimalCell newCell = new AnimalCell(this, animal.getPosition(), generalMap.getAnimalAndGrassObserver(), generalMap.getSettings());

        newCell.add(animal);
        this.field.put(animal.getPosition(), newCell);
        return true;
    }


    public void run() {
        for(AnimalCell cell : this.field.values()) {
            for(Animal animal : cell.getList()) {
                animal.resetMovementFlag();
            }
        }

        this.temporaryField = (HashMap<Vector2d, AnimalCell>) this.field.clone();

        for(AnimalCell cell : this.field.values()) {
            cell.run();
        }

        removeEmptyCells();

        this.field = (HashMap<Vector2d, AnimalCell>) this.temporaryField.clone();

        placeJustBornAnimals();
    }


    private void removeEmptyCells() {
        for(AnimalCell ac : field.values()) {
            if(ac.isEmpty()) {
                temporaryField.remove(ac.getPosition());
            }
        }
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {

        if (oldPosition.equals(newPosition))
            return;

        if(!this.temporaryField.containsKey(newPosition)) {
            this.temporaryField.put(newPosition, new AnimalCell(this, newPosition, generalMap.getAnimalAndGrassObserver(), generalMap.getSettings()));
        }

        this.temporaryField.get(newPosition).add(animal);
    }


    // Performs search by spiraling out of the starting position.
    // As a result a directly neighbouring position is not considered further away than diagonal neighbour.
    public Vector2d closestNotOccupied(Vector2d position) {
        int stepsToRotation = 1;
        int stepsCount = 1;
        int pathCount = 0;
        int rotation = 0;

        int x = position.x;
        int y = position.y;

        while (isOccupied(new Vector2d(x, y)) || !isInBounds(new Vector2d(x, y)))  {
            switch (rotation) {
                case 0:
                    y+=1;
                    break;
                case 1:
                    x+=1;
                    break;
                case 2:
                    y-=1;
                    break;
                case 3:
                    x-=1;
                    break;
            }
            stepsCount -= 1;
            if (stepsCount == 0) {
                pathCount += 1;
                if (pathCount == 2) {
                    pathCount = 0;
                    stepsToRotation += 1;
                }
                stepsCount = stepsToRotation;
                rotation = (rotation + 1) % 4;
            }
        }
        return new Vector2d(x, y);
    }
}

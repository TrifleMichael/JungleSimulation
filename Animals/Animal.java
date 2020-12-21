package Animals;

import Maps.AnimalMap;
import Utilities.IPositionChangeObserver;
import Utilities.Vector2d;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Animal {
    boolean movementFlag = false;
    Vector2d position;
    int direction = ThreadLocalRandom.current().nextInt(0,9);
    ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    TrackingStatus trackingStatus = TrackingStatus.NONE;
    AnimalTracker animalTracker;


    final Genome genome;
    float food;
    final AnimalMap map;
    int lifespan = 0;
    int children = 0;



    public Animal(AnimalMap map, Vector2d position, int food) {
        this.map = map;
        this.position = position;
        addObserver(map);

        this.genome = new Genome();
        this.food = food;
    }


    public Animal getAnimalOfStrongerStatus(Animal animal) {
        if (this.trackingStatus.strongerStatus(animal.getTrackingStatus()) == this.trackingStatus)
            return this;
        else
            return animal;
    }


    public Animal(AnimalMap map, Animal parent1, Animal parent2, Vector2d position) {
        this.map = map;
        this.position = position;
        addObserver(map);

        // This code sets up the tracking status of the child.
        // It selects the parent with stronger connection to Chosen animal,
        // Next it assigns adequate status to child.
        switch(parent1.getTrackingStatus().strongerStatus(parent2.getTrackingStatus())) {
            case NONE:
                break;
            case CHOSEN:
                setAnimalTracker(parent1.getAnimalOfStrongerStatus(parent2).getAnimalTracker());
                setTrackingStatus(TrackingStatus.CHILD);
                break;
            case CHILD,DESCENDANT:
                setAnimalTracker(parent1.getAnimalOfStrongerStatus(parent2).getAnimalTracker());
                setTrackingStatus(TrackingStatus.DESCENDANT);
                break;
        }

        this.genome = new Genome(parent1.getGenome(), parent2.getGenome());
    }

    public int getLifespan() {
        return lifespan;
    }

    public boolean ifMoved() {
        return this.movementFlag;
    }
    
    public void resetMovementFlag() {
        this.movementFlag = false;
    }
    

    public float getNutrition() {
        return this.food;
    }

    public void feed(float nutrition) {
        this.food += nutrition;
    }

    public void becomeHungry(float nutrition) {
        this.food -= nutrition;
    }

    public void setHunger(float food) {
        this.food = food;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Genome getGenome() {
        return this.genome;
    }

    public boolean move() {
        lifespan++;
        this.direction = (this.direction + this.getGenome().getRandomGene()) % 8;

        Vector2d newPosition = new Vector2d(this.getPosition().x, this.getPosition().y);
        switch(this.direction) {
            case 0:
                newPosition = newPosition.add(new Vector2d(0, 1));
                break;
            case 1:
                newPosition = newPosition.add(new Vector2d(1, 1));
                break;
            case 2:
                newPosition = newPosition.add(new Vector2d(1, 0));
                break;
            case 3:
                newPosition = newPosition.add(new Vector2d(1, -1));
                break;
            case 4:
                newPosition = newPosition.add(new Vector2d(0, -1));
                break;
            case 5:
                newPosition = newPosition.add(new Vector2d(-1, -1));
                break;
            case 6:
                newPosition = newPosition.add(new Vector2d(-1, 0));
                break;
            case 7:
                newPosition = newPosition.add(new Vector2d(-1, 1));
                break;
        }

        this.movementFlag = true;


        if (this.map.isInBounds(newPosition)) {
            positionChanged(this.position, newPosition);
            this.position = newPosition;
            return true;
        }
        else {
            positionChanged(this.position, this.position);
            return false;
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : this.observers)
            observer.positionChanged(this, oldPosition, newPosition);
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }


    public int getChildren() {
        return children;
    }

    public AnimalTracker getAnimalTracker() {
        return animalTracker;
    }

    public void setAnimalTracker(AnimalTracker animalTracker) {
        this.animalTracker = animalTracker;
    }

    public void animalBred() {
        children++;
        switch (trackingStatus) {
            case NONE:
                return;
            case CHOSEN:
                animalTracker.chosenAnimalBred();
                return;
            case CHILD,DESCENDANT:
                animalTracker.descendantBred();
                return;
        }
    }

    public void animalDied() {
        switch (trackingStatus) {
            case NONE:
                return;
            case CHOSEN:
                animalTracker.chosenAnimalDied();
                return;
            case CHILD:
                animalTracker.childDied();
                return;
            case DESCENDANT:
                animalTracker.descendantDied();
                return;
        }
    }

    public TrackingStatus getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(TrackingStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

}

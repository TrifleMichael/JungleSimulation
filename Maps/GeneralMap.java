package Maps;

import Utilities.Settings;
import Animals.Animal;
import Animals.AnimalAndGrassObserver;
import Utilities.Vector2d;
import Animals.Grass;

// Class used to organize and connect lower level maps.
public class GeneralMap {
    AnimalAndGrassObserver animalAndGrassObserver;
    AnimalMap animalMap;
    Jungle jungle;
    Savanna savanna;
    Settings settings;
    int cycle = 0;


    public GeneralMap(AnimalAndGrassObserver animalAndGrassObserver, Settings settings) {
        this.animalAndGrassObserver = animalAndGrassObserver;
        this.settings = settings;
        animalMap = new AnimalMap(this);
        savanna = new Savanna(this);
        jungle = new Jungle(this);
    }

    public void deconstruct() {
        animalAndGrassObserver = null;
        animalMap.deconstruct();
        animalMap = null;
        jungle.deconstruct();
        jungle = null;
        savanna.deconstruct();
        savanna = null;
        settings = null;
    }

    public void run() {
        try {
            jungle.addPlants();
            savanna.addPlants();
            animalMap.run();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            System.exit(-1);
        }
        cycle++;
    }

    public void placeGrass(Grass grass) {
        if(savanna.isInJungleBounds(grass.getPosition().x, grass.getPosition().y)) {
            jungle.place(grass);
            animalAndGrassObserver.grassPlanted(grass.getPosition());
        }
        if(savanna.isInBounds(grass.getPosition())) {
            savanna.place(grass);
            animalAndGrassObserver.grassPlanted(grass.getPosition());
        }
    }

    public void removeGrass(Vector2d position) {
        if(savanna.isInJungleBounds(position.x, position.y)) {
            jungle.removePlantFrom(position);
        }
        if(savanna.isInBounds(position)) {
            savanna.removePlantFrom(position);
        }
    }

    public Vector2d getCoordinates(double X, double Y) {
        X/= settings.getScale();
        Y/= settings.getScale();
        return new Vector2d((int)X, (int)Y);
    }

    public Settings getSettings() {
        return settings;
    }

    public int getCycle() {
        return cycle;
    }

    public AnimalAndGrassObserver getAnimalAndGrassObserver() {
        return animalAndGrassObserver;
    }

    public int getAnimalNumber() {
        return animalMap.getAnimalNumber();
    }

    public int getGrassNumber() {
        return jungle.getGrassNumber()+savanna.getGrassNumber();
    }



    public boolean isAnimalAt(Vector2d position) {
        return  animalMap.isOccupied(position);
    }

    public Animal getAnimalAt(Vector2d position) {
        return animalMap.getAnimalAt(position);
    }

    public AnimalMap getAnimalMap() {
        return animalMap;
    }


    public boolean anyPlantAt(Vector2d position) {
        return jungle.isOccupied(position) || savanna.isOccupied(position);
    }


    public boolean isOccupied(Vector2d position) {
        return jungle.isOccupied(position) || savanna.isOccupied(position) || animalMap.isOccupied(position);
    }
}

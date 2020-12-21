package Animals;

import Animation.IAnimalCellObserver;
import Animation.SpriteManager;
import Utilities.IPositionChangeObserver;
import Utilities.StatKeeper;
import Utilities.Vector2d;

// Main class that records animal movement
// Connects with StatKeeper to organize simulation statistics and with SpriteManager to organize movement
// Is assigned as observer to each animal.
public class AnimalAndGrassObserver implements IAnimalCellObserver, IPositionChangeObserver {
    SpriteManager spriteManager;
    StatKeeper statKeeper;

    public AnimalAndGrassObserver(SpriteManager spriteManager, StatKeeper statKeeper) {
        this.spriteManager = spriteManager;
        this.statKeeper = statKeeper;
    }


    @Override
    public void animalDied(Animal animal) {
        spriteManager.removeSprite(animal);
        statKeeper.animalDied(animal);
    }

    @Override
    public void animalBorn(Animal animal) {
        spriteManager.addAnimalSprite(animal);
        spriteManager.updateTrackedAnimalSprite(animal);
        statKeeper.animalBorn(animal);
    }

    @Override
    public void grassEaten(Vector2d position) {
        spriteManager.removeGrassSpriteFrom(position);
        statKeeper.grassEaten();
    }

    @Override
    public void grassPlanted(Vector2d position) {
        spriteManager.addGrassSprite(position);
        statKeeper.grassPlanted();
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        spriteManager.animalMoved(animal, oldPosition, newPosition);
    }


}

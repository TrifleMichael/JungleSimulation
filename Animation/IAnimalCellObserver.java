package Animation;

import Animals.Animal;
import Utilities.Vector2d;

public interface IAnimalCellObserver {

    void animalDied(Animal animal);

    void animalBorn(Animal animal);

    void grassEaten(Vector2d position);

    void grassPlanted(Vector2d position);
}

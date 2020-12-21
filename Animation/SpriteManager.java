package Animation;

import Utilities.Settings;
import Animals.Animal;
import Utilities.Vector2d;
import Animals.Grass;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.HashMap;

public class SpriteManager {
    HashMap<Animal, AnimalSprite> sprites = new HashMap<>();
    HashMap<Vector2d, GrassSprite> grasses = new HashMap<>();
    JungleSprite jungleSprite;
    BackGroundSprite backGroundSprite;
    Pane root;
    final Settings settings;


    public void deconstruct() {
        sprites = null;
        grasses = null;
    }


    public SpriteManager(Pane root, Settings settings) {
        this.root = root;
        this.settings = settings;
    }

    public Shape getBackGroundSprite() {
        return backGroundSprite.getShape();
    }

    public void setupBackgroundSprites() {
        backGroundSprite = new BackGroundSprite(settings.getXCells()*settings.getScale(), settings.getYRes()-settings.getOptionsSectionSize());
        bindSprite(backGroundSprite);
        jungleSprite = new JungleSprite(settings.getJungleLowerLeft(), settings.getJungleUpperRight(), settings.getScale(), settings.getScale());
        bindSprite(jungleSprite);
    }


    public void animalMoved(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        sprites.get(animal).setAnimation(newPosition.x-oldPosition.x, newPosition.y-oldPosition.y);
    }

    public void addAnimalSprite(Animal animal) {
        AnimalSprite newSprite = new AnimalSprite(animal.getPosition().x, animal.getPosition().y, settings);
        sprites.put(animal, newSprite);
        bindSprite(newSprite);
    }

    public void addGrassSprite(Vector2d position) {
        GrassSprite newSprite = new GrassSprite(position.x, position.y, settings.getScale());
        grasses.put(position, newSprite);
        bindSprite(newSprite);
    }

    public void updateTrackedAnimalSprite(Animal animal) {
        switch (animal.getTrackingStatus()) {
            case CHOSEN:
                sprites.get(animal).setToChosenSprite();
                break;
            case CHILD:
                sprites.get(animal).setToChildSprite();
                break;
            case DESCENDANT:
                sprites.get(animal).setToDescendantSprite();
                break;
        }
    }

    public void removeSprite(Animal animal) {
        root.getChildren().remove(sprites.get(animal).getShape());
        sprites.remove(animal);
    }

    public void removeSprite(Grass grass) {
        root.getChildren().remove(grasses.get(grass.getPosition()).getShape());
        grasses.remove(grass.getPosition());
    }

    public void removeGrassSpriteFrom(Vector2d position) {
        root.getChildren().remove(grasses.get(position).getShape());
        grasses.remove(position);
    }

    public void setToMostPopularGenomeSprite(Animal animal) {
         sprites.get(animal).setToMostPopularGenomeSprite();
    }

    public void setToOriginalSprite(Animal animal) {
        sprites.get(animal).setToOriginalSprite();
    }

    public void playOneTick() {
        for(AnimalSprite sprite : sprites.values()) {
            sprite.playAnimation();
        }
    }

    public void bindSprite(ISprite sprite) {
        root.getChildren().add(sprite.getShape());
    }

}

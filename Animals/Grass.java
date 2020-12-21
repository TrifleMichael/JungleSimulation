package Animals;

import Utilities.Vector2d;

public class Grass {
    final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }

    public Vector2d getPosition() {
        return this.position;
    }

}

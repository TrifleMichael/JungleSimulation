package Animation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BackGroundSprite implements ISprite {

    final Rectangle shape;

    public BackGroundSprite(int xRes, int yRes) {
        shape = new Rectangle(0,0, xRes, yRes);
        shape.setFill(Color.GREEN);
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}

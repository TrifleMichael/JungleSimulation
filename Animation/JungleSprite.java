package Animation;

import Utilities.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class JungleSprite implements ISprite {

    final Rectangle shape;

    public JungleSprite(Vector2d lowerLeft, Vector2d upperRight, float xScale, float yScale) {
        shape = new Rectangle(lowerLeft.x*xScale, lowerLeft.y*yScale, (upperRight.x-lowerLeft.x+1)*xScale, (upperRight.y-lowerLeft.y+1)*yScale);
        shape.setFill(Color.DARKGREEN);
    }


    @Override
    public Shape getShape() {
        return shape;
    }
}

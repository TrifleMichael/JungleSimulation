package Animation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GrassSprite implements ISprite {
    final Rectangle shape;

    public GrassSprite(int x, int y, int scale) {
        this.shape = new Rectangle(x*scale, y*scale, scale, scale);
        this.shape.setFill(Color.LIGHTGREEN);
    }

    public Rectangle getShape() {
        return this.shape;
    }
}

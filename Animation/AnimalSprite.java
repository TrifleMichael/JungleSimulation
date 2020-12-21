package Animation;

import Utilities.Settings;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class AnimalSprite implements ISprite {
    final int r;
    final Circle shape;
    final int scale;
    Color originalColor = Color.BROWN;

    final TranslateTransition moveAnimation = new TranslateTransition();

    public AnimalSprite(int x, int y, Settings settings) {
        this.scale = settings.getScale();
        r = scale/2;

        this.shape = new Circle();
        this.shape.setCenterX(x*scale+scale/2);
        this.shape.setCenterY(y*scale+scale/2);
        this.shape.setRadius(r);
        this.shape.setFill(Color.BROWN);

        this.moveAnimation.setNode(shape);
        this.moveAnimation.setDuration(settings.getFrameLength());
        this.moveAnimation.setCycleCount(1);
    }

    public Shape getShape() {
        return shape;
    }

    public void setAnimation(int xShift, int yShift) {

        switch (xShift) {
            case 1:
                this.moveAnimation.setByX(scale);
                break;
            case 0:
                this.moveAnimation.setByX(0);
                break;
            case -1:
                this.moveAnimation.setByX(-scale);
                break;
        }
        switch (yShift) {
            case 1:
                this.moveAnimation.setByY(scale);
                break;
            case 0:
                this.moveAnimation.setByY(0);
                break;
            case -1:
                this.moveAnimation.setByY(-scale);
                break;
        }
    }

    public void setToChosenSprite() {
        originalColor = Color.GOLD;
        shape.setFill(Color.GOLD);
    }

    public void setToChildSprite() {
        originalColor = Color.BLUE;
        shape.setFill(Color.BLUE);
    }

    public void setToDescendantSprite() {
        originalColor = Color.ORANGE;
        shape.setFill(Color.ORANGE);
    }

    public void setToMostPopularGenomeSprite() {
        shape.setFill(Color.LIGHTCYAN);
    }

    public void setToOriginalSprite() {
        shape.setFill(originalColor);
    }

    public void playAnimation() {
        this.moveAnimation.play();
    }
}

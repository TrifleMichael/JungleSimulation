package App;


import Utilities.Settings;
import Utilities.TextBox;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.stage.Stage;


// Panel with main view of the simulation.
public class SimulationView {



    final Pane root = new Pane();
    boolean isPaused = false;
    boolean positionPopupActive = false;
    final AppHandler appHandler;
    final Settings settings;
    TextBox textBox;


    public SimulationView(Settings settings) {
        this.settings = settings;
        appHandler = new AppHandler(this, settings);
    }



    public void main() {

        appHandler.setup();

        // BASIC SETUP

        Scene scene = new Scene(root, settings.getXRes(), settings.getYRes());



        // BUTTON SETUP

        Button pauseButton = new Button("Toggle pause");
        EventHandler<ActionEvent> pauseEvent =
                e -> switchPause();

        pauseButton.setOnAction(pauseEvent);
        pauseButton.setLayoutX(65);
        pauseButton.setLayoutY(settings.getYRes()-settings.getOptionsSectionSize() + 2);
        root.getChildren().add(pauseButton);


        Button updateGenomeOverlay = new Button("Show most popular genome");
        EventHandler<ActionEvent> genomeOverlayEvent =
                e -> appHandler.toggleColorForMostPopularGenome();

        updateGenomeOverlay.setOnAction(genomeOverlayEvent);
        updateGenomeOverlay.setLayoutX(25);
        updateGenomeOverlay.setLayoutY(settings.getYRes()-settings.getOptionsSectionSize() + 27);
        root.getChildren().add(updateGenomeOverlay);



        Button hideGenomeOverlay = new Button("Hide genome overlay");
        EventHandler<ActionEvent> hideGenomeOverlayEvent =
                e -> appHandler.hideMostPopularGenomeSprite();

        hideGenomeOverlay.setOnAction(hideGenomeOverlayEvent);
        hideGenomeOverlay.setLayoutX(40);
        hideGenomeOverlay.setLayoutY(settings.getYRes()-settings.getOptionsSectionSize() + 52);
        root.getChildren().add(hideGenomeOverlay);




        // BOTTOM INFO BAR

        textBox = new TextBox(appHandler, settings);
        textBox.bindToRoot(root);

        // CREATING POPUP

        ClickPopup clickPopup = new ClickPopup(appHandler, this);


        // CREATING MOUSE CLICK EVENT
        EventHandler<MouseEvent> eventHandler = e -> {
            if (!positionPopupActive && e.getY() < settings.getYRes()-settings.getOptionsSectionSize() ) {
                if (e.getX() <= settings.getXCells() * settings.getScale())
                    clickPopup.onClick(e.getX(), e.getY(), appHandler.getAnimalTracker());
            }
        };
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


        // ANIMATION SETUP

        Rectangle syncRect = new Rectangle(0,0,0, 0);
        TranslateTransition syncAnim = new TranslateTransition(settings.getFrameLength(), syncRect);
        syncAnim.setByX(50);
        syncAnim.setCycleCount(2);
        syncAnim.setAutoReverse(true);

        syncAnim.play();

        syncAnim.setOnFinished(e -> playOneTick(syncAnim, textBox));


        root.getChildren().addAll(syncRect);

        Stage newWindow = new Stage();
        newWindow.setTitle("Simulation");
        newWindow.setScene(scene);
        newWindow.show();




        newWindow.setOnCloseRequest(we -> {
            isPaused = true;
            appHandler.deconstruct();
            textBox = null;
            newWindow.close();
        });

    }


    private void playOneTick(TranslateTransition syncAnim, TextBox textBox) {
        if (!isPaused) {
            textBox.update();
            appHandler.runOneTick();
        }
        syncAnim.play();
    }

    public void togglePopupActive() {
        positionPopupActive = !positionPopupActive;
    }

    public Pane getRoot() {
        return root;
    }

    private void switchPause() {
        isPaused = !isPaused;
    }

    public void expandTextBox() {
        textBox.showAnimalInfo(root);
    }

}

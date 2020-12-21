package App;

import Animals.Animal;
import Animals.AnimalTracker;
import Animals.TrackingStatus;
import Utilities.Vector2d;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Shows up after clicking on map
public class ClickPopup {
    SimulationView simulationView;
    AppHandler appHandler;

    public ClickPopup(AppHandler appHandler, SimulationView simulationView) {
        this.appHandler = appHandler;
        this.simulationView = simulationView;
    }



    public void onClick(double x, double y, AnimalTracker animalTracker) {
        simulationView.togglePopupActive();

        Pane secondaryLayout = new Pane();
        Vector2d position = appHandler.getCoordinates(x, y);
        Scene secondScene = new Scene(secondaryLayout, 300, 200);

        Text coordinatesDescription = new Text("Position: "+position);
        coordinatesDescription.setX(10);
        coordinatesDescription.setY(30);
        secondaryLayout.getChildren().add(coordinatesDescription);

        Text animalHunger;
        Text animalGenome;
        if (appHandler.isAnimalAt(position)) {
            Animal animal = appHandler.getAnimalAt(position);
            animalHunger = new Text("Animal hunger : "+animal.getNutrition());
            animalGenome = new Text("Genome: "+animal.getGenome());


            if (!animalTracker.animalAlreadyChosen()) {


                TextField inputAnimalName = new TextField("Set animal name");
                inputAnimalName.setLayoutX(10);
                inputAnimalName.setLayoutY(75);
                secondaryLayout.getChildren().add(inputAnimalName);

                Button trackButton = new Button("Track animal");
                EventHandler<ActionEvent> event =
                        e -> {
                            animalTracker.setAnimal(animal);
                            animal.setTrackingStatus(TrackingStatus.CHOSEN);
                            animal.setAnimalTracker(animalTracker);
                            appHandler.getSpriteManager().updateTrackedAnimalSprite(animal);

                            animalTracker.setAnimalName(inputAnimalName.getText());
                            simulationView.expandTextBox();
                        };
                trackButton.setOnAction(event);
                trackButton.setLayoutX(10);
                trackButton.setLayoutY(110);
                secondaryLayout.getChildren().add(trackButton);
            }
        }
        else {
            animalHunger = new Text("No animal found");
            animalGenome = new Text("");
        }

        animalGenome.setX(10);
        animalGenome.setY(45);
        secondaryLayout.getChildren().add(animalGenome);

        animalHunger.setX(10);
        animalHunger.setY(60);
        secondaryLayout.getChildren().add(animalHunger);




        // New window (stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);



        // Set position of second window, related to primary window.
        newWindow.setX(200);
        newWindow.setY(100);

        newWindow.show();
        newWindow.setOnCloseRequest(we -> simulationView.togglePopupActive());
    }
}

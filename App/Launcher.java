package App;

import Utilities.Settings;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;


// First screen that launches simulations.
public class Launcher extends Application {


    @Override
    public void start(Stage stage){
        VBox root = new VBox();
        root.setAlignment(CENTER);
        root.setSpacing(10);
        Scene scene = new Scene(root, 400, 550);


        stage.setScene(scene);
        stage.setTitle("Simulation launcher");
        stage.show();

        Button launchSimulationBtn = new Button("Launch simulation with default settings");
        EventHandler<ActionEvent> defaultSimulation =
                e -> launchSimulation(new Settings());

        launchSimulationBtn.setOnAction(defaultSimulation);
        root.getChildren().add(launchSimulationBtn);





        Text sizeX = new Text("Input map width");
        root.getChildren().add(sizeX);

        TextField sizeXField = new TextField();
        root.getChildren().add(sizeXField);


        Text sizeY = new Text("Input map height");
        root.getChildren().add(sizeY);

        TextField sizeYField = new TextField();
        root.getChildren().add(sizeYField);


        Text startEnergy = new Text("Starting energy for each animal");
        root.getChildren().add(startEnergy);

        TextField startEnergyField = new TextField();
        root.getChildren().add(startEnergyField);

        Text moveEnergy = new Text("Energy required for movement");
        root.getChildren().add(moveEnergy);

        TextField moveEnergyField = new TextField();
        root.getChildren().add(moveEnergyField);

        Text plantEnergy = new Text("Energy extracted from each plant");
        root.getChildren().add(plantEnergy);

        TextField plantEnergyField = new TextField();
        root.getChildren().add(plantEnergyField);

        Text jungleRatio = new Text("Savanna to jungle ratio");
        root.getChildren().add(jungleRatio);

        TextField jungleRatioField = new TextField();
        root.getChildren().add(jungleRatioField);

        Text timePerFrame = new Text("Time per frame (milliseconds)");
        root.getChildren().add(timePerFrame);

        TextField timePerFrameField = new TextField();
        root.getChildren().add(timePerFrameField);



        Button launchSimulationWithNewSettings = new Button("Launch with custom settings");
        EventHandler<ActionEvent> launchCustomSimulation =
                e -> {
                    Boolean anyError = false;
                    Settings settings = new Settings();
                    try {
                        settings.setMapHeight(parseToInt(sizeYField.getText()));
                        settings.setMapWidth(parseToInt(sizeXField.getText()));
                        settings.setStartingEnergy(parseToInt(startEnergyField.getText()));
                        settings.setMoveEnergy(parseToInt(moveEnergyField.getText()));
                        settings.setPlantNutrition(parseToInt(plantEnergyField.getText()));
                        settings.setTimePerFrame(parseToInt(timePerFrameField.getText()));
                        try {
                            settings.setSavannaToJungleRatio(parseToFloat(jungleRatioField.getText()));
                        } catch (IllegalArgumentException iae) {
                            System.out.println("Illegal argument exception");
                            ErrorPopup.main(iae.getMessage());
                            anyError = true;
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Number Format Exception");
                        ErrorPopup.main("Incorrect number format\n"+nfe.getMessage());
                        anyError = true;
                }
                    if(!anyError)
                        launchSimulation(settings);
                };

        launchSimulationWithNewSettings.setOnAction(launchCustomSimulation);
        root.getChildren().add(launchSimulationWithNewSettings);
    }

    private int parseToInt(String string) throws NumberFormatException {
            return Integer.parseInt(string);
    }

    private float parseToFloat(String string) throws NumberFormatException  {
        return Float.parseFloat(string);
    }

    private void launchSimulation(Settings settings) {
        SimulationView newSimulation = new SimulationView(settings);
        newSimulation.main();
    }

}

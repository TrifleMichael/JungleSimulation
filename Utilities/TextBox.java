package Utilities;

import Animals.AnimalTracker;
import App.AppHandler;
import javafx.scene.layout.Pane;


// Organizes and updates information in SimulationView
public class TextBox {
    final AppHandler appHandler;


    boolean chosenAnimalIsAlive = true;
    int lastDayOfChosenAnimal = 0;


    TextLine animalNumber;
    TextLine grassNumber;
    TextLine averageLifespan;
    TextLine mostPopularGene;
    TextLine mostPopularGeneInstances;
    TextLine cycle;
    TextLine averageEnergy;
    TextLine averageChildren;
    TextLine getMostPopularGenome;

    TextLine children;
    TextLine descendants;
    TextLine animalStatus;

    private StatKeeper getStatKeeper() {
        return appHandler.getStatKeeper();
    }

    private AnimalTracker getAnimalTracker() {
        return appHandler.getAnimalTracker();
    }

    public TextBox(AppHandler appHandler, Settings settings) {
        this.appHandler = appHandler;

        animalNumber = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+15);
        animalNumber.setText("There are ", String.valueOf(getStatKeeper().getAnimalNumber()) ," animals present.");

        grassNumber = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+30);
        grassNumber.setText("There are ", String.valueOf(getStatKeeper().getGrassNumber()) ," grass sprouts.");

        averageLifespan = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+45);
        averageLifespan.setText("The average lifespan is ", String.valueOf(getStatKeeper().getAverageLifeSpan()) ,"");

        averageEnergy = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+60);
        averageEnergy.setText("Average energy is ", String.valueOf(getStatKeeper().getAverageEnergy()) ,".");

        averageChildren = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+75);
        averageChildren.setText("Average animal has ", String.valueOf(getStatKeeper().getAverageChildren()), " children.");

        getMostPopularGenome = new TextLine(600, settings.getYRes()-settings.getOptionsSectionSize()+90);
        getMostPopularGenome.setText("The most popular genome is ", String.valueOf(getStatKeeper().getMostPopularGenome()), ".");



        mostPopularGene = new TextLine(230, settings.getYRes()-settings.getOptionsSectionSize()+60);
        mostPopularGene.setText("The most popular gene is ", String.valueOf(getStatKeeper().getMostPopularGene()) ,"");

        mostPopularGeneInstances = new TextLine(230, settings.getYRes()-settings.getOptionsSectionSize()+75);
        mostPopularGeneInstances.setText("Average animal has ", String.valueOf(getStatKeeper().getMostPopularGeneInstances()) ," instances of that gene.");




        cycle = new TextLine(60, settings.getYRes()-5);
        cycle.setText("Current cycle is ", String.valueOf(getStatKeeper().getCycles()),".");


        animalStatus = new TextLine(230, settings.getYRes()-settings.getOptionsSectionSize()+15);
        animalStatus.setText("", "", "");

        children = new TextLine(230, settings.getYRes()-settings.getOptionsSectionSize()+30);
        children.setText("", getAnimalTracker().getAnimalName()+" has "+getAnimalTracker().getChildren(), " children");

        descendants = new TextLine(230, settings.getYRes()-settings.getOptionsSectionSize()+45);
        descendants.setText("", getAnimalTracker().getAnimalName()+" has "+getAnimalTracker().getDescendants(), " descendants");
    }

    public void bindToRoot(Pane root) {
        root.getChildren().add(animalNumber.getText());
        root.getChildren().add(grassNumber.getText());
        root.getChildren().add(averageLifespan.getText());
        root.getChildren().add(mostPopularGene.getText());
        root.getChildren().add(mostPopularGeneInstances.getText());
        root.getChildren().add(cycle.getText());
        root.getChildren().add(averageEnergy.getText());
        root.getChildren().add(averageChildren.getText());
        root.getChildren().add(getMostPopularGenome.getText());
    }

    public void update() {
        animalNumber.update(String.valueOf(getStatKeeper().getAnimalNumber()));
        grassNumber.update(String.valueOf(getStatKeeper().getGrassNumber()));
        averageLifespan.update(String.valueOf(getStatKeeper().getAverageLifeSpan()));
        mostPopularGene.update(String.valueOf(getStatKeeper().getMostPopularGene()));
        mostPopularGeneInstances.update(String.valueOf(getStatKeeper().getMostPopularGeneInstances()));
        cycle.update(String.valueOf(getStatKeeper().getCycles()));
        averageEnergy.update(String.valueOf(getStatKeeper().getAverageEnergy()));
        averageChildren.update(String.valueOf(getStatKeeper().getAverageChildren()));
        getMostPopularGenome.update(getStatKeeper().getMostPopularGenome().toString());

        if(getAnimalTracker().isAnimalAlive())
            animalStatus.update(getAnimalTracker().getAnimalName()+" is alive and well.");

        else if(chosenAnimalIsAlive) { // If chosen animal just died
            chosenAnimalIsAlive = false;
            lastDayOfChosenAnimal = getStatKeeper().getCycles();
            animalStatus.update(getAnimalTracker().getAnimalName()+" is dead as of "+lastDayOfChosenAnimal);
        }
        else
            animalStatus.update(getAnimalTracker().getAnimalName()+" is dead as of "+lastDayOfChosenAnimal);

        children.update(getAnimalTracker().getAnimalName()+" has "+getAnimalTracker().getChildren());
        descendants.update(getAnimalTracker().getAnimalName()+" has "+getAnimalTracker().getDescendants());
    }

    public void showAnimalInfo(Pane root) {
        root.getChildren().add(children.getText());
        root.getChildren().add(descendants.getText());
        root.getChildren().add(animalStatus.getText());
    }
}

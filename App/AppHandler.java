package App;

import Animals.*;
import Animation.SpriteManager;
import Maps.AnimalCell;
import Maps.GeneralMap;
import Utilities.*;


// Main hub for program,
// Connects frontend with backend.
public class AppHandler {
    SimulationView simulationView;
    SpriteManager spriteManager;
    final StatKeeper statKeeper = new StatKeeper(this);
    final AnimalAndGrassObserver animalAndGrassObserver;
    final Settings settings;
    final AnimalTracker animalTracker = new AnimalTracker();
    final StatRecorder statRecorder = new StatRecorder();

    final int cyclesPerSavingToFile = 50;

    GeneralMap generalMap;

    public void deconstruct() {
        generalMap.deconstruct();
        generalMap = null;
        spriteManager.deconstruct();
        spriteManager = null;
    }



    public AppHandler(SimulationView simulationView, Settings settings) {
        this.simulationView = simulationView;
        this.settings = settings;

        spriteManager = new SpriteManager(simulationView.getRoot(), settings);
        animalAndGrassObserver = new AnimalAndGrassObserver(spriteManager, statKeeper);
        generalMap = new GeneralMap(animalAndGrassObserver, settings);

        spriteManager.setupBackgroundSprites();
    }

    public void setup() {
        generalMap.getAnimalMap().placeAnimalAtRandom(settings.getInitialAnimalNumber());
        statKeeper.resetStats();
    }

    public void runOneTick() {
        generalMap.run();
        spriteManager.playOneTick();

        if(generalMap.getCycle() % cyclesPerSavingToFile == 0) {
            statRecorder.addLine("At cycle "+generalMap.getCycle()+":\n");
            statRecorder.addLine(statKeeper.getGrassNumber()+" grasses were grown.\n");
            statRecorder.addLine(statKeeper.getAnimalNumber()+" animals were born.\n");
            statRecorder.addLine("The average lifespan was "+statKeeper.getAverageLifeSpan()+".\n");
            statRecorder.addLine("The average animal had "+statKeeper.getAverageChildren()+" children.\n");
            statRecorder.addLine("The average energy animal had was "+statKeeper.getAverageEnergy()+".\n\n");

        }
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public boolean isAnimalAt(Vector2d position) {
        return generalMap.isAnimalAt(position);
    }

    public Animal getAnimalAt(Vector2d position) {
        return generalMap.getAnimalAt(position);
    }

    public Vector2d getCoordinates(double x, double y) {
        return generalMap.getCoordinates(x, y);
    }

    public void toggleColorForMostPopularGenome() {
        Genome mostPopular = statKeeper.getMostPopularGenome();
        for(AnimalCell animalCell : generalMap.getAnimalMap().getAnimalCellMap().values()) {
            for(Animal animal : animalCell.getList()) {
                if (animal.getGenome().equals(mostPopular))
                    spriteManager.setToMostPopularGenomeSprite(animal);
                else
                    spriteManager.setToOriginalSprite(animal);
            }
        }
    }

    public void hideMostPopularGenomeSprite() {
        for(AnimalCell animalCell : generalMap.getAnimalMap().getAnimalCellMap().values()) {
            for(Animal animal : animalCell.getList()) {
                spriteManager.setToOriginalSprite(animal);
            }
        }
    }

    public Genome getMostPopularGenome() {return statKeeper.getMostPopularGenome();}


    public AnimalTracker getAnimalTracker() {
        return animalTracker;
    }

    public StatKeeper getStatKeeper() {
        return statKeeper;
    }


    public GeneralMap getGeneralMap() {
        return generalMap;
    }



}

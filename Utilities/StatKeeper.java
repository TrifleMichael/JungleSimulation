package Utilities;

import Animals.Animal;
import Animals.Genome;
import App.AppHandler;
import Maps.AnimalCell;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Keeps map statistics
public class StatKeeper {
    AppHandler appHandler;
    int grassNumber;
    int animalNumber;
    HashMap<Genome, Integer> genomes = new HashMap<>();



    public StatKeeper(AppHandler appHandler) {
        this.appHandler = appHandler;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    public int getAnimalNumber() {
        return animalNumber;
    }


    public void resetStats() {
        animalNumber = appHandler.getGeneralMap().getAnimalNumber();
        grassNumber = appHandler.getGeneralMap().getGrassNumber();
    }




    public void animalDied(Animal animal) {
        animalNumber--;

        Genome genome = animal.getGenome();
        if (genomes.get(genome)==1)
            genomes.remove(genome);
        else
            genomes.put(genome, genomes.get(genome)-1);
    }

    public void animalBorn(Animal animal) {
        animalNumber++;

        Genome genome = animal.getGenome();
        if (!genomes.containsKey(genome))
            genomes.put(genome, 1);
        else
            genomes.put(animal.getGenome(), genomes.get(genome)+1);
    }

    public Genome getMostPopularGenome() {
        Genome genome = null;
        int max = 0;
        Iterator it = genomes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((Integer)pair.getValue() > max) {
                max = (Integer)pair.getValue();
                genome = (Genome)pair.getKey();
            }
        }
        return genome;
    }

    public void grassEaten() {
        grassNumber--;
    }

    public void grassPlanted() {
        grassNumber++;
    }

    public float getAverageEnergy() {
        int n = 0;
        int E = 0;
        for(AnimalCell AC : getAnimalCellMap().values()) {
            for(Animal animal : AC.getList()) {
                E+=animal.getNutrition();
                n++;
            }
        }
        if (n==0)
            return 0;
        else
            return (float)E/n;
    }

    public double getAverageLifeSpan() {
        int n = 0;
        double avgLen = 0;
        for(AnimalCell animalCell : getAnimalCellMap().values()) {
            for(Animal animal : animalCell.getList()) {
                avgLen += animal.getLifespan();
                n++;
            }
        }
        if(n == 0)
            return 0;
        else
            return avgLen/n;
    }


    public int getMostPopularGene() {
        int[] genes = new int[8];
        for(int i = 0; i < 8; i++) {
            genes[i] = 0;
        }

        for(AnimalCell animalCell : getAnimalCellMap().values()) {
            for(Animal animal : animalCell.getList()) {
                for(int gene : animal.getGenome().getGenomeSequence()) {
                    genes[gene]++;
                }
            }
        }

        int i = 0;
        int max = 0;
        for(int j = 0; j < 8; j++) {
            if(genes[j] > max) {
                max = genes[j];
                i = j;
            }
        }

        return i;
    }

    public double getMostPopularGeneInstances() {
        int[] genes = new int[8];
        for(int i = 0; i < 8; i++) {
            genes[i] = 0;
        }

        for(AnimalCell animalCell : getAnimalCellMap().values()) {
            for(Animal animal : animalCell.getList()) {
                for(int gene : animal.getGenome().getGenomeSequence()) {
                    genes[gene]++;
                }
            }
        }

        int i = 0;
        int max = 0;
        for(int j = 0; j < 8; j++) {
            if(genes[j] > max) {
                max = genes[j];
                i = j;
            }
        }
        if (getAnimalNumber()==0) return 0;
        else return (double)genes[i]/getAnimalNumber();
    }

    public float getAverageChildren() {
        int n = 0;
        int c = 0;
        for(AnimalCell AC : getAnimalCellMap().values()) {
            for(Animal animal : AC.getList()) {
                c+= animal.getChildren();
                n++;
            }
        }
        if (n==0) return 0;
        else return (float)c/n;
    }

    private HashMap<Vector2d, AnimalCell> getAnimalCellMap() {
        return appHandler.getGeneralMap().getAnimalMap().getAnimalCellMap();
    }

    public int getCycles() {
        return appHandler.getGeneralMap().getCycle();
    }


}

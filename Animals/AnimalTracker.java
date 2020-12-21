package Animals;


// Tracks information regarding animal chosen by button press and its descendants.
// Is assigned to each animal.
public class AnimalTracker {
    Animal chosenAnimal = null;
    boolean isAnimalChosen = false;
    boolean isAnimalAlive = true;
    int children = 0;
    int descendants = 0;
    String animalName = "";



    public boolean animalAlreadyChosen() {
        return isAnimalChosen;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
        isAnimalChosen = true;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimal(Animal animal) {
        isAnimalChosen = true;
        this.chosenAnimal = animal;
    }

    public void chosenAnimalBred() {
        children++;
    }

    public boolean isAnimalAlive() {
        return isAnimalAlive;
    }

    public void descendantBred() {
        descendants++;
    }

    public void chosenAnimalDied() {
        isAnimalAlive = false;
    }

    public void childDied() {
        children--;
    }

    public void descendantDied() {
        descendants--;
    }

    public Animal getChosenAnimal() {
        return chosenAnimal;
    }

    public int getChildren() {
        return children;
    }

    public int getDescendants() {
        return descendants;
    }
}

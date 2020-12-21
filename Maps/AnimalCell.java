package Maps;

import Animals.Animal;
import Utilities.Vector2d;
import Animation.IAnimalCellObserver;
import Utilities.Settings;

import java.util.ArrayList;
import java.util.Iterator;

// Animals cells are kept in animal map
// Represented by unique Vector2d, handles all actions restricted to one cell such as:
// - animal breeding
// - animal feeding
// - starvation
// and others
public class AnimalCell {
    ArrayList<IAnimalCellObserver> observers = new ArrayList<>();
    final AnimalMap map;
    ArrayList<Animal> list = new ArrayList<>();
    ArrayList<Animal> temporaryList = new ArrayList<>();
    final Settings settings;
    final float minEnergyToBreed;
    final Vector2d position;

    public AnimalCell(AnimalMap map, Vector2d position, IAnimalCellObserver observer, Settings settings) {
        this.observers.add(observer);
        this.map = map;
        this.position = position;
        this.settings = settings;
        minEnergyToBreed = settings.getMinEnergyToBreed();
    }

    // Main loop for cell.
    public void run() {

        // Feeding animals
        if (map.getGeneralMap().anyPlantAt(this.position)) {
            feedAnimals(settings.getPlantNutrition());

            for(IAnimalCellObserver observer : observers) {
                observer.grassEaten(this.position);
            }
            map.getGeneralMap().removeGrass(this.position);
        }
        makeAnimalsHungry();

        if (canBreed()) {
            map.addToBirthQueue(createBaby());
        }
        removeStarved();
        moveAnimals();
    }


    private void makeAnimalsHungry() {
        for(Animal animal : this.list) {
            animal.becomeHungry(settings.getStarvationPerCycle());
        }
    }

    private void removeStarved() {
        temporaryList = (ArrayList<Animal>) list.clone();
        for(Animal animal : list) {
            if (animal.getNutrition() <= 0) {
                animal.animalDied();
                temporaryList.remove(animal);

                for(IAnimalCellObserver observer : observers) {
                    observer.animalDied(animal);
                }
            }
        }
        list = temporaryList;

    }
    private Animal selectStrongest() {
        Animal strongest = getFirst();
        for(Animal animal : this.list) {
            if (animal.getNutrition() > strongest.getNutrition())
                strongest = animal;
        }
        return strongest;
    }

    private int numOfStrongest() {
        float nutrition = -1;
        int multiple = 0;
        for(int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getNutrition() == nutrition)
                multiple++;
            if (this.list.get(i).getNutrition() > nutrition) {
                multiple = 1;
                nutrition = this.list.get(i).getNutrition();
            }
        }
        return multiple;
    }

    private void feedAnimals(float nutrition) {
        Animal strongest =  selectStrongest();
        float strongestNum = numOfStrongest();

        if (strongestNum == 1)
            strongest.feed(nutrition);
        else {
            for(Animal animal : this.list)
                if (animal.getNutrition() == strongest.getNutrition())
                    animal.feed(nutrition/strongestNum);
        }
    }

    private void moveAnimals() {
        Iterator<Animal> it = this.list.iterator();
        while(it.hasNext()) {
            Animal animal = it.next();
            if (!animal.ifMoved()) {
                if(animal.move()) {
                    it.remove();
                }
            }
        }
    }

    private boolean canBreed() {
        if (this.list.size() < 2)
            return false;
        int capable = 0;
        for(int i = 0; i < this.list.size() && capable < 2; i++) {
            if (this.list.get(i).getNutrition() >= this.minEnergyToBreed)
                capable++;
        }
        return capable >= 2;
    }

    private Animal createBaby() {

        Animal first;
        Animal second;

        if (this.list.get(0).getNutrition() > this.list.get(1).getNutrition()) {
            first = this.list.get(0);
            second = this.list.get(1);
        }
        else  {
            second = this.list.get(0);
            first = this.list.get(1);
        }

        for(int i = 2; i < this.list.size(); i++) {
            if (this.list.get(i).getNutrition() >= first.getNutrition()) {
                second = first;
                first = this.list.get(i);
            }
            else if (this.list.get(i).getNutrition() >= second.getNutrition()) {
                second = this.list.get(i);
            }
        }

        Animal baby = new Animal(this.map, first, second, this.map.closestNotOccupied(this.position));
        baby.setHunger(first.getNutrition()/4+second.getNutrition()/4);
        first.becomeHungry(first.getNutrition()/4);
        second.becomeHungry(second.getNutrition()/4);

        // Informing AnimalTracker of breeding
        first.getAnimalOfStrongerStatus(second).animalBred();

        return baby;
    }


    public void add(Animal animal) {
        this.list.add(animal);
    }



    public Animal getFirst() {
        return this.list.get(0);
    }

    public ArrayList<Animal> getList() {
        return this.list;
    }

    public boolean isEmpty() {
        if(this.list.size() == 0)
            return true;
        else
            return false;
    }

    public int hashCode() {
        int code = 31;
        code *= this.position.y;
        code += this.position.x;
        return code;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public boolean equals(AnimalCell cell) {
        if (this.position.equals(cell.getPosition()))
            return true;
        return false;
    }

    public void addObserver(IAnimalCellObserver observer) {
        observers.add(observer);
    }
}

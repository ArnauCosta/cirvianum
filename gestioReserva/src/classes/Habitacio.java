package classes;

public class Habitacio{

    private  int numHabitacio, numPersonesMax;



    public Habitacio(int numHabitacio) {
        this.numHabitacio = numHabitacio;
    }

    public int getNumHabitacio() {
        return numHabitacio;
    }

    public void setNumHabitacio(int numHabitacio) {
        this.numHabitacio = numHabitacio;
    }

    public int getNumPersonesMax() {
        return numPersonesMax;
    }

    public void setNumPersonesMax(int numPersonesMax) {
        this.numPersonesMax = numPersonesMax;
    }

    @Override
    public String toString() {
        return numHabitacio+"--"+numPersonesMax;
    }
}
public class Person {
    private int weight;
    private boolean top;
    private int currentLevel;
    private int level;

    public Person(int weight, int currentLevel, int level){
        this.weight = weight;
        this.currentLevel = currentLevel;
        this.level = level;
        top = currentLevel < level ? true : false;
    }

    boolean getTop(){
        return this.top;
    }

    int getCurrentLevel(){
        return this.currentLevel;
    }


    public int getWeight() {
        return weight;
    }

    public int getLevel() {
        return level;
    }
}

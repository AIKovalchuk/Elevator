import java.util.ArrayList;
import java.util.TreeSet;

public class Elevator implements Runnable, Observer {
    private static final int MAX_WEiGHT = 500;
    private int current_weight = 0;
    private boolean wait = true;
    private int x = 0;
    private Manager manager;
    private ArrayList<Person> people;
    private ArrayList<Person> skip;
    private int[] callUp;
    private int[] callDown;
    private int[] callCar;
    private int call;
    private boolean running;
    private int task;
    private int id;

    public Elevator(int id) {
        manager = Manager.getInstance();
        /*people = new ArrayList<Person>();
        skip = new ArrayList<Person>();
        callUp = new int[20];
        callDown = new int[20];
        callCar = new int[20];
        call = 0;*/
        this.id = id;
        running = false;
    }

    @Override
    public void run() {
        /*while (true){
            while (call != 0 && people.isEmpty()){}

            try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            check();

            if(call == 0){
                for (int i = 0; i < callDown.length; i++){
                        if (callDown[i] == 1){
                            call = 2;
                            break;
                        }
                    }
                for (int i = 0; i < callUp.length; i++){
                        if (callUp[i] == 1){
                            call = 1;
                            break;
                        }
                    }
            }
            else if(call == 1) {
                moveUp();
            }
            else if(call==2){
                moveDown();
            }

        }
    }
*/
        while (running){
            move();
            Person person = manager.popPerson(x);
            if (person != null){
                task = person.getLevel();
                System.out.printf("Поехал на %d  \n",task);
            }
            else {
                running = false;
                continue;
            }
            move();
            running = false;
        }
    }

    private void move() {
        while (x != task){
            try {
                Thread.sleep(500);
                x++;
                System.out.printf("Лифт на %d этаже.\n", x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /*private void check(){
        for (Person per:people) {
            if (per.getLevel() == x){
                int indx = people.indexOf(per);
                current_weight -= per.getWeight();
                people.remove(indx);
            }
        }
        if (people.isEmpty()){
            call = 0;
        }
        if(callUp[x] != 0 || callDown[x] != 0){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (manager.checkLevel(x)){
                Person person = manager.getFromLevel(x);
                if (person != null){
                    if(person.getWeight()+current_weight < MAX_WEiGHT){
                        people.add(person);
                        current_weight += person.getWeight();
                        callCar[person.getLevel()] = 1;
                    }
                    else {
                        skip.add(person);
                    }
                }
            }
            if (call == 1){
                callUp[x]=0;
            }
            else if(call == 2){
                callDown[x] = 0;
            }
            callCar[x] = 0;
        }
    }*/

    @Override
    public void update(int level, boolean come) {
        /*if (come){
            callUp[level] += 1;
        }
        else {
            callDown[level] += 1;
        }*/
        if(!running){
            task = level;
            running = true;
            System.out.printf("Лифт вызван на этаж %d\n", level);
        }
    }

    /*private void moveUp(){
        if(x<20){
            this.x += 1;
            if(callCar[x] == 1){
                check();
            }
        }
    }*/

    /*private void moveDown(){
        if(x>0){
            this.x -= 1;
            if(callCar[x] == 1){
                check();
            }
        }
    }*/
}

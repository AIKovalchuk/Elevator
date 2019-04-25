import javax.imageio.ImageTranscoder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Manager implements Subject {
    private static Manager uniqueManager = new Manager();
    //private ArrayList<ArrayList<Person>> people;
    private ArrayList observers;
    private Person[][] pers;
    private int[] dop;

    private Manager(){
        //people = new ArrayList<ArrayList<Person>>(20);
        observers = new ArrayList();
        pers = new Person[20][20];
        dop = new int[20];
    }

    public static Manager getInstance(){
        return uniqueManager;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if( i >= 0){
            observers.remove(i);
        }
    }

    @Override
    public void notifyObserver(Person o, boolean top) {
        for (int i = 0; i < observers.size(); i++){
            Observer observer = (Observer)observers.get(i);
            observer.update(o.getCurrentLevel(),top);
        }
    }

    public synchronized void pushPersonToQueue(Person p){
        /*Iterator it = people.iterator();
        int i =0;
        while (it.hasNext() && i != p.getCurrentLevel()){
            it.next();
            i++;
        }
        if (i <= 20 && i >=0){
            ((ArrayList<Person>)it).add(p);
        }*/
        if(p!=null) {
            pers[p.getCurrentLevel()][dop[p.getCurrentLevel()]] = p;
            dop[p.getCurrentLevel()] += 1;
            notifyObserver(p,true);
        }
    }

    /*public Person getFromLevel(int x) {
        Iterator it = people.iterator();
        int i = 0;
        while (it.hasNext() && i < x){
            it.next();
            i++;
        }
        if(!((ArrayList<Person>)it).isEmpty()){
            Person person = ((ArrayList<Person>)it).get(0);
            ((ArrayList<Person>)it).remove(0);
            return person;
        }
        return null;
    }

    public boolean checkLevel(int x) {
        Iterator it = people.iterator();
        int i = 0;
        while (it.hasNext() && i < x){
            it.next();
            i++;
        }
        if (!((ArrayList<Person>)it).isEmpty()){
            return true;
        }
        return false;
    }*/

    public synchronized Person popPerson(int x) {
        /*if (!pers[x].isEmpty()){
            Person person =  pers[x].get(0);
            pers[x].remove(0);
            return person;
        }*/
        for (int i = 0; i < pers[x].length; i++){
            if (pers[x][i] != null){
                Person person = pers[x][i];
                pers[x][i] = null;
                return person;
            }
        }
        return null;
    }
}

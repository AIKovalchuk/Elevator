package Algoritms;

import java.util.ArrayList;
import java.util.Iterator;


public class Manager implements Runnable {
    private static Manager uniqueManager = new Manager();
    private ArrayList<Order> listOfOrder;
    private Elevator elevator1;
    private Elevator elevator2;
    private Thread thrElevator1;
    private Thread thrElevator2;
    private boolean running = true;

    private Manager(){
        listOfOrder = new ArrayList<>();
        elevator1 = new Elevator(1);
        elevator2 = new Elevator(2);
        thrElevator1 = new Thread(elevator1);
        thrElevator2 = new Thread(elevator2);
    }

    public static Manager getInstance() {
        return uniqueManager;
    }

    @Override
    public void run() {
        thrElevator1.start();
        thrElevator2.start();


        while (running){
            /*while (listOfOrder.isEmpty()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Order order = listOfOrder.get(0);
            listOfOrder.remove(listOfOrder.indexOf(order));
            if (elevator1.getDirection().equals(order.getDirection()) || elevator1.getDirection().equals(Direction.WAIT) ){
                    elevator1.update(order);
                    continue;

            } else if (elevator2.getDirection().equals(order.getDirection()) || elevator2.getDirection().equals(Direction.WAIT)){
                    elevator2.update(order);
                    continue;
            }
            else {
                push(order);
            }*/
        }


        try{
            thrElevator1.join();
            thrElevator2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void push(Order order) {
        listOfOrder.add(order);
    }

    public synchronized boolean isEmpty() { return listOfOrder.isEmpty(); }

    public synchronized Order pop() {
        Order order = listOfOrder.get(0);
        if (!listOfOrder.isEmpty()) {
            listOfOrder.remove(listOfOrder.indexOf(order));
        }
        return order;
    }

    public synchronized ArrayList<Order> popFromLevel(int level, Direction dir, int freeMass){
        ArrayList<Order> list = new ArrayList<>();
        Iterator<Order> it = listOfOrder.iterator();
        while (it.hasNext()){
            Order order = it.next();
            if (order.getFloor() == level && order.getDirection().equals(dir) && (order.getWeight()<freeMass)){
                freeMass -= order.getWeight();
                list.add(order);
                it.remove();
            }
        }
        return list;
    }

    public int getFloorEl1(){ return elevator1.getCurrent_floor(); }

    public int getWeight1(){ return elevator1.getWeight();}

    public int getFloorEl2(){ return elevator2.getCurrent_floor(); }

    public int getWeight2(){ return elevator2.getWeight();}

    public int getLevel1() {
        return elevator1.getCurrent_floor();
    }

    public int getLevel2() {
        return elevator2.getCurrent_floor();
    }

    public ArrayList<Order> getListOfOrder(){ return (ArrayList<Order>) listOfOrder.clone(); }

    public ArrayList<Order> getListOfOrderEl1(){ return elevator1.getListOfOrder();}

    public ArrayList<Order> getListOfOrderEl2(){ return elevator2.getListOfOrder();}

    public ArrayList<Order> getListOfinOrderEl1(){ return elevator1.getInElevator();}

    public ArrayList<Order> getListOfinOrderEl2(){ return elevator2.getInElevator();}

}

package Algoritms;

import java.util.ArrayList;
import java.util.Iterator;

public class Elevator implements Runnable {
    private int current_floor;
    private final int MAX_WEIGHT = 800;
    private int current_weight;
    private boolean running;
    private int id;
    private int floor;
    private ArrayList<Order> listOfOrder;
    private ArrayList<Order> inElevator;
    private boolean work;
    private Direction direction;
    private Order mainOrder;

    Elevator(int id){
        listOfOrder = new ArrayList<>();
        inElevator = new ArrayList<>();
        this.id = id;
        this.running = false;
        current_floor = (int)(Math.random() * 20 ) + 1;
        work = true;
        current_weight = 0;
        direction = Direction.WAIT;
    }

    @Override
    public void run() {
       /* while (work) {
            while (!listOfOrder.isEmpty() || !inElevator.isEmpty()) {
                if (!listOfOrder.isEmpty()){
                    Iterator<Order> it = listOfOrder.iterator();
                    while (it.hasNext()) {
                        Order order = it.next();
                        if (order.getFloor() == current_floor){
                            if(order.getWeight() + current_weight < MAX_WEIGHT){
                                current_weight += order.getWeight();
                                if ((order.getNext() > floor && direction.equals(Direction.UP)) ||
                                        (order.getNext() < floor && direction.equals(Direction.DOWN)) ){
                                    floor = order.getNext();
                                }
                                inElevator.add(order);
                                System.out.printf("Er1\n");
                                listOfOrder.remove(listOfOrder.indexOf(order));
                            }
                            else {
                                Manager.getInstance().push(order);
                                System.out.printf("Er2\n");
                                listOfOrder.remove(listOfOrder.indexOf(order));
                            }
                        }
                    }
                }
                if (!inElevator.isEmpty()){
                    Iterator<Order> it = inElevator.iterator();
                        while (it.hasNext()) {
                            Order order = it.next();
                            if (current_floor == order.getNext()){
                                inElevator.remove(inElevator.indexOf(order));
                                current_weight -= order.getWeight();
                            }
                        }
                    }
                if (current_floor < floor){
                    System.out.printf("ID #%d ++",id);
                    current_floor++;
                }
                else {
                    System.out.printf("ID #%d --",id);
                    current_floor--;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
       while (work){
           while (!Manager.getInstance().isEmpty()){
               mainOrder = Manager.getInstance().pop();
               current_weight += mainOrder.getWeight();
               floor= mainOrder.getFloor();
               while (current_floor != floor){
                  move();
               }
               
               floor = mainOrder.getNext();
               direction = mainOrder.getDirection();
               inElevator.add(mainOrder);
               
               while (current_floor != floor){
                   move();
                   ArrayList<Order> list =  Manager.getInstance().popFromLevel(
                           current_floor, direction, MAX_WEIGHT - current_weight);
                   if (list != null){
                       for (Order order:
                            list) {
                           if ((order.getNext() > floor && direction.equals(Direction.UP)) ||
                                   (order.getNext() < floor && direction.equals(Direction.DOWN)) ){
                               floor = order.getNext();
                           }
                           current_weight += order.getWeight();
                           inElevator.add(order);
                       }
                   }
                   Iterator<Order> it = inElevator.iterator();
                   while (it.hasNext()){
                       Order order = it.next();
                       if (order.getNext() == current_floor){
                           current_weight -= order.getWeight();
                           it.remove();
                       }
                   }
               }
           }
       }
    }

    private void move(){
        if (current_floor < floor){
            current_floor++;
        }
        else {
            current_floor--;
        }
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getFloor() {
        return floor;
    }

    public void update(Order order) {
        if (!running){
            floor = order.getNext();
            listOfOrder.add(order);
            running = true;
            direction = order.getDirection();
        }
        else if(order.getDirection().equals(direction)) {
            if((direction.equals(Direction.UP) && current_floor < order.getFloor()) ||
                    (direction.equals(Direction.DOWN) && current_floor > order.getFloor())){
                listOfOrder.add(order);
            }
            else {
                Manager.getInstance().push(order);
            }
        }
        else {
            Manager.getInstance().push(order);
        }
    }

    public int getCurrent_floor() { return current_floor; }

    public Direction getDirection() {
        return direction;
    }

    public int getWeight() {
        return current_weight;
    }

    public ArrayList<Order> getListOfOrder() {
        return (ArrayList<Order>)listOfOrder.clone();
    }

    public ArrayList<Order> getInElevator() {
        return (ArrayList<Order>)inElevator.clone();
    }
}

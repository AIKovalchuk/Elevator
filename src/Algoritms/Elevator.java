package Algoritms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;

public class Elevator implements Runnable {
    private int current_floor;
    private final int MAX_WEIGHT = 200;
    private int current_weight;
    private boolean running;
    private int id;
    private int floor;
    private ArrayList<Order> inElevator;
    private boolean work;
    private Direction direction;
    private Order mainOrder;
    private int CountEl = 0;

    Elevator(int id){
        inElevator = new ArrayList<>();
        this.id = id;
        this.running = false;
        current_floor = (int)(Math.random() * 10 ) + 1;
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
               try {
                   mainOrder = Manager.getInstance().pop();
                   floor = mainOrder.getFloor();
                   Manager.getInstance().push(mainOrder);
                   mainOrder = null;
               }
               catch(NullPointerException ex){
                   floor = current_floor;
                   continue;
               }
               while (current_floor != floor){
                  move();
               }
               if (inElevator.isEmpty()){
                   current_weight = 0;
               }
               
               try {
                   Order order = Manager.getInstance().pop();
                   if (order.getFloor() == current_floor) {
                       inElevator.add(order);
                       floor = order.getNext();
                       current_weight = order.getWeight();
                       System.out.printf("DEBUG: Order was takken. Direction to %d, elevator %d\n", order.getNext(), this.id);
                   }
                   else {
                       Manager.getInstance().push(order);
                   }
               }
               catch (NullPointerException ex){
                   floor = 0;
                   current_weight = 0;
                   continue;
               }
               
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
                           System.out.printf("DEBUG: Elevator %d get order: level to %d, weight %d\n", this.id, order.getFloor(), order.getWeight());
                       }
                   }
                   Iterator<Order> it = inElevator.iterator();
                   while (it.hasNext()){
                       Order order = it.next();
                       try {
                           if (order.getNext() == current_floor) {
                               current_weight -= order.getWeight();
                               it.remove();
                               System.out.printf("DEBUG: Man get out from elevator %d in level %d\n", this.id, this.current_floor);
                           }
                       }
                       catch (NullPointerException ex){

                       }
                   }
               }
           }
       }
    }

    private void move(){
        if (current_floor < floor){
            current_floor++;
            direction = Direction.UP;
        }
        else {
            current_floor--;
            direction = Direction.DOWN;
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("DEBUG: the Elevator %d in %d level to %d floor. Man in Elevator %d \n", this.id, this.current_floor, this.floor, this.inElevator.size());
    }


    public int getCurrent_floor() { return current_floor; }

    public void render(GraphicsContext gc, Image man, Image man2, Image el){
        int x;
        if (this.id==1){
            x = 130;
        }else {
            x = 500;
        }
        gc.strokeText("Mass of 1  = " + current_weight, x, 30);
        gc.strokeText("Level 1  = " + current_floor, x, 50);
        gc.drawImage(el, x, current_floor * 60+20);
        try {
            if (!inElevator.isEmpty()) {
                CountEl = inElevator.size();
                for (int i = 0; i < inElevator.size(); i++) {
                    if (inElevator.get(i).getDirection().equals(Direction.UP)) {
                        gc.drawImage(man2, x + 50 + 15 * CountEl, current_floor * 60 + 20);
                    } else {
                        gc.drawImage(man, x + 50 + 15 * CountEl, current_floor * 60 + 20);
                    }
                    CountEl--;
                }
            }
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}

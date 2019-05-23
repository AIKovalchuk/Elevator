package Algoritms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
        Order order = null;
        if (!listOfOrder.isEmpty()) {
            Iterator<Order> it = listOfOrder.iterator();
            order = it.next();
            it.remove();
        }
        return order;
    }

    public synchronized ArrayList<Order> popFromLevel(int level, Direction dir, int freeMass){
        ArrayList<Order> list = new ArrayList<Order>();
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

    public void render(GraphicsContext gc, Image man, Image man2){
        if(!listOfOrder.isEmpty()) {
            for (int i = 0; i < listOfOrder.size(); i++) {
                if (listOfOrder.get(i).getDirection().equals(Direction.UP)) {
                    gc.drawImage(man2, 400, listOfOrder.get(i).getFloor() * 60 + 20);
                }
                else {
                    gc.drawImage(man, 400, listOfOrder.get(i).getFloor() * 60 + 20);
                }
            }
        }
    }

    public void renderElevator(GraphicsContext gc, Image man, Image man2, Image el){
        elevator1.render(gc, man, man2, el);
        elevator2.render(gc, man, man2, el);
    }

}

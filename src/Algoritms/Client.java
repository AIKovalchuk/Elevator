package Algoritms;

public class Client implements Runnable {
    private Manager manager;

    public Client(){
        manager = Manager.getInstance();
    }

    @Override
    public void run() {
        while (true){
            Order order = new Order();
            manager.push(order);
            System.out.printf("DEBUG: order was created. Level %d, weight %d\n", order.getFloor(), order.getWeight());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

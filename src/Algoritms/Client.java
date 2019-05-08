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
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

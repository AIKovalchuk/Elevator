public class Client {
    private Manager manager = Manager.getInstance();

    public Client(){ }

    public void createPerson(){
        while (true){
            Person person = new Person((int)(Math.random()*110)+30,
                    (int)(Math.random()*20)+1,
                    (int)(Math.random()*20)+1);
            manager.pushPersonToQueue(person);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

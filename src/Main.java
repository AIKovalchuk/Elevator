import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class Main /*extends Application*/ {

    public static void main(String[] args){
        Client client = new Client();
        Elevator el1 = new Elevator(1);
        Elevator el2 = new Elevator(2);
        Manager manager =  Manager.getInstance();
        Thread thr1 = new Thread(el1);
        Thread thr2 = new Thread(el2);
        manager.registerObserver(el1);
        manager.registerObserver(el2);
        thr1.start();
        thr2.start();
        client.createPerson();
        try {

            thr1.join();
            thr2.join();
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
        //Application.launch(args);

    }

/*    @Override
    public void start(Stage stage){



        stage.setTitle("Elevators!"); // установка заголовка окна
        stage.setWidth(800);        // установка ширины окна
        stage.setHeight(800);       // установка длины окна
        stage.show();               // отображаем окно на экране устройства
    }*/
}

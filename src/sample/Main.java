package sample;


import Algoritms.Client;
import Algoritms.Manager;
import Algoritms.Order;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
    //private static Manager manager;
    private static int CountEl1 = 0;
    private static int CountEl2 = 0;

    public void update(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Elevators GUI");
        primaryStage.setScene(new Scene(root, 900, 750));
        primaryStage.show();*/

        primaryStage.setTitle("Elevators");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(900, 750);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 18 );
        gc.setFont( theFont );
        //gc.fillText( "Hello, World!", 60, 50 );
        //gc.strokeText( "Hello, World!", 60, 50 );

        Image el1 = new Image("sample/Elevator.png");
        Image el2 = new Image("sample/Elevator.png");
        Image man = new Image("sample/Man.png");
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            //Main loop
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                gc.clearRect(0,0,900,750);
                ArrayList<Order> list = Manager.getInstance().getListOfOrder();
                if(!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        gc.drawImage(man, 400, list.get(i).getFloor() * 20);
                    }
                }



                ArrayList<Order> listInEl1 = Manager.getInstance().getListOfinOrderEl1();
                CountEl1 = listInEl1.size();
                for (int i = 0; i < list.size(); i++){
                    gc.drawImage(man,150 + 100 + 15*CountEl1,Manager.getInstance().getFloorEl1()*20 + 5);
                    CountEl1--;
                }

                ArrayList<Order> listInEl2 = Manager.getInstance().getListOfinOrderEl2();
                CountEl2 = listInEl2.size();
                for (int i = 0; i < list.size(); i++){
                    gc.drawImage(man,650 + 100 + 15*CountEl2,Manager.getInstance().getFloorEl2()*20 + 5);
                    CountEl2--;
                }

                //gc.fillText( "Mass of 1  = "+Manager.getInstance().getWeight1(),150, 30);
                gc.strokeText("Mass of 1  = "+Manager.getInstance().getWeight1(),150, 30);
                //gc.fillText( "Mass of 2  = "+Manager.getInstance().getWeight2(),450, 30);
                gc.strokeText("Mass of 2  = "+Manager.getInstance().getWeight2(),450, 30);
                //gc.fillText( "Level of 1  = "+Manager.getInstance().getLevel1(),150, 50);
                gc.strokeText("Level of 1  = "+Manager.getInstance().getLevel1(),150, 50);
                //gc.fillText( "Level of 2  = "+Manager.getInstance().getLevel2(),450, 50);
                gc.strokeText("Mass of 2  = "+Manager.getInstance().getLevel2(),450, 50);
                gc.drawImage(el1, 150, Manager.getInstance().getFloorEl1()*20);
                gc.drawImage(el2, 500, Manager.getInstance().getFloorEl2()*20);
            }
        }.start();

        /*Image earth = new Image( "earth.png" );
        gc.drawImage( earth, 180, 100 );*/

        primaryStage.show();
    }


    public static void main(String[] args) {
        Client client = new Client();
        Manager manager =  Manager.getInstance();
        Thread thr1 = new Thread(client);
        Thread thr2 = new Thread(manager);
        thr1.start();
        thr2.start();
        launch(args);
        try {
            thr1.join();
            thr2.join();
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}

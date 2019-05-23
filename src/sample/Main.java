package sample;


import Algoritms.Client;
import Algoritms.Direction;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Elevators");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(900, 750);
        Line line1 = new Line(20, 180, 750, 180);
        line1.getStrokeDashArray().addAll(100.0, 1.0);
        Line line2 = new Line(20, 240, 750, 240);
        line2.getStrokeDashArray().addAll(100.0, 1.0);
        Line line3 = new Line(20, 300, 750, 300);
        line3.getStrokeDashArray().addAll(100.0, 1.0);
        Line line4 = new Line(20, 360, 750, 360);
        line4.getStrokeDashArray().addAll(100.0, 1.0);
        Line line5 = new Line(20, 420, 750, 420);
        line5.getStrokeDashArray().addAll(100.0, 1.0);
        Line line6 = new Line(20, 480, 750, 480);
        line6.getStrokeDashArray().addAll(100.0, 1.0);
        Line line7 = new Line(20, 540, 750, 540);
        line7.getStrokeDashArray().addAll(100.0, 1.0);
        Line line8 = new Line(20, 600, 750, 600);
        line8.getStrokeDashArray().addAll(100.0, 1.0);
        Line line9 = new Line(20, 660, 750, 660);
        line9.getStrokeDashArray().addAll(100.0, 1.0);
        Line line10 = new Line(20, 720, 750, 720);
        line10.getStrokeDashArray().addAll(100.0, 1.0);
        root.getChildren().addAll(canvas,line1,line2,line3,line4,line5,line6,line7,line8,line9,line10);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", 18 );
        gc.setFont( theFont );

        Image el = new Image("sample/Elevator.png");
        Image man = new Image("sample/Man.png");
        Image man2 = new Image("sample/Man2.png");
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            //Main loop
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                gc.clearRect(0,0,900,750);
                Manager.getInstance().render(gc,man,man2);
                Manager.getInstance().renderElevator(gc,man,man2,el);
            }
        }.start();
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

package sample;
/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import Algoritms.Client;
import Algoritms.Manager;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Elevator_1"
    private Rectangle Elevator_1; // Value injected by FXMLLoader

    @FXML // fx:id="Elevator_2"
    private Rectangle Elevator_2; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        Elevator_1.setTranslateY((Algoritms.Manager.getInstance().getFloorEl1()));

    }

    public Rectangle getElevator_1() { return Elevator_1; }
}

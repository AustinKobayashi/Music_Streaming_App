package sample;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static java.lang.Math.abs;

public class Controller {

    @FXML
    public Pane topBar;

    @FXML
    private Pane centerPane;
    @FXML
    private ScrollPane playlistScrollPane;
    @FXML
    private AnchorPane playlistAnchorPane;

    @FXML
    private Pane bottomBar;

    @FXML
    private Pane artistDetails;

    private double orgSceneX;
    private double orgTranslateX;

    public Controller(){}

    @FXML
    private void initialize(){}


    @FXML
    private void SwipeClick(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgTranslateX = centerPane.getTranslateX();

        System.out.println(centerPane.getBoundsInLocal().getMinY());
        //System.out.println("orgSceneX " + orgSceneX);
        //System.out.println("orgTranslateX " + orgTranslateX);
    }

    @FXML
    private void SwipeDrag(MouseEvent t){
        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        //System.out.println("offsetX " + offsetX);
        //System.out.println("newTranslateX " + newTranslateX);

        if(newTranslateX < 0)
            centerPane.setTranslateX(newTranslateX);
    }


    @FXML
    private void SwipeRelease(MouseEvent t){

        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        System.out.println("offsetX " + offsetX);
        System.out.println("newTranslateX " + newTranslateX);

        if(abs(offsetX) <= 1)
            return;

        if(newTranslateX >= -450/2) {

            MoveAlongPath(newTranslateX, 0);
            centerPane.setTranslateX(0);

        } else if(newTranslateX < -450/2){

            MoveAlongPath(newTranslateX, -450);
            centerPane.setTranslateX(-450);
        }
    }



    private void MoveAlongPath(double fromX, double toX){

        TranslateTransition translateTransition =  new TranslateTransition();
        translateTransition.setNode(centerPane);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }
}

package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static java.lang.Math.abs;

/**
 * Created by Austin on 2018-07-05.
 */
public class NodeMover {

    private static NodeMover instance = new NodeMover();

    private NodeMover(){
        centerPane = (Pane) Test.scene.lookup("#centerPane");
    }

    public static NodeMover getInstance(){ return instance; }


    private Pane centerPane;

    private double orgSceneX;
    private double orgTranslateX;



    public void SwipeClick(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgTranslateX = centerPane.getTranslateX();
    }


    public void SwipeDrag(MouseEvent t){
        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        if(newTranslateX < 0)
            centerPane.setTranslateX(newTranslateX);
    }


    public void SwipeRelease(MouseEvent t){

        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        if(abs(offsetX) <= 1)
            return;

        if(centerPane.getTranslateX() <= 0 && centerPane.getTranslateX() > -450){

            if(newTranslateX >= - 450/2 && newTranslateX <= 0){
                MoveAlongPath(centerPane, newTranslateX, 0);
                centerPane.setTranslateX(0);

            } else if(newTranslateX >= -450 && newTranslateX < -450/2){
                MoveAlongPath(centerPane, newTranslateX, -450);
                centerPane.setTranslateX(-450);
            }

        } else if(centerPane.getTranslateX() <= -450 && centerPane.getTranslateX() > -900) {

            if(newTranslateX >= -900 + 450/2 && newTranslateX <= -450){
                MoveAlongPath(centerPane, newTranslateX, -450);
                centerPane.setTranslateX(-450);

            } else if(newTranslateX >= -900 && newTranslateX < -900 + 450/2){
                MoveAlongPath(centerPane, newTranslateX, -900);
                centerPane.setTranslateX(-900);
            }
        }

        if(centerPane.getTranslateX() > 0)
            centerPane.setTranslateX(0);
    }



    public void MoveAlongPath(Node node, double fromX, double toX){

        TranslateTransition translateTransition =  new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }
}

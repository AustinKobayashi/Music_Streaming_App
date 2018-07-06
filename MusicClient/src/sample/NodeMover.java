package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by Austin on 2018-07-05.
 */
public class NodeMover {

    private static NodeMover instance = new NodeMover();

    private NodeMover(){}

    public static NodeMover getInstance(){ return instance; }


    public void MoveAlongPath(Node node, double fromX, double toX){

        System.out.println("moving " + node);
        TranslateTransition translateTransition =  new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }
}

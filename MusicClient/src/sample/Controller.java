package sample;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    @FXML
    private Pane albumDetails;

    @FXML
    private Pane songList;


    private double orgSceneX;
    private double orgTranslateX;

    public Controller(){}

    @FXML
    private void initialize(){}


    @FXML
    private void SwipeClick(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgTranslateX = centerPane.getTranslateX();

        //System.out.println(centerPane.getBoundsInLocal().getMinY());
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

        //System.out.println("offsetX " + offsetX);
        //System.out.println("newTranslateX " + newTranslateX);
        //System.out.println("position " + centerPane.getTranslateX());

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

        /*
        if(newTranslateX >= -450/2) {

            MoveAlongPath(centerPane, newTranslateX, 0);
            centerPane.setTranslateX(0);

        } else if(newTranslateX < -450/2){

            MoveAlongPath(centerPane, newTranslateX, -450);
            centerPane.setTranslateX(-450);
        }
        */
    }


    @FXML
    private void ArtistDetailsBackButton(){

        MoveAlongPath(artistDetails, 0, 450);
    }



    @FXML
    private void AlbumDetailsBackButton(){

        MoveAlongPath(albumDetails, 0, 450);
    }


    @FXML
    private void SongListBackButton(){

        MoveAlongPath(songList, 0, 450);
    }



    @FXML
    private void ToggleMusicPlayback(){
        MusicPlayer.getInstance().ToggleMusicPlayback();
    }


    private void MoveAlongPath(Node node, double fromX, double toX){

        TranslateTransition translateTransition =  new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }
}

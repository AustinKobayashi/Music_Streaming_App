package sample;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    private double orgSceneX;
    private double orgTranslateX;

    public Controller(){}

    @FXML
    private void initialize(){}


    @FXML
    private void click(MouseEvent t){
        orgSceneX = t.getSceneX();
        orgTranslateX = ((Pane)(t.getSource())).getTranslateX();

        //System.out.println("orgSceneX " + orgSceneX);
        //System.out.println("orgTranslateX " + orgTranslateX);

    }

    @FXML
    private void drag(MouseEvent t){
        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        //System.out.println("offsetX " + offsetX);
        //System.out.println("newTranslateX " + newTranslateX);

        if(newTranslateX < 0)
            ((Pane)(t.getSource())).setTranslateX(newTranslateX);
    }


    @FXML
    private void release(MouseEvent t){

        double offsetX = t.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        //System.out.println("offsetX " + offsetX);
        //System.out.println("newTranslateX " + newTranslateX);

        if(newTranslateX > -450/2) {
            // float inc = (450/2) / 0.5f;
            while(newTranslateX < 0){
                newTranslateX += 0.000001f;
                    //System.out.println(newTranslateX);
                ((Pane) (t.getSource())).setTranslateX(newTranslateX);
            }
            ((Pane) (t.getSource())).setTranslateX(0);

        } else if(newTranslateX < -450/2){

            while(newTranslateX > -450){
                newTranslateX -= 0.0001f;
                //System.out.println(newTranslateX);
                ((Pane) (t.getSource())).setTranslateX(newTranslateX);
            }

            ((Pane)(t.getSource())).setTranslateX(-450);
        }
    }
}

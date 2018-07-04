package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;

import java.awt.*;

public class Test extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music");
        Scene scene = new Scene(root, 450, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        Test(scene);

    }



    void Test(Scene scene){

        AnchorPane playlistAnchorPane = (AnchorPane) scene.lookup("#playlistAnchorPane");

        GridPane grid = new GridPane();

        double width = playlistAnchorPane.widthProperty().doubleValue() / 2;
        double height = playlistAnchorPane.widthProperty().doubleValue() / 3;

        //grid.setGridLinesVisible(true);

        for(int i = 0; i < 100; i++){

            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getRowConstraints().add(new RowConstraints(height));

            Button btn1 = new Button();
            btn1.setText(i + "- 0");
            GridPane.setHalignment(btn1, HPos.CENTER);
            GridPane.setValignment(btn1, VPos.CENTER);
            btn1.setMinSize(width * 0.8, height * 0.8);
            btn1.setOnAction(event -> {
                Node artistDetails = scene.lookup("#artistDetails");
                MoveAlongPath(artistDetails, 0, -450);
            });

            grid.add(btn1, 0, i );


            Button btn2 = new Button();
            btn2.setText(i + "- 1");
            GridPane.setHalignment(btn2, HPos.CENTER);
            GridPane.setValignment(btn2, VPos.CENTER);
            btn2.setMinSize(width * 0.8, height * 0.8);
            grid.add(btn2, 1, i );
        }

        playlistAnchorPane.getChildren().add(grid);
    }


    private void MoveAlongPath(Node node, double fromX, double toX){

        System.out.println("moving " + node);
        TranslateTransition translateTransition =  new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

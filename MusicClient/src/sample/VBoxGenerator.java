package sample;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.awt.*;

public class VBoxGenerator {


    public static VBox GenerateVBox(GridPane grid, double width1, double height1, String boxText, int index, int mod){

        ColumnConstraints column1 = new ColumnConstraints(225);
        column1.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints(225);
        column2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column2);
        RowConstraints row = new RowConstraints(150);
        row.setValignment(VPos.CENTER);
        grid.getRowConstraints().add(row);


        VBox vBox = new VBox(5);
        vBox.setStyle("-fx-background-color: white;");
        vBox.setMaxSize(180, 120);

        vBox.setTranslateX(vBox.getTranslateX() + (180 * 0.05 * mod));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
        vBox.setEffect(dropShadow);

        AnchorPane img = new AnchorPane();
        img.setMinSize(Test.displayableWidth * 0.8, Test.displayableHeight * 0.6);

        ImageView noArtistImage = new ImageView(new Image("file:resources\\NoArtistImage.png"));

        AnchorPane.setTopAnchor(noArtistImage, 0.0);
        AnchorPane.setLeftAnchor(noArtistImage, 0.0);
        AnchorPane.setRightAnchor(noArtistImage, 0.0);

        img.getChildren().add(noArtistImage);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                vBox.setMaxWidth(Test.displayableWidth * 0.8);
                noArtistImage.setFitHeight(Test.displayableHeight * 0.6);
                noArtistImage.setFitWidth(Test.displayableWidth * 0.8);

                noArtistImage.setVisible(true);
            }
        });

        Label text = new Label(boxText);
        text.setStyle("-fx-font-size: " + 20 + "; -fx-padding: 0 10 0 10;");
        text.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().addAll(img, text);
        vBox.setAlignment(Pos.TOP_CENTER);

        return vBox;
    }
}

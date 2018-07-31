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
import java.io.File;

public class VBoxGenerator {


    public static VBox GenerateArtistVBox(GridPane grid, double width1, double height1, String boxText, int index, int mod){

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: white;");
        vBox.setMaxSize(180, 120);

        vBox.setTranslateX(vBox.getTranslateX() + (180 * 0.05 * mod));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
        vBox.setEffect(dropShadow);

        AnchorPane img = new AnchorPane();
        img.setMinSize(Test.displayableWidth * 0.8, Test.displayableHeight * 0.6);

        ImageView noArtistImage = new ImageView(new Image("file:resources" + File.separator + "NoArtistImage.png"));

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



    public static VBox GenerateAlbumVBox(GridPane grid, double width1, double height1, String boxText, int index, int mod){

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: white;");
        vBox.setMaxSize(180, 231);

        vBox.setTranslateX(vBox.getTranslateX() + (180 * 0.05 * mod));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
        vBox.setEffect(dropShadow);

        AnchorPane img = new AnchorPane();
        img.setPrefSize(Test.displayableWidth * 0.8, 210 * 0.75);

        ImageView noArtistImage = new ImageView(new Image("file:resources" + File.separator + "NoAlbumImageRectangle.png"));

        AnchorPane.setTopAnchor(noArtistImage, 0.0);
        AnchorPane.setLeftAnchor(noArtistImage, 0.0);
        AnchorPane.setRightAnchor(noArtistImage, 0.0);

        img.getChildren().add(noArtistImage);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                vBox.setMaxWidth(Test.displayableWidth * 0.8);
                //noArtistImage.setFitHeight(210 * 0.8);
                noArtistImage.setPreserveRatio(true);
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

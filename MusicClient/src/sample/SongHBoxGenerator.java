package sample;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.scene.control.Label;

import java.io.File;


public class SongHBoxGenerator {

    public static HBox GenerateSongHbox(JSONObject song, int songNumber) throws JSONException {

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);

        Label songNumberLabel = new Label(String.valueOf(songNumber));
        songNumberLabel.setStyle("-fx-font-size: 12; -fx-padding: 0 30 0 35;");
        songNumberLabel.setTextAlignment(TextAlignment.CENTER);

        VBox vbox = new VBox();

        Label songNameLabel = new Label(song.getString("name"));
        songNameLabel.setStyle("-fx-font-size: 20;");

        Label songDurationLabel = new Label(song.getString("duration"));
        songDurationLabel.setStyle("-fx-font-size: 12;");
        vbox.setStyle("-fx-padding: 10 0 10 0;");

        vbox.getChildren().addAll(songNameLabel, songDurationLabel);
        hbox.getChildren().addAll(songNumberLabel, vbox);

        return hbox;
    }


    public static HBox GenerateSongHboxWithImage(JSONObject song) throws JSONException {

        HBox hbox = new HBox();

        hbox.setStyle("-fx-padding: 0 45 0 45");

        hbox.setAlignment(Pos.CENTER_LEFT);

        StackPane pane = new StackPane();
        pane.setMaxSize(Test.displayableWidth / 5, Test.displayableWidth / 5);

        ImageView image = new ImageView(new Image("file:resources" + File.separator + "NoAlbumImageSquare.png"));
        image.setFitWidth(Test.displayableWidth / 5);
        image.setFitHeight(Test.displayableWidth / 5);

        pane.getChildren().add(image);

        VBox vbox = new VBox();

        Label songNameLabel = new Label(song.getString("name"));
        songNameLabel.setStyle("-fx-font-size: 20;");

        Label artistAndDurationLabel = new Label(DataParser.getInstance().GetArtistName(song.getInt("artist_id")) +
        " · " + song.getString("duration"));
        artistAndDurationLabel.setStyle("-fx-font-size: 12;");

        vbox.setStyle("-fx-padding: 10 0 10 10;");

        vbox.getChildren().addAll(songNameLabel, artistAndDurationLabel);
        hbox.getChildren().addAll(pane, vbox);

        return hbox;
    }
}

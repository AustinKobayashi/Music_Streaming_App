package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.json.JSONObject;
import javafx.scene.control.Label;


import java.awt.*;

public class SongHBoxGenerator {

    public static HBox GenerateSongHbox(JSONObject song, int songNumber){

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
}

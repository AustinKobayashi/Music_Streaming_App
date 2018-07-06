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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;

import java.awt.*;

public class Test extends Application {


    private DataParser dataParser;

    public static final String base_url = "http://localhost:3000";

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Playlist test = new Playlist("test");

        dataParser = DataParser.getInstance();
        dataParser.ParseData();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music");
        scene = new Scene(root, 450, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        ArtistListRenderer.getInstance().RenderArtistList(scene);
        //AlbumListRenderer.getInstance().RenderAlbumList();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

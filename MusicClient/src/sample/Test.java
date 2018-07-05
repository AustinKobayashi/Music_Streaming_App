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

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    DataParser dataParser;

    private static final String base_url = "http://localhost:3000";

    @Override
    public void start(Stage primaryStage) throws Exception{

        dataParser = new DataParser();
        dataParser.ParseData();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music");
        Scene scene = new Scene(root, 450, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        Test(scene);

    }



    void Test(Scene scene){

        AnchorPane playlistAnchorPane = (AnchorPane) scene.lookup("#playlistAnchorPane");
        playlistAnchorPane.getChildren().clear();

        GridPane grid = new GridPane();

        double width = playlistAnchorPane.widthProperty().doubleValue() / 2;
        double height = playlistAnchorPane.widthProperty().doubleValue() / 3;

        grid.setGridLinesVisible(true);

        int index = 1;
        int x = 0;
        int y = 0;

        while(index <= dataParser.artists.size()){

            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getRowConstraints().add(new RowConstraints(height));

            Button btn1 = new Button();
            btn1.setText(dataParser.artists.get(index).getString("name"));
            GridPane.setHalignment(btn1, HPos.CENTER);
            GridPane.setValignment(btn1, VPos.CENTER);
            btn1.setMinSize(width * 0.8, height * 0.8);

            int artistId = index;
            btn1.setOnAction(event -> {
                Node artistDetails = scene.lookup("#artistDetails");
                RenderArtistPage(scene, artistId);
                MoveAlongPath(artistDetails, 0, -450);
            });

            grid.add(btn1, x, y);

            index++;
            y += x;
            x = (index - 1) % 2;
        }

        playlistAnchorPane.getChildren().add(grid);
    }



    private void RenderArtistPage(Scene scene, int artistId){

        AnchorPane artistDetailsAnchorPage = (AnchorPane) scene.lookup("#artistDetailsAnchorPage");
        artistDetailsAnchorPage.getChildren().clear();
        GridPane grid = new GridPane();

        double width = artistDetailsAnchorPage.widthProperty().doubleValue() / 2;
        double height = artistDetailsAnchorPage.widthProperty().doubleValue() / 3;

        grid.setGridLinesVisible(true);

        Button btn = new Button();
        btn.setText("All Songs");
        GridPane.setHalignment(btn, HPos.CENTER);
        GridPane.setValignment(btn, VPos.CENTER);
        btn.setMinSize(width * 0.8, height * 0.8);
        grid.add(btn, 0, 0);

        int x = 1;
        int y = 0;

        JSONArray artistAlbums = dataParser.GetAllArtistAlbums(artistId);

        for (int i = 0; i < artistAlbums.length(); i ++, y += x, x = x ^ 1){

            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getRowConstraints().add(new RowConstraints(height));

            Button btn1 = new Button();
            btn1.setText(artistAlbums.getJSONObject(i).getString("name"));
            GridPane.setHalignment(btn1, HPos.CENTER);
            GridPane.setValignment(btn1, VPos.CENTER);
            btn1.setMinSize(width * 0.8, height * 0.8);

            int finalAlbumId = i + 1;
            btn1.setOnAction(event -> {
                Node albumDetails = scene.lookup("#albumDetails");
                RenderAlbumDetailsPage(scene, finalAlbumId);
                //artistDetailsAnchorPage.setTranslateX(450);
                MoveAlongPath(albumDetails, 0, -450);
            });

            grid.add(btn1, x, y);
        }

        artistDetailsAnchorPage.getChildren().add(grid);
    }



    private void RenderAlbumDetailsPage(Scene scene, int albumId){

        VBox albumDetailsVbox = (VBox) scene.lookup("#albumDetailsVbox");
        albumDetailsVbox.getChildren().clear();
        JSONArray albumSongs = dataParser.GetAllAlbumSongs(albumId);

        for (int i = 0; i < albumSongs.length(); i ++){

            Button btn1 = new Button();
            btn1.setText(albumSongs.getJSONObject(i).getString("name"));
            albumDetailsVbox.getChildren().add(btn1);

            int finalI = i;
            btn1.setOnAction(event -> {
                PlaySong(scene, albumSongs.getJSONObject(finalI).getString("url"));
            });

        }
    }



    void PlaySong(Scene scene, String url) {

        Pane bottomBar = (Pane) scene.lookup("#bottomBar");

        Media media = new Media(base_url + "/" + url);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        bottomBar.getChildren().add(mediaView);
        mediaPlayer.play();

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

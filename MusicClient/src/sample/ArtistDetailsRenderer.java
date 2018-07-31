package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

/**
 * Created by Austin on 2018-07-05.
 */
public class ArtistDetailsRenderer {

    private DataParser dataParser;

    private boolean rendered = false;

    private static ArtistDetailsRenderer instance = new ArtistDetailsRenderer();

    private ArtistDetailsRenderer(){}

    public static ArtistDetailsRenderer getInstance(){ return instance; }


    public void RenderArtistDetails(Scene scene, int artistId) throws JSONException {

        dataParser = DataParser.getInstance();

        ImageView artistImage = (ImageView) scene.lookup("#artistImage");
        artistImage.setImage(new Image("file:resources" + File.separator + "NoArtistImageExtended.png", 450, 0, true, false));

        Label artistNameLabel = (Label) scene.lookup("#artistNameLabel");
        artistNameLabel.setText(dataParser.GetArtistName(artistId));

        VBox artistDetailsVbox = (VBox) scene.lookup("#artistDetailsVbox");

        artistDetailsVbox.getChildren().remove(scene.lookup("#artistDetailsGrid"));

        GridPane grid = new GridPane();
        grid.setId("artistDetailsGrid");

        ColumnConstraints column1 = new ColumnConstraints(225);
        column1.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints(225);
        column2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column2);
        RowConstraints row = new RowConstraints(275);
        row.setValignment(VPos.CENTER);
        grid.getRowConstraints().add(row);


        double width = artistDetailsVbox.widthProperty().doubleValue() / 2;
        //double height = artistDetailsVbox.widthProperty().doubleValue() / 3;
        double height = 471 / 3;

        dataParser = DataParser.getInstance();

        int index = 0;
        int x = 0;
        int y = 0;
        int mod = 1;

        JSONArray artistAlbums = dataParser.GetAllArtistAlbums(artistId);

        while (index <= artistAlbums.length()) {

            VBox vBox;

            if(index == 0){

                vBox = VBoxGenerator.GenerateAlbumVBox(grid, width, height, "All Songs", index, mod);
                vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                SongListRenderer.getInstance().RenderAllArtistSongs(scene, artistId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Node songList = scene.lookup("#songList");
                            NodeMover.getInstance().MoveAlongPath(songList, 0, -450);
                        }
                    });

            } else {

                vBox = VBoxGenerator.GenerateAlbumVBox(grid, width, height, artistAlbums.getJSONObject(index - 1).getString("name"), index, mod);
                int albumId = artistAlbums.getJSONObject(index - 1).getInt("id");
                vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            try {
                                SongListRenderer.getInstance().RenderSongList(scene, dataParser.GetAllAlbumSongs(albumId), (VBox) scene.lookup("#songListVbox"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Node songList = scene.lookup("#songList");
                            NodeMover.getInstance().MoveAlongPath(songList, 0, -450);
                        }
                    });
            }

            grid.add(vBox, x, y);

            index++;
            y += x;
            x = (index) % 2;
            mod *= -1;
        }

        //grid.setStyle("-fx-border-style: solid; -fx-border-color: red;");
        artistDetailsVbox.getChildren().add(grid);

        /*
        grid.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        NodeMover.getInstance().SwipeClick(mouseEvent);
                    }
                });

        grid.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        NodeMover.getInstance().SwipeRelease(mouseEvent);
                    }
                });

        grid.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        NodeMover.getInstance().SwipeDrag(mouseEvent);
                    }
                });
        */
        rendered = true;
    }
}

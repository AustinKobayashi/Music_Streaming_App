package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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


        System.out.println("Called");

        dataParser = DataParser.getInstance();

        AnchorPane artistDetailsAnchorPage = (AnchorPane) scene.lookup("#artistDetailsAnchorPage");
        artistDetailsAnchorPage.getChildren().clear();
        GridPane grid = new GridPane();

        double width = artistDetailsAnchorPage.widthProperty().doubleValue() / 2;
        double height = artistDetailsAnchorPage.widthProperty().doubleValue() / 3;

        dataParser = DataParser.getInstance();

        int index = 0;
        int x = 0;
        int y = 0;
        int mod = 1;

        JSONArray artistAlbums = dataParser.GetAllArtistAlbums(artistId);

        while (index <= artistAlbums.length()) {

            VBox vBox;

            if(index == 0){

                vBox = VBoxGenerator.GenerateVBox(grid, width, height, "All Songs", index, mod);
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

                vBox = VBoxGenerator.GenerateVBox(grid, width, height, artistAlbums.getJSONObject(index - 1).getString("name"), index, mod);
                int albumId = index;
                vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            Node albumDetails = scene.lookup("#albumDetails");
                            try {
                                AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene,
                                        artistAlbums.getJSONObject(albumId).getInt("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
                        }
                    });
            }

            grid.add(vBox, x, y);

            index++;
            y += x;
            x = (index) % 2;
            mod *= -1;
        }

        /*
        Button btn = new Button();
        btn.setText("All Songs");
        btn.setOnAction(event -> {
            try {
                SongListRenderer.getInstance().RenderAllArtistSongs(scene, artistId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Node songList = scene.lookup("#songList");
            NodeMover.getInstance().MoveAlongPath(songList, 0, -450);
        });

        */


        /*
        grid.setGridLinesVisible(true);

        Button btn = new Button();
        btn.setText("All Songs");
        btn.setOnAction(event -> {
            try {
                SongListRenderer.getInstance().RenderAllArtistSongs(scene, artistId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Node songList = scene.lookup("#songList");
            NodeMover.getInstance().MoveAlongPath(songList, 0, -450);
        });


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

            int finalAlbumId = i;
            btn1.setOnAction(event -> {
                Node albumDetails = scene.lookup("#albumDetails");
                try {
                    AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene,
                            artistAlbums.getJSONObject(finalAlbumId).getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
            });

            grid.add(btn1, x, y);
        }
        */
        artistDetailsAnchorPage.getChildren().add(grid);


        rendered = true;
    }
}

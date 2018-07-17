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

    private static ArtistDetailsRenderer instance = new ArtistDetailsRenderer();

    private ArtistDetailsRenderer(){}

    public static ArtistDetailsRenderer getInstance(){ return instance; }


    public void RenderArtistDetails(Scene scene, int artistId) throws JSONException {

        dataParser = DataParser.getInstance();

        AnchorPane artistDetailsAnchorPage = (AnchorPane) scene.lookup("#artistDetailsAnchorPage");
        artistDetailsAnchorPage.getChildren().clear();
        GridPane grid = new GridPane();

        double width = artistDetailsAnchorPage.widthProperty().doubleValue() / 2;
        double height = artistDetailsAnchorPage.widthProperty().doubleValue() / 3;

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

        int index = 1;
        int x = 1;
        int y = 0;
        int mod = 1;

        JSONArray artistAlbums = dataParser.GetAllArtistAlbums(artistId);

        for (int i = 0; i < artistAlbums.length(); i ++, y += x, x = x ^ 1){

            ColumnConstraints column1 = new ColumnConstraints(width);
            column1.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(column1);
            ColumnConstraints column2 = new ColumnConstraints(width);
            column2.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(column2);
            RowConstraints row = new RowConstraints(height);
            row.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(row);


            VBox vBox = new VBox(5);
            vBox.setStyle("-fx-background-color: white;");
            vBox.setMaxSize(width * 0.8, height * 0.8);
            vBox.setTranslateX(vBox.getTranslateX() + (width * 0.05 * mod));

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setColor(Color.color(0.0, 0.0, 0.0));
            vBox.setEffect(dropShadow);

            AnchorPane img = new AnchorPane();
            img.setMinSize(width * 0.72, height * 0.6);

            ImageView noAlbumImage = new ImageView(new Image("file:resources\\NoArtistImage.png"));

            AnchorPane.setTopAnchor(noAlbumImage, 0.0);
            AnchorPane.setLeftAnchor(noAlbumImage, 0.0);
            AnchorPane.setRightAnchor(noAlbumImage, 0.0);

            img.getChildren().add(noAlbumImage);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    noAlbumImage.setFitHeight(img.getHeight());
                    noAlbumImage.setFitWidth(img.getWidth());
                    noAlbumImage.setPreserveRatio(true);
                    noAlbumImage.setVisible(true);
                }
            });


            Text text = new Text();
            text.setText(artistAlbums.getJSONObject(i).getString("name"));
            text.setStyle("-fx-font-size: " + 20 + ";");
            text.setTextAlignment(TextAlignment.CENTER);

            vBox.getChildren().addAll(img, text);
            vBox.setAlignment(Pos.TOP_CENTER);

            int finalAlbumId = index;
            vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            Node albumDetails = scene.lookup("#albumDetails");
                            try {
                                AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene,
                                        artistAlbums.getJSONObject(finalAlbumId).getInt("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
                        }
                    });

            grid.add(vBox, x, y);
            mod *= -1;
        }

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
    }
}

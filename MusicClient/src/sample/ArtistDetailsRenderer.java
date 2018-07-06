package sample;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
                try {
                    AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene, finalAlbumId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
            });

            grid.add(btn1, x, y);
        }

        artistDetailsAnchorPage.getChildren().add(grid);
    }
}

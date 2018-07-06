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
import org.json.JSONException;

/**
 * Created by Austin on 2018-07-05.
 */
public class ArtistListRenderer {

    private DataParser dataParser;

    private static ArtistListRenderer instance = new ArtistListRenderer();

    private ArtistListRenderer(){}

    public static ArtistListRenderer getInstance(){ return instance; }



    public void RenderArtistList(Scene scene) throws JSONException {

        dataParser = DataParser.getInstance();

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
                try {
                    System.out.println(artistId);
                    ArtistDetailsRenderer.getInstance().RenderArtistDetails(scene, artistId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NodeMover.getInstance().MoveAlongPath(artistDetails, 0, -450);
            });

            grid.add(btn1, x, y);

            index++;
            y += x;
            x = (index - 1) % 2;
        }

        playlistAnchorPane.getChildren().add(grid);
    }
}

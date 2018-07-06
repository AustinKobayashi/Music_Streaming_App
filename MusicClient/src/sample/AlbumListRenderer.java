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
public class AlbumListRenderer {

    private DataParser dataParser;

    private static AlbumListRenderer instance = new AlbumListRenderer();

    private AlbumListRenderer(){}

    public static AlbumListRenderer getInstance(){ return instance; }



    public void RenderAlbumList() throws JSONException {

        dataParser = DataParser.getInstance();
        Scene scene = Test.scene;

        AnchorPane albumAnchorPane = (AnchorPane) scene.lookup("#albumAnchorPane");
        albumAnchorPane.getChildren().clear();

        GridPane grid = new GridPane();

        double width = albumAnchorPane.widthProperty().doubleValue() / 2;
        double height = albumAnchorPane.widthProperty().doubleValue() / 3;

        grid.setGridLinesVisible(true);

        int index = 1;
        int x = 0;
        int y = 0;

        while(index <= dataParser.albums.size()){

            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.getRowConstraints().add(new RowConstraints(height));

            Button btn1 = new Button();
            btn1.setText(dataParser.albums.get(index).getString("name"));
            GridPane.setHalignment(btn1, HPos.CENTER);
            GridPane.setValignment(btn1, VPos.CENTER);
            btn1.setMinSize(width * 0.8, height * 0.8);

            int artistId = index;

            btn1.setOnAction(event -> {
                Node albumDetails = scene.lookup("#albumDetails");
                try {
                    AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene, artistId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
            });

            grid.add(btn1, x, y);

            index++;
            y += x;
            x = (index - 1) % 2;
        }

        albumAnchorPane.getChildren().add(grid);
    }
}

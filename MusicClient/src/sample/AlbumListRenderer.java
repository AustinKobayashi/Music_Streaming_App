package sample;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONException;

/**
 * Created by Austin on 2018-07-05.
 */
public class AlbumListRenderer {

    private DataParser dataParser;

    private boolean rendered = false;

    private static AlbumListRenderer instance = new AlbumListRenderer();

    private AlbumListRenderer(){}

    public static AlbumListRenderer getInstance(){ return instance; }



    public void RenderAlbumList() throws JSONException {

        if(rendered)
            return;

        dataParser = DataParser.getInstance();
        Scene scene = Test.scene;

        AnchorPane albumAnchorPane = (AnchorPane) scene.lookup("#albumAnchorPane");
        albumAnchorPane.getChildren().clear();

        GridPane grid = new GridPane();

        double width = albumAnchorPane.widthProperty().doubleValue() / 2;
        double height = albumAnchorPane.widthProperty().doubleValue() / 3;

        int index = 1;
        int x = 0;
        int y = 0;
        int mod = 1;

        while(index <= dataParser.albums.size()){

            VBox vBox = VBoxGenerator.GenerateVBox(grid, width, height, dataParser.albums.get(index).getString("name"), index, mod);
            grid.add(vBox, x, y);
            int artistId = index;
            vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene, artistId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Node albumDetails = scene.lookup("#albumDetails");
                            NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);
                        }
                    });

            index++;
            y += x;
            x = (index - 1) % 2;
            mod *= -1;
        }

        albumAnchorPane.getChildren().add(grid);

        rendered = true;
    }
}

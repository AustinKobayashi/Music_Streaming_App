package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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

        ScrollPane albumScrollPane = (ScrollPane) scene.lookup("#albumScrollPane");

        GridPane grid = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints(225);
        column1.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints(225);
        column2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column2);
        RowConstraints row = new RowConstraints(260);
        row.setValignment(VPos.CENTER);

        double width = albumScrollPane.widthProperty().doubleValue() / 2;
        double height = albumScrollPane.widthProperty().doubleValue() / 3;

        int index = 1;
        int x = 0;
        int y = 0;
        int mod = 1;

        while(index <= dataParser.albums.size()){

            if(index % 2 == 1)
                grid.getRowConstraints().add(row);

            VBox vBox = VBoxGenerator.GenerateAlbumVBox(grid, width, height, dataParser.albums.get(index).getString("name"), index, mod);
            grid.add(vBox, x, y);

            int albumId = index;

            vBox.addEventHandler(MouseEvent.ANY,
                    new NodeEventHandler(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            NodeMover.getInstance().SwipeDrag(mouseEvent);
                        }
                    },
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
                    }));

            /*
            vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                //AlbumDetailsRenderer.getInstance().RenderAlbumDetails(scene, artistId);
                                SongListRenderer.getInstance().RenderSongList(scene, dataParser.GetAllAlbumSongs(albumId), (VBox) scene.lookup("#songListVbox"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Node albumDetails = scene.lookup("#albumDetails");
                            NodeMover.getInstance().MoveAlongPath(albumDetails, 0, -450);

                            Node songList = scene.lookup("#songList");
                            NodeMover.getInstance().MoveAlongPath(songList, 0, -450);
                        }
                    });
                    */

            index++;
            y += x;
            x = (index - 1) % 2;
            mod *= -1;
        }

        albumScrollPane.setContent(grid);

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


        rendered = true;
    }
}

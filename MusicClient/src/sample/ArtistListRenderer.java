package sample;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONException;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Austin on 2018-07-05.
 */
public class ArtistListRenderer {

    private DataParser dataParser;

    private boolean rendered = false;

    private static ArtistListRenderer instance = new ArtistListRenderer();

    private ArtistListRenderer(){}

    public static ArtistListRenderer getInstance(){ return instance; }



    public void RenderArtistList(Scene scene) throws JSONException {

        if(rendered)
            return;

        dataParser = DataParser.getInstance();

        ScrollPane albumScrollPane = (ScrollPane) scene.lookup("#playlistScrollPane");

        GridPane grid = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints(225);
        column1.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints(225);
        column2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(column2);
        RowConstraints row = new RowConstraints(150);
        row.setValignment(VPos.CENTER);

        double width = albumScrollPane.widthProperty().doubleValue() / 2;
        double height = albumScrollPane.widthProperty().doubleValue() / 3;

        int index = 1;
        int x = 0;
        int y = 0;
        int mod = 1;

        while (index <= dataParser.artists.size()) {

            if(index % 2 == 1)
                grid.getRowConstraints().add(row);

            VBox vBox = VBoxGenerator.GenerateArtistVBox(grid, width, height, dataParser.artists.get(index).getString("name"), index, mod);
            grid.add(vBox, x, y);
            int artistId = index;

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
                                //System.out.println(artistId);
                                ArtistDetailsRenderer.getInstance().RenderArtistDetails(scene, artistId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Node artistDetails = scene.lookup("#artistDetails");
                            NodeMover.getInstance().MoveAlongPath(artistDetails, 0, -450);
                        }
                    }));

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

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

        //grid.setGridLinesVisible(true);

        int index = 1;
        int x = 0;
        int y = 0;
        int mod = 1;

        while(index <= dataParser.artists.size()) {

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

            ImageView noArtistImage = new ImageView(new Image("file:resources\\NoArtistImage.png"));

            AnchorPane.setTopAnchor(noArtistImage, 0.0);
            AnchorPane.setLeftAnchor(noArtistImage, 0.0);
            AnchorPane.setRightAnchor(noArtistImage, 0.0);

            img.getChildren().add(noArtistImage);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    noArtistImage.setFitHeight(img.getHeight());
                    noArtistImage.setFitWidth(img.getWidth());
                    noArtistImage.setPreserveRatio(true);
                    noArtistImage.setVisible(true);
                }
            });


            Text text = new Text();
            text.setText(dataParser.artists.get(index).getString("name"));
            text.setStyle("-fx-font-size: " + 20 + ";");
            text.setTextAlignment(TextAlignment.CENTER);

            vBox.getChildren().addAll(img, text);
            vBox.setAlignment(Pos.TOP_CENTER);

            int finalIndex = index;
            vBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Node artistDetails = scene.lookup("#artistDetails");
                        try {
                            System.out.println(finalIndex);
                            ArtistDetailsRenderer.getInstance().RenderArtistDetails(scene, finalIndex);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NodeMover.getInstance().MoveAlongPath(artistDetails, 0, -450);
                    }
            });

            grid.add(vBox, x, y);

            index++;
            y += x;
            x = (index - 1) % 2;
            mod *= -1;
        }

        playlistAnchorPane.getChildren().add(grid);
    }
}

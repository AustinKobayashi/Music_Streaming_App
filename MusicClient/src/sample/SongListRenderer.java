package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SongListRenderer {

    private DataParser dataParser;

    private SongQueue queue;

    private static SongListRenderer instance = new SongListRenderer();

    private SongListRenderer(){}

    public static SongListRenderer getInstance(){ return instance; }


    public void RenderAllSongs(Scene scene){

        dataParser = DataParser.getInstance();

        ScrollPane songListScrollPane = (ScrollPane) scene.lookup("#songListScrollPane");

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        JSONArray allSongs = dataParser.GetAllSongs();

        try {
            RenderSongList(scene, allSongs, vbox, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        songListScrollPane.setContent(vbox);

        if(65 * allSongs.length() < 561)
            vbox.setStyle("-fx-padding: 0 0 " + (561 - 65 * allSongs.length()) + " 0;");

        vbox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("click");
                        NodeMover.getInstance().SwipeClick(mouseEvent);
                    }
                });

        vbox.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        NodeMover.getInstance().SwipeRelease(mouseEvent);
                    }
                });

        vbox.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        NodeMover.getInstance().SwipeDrag(mouseEvent);
                    }
                });
    }



    public void RenderAllArtistSongs(Scene scene, int artistId) throws JSONException {

        dataParser = DataParser.getInstance();

        VBox songDetailsVbox = (VBox) scene.lookup("#songListVbox");
        songDetailsVbox.getChildren().clear();
        JSONArray allSongs = dataParser.GetAllArtistSongs(artistId);

        RenderSongList(scene, allSongs, songDetailsVbox);
    }


    public void RenderSongList(Scene scene, JSONArray objectArrray, VBox vBox){
        try {
            RenderSongList(scene, objectArrray, vBox, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void RenderSongList(Scene scene, JSONArray objectArrray, VBox vBox, boolean useImage) throws JSONException {

        queue = SongQueue.getInstance();

        vBox.getChildren().clear();

        ArrayList<String> urlList = new ArrayList<>();

        for (int i = 0; i < objectArrray.length(); i ++)
            urlList.add(objectArrray.getJSONObject(i).getString("url"));

        for (int i = 0; i < objectArrray.length(); i ++){

            HBox hBox = useImage ? SongHBoxGenerator.GenerateSongHboxWithImage(objectArrray.getJSONObject(i)) :
                    SongHBoxGenerator.GenerateSongHbox(objectArrray.getJSONObject(i), i + 1);

            int finalI = i;
            if(useImage) {
                hBox.addEventHandler(MouseEvent.ANY,
                        new NodeEventHandler(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                NodeMover.getInstance().SwipeDrag(mouseEvent);
                            }
                        },
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                queue.ClearQueue();
                                queue.AddAllSongs(urlList);
                                queue.SetPos(finalI);
                                MusicPlayer.getInstance().PlaySong(scene, queue.GetCurrentSongUrl());
                            }
                        }));
            } else {
                hBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            queue.ClearQueue();
                            queue.AddAllSongs(urlList);
                            queue.SetPos(finalI);
                            MusicPlayer.getInstance().PlaySong(scene, queue.GetCurrentSongUrl());
                        }
                    });
            }

            vBox.getChildren().add(hBox);
        }

        Button playAllBtn = (Button) scene.lookup("#playAllSongsBtn");
        playAllBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                queue.ClearQueue();
                queue.AddAllSongs(urlList);
                MusicPlayer.getInstance().PlaySong(scene, queue.GetCurrentSongUrl());
            }
        });
    }
}

package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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


    public void RenderAllArtistSongs(Scene scene, int artistId) throws JSONException {

        dataParser = DataParser.getInstance();

        VBox songDetailsVbox = (VBox) scene.lookup("#songListVbox");
        songDetailsVbox.getChildren().clear();
        JSONArray allSongs = dataParser.GetAllArtistSongs(artistId);

        RenderSongList(scene, allSongs, songDetailsVbox);
    }



    public void RenderSongList(Scene scene, JSONArray objectArrray, VBox vBox){

        queue = SongQueue.getInstance();

        vBox.getChildren().clear();

        ArrayList<String> urlList = new ArrayList<>();

        for (int i = 0; i < objectArrray.length(); i ++)
            urlList.add(objectArrray.getJSONObject(i).getString("url"));

        for (int i = 0; i < objectArrray.length(); i ++){

            HBox hBox = SongHBoxGenerator.GenerateSongHbox(objectArrray.getJSONObject(i), i + 1);
            int finalI = i;
            hBox.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            queue.ClearQueue();
                            queue.AddAllSongs(urlList);
                            queue.SetPos(finalI);
                            MusicPlayer.getInstance().PlaySong(scene, queue.GetCurrentSongUrl());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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

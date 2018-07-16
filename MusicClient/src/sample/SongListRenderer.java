package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;

public class SongListRenderer {

    private DataParser dataParser;

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

        for (int i = 0; i < objectArrray.length(); i ++){

            Button btn1 = new Button();
            btn1.setText(objectArrray.getJSONObject(i).getString("name"));
            vBox.getChildren().add(btn1);

            int finalI = i;
            btn1.setOnAction(event -> {
                try {
                    MusicPlayer.getInstance().PlaySong(scene, objectArrray.getJSONObject(finalI).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}

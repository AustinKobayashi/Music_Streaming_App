package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Austin on 2018-07-05.
 */
public class AlbumDetailsRenderer {

    private DataParser dataParser;

    private static AlbumDetailsRenderer instance = new AlbumDetailsRenderer();

    private AlbumDetailsRenderer(){}

    public static AlbumDetailsRenderer getInstance(){ return instance; }



    public void RenderAlbumDetails(Scene scene, int albumId) throws JSONException {

        dataParser = DataParser.getInstance();

        VBox albumDetailsVbox = (VBox) scene.lookup("#albumDetailsVbox");
        albumDetailsVbox.getChildren().clear();
        JSONArray albumSongs = dataParser.GetAllAlbumSongs(albumId);

        SongListRenderer songListRenderer = SongListRenderer.getInstance();

        songListRenderer.RenderSongList(scene, albumSongs, albumDetailsVbox);
    }
}

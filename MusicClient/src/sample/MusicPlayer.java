package sample;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Austin on 2018-07-05.
 */
public class MusicPlayer {


    private static MusicPlayer instance = new MusicPlayer();

    private MusicPlayer(){}

    public static MusicPlayer getInstance(){ return instance; }

    MediaPlayer mediaPlayer;

    Playlist currentPlaylist;

    public void PlaySong(Scene scene, String url) {

        if (url == null)
            return;

        Pane mediaPlayerPane = (Pane) scene.lookup("#mediaPlayerPane");

        if(mediaPlayer != null) {
            mediaPlayer.dispose();
            mediaPlayerPane.getChildren().clear();
        }

        Media media = new Media(Test.base_url + "/" + url);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                PlaySong(scene, SongQueue.getInstance().GetNextSongUrl());
            }
        });
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayerPane.getChildren().add(mediaView);
        mediaPlayer.play();
    }



    public void NextSong(){
        PlaySong(Test.scene, SongQueue.getInstance().GetNextSongUrlWithLoop());
    }



    public void PreviousSong(){

        if(mediaPlayer.getCurrentTime().toMillis() >= 3000)
            mediaPlayer.seek(new Duration(0));
        else
            PlaySong(Test.scene, SongQueue.getInstance().GetPreviousSongUrl());
    }



    public void ToggleMusicPlayback(){

        try {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        } catch (NullPointerException e){
            System.out.println("Null media player");
        }
    }
}

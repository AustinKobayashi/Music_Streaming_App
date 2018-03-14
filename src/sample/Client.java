package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{/*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        */
        Client http = new Client();
        System.out.println("Testing 1 - Send Http GET request");
        http.RequestSong("Macklemore_Thrift_Shop");
        //while(currentSongPart == null){}
        player = new MediaPlayer(new Media(currentSongPart.toURI().toString()));
        nextPlayer =new MediaPlayer(new Media(nextSongPart.toURI().toString()));
        player.play();
        SetOnEnd();


        /*
        player.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                currentSongPart.delete();
                currentSongPart = nextSongPart;
                nextSongPart = null;
            }
        });
        */
        //Media media = new Media(songPart.toURI().toString()); //replace /Movies/test.mp3 with your file
        //player = new MediaPlayer(media);
        //player.play();
    }

    void SetOnEnd(){
        player.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                nextPlayer.play();
                player = nextPlayer;
                try {
                    currentSongPart.delete();
                    //currentSongPart = nextSongPart;
                    Files.move(nextSongPart.toPath(), currentSongPart.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    nextSongPart = null;
                    RequestSongPart(index.GetNextSongPartPath());
                } catch (Exception e){}

                nextPlayer = new MediaPlayer(new Media(nextSongPart.toURI().toString()));
                //player.play();
                SetOnEnd();
            }
        });
    }

    private final String USER_AGENT = "Client";
    private String url = "http://localhost:8000/MusicServer";
    //List<MediaPlayer> players = new ArrayList<MediaPlayer>();
    MediaPlayer player;
    MediaPlayer nextPlayer;
    static Index index;
    static File currentSongPart = null;
    static File nextSongPart = null;


    public static void main(String[] args) throws Exception { launch(args); }




    private void RequestSong(String song) throws Exception{

        index = RequestSongIndex(song);
        RequestSongPart(index.GetNextSongPartPath());
        RequestSongPart(index.GetNextSongPartPath());
    }



    private Index RequestSongIndex(String song) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Index", song);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//System.out.println(response.toString());
        return new Index(response.toString());
    }


    private void RequestSongPart(String song) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Song", song);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        //BufferedReader in = new BufferedReader(
        //      new InputStreamReader(con.getInputStream()));

        InputStream is = con.getInputStream();
        byte[] songBytes = IOUtils.toByteArray(is);

        if(currentSongPart == null) {
            File songPart = new File("currentSongPart.mp3");
            FileUtils.writeByteArrayToFile(songPart, songBytes);
            currentSongPart = songPart;
        }else {
            File songPart = new File("nextSongPart.mp3");
            FileUtils.writeByteArrayToFile(songPart, songBytes);
            nextSongPart = songPart;
        }
    }
}

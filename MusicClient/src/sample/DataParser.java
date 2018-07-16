package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    public HashMap<Integer, JSONObject> artists = new HashMap<>();
    public HashMap<Integer, JSONObject> albums = new HashMap<>();
    public HashMap<Integer, JSONObject> songs = new HashMap<>();
    public HashMap<Integer, JSONObject> genres = new HashMap<>();

    private static DataParser instance = new DataParser();

    private DataParser(){}

    public static DataParser getInstance(){ return instance; }


    public void ParseData() throws JSONException {

        String artists_string = ParseCache("artists");
        assert artists_string != null;
        JSONArray artist_array = new JSONArray(artists_string);
        for(int i = 0; i < artist_array.length(); i++){
            artists.put(artist_array.getJSONObject(i).getInt("id"), artist_array.getJSONObject(i));
        }

        String albums_string = ParseCache("albums");
        assert albums_string != null;
        JSONArray album_array = new JSONArray(albums_string);
        for(int i = 0; i < album_array.length(); i++){
            albums.put(album_array.getJSONObject(i).getInt("id"), album_array.getJSONObject(i));
        }

        String songs_string = ParseCache("songs");
        assert songs_string != null;
        JSONArray songs_array = new JSONArray(songs_string);
        for(int i = 0; i < songs_array.length(); i++){
            songs.put(songs_array.getJSONObject(i).getInt("id"), songs_array.getJSONObject(i));
        }

        String genres_string = ParseCache("genres");
        assert genres_string != null;
        JSONArray genres_array = new JSONArray(genres_string);
        for(int i = 0; i < genres_array.length(); i++){
            genres.put(genres_array.getJSONObject(i).getInt("id"), genres_array.getJSONObject(i));
        }
    }



    private String ParseCache(String url){

        try{

            //File text_file = new File("cache\\" + url + ".txt");

            List<String> data_list = Files.readAllLines(Paths.get("cache/" + url + ".txt"));

            return data_list.get(0);

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



    public JSONArray GetAllArtistAlbums(int artistId) throws JSONException {

        JSONArray allArtistAlbums = new JSONArray();

        for(JSONObject album : albums.values()){
            if(album.getInt("artist_id") == artistId)
                allArtistAlbums.put(album);
        }

        return allArtistAlbums;
    }


    public JSONArray GetAllArtistSongs(int artistId) throws JSONException {

        JSONArray allArtistSongs = new JSONArray();

        for(JSONObject song : songs.values()){
            if(song.getInt("artist_id") == artistId)
                allArtistSongs.put(song);
        }

        return allArtistSongs;
    }

    

    public JSONArray GetAllAlbumSongs(int albumId) throws JSONException {

        JSONArray allAlbumSongs = new JSONArray();

        for(JSONObject song : songs.values()){
            if(song.getInt("album_id") == albumId)
                allAlbumSongs.put(song);
        }

        return allAlbumSongs;
    }
}

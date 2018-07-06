package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 2018-07-05.
 */
public class Playlist {

    public String name;
    ArrayList<String> playList = new ArrayList<>();
    int position = 0;

    public Playlist(String name){
        this.name = name;
    }


    public void AddSong(String url){
        playList.add(url);
    }


    public String GetNextSong(){

        String nextSong = playList.get(position++);

        if(position >= playList.size())
            position = 0;

        return nextSong;
    }
}

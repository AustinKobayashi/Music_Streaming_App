package sample;

import java.util.ArrayList;
import java.util.Collection;

public class SongQueue {

    private ArrayList<String> queue = new ArrayList<>();
    private int pos = 0;

    private static SongQueue instance = new SongQueue();

    private SongQueue(){}

    public static SongQueue getInstance(){ return instance; }


    public void SetPos(int pos){
        this.pos = pos;
    }


    public String GetCurrentSongUrl(){
        if(pos >= queue.size())
            return null;

        return queue.get(pos);
    }



    public String GetNextSongUrl(){
        pos++;

        if(pos >= queue.size())
            return null;

        return queue.get(pos);
    }



    public String GetPreviousSongUrl(){
        pos --;

        if(pos < 0)
            pos = 0;

        return queue.get(pos);
    }



    public String GetNextSongUrlWithLoop(){
        pos++;

        if(pos >= queue.size())
            pos = 0;

        return queue.get(pos);
    }


    public void AddSong(String url){
        queue.add(url);
    }



    public void AddAllSongs(Collection<String> songs){
        queue.addAll(songs);
    }


    public void AddAndPlaySong(String url){
        queue.add(url);
        pos = queue.size() - 1;
    }


    public void ClearQueue(){
        queue.clear();
    }
}

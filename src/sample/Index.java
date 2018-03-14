package sample;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 2018-03-14.
 */
public class Index {

    String index;
    List<SongPart> songParts = new ArrayList<SongPart>();
    int numSongParts;
    int pos = 0;

    Index(String index){
        this.index = index;
        ParseIndex();
        numSongParts = songParts.size();
    }


    void ParseIndex(){
        String[] lines = index.split("#");

        for(int i = 0; i < lines.length; i++){
            if(lines[i].contains("EXTINF")){
                int endOfDur = lines[i].indexOf(",");
                float duration = Float.parseFloat(lines[i].substring(7, endOfDur).replace(",", ""));
                String path = lines[i].substring(endOfDur+1).replace("\t", "");
                SongPart songPart = new SongPart(duration, path);
                songParts.add(songPart);
            }
        }
    }


    public String GetNextSongPartPath(){
        String path = songParts.get(pos).path;
        pos++;
        return path;
    }

    public List<SongPart> GetSongParts(){ return songParts; }

    public int GetNumSongParts(){ return numSongParts; }

}

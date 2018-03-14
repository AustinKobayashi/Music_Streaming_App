package sample;

/**
 * Created by Austin on 2018-03-14.
 */
public class SongPart {

    float duration;
    String path;

    SongPart(float duration, String path){
        this.duration = duration;
        this.path = path;
    }


    public float GetDuration(){ return duration; }

    public String GetPath(){ return path; }
}

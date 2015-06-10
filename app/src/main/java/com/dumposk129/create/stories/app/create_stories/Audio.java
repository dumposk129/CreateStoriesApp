package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129.
 */
public class Audio {
    private int _id;
    private String path_audio, duration;

    public Audio(){ }

    public Audio(int id, String path_audio, String duration){
        this._id = id;
        this.path_audio = path_audio;
        this.duration = duration;
    }

    // Setter
    public void setID(int id){
        this._id = id;
    }

    public void setPathAudio(String path_audio){
        this.path_audio = path_audio;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    // Getter
    public int getID(){
        return this._id;
    }

    public String getPathAudio(){
        return this.path_audio;
    }

    public String getDuration(String duration){
        return this.duration;
    }

}

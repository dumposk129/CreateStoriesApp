package com.dumposk129.create.stories.app.model;

/**
 * Created by DumpOSK129.
 */
public class Audio {
    private int id;
    private String path_audio, duration;

    public Audio(){ }

    public Audio(int id, String path_audio, String duration){
        this.id = id;
        this.path_audio = path_audio;
        this.duration = duration;
    }

    // Setter
    public void setID(int id){
        this.id = id;
    }

    public void setPathAudio(String path_audio){
        this.path_audio = path_audio;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    // Getter
    public int getID(){
        return id;
    }

    public String getPathAudio(){
        return path_audio;
    }

    public String getDuration(String duration){
        return duration;
    }

}

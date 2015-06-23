package com.dumposk129.create.stories.app.model;

/**
 * Created by DumpOSK129.
 */
public class Audio {
    private int id, frame_id;
    private String path_audio, duration;

    public Audio(){ }

    public Audio(int id, int frame_id, String path_audio, String duration){
        this.id = id;
        this.frame_id = frame_id;
        this.path_audio = path_audio;
        this.duration = duration;
    }

    // Setter
    public void setID(int id){
        this.id = id;
    }

    public void setFrameID(int frame_id){
        this.frame_id = frame_id;
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

    public int getFrameID(){
        return frame_id;
    }

    public String getPathAudio(){
        return path_audio;
    }

    public String getDuration(){
        return duration;
    }

}

package com.dumposk129.create.stories.app.model;

/**
 * Created by DumpOSK129.
 */
public class Frame {
    private int id, frame_order, story_id;
    private String audio_path, pic_path;

    public Frame(){ }

    public Frame(int id, int frame_order, String audio_path, String pic_path, int story_id){
        this.id = id;
        this.frame_order = frame_order;
        this.pic_path = pic_path;
        this.audio_path = audio_path;
        this.story_id = story_id;
    }

    public Frame(int id, String pic_path, int story_id){
        this.id = id;
        this.pic_path = pic_path;
        this.story_id = story_id;
    }

    public Frame(int id, String pic_path, String audio_path, int story_id){
        this.id = id;
        this.pic_path = pic_path;
        this.audio_path = audio_path;
        this.story_id = story_id;
    }

    // Setter
    public void setId(int id){
        this.id = id;
    }
    public void setFrameOrder(int frame_order){
        this.frame_order = frame_order;
    }

    public void setPathPic(String path_pic){
        this.pic_path = path_pic;
    }

    public void setStoryId(int story_id){
        this.story_id = story_id;
    }

    public void setPathAudio(String audio_path) {
        this.audio_path = audio_path;
    }
    // Getter
    public int getId(){
        return id;
    }

    public int getFrameOrder(){
        return  frame_order;
    }

    public String getPathPic(){
        return pic_path;
    }

    public int getStoryId(){
        return this.story_id;
    }
    public String getPathAudio() {
        return audio_path;
    }
}

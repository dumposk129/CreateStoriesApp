package com.dumposk129.create.stories.app.model;

/**
 * Created by DumpOSK129.
 */
public class Frame {
    private int id, frame_order, step, story_id;
    private String path_video, path_pic;

    public Frame(){ }

    public Frame(int id, int frame_order, String path_video, String path_pic, int step, int story_id){
        this.id = id;
        this.frame_order = frame_order;
        this.path_video = path_video;
        this.path_pic = path_pic;
        this.step = step;
        this.story_id = story_id;
    }

    public Frame(int frame_order, String path_video, String path_pic, int step, int story_id){
        this.frame_order = frame_order;
        this.path_video = path_video;
        this.path_pic = path_pic;
        this.step = step;
        this.story_id = story_id;
    }

    public Frame(int frame_order, int step, int story_id){
        this.frame_order = frame_order;
        this.step = step;
        this.story_id = story_id;
    }

    public Frame(int id, int step, String path_pic){
        this.id = id;
        this.step = step;
        this.path_pic = path_pic;
    }

    // Setter
    public void setId(int id){
        this.id = id;
    }
    public void setFrameOrder(int frame_order){
        this.frame_order = frame_order;
    }

    public void setPathVideo(String path_video){
        this.path_video = path_video;
    }

    public void setPathPic(String path_pic){
        this.path_pic = path_pic;
    }

    public void setStepId(int step_id){
        this.step = step_id;
    }

    public void setStoryId(int story_id){
        this.story_id = story_id;
    }

    // Getter
    public int getId(){
        return id;
    }

    public int getFrameOrder(){
        return  frame_order;
    }

    public String getPathVideo(){
        return path_video;
    }

    public String getPathPic(){
        return path_pic;
    }

    public int getStep(){
        return step;
    }

    public int getStoryId(){
        return this.story_id;
    }
}

package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129.
 */
public class Frame {
    private int _id, frame_order, step_id;
    private String path_video, path_pic;

    public Frame(){ }

    public Frame(int id, int frame_order, String path_video, String path_pic, int step_id){
        this._id = id;
        this.frame_order = frame_order;
        this.path_video = path_video;
        this.path_pic = path_pic;
        this.step_id = step_id;
    }

    // Setter
    public void setId(int id){
        this._id = id;
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
        this.step_id = step_id;
    }

    // Getter
    public int getId(){
        return this._id;
    }

    public int getFrameOrder(){
        return  this.frame_order;
    }

    public String getPathVideo(){
        return this.path_video;
    }

    public String getPathPic(){
        return this.path_pic;
    }

    public int getStepId(){
        return this.step_id;
    }
}

package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129.
 */
public class Story {
    private int _id;
    private String title_name;
    private boolean isComplete;

    //Default Constructor
    public Story(){ }

    public Story(int id, String title_name, boolean isComplete){
        this._id = id;
        this.title_name = title_name;
        this.isComplete = isComplete;
    }

    public Story(String title_name, boolean isComplete){
        this.title_name = title_name;
        this.isComplete = isComplete;
    }

    // Setter
    public void setId(int id){
        this._id = id;
    }

    public void setTitle(String title_name){
        this.title_name = title_name;
    }

    public void setIsComplete(boolean isComplete){
        this.isComplete = isComplete;
    }

    // Getter
    public int getId(){
        return this._id;
    }

    public String getTitle(){
        return  this.title_name;
    }

    public boolean getIsComplete(){
        return this.isComplete;
    }
}

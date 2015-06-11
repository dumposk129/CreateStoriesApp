package com.dumposk129.create.stories.app.create_stories;

/**
 * Created by DumpOSK129.
 */
public class Story {
    private int _id;
    private String title_name;
    private boolean is_complete;

    //Default Constructor
    public Story(){ }

    public Story(int id, String title_name, boolean is_complete){
        this._id = id;
        this.title_name = title_name;
        this.is_complete = is_complete;
    }

    public Story(String title_name, boolean is_complete){
        this.title_name = title_name;
        this.is_complete = is_complete;
    }

    // Setter
    public void setId(int id){
        this._id = id;
    }

    public void setTitle(String title_name){
        this.title_name = title_name;
    }

    public void setIsComplete(boolean isComplete){
        this.is_complete = isComplete;
    }

    // Getter
    public int getId(){
        return this._id;
    }

    public String getTitle(){
        return  this.title_name;
    }

    public boolean getIsComplete(){
        return this.is_complete;
    }

    public int getIsCompleteInt() { return this.is_complete ? 1 : 0; }
}

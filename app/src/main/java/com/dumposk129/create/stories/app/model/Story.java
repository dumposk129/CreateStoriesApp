package com.dumposk129.create.stories.app.model;

/**
 * Created by DumpOSK129
 */
public class Story {
    private int id;
    private String title_name;
    private boolean is_complete;

    private String pic_path;
    private int questionId;
    private String questionName;

    //Default Constructor
    public Story(){ }

    public Story(int id, String title_name, boolean is_complete){
        this.id = id;
        this.title_name = title_name;
        this.is_complete = is_complete;
    }

    public Story(String title_name, boolean is_complete){
        this.title_name = title_name;
        this.is_complete = is_complete;
    }

    public Story(String title_name, String pic_path){
        this.title_name = title_name;
        this.pic_path = pic_path;
    }


    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_pth) {
        this.pic_path = pic_pth;
    }


    // Setter.
    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title_name){
        this.title_name = title_name;
    }

    public void setIsComplete(boolean isComplete){
        this.is_complete = isComplete;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    // Getter
    public int getId(){
        return id;
    }

    public String getTitle(){
        return title_name;
    }

    public boolean getIsComplete(){
        return is_complete;
    }

    public int getIsCompleteInt() {
        return is_complete ? 1 : 0;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionName() {
        return questionName;
    }
}
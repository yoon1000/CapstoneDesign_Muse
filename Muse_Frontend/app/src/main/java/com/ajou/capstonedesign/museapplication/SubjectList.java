package com.ajou.capstonedesign.museapplication;

public class SubjectList {
    public boolean isSelected;
    private String subject_name;
    private int position;

    public SubjectList(String subject_name, Boolean ischecked){
        this.subject_name = subject_name;
    }

    public String getSubject(){
        return subject_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

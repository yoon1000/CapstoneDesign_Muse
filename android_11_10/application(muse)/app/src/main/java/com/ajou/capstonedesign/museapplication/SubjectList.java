package com.ajou.capstonedesign.museapplication;

public class SubjectList {
    private String subject_name;
    private Boolean ischecked;

    public SubjectList(String subject_name){
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

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }
}

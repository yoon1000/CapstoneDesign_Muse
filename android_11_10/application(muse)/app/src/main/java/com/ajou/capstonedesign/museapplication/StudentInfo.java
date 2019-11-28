package com.ajou.capstonedesign.museapplication;

public class StudentInfo {
    private String studentmajor;
    private String num;

    public String getStudentmajor() {
        return studentmajor;
    }

    public void setStudentmajor(String studentmajor) {
        this.studentmajor = studentmajor;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public static void setInstance(StudentInfo instance) {
        StudentInfo.instance = instance;
    }

    private static StudentInfo instance = null;

    public static synchronized StudentInfo getInstance(){
        if(null==instance){
            instance = new StudentInfo();
        }
        return instance;
    }
}

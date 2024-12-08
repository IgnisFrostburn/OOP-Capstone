package com.example.Database;

import javafx.scene.image.Image;

public class Course {
    private int ID;
    private String instructorName;
    private int instructorID;
    private String courseTitle;
    private String cat1, cat2, cat3;
    private String shortDesc;
    private Image courseImage;

    public Course(int ID, String courseTitle, int instructorID) {
        this.ID = ID;
        this.courseTitle = courseTitle;
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public Course(int ID, String instructorName, String courseTitle, String cat1, String cat2, String cat3, String shortDesc) {
        this.instructorName = instructorName;
        this.courseTitle = courseTitle;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.shortDesc = shortDesc;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCat1() {
        return cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Image getCourseImage() {
        return courseImage;
    }
}

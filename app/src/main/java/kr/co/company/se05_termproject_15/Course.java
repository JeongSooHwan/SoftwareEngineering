package kr.co.company.se05_termproject_15;

public class Course {
    int courseID;
    String courseTitle;
    int courseCredit;
    int courseCapacity;
    String yoil;
    String courseSTime;
    String courseETime;
    String courseRoom;
    String courseGrade;

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getYoil() {
        return yoil;
    }

    public void setYoil(String yoil) {
        this.yoil = yoil;
    }

    public String getCourseSTime() {
        return courseSTime;
    }

    public void setCourseSTime(String courseSTime) {
        this.courseSTime = courseSTime;
    }

    public String getCourseETime() {
        return courseETime;
    }

    public void setCourseETime(String courseETime) {
        this.courseETime = courseETime;
    }

    public int getId() {
        return courseID;
    }

    public void setId(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCoureCredit(int coureCredit) {
        this.courseCredit = coureCredit;
    }

    public int getCourseCapacity() {
        return courseCapacity;
    }

    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }


    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public Course(int courseID, String courseTitle, int courseCredit, int courseCapacity, String yoil, String courseSTime, String courseETime, String courseRoom) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseCapacity = courseCapacity;
        this.yoil = yoil;
        this.courseSTime = courseSTime;
        this.courseETime = courseETime;
        this.courseRoom = courseRoom;
    }

    public Course(String courseTitle, int courseCredit, String courseGrade) {
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseGrade = courseGrade;
    }

}

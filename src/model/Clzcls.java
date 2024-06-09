package model;

public class Clzcls {

    private String id;
    private String subjectName;
    private String classId;
    private String className;
    private String lecturerId;
    private String lecturer;
    private String lecturerContact;
    private String lecturerEmail;
    public String startAt;
    public String endAt;

    public Clzcls() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getLecturerContact() {
        return lecturerContact;
    }

    public void setLecturerContact(String lecturerContact) {
        this.lecturerContact = lecturerContact;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setStartAt(String start) {
        this.startAt = start;
    }

    public void setEnd(String to) {
        this.endAt = to;
    }

    public String getStartAt() {
        return startAt;
    }

    public String getEnd() {
        return endAt;
    }

}

package model;

public interface UpdateCallback {
    
    void onMobileUpdateClosed(String newMobile);
    void onEmailUpdateClosed(String newEmail);
    void onAddressUpdateClosed(String line1,String line2);
    void onDepartmentUpdateClosed(String department);
    void onSubjectUpdateClosed(String subject);
    void onEmergencyContactUpdateClosed(String newEC1Name,String newEC1Contact,String newEC2Name,String newEC2Contact);
    void onClassUpdateClosed(String classId, String className);
    void onLecturerUpdateClosed(String classId, String lecturerName);
    void onClassPalletSubjectUpdateClosed(String classId, String subjectName);
    
}

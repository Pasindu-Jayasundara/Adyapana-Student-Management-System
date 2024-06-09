package gui;

import java.awt.Toolkit;
import model.MySQL;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Administrator;
import model.Student;
import model.Subject;
import model.Teacher;
import model.UpdateCallback;
import model.Clzcls;

public class Management extends javax.swing.JPanel implements UpdateCallback {
    
    private HashMap<String, Administrator> administratorMap = new HashMap<>();
    private String administratorFullQuery = "SELECT * FROM `administrator` "
            + "INNER JOIN `surname` ON `administrator`.`surname_surname_id`=`surname`.`surname_id` "
            + "INNER JOIN `administrator_address` ON `administrator_address`.`administrator_administrator_id`=`administrator`.`administrator_id` "
            + "INNER JOIN `administrator_department` ON `administrator_department`.`administrator_department_id`=`administrator`.`administrator_department_administrator_department_id` "
            + "INNER JOIN `gender` ON `gender`.`gender_id`=`administrator`.`gender_gender_id`";
    private String tableSelectedUserId;
    private String tableSelectedUserMobile;
    private String tableSelectedUserAddressLine1;
    private String tableSelectedUserAddressLine2;
    private String tableSelectedUserEmail;
    private String tableSelectedUserDepartment;
    private Integer selectedTableRow;
    
    private String tableSelectedUserEmergencyName1;
    private String tableSelectedUserEmergencyContact1;
    private String tableSelectedUserEmergencyName2;
    private String tableSelectedUserEmergencyContact2;
    private String tableSelectedUserEmergencyContactId1;
    private String tableSelectedUserEmergencyContactId2;
    
    private Integer table = 1;
    
    private boolean isTeacherSubjectBtnClicked = false;
    private boolean isAdministratorDepartmentClicked = false;
    
    private String teacherFullQuery = "SELECT * FROM `teacher` "
            + "INNER JOIN `surname` ON `teacher`.`surname_surname_id`=`surname`.`surname_id` "
            + "INNER JOIN `teacher_address` ON `teacher_address`.`teacher_teacher_id`=`teacher`.`teacher_id` "
            + "INNER JOIN `subject` ON `subject`.`subject_id`=`teacher`.`subject_subject_id` "
            + "INNER JOIN `gender` ON `gender`.`gender_id`=`teacher`.`gender_gender_id`";
    private HashMap<String, Teacher> teacherMap = new HashMap<>();
    
    private String StudentFullQuery = "SELECT * FROM `student` "
            + "INNER JOIN `surname` ON `student`.`surname_surname_id`=`surname`.`surname_id` "
            + "INNER JOIN `subject` ON `subject`.`subject_id`=`student`.`subject_subject_id` "
            + "INNER JOIN `student_address` ON `student_address`.`student_student_id`=`student`.`student_id` "
            + "INNER JOIN `gender` ON `gender`.`gender_id`=`student`.`gender_gender_id`";
    private HashMap<String, Student> studentMap = new HashMap<>();
    
    private String subMainQuery = "SELECT * FROM `subject` INNER JOIN `teacher` ON `teacher`.`subject_subject_id`=`subject`.`subject_id` "
            + "INNER JOIN `surname` ON `teacher`.`surname_surname_id`=`surname`.`surname_id` "
            + "INNER JOIN `class` ON `class`.`class_id`=`subject`.`class_class_id` ";
    private HashMap<String, Subject> subjectMap = new HashMap<>();
    private boolean isOneRoundDone = true;
    private Integer subTableClickedRow;
    private String selectedSubjectId;
    private String selectedSubjectTeacherId;
    private String selectedClassId;
    
    private String classMainQuery = "SELECT * FROM `class` INNER JOIN `subject` ON `class`.`class_id`=`subject`.`class_class_id` "
            + "INNER JOIN `teacher` ON `teacher`.`subject_subject_id`=`subject`.`subject_id` "
            + "INNER JOIN `surname` ON `teacher`.`surname_surname_id`=`surname`.`surname_id` ";
    private HashMap<String, Clzcls> classMap = new HashMap<>();
    private Integer clzTableClickedRow;
    
    public Management() {
        initComponents();
        setUpAdministratorBg();
        setUpTeacherBg();
        setUpStudentBg();
        loadSubjectPanel();
    }
    
    private void setUpAdministratorBg() {
        jButton1.setVisible(false);
        jSpinner1.setEnabled(false);
        
        startatupdatebtn.setVisible(false);
        endatupdatebtn.setVisible(false);
        administratorMap.clear();
        
        pddPanel.setVisible(false);
        pidpanel.setVisible(false);
        a_pt_btn.setVisible(false);
        
        DefaultTableModel dtm = (DefaultTableModel) a_table.getModel();
        dtm.setRowCount(0);
        
        try {
            
            String originalAdministratorFullQuery = administratorFullQuery;
            administratorFullQuery += " WHERE `administrator`.`administrator_department_administrator_department_id`='1' "
                    + "AND `administrator`.`status_status_id`='1'";
            
            ResultSet resultset = MySQL.executeSearch(administratorFullQuery);
            int rowCount = 0;
            if (resultset.last()) {
                rowCount = resultset.getRow();
                resultset.beforeFirst();
            }
            
            if (rowCount > 0) {// departments need to be assigned
                dipartmentAssignRequired_btn.setVisible(true);
            } else {
                administratorMap.clear();
                dipartmentAssignRequired_btn.setVisible(false);
            }
            administratorFullQuery = originalAdministratorFullQuery;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void setUpTeacherBg() {
        
        teacherMap.clear();
        
        t_add_panel.setVisible(false);
        t_pp_panel.setVisible(false);
        t_pt_btn.setVisible(false);
        
        DefaultTableModel dtm = (DefaultTableModel) t_table.getModel();
        dtm.setRowCount(0);
        
        try {
            
            String backup = teacherFullQuery;
            teacherFullQuery += " WHERE `teacher`.`subject_subject_id`='1' AND `teacher`.`status_status_id`='1'";
            
            ResultSet resultset = MySQL.executeSearch(teacherFullQuery);
            int rowCount = 0;
            if (resultset.last()) {
                rowCount = resultset.getRow();
                resultset.beforeFirst();
            }
            
            if (rowCount > 0) {// departments need to be assigned
                l_subjectAssignReq_btn.setVisible(true);
            } else {
                teacherMap.clear();
                l_subjectAssignReq_btn.setVisible(false);
            }
            teacherFullQuery = backup;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void setUpStudentBg() {
        
        studentMap.clear();
        
        s_adp_panel.setVisible(false);
        s_pp_panel.setVisible(false);
        s_printTable.setVisible(false);
        
        DefaultTableModel dtm = (DefaultTableModel) s_table.getModel();
        dtm.setRowCount(0);
        s_table.setModel(dtm);
        
    }
    
    private void addToAdministratorTable(String query) {
        try {
            a_pt_btn.setVisible(true);
            ResultSet resultset = MySQL.executeSearch(query);
            
            DefaultTableModel dtm = (DefaultTableModel) a_table.getModel();
            dtm.setRowCount(0);
            
            while (resultset.next()) {
                
                Vector<String> vector = new Vector();
                vector.add(resultset.getString("administrator_id"));
                vector.add(resultset.getString("surname.surname") + " " + resultset.getString("administrator.first_name") + " " + resultset.getString("administrator.last_name"));
                vector.add(resultset.getString("administrator.mobile"));
                vector.add(resultset.getString("administrator.nic"));
                
                if (!administratorMap.containsKey(resultset.getString("administrator_id"))) {
                    
                    Administrator administrator = new Administrator();
                    
                    administrator.setId(resultset.getString("administrator_id"));
                    administrator.setFname(resultset.getString("first_name"));
                    administrator.setLname(resultset.getString("last_name"));
                    administrator.setNic(resultset.getString("nic"));
                    administrator.setMobile(resultset.getString("mobile"));
                    administrator.setLine1(resultset.getString("line_1"));
                    administrator.setLine2(resultset.getString("line_2"));
                    administrator.setEmail(resultset.getString("email"));
                    administrator.setDepartment(resultset.getString("administrator_department.name"));
                    administrator.setImageUrl(resultset.getString("image_url"));
                    administrator.setGender(resultset.getString("gender"));
                    administrator.setRegisterdDateTime(resultset.getString("registered_date_time"));
                    administrator.setSurname(resultset.getString("surname"));
                    
                    ResultSet rs = MySQL.executeSearch("SELECT * FROM `administrator_emergency_contact` WHERE `administrator_emergency_contact`.`administrator_administrator_id`='" + resultset.getString("administrator_id") + "'");
                    if (rs.next()) {
                        administrator.setEci1(rs.getString("administrator_emergency_contact_id"));
                        administrator.setEcn1(rs.getString("name"));
                        administrator.setEcc1(rs.getString("contact"));
                    }
                    if (rs.next()) {
                        administrator.setEci2(rs.getString("administrator_emergency_contact_id"));
                        administrator.setEcn2(rs.getString("name"));
                        administrator.setEcc2(rs.getString("contact"));
                    }
                    
                    administratorMap.put(resultset.getString("administrator_id"), administrator);
                    
                }
                
                dtm.addRow(vector);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addToTeacherTable(String query) {
        try {
            
            ResultSet resultset = MySQL.executeSearch(query);
            
            DefaultTableModel dtm = (DefaultTableModel) t_table.getModel();
            dtm.setRowCount(0);
            
            while (resultset.next()) {
                
                Vector<String> vector = new Vector();
                vector.add(resultset.getString("teacher_id"));
                vector.add(resultset.getString("surname.surname") + " " + resultset.getString("teacher.first_name") + " " + resultset.getString("teacher.last_name"));
                vector.add(resultset.getString("teacher.mobile"));
                vector.add(resultset.getString("teacher.nic"));
                
                if (!teacherMap.containsKey(resultset.getString("teacher_id"))) {
                    
                    Teacher teacher = new Teacher();
                    
                    teacher.setId(resultset.getString("teacher_id"));
                    teacher.setFname(resultset.getString("first_name"));
                    teacher.setLname(resultset.getString("last_name"));
                    teacher.setNic(resultset.getString("nic"));
                    teacher.setMobile(resultset.getString("mobile"));
                    teacher.setLine1(resultset.getString("line_1"));
                    teacher.setLine2(resultset.getString("line_2"));
                    teacher.setEmail(resultset.getString("email"));
                    teacher.setSubject(resultset.getString("subject.subject"));
                    teacher.setImageUrl(resultset.getString("image_url"));
                    teacher.setGender(resultset.getString("gender"));
                    teacher.setRegisterdDateTime(resultset.getString("registered_date_time"));
                    teacher.setSurname(resultset.getString("surname"));
                    
                    ResultSet rs = MySQL.executeSearch("SELECT * FROM `teacher_emergency_contact` WHERE `teacher_emergency_contact`.`teacher_teacher_id`='" + resultset.getString("teacher_id") + "'");
                    if (rs.next()) {
                        teacher.setEci1(rs.getString("teacher_emergency_contact_id"));
                        teacher.setEcn1(rs.getString("name"));
                        teacher.setEcc1(rs.getString("contact"));
                    }
                    if (rs.next()) {
                        teacher.setEci2(rs.getString("teacher_emergency_contact_id"));
                        teacher.setEcn2(rs.getString("name"));
                        teacher.setEcc2(rs.getString("contact"));
                    }
                    
                    teacherMap.put(resultset.getString("teacher_id"), teacher);
                    
                }
                
                dtm.addRow(vector);
                
            }
            
            t_pt_btn.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addToStudentTable(String query) {
        try {
            
            ResultSet resultset = MySQL.executeSearch(query);
            
            DefaultTableModel dtm = (DefaultTableModel) s_table.getModel();
            dtm.setRowCount(0);
            
            while (resultset.next()) {
                
                Vector<String> vector = new Vector();
                vector.add(resultset.getString("student_id"));
                vector.add(resultset.getString("surname.surname") + " " + resultset.getString("student.first_name") + " " + resultset.getString("student.last_name"));
                vector.add(resultset.getString("student.mobile"));
                vector.add(resultset.getString("student.nic"));
                
                if (!studentMap.containsKey(resultset.getString("student_id"))) {
                    
                    Student student = new Student();
                    
                    student.setId(resultset.getString("student_id"));
                    student.setFname(resultset.getString("first_name"));
                    student.setLname(resultset.getString("last_name"));
                    student.setNic(resultset.getString("nic"));
                    student.setMobile(resultset.getString("mobile"));
                    student.setLine1(resultset.getString("line_1"));
                    student.setLine2(resultset.getString("line_2"));
                    student.setSubject(resultset.getString("subject.subject"));
                    student.setEmail(resultset.getString("email"));
                    student.setImageUrl(resultset.getString("image_url"));
                    student.setGender(resultset.getString("gender"));
                    student.setRegisterdDateTime(resultset.getString("registered_date_time"));
                    student.setSurname(resultset.getString("surname"));
                    
                    ResultSet rs = MySQL.executeSearch("SELECT * FROM `c` WHERE `student_emergency_contact`.`student_student_id`='" + resultset.getString("student_id") + "'");
                    if (rs.next()) {
                        student.setEci1(rs.getString("student_emergency_contact_id"));
                        student.setEcn1(rs.getString("name"));
                        student.setEcc1(rs.getString("contact"));
                    }
                    if (rs.next()) {
                        student.setEci2(rs.getString("student_emergency_contact_id"));
                        student.setEcn2(rs.getString("name"));
                        student.setEcc2(rs.getString("contact"));
                    }
                    
                    studentMap.put(resultset.getString("student_id"), student);
                    
                }
                
                dtm.addRow(vector);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String time(String time) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        LocalTime newTime = LocalTime.parse(time, inputFormat);
        return newTime.format(outputFormat);
    }
    
    @Override
    public void onMobileUpdateClosed(String newMobile) {
        
        if (table == 1) {//administrator

            a_mobile_lbl.setText(newMobile);
            a_table.setValueAt(newMobile, selectedTableRow, 2);
            Administrator administrator = administratorMap.get(tableSelectedUserId);
            administrator.setMobile(newMobile);
            
        } else if (table == 2) {//teacher

            t_table.setValueAt(newMobile, selectedTableRow, 2);
            t_mobile_lbl.setText(newMobile);
            Teacher teacher = teacherMap.get(tableSelectedUserId);
            teacher.setMobile(newMobile);
            
        } else if (table == 3) {//student

            s_table.setValueAt(newMobile, selectedTableRow, 2);
            s_mobile_lbl.setText(newMobile);
            Student student = studentMap.get(tableSelectedUserId);
            student.setMobile(newMobile);
        }
        
    }
    
    @Override
    public void onEmailUpdateClosed(String newEmail) {
        
        if (table == 1) {//administrator

            a_email_lbl.setText(newEmail);
            Administrator administrator = administratorMap.get(tableSelectedUserId);
            administrator.setEmail(newEmail);
            
        } else if (table == 2) {//teacher

            t_email_lbl.setText(newEmail);
            Teacher teacher = teacherMap.get(tableSelectedUserId);
            teacher.setEmail(newEmail);
            
        } else if (table == 3) {//student

            s_email_lbl.setText(newEmail);
            Student student = studentMap.get(tableSelectedUserId);
            student.setEmail(newEmail);
            
        }
        
    }
    
    @Override
    public void onAddressUpdateClosed(String line1, String line2) {
        
        if (table == 1) {//administrator

            a_address_lbl.setText(line1 + ", " + line2);
            
            Administrator administrator = administratorMap.get(tableSelectedUserId);
            administrator.setLine1(line1);
            administrator.setLine2(line2);
            
        } else if (table == 2) {//teacher

            t_address_lbl.setText(line1 + ", " + line2);
            Teacher teacher = teacherMap.get(tableSelectedUserId);
            teacher.setLine1(line1);
            teacher.setLine2(line2);
            
        } else if (table == 3) {//student

            s_address_lbl.setText(line1 + ", " + line2);
            Student student = studentMap.get(tableSelectedUserId);
            student.setLine1(line1);
            student.setLine2(line2);
            
        }
        
    }
    
    @Override
    public void onDepartmentUpdateClosed(String newDepartment) {
        
        a_department_lbl.setText(newDepartment);
        
        if (isAdministratorDepartmentClicked) {
            DefaultTableModel dtm = (DefaultTableModel) a_table.getModel();
            dtm.removeRow(selectedTableRow);
            a_table.setModel(dtm);
        }
        
        Administrator administrator = administratorMap.get(tableSelectedUserId);
        administrator.setDepartment(newDepartment);
        
    }
    
    @Override
    public void onSubjectUpdateClosed(String newSubject) {
        
        if (table == 2) {//teacher

            t_subject_lbl.setText(newSubject);
            
            if (isTeacherSubjectBtnClicked) {
                DefaultTableModel dtm = (DefaultTableModel) t_table.getModel();
                dtm.removeRow(selectedTableRow);
                int rows = dtm.getRowCount();
                t_table.setModel(dtm);
                t_pt_btn.setVisible(false);
                t_add_panel.setVisible(false);
                t_pp_panel.setVisible(false);
                if (rows == 0) {
                    l_subjectAssignReq_btn.setVisible(false);
                }
            }
            
            Teacher teacher = teacherMap.get(tableSelectedUserId);
            teacher.setSubject(newSubject);
            
        } else if (table == 3) {//student

            s_subject_lbl.setText(newSubject);
            Student student = studentMap.get(tableSelectedUserId);
            student.setSubject(newSubject);
            
        }
        
    }
    
    @Override
    public void onEmergencyContactUpdateClosed(String newEC1Name, String newEC1Contact, String newEC2Name, String newEC2Contact) {
        
        if (table == 1) {//administrator

            a_ecn_lbl.setText(newEC1Name);
            a_ecnu_lbl.setText(newEC1Contact);
            a_ecn_lb.setText(newEC2Name);
            a_ecnu_lb.setText(newEC2Contact);
            
            Administrator administrator = administratorMap.get(tableSelectedUserId);
            administrator.setEcn1(newEC1Name);
            administrator.setEcc1(newEC1Contact);
            administrator.setEcn2(newEC2Name);
            administrator.setEcc2(newEC2Contact);
            
        } else if (table == 2) {//teacher

            t_ecn_lbl.setText(newEC1Name);
            t_ecnu_lbl.setText(newEC1Contact);
            t_ecn_lb.setText(newEC2Name);
            t_ecnu_lb.setText(newEC2Contact);
            
            Teacher teacher = teacherMap.get(tableSelectedUserId);
            teacher.setEcn1(newEC1Name);
            teacher.setEcc1(newEC1Contact);
            teacher.setEcn2(newEC2Name);
            teacher.setEcc2(newEC2Contact);
            
        } else if (table == 3) {//student

            s_ecn_lbl.setText(newEC1Name);
            s_ecnu_lbl.setText(newEC1Contact);
            s_ecn_lb2.setText(newEC2Name);
            s_ecnu_lb2.setText(newEC2Contact);
            
            Student student = studentMap.get(tableSelectedUserId);
            student.setEcn1(newEC1Name);
            student.setEcc1(newEC1Contact);
            student.setEcn2(newEC2Name);
            student.setEcc2(newEC2Contact);
            
        }
        
    }
    
    @Override
    public void onClassUpdateClosed(String classId, String className) {
        
        selectedClassId = classId;
        sub_class_lbl.setText(className);
        subTable.setValueAt(className, subTableClickedRow, 3);
        
        subjectMap.get(selectedSubjectId).setClassId(classId);
        
    }
    
    @Override
    public void onLecturerUpdateClosed(String classId, String lecturerName) {
        
        selectedClassId = classId;
        sub_lec_lbl.setText(lecturerName);
        
    }
    
    @Override
    public void onClassPalletSubjectUpdateClosed(String classId, String subjectName) {
        
        selectedClassId = classId;
        cl_sub_lbl.setText(subjectName);
        cl_lec_lbl.setText(subjectName);
        
    }
    
    private void loadSubjectPanel() {
        
        subjectMap.clear();
        
        sub_table_print_btn.setVisible(false);
        sub_addi_det_panel.setVisible(false);
        
        sub_name_textfield.setText("");
        
        DefaultTableModel dtm = (DefaultTableModel) subTable.getModel();
        dtm.setRowCount(0);
        subTable.setModel(dtm);
        
    }
    
    private void addToSubjectTable(String query) {
        
        try {
            ResultSet rs = MySQL.executeSearch(query);
            
            DefaultTableModel dtm = (DefaultTableModel) subTable.getModel();
            dtm.setRowCount(0);
            
            while (rs.next()) {
                
                Vector<String> vector = new Vector();
                vector.add(rs.getString("subject_id"));
                vector.add(rs.getString("subject"));
                vector.add(rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
                vector.add(rs.getString("class_name"));
                
                if (!subjectMap.containsKey(rs.getString("subject_id"))) {
                    
                    Subject subject = new Subject();
                    subject.setId(rs.getString("subject_id"));
                    subject.setSubjectName(rs.getString("subject"));
                    subject.setLecturer(rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    subject.setLecturerContact(rs.getString("mobile"));
                    subject.setLecturerEmail(rs.getString("email"));
                    subject.setClassName(rs.getString("class_name"));
                    subject.setLecturerId(rs.getString("teacher_id"));
                    subject.setClassId("class_id");
                    
                    subjectMap.put(rs.getString("subject_id"), subject);
                    
                }
                
                dtm.addRow(vector);
            }
            subTable.setModel(dtm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void loadClassPanel() {
        
        classMap.clear();
        
        cl_pr_tb_btn.setVisible(false);
        cl_addi_det_panel.setVisible(false);
        
        cl_name_textfield.setText("");
        
        DefaultTableModel dtm = (DefaultTableModel) cl_table.getModel();
        dtm.setRowCount(0);
        cl_table.setModel(dtm);
        
    }
    
    private void addToClassTable(String query) {
        
        try {
            ResultSet rs = MySQL.executeSearch(query);
            
            DefaultTableModel dtm = (DefaultTableModel) cl_table.getModel();
            dtm.setRowCount(0);
            
            while (rs.next()) {
                
                Vector<String> vector = new Vector();
                vector.add(rs.getString("class_id"));
                vector.add(rs.getString("class_name"));
                vector.add(rs.getString("subject"));
                vector.add(rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
                
                if (!classMap.containsKey(rs.getString("class_id"))) {
                    
                    Clzcls clss = new Clzcls();
                    clss.setId(rs.getString("class_id"));
                    clss.setSubjectName(rs.getString("subject"));
                    clss.setLecturer(rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    clss.setLecturerContact(rs.getString("mobile"));
                    clss.setLecturerEmail(rs.getString("email"));
                    clss.setClassName(rs.getString("class_name"));
                    clss.setLecturerId(rs.getString("teacher_id"));
                    clss.setClassId("class_id");
                    clss.setStartAt("start");
                    clss.setEnd("to");
                    
                    classMap.put(rs.getString("class_id"), clss);
                    
                }
                
                dtm.addRow(vector);
            }
            cl_table.setModel(dtm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        startAt = new com.raven.swing.TimePicker();
        endAt = new com.raven.swing.TimePicker();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        administrator = new javax.swing.JPanel();
        dipartmentAssignRequired_btn = new javax.swing.JButton();
        a_loadAll_btn = new javax.swing.JButton();
        a_search_id_btn = new javax.swing.JButton();
        pidpanel = new javax.swing.JPanel();
        a_profilePic_lbl = new javax.swing.JLabel();
        a_s_name_lbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        a_qualification_textarea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        a_reg_lbl = new javax.swing.JLabel();
        a_gender_lbl = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        a_ec_up_btn = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        a_ecn_lbl = new javax.swing.JLabel();
        a_ecnu_lbl = new javax.swing.JLabel();
        a_ecn_lb = new javax.swing.JLabel();
        a_ecnu_lb = new javax.swing.JLabel();
        a_id_search_textfield = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        pddPanel = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        a_address_lbl = new javax.swing.JLabel();
        a_email_lbl = new javax.swing.JLabel();
        a_department_lbl = new javax.swing.JLabel();
        a_address_up_btn = new javax.swing.JButton();
        a_email_up_btn = new javax.swing.JButton();
        a_department_up_btn = new javax.swing.JButton();
        a_name_lbl = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        a_mobile_lbl = new javax.swing.JLabel();
        a_editMobile_btn = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        a_removeAdministrator_btn = new javax.swing.JButton();
        a_id_lbl = new javax.swing.JLabel();
        a_nic_lbl = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        a_table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        a_reset_btn = new javax.swing.JButton();
        a_pt_btn = new javax.swing.JButton();
        teacher = new javax.swing.JPanel();
        t_loadAll_btn = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        t_table = new javax.swing.JTable();
        t_search_btn = new javax.swing.JButton();
        t_idin_textfield = new javax.swing.JTextField();
        t_add_panel = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        t_address_lbl = new javax.swing.JLabel();
        t_email_lbl = new javax.swing.JLabel();
        t_subject_lbl = new javax.swing.JLabel();
        t_add_up_btn = new javax.swing.JButton();
        t_email_up_btn = new javax.swing.JButton();
        t_sub_up_btn = new javax.swing.JButton();
        t_s_name_lbl = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        t_mobile_lbl = new javax.swing.JLabel();
        t_mobile_up_btn = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        t_remove_btn = new javax.swing.JButton();
        t_id_lbl = new javax.swing.JLabel();
        t_nic_lbl = new javax.swing.JLabel();
        l_subjectAssignReq_btn = new javax.swing.JButton();
        t_pp_panel = new javax.swing.JPanel();
        t_profilePic_lbl = new javax.swing.JLabel();
        t_name_lbl = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        t_qualification_textarea = new javax.swing.JTextArea();
        t_reg_lbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        t_gender_lbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        t_ecn_lbl = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        t_ecnu_lbl = new javax.swing.JLabel();
        t_ecn_lb = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        t_ecnu_lb = new javax.swing.JLabel();
        t_ec_up_btn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        t_reset_btn = new javax.swing.JButton();
        t_pt_btn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        s_table = new javax.swing.JTable();
        s_id_textfield = new javax.swing.JTextField();
        s_adp_panel = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        s_address_lbl = new javax.swing.JLabel();
        s_email_lbl = new javax.swing.JLabel();
        s_subject_lbl = new javax.swing.JLabel();
        s_add_btn = new javax.swing.JButton();
        s_email_btn = new javax.swing.JButton();
        s_sub_btn = new javax.swing.JButton();
        s_name_lbl = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        s_mobile_lbl = new javax.swing.JLabel();
        s_mobile_btn = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        s_id_lbl = new javax.swing.JLabel();
        s_nic_lbl = new javax.swing.JLabel();
        s_pp_panel = new javax.swing.JPanel();
        s_profilePic_lbl = new javax.swing.JLabel();
        s_s_name_lbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        s_reg_lbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        s_gender_lbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        s_ecn_lbl = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        s_ecnu_lbl = new javax.swing.JLabel();
        s_ecu_btn = new javax.swing.JButton();
        s_ecnu_lb2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        s_ecn_lb2 = new javax.swing.JLabel();
        s_search_btn = new javax.swing.JButton();
        s_loadAll_btn = new javax.swing.JButton();
        s_printTable = new javax.swing.JButton();
        s_reset_btn = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jButton51 = new javax.swing.JButton();
        sub_search_btn = new javax.swing.JButton();
        sub_name_textfield = new javax.swing.JTextField();
        sub_reset_btn = new javax.swing.JButton();
        jLabel96 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        subTable = new javax.swing.JTable();
        sub_addi_det_panel = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        sub_class_lbl = new javax.swing.JLabel();
        sub_cl_up_btn = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jLabel109 = new javax.swing.JLabel();
        subname_lbl = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        sub_lec_lbl = new javax.swing.JLabel();
        sub_lec_up_btn = new javax.swing.JButton();
        jLabel113 = new javax.swing.JLabel();
        sub_lec_con_lbl = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        sub_lec_em_lbl = new javax.swing.JLabel();
        submit_class_btn = new javax.swing.JButton();
        sub_remove_sub_btn = new javax.swing.JButton();
        subid_lbl = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        sub_lec_up_btn4 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        sub_addnew_btn = new javax.swing.JButton();
        sub_loadall_btn = new javax.swing.JButton();
        sub_table_print_btn = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        cl_name_textfield = new javax.swing.JTextField();
        cl_search_btn = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        cl_table = new javax.swing.JTable();
        cl_loadall_btn = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        addNewClassBtn = new javax.swing.JButton();
        cl_reset_btn = new javax.swing.JButton();
        cl_pr_tb_btn = new javax.swing.JButton();
        cl_addi_det_panel = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        cl_sub_lbl = new javax.swing.JLabel();
        sub_cl_up_btn1 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jLabel114 = new javax.swing.JLabel();
        cl_cl_name_lbl = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        cl_lec_lbl = new javax.swing.JLabel();
        sub_lec_up_btn1 = new javax.swing.JButton();
        jLabel118 = new javax.swing.JLabel();
        cl_le_con_lbl = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        cl_lec_em_lbl = new javax.swing.JLabel();
        submit_class_btn1 = new javax.swing.JButton();
        cl_remove_class_btn = new javax.swing.JButton();
        cl_clid_lbl = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        sub_lec_up_btn2 = new javax.swing.JButton();
        sub_lec_up_btn3 = new javax.swing.JButton();
        startAttextfield = new javax.swing.JTextField();
        endAttextfield = new javax.swing.JTextField();
        startatupdatebtn = new javax.swing.JButton();
        endatupdatebtn = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        startAt.set24hourMode(false);
        startAt.setDisplayText(startAttextfield);

        endAt.setDisplayText(endAttextfield);

        setPreferredSize(new java.awt.Dimension(808, 463));
        setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        dipartmentAssignRequired_btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        dipartmentAssignRequired_btn.setForeground(new java.awt.Color(255, 0, 0));
        dipartmentAssignRequired_btn.setText("Department Assign Required");
        dipartmentAssignRequired_btn.setContentAreaFilled(false);
        dipartmentAssignRequired_btn.setOpaque(true);
        dipartmentAssignRequired_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dipartmentAssignRequired_btnActionPerformed(evt);
            }
        });

        a_loadAll_btn.setText("Load All");
        a_loadAll_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_loadAll_btnActionPerformed(evt);
            }
        });

        a_search_id_btn.setText("Search");
        a_search_id_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_search_id_btnActionPerformed(evt);
            }
        });

        pidpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pidpanel.add(a_profilePic_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 160, 150));

        a_s_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        a_s_name_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a_s_name_lbl.setText("  ");
        pidpanel.add(a_s_name_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 150, -1));

        jScrollPane3.setEnabled(false);

        a_qualification_textarea.setColumns(20);
        a_qualification_textarea.setLineWrap(true);
        a_qualification_textarea.setRows(5);
        a_qualification_textarea.setEnabled(false);
        jScrollPane3.setViewportView(a_qualification_textarea);

        pidpanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 170, -1, -1));

        jLabel2.setText("Registered At :");
        pidpanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 80, -1));

        jLabel3.setText("Gender :");
        pidpanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        a_reg_lbl.setText("  ");
        pidpanel.add(a_reg_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 120, -1));

        a_gender_lbl.setText("   ");
        pidpanel.add(a_gender_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 100, -1));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Emergency Contact :");
        pidpanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        a_ec_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        a_ec_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_ec_up_btnjButton13ActionPerformed(evt);
            }
        });
        pidpanel.add(a_ec_up_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 30, 30));

        jLabel20.setText("Name :");
        pidpanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        jLabel21.setText("Mobile :");
        pidpanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        jLabel22.setText("Name :");
        pidpanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        jLabel23.setText("Mobile :");
        pidpanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        a_ecn_lbl.setText("   ");
        pidpanel.add(a_ecn_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 130, -1));

        a_ecnu_lbl.setText("   ");
        pidpanel.add(a_ecnu_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 130, -1));

        a_ecn_lb.setText("   ");
        pidpanel.add(a_ecn_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 130, -1));

        a_ecnu_lb.setText("  ");
        pidpanel.add(a_ecnu_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 130, -1));

        a_id_search_textfield.setText("  ");

        jButton2.setText("Print Administrator List");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel50.setText("Additional Details :");

        jLabel51.setText("Address :");

        jLabel52.setText("Email :");

        jLabel53.setText("Department :");

        a_address_lbl.setText("  ");

        a_email_lbl.setText("  ");

        a_department_lbl.setText("  ");

        a_address_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        a_address_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_address_up_btnjButton11ActionPerformed(evt);
            }
        });

        a_email_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        a_email_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_email_up_btnjButton12ActionPerformed(evt);
            }
        });

        a_department_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        a_department_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_department_up_btnjButton13ActionPerformed(evt);
            }
        });

        a_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        a_name_lbl.setText("Pasindu Bhathiya :");

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel58.setText("ID :");

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel59.setText("Mobile :");

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel60.setText("NIC :");

        a_mobile_lbl.setText("  ");

        a_editMobile_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        a_editMobile_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_editMobile_btnjButton6ActionPerformed(evt);
            }
        });

        jButton32.setText("Print");

        a_removeAdministrator_btn.setText("Remove Administrator");
        a_removeAdministrator_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_removeAdministrator_btnActionPerformed(evt);
            }
        });

        a_id_lbl.setText("  ");

        a_nic_lbl.setText("  ");

        javax.swing.GroupLayout pddPanelLayout = new javax.swing.GroupLayout(pddPanel);
        pddPanel.setLayout(pddPanelLayout);
        pddPanelLayout.setHorizontalGroup(
            pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pddPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pddPanelLayout.createSequentialGroup()
                        .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel60)
                            .addComponent(jLabel58)
                            .addComponent(a_name_lbl)
                            .addComponent(jLabel53)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51)
                            .addComponent(jLabel50)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pddPanelLayout.createSequentialGroup()
                                .addComponent(a_department_up_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(a_department_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pddPanelLayout.createSequentialGroup()
                                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(a_email_up_btn)
                                    .addComponent(a_address_up_btn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(a_email_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(a_address_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pddPanelLayout.createSequentialGroup()
                                    .addComponent(a_editMobile_btn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(a_mobile_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(a_id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a_nic_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pddPanelLayout.createSequentialGroup()
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a_removeAdministrator_btn)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pddPanelLayout.setVerticalGroup(
            pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pddPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(a_name_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(a_id_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(a_editMobile_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a_mobile_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(a_nic_lbl))
                .addGap(18, 18, 18)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(a_department_up_btn)
                    .addGroup(pddPanelLayout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pddPanelLayout.createSequentialGroup()
                                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                    .addComponent(jLabel51)
                                    .addComponent(a_address_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(a_address_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52)
                                    .addComponent(a_email_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(a_email_up_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel53)
                            .addComponent(a_department_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(pddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton32)
                    .addComponent(a_removeAdministrator_btn))
                .addContainerGap())
        );

        a_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Mobile", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        a_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(a_table);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Administrator ID");

        a_reset_btn.setText("Reset");
        a_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_reset_btnActionPerformed(evt);
            }
        });

        a_pt_btn.setText("Print Table");

        javax.swing.GroupLayout administratorLayout = new javax.swing.GroupLayout(administrator);
        administrator.setLayout(administratorLayout);
        administratorLayout.setHorizontalGroup(
            administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administratorLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorLayout.createSequentialGroup()
                        .addGroup(administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a_pt_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pidpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(a_id_search_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a_search_id_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a_reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a_loadAll_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addGap(21, 21, 21))
            .addGroup(administratorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dipartmentAssignRequired_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        administratorLayout.setVerticalGroup(
            administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administratorLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(dipartmentAssignRequired_btn)
                .addGap(18, 18, 18)
                .addGroup(administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(a_id_search_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a_search_id_btn)
                    .addComponent(jButton2)
                    .addComponent(a_loadAll_btn)
                    .addComponent(a_reset_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(administratorLayout.createSequentialGroup()
                        .addComponent(a_pt_btn)
                        .addGap(18, 18, 18)
                        .addComponent(pddPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pidpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Administrator", administrator);

        t_loadAll_btn.setText("Load All");
        t_loadAll_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_loadAll_btnActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel64.setText("Lecturer ID");

        jButton24.setText("Print Lecturer List");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        t_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Mobile", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        t_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_tableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(t_table);

        t_search_btn.setText("Search");
        t_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_search_btnActionPerformed(evt);
            }
        });

        t_idin_textfield.setText("  ");

        jLabel65.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel65.setText("Additional Details :");

        jLabel66.setText("Address :");

        jLabel67.setText("Email :");

        jLabel68.setText("Subject");

        t_address_lbl.setText("   ");

        t_email_lbl.setText("  ");

        t_subject_lbl.setText("   ");

        t_add_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        t_add_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_add_up_btnjButton11ActionPerformed(evt);
            }
        });

        t_email_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        t_email_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_email_up_btnjButton12ActionPerformed(evt);
            }
        });

        t_sub_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        t_sub_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_sub_up_btnjButton13ActionPerformed(evt);
            }
        });

        t_s_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_s_name_lbl.setText("Pasindu Bhathiya :");

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel73.setText("ID :");

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel74.setText("Mobile :");

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel75.setText("NIC :");

        t_mobile_lbl.setText("   ");

        t_mobile_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        t_mobile_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_mobile_up_btnjButton6ActionPerformed(evt);
            }
        });

        jButton31.setText("Print");

        t_remove_btn.setText("Remove Lecturer");
        t_remove_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_remove_btnActionPerformed(evt);
            }
        });

        t_id_lbl.setText("    ");

        t_nic_lbl.setText("  ");

        javax.swing.GroupLayout t_add_panelLayout = new javax.swing.GroupLayout(t_add_panel);
        t_add_panel.setLayout(t_add_panelLayout);
        t_add_panelLayout.setHorizontalGroup(
            t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_add_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_add_panelLayout.createSequentialGroup()
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_remove_btn))
                    .addGroup(t_add_panelLayout.createSequentialGroup()
                        .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel75)
                            .addComponent(jLabel73)
                            .addComponent(t_s_name_lbl)
                            .addComponent(jLabel68)
                            .addComponent(jLabel67)
                            .addComponent(jLabel66)
                            .addComponent(jLabel65)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(t_add_panelLayout.createSequentialGroup()
                                .addComponent(t_sub_up_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_subject_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(t_add_panelLayout.createSequentialGroup()
                                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t_email_up_btn)
                                    .addComponent(t_add_up_btn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_email_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(t_address_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, t_add_panelLayout.createSequentialGroup()
                                    .addComponent(t_mobile_up_btn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(t_mobile_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(t_id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_nic_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        t_add_panelLayout.setVerticalGroup(
            t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_add_panelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(t_s_name_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t_id_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_add_panelLayout.createSequentialGroup()
                        .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(t_mobile_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(t_mobile_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t_nic_lbl))
                .addGap(18, 18, 18)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(t_add_panelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(t_sub_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(t_add_panelLayout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(t_add_panelLayout.createSequentialGroup()
                                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(t_add_panelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(t_add_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(t_address_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t_email_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(t_add_panelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(t_email_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(t_add_panelLayout.createSequentialGroup()
                                .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addComponent(t_subject_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(43, 43, 43)
                .addGroup(t_add_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton31)
                    .addComponent(t_remove_btn)))
        );

        l_subjectAssignReq_btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        l_subjectAssignReq_btn.setForeground(new java.awt.Color(255, 0, 0));
        l_subjectAssignReq_btn.setText("Subject Assign Required");
        l_subjectAssignReq_btn.setContentAreaFilled(false);
        l_subjectAssignReq_btn.setOpaque(true);
        l_subjectAssignReq_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                l_subjectAssignReq_btnActionPerformed(evt);
            }
        });

        t_pp_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        t_pp_panel.add(t_profilePic_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 150, 150));

        t_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        t_name_lbl.setText("   ");
        t_pp_panel.add(t_name_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 140, -1));

        t_qualification_textarea.setColumns(20);
        t_qualification_textarea.setRows(5);
        t_qualification_textarea.setEnabled(false);
        jScrollPane6.setViewportView(t_qualification_textarea);

        t_pp_panel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 220, -1));

        t_reg_lbl.setText("   ");
        t_pp_panel.add(t_reg_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 100, -1));

        jLabel5.setText("Registered At :");
        t_pp_panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel6.setText("Gender :");
        t_pp_panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        t_gender_lbl.setText("   ");
        t_pp_panel.add(t_gender_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 100, -1));

        jLabel14.setText("Name :");
        t_pp_panel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        t_ecn_lbl.setText("   ");
        t_pp_panel.add(t_ecn_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 130, -1));

        jLabel15.setText("Mobile :");
        t_pp_panel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        t_ecnu_lbl.setText("   ");
        t_pp_panel.add(t_ecnu_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, 130, -1));

        t_ecn_lb.setText("   ");
        t_pp_panel.add(t_ecn_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 130, -1));

        jLabel16.setText("Name :");
        t_pp_panel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        jLabel17.setText("Mobile :");
        t_pp_panel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, -1, -1));

        t_ecnu_lb.setText("  ");
        t_pp_panel.add(t_ecnu_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 130, -1));

        t_ec_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        t_ec_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_ec_up_btnjButton13ActionPerformed(evt);
            }
        });
        t_pp_panel.add(t_ec_up_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 30, 30));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Emergency Contact :");
        t_pp_panel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        t_reset_btn.setText("Reset");
        t_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_reset_btnActionPerformed(evt);
            }
        });

        t_pt_btn.setText("Print Table");

        javax.swing.GroupLayout teacherLayout = new javax.swing.GroupLayout(teacher);
        teacher.setLayout(teacherLayout);
        teacherLayout.setHorizontalGroup(
            teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teacherLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(teacherLayout.createSequentialGroup()
                        .addGroup(teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(t_add_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_pt_btn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(t_pp_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teacherLayout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(t_idin_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_search_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(t_loadAll_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24)))
                .addGap(41, 41, 41))
            .addGroup(teacherLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(l_subjectAssignReq_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        teacherLayout.setVerticalGroup(
            teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teacherLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(l_subjectAssignReq_btn)
                .addGap(24, 24, 24)
                .addGroup(teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(t_idin_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_search_btn)
                    .addComponent(jButton24)
                    .addComponent(t_loadAll_btn)
                    .addComponent(t_reset_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(teacherLayout.createSequentialGroup()
                        .addComponent(t_pt_btn)
                        .addGap(27, 27, 27)
                        .addComponent(t_add_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(t_pp_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Lecturer", teacher);

        jLabel79.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel79.setText("Student ID :");

        jButton38.setText("Print  Student List");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        s_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Mobile", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        s_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_tableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(s_table);

        s_id_textfield.setText("  ");

        jLabel80.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel80.setText("Additional Details :");

        jLabel81.setText("Address :");

        jLabel82.setText("Email :");

        jLabel83.setText("Subject");

        s_address_lbl.setText("  ");

        s_email_lbl.setText("  ");

        s_subject_lbl.setText("  ");

        s_add_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        s_add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_add_btnjButton11ActionPerformed(evt);
            }
        });

        s_email_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        s_email_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_email_btnjButton12ActionPerformed(evt);
            }
        });

        s_sub_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        s_sub_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_sub_btnjButton13ActionPerformed(evt);
            }
        });

        s_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        s_name_lbl.setText("Pasindu Bhathiya :");

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel88.setText("ID :");

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel89.setText("Mobile :");

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel90.setText("NIC :");

        s_mobile_lbl.setText("  ");

        s_mobile_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        s_mobile_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_mobile_btnjButton6ActionPerformed(evt);
            }
        });

        jButton44.setText("Print");

        jButton45.setText("Remove Student");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        s_id_lbl.setText(" ");

        s_nic_lbl.setText("  ");

        javax.swing.GroupLayout s_adp_panelLayout = new javax.swing.GroupLayout(s_adp_panel);
        s_adp_panel.setLayout(s_adp_panelLayout);
        s_adp_panelLayout.setHorizontalGroup(
            s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(s_adp_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                        .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton45))
                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                        .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel90)
                            .addComponent(jLabel88)
                            .addComponent(s_name_lbl)
                            .addComponent(jLabel83)
                            .addComponent(jLabel82)
                            .addComponent(jLabel81)
                            .addComponent(jLabel80)
                            .addComponent(jLabel89))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(s_adp_panelLayout.createSequentialGroup()
                                .addComponent(s_sub_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_subject_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(s_adp_panelLayout.createSequentialGroup()
                                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(s_email_btn)
                                    .addComponent(s_add_btn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_email_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(s_address_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, s_adp_panelLayout.createSequentialGroup()
                                    .addComponent(s_mobile_btn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(s_mobile_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(s_id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(s_nic_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        s_adp_panelLayout.setVerticalGroup(
            s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, s_adp_panelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(s_name_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s_id_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                        .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(s_mobile_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(s_mobile_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s_nic_lbl))
                .addGap(18, 18, 18)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(s_sub_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                        .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(s_adp_panelLayout.createSequentialGroup()
                                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(s_add_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(s_address_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(s_adp_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(6, 6, 6))
                                    .addComponent(s_email_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(s_adp_panelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(s_email_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(s_adp_panelLayout.createSequentialGroup()
                                .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addComponent(s_subject_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(43, 43, 43)
                .addGroup(s_adp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton44)
                    .addComponent(jButton45)))
        );

        s_pp_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        s_pp_panel.add(s_profilePic_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 160, 140));

        s_s_name_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        s_s_name_lbl.setText("  ");
        s_pp_panel.add(s_s_name_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 160, -1));

        jLabel4.setText("jLabel4");
        s_pp_panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-38, -16, -1, -1));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Emergency Contact :");
        s_pp_panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jLabel8.setText("Registered At :");
        s_pp_panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        s_reg_lbl.setText("  ");
        s_pp_panel.add(s_reg_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 130, -1));

        jLabel10.setText("Gender :");
        s_pp_panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        s_gender_lbl.setText("  ");
        s_pp_panel.add(s_gender_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 120, -1));

        jLabel9.setText("Name :");
        s_pp_panel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        s_ecn_lbl.setText(" ");
        s_pp_panel.add(s_ecn_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 130, -1));

        jLabel12.setText("Mobile :");
        s_pp_panel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        s_ecnu_lbl.setText(" ");
        s_pp_panel.add(s_ecnu_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 130, -1));

        s_ecu_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        s_ecu_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_ecu_btnjButton13ActionPerformed(evt);
            }
        });
        s_pp_panel.add(s_ecu_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 262, 30, 30));

        s_ecnu_lb2.setText(" ");
        s_pp_panel.add(s_ecnu_lb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 130, -1));

        jLabel13.setText("Mobile :");
        s_pp_panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        jLabel11.setText("Name :");
        s_pp_panel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, -1, -1));

        s_ecn_lb2.setText(" ");
        s_pp_panel.add(s_ecn_lb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 130, -1));

        s_search_btn.setText("Search");
        s_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_search_btnActionPerformed(evt);
            }
        });

        s_loadAll_btn.setText("Load All");
        s_loadAll_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_loadAll_btnActionPerformed(evt);
            }
        });

        s_printTable.setText("Print Table");

        s_reset_btn.setText("Reset");
        s_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_reset_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(s_printTable)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(s_adp_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_pp_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel79)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(s_search_btn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(s_reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(s_loadAll_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton38)))
                        .addGap(14, 14, 14))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s_search_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton38)
                    .addComponent(s_loadAll_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s_reset_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_printTable)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s_adp_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s_pp_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jTabbedPane2.addTab("Student", jPanel2);

        jButton51.setText("Print Subject List");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        sub_search_btn.setText("Search");
        sub_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_search_btnActionPerformed(evt);
            }
        });

        sub_name_textfield.setText("  ");
        sub_name_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sub_name_textfieldKeyReleased(evt);
            }
        });

        sub_reset_btn.setText("Reset");
        sub_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_reset_btnActionPerformed(evt);
            }
        });

        jLabel96.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel96.setText("Subject Name");

        subTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Subject Name", "Assigned Lecturer", "Class Room No"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subTableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(subTable);
        if (subTable.getColumnModel().getColumnCount() > 0) {
            subTable.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel105.setText("Subject ID :");

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel106.setText("Class :");

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel107.setText("Lecturer :");

        sub_class_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        sub_cl_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_cl_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_cl_up_btnjButton6ActionPerformed(evt);
            }
        });

        jButton56.setText("Print");

        jButton57.setText("Remove Student");

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel109.setText("Subject Name :");

        subname_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        jLabel111.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel111.setText("Additional Details :");

        sub_lec_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        sub_lec_up_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_lec_up_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_lec_up_btnjButton6ActionPerformed(evt);
            }
        });

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel113.setText("Lecturer Contact :");

        sub_lec_con_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel115.setText("Lecturer Email :");

        sub_lec_em_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        submit_class_btn.setText("Print");

        sub_remove_sub_btn.setText("Remove Subject");
        sub_remove_sub_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_remove_sub_btnActionPerformed(evt);
            }
        });

        subid_lbl.setText("jLabel24");

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel122.setText("Price :");

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel123.setText("Description :");

        sub_lec_up_btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_lec_up_btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_lec_up_btn4jButton6ActionPerformed(evt);
            }
        });

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setEnabled(false);
        jScrollPane8.setViewportView(jTextArea2);

        jSpinner1.setEnabled(false);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sub_addi_det_panelLayout = new javax.swing.GroupLayout(sub_addi_det_panel);
        sub_addi_det_panel.setLayout(sub_addi_det_panelLayout);
        sub_addi_det_panelLayout.setHorizontalGroup(
            sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(sub_lec_con_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                    .addComponent(jLabel111)
                                    .addGap(357, 357, 357))
                                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(40, 40, 40)
                                        .addComponent(sub_lec_em_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel107)
                                            .addComponent(jLabel105)
                                            .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel109, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.TRAILING)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(subname_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sub_addi_det_panelLayout.createSequentialGroup()
                                                .addComponent(sub_lec_up_btn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sub_lec_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                                .addComponent(sub_cl_up_btn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(sub_class_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(subid_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sub_lec_up_btn4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addGap(101, 101, 101))))
                            .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sub_addi_det_panelLayout.createSequentialGroup()
                                    .addComponent(submit_class_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(sub_remove_sub_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sub_addi_det_panelLayout.createSequentialGroup()
                                    .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton57))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                        .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))))
        );
        sub_addi_det_panelLayout.setVerticalGroup(
            sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sub_addi_det_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subname_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subid_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sub_cl_up_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sub_class_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sub_lec_up_btn)
                        .addComponent(sub_lec_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sub_lec_con_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sub_lec_em_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sub_addi_det_panelLayout.createSequentialGroup()
                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sub_lec_up_btn4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(submit_class_btn)
                            .addComponent(sub_remove_sub_btn))
                        .addGap(188, 188, 188)
                        .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton56)
                            .addComponent(jButton57)))
                    .addGroup(sub_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))))
        );

        sub_addnew_btn.setBackground(new java.awt.Color(0, 204, 51));
        sub_addnew_btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        sub_addnew_btn.setForeground(new java.awt.Color(255, 255, 255));
        sub_addnew_btn.setText("Add New Subject");
        sub_addnew_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_addnew_btnActionPerformed(evt);
            }
        });

        sub_loadall_btn.setText("Load All");
        sub_loadall_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_loadall_btnActionPerformed(evt);
            }
        });

        sub_table_print_btn.setText("Print Table");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(sub_loadall_btn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton51))
                                    .addComponent(sub_addnew_btn)))
                            .addComponent(sub_addi_det_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(sub_table_print_btn)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel96)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sub_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sub_search_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sub_reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(sub_addnew_btn)
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(sub_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sub_search_btn)
                    .addComponent(sub_reset_btn)
                    .addComponent(jButton51)
                    .addComponent(sub_loadall_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sub_table_print_btn)
                .addGap(44, 44, 44)
                .addComponent(sub_addi_det_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Subject", jPanel14);

        jLabel117.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel117.setText("Class Name");

        cl_name_textfield.setText("  ");
        cl_name_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cl_name_textfieldKeyReleased(evt);
            }
        });

        cl_search_btn.setText("Search");
        cl_search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_search_btnActionPerformed(evt);
            }
        });

        cl_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Name", "Subject", "Lecturer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cl_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cl_tableKeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(cl_table);
        if (cl_table.getColumnModel().getColumnCount() > 0) {
            cl_table.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        cl_loadall_btn.setText("Load All");
        cl_loadall_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_loadall_btnActionPerformed(evt);
            }
        });

        jButton62.setText("Print Class List");

        addNewClassBtn.setBackground(new java.awt.Color(0, 204, 51));
        addNewClassBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        addNewClassBtn.setForeground(new java.awt.Color(255, 255, 255));
        addNewClassBtn.setText("Add New Class");
        addNewClassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewClassBtnActionPerformed(evt);
            }
        });

        cl_reset_btn.setText("Reset");
        cl_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_reset_btnActionPerformed(evt);
            }
        });

        cl_pr_tb_btn.setText("Print Table");

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel108.setText("Class ID :");

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel110.setText("Subject :");

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel112.setText("Lecturer :");

        cl_sub_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        sub_cl_up_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_cl_up_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_cl_up_btn1jButton6ActionPerformed(evt);
            }
        });

        jButton58.setText("Print");

        jButton59.setText("Remove Student");

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel114.setText("Class Name :");

        cl_cl_name_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        jLabel116.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel116.setText("Additional Details :");

        cl_lec_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        sub_lec_up_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_lec_up_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_lec_up_btn1jButton6ActionPerformed(evt);
            }
        });

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel118.setText("Lecturer Contact :");

        cl_le_con_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel119.setText("Lecturer Email :");

        cl_lec_em_lbl.setText("dcdwcwdcwdcwdcwdcwdcwdcw");

        submit_class_btn1.setText("Print");

        cl_remove_class_btn.setText("Remove Class");
        cl_remove_class_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_remove_class_btnActionPerformed(evt);
            }
        });

        cl_clid_lbl.setText("jLabel24");

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel120.setText("Start At :");

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel121.setText("End At :");

        sub_lec_up_btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_lec_up_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_lec_up_btn2jButton6ActionPerformed(evt);
            }
        });

        sub_lec_up_btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/office-material.png"))); // NOI18N
        sub_lec_up_btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sub_lec_up_btn3jButton6ActionPerformed(evt);
            }
        });

        startAttextfield.setEditable(false);
        startAttextfield.setEnabled(false);

        endAttextfield.setEditable(false);
        endAttextfield.setEnabled(false);

        startatupdatebtn.setText("update");
        startatupdatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startatupdatebtnActionPerformed(evt);
            }
        });

        endatupdatebtn.setText("update");
        endatupdatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endatupdatebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cl_addi_det_panelLayout = new javax.swing.GroupLayout(cl_addi_det_panel);
        cl_addi_det_panel.setLayout(cl_addi_det_panelLayout);
        cl_addi_det_panelLayout.setHorizontalGroup(
            cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel116)
                    .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                        .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton59)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(cl_le_con_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                        .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                                    .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(40, 40, 40)
                                    .addComponent(cl_lec_em_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel112)
                                        .addComponent(jLabel108)
                                        .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel114, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cl_cl_name_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cl_addi_det_panelLayout.createSequentialGroup()
                                            .addComponent(sub_lec_up_btn1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cl_lec_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                                            .addComponent(sub_cl_up_btn1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cl_sub_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cl_clid_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cl_addi_det_panelLayout.createSequentialGroup()
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(12, 12, 12)
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sub_lec_up_btn2)
                                        .addComponent(sub_lec_up_btn3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(startAttextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(endAttextfield))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(startatupdatebtn)
                                        .addComponent(endatupdatebtn))
                                    .addGap(108, 108, 108)))
                            .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                                .addComponent(submit_class_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cl_remove_class_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(74, 303, Short.MAX_VALUE))))
        );
        cl_addi_det_panelLayout.setVerticalGroup(
            cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cl_addi_det_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cl_cl_name_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cl_clid_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sub_cl_up_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cl_sub_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sub_lec_up_btn1)
                        .addComponent(cl_lec_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cl_le_con_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cl_lec_em_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                        .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sub_lec_up_btn2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cl_addi_det_panelLayout.createSequentialGroup()
                                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(endAttextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(endatupdatebtn)))
                                .addGap(88, 88, 88)
                                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(submit_class_btn1)
                                    .addComponent(cl_remove_class_btn))
                                .addGap(149, 149, 149)
                                .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton58)
                                    .addComponent(jButton59)))
                            .addComponent(sub_lec_up_btn3)))
                    .addGroup(cl_addi_det_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startAttextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startatupdatebtn))))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(cl_pr_tb_btn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addNewClassBtn))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel117)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cl_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cl_search_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cl_reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cl_loadall_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cl_addi_det_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(addNewClassBtn)
                .addGap(23, 23, 23)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cl_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cl_search_btn)
                    .addComponent(cl_reset_btn)
                    .addComponent(cl_loadall_btn)
                    .addComponent(jButton62)
                    .addComponent(jLabel117))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cl_pr_tb_btn)
                .addGap(34, 34, 34)
                .addComponent(cl_addi_det_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Class", jPanel16);

        jScrollPane5.setViewportView(jTabbedPane2);

        add(jScrollPane5);
    }// </editor-fold>//GEN-END:initComponents

    private void dipartmentAssignRequired_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dipartmentAssignRequired_btnActionPerformed
        
        String backupQuery = administratorFullQuery;
        administratorFullQuery += "WHERE `administrator_department`.`administrator_department_id`='1' ORDER BY `administrator_id` ASC";
        addToAdministratorTable(administratorFullQuery);
        administratorFullQuery = backupQuery;
        
        isAdministratorDepartmentClicked = true;

    }//GEN-LAST:event_dipartmentAssignRequired_btnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void a_address_up_btnjButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_address_up_btnjButton11ActionPerformed
        
        new gui.UpdateAddressDialog(null, true, tableSelectedUserId, tableSelectedUserAddressLine1, tableSelectedUserAddressLine2, table, this).setVisible(true);

    }//GEN-LAST:event_a_address_up_btnjButton11ActionPerformed

    private void a_email_up_btnjButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_email_up_btnjButton12ActionPerformed
        
        new gui.UpdateEmailDialog(null, true, tableSelectedUserId, tableSelectedUserEmail, table, this).setVisible(true);

    }//GEN-LAST:event_a_email_up_btnjButton12ActionPerformed

    private void a_department_up_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_department_up_btnjButton13ActionPerformed
        
        new gui.UpdateDepartmentDialog(null, true, tableSelectedUserId, tableSelectedUserDepartment, this).setVisible(true);

    }//GEN-LAST:event_a_department_up_btnjButton13ActionPerformed

    private void a_editMobile_btnjButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_editMobile_btnjButton6ActionPerformed
        
        new gui.UpdateMobileDialog(null, true, tableSelectedUserId, tableSelectedUserMobile, table, this).setVisible(true);

    }//GEN-LAST:event_a_editMobile_btnjButton6ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton24ActionPerformed

    private void t_add_up_btnjButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_add_up_btnjButton11ActionPerformed
        
        new gui.UpdateAddressDialog(null, true, tableSelectedUserId, tableSelectedUserAddressLine1, tableSelectedUserAddressLine2, table, this).setVisible(true);

    }//GEN-LAST:event_t_add_up_btnjButton11ActionPerformed

    private void t_email_up_btnjButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_email_up_btnjButton12ActionPerformed
        
        new gui.UpdateEmailDialog(null, true, tableSelectedUserId, tableSelectedUserEmail, table, this).setVisible(true);

    }//GEN-LAST:event_t_email_up_btnjButton12ActionPerformed

    private void t_sub_up_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_sub_up_btnjButton13ActionPerformed
        
        new gui.UpdateSubjectDialog(null, true, tableSelectedUserId, tableSelectedUserDepartment, table, this).setVisible(true);

    }//GEN-LAST:event_t_sub_up_btnjButton13ActionPerformed

    private void t_mobile_up_btnjButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_mobile_up_btnjButton6ActionPerformed
        
        new gui.UpdateMobileDialog(null, true, tableSelectedUserId, tableSelectedUserMobile, table, this).setVisible(true);

    }//GEN-LAST:event_t_mobile_up_btnjButton6ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton38ActionPerformed

    private void s_add_btnjButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_add_btnjButton11ActionPerformed
        
        new gui.UpdateAddressDialog(null, true, tableSelectedUserId, tableSelectedUserAddressLine1, tableSelectedUserAddressLine2, table, this).setVisible(true);

    }//GEN-LAST:event_s_add_btnjButton11ActionPerformed

    private void s_email_btnjButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_email_btnjButton12ActionPerformed
        
        new gui.UpdateEmailDialog(null, true, tableSelectedUserId, tableSelectedUserEmail, table, this).setVisible(true);

    }//GEN-LAST:event_s_email_btnjButton12ActionPerformed

    private void s_sub_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_sub_btnjButton13ActionPerformed
        
        new gui.UpdateSubjectDialog(null, true, tableSelectedUserId, tableSelectedUserDepartment, table, this).setVisible(true);

    }//GEN-LAST:event_s_sub_btnjButton13ActionPerformed

    private void s_mobile_btnjButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_mobile_btnjButton6ActionPerformed
        
        new gui.UpdateMobileDialog(null, true, tableSelectedUserId, tableSelectedUserMobile, table, this).setVisible(true);

    }//GEN-LAST:event_s_mobile_btnjButton6ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton51ActionPerformed

    private void s_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_search_btnActionPerformed
        
        String id = s_id_textfield.getText();
        if (id.isBlank()) {
            JOptionPane.showMessageDialog(this, "Insert Student ID First", "Need An ID", JOptionPane.WARNING_MESSAGE);
        } else {
            
            String backup = StudentFullQuery;
            StudentFullQuery += " WHERE `student`.`student_id`='" + id + "'";
            addToStudentTable(StudentFullQuery);
            StudentFullQuery = backup;
            
        }
        

    }//GEN-LAST:event_s_search_btnActionPerformed

    private void sub_cl_up_btnjButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_cl_up_btnjButton6ActionPerformed
        
        new gui.UpdateClassDialog(null, true, selectedSubjectId, selectedClassId, this).setVisible(true);

    }//GEN-LAST:event_sub_cl_up_btnjButton6ActionPerformed

    private void sub_lec_up_btnjButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_lec_up_btnjButton6ActionPerformed
        
        new gui.UpdateLecturerDialog(null, true, selectedSubjectId, selectedClassId, this).setVisible(true);

    }//GEN-LAST:event_sub_lec_up_btnjButton6ActionPerformed

    private void a_search_id_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_search_id_btnActionPerformed
        
        String id = a_id_search_textfield.getText();
        if (id.isBlank()) {
            JOptionPane.showMessageDialog(this, "Insert Administrator ID First", "Need An ID", JOptionPane.WARNING_MESSAGE);
        } else {
            
            String backup_administratorFullQuery = administratorFullQuery;
            administratorFullQuery += " WHERE `administrator`.`administrator_id`='" + id + "'";
            addToAdministratorTable(administratorFullQuery);
            administratorFullQuery = backup_administratorFullQuery;
            
        }

    }//GEN-LAST:event_a_search_id_btnActionPerformed

    private void a_loadAll_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_loadAll_btnActionPerformed
        
        String backup = administratorFullQuery;
        administratorFullQuery += "WHERE `administrator`.`status_status_id`='1' ORDER BY `administrator_id` ASC";
        addToAdministratorTable(administratorFullQuery);
        administratorFullQuery = backup;

    }//GEN-LAST:event_a_loadAll_btnActionPerformed

    private void a_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a_tableMouseClicked
        
        DefaultTableModel dtm = (DefaultTableModel) a_table.getModel();
        if (dtm.getRowCount() > 0) {
            
            pddPanel.setVisible(true);
            pidpanel.setVisible(true);
            
            try {
                
                selectedTableRow = a_table.getSelectedRow();
                Administrator administrator = administratorMap.get(String.valueOf(a_table.getValueAt(a_table.getSelectedRow(), 0)));
                
                tableSelectedUserId = String.valueOf(a_table.getValueAt(a_table.getSelectedRow(), 0));
                tableSelectedUserMobile = String.valueOf(administrator.getMobile());
                tableSelectedUserAddressLine1 = String.valueOf(administrator.getLine1());
                tableSelectedUserAddressLine2 = String.valueOf(administrator.getLine2());
                tableSelectedUserEmail = String.valueOf(administrator.getEmail());
                tableSelectedUserDepartment = String.valueOf(administrator.getDepartment());
                tableSelectedUserEmergencyName1 = String.valueOf(administrator.getEcn1());
                tableSelectedUserEmergencyContact1 = String.valueOf(administrator.getEcc1());
                tableSelectedUserEmergencyName2 = String.valueOf(administrator.getEcn2());
                tableSelectedUserEmergencyContact2 = String.valueOf(administrator.getEcc2());
                tableSelectedUserEmergencyContactId1 = String.valueOf(administrator.getEci1());
                tableSelectedUserEmergencyContactId2 = String.valueOf(administrator.getEci2());
                
                ResultSet q_rs = MySQL.executeSearch("SELECT * FROM `administrator_qualification` WHERE `administrator_qualification`.`administrator_administrator_id`='" + String.valueOf(a_table.getValueAt(a_table.getSelectedRow(), 0)) + "'");
                if (q_rs.next()) {
                    a_qualification_textarea.setText(q_rs.getString("qualification"));
                } else {
                    a_qualification_textarea.setText("");
                }
                
                a_ecn_lbl.setText(administrator.getEcn1());
                a_ecnu_lbl.setText(administrator.getEcc1());
                a_ecn_lb.setText(administrator.getEcn2());
                a_ecnu_lb.setText(administrator.getEcc1());
                a_id_lbl.setText(administrator.getId());
                a_mobile_lbl.setText(administrator.getMobile());
                a_nic_lbl.setText(administrator.getNic());
                a_address_lbl.setText(administrator.getLine1() + ", " + administrator.getLine2());
                a_email_lbl.setText(administrator.getEmail());
                a_department_lbl.setText(administrator.getDepartment());
                a_profilePic_lbl.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(administrator.getImageUrl().replace("src", "")))));
                a_name_lbl.setText(administrator.getSurname() + " " + administrator.getFname() + " " + administrator.getLname());
                a_s_name_lbl.setText(administrator.getSurname() + " " + administrator.getFname() + " " + administrator.getLname());
                a_reg_lbl.setText(administrator.getRegisterdDateTime());
                a_gender_lbl.setText(administrator.getGender());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

    }//GEN-LAST:event_a_tableMouseClicked

    private void a_removeAdministrator_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_removeAdministrator_btnActionPerformed
        
        try {
            
            int selectedOption = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Remove This Account ?", "Confirm Account De-activation", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `administrator` SET `administrator`.`status_status_id`='2' "
                        + "WHERE `administrator`.`administrator_id`='" + tableSelectedUserId + "'");
                
                JOptionPane.showMessageDialog(this, "Account De-activation Success", "Acount De-activated", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_a_removeAdministrator_btnActionPerformed

    private void a_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_reset_btnActionPerformed
        
        a_id_search_textfield.setText("");
        setUpAdministratorBg();

    }//GEN-LAST:event_a_reset_btnActionPerformed

    private void l_subjectAssignReq_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_l_subjectAssignReq_btnActionPerformed
        
        String backupQuery = teacherFullQuery;
        teacherFullQuery += "WHERE `subject`.`subject_id`='1' ORDER BY `teacher_id` ASC";
        addToTeacherTable(teacherFullQuery);
        teacherFullQuery = backupQuery;
        
        isTeacherSubjectBtnClicked = true;

    }//GEN-LAST:event_l_subjectAssignReq_btnActionPerformed

    private void t_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_search_btnActionPerformed
        
        String id = t_idin_textfield.getText();
        if (id.isBlank()) {
            JOptionPane.showMessageDialog(this, "Insert Teacher ID First", "Need An ID", JOptionPane.WARNING_MESSAGE);
        } else {
            
            String backup = teacherFullQuery;
            teacherFullQuery += " WHERE `teacher`.`teacher_id`='" + id + "'";
            addToTeacherTable(teacherFullQuery);
            teacherFullQuery = backup;
            
        }

    }//GEN-LAST:event_t_search_btnActionPerformed

    private void t_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_reset_btnActionPerformed
        
        t_idin_textfield.setText("");
        setUpTeacherBg();

    }//GEN-LAST:event_t_reset_btnActionPerformed

    private void t_loadAll_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_loadAll_btnActionPerformed
        
        t_idin_textfield.setText("");
        setUpTeacherBg();
        
        String backup = teacherFullQuery;
        teacherFullQuery += "ORDER BY `teacher_id` ASC";
        addToTeacherTable(teacherFullQuery);
        teacherFullQuery = backup;

    }//GEN-LAST:event_t_loadAll_btnActionPerformed

    private void t_remove_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_remove_btnActionPerformed
        
        try {
            
            int selectedOption = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Remove This Account ?", "Confirm Account De-activation", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `teacher` SET `teacher`.`status_status_id`='2' WHERE `teacher`.`teacher_id`='" + tableSelectedUserId + "'");
                
                DefaultTableModel dtm = (DefaultTableModel) t_table.getModel();
                dtm.removeRow(selectedTableRow);
                int rowCount = dtm.getRowCount();
                t_table.setModel(dtm);
                
                if (rowCount == 0) {
                    t_pt_btn.setVisible(false);
                }
                t_add_panel.setVisible(false);
                t_pp_panel.setVisible(false);
                
                teacherMap.remove(tableSelectedUserId);
                
                JOptionPane.showMessageDialog(this, "Account De-activation Success", "Acount De-activated", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_t_remove_btnActionPerformed

    private void t_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_tableMouseClicked
        
        DefaultTableModel dtm = (DefaultTableModel) t_table.getModel();
        if (dtm.getRowCount() > 0) {
            
            t_add_panel.setVisible(true);
            t_pp_panel.setVisible(true);
            
            try {
                
                selectedTableRow = t_table.getSelectedRow();
                Teacher teacher = teacherMap.get(String.valueOf(t_table.getValueAt(t_table.getSelectedRow(), 0)));
                
                tableSelectedUserId = String.valueOf(t_table.getValueAt(t_table.getSelectedRow(), 0));
                tableSelectedUserMobile = String.valueOf(teacher.getMobile());
                tableSelectedUserAddressLine1 = String.valueOf(teacher.getLine1());
                tableSelectedUserAddressLine2 = String.valueOf(teacher.getLine2());
                tableSelectedUserEmail = String.valueOf(teacher.getEmail());
                tableSelectedUserDepartment = String.valueOf(teacher.getSubject());
                tableSelectedUserEmergencyName1 = String.valueOf(teacher.getEcn1());
                tableSelectedUserEmergencyContact1 = String.valueOf(teacher.getEcn1());
                tableSelectedUserEmergencyName2 = String.valueOf(teacher.getEcn2());
                tableSelectedUserEmergencyContact2 = String.valueOf(teacher.getEcn2());
                tableSelectedUserEmergencyContactId1 = String.valueOf(teacher.getEci1());
                tableSelectedUserEmergencyContactId2 = String.valueOf(teacher.getEci2());
                
                ResultSet q_rs = MySQL.executeSearch("SELECT * FROM `teacher_qualification` WHERE `teacher_qualification`.`teacher_teacher_id`='" + String.valueOf(t_table.getValueAt(t_table.getSelectedRow(), 0)) + "'");
                if (q_rs.next()) {
                    t_qualification_textarea.setText(q_rs.getString("qualification"));
                } else {
                    t_qualification_textarea.setText("");
                }
                
                t_ecn_lbl.setText(teacher.getEcn1());
                t_ecnu_lbl.setText(teacher.getEcn1());
                t_ecn_lb.setText(teacher.getEcn2());
                t_ecnu_lb.setText(teacher.getEcn2());
                t_id_lbl.setText(teacher.getId());
                t_mobile_lbl.setText(teacher.getMobile());
                t_nic_lbl.setText(teacher.getNic());
                t_address_lbl.setText(teacher.getLine1() + ", " + teacher.getLine2());
                t_email_lbl.setText(teacher.getEmail());
                t_subject_lbl.setText(teacher.getSubject());
                t_profilePic_lbl.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(teacher.getImageUrl().replace("src", "")))));
                t_name_lbl.setText(teacher.getSurname() + " " + teacher.getFname() + " " + teacher.getLname());
                t_s_name_lbl.setText(teacher.getSurname() + " " + teacher.getFname() + " " + teacher.getLname());
                t_reg_lbl.setText(teacher.getRegisterdDateTime());
                t_gender_lbl.setText(teacher.getGender());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

    }//GEN-LAST:event_t_tableMouseClicked

    private void s_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_reset_btnActionPerformed
        
        s_id_textfield.setText("");
        setUpStudentBg();

    }//GEN-LAST:event_s_reset_btnActionPerformed

    private void s_loadAll_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_loadAll_btnActionPerformed
        
        String backup = StudentFullQuery;
        StudentFullQuery += "ORDER BY `student_id` ASC";
        addToStudentTable(StudentFullQuery);
        StudentFullQuery = backup;

    }//GEN-LAST:event_s_loadAll_btnActionPerformed

    private void s_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_tableMouseClicked
        
        DefaultTableModel dtm = (DefaultTableModel) s_table.getModel();
        if (dtm.getRowCount() > 0) {
            
            s_adp_panel.setVisible(true);
            s_pp_panel.setVisible(true);
            
            try {
                
                selectedTableRow = s_table.getSelectedRow();
                Student student = studentMap.get(String.valueOf(s_table.getValueAt(s_table.getSelectedRow(), 0)));
                
                tableSelectedUserId = String.valueOf(s_table.getValueAt(s_table.getSelectedRow(), 0));
                tableSelectedUserMobile = String.valueOf(student.getMobile());
                tableSelectedUserAddressLine1 = String.valueOf(student.getLine1());
                tableSelectedUserAddressLine2 = String.valueOf(student.getLine2());
                tableSelectedUserEmail = String.valueOf(student.getEmail());
                tableSelectedUserDepartment = String.valueOf(student.getSubject());
                tableSelectedUserEmergencyName1 = String.valueOf(student.getEcn1());
                tableSelectedUserEmergencyContact1 = String.valueOf(student.getEcn1());
                tableSelectedUserEmergencyName2 = String.valueOf(student.getEcn2());
                tableSelectedUserEmergencyContact2 = String.valueOf(student.getEcn2());
                tableSelectedUserEmergencyContactId1 = String.valueOf(student.getEci1());
                tableSelectedUserEmergencyContactId2 = String.valueOf(student.getEci2());
                
                s_ecn_lbl.setText(student.getEcn1());
                s_ecnu_lbl.setText(student.getEcc1());
                s_ecn_lb2.setText(student.getEcn2());
                s_ecnu_lb2.setText(student.getEcc1());
                s_id_lbl.setText(student.getId());
                s_mobile_lbl.setText(student.getMobile());
                s_nic_lbl.setText(student.getNic());
                s_address_lbl.setText(student.getLine1() + ", " + student.getLine2());
                s_email_lbl.setText(student.getEmail());
                s_subject_lbl.setText(student.getSubject());
                s_profilePic_lbl.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(student.getImageUrl().replace("src", "")))));
                s_name_lbl.setText(student.getSurname() + " " + student.getFname() + " " + student.getLname());
                s_s_name_lbl.setText(student.getSurname() + " " + student.getFname() + " " + student.getLname());
                s_reg_lbl.setText(student.getRegisterdDateTime());
                s_gender_lbl.setText(student.getGender());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

    }//GEN-LAST:event_s_tableMouseClicked

    private void s_ecu_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_ecu_btnjButton13ActionPerformed
        
        HashMap<String, String> ecMap = new HashMap<>();
        ecMap.put("id", tableSelectedUserId);
        ecMap.put("ecid1", tableSelectedUserEmergencyContactId1);
        ecMap.put("ecid2", tableSelectedUserEmergencyContactId2);
        ecMap.put("ec1N", tableSelectedUserEmergencyName1);
        ecMap.put("ec1C", tableSelectedUserEmergencyContact1);
        ecMap.put("ec2N", tableSelectedUserEmergencyName2);
        ecMap.put("ec2C", tableSelectedUserEmergencyContact2);
        ecMap.put("table", String.valueOf(table));
        
        new gui.UpdateEmergencyContactDialog(null, true, ecMap, this).setVisible(true);

    }//GEN-LAST:event_s_ecu_btnjButton13ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        
        try {
            
            int selectedOption = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Remove This Account ?", "Confirm Account De-activation", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `student` SET `student`.`status_status_id`='2' "
                        + "WHERE `student`.`student_id`='" + tableSelectedUserId + "'");
                
                JOptionPane.showMessageDialog(this, "Account De-activation Success", "Acount De-activated", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton45ActionPerformed

    private void t_ec_up_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_ec_up_btnjButton13ActionPerformed
        
        HashMap<String, String> ecMap = new HashMap<>();
        ecMap.put("id", tableSelectedUserId);
        ecMap.put("ecid1", tableSelectedUserEmergencyContactId1);
        ecMap.put("ecid2", tableSelectedUserEmergencyContactId2);
        ecMap.put("ec1N", tableSelectedUserEmergencyName1);
        ecMap.put("ec1C", tableSelectedUserEmergencyContact1);
        ecMap.put("ec2N", tableSelectedUserEmergencyName2);
        ecMap.put("ec2C", tableSelectedUserEmergencyContact2);
        ecMap.put("table", String.valueOf(table));
        
        new gui.UpdateEmergencyContactDialog(null, true, ecMap, this).setVisible(true);

    }//GEN-LAST:event_t_ec_up_btnjButton13ActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        
        switch (jTabbedPane2.getSelectedIndex()) {
            case 0:
                //administrator
                table = 1;
                isOneRoundDone = true;
                setUpAdministratorBg();
                break;
            case 1:
                //teacher
                table = 2;
                isOneRoundDone = true;
                setUpTeacherBg();
                break;
            case 2:
                //student
                table = 3;
                isOneRoundDone = true;
                setUpStudentBg();
                break;
            case 3:
                //subject
                isOneRoundDone = true;
                loadSubjectPanel();
            case 4:
                //class
                isOneRoundDone = true;
                loadClassPanel();
            default:
                break;
        }

    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void a_ec_up_btnjButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_ec_up_btnjButton13ActionPerformed
        
        HashMap<String, String> ecMap = new HashMap<>();
        ecMap.put("id", tableSelectedUserId);
        ecMap.put("ecid1", tableSelectedUserEmergencyContactId1);
        ecMap.put("ecid2", tableSelectedUserEmergencyContactId2);
        ecMap.put("ec1N", tableSelectedUserEmergencyName1);
        ecMap.put("ec1C", tableSelectedUserEmergencyContact1);
        ecMap.put("ec2N", tableSelectedUserEmergencyName2);
        ecMap.put("ec2C", tableSelectedUserEmergencyContact2);
        ecMap.put("table", String.valueOf(table));
        
        new gui.UpdateEmergencyContactDialog(null, true, ecMap, this).setVisible(true);

    }//GEN-LAST:event_a_ec_up_btnjButton13ActionPerformed

    private void sub_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_search_btnActionPerformed
        
        sub_table_print_btn.setVisible(false);
        sub_addi_det_panel.setVisible(false);
        
        if (!sub_name_textfield.getText().isBlank()) {
            
            String subName = sub_name_textfield.getText();
            
            String backup = subMainQuery;
            subMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `subject`.`status_status_id`='1' AND `subject`.`subject` LIKE'" + subName + "%'";
            addToSubjectTable(subMainQuery);
            subMainQuery = backup;
            
        }

    }//GEN-LAST:event_sub_search_btnActionPerformed

    private void sub_addnew_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_addnew_btnActionPerformed
        
        new AddNewSubjectDialog(null, true).setVisible(true);

    }//GEN-LAST:event_sub_addnew_btnActionPerformed

    private void sub_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_reset_btnActionPerformed
        
        loadSubjectPanel();

    }//GEN-LAST:event_sub_reset_btnActionPerformed

    private void sub_loadall_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_loadall_btnActionPerformed
        
        String backup = subMainQuery;
        subMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `subject`.`status_status_id`='1'";
        addToSubjectTable(subMainQuery);
        subMainQuery = backup;

    }//GEN-LAST:event_sub_loadall_btnActionPerformed

    private void sub_name_textfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sub_name_textfieldKeyReleased
        
        if (!sub_name_textfield.getText().isBlank() && isOneRoundDone) {
            
            isOneRoundDone = false;
            String subName = sub_name_textfield.getText();
            
            String backup = subMainQuery;
            subMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `subject`.`status_status_id`='1' AND `subject`.`subject` LIKE'" + subName + "%'";
            addToSubjectTable(subMainQuery);
            subMainQuery = backup;
            isOneRoundDone = true;
            
        }

    }//GEN-LAST:event_sub_name_textfieldKeyReleased

    private void subTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subTableMouseClicked
        
        sub_table_print_btn.setVisible(true);
        sub_addi_det_panel.setVisible(true);
        
        subTableClickedRow = subTable.getSelectedRow();
        
        String subId = String.valueOf(subTable.getValueAt(subTableClickedRow, 0));
        Subject subject = subjectMap.get(subId);
        
        subname_lbl.setText(subject.getSubjectName());
        subid_lbl.setText(subject.getId());
        sub_class_lbl.setText(subject.getClassName());
        sub_lec_lbl.setText(subject.getLecturer());
        sub_lec_con_lbl.setText(subject.getLecturerContact());
        sub_lec_em_lbl.setText(subject.getLecturerEmail());
        
        selectedSubjectId = subject.getId();
        selectedClassId = subject.getClassId();
        selectedSubjectTeacherId = subject.getLecturerId();

    }//GEN-LAST:event_subTableMouseClicked

    private void sub_remove_sub_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_remove_sub_btnActionPerformed
        
        try {
            
            int selectedOption = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Remove This Subject ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `teacher` SET `teacher`.`subject_subject_id`='1' WHERE `teacher`.`teacher_id`='" + selectedSubjectTeacherId + "'");
                MySQL.executeIUD("UPDATE `subject` SET `subject`.`status_status_id`='2' WHERE `subject`.`subject_id`='" + selectedSubjectId + "'");
                
                DefaultTableModel dtm = (DefaultTableModel) subTable.getModel();
                dtm.removeRow(selectedTableRow);
                int rowCount = dtm.getRowCount();
                subTable.setModel(dtm);
                
                if (rowCount == 0) {
                    s_printTable.setVisible(false);
                }
                s_adp_panel.setVisible(false);
                
                subjectMap.remove(selectedSubjectId);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_sub_remove_sub_btnActionPerformed

    private void sub_cl_up_btn1jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_cl_up_btn1jButton6ActionPerformed
        
        new gui.UpdateClassPaletSubjectDialog(null, true, selectedSubjectId, selectedClassId, this).setVisible(true);

    }//GEN-LAST:event_sub_cl_up_btn1jButton6ActionPerformed

    private void sub_lec_up_btn1jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_lec_up_btn1jButton6ActionPerformed
        
        new gui.UpdateLecturerDialog(null, true, selectedSubjectId, selectedClassId, this).setVisible(true);

    }//GEN-LAST:event_sub_lec_up_btn1jButton6ActionPerformed

    private void cl_remove_class_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_remove_class_btnActionPerformed
        
        try {
            
            int selectedOption = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Remove This Class ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `subject` SET `subject`.`class_class_id`='1' WHERE `subject`.`subject`='" + selectedSubjectId + "'");
                MySQL.executeIUD("UPDATE `class` SET `class`.`status_status_id`='2' WHERE `class`.`class_id`='" + selectedClassId + "'");
                
                DefaultTableModel dtm = (DefaultTableModel) cl_table.getModel();
                dtm.removeRow(selectedTableRow);
                int rowCount = dtm.getRowCount();
                cl_table.setModel(dtm);
                
                if (rowCount == 0) {
                    cl_pr_tb_btn.setVisible(false);
                }
                cl_addi_det_panel.setVisible(false);
                
                classMap.remove(selectedClassId);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }//GEN-LAST:event_cl_remove_class_btnActionPerformed

    private void cl_name_textfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cl_name_textfieldKeyReleased
        
        if (!cl_name_textfield.getText().isBlank() && isOneRoundDone) {
            
            isOneRoundDone = false;
            String clzName = cl_name_textfield.getText();
            
            String backup = classMainQuery;
            subMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `subject`.`status_status_id`='1' AND `class`.`status_status_id`='1' AND `class`.`class_name` LIKE'" + clzName + "%'";
            addToClassTable(classMainQuery);
            classMainQuery = backup;
            isOneRoundDone = true;
            
        }

    }//GEN-LAST:event_cl_name_textfieldKeyReleased

    private void cl_search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_search_btnActionPerformed
        
        cl_pr_tb_btn.setVisible(false);
        cl_addi_det_panel.setVisible(false);
        
        if (!cl_name_textfield.getText().isBlank()) {
            
            String subName = cl_name_textfield.getText();
            
            String backup = classMainQuery;
            classMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `class`.`status_status_id`='1' AND `class`.`class_name` LIKE'" + subName + "%'";
            addToClassTable(classMainQuery);
            classMainQuery = backup;
            
        }

    }//GEN-LAST:event_cl_search_btnActionPerformed

    private void cl_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_reset_btnActionPerformed
        
        loadClassPanel();

    }//GEN-LAST:event_cl_reset_btnActionPerformed

    private void cl_loadall_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_loadall_btnActionPerformed
        
        String backup = classMainQuery;
        classMainQuery += " WHERE `teacher`.`status_status_id`='1' AND `subject`.`status_status_id`='1'  AND `class`.`status_status_id`='1'";
        System.out.println(classMainQuery);
        addToSubjectTable(classMainQuery);
        classMainQuery = backup;

    }//GEN-LAST:event_cl_loadall_btnActionPerformed

    private void cl_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cl_tableKeyReleased
        
        cl_pr_tb_btn.setVisible(true);
        cl_addi_det_panel.setVisible(true);
        
        clzTableClickedRow = cl_table.getSelectedRow();
        
        String clzId = String.valueOf(cl_table.getValueAt(clzTableClickedRow, 0));
        Clzcls clss = classMap.get(clzId);
        
        cl_sub_lbl.setText(clss.getSubjectName());
        cl_clid_lbl.setText(clss.getId());
        cl_cl_name_lbl.setText(clss.getClassName());
        cl_lec_lbl.setText(clss.getLecturer());
        cl_le_con_lbl.setText(clss.getLecturerContact());
        cl_lec_em_lbl.setText(clss.getLecturerEmail());
        startAttextfield.setText(clss.getStartAt());
        endAttextfield.setText(clss.getEnd());
        
        selectedSubjectId = clss.getId();
        selectedClassId = clss.getClassId();
        selectedSubjectTeacherId = clss.getLecturerId();

    }//GEN-LAST:event_cl_tableKeyReleased

    private void addNewClassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewClassBtnActionPerformed
        new AddNewClassDialog(null, true).setVisible(true);
    }//GEN-LAST:event_addNewClassBtnActionPerformed

    private void sub_lec_up_btn2jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_lec_up_btn2jButton6ActionPerformed
        
        startAt.showPopup(this, 100, 100);
        startatupdatebtn.setVisible(true);

    }//GEN-LAST:event_sub_lec_up_btn2jButton6ActionPerformed

    private void sub_lec_up_btn3jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_lec_up_btn3jButton6ActionPerformed
        
        endAt.showPopup(this, 100, 100);
        endatupdatebtn.setVisible(true);

    }//GEN-LAST:event_sub_lec_up_btn3jButton6ActionPerformed

    private void sub_lec_up_btn4jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sub_lec_up_btn4jButton6ActionPerformed
       
        jSpinner1.setEnabled(true);
        jButton1.setVisible(true);
        
    }//GEN-LAST:event_sub_lec_up_btn4jButton6ActionPerformed

    private void startatupdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startatupdatebtnActionPerformed
        
        String startTime = startAttextfield.getText();
        String FormatedTime = time(startTime);
        
        try {
            
            int option = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update Start Time ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `class` SET `class`.`start`='" + FormatedTime + "' WHERE `class`.`class_id`='" + cl_clid_lbl.getText() + "'");
                JOptionPane.showMessageDialog(this, "Start Time Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_startatupdatebtnActionPerformed

    private void endatupdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endatupdatebtnActionPerformed
        
        String endTime = endAttextfield.getText();
        String FormatedTime = time(endTime);
        
        try {
            
            int option = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update End Time ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                
                MySQL.executeIUD("UPDATE `class` SET `class`.`start`='" + FormatedTime + "' WHERE `class`.`class_id`='" + cl_clid_lbl.getText() + "'");
                JOptionPane.showMessageDialog(this, "End Time Update Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_endatupdatebtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        double price = (double)jSpinner1.getValue();
        if(price > 0){
            
            int option =JOptionPane.showConfirmDialog(this,"Are You Sure You Want To Update The Price ?","Confirm",JOptionPane.YES_NO_OPTION);
            if(option ==JOptionPane.YES_OPTION){
                
                try {
                    
                    MySQL.executeIUD("UPDATE `subject` SET `price`='"+price+"' WHERE `subject`.`subject_id`='"+subid_lbl.getText()+"'");
                    JOptionPane.showMessageDialog(this,"Price Update Success","Success",JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a_address_lbl;
    private javax.swing.JButton a_address_up_btn;
    private javax.swing.JLabel a_department_lbl;
    private javax.swing.JButton a_department_up_btn;
    private javax.swing.JButton a_ec_up_btn;
    private javax.swing.JLabel a_ecn_lb;
    private javax.swing.JLabel a_ecn_lbl;
    private javax.swing.JLabel a_ecnu_lb;
    private javax.swing.JLabel a_ecnu_lbl;
    private javax.swing.JButton a_editMobile_btn;
    private javax.swing.JLabel a_email_lbl;
    private javax.swing.JButton a_email_up_btn;
    private javax.swing.JLabel a_gender_lbl;
    private javax.swing.JLabel a_id_lbl;
    private javax.swing.JTextField a_id_search_textfield;
    private javax.swing.JButton a_loadAll_btn;
    private javax.swing.JLabel a_mobile_lbl;
    private javax.swing.JLabel a_name_lbl;
    private javax.swing.JLabel a_nic_lbl;
    private javax.swing.JLabel a_profilePic_lbl;
    private javax.swing.JButton a_pt_btn;
    private javax.swing.JTextArea a_qualification_textarea;
    private javax.swing.JLabel a_reg_lbl;
    private javax.swing.JButton a_removeAdministrator_btn;
    private javax.swing.JButton a_reset_btn;
    private javax.swing.JLabel a_s_name_lbl;
    private javax.swing.JButton a_search_id_btn;
    private javax.swing.JTable a_table;
    private javax.swing.JButton addNewClassBtn;
    private javax.swing.JPanel administrator;
    private javax.swing.JPanel cl_addi_det_panel;
    private javax.swing.JLabel cl_cl_name_lbl;
    private javax.swing.JLabel cl_clid_lbl;
    private javax.swing.JLabel cl_le_con_lbl;
    private javax.swing.JLabel cl_lec_em_lbl;
    private javax.swing.JLabel cl_lec_lbl;
    private javax.swing.JButton cl_loadall_btn;
    private javax.swing.JTextField cl_name_textfield;
    private javax.swing.JButton cl_pr_tb_btn;
    private javax.swing.JButton cl_remove_class_btn;
    private javax.swing.JButton cl_reset_btn;
    private javax.swing.JButton cl_search_btn;
    private javax.swing.JLabel cl_sub_lbl;
    private javax.swing.JTable cl_table;
    private javax.swing.JButton dipartmentAssignRequired_btn;
    private com.raven.swing.TimePicker endAt;
    private javax.swing.JTextField endAttextfield;
    private javax.swing.JButton endatupdatebtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton62;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton l_subjectAssignReq_btn;
    private javax.swing.JPanel pddPanel;
    private javax.swing.JPanel pidpanel;
    private javax.swing.JButton s_add_btn;
    private javax.swing.JLabel s_address_lbl;
    private javax.swing.JPanel s_adp_panel;
    private javax.swing.JLabel s_ecn_lb2;
    private javax.swing.JLabel s_ecn_lbl;
    private javax.swing.JLabel s_ecnu_lb2;
    private javax.swing.JLabel s_ecnu_lbl;
    private javax.swing.JButton s_ecu_btn;
    private javax.swing.JButton s_email_btn;
    private javax.swing.JLabel s_email_lbl;
    private javax.swing.JLabel s_gender_lbl;
    private javax.swing.JLabel s_id_lbl;
    private javax.swing.JTextField s_id_textfield;
    private javax.swing.JButton s_loadAll_btn;
    private javax.swing.JButton s_mobile_btn;
    private javax.swing.JLabel s_mobile_lbl;
    private javax.swing.JLabel s_name_lbl;
    private javax.swing.JLabel s_nic_lbl;
    private javax.swing.JPanel s_pp_panel;
    private javax.swing.JButton s_printTable;
    private javax.swing.JLabel s_profilePic_lbl;
    private javax.swing.JLabel s_reg_lbl;
    private javax.swing.JButton s_reset_btn;
    private javax.swing.JLabel s_s_name_lbl;
    private javax.swing.JButton s_search_btn;
    private javax.swing.JButton s_sub_btn;
    private javax.swing.JLabel s_subject_lbl;
    private javax.swing.JTable s_table;
    private com.raven.swing.TimePicker startAt;
    private javax.swing.JTextField startAttextfield;
    private javax.swing.JButton startatupdatebtn;
    private javax.swing.JTable subTable;
    private javax.swing.JPanel sub_addi_det_panel;
    private javax.swing.JButton sub_addnew_btn;
    private javax.swing.JButton sub_cl_up_btn;
    private javax.swing.JButton sub_cl_up_btn1;
    private javax.swing.JLabel sub_class_lbl;
    private javax.swing.JLabel sub_lec_con_lbl;
    private javax.swing.JLabel sub_lec_em_lbl;
    private javax.swing.JLabel sub_lec_lbl;
    private javax.swing.JButton sub_lec_up_btn;
    private javax.swing.JButton sub_lec_up_btn1;
    private javax.swing.JButton sub_lec_up_btn2;
    private javax.swing.JButton sub_lec_up_btn3;
    private javax.swing.JButton sub_lec_up_btn4;
    private javax.swing.JButton sub_loadall_btn;
    private javax.swing.JTextField sub_name_textfield;
    private javax.swing.JButton sub_remove_sub_btn;
    private javax.swing.JButton sub_reset_btn;
    private javax.swing.JButton sub_search_btn;
    private javax.swing.JButton sub_table_print_btn;
    private javax.swing.JLabel subid_lbl;
    private javax.swing.JButton submit_class_btn;
    private javax.swing.JButton submit_class_btn1;
    private javax.swing.JLabel subname_lbl;
    private javax.swing.JPanel t_add_panel;
    private javax.swing.JButton t_add_up_btn;
    private javax.swing.JLabel t_address_lbl;
    private javax.swing.JButton t_ec_up_btn;
    private javax.swing.JLabel t_ecn_lb;
    private javax.swing.JLabel t_ecn_lbl;
    private javax.swing.JLabel t_ecnu_lb;
    private javax.swing.JLabel t_ecnu_lbl;
    private javax.swing.JLabel t_email_lbl;
    private javax.swing.JButton t_email_up_btn;
    private javax.swing.JLabel t_gender_lbl;
    private javax.swing.JLabel t_id_lbl;
    private javax.swing.JTextField t_idin_textfield;
    private javax.swing.JButton t_loadAll_btn;
    private javax.swing.JLabel t_mobile_lbl;
    private javax.swing.JButton t_mobile_up_btn;
    private javax.swing.JLabel t_name_lbl;
    private javax.swing.JLabel t_nic_lbl;
    private javax.swing.JPanel t_pp_panel;
    private javax.swing.JLabel t_profilePic_lbl;
    private javax.swing.JButton t_pt_btn;
    private javax.swing.JTextArea t_qualification_textarea;
    private javax.swing.JLabel t_reg_lbl;
    private javax.swing.JButton t_remove_btn;
    private javax.swing.JButton t_reset_btn;
    private javax.swing.JLabel t_s_name_lbl;
    private javax.swing.JButton t_search_btn;
    private javax.swing.JButton t_sub_up_btn;
    private javax.swing.JLabel t_subject_lbl;
    private javax.swing.JTable t_table;
    private javax.swing.JPanel teacher;
    // End of variables declaration//GEN-END:variables

}

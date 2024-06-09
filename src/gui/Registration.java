package gui;

import java.util.HashMap;
import javax.swing.JOptionPane;
import model.MySQL;
import model.SelectImage;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import model.CheckInternetConnection;
import model.EmailFormatValidation;
import model.MobileFormatValidation;
import model.SendEmail;
import model.UniqId;
import model.UploadImage;

public class Registration extends javax.swing.JPanel {

    private HashMap<String, String> imageMap;
    private HashMap<String, Integer> surnameMap;
    private HashMap<String, Integer> genderMap;
    private boolean isImageSelected = false;
    private boolean isAdministratorPanel = false;
    private boolean isTeacherPanel = false;
    private boolean isStudentPanel = false;
    private HashMap<String, String> subject = new HashMap<>();
    private String subjectId;
    
    public static int id = 0;

    public Registration() {
        initComponents();
        loadSurname();
        loadGender();
        clearAdministrator();
        clearTeacher();
        clearStudent();
    }

    public void loadSurname() {

        try {
            ResultSet resultset = MySQL.executeSearch("SELECT * FROM `surname`");

            surnameMap = new HashMap<>();
            surnameMap.put("Choose", 0);

            Vector<String> surnameVector = new Vector();
            surnameVector.add("Choose");

            while (resultset.next()) {
                surnameMap.put(resultset.getString("surname"), Integer.parseInt(resultset.getString("surname_id")));
                surnameVector.add(resultset.getString("surname"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(surnameVector);

            a_comboBox_surname.setModel(dcm);
            t_comboBox_surname.setModel(dcm);
            s_comboBox_surname.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGender() {

        try {
            ResultSet resultset = MySQL.executeSearch("SELECT * FROM `gender`");

            genderMap = new HashMap<>();
            genderMap.put("Choose", 0);

            Vector<String> genderVector = new Vector();
            genderVector.add("Choose");

            while (resultset.next()) {
                genderMap.put(resultset.getString("gender"), Integer.parseInt(resultset.getString("gender_id")));
                genderVector.add(resultset.getString("gender"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(genderVector);

            a_comboBox_gender.setModel(dcm);
            t_comboBox_gender.setModel(dcm);
            s_comboBox_gender.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAdministrator() {

        a_comboBox_surname.setSelectedIndex(0);
        a_comboBox_gender.setSelectedIndex(0);

        a_textField_nic.setText("");
        a_textField_firstName.setText("");
        a_textField_lastName.setText("");
        a_textField_email.setText("");
        a_textField_mobile.setText("");
        a_birthday.setText("");
        a_textField_addressLine1.setText("");
        a_textField_addressLine2.setText("");

        a_ec1_name_TextField.setText("");
        a_ec1_mobile_TextField.setText("");
        a_ec2_name_TextField.setText("");
        a_ec2_mobile_TextField.setText("");

        a_qualification_textArea.setText("");

    }

    public void clearTeacher() {

        t_comboBox_surname.setSelectedIndex(0);
        t_comboBox_gender.setSelectedIndex(0);

        t_textField_nic.setText("");
        t_textField_firstName.setText("");
        t_textField_lastName.setText("");
        t_textField_email.setText("");
        t_mobile_textfiled.setText("");
        birthday_T.setText("");
        t_textField_addressLine1.setText("");
        t_textField_addressLine2.setText("");

        t_ecn1_name_textfield.setText("");
        t_ecn1_mobile_textfield.setText("");
        t_ecn2_name_textfield.setText("");
        t_ecn2_mobile_textfield.setText("");

        t_qualification_textArea.setText("");

    }

    public void clearStudent() {

        s_comboBox_surname.setSelectedIndex(0);
        s_comboBox_gender.setSelectedIndex(0);

        s_textField_nic.setText("");
        s_textField_firstName.setText("");
        s_textField_lastName.setText("");
        s_textField_email.setText("");
        s_textField_mobile.setText("");
        textField_S_birthday.setText("");
        textField_S_addressLine1.setText("");
        textField_S_addressLine2.setText("");

        s_ecn1_name_textfield.setText("");
        s_ecn1_mobile_textfield.setText("");
        s_ecn2_name_textfield.setText("");
        s_ecn2_mobile_textfield.setText("");

        try {

            ResultSet subrs = MySQL.executeSearch("SELECT * FROM `subject` WHERE `subject`.`status_status_id`='1' AND `subject`.`subject_id`!='1' "
                    + "ORDER BY `subject`.`subject_id` ASC");

            Vector<String> v = new Vector<>();
            v.add("Choose");
            while (subrs.next()) {
                v.add(subrs.getString("subject"));

                if (!subject.containsKey(subrs.getString("subject"))) {
                    subject.put(subrs.getString("subject"), subrs.getString("subject_id"));
                }
            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox1.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        jComboBox1.setSelectedIndex(0);

    }

    private void selectImage() {
        imageMap = SelectImage.getImage();

        switch (imageMap.get("image")) {
            case "1":
                JOptionPane.showMessageDialog(this, "Image Selected", "Notification", JOptionPane.INFORMATION_MESSAGE);
                isImageSelected = true;
                break;
            case "2":
                JOptionPane.showMessageDialog(this, "Selected file is not an image", "Notification", JOptionPane.WARNING_MESSAGE);
                break;
            case "3":
                JOptionPane.showMessageDialog(this, "No file selected", "Notification", JOptionPane.WARNING_MESSAGE);
                break;
            default:
                break;
        }
    }

    private void submitVerification(int genderID, int surnameID, String nic, String firstName, String lastName, String email, String mobile, String birthday, String line1, String line2, String ec1name, String ec1mobile, String ec2name, String ec2mobile, String qualifications) {

        if (CheckInternetConnection.isInternetAvailable()) {

            String dbTableName = "";
            String dbEmergencyContactTableName = "";
            String dbMainForeignColumnName = "";
            String dbQualificationTableName = "";
            String accountType = "";
            String addressTableName = "";

            if (isAdministratorPanel) {

                dbTableName = "administrator";
                dbEmergencyContactTableName = "administrator_emergency_contact";
                dbMainForeignColumnName = "administrator_administrator_id";
                dbQualificationTableName = "administrator_qualification";
                accountType = "2";
                addressTableName = "administrator_address";

            } else if (isTeacherPanel) {

                dbTableName = "teacher";
                dbEmergencyContactTableName = "teacher_emergency_contact";
                dbMainForeignColumnName = "teacher_teacher_id";
                dbQualificationTableName = "teacher_qualification";
                accountType = "3";
                addressTableName = "teacher_address";

            } else if (isStudentPanel) {

                if (!qualifications.equals("0")) {
                    dbTableName = "student";
                    dbEmergencyContactTableName = "student_emergency_contact";
                    dbMainForeignColumnName = "student_student_id";
                    accountType = "4";
                    addressTableName = "student_address";
                } else {
                    JOptionPane.showMessageDialog(this, "Please Select Your Subject", "Missing Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }

            if (nic.isBlank() || firstName.isBlank() || lastName.isBlank() || email.isBlank() || mobile.isBlank()
                    || birthday.isBlank() || line1.isBlank() || line2.isBlank() || firstName.isBlank()) {

                JOptionPane.showMessageDialog(this, "Please Fill All From Personal Detail Section", "Missing Data", JOptionPane.WARNING_MESSAGE);

            } else if (nic.length() > 19) {
                JOptionPane.showMessageDialog(this, "NIC Is Too Long", "Too Long Data", JOptionPane.WARNING_MESSAGE);

            } else if (firstName.length() > 45) {
                JOptionPane.showMessageDialog(this, "First Name Is Too Long", "Too Long Data", JOptionPane.WARNING_MESSAGE);

            } else if (lastName.length() > 45) {
                JOptionPane.showMessageDialog(this, "Last Name Is Too Long", "Too Long Data", JOptionPane.WARNING_MESSAGE);

            } else if (email.length() > 100) {
                JOptionPane.showMessageDialog(this, "Email Is Too Long", "Too Long Data", JOptionPane.WARNING_MESSAGE);

            } else if (mobile.length() > 15) {
                JOptionPane.showMessageDialog(this, "Mobile Is Too Long", "Too Long Data", JOptionPane.WARNING_MESSAGE);

            } else if (surnameID == 0) {
                JOptionPane.showMessageDialog(this, "Please Select Your Surname", "Missing Data", JOptionPane.WARNING_MESSAGE);

            } else if (genderID == 0) {
                JOptionPane.showMessageDialog(this, "Please Select Your Gender", "Missing Data", JOptionPane.WARNING_MESSAGE);

            } else if (!EmailFormatValidation.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid Email Address", "Invalid Data", JOptionPane.WARNING_MESSAGE);

            } else if (!MobileFormatValidation.isValidMobile(mobile)) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile Number", "Invalid Data", JOptionPane.WARNING_MESSAGE);

            } else if ((ec1name.isBlank() && ec1mobile.isBlank()) && (ec2name.isBlank() && ec2mobile.isBlank())) {
                JOptionPane.showMessageDialog(this, "At Least One Emergency Contact Must Be Filled", "Missing Data", JOptionPane.WARNING_MESSAGE);

            } else {

                try {

                    ResultSet alreadyInRS = MySQL.executeSearch("SELECT * FROM `" + dbTableName + "` "
                            + "WHERE `" + dbTableName + "`.`email`='" + email + "' OR `" + dbTableName + "`.`nic`='" + nic + "' "
                            + "AND `" + dbTableName + "`.`status_status_id`='1'");

                    int rowNum = 0;
                    if (alreadyInRS.last()) {
                        rowNum = alreadyInRS.getRow();
                        alreadyInRS.beforeFirst();
                    }

                    if (!(rowNum > 0)) {// new user

                        if (isImageSelected) {
                            HashMap<String, String> imageUploadMap = UploadImage.upload(imageMap);
                            if (imageUploadMap.get("status").equals("1")) {//success

                                String uploadedUrl = imageUploadMap.get("url");
                                String password = UniqId.generate();

                                if (!isStudentPanel) {
                                    id = MySQL.executeIUD("INSERT INTO `" + dbTableName + "`(`first_name`,`last_name`,`nic`,`email`,`mobile`,`birthday`,`password`,`surname_surname_id`,`account_type_account_type_id`,`gender_gender_id`,`image_url`) "
                                            + "VALUES('" + firstName + "','" + lastName + "','" + nic + "','" + email + "','" + mobile + "','" + birthday + "','" + password + "','" + surnameID + "','" + accountType + "','" + genderID + "','" + uploadedUrl + "')");
                                } else {
                                    id = MySQL.executeIUD("INSERT INTO `" + dbTableName + "`(`first_name`,`last_name`,`nic`,`email`,`mobile`,`birthday`,`password`,`surname_surname_id`,`account_type_account_type_id`,`gender_gender_id`,`image_url`,`subject_subject_id`) "
                                            + "VALUES('" + firstName + "','" + lastName + "','" + nic + "','" + email + "','" + mobile + "','" + birthday + "','" + password + "','" + surnameID + "','" + accountType + "','" + genderID + "','" + uploadedUrl + "','"+subjectId+"')");
                                }
                                String isEmailSend = SendEmail.send(email, "Registration Credentials", "Welcome to ADYAPANA INSTITUTE !, Your ID is: '" + id + "' Your Username is: '" + email + "' AND Your Password is: '" + password + "'");
                                if (!isEmailSend.equals("1")) {
                                    JOptionPane.showMessageDialog(this, "Credential Email Sending Faild", "Email Sending Faild", JOptionPane.ERROR_MESSAGE);
                                }

                                MySQL.executeIUD("INSERT INTO `" + addressTableName + "`(`line_1`,`line_2`,`" + dbMainForeignColumnName + "`) VALUES('" + line1 + "','" + line2 + "','" + id + "')");

                            } else {
                                JOptionPane.showMessageDialog(this, "Image Uploading Faild, Process Will Be Terminated", "Something Went Wrong, Please Try Again Later", JOptionPane.ERROR_MESSAGE);

                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Please Select Profile Image", "Profile Image Required", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {// already registered
                        JOptionPane.showMessageDialog(this, "You Are a Active User In Our Institute", "Already Registered", JOptionPane.ERROR_MESSAGE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (id == 0) {
                    JOptionPane.showMessageDialog(this, "Data Could not be Saved", "Something Went Wrong, Please Try Again Later", JOptionPane.ERROR_MESSAGE);

                } else {

                    if (!ec1name.isBlank() && ec1mobile.isBlank()) {
                        JOptionPane.showMessageDialog(this, "Please Insert Your First Emergency Contact Number", "Missing Data", JOptionPane.WARNING_MESSAGE);

                    } else if (ec1name.isBlank() && !ec1mobile.isBlank()) {
                        JOptionPane.showMessageDialog(this, "Please Insert Your First Emergency Contact Name", "Missing Data", JOptionPane.WARNING_MESSAGE);

                    } else if (!ec1name.isBlank() && !ec1mobile.isBlank()) {
                        try {
                            MySQL.executeIUD("INSERT INTO `" + dbEmergencyContactTableName + "`(`name`,`contact`,`" + dbMainForeignColumnName + "`) "
                                    + "VALUES('" + ec1name + "','" + ec1mobile + "','" + id + "')");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (!ec2name.isBlank() && ec2mobile.isBlank()) {
                        JOptionPane.showMessageDialog(this, "Please Insert Your Second Emergency Contact Number", "Missing Data", JOptionPane.WARNING_MESSAGE);

                    } else if (ec2name.isBlank() && !ec2mobile.isBlank()) {
                        JOptionPane.showMessageDialog(this, "Please Insert Your Second Emergency Contact Name", "Missing Data", JOptionPane.WARNING_MESSAGE);

                    } else if (!ec2name.isBlank() && !ec2mobile.isBlank()) {
                        try {
                            MySQL.executeIUD("INSERT INTO `" + dbEmergencyContactTableName + "`(`name`,`contact`,`" + dbMainForeignColumnName + "`) "
                                    + "VALUES('" + ec2name + "','" + ec2mobile + "','" + id + "')");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (!isStudentPanel) {
                        if (!qualifications.isBlank()) {
                            try {
                                MySQL.executeIUD("INSERT INTO `" + dbQualificationTableName + "`(`qualification`,`" + dbMainForeignColumnName + "`) "
                                        + "VALUES('" + qualifications + "','" + id + "')");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (isStudentPanel) {
                        if (!qualifications.isBlank()) {// classes
                            try {
                                MySQL.executeIUD("UPDATE `student` SET `student`.`classes`='" + qualifications + "' "
                                        + "WHERE `student`.`student_id`='" + id + "'");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(this, "Registration Complete", "Success", JOptionPane.INFORMATION_MESSAGE);

                    if (isAdministratorPanel) {
                        clearAdministrator();
                    } else if (isTeacherPanel) {
                        clearTeacher();
                    } else if (isStudentPanel) {
                        clearStudent();
                    }
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Could not Fint Active Internet Connection", "Internet Connection not Found", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        a_birthday_chooser = new com.raven.datechooser.DateChooser();
        t_birthday_chooser = new com.raven.datechooser.DateChooser();
        s_birthday_chooser = new com.raven.datechooser.DateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrationPanel = new javax.swing.JPanel();
        tabPanel = new javax.swing.JTabbedPane();
        administratorTabPanel = new javax.swing.JPanel();
        a_textField_lastName = new javax.swing.JTextField();
        a_comboBox_gender = new javax.swing.JComboBox<>();
        a_printApplication_btn = new javax.swing.JButton();
        personalDetails_lbl1 = new javax.swing.JLabel();
        a_textField_mobile = new javax.swing.JTextField();
        a_birthday = new javax.swing.JTextField();
        email_lbl2 = new javax.swing.JLabel();
        birthday_lbl1 = new javax.swing.JLabel();
        surname_lbl2 = new javax.swing.JLabel();
        firstName_lbl2 = new javax.swing.JLabel();
        a_comboBox_surname = new javax.swing.JComboBox<>();
        a_textField_nic = new javax.swing.JTextField();
        a_textField_firstName = new javax.swing.JTextField();
        nic_lbl2 = new javax.swing.JLabel();
        lastName_lbl2 = new javax.swing.JLabel();
        a_textField_email = new javax.swing.JTextField();
        email_lbl3 = new javax.swing.JLabel();
        mobile_lbl1 = new javax.swing.JLabel();
        addressLine1_lbl1 = new javax.swing.JLabel();
        a_textField_addressLine1 = new javax.swing.JTextField();
        a_textField_addressLine2 = new javax.swing.JTextField();
        addressLine2_lbl1 = new javax.swing.JLabel();
        a_ec2_mobile_TextField = new javax.swing.JTextField();
        eC2Mobile_lbl1 = new javax.swing.JLabel();
        a_ec1_name_TextField = new javax.swing.JTextField();
        eCName_lbl1 = new javax.swing.JLabel();
        emergencyContact_lbl1 = new javax.swing.JLabel();
        a_ec1_mobile_TextField = new javax.swing.JTextField();
        eCM_lbl1 = new javax.swing.JLabel();
        contact1_lbl1 = new javax.swing.JLabel();
        contact2_lbl1 = new javax.swing.JLabel();
        a_ec2_name_TextField = new javax.swing.JTextField();
        eC2TextField1 = new javax.swing.JLabel();
        academicQalification_lbl1 = new javax.swing.JLabel();
        qualification_lbl1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        a_qualification_textArea = new javax.swing.JTextArea();
        a_submit_btn = new javax.swing.JButton();
        a_reset_btn = new javax.swing.JButton();
        selectProfilePic_btn = new javax.swing.JButton();
        a_date_popup_btn = new javax.swing.JButton();
        teacherTabPanel = new javax.swing.JPanel();
        qualification_lbl = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_qualification_textArea = new javax.swing.JTextArea();
        t_reset_btn = new javax.swing.JButton();
        t_submit_btn = new javax.swing.JButton();
        academicQalification_lbl = new javax.swing.JLabel();
        contact1_lbl = new javax.swing.JLabel();
        contact2_lbl = new javax.swing.JLabel();
        t_ecn2_name_textfield = new javax.swing.JTextField();
        eC2TextField = new javax.swing.JLabel();
        t_ecn2_mobile_textfield = new javax.swing.JTextField();
        eC2Mobile_lbl = new javax.swing.JLabel();
        t_ecn1_name_textfield = new javax.swing.JTextField();
        eCName_lbl = new javax.swing.JLabel();
        emergencyContact_lbl = new javax.swing.JLabel();
        t_ecn1_mobile_textfield = new javax.swing.JTextField();
        eCM_lbl = new javax.swing.JLabel();
        birthday_lbl = new javax.swing.JLabel();
        surname_lbl = new javax.swing.JLabel();
        firstName_lbl = new javax.swing.JLabel();
        t_comboBox_surname = new javax.swing.JComboBox<>();
        t_textField_nic = new javax.swing.JTextField();
        t_textField_firstName = new javax.swing.JTextField();
        nic_lbl = new javax.swing.JLabel();
        lastName_lbl = new javax.swing.JLabel();
        t_textField_email = new javax.swing.JTextField();
        email_lbl = new javax.swing.JLabel();
        mobile_lbl = new javax.swing.JLabel();
        addressLine1_lbl = new javax.swing.JLabel();
        t_textField_addressLine1 = new javax.swing.JTextField();
        t_textField_addressLine2 = new javax.swing.JTextField();
        addressLine2_lbl = new javax.swing.JLabel();
        t_textField_lastName = new javax.swing.JTextField();
        t_comboBox_gender = new javax.swing.JComboBox<>();
        t_printApplication_btn = new javax.swing.JButton();
        personalDetails_lbl = new javax.swing.JLabel();
        t_mobile_textfiled = new javax.swing.JTextField();
        birthday_T = new javax.swing.JTextField();
        email_lbl1 = new javax.swing.JLabel();
        t_profilepic_btn = new javax.swing.JButton();
        t_date_popup_btn = new javax.swing.JButton();
        studentTabPanel = new javax.swing.JPanel();
        s_textField_email = new javax.swing.JTextField();
        email_S_lbl = new javax.swing.JLabel();
        mobile_S_lbl = new javax.swing.JLabel();
        s_textField_mobile = new javax.swing.JTextField();
        addressLine1_S_lbl = new javax.swing.JLabel();
        textField_S_addressLine1 = new javax.swing.JTextField();
        textField_S_addressLine2 = new javax.swing.JTextField();
        addressLine2_S_lbl = new javax.swing.JLabel();
        gender_S_lbl = new javax.swing.JLabel();
        s_comboBox_gender = new javax.swing.JComboBox<>();
        textField_S_birthday = new javax.swing.JTextField();
        birthday_S_lbl = new javax.swing.JLabel();
        personalDetails_S_lbl1 = new javax.swing.JLabel();
        s_ecn1_name_textfield = new javax.swing.JTextField();
        eCName_S_lbl = new javax.swing.JLabel();
        emergencyContact_S_lbl = new javax.swing.JLabel();
        s_ecn1_mobile_textfield = new javax.swing.JTextField();
        eCM_S_lbl = new javax.swing.JLabel();
        academicQalification_S_lbl = new javax.swing.JLabel();
        qualification_S_lbl = new javax.swing.JLabel();
        contact1_S_lbl = new javax.swing.JLabel();
        contact2_S_lbl = new javax.swing.JLabel();
        s_ecn2_name_textfield = new javax.swing.JTextField();
        eC2TextField1S = new javax.swing.JLabel();
        s_ecn2_mobile_textfield = new javax.swing.JTextField();
        eC2Mobile_S_lbl = new javax.swing.JLabel();
        reset_S_btn = new javax.swing.JButton();
        submit_S_btn = new javax.swing.JButton();
        print_S_btn = new javax.swing.JButton();
        surname_lbl1 = new javax.swing.JLabel();
        firstName_lbl1 = new javax.swing.JLabel();
        s_comboBox_surname = new javax.swing.JComboBox<>();
        s_textField_nic = new javax.swing.JTextField();
        s_textField_firstName = new javax.swing.JTextField();
        nic_lbl1 = new javax.swing.JLabel();
        lastName_lbl1 = new javax.swing.JLabel();
        s_textField_lastName = new javax.swing.JTextField();
        s_profilepic_btn = new javax.swing.JButton();
        s_date_popup_btn = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        a_birthday_chooser.setForeground(new java.awt.Color(78, 166, 255));
        a_birthday_chooser.setDateFormat("yyyy-MM-dd");
        a_birthday_chooser.setTextRefernce(a_birthday);

        t_birthday_chooser.setForeground(new java.awt.Color(78, 166, 255));
        t_birthday_chooser.setDateFormat("yyyy-MM-dd");
        t_birthday_chooser.setTextRefernce(birthday_T);

        s_birthday_chooser.setForeground(new java.awt.Color(78, 166, 255));
        s_birthday_chooser.setDateFormat("yyyy-MM-dd");
        s_birthday_chooser.setTextRefernce(textField_S_birthday);

        setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setViewportView(null);

        registrationPanel.setLayout(new java.awt.GridLayout(1, 0));

        administratorTabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                administratorTabPanelMouseClicked(evt);
            }
        });

        a_textField_lastName.setText("  ");

        a_comboBox_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        a_printApplication_btn.setBackground(new java.awt.Color(52, 127, 73));
        a_printApplication_btn.setForeground(new java.awt.Color(255, 255, 255));
        a_printApplication_btn.setText("Print Application");
        a_printApplication_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_printApplication_btnActionPerformed(evt);
            }
        });

        personalDetails_lbl1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        personalDetails_lbl1.setText("Personal Details");

        a_textField_mobile.setText("  ");

        a_birthday.setEnabled(false);

        email_lbl2.setText("Gender");

        birthday_lbl1.setText("Birthday");

        surname_lbl2.setText("Surname");

        firstName_lbl2.setText("First Name");

        a_comboBox_surname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        a_textField_nic.setText("  ");

        a_textField_firstName.setText("  ");

        nic_lbl2.setText("NIC");

        lastName_lbl2.setText("Last Name");

        a_textField_email.setText("  ");

        email_lbl3.setText("Email");

        mobile_lbl1.setText("Mobile");

        addressLine1_lbl1.setText("Address Line 1");

        a_textField_addressLine1.setText("  ");

        a_textField_addressLine2.setText("  ");

        addressLine2_lbl1.setText("Address Line 2");

        a_ec2_mobile_TextField.setText("  ");

        eC2Mobile_lbl1.setText("Mobile");

        a_ec1_name_TextField.setText("  ");

        eCName_lbl1.setText("Name");

        emergencyContact_lbl1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        emergencyContact_lbl1.setText("Emergency Contact");

        a_ec1_mobile_TextField.setText("  ");

        eCM_lbl1.setText("Mobile");

        contact1_lbl1.setText("Contact 1 :");

        contact2_lbl1.setText("Contact 2 :");

        a_ec2_name_TextField.setText("  ");

        eC2TextField1.setText("Name");

        academicQalification_lbl1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        academicQalification_lbl1.setText("Other Qualifications");

        qualification_lbl1.setText("Qualifications");

        a_qualification_textArea.setColumns(20);
        a_qualification_textArea.setRows(5);
        jScrollPane4.setViewportView(a_qualification_textArea);

        a_submit_btn.setBackground(new java.awt.Color(0, 153, 51));
        a_submit_btn.setForeground(new java.awt.Color(255, 255, 255));
        a_submit_btn.setText("Submit");
        a_submit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_submit_btnActionPerformed(evt);
            }
        });

        a_reset_btn.setBackground(new java.awt.Color(153, 0, 0));
        a_reset_btn.setForeground(new java.awt.Color(255, 255, 255));
        a_reset_btn.setText("Reset");
        a_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_reset_btnActionPerformed(evt);
            }
        });

        selectProfilePic_btn.setBackground(new java.awt.Color(69, 70, 200));
        selectProfilePic_btn.setForeground(new java.awt.Color(255, 255, 255));
        selectProfilePic_btn.setText("Select Profile Picture");
        selectProfilePic_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectProfilePic_btnActionPerformed(evt);
            }
        });

        a_date_popup_btn.setBackground(new java.awt.Color(0, 102, 102));
        a_date_popup_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a_date_popup_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout administratorTabPanelLayout = new javax.swing.GroupLayout(administratorTabPanel);
        administratorTabPanel.setLayout(administratorTabPanelLayout);
        administratorTabPanelLayout.setHorizontalGroup(
            administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorTabPanelLayout.createSequentialGroup()
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a_reset_btn)
                        .addGap(18, 18, 18)
                        .addComponent(a_submit_btn))
                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addComponent(personalDetails_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selectProfilePic_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(a_printApplication_btn))
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(a_textField_addressLine1)
                                        .addGap(18, 18, 18))
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(addressLine1_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(238, 238, 238)))
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(addressLine2_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(221, 221, 221))
                                    .addComponent(a_textField_addressLine2)))
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(surname_lbl2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(a_comboBox_surname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(a_textField_nic)
                                    .addComponent(nic_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(firstName_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(103, 103, 103))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(a_textField_firstName)
                                        .addGap(18, 18, 18)))
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(lastName_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(86, 86, 86))
                                    .addComponent(a_textField_lastName)))
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(email_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(a_textField_email))
                                .addGap(12, 12, 12)
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(a_comboBox_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(email_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(mobile_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                        .addComponent(a_textField_mobile)
                                        .addGap(18, 18, 18)))
                                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(a_birthday)
                                    .addComponent(birthday_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(a_date_popup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addComponent(qualification_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addGap(556, 556, 556))
                            .addComponent(jScrollPane4)
                            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                .addComponent(academicQalification_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(452, 452, 452)))))
                .addGap(43, 43, 43))
            .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                            .addComponent(emergencyContact_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(501, 501, 501))
                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                            .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(a_ec1_name_TextField)
                                            .addGap(18, 18, 18))
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(eCName_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(114, 114, 114)))
                                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(a_ec1_mobile_TextField)
                                            .addGap(65, 65, 65))
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(eCM_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(157, 157, 157))))
                                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                    .addComponent(contact1_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(297, 297, 297)))
                            .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                    .addComponent(contact2_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(218, 218, 218))
                                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(a_ec2_name_TextField)
                                            .addGap(18, 18, 18))
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(eC2TextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(113, 113, 113)))
                                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                            .addComponent(eC2Mobile_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(90, 90, 90))
                                        .addComponent(a_ec2_mobile_TextField))))))
                    .addGap(40, 40, 40)))
        );
        administratorTabPanelLayout.setVerticalGroup(
            administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administratorTabPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalDetails_lbl1)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(a_printApplication_btn)
                        .addComponent(selectProfilePic_btn)))
                .addGap(26, 26, 26)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(surname_lbl2)
                    .addComponent(nic_lbl2)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(firstName_lbl2)
                        .addComponent(lastName_lbl2)))
                .addGap(6, 6, 6)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(a_comboBox_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a_textField_nic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a_textField_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a_textField_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(administratorTabPanelLayout.createSequentialGroup()
                        .addComponent(birthday_lbl1)
                        .addGap(6, 6, 6)
                        .addComponent(a_birthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                            .addComponent(mobile_lbl1)
                            .addGap(6, 6, 6)
                            .addComponent(a_textField_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                            .addComponent(email_lbl3)
                            .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(a_textField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, administratorTabPanelLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(a_date_popup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2))))
                        .addGroup(administratorTabPanelLayout.createSequentialGroup()
                            .addComponent(email_lbl2)
                            .addGap(6, 6, 6)
                            .addComponent(a_comboBox_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addressLine1_lbl1)
                    .addComponent(addressLine2_lbl1))
                .addGap(6, 6, 6)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(a_textField_addressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a_textField_addressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                .addComponent(academicQalification_lbl1)
                .addGap(18, 18, 18)
                .addComponent(qualification_lbl1)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a_reset_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(a_submit_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
            .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(administratorTabPanelLayout.createSequentialGroup()
                    .addGap(315, 315, 315)
                    .addComponent(emergencyContact_lbl1)
                    .addGap(18, 18, 18)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(contact1_lbl1)
                        .addComponent(contact2_lbl1))
                    .addGap(12, 12, 12)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eCName_lbl1)
                            .addComponent(eCM_lbl1))
                        .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eC2TextField1)
                            .addComponent(eC2Mobile_lbl1)))
                    .addGap(6, 6, 6)
                    .addGroup(administratorTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(a_ec1_name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(a_ec1_mobile_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(a_ec2_name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(a_ec2_mobile_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(318, Short.MAX_VALUE)))
        );

        tabPanel.addTab("Administrator", administratorTabPanel);

        teacherTabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teacherTabPanelMouseClicked(evt);
            }
        });

        qualification_lbl.setText("Qualifications");

        t_qualification_textArea.setColumns(20);
        t_qualification_textArea.setRows(5);
        jScrollPane2.setViewportView(t_qualification_textArea);

        t_reset_btn.setBackground(new java.awt.Color(153, 0, 0));
        t_reset_btn.setForeground(new java.awt.Color(255, 255, 255));
        t_reset_btn.setText("Reset");
        t_reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_reset_btnActionPerformed(evt);
            }
        });

        t_submit_btn.setBackground(new java.awt.Color(0, 153, 51));
        t_submit_btn.setForeground(new java.awt.Color(255, 255, 255));
        t_submit_btn.setText("Submit");
        t_submit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_submit_btnActionPerformed(evt);
            }
        });

        academicQalification_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        academicQalification_lbl.setText("Academic Qualifications");

        contact1_lbl.setText("Contact 1 :");

        contact2_lbl.setText("Contact 2 :");

        t_ecn2_name_textfield.setText("  ");

        eC2TextField.setText("Name");

        t_ecn2_mobile_textfield.setText("  ");

        eC2Mobile_lbl.setText("Mobile");

        t_ecn1_name_textfield.setText("  ");

        eCName_lbl.setText("Name");

        emergencyContact_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        emergencyContact_lbl.setText("Emergency Contact");

        t_ecn1_mobile_textfield.setText("  ");

        eCM_lbl.setText("Mobile");

        birthday_lbl.setText("Birthday");

        surname_lbl.setText("Surname");

        firstName_lbl.setText("First Name");

        t_comboBox_surname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        t_textField_nic.setText("  ");

        t_textField_firstName.setText("  ");

        nic_lbl.setText("NIC");

        lastName_lbl.setText("Last Name");

        t_textField_email.setText("  ");

        email_lbl.setText("Email");

        mobile_lbl.setText("Mobile");

        addressLine1_lbl.setText("Address Line 1");

        t_textField_addressLine1.setText("  ");

        t_textField_addressLine2.setText("  ");

        addressLine2_lbl.setText("Address Line 2");

        t_textField_lastName.setText("  ");

        t_comboBox_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        t_comboBox_gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_comboBox_genderActionPerformed(evt);
            }
        });

        t_printApplication_btn.setBackground(new java.awt.Color(52, 127, 73));
        t_printApplication_btn.setForeground(new java.awt.Color(255, 255, 255));
        t_printApplication_btn.setText("Print Application");
        t_printApplication_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_printApplication_btnActionPerformed(evt);
            }
        });

        personalDetails_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        personalDetails_lbl.setText("Personal Details");

        t_mobile_textfiled.setText("  ");

        birthday_T.setEditable(false);
        birthday_T.setEnabled(false);

        email_lbl1.setText("Gender");

        t_profilepic_btn.setBackground(new java.awt.Color(69, 70, 200));
        t_profilepic_btn.setForeground(new java.awt.Color(255, 255, 255));
        t_profilepic_btn.setText("Select Profile Picture");
        t_profilepic_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_profilepic_btnActionPerformed(evt);
            }
        });

        t_date_popup_btn.setBackground(new java.awt.Color(0, 102, 102));
        t_date_popup_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_date_popup_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout teacherTabPanelLayout = new javax.swing.GroupLayout(teacherTabPanel);
        teacherTabPanel.setLayout(teacherTabPanelLayout);
        teacherTabPanelLayout.setHorizontalGroup(
            teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teacherTabPanelLayout.createSequentialGroup()
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(t_reset_btn)
                        .addGap(18, 18, 18)
                        .addComponent(t_submit_btn))
                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addComponent(personalDetails_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(t_profilepic_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_printApplication_btn))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addComponent(qualification_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addGap(556, 556, 556))
                            .addComponent(jScrollPane2)
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addComponent(emergencyContact_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(501, 501, 501))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addComponent(academicQalification_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(452, 452, 452))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(t_textField_addressLine1)
                                        .addGap(18, 18, 18))
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(addressLine1_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(238, 238, 238)))
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(addressLine2_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(221, 221, 221))
                                    .addComponent(t_textField_addressLine2)))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(surname_lbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(t_comboBox_surname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(t_textField_nic)
                                    .addComponent(nic_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(firstName_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(103, 103, 103))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(t_textField_firstName)
                                        .addGap(18, 18, 18)))
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(lastName_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(86, 86, 86))
                                    .addComponent(t_textField_lastName)))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(email_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(t_textField_email))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t_comboBox_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(email_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(50, 50, 50)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t_mobile_textfiled)
                                    .addComponent(mobile_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(birthday_T)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(birthday_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(63, 63, 63)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_date_popup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(t_ecn1_name_textfield)
                                                .addGap(18, 18, 18))
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(eCName_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(114, 114, 114)))
                                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(t_ecn1_mobile_textfield)
                                                .addGap(65, 65, 65))
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(eCM_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(157, 157, 157))))
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(contact1_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(297, 297, 297)))
                                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addComponent(contact2_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(218, 218, 218))
                                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(t_ecn2_name_textfield)
                                                .addGap(18, 18, 18))
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(eC2TextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(113, 113, 113)))
                                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                                .addComponent(eC2Mobile_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(90, 90, 90))
                                            .addComponent(t_ecn2_mobile_textfield))))))))
                .addGap(36, 36, 36))
        );
        teacherTabPanelLayout.setVerticalGroup(
            teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalDetails_lbl)
                    .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(t_printApplication_btn)
                        .addComponent(t_profilepic_btn)))
                .addGap(26, 26, 26)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(surname_lbl)
                    .addComponent(nic_lbl)
                    .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(firstName_lbl)
                        .addComponent(lastName_lbl)))
                .addGap(6, 6, 6)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_comboBox_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_textField_nic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_textField_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_textField_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                        .addComponent(email_lbl)
                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(t_textField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teacherTabPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_date_popup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))))
                    .addGroup(teacherTabPanelLayout.createSequentialGroup()
                        .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(t_comboBox_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                .addComponent(email_lbl1)
                                .addGap(26, 26, 26))
                            .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                    .addComponent(birthday_lbl)
                                    .addGap(6, 6, 6)
                                    .addComponent(birthday_T, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(teacherTabPanelLayout.createSequentialGroup()
                                    .addComponent(mobile_lbl)
                                    .addGap(6, 6, 6)
                                    .addComponent(t_mobile_textfiled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(2, 2, 2)))
                .addGap(18, 18, 18)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addressLine1_lbl)
                    .addComponent(addressLine2_lbl))
                .addGap(6, 6, 6)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_textField_addressLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_textField_addressLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(emergencyContact_lbl)
                .addGap(18, 18, 18)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact1_lbl)
                    .addComponent(contact2_lbl))
                .addGap(12, 12, 12)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(eCName_lbl)
                        .addComponent(eCM_lbl))
                    .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(eC2TextField)
                        .addComponent(eC2Mobile_lbl)))
                .addGap(6, 6, 6)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_ecn1_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_ecn1_mobile_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_ecn2_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_ecn2_mobile_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addComponent(academicQalification_lbl)
                .addGap(18, 18, 18)
                .addComponent(qualification_lbl)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(teacherTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t_reset_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(t_submit_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        tabPanel.addTab("Teacher", teacherTabPanel);

        studentTabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTabPanelMouseClicked(evt);
            }
        });

        s_textField_email.setText("  ");

        email_S_lbl.setText("Email");

        mobile_S_lbl.setText("Mobile");

        s_textField_mobile.setText("  ");

        addressLine1_S_lbl.setText("Address Line 1");

        textField_S_addressLine1.setText("  ");

        textField_S_addressLine2.setText("  ");

        addressLine2_S_lbl.setText("Address Line 2");

        gender_S_lbl.setText("Gender");

        s_comboBox_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        textField_S_birthday.setEnabled(false);

        birthday_S_lbl.setText("Birthday");

        personalDetails_S_lbl1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        personalDetails_S_lbl1.setText("Personal Details");

        s_ecn1_name_textfield.setText("  ");

        eCName_S_lbl.setText("Name");

        emergencyContact_S_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        emergencyContact_S_lbl.setText("Emergency Contact");

        s_ecn1_mobile_textfield.setText("  ");

        eCM_S_lbl.setText("Mobile");

        academicQalification_S_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        academicQalification_S_lbl.setText("Joined Class");

        qualification_S_lbl.setText("Class :");

        contact1_S_lbl.setText("Contact 1 :");

        contact2_S_lbl.setText("Contact 2 :");

        s_ecn2_name_textfield.setText("  ");

        eC2TextField1S.setText("Name");

        s_ecn2_mobile_textfield.setText("  ");

        eC2Mobile_S_lbl.setText("Mobile");

        reset_S_btn.setBackground(new java.awt.Color(153, 0, 0));
        reset_S_btn.setForeground(new java.awt.Color(255, 255, 255));
        reset_S_btn.setText("Reset");
        reset_S_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_S_btnActionPerformed(evt);
            }
        });

        submit_S_btn.setBackground(new java.awt.Color(0, 153, 51));
        submit_S_btn.setForeground(new java.awt.Color(255, 255, 255));
        submit_S_btn.setText("Submit");
        submit_S_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_S_btnActionPerformed(evt);
            }
        });

        print_S_btn.setBackground(new java.awt.Color(52, 127, 73));
        print_S_btn.setForeground(new java.awt.Color(255, 255, 255));
        print_S_btn.setText("Print Application");
        print_S_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                print_S_btnActionPerformed(evt);
            }
        });

        surname_lbl1.setText("Surname");

        firstName_lbl1.setText("First Name");

        s_comboBox_surname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        s_textField_nic.setText("  ");

        s_textField_firstName.setText("  ");

        nic_lbl1.setText("NIC");

        lastName_lbl1.setText("Last Name");

        s_textField_lastName.setText("  ");

        s_profilepic_btn.setBackground(new java.awt.Color(69, 70, 200));
        s_profilepic_btn.setForeground(new java.awt.Color(255, 255, 255));
        s_profilepic_btn.setText("Select Profile Picture");
        s_profilepic_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_profilepic_btnActionPerformed(evt);
            }
        });

        s_date_popup_btn.setBackground(new java.awt.Color(0, 102, 102));
        s_date_popup_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_date_popup_btnActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout studentTabPanelLayout = new javax.swing.GroupLayout(studentTabPanel);
        studentTabPanel.setLayout(studentTabPanelLayout);
        studentTabPanelLayout.setHorizontalGroup(
            studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(surname_lbl1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(s_comboBox_surname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(s_textField_nic)
                            .addComponent(nic_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(firstName_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(103, 103, 103))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(s_textField_firstName)
                                .addGap(18, 18, 18)))
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(lastName_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(42, 42, 42))
                            .addComponent(s_textField_lastName)))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(emergencyContact_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(501, 501, 501))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                                        .addComponent(eCName_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(80, 80, 80))
                                    .addComponent(s_ecn1_name_textfield))
                                .addGap(18, 18, 18)
                                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                                        .addComponent(eCM_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(114, 114, 114))
                                    .addComponent(s_ecn1_mobile_textfield))
                                .addGap(36, 36, 36))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(contact1_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(262, 262, 262)))
                        .addGap(29, 29, 29)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(contact2_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(226, 226, 226))
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                                        .addComponent(eC2TextField1S, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(80, 80, 80))
                                    .addComponent(s_ecn2_name_textfield))
                                .addGap(18, 18, 18)
                                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                                        .addComponent(eC2Mobile_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(114, 114, 114))
                                    .addComponent(s_ecn2_mobile_textfield)))))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(academicQalification_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(507, 507, 507))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(addressLine1_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(223, 223, 223))
                            .addComponent(textField_S_addressLine1))
                        .addGap(18, 18, 18)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(addressLine2_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(234, 234, 234))
                            .addComponent(textField_S_addressLine2)))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(email_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addGap(188, 188, 188))
                            .addComponent(s_textField_email, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                        .addGap(14, 14, 14)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(gender_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                .addGap(59, 59, 59))
                            .addComponent(s_comboBox_gender, 0, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(mobile_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                .addGap(86, 86, 86))
                            .addComponent(s_textField_mobile, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentTabPanelLayout.createSequentialGroup()
                                .addComponent(birthday_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                .addGap(84, 84, 84))
                            .addComponent(textField_S_birthday, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addComponent(s_date_popup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(personalDetails_S_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGap(259, 259, 259)
                        .addComponent(s_profilepic_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(print_S_btn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(reset_S_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(submit_S_btn))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(qualification_S_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(47, 47, 47))
        );
        studentTabPanelLayout.setVerticalGroup(
            studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentTabPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(personalDetails_S_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(print_S_btn)
                        .addComponent(s_profilepic_btn)))
                .addGap(18, 18, 18)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(surname_lbl1)
                    .addComponent(nic_lbl1)
                    .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(firstName_lbl1)
                        .addComponent(lastName_lbl1)))
                .addGap(6, 6, 6)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s_comboBox_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s_textField_nic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s_textField_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s_textField_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(gender_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s_comboBox_gender))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(mobile_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s_textField_mobile))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(birthday_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textField_S_birthday)
                            .addComponent(s_date_popup_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(email_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s_textField_email)))
                .addGap(18, 18, 18)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                        .addComponent(addressLine1_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField_S_addressLine1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                        .addComponent(addressLine2_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField_S_addressLine2)))
                .addGap(59, 59, 59)
                .addComponent(emergencyContact_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(contact1_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(eCName_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_ecn1_name_textfield, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(eCM_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_ecn1_mobile_textfield))))
                    .addGroup(studentTabPanelLayout.createSequentialGroup()
                        .addComponent(contact2_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(eC2TextField1S, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_ecn2_name_textfield))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTabPanelLayout.createSequentialGroup()
                                .addComponent(eC2Mobile_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_ecn2_mobile_textfield)))))
                .addGap(62, 62, 62)
                .addComponent(academicQalification_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qualification_S_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(115, 115, 115)
                .addGroup(studentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submit_S_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reset_S_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );

        tabPanel.addTab("Student", studentTabPanel);

        registrationPanel.add(tabPanel);

        jScrollPane1.setViewportView(registrationPanel);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void t_printApplication_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_printApplication_btnActionPerformed


    }//GEN-LAST:event_t_printApplication_btnActionPerformed

    private void print_S_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_print_S_btnActionPerformed


    }//GEN-LAST:event_print_S_btnActionPerformed

    private void a_printApplication_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_printApplication_btnActionPerformed


    }//GEN-LAST:event_a_printApplication_btnActionPerformed

    private void selectProfilePic_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectProfilePic_btnActionPerformed
        selectImage();
    }//GEN-LAST:event_selectProfilePic_btnActionPerformed

    private void t_profilepic_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_profilepic_btnActionPerformed
        selectImage();
    }//GEN-LAST:event_t_profilepic_btnActionPerformed

    private void s_profilepic_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_profilepic_btnActionPerformed
        selectImage();
    }//GEN-LAST:event_s_profilepic_btnActionPerformed

    private void a_submit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_submit_btnActionPerformed

        int surnameID = surnameMap.get(a_comboBox_surname.getSelectedItem());
        int genderID = genderMap.get(a_comboBox_gender.getSelectedItem());

        String nic = a_textField_nic.getText();
        String firstName = a_textField_firstName.getText();
        String lastName = a_textField_lastName.getText();
        String email = a_textField_email.getText();
        String mobile = a_textField_mobile.getText();
        String birthday = a_birthday.getText();
        String line1 = a_textField_addressLine1.getText();
        String line2 = a_textField_addressLine2.getText();

        String ec1name = a_ec1_name_TextField.getText();
        String ec1mobile = a_ec1_mobile_TextField.getText();
        String ec2name = a_ec2_name_TextField.getText();
        String ec2mobile = a_ec2_mobile_TextField.getText();

        String qualifications = a_qualification_textArea.getText();

        submitVerification(genderID, surnameID, nic, firstName, lastName, email, mobile, birthday, line1, line2, ec1name, ec1mobile, ec2name, ec2mobile, qualifications);

    }//GEN-LAST:event_a_submit_btnActionPerformed

    private void a_date_popup_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_date_popup_btnActionPerformed
        a_birthday_chooser.showPopup();
    }//GEN-LAST:event_a_date_popup_btnActionPerformed

    private void a_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a_reset_btnActionPerformed
        clearAdministrator();
    }//GEN-LAST:event_a_reset_btnActionPerformed

    private void t_date_popup_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_date_popup_btnActionPerformed
        t_birthday_chooser.showPopup();
    }//GEN-LAST:event_t_date_popup_btnActionPerformed

    private void t_comboBox_genderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_comboBox_genderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_comboBox_genderActionPerformed

    private void t_submit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_submit_btnActionPerformed

        int surnameID = surnameMap.get(t_comboBox_surname.getSelectedItem());
        int genderID = genderMap.get(t_comboBox_gender.getSelectedItem());

        String nic = t_textField_nic.getText();
        String firstName = t_textField_firstName.getText();
        String lastName = t_textField_lastName.getText();
        String email = t_textField_email.getText();
        String mobile = t_mobile_textfiled.getText();
        String birthday = birthday_T.getText();
        String line1 = t_textField_addressLine1.getText();
        String line2 = t_textField_addressLine2.getText();

        String ec1name = t_ecn1_name_textfield.getText();
        String ec1mobile = t_ecn1_mobile_textfield.getText();
        String ec2name = t_ecn2_name_textfield.getText();
        String ec2mobile = t_ecn2_mobile_textfield.getText();

        String qualifications = t_qualification_textArea.getText();

        submitVerification(genderID, surnameID, nic, firstName, lastName, email, mobile, birthday, line1, line2, ec1name, ec1mobile, ec2name, ec2mobile, qualifications);

    }//GEN-LAST:event_t_submit_btnActionPerformed

    private void t_reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_reset_btnActionPerformed
        clearTeacher();
    }//GEN-LAST:event_t_reset_btnActionPerformed

    private void administratorTabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administratorTabPanelMouseClicked

        isAdministratorPanel = true;
        isTeacherPanel = false;
        isStudentPanel = false;

    }//GEN-LAST:event_administratorTabPanelMouseClicked

    private void teacherTabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teacherTabPanelMouseClicked

        isAdministratorPanel = false;
        isTeacherPanel = true;
        isStudentPanel = false;

    }//GEN-LAST:event_teacherTabPanelMouseClicked

    private void studentTabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTabPanelMouseClicked

        isAdministratorPanel = false;
        isTeacherPanel = false;
        isStudentPanel = true;

    }//GEN-LAST:event_studentTabPanelMouseClicked

    private void s_date_popup_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_date_popup_btnActionPerformed
        s_birthday_chooser.showPopup();
    }//GEN-LAST:event_s_date_popup_btnActionPerformed

    private void reset_S_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_S_btnActionPerformed
        clearStudent();
    }//GEN-LAST:event_reset_S_btnActionPerformed

    private void submit_S_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_S_btnActionPerformed

        int surnameID = surnameMap.get(s_comboBox_surname.getSelectedItem());
        int genderID = genderMap.get(s_comboBox_gender.getSelectedItem());

        String nic = s_textField_nic.getText();
        String firstName = s_textField_firstName.getText();
        String lastName = s_textField_lastName.getText();
        String email = s_textField_email.getText();
        String mobile = s_textField_mobile.getText();
        String birthday = textField_S_birthday.getText();
        String line1 = textField_S_addressLine1.getText();
        String line2 = textField_S_addressLine2.getText();

        String ec1name = s_ecn1_name_textfield.getText();
        String ec1mobile = s_ecn1_mobile_textfield.getText();
        String ec2name = s_ecn2_name_textfield.getText();
        String ec2mobile = s_ecn2_mobile_textfield.getText();

        String classes = String.valueOf(jComboBox1.getSelectedIndex());
        if (!classes.equals("0")) {
            subjectId = subject.get(jComboBox1.getSelectedItem());
        }

        submitVerification(genderID, surnameID, nic, firstName, lastName, email, mobile, birthday, line1, line2, ec1name, ec1mobile, ec2name, ec2mobile, classes);

    }//GEN-LAST:event_submit_S_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField a_birthday;
    private com.raven.datechooser.DateChooser a_birthday_chooser;
    private javax.swing.JComboBox<String> a_comboBox_gender;
    private javax.swing.JComboBox<String> a_comboBox_surname;
    private javax.swing.JButton a_date_popup_btn;
    private javax.swing.JTextField a_ec1_mobile_TextField;
    private javax.swing.JTextField a_ec1_name_TextField;
    private javax.swing.JTextField a_ec2_mobile_TextField;
    private javax.swing.JTextField a_ec2_name_TextField;
    private javax.swing.JButton a_printApplication_btn;
    private javax.swing.JTextArea a_qualification_textArea;
    private javax.swing.JButton a_reset_btn;
    private javax.swing.JButton a_submit_btn;
    private javax.swing.JTextField a_textField_addressLine1;
    private javax.swing.JTextField a_textField_addressLine2;
    private javax.swing.JTextField a_textField_email;
    private javax.swing.JTextField a_textField_firstName;
    private javax.swing.JTextField a_textField_lastName;
    private javax.swing.JTextField a_textField_mobile;
    private javax.swing.JTextField a_textField_nic;
    private javax.swing.JLabel academicQalification_S_lbl;
    private javax.swing.JLabel academicQalification_lbl;
    private javax.swing.JLabel academicQalification_lbl1;
    private javax.swing.JLabel addressLine1_S_lbl;
    private javax.swing.JLabel addressLine1_lbl;
    private javax.swing.JLabel addressLine1_lbl1;
    private javax.swing.JLabel addressLine2_S_lbl;
    private javax.swing.JLabel addressLine2_lbl;
    private javax.swing.JLabel addressLine2_lbl1;
    private javax.swing.JPanel administratorTabPanel;
    private javax.swing.JLabel birthday_S_lbl;
    private javax.swing.JTextField birthday_T;
    private javax.swing.JLabel birthday_lbl;
    private javax.swing.JLabel birthday_lbl1;
    private javax.swing.JLabel contact1_S_lbl;
    private javax.swing.JLabel contact1_lbl;
    private javax.swing.JLabel contact1_lbl1;
    private javax.swing.JLabel contact2_S_lbl;
    private javax.swing.JLabel contact2_lbl;
    private javax.swing.JLabel contact2_lbl1;
    private javax.swing.JLabel eC2Mobile_S_lbl;
    private javax.swing.JLabel eC2Mobile_lbl;
    private javax.swing.JLabel eC2Mobile_lbl1;
    private javax.swing.JLabel eC2TextField;
    private javax.swing.JLabel eC2TextField1;
    private javax.swing.JLabel eC2TextField1S;
    private javax.swing.JLabel eCM_S_lbl;
    private javax.swing.JLabel eCM_lbl;
    private javax.swing.JLabel eCM_lbl1;
    private javax.swing.JLabel eCName_S_lbl;
    private javax.swing.JLabel eCName_lbl;
    private javax.swing.JLabel eCName_lbl1;
    private javax.swing.JLabel email_S_lbl;
    private javax.swing.JLabel email_lbl;
    private javax.swing.JLabel email_lbl1;
    private javax.swing.JLabel email_lbl2;
    private javax.swing.JLabel email_lbl3;
    private javax.swing.JLabel emergencyContact_S_lbl;
    private javax.swing.JLabel emergencyContact_lbl;
    private javax.swing.JLabel emergencyContact_lbl1;
    private javax.swing.JLabel firstName_lbl;
    private javax.swing.JLabel firstName_lbl1;
    private javax.swing.JLabel firstName_lbl2;
    private javax.swing.JLabel gender_S_lbl;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lastName_lbl;
    private javax.swing.JLabel lastName_lbl1;
    private javax.swing.JLabel lastName_lbl2;
    private javax.swing.JLabel mobile_S_lbl;
    private javax.swing.JLabel mobile_lbl;
    private javax.swing.JLabel mobile_lbl1;
    private javax.swing.JLabel nic_lbl;
    private javax.swing.JLabel nic_lbl1;
    private javax.swing.JLabel nic_lbl2;
    private javax.swing.JLabel personalDetails_S_lbl1;
    private javax.swing.JLabel personalDetails_lbl;
    private javax.swing.JLabel personalDetails_lbl1;
    private javax.swing.JButton print_S_btn;
    private javax.swing.JLabel qualification_S_lbl;
    private javax.swing.JLabel qualification_lbl;
    private javax.swing.JLabel qualification_lbl1;
    private javax.swing.JPanel registrationPanel;
    private javax.swing.JButton reset_S_btn;
    private com.raven.datechooser.DateChooser s_birthday_chooser;
    private javax.swing.JComboBox<String> s_comboBox_gender;
    private javax.swing.JComboBox<String> s_comboBox_surname;
    private javax.swing.JButton s_date_popup_btn;
    private javax.swing.JTextField s_ecn1_mobile_textfield;
    private javax.swing.JTextField s_ecn1_name_textfield;
    private javax.swing.JTextField s_ecn2_mobile_textfield;
    private javax.swing.JTextField s_ecn2_name_textfield;
    private javax.swing.JButton s_profilepic_btn;
    private javax.swing.JTextField s_textField_email;
    private javax.swing.JTextField s_textField_firstName;
    private javax.swing.JTextField s_textField_lastName;
    private javax.swing.JTextField s_textField_mobile;
    private javax.swing.JTextField s_textField_nic;
    private javax.swing.JButton selectProfilePic_btn;
    private javax.swing.JPanel studentTabPanel;
    private javax.swing.JButton submit_S_btn;
    private javax.swing.JLabel surname_lbl;
    private javax.swing.JLabel surname_lbl1;
    private javax.swing.JLabel surname_lbl2;
    private com.raven.datechooser.DateChooser t_birthday_chooser;
    private javax.swing.JComboBox<String> t_comboBox_gender;
    private javax.swing.JComboBox<String> t_comboBox_surname;
    private javax.swing.JButton t_date_popup_btn;
    private javax.swing.JTextField t_ecn1_mobile_textfield;
    private javax.swing.JTextField t_ecn1_name_textfield;
    private javax.swing.JTextField t_ecn2_mobile_textfield;
    private javax.swing.JTextField t_ecn2_name_textfield;
    private javax.swing.JTextField t_mobile_textfiled;
    private javax.swing.JButton t_printApplication_btn;
    private javax.swing.JButton t_profilepic_btn;
    private javax.swing.JTextArea t_qualification_textArea;
    private javax.swing.JButton t_reset_btn;
    private javax.swing.JButton t_submit_btn;
    private javax.swing.JTextField t_textField_addressLine1;
    private javax.swing.JTextField t_textField_addressLine2;
    private javax.swing.JTextField t_textField_email;
    private javax.swing.JTextField t_textField_firstName;
    private javax.swing.JTextField t_textField_lastName;
    private javax.swing.JTextField t_textField_nic;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel teacherTabPanel;
    private javax.swing.JTextField textField_S_addressLine1;
    private javax.swing.JTextField textField_S_addressLine2;
    private javax.swing.JTextField textField_S_birthday;
    // End of variables declaration//GEN-END:variables
}

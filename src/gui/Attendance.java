package gui;

import model.MySQL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.AttendanceClass;
import model.AttendanceSearchContact;

public class Attendance extends javax.swing.JPanel {

    private HashMap<String, AttendanceClass> student = new HashMap<>();
    private int selectedRow;
    private String studentId;
    private HashMap<String, String> subject = new HashMap<>();
    private String studentClassId;
    private HashMap<String, AttendanceSearchContact> contact = new HashMap<>();

    public Attendance() {
        initComponents();
        setAttendanceMarkUp();
    }

    private void setAttendanceMarkUp() {
        DefaultTableModel dtm = (DefaultTableModel) att_table.getModel();
        dtm.setRowCount(0);
        att_table.setModel(dtm);
        student.clear();
        jPanel3.setVisible(false);
    }

    private void setAttendanceSearchUp() {
        DefaultTableModel dtm = (DefaultTableModel) searchtable.getModel();
        dtm.setRowCount(0);
        searchtable.setModel(dtm);

        try {

            ResultSet subrs = MySQL.executeSearch("SELECT * FROM `subject` WHERE `subject`.`status_status_id`='1' AND `subject`.`subject_id`!='1' "
                    + "ORDER BY `subject`.`subject_id` ASC");

            Vector<String> v = new Vector<>();
            while (subrs.next()) {

                v.add(subrs.getString("subject"));

                if (!subject.containsKey(subrs.getString("subject"))) {
                    subject.put(subrs.getString("subject"), subrs.getString("subject_id"));
                }
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            selectcombobox.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        searchTextfield.setText("");
        selectcombobox.setSelectedIndex(0);
        jButton4.setVisible(false);
        jPanel4.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        s_id_textfield = new javax.swing.JTextField();
        atte_sid_se_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        att_table = new javax.swing.JTable();
        resetbtn = new javax.swing.JButton();
        mark_tday_aate_btn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchTextfield = new javax.swing.JTextField();
        search_btn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchtable = new javax.swing.JTable();
        selectcombobox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        reset_btn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel1.setText("Student ID :");

        s_id_textfield.setText("  ");

        atte_sid_se_btn.setText("Search");
        atte_sid_se_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atte_sid_se_btnActionPerformed(evt);
            }
        });

        att_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Name", "Subject", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        att_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(att_table);
        if (att_table.getColumnModel().getColumnCount() > 0) {
            att_table.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        resetbtn.setText("Reset");
        resetbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetbtnActionPerformed(evt);
            }
        });

        mark_tday_aate_btn.setText("Mark Today Attendance");
        mark_tday_aate_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mark_tday_aate_btnActionPerformed(evt);
            }
        });

        jLabel10.setText("Start At :");

        jLabel11.setText("To At :");

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel12");

        jLabel14.setText("jLabel12");

        jLabel15.setText("Lecturer Mobile :");

        jLabel16.setText("jLabel12");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel8.setText("Additional Details :");

        jLabel9.setText("Lecturer :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addGap(61, 61, 61))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(atte_sid_se_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mark_tday_aate_btn)))
                        .addGap(44, 44, 44))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(atte_sid_se_btn)
                    .addComponent(resetbtn)
                    .addComponent(mark_tday_aate_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mark Attendance", jPanel1);

        jLabel2.setText("Student ID :");

        searchTextfield.setText("   ");

        search_btn.setText("Search");
        search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_btnActionPerformed(evt);
            }
        });

        searchtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Attendance Date", "Student", "Subject", "Class", "Lecturer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchtableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(searchtable);

        selectcombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Select Subject :");

        reset_btn.setText("Reset");
        reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_btnActionPerformed(evt);
            }
        });

        jButton4.setText("Print Report");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel17.setText("Aditional Details");

        jLabel4.setText("Lecturer Mobile :");

        jLabel5.setText("Student Mobile :");

        jLabel6.setText("jLabel6");

        jLabel7.setText("jLabel6");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel17))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(62, 62, 62)
                                        .addComponent(jLabel3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(searchTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(selectcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(28, 28, 28))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_btn)
                    .addComponent(selectcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reset_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Search", jPanel2);

        jScrollPane1.setViewportView(jTabbedPane1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_btnActionPerformed

        setAttendanceSearchUp();

    }//GEN-LAST:event_reset_btnActionPerformed

    private void atte_sid_se_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atte_sid_se_btnActionPerformed

        if (!s_id_textfield.getText().isBlank()) {

            String sid = s_id_textfield.getText();
            try {
                String q = "SELECT * FROM `student` "
                        + "INNER JOIN `surname` ON `student`.`surname_surname_id`=`surname`.`surname_id` "
                        + "INNER JOIN `subject` ON `subject`.`subject_id`=`student`.`subject_subject_id` "
                        + "INNER JOIN `teacher` ON `teacher`.`subject_subject_id`=`subject`.`subject_id` "
                        + "INNER JOIN `class` ON `class`.`class_id`=`subject`.`class_class_id` "
                        + "WHERE `student`.`student_id`='" + sid + "'";

                ResultSet ars = MySQL.executeSearch(q);

                DefaultTableModel dtm = (DefaultTableModel) att_table.getModel();
                dtm.setRowCount(0);

                while (ars.next()) {

                    Vector<String> v = new Vector<>();
                    v.add(ars.getString("student_id"));
                    v.add(ars.getString("surname") + " " + ars.getString("first_name") + " " + ars.getString("last_name"));
                    v.add(ars.getString("subject"));
                    v.add(ars.getString("class_name"));

                    if (!student.containsKey(ars.getString("student_id"))) {
                        AttendanceClass attendance = new AttendanceClass();
                        attendance.setsId(ars.getString("student_id"));
                        attendance.setClassId(ars.getString("class_id"));
                        attendance.setSubjectId(ars.getString("subject_id"));
                        attendance.setTeacher(ars.getString("surname") + " " + ars.getString("teacher.first_name") + " " + ars.getString("teacher.last_name"));
                        attendance.setToAt(ars.getString("to"));
                        attendance.setStartAt(ars.getString("start"));
                        attendance.setTeacherMobile(ars.getString("teacher.mobile"));

                        student.put(ars.getString("student_id"), attendance);
                    }

                    dtm.addRow(v);

                }

                att_table.setModel(dtm);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_atte_sid_se_btnActionPerformed

    private void resetbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetbtnActionPerformed

        setAttendanceMarkUp();

    }//GEN-LAST:event_resetbtnActionPerformed

    private void att_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_tableMouseClicked

        jPanel3.setVisible(true);

        selectedRow = att_table.getSelectedRow();
        studentId = String.valueOf(att_table.getValueAt(selectedRow, 0));

        jLabel12.setText(student.get(studentId).getTeacher());
        jLabel16.setText(student.get(studentId).getTeacherMobile());
        jLabel14.setText(student.get(studentId).getStartAt());
        jLabel13.setText(student.get(studentId).getToAt());

    }//GEN-LAST:event_att_tableMouseClicked

    private void mark_tday_aate_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mark_tday_aate_btnActionPerformed

        try {

            int option = JOptionPane.showConfirmDialog(this, "Mark The Attendance ?", "Confirm", JOptionPane.YES_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                MySQL.executeIUD("INSERT INTO `attendance`(`class_class_id`,`subject_subject_id`,`student_student_id`) "
                        + "VALUES('" + student.get(studentId).getClassId() + "','" + student.get(studentId).getSubjectId() + "','" + student.get(studentId).getsId() + "')");

                JOptionPane.showMessageDialog(this, "Attendance Marking Success", "Success", JOptionPane.INFORMATION_MESSAGE);

            }
            setAttendanceMarkUp();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_mark_tday_aate_btnActionPerformed

    private void search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_btnActionPerformed

        if (!searchTextfield.getText().isBlank()) {

            studentId = searchTextfield.getText();
            studentClassId = subject.get(String.valueOf(selectcombobox.getSelectedItem()));

            try {

                String q = "SELECT * FROM `attendance` "
                        + "INNER JOIN `student` ON `student`.`student_id`=`attendance`.`student_student_id` "
                        + "INNER JOIN `class` ON `class`.`class_id`=`attendance`.`class_class_id` "
                        + "INNER JOIN `subject` ON `subject`.`subject_id`=`attendance`.`subject_subject_id` "
                        + "INNER JOIN `teacher` ON `subject`.`subject_id`=`teacher`.`subject_subject_id` "
                        + "WHERE `attendance`.`student_student_id`='" + studentId + "' AND `attendance`.`subject_subject_id`='" + studentClassId + "'";

                ResultSet rs = MySQL.executeSearch(q);

                DefaultTableModel dtm = (DefaultTableModel) searchtable.getModel();
                dtm.setRowCount(0);

                while (rs.next()) {

                    Vector<String> v = new Vector<>();
                    v.add(rs.getString("atendance.student_student_id"));
                    v.add(rs.getString("atendance.date_time"));
                    v.add(rs.getString("atendance.date_time"));
                    v.add(rs.getString("student.first_name") + " " + rs.getString("student.last_name"));
                    v.add(rs.getString("subject"));
                    v.add(rs.getString("class_name"));
                    v.add(rs.getString("teacher.first_name") + " " + rs.getString("teacher.last_name"));

                    if (!contact.containsKey(rs.getString("atendance.student_student_id"))) {

                        AttendanceSearchContact searchContact = new AttendanceSearchContact();
                        searchContact.setsId(rs.getString("atendance.student_student_id"));
                        searchContact.settId(rs.getString("teacher.teacher_id"));
                        searchContact.setsMobile(rs.getString("student.mobile"));
                        searchContact.settMobile(rs.getString("teacher.mobile"));

                        contact.put(rs.getString("atendance.student_student_id"), searchContact);

                    }
                    dtm.addRow(v);
                }
                searchtable.setModel(dtm);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Insert Student Id First", "Waning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_search_btnActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

        setAttendanceMarkUp();
        setAttendanceSearchUp();

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void searchtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchtableMouseClicked

        jPanel4.setVisible(true);

        selectedRow = searchtable.getSelectedRow();
        studentId = String.valueOf(searchtable.getValueAt(selectedRow, 0));

        jLabel6.setText(contact.get(studentId).gettMobile());
        jLabel7.setText(contact.get(studentId).getsMobile());

    }//GEN-LAST:event_searchtableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable att_table;
    private javax.swing.JButton atte_sid_se_btn;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton mark_tday_aate_btn;
    private javax.swing.JButton reset_btn;
    private javax.swing.JButton resetbtn;
    private javax.swing.JTextField s_id_textfield;
    private javax.swing.JTextField searchTextfield;
    private javax.swing.JButton search_btn;
    private javax.swing.JTable searchtable;
    private javax.swing.JComboBox<String> selectcombobox;
    // End of variables declaration//GEN-END:variables
}

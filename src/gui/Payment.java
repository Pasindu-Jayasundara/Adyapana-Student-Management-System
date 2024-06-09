package gui;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import model.PaymentClass;

public class Payment extends javax.swing.JPanel {

    private HashMap<String, PaymentClass> student = new HashMap<>();
    private int selectedRow;
    private String studentId;

    public Payment() {
        initComponents();
        setAddPaymentUp();
    }

    private void setAddPaymentUp() {
        DefaultTableModel dtm = (DefaultTableModel) add_payment_table.getModel();
        dtm.setRowCount(0);
        add_payment_table.setModel(dtm);
        student.clear();
        jPanel1.setVisible(false);
    }
    
    private void setSearchSearchUp(){
        DefaultTableModel dtm = (DefaultTableModel) search_paymeny_table.getModel();
        dtm.setRowCount(0);
        search_paymeny_table.setModel(dtm);
        jButton2.setVisible(false);
        searchTextfield.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        s_id_textfield = new javax.swing.JTextField();
        searchpayment = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        add_payment_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        addpaymentbtn = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        search_paymeny_table = new javax.swing.JTable();
        searchTextfield = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        serchpaymentbtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel9.setText("Student ID :");

        searchpayment.setText("Search");
        searchpayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchpaymentActionPerformed(evt);
            }
        });

        add_payment_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "NIC", "Subject", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        add_payment_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_payment_tableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(add_payment_table);

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel16.setText("Student ID :");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel17.setText("Subject :");

        jLabel12.setForeground(new java.awt.Color(86, 115, 180));
        jLabel12.setText("Rs. 20000 .00");

        jLabel11.setForeground(new java.awt.Color(86, 115, 180));
        jLabel11.setText("Mathematics");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel18.setText("Price :");

        addpaymentbtn.setText("Add Payment ");
        addpaymentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addpaymentbtnActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel19.setText("Paid Amount :");

        jLabel20.setText("Rs.");

        jLabel10.setForeground(new java.awt.Color(86, 115, 180));
        jLabel10.setText("00215f112 5");

        jButton1.setText("Print Receipt");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addpaymentbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel17)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel11)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17)
                    .addComponent(jLabel11)
                    .addComponent(jLabel18)
                    .addComponent(jLabel12))
                .addGap(27, 27, 27)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addpaymentbtn)
                    .addComponent(jButton1))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Status :");

        jLabel2.setForeground(new java.awt.Color(0, 255, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel9)
                        .addGap(9, 9, 9)
                        .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchpayment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(s_id_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchpayment)
                        .addComponent(jLabel1))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );

        jTabbedPane1.addTab("Add Payment", jPanel4);

        search_paymeny_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Month", "Subject", "Lecturer", "Paid Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(search_paymeny_table);

        searchTextfield.setText(" ");

        jLabel5.setText("Student Id :");

        serchpaymentbtn.setText("Search");
        serchpaymentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchpaymentbtnActionPerformed(evt);
            }
        });

        jButton2.setText("Print Table");

        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                        .addGap(435, 435, 435))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(searchTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serchpaymentbtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serchpaymentbtn)
                    .addComponent(jButton3))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Search", jPanel3);

        jScrollPane1.setViewportView(jTabbedPane1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void serchpaymentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchpaymentbtnActionPerformed
        
        if (!searchTextfield.getText().isBlank()) {

            studentId = searchTextfield.getText();
            try {

                String q = "SELECT * FROM `invoice` "
                        + "INNER JOIN `student` ON `student`.`student_id`=`invoice`.`student_student_id` "
                        + "INNER JOIN `class` ON `class`.`class_id`=`invoice`.`class_class_id` "
                        + "INNER JOIN `subject` ON `subject`.`subject_id`=`invoice`.`subject_subject_id` "
                        + "INNER JOIN `teacher` ON `subject`.`subject_id`=`teacher`.`subject_subject_id` "
                        + "WHERE `invoice`.`student_student_id`='" + studentId + "'";

                ResultSet rs = MySQL.executeSearch(q);

                DefaultTableModel dtm = (DefaultTableModel) search_paymeny_table.getModel();
                dtm.setRowCount(0);

                while (rs.next()) {

                    Vector<String> v = new Vector<>();
                    v.add(rs.getString("invoice.invoice_id"));
                    v.add(rs.getString("invoice.month"));
                    v.add(rs.getString("subject"));
                    v.add(rs.getString("teacher.first_name") + " " + rs.getString("teacher.last_name"));
                    v.add(rs.getString("paid_amount"));
                    

                    dtm.addRow(v);
                }
                search_paymeny_table.setModel(dtm);
                jButton2.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Insert Student Id First", "Waning", JOptionPane.WARNING_MESSAGE);
        }

        
    }//GEN-LAST:event_serchpaymentbtnActionPerformed

    private void searchpaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchpaymentActionPerformed

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

                DefaultTableModel dtm = (DefaultTableModel) add_payment_table.getModel();
                dtm.setRowCount(0);

                while (ars.next()) {

                    Vector<String> v = new Vector<>();
                    v.add(ars.getString("student_id"));
                    v.add(ars.getString("surname") + " " + ars.getString("first_name") + " " + ars.getString("last_name"));
                    v.add(ars.getString("nic"));
                    v.add(ars.getString("subject"));
                    v.add(ars.getString("price"));

                    if (!student.containsKey(ars.getString("student_id"))) {
                        PaymentClass payment = new PaymentClass();
                        payment.setsId(ars.getString("student_id"));
                        payment.setSubjectId(ars.getString("subject_id"));
                        payment.setStudentName(ars.getString("surname") + " " + ars.getString("first_name") + " " + ars.getString("last_name"));
                        payment.setPrice(ars.getString("price"));
                        payment.setSubjectName(ars.getString("subject"));
                        payment.setClassId(ars.getString("class_id"));
                        payment.settId(ars.getString("teacher_id"));

                        ResultSet prs = MySQL.executeSearch("SELECT * FROM `invoice` "
                                + "WHERE `invoice`.`student_studenty_id`='" + ars.getString("student_id") + "' AND `invoice`.`date_time`='" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'");

                        if (!prs.next()) {
                            payment.setStatus("Not-Paid");
                        } else {
                            payment.setStatus("Paid");
                        }
                        student.put(ars.getString("student_id"), payment);
                    }

                    dtm.addRow(v);

                }

                add_payment_table.setModel(dtm);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_searchpaymentActionPerformed

    private void add_payment_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_payment_tableMouseClicked

        jPanel1.setVisible(true);

        selectedRow = add_payment_table.getSelectedRow();
        studentId = String.valueOf(add_payment_table.getValueAt(selectedRow, 0));

        jLabel10.setText(student.get(studentId).getsId());
        jLabel11.setText(student.get(studentId).getSubjectName());
        jLabel12.setText(student.get(studentId).getPrice());
        jSpinner1.setValue(student.get(studentId).getPrice());

    }//GEN-LAST:event_add_payment_tableMouseClicked

    private void addpaymentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addpaymentbtnActionPerformed

        try {

            int option = JOptionPane.showConfirmDialog(this, "Add Payment ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {

                MySQL.executeIUD("INSERT INTO `invoice`(`class_class_id`,`subject_subject_id`,`student_student_id`,`teacher_teacher_id`,`paid_amount`) "
                        + "VALUES('" + student.get(studentId).getClassId() + "','" + student.get(studentId).getSubjectId() + "'"
                        + ",'" + student.get(studentId).getsId() + "','" + student.get(studentId).gettId() + "','" + (double) jSpinner1.getValue() + "')");

                JOptionPane.showMessageDialog(this, "Attendance Marking Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                setAddPaymentUp();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_addpaymentbtnActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

        setAddPaymentUp();
        setSearchSearchUp();

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        setSearchSearchUp();
        
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable add_payment_table;
    private javax.swing.JButton addpaymentbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField s_id_textfield;
    private javax.swing.JTextField searchTextfield;
    private javax.swing.JTable search_paymeny_table;
    private javax.swing.JButton searchpayment;
    private javax.swing.JButton serchpaymentbtn;
    // End of variables declaration//GEN-END:variables
}

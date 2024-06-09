package gui;

import model.MySQL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.UpdateCallback;

public class UpdateLecturerDialog extends javax.swing.JDialog {

    private UpdateCallback callback;
    private String subjectId;
    private String classId;
    private String teacherName;
    private String teacherId;
    private HashMap<String, String> teacherMap = new HashMap<>();

    public UpdateLecturerDialog(java.awt.Frame parent, boolean modal, String subjectId, String classId, UpdateCallback callback) {
        super(parent, modal);
        initComponents();
        this.callback = callback;
        setBg(subjectId, classId);
    }

    private void setBg(String subjectId, String classId) {

        this.subjectId = subjectId;
        this.classId = classId;
        id_lbl.setText(subjectId);

        try {

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `teacher` INNER JOIN `subject` ON `subject`.`subject_id`=`teacher`.`subject_subject_id` "
                    + "INNER JOIN `class` ON `class`.`class_id`=`subject`.`class_class_id` "
                    + "INNER JOIN `surname` ON `teacher`.`surname_surname_id`=`surname`.`surname_id` "
                    + "WHERE `class`.`class_id`='" + classId + "' AND `subject`.`subject_id`='" + subjectId + "'");
            if (rs.next()) {
                old_lec_lbl.setText(rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
                teacherId=rs.getString("teacher_id");
            }
            teacherName = old_lec_lbl.getText();

            ResultSet l_rs = MySQL.executeSearch("SELECT * FROM `teacher` INNER JOIN `surname` ON `teacher`.`surname_surname_id`=`surname`.`surname_id` WHERE `teacher`.`status_status_id`=='1'");

            Vector<String> vector = new Vector<>();
            vector.add("Choose");

            while (l_rs.next()) {

                vector.add(l_rs.getString("surname") + " " + l_rs.getString("first_name") + " " + l_rs.getString("last_name"));
                teacherMap.put(l_rs.getString("surname") + " " + l_rs.getString("first_name") + " " + l_rs.getString("last_name"), l_rs.getString("teacher_id"));

            }

            DefaultComboBoxModel cbm = new DefaultComboBoxModel(vector);
            lecCombobox.setModel(cbm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        icon.setIcon(new ImageIcon(new model.Icon().getIcon()));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        id_lbl = new javax.swing.JLabel();
        update_btn = new javax.swing.JButton();
        cancel_btn = new javax.swing.JButton();
        old_lec_lbl = new javax.swing.JLabel();
        lecCombobox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Update Lecturer");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Subject ID :");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Old Lecturer :");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("New Lecturer :");

        id_lbl.setForeground(new java.awt.Color(255, 255, 255));
        id_lbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        id_lbl.setText("  ");

        update_btn.setBackground(new java.awt.Color(0, 153, 51));
        update_btn.setForeground(new java.awt.Color(255, 255, 255));
        update_btn.setText("Update");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        cancel_btn.setBackground(new java.awt.Color(255, 51, 0));
        cancel_btn.setForeground(new java.awt.Color(255, 255, 255));
        cancel_btn.setText("Cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });

        old_lec_lbl.setForeground(new java.awt.Color(255, 51, 51));
        old_lec_lbl.setText("  ");

        lecCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(update_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancel_btn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(old_lec_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(64, 64, 64))
                                    .addComponent(lecCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(43, 43, 43))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(id_lbl))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(old_lec_lbl)
                    .addComponent(lecCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(update_btn)
                    .addComponent(cancel_btn))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
        callback.onClassUpdateClosed(classId, teacherName);
        this.dispose();
    }//GEN-LAST:event_cancel_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed

        if (lecCombobox.getSelectedIndex() != 0) {

            int isAgreed = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update The Lecturer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (isAgreed == JOptionPane.YES_OPTION) {

                try {

                    String lecName = String.valueOf(lecCombobox.getSelectedItem());
                    String lecId = teacherMap.get(lecCombobox.getSelectedItem());

                    MySQL.executeIUD("UPDATE `teacher` SET `teacher`.`subject_subject_id`='1' WHERE `teacher`.`teacher_id`='" + teacherId + "' ");
                    MySQL.executeIUD("UPDATE `teacher` SET `teacher`.`subject_subject_id`='" + subjectId + "' WHERE `teacher`.`teacher_id`='" + lecId + "' ");


                    JOptionPane.showMessageDialog(this, "Lecturer Update Success !", "Success", JOptionPane.INFORMATION_MESSAGE);

                    callback.onLecturerUpdateClosed(classId, lecName);
                    this.dispose();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Please Select New Class First", "Missing Data", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_update_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_btn;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel id_lbl;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> lecCombobox;
    private javax.swing.JLabel old_lec_lbl;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables

}

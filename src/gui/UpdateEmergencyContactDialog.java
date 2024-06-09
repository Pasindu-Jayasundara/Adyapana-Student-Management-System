package gui;

import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.MySQL;
import model.UpdateCallback;

public class UpdateEmergencyContactDialog extends javax.swing.JDialog {

    private UpdateCallback callback;
    private String id;
    private String ecn1;
    private String ecn2;
    private String ecc1;
    private String ecc2;
    private String ecid1;
    private String ecid2;
    private String table;
    private HashMap<String, String> ecMap;

    private String tableName;
    private String tableFk;
    private String tablePk;

    private boolean isStillUpdating = true;

    public UpdateEmergencyContactDialog(java.awt.Frame parent, boolean modal, HashMap<String, String> ecMap, UpdateCallback callback) {
        super(parent, modal);
        initComponents();
        this.callback = callback;
        setBg(ecMap);
        dbName();
    }

    private void setBg(HashMap<String, String> ecMap) {
        this.id = ecMap.get("id");
        this.table = ecMap.get("table");
        this.ecMap = ecMap;
        this.ecn1 = ecMap.get("ec1N");
        this.ecn2 = ecMap.get("ec2N");
        this.ecc1 = ecMap.get("ec1C");
        this.ecc2 = ecMap.get("ec2C");
        this.ecid1 = ecMap.get("ecid1");
        this.ecid2 = ecMap.get("ecid2");
        uid_lbl.setText(id);

        omb_lbl.setText(ecMap.get("ec1N"));
        omb_lbl2.setText(ecMap.get("ec1C"));
        omb_lbl1.setText(ecMap.get("ec2N"));
        omb_lbl3.setText(ecMap.get("ec2C"));

        icon.setIcon(new ImageIcon(new model.Icon().getIcon()));
    }

    private void dbName() {

        switch (table) {
            case "1":
                //administrator

                tableName = "administrator_emergency_contact";
                tableFk = "administrator_administrator_id";
                tablePk = "administrator_emergency_contact_id";
                break;
            case "2":
                //teacher

                tableName = "teacher_emergency_contact";
                tableFk = "teacher_teacher_id";
                tablePk = "teacher_emergency_contact_id";
                break;
            case "3":
                //student

                tableName = "student_emergency_contact";
                tableFk = "student_student_id";
                tablePk = "student_emergency_contact_id";
                break;
            default:
                break;
        }

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
        newMobileTextfield = new javax.swing.JTextField();
        uid_lbl = new javax.swing.JLabel();
        mobile_update_btn = new javax.swing.JButton();
        m_cancel_btn = new javax.swing.JButton();
        omb_lbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        omb_lbl2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        newMobileTextfield1 = new javax.swing.JTextField();
        omb_lbl3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        omb_lbl1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        newMobileTextfield2 = new javax.swing.JTextField();
        newMobileTextfield3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Update Emergency Contact");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel3.setText("ID :");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Old Emergency Contact");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("New Emergency Contact");

        newMobileTextfield.setForeground(new java.awt.Color(255, 255, 255));

        uid_lbl.setForeground(new java.awt.Color(255, 255, 255));
        uid_lbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        uid_lbl.setText("    ");

        mobile_update_btn.setBackground(new java.awt.Color(0, 153, 51));
        mobile_update_btn.setForeground(new java.awt.Color(255, 255, 255));
        mobile_update_btn.setText("Update");
        mobile_update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobile_update_btnActionPerformed(evt);
            }
        });

        m_cancel_btn.setBackground(new java.awt.Color(255, 51, 0));
        m_cancel_btn.setForeground(new java.awt.Color(255, 255, 255));
        m_cancel_btn.setText("Cancel");
        m_cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_cancel_btnActionPerformed(evt);
            }
        });

        omb_lbl.setForeground(new java.awt.Color(255, 51, 51));
        omb_lbl.setText("   ");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Name :");

        omb_lbl2.setForeground(new java.awt.Color(255, 51, 51));
        omb_lbl2.setText("   ");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Contact :");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Contact :");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Name :");

        newMobileTextfield1.setForeground(new java.awt.Color(255, 255, 255));

        omb_lbl3.setForeground(new java.awt.Color(255, 51, 51));
        omb_lbl3.setText("   ");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Contact :");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Name :");

        omb_lbl1.setForeground(new java.awt.Color(255, 51, 51));
        omb_lbl1.setText("   ");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("Contact :");

        newMobileTextfield2.setForeground(new java.awt.Color(255, 255, 255));

        newMobileTextfield3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Name :");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("1 )");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("2)");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("1)");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("2)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(mobile_update_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(m_cancel_btn)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uid_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(3, 3, 3)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(newMobileTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newMobileTextfield1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(3, 3, 3)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(newMobileTextfield2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newMobileTextfield3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(174, 174, 174))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(omb_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(3, 3, 3))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(omb_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(3, 3, 3))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(omb_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(omb_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 77, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(uid_lbl))
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(omb_lbl)
                    .addComponent(jLabel1)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(omb_lbl2)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(omb_lbl1)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(omb_lbl3)
                    .addComponent(jLabel9))
                .addGap(36, 36, 36)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(newMobileTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(newMobileTextfield1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(newMobileTextfield2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(newMobileTextfield3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mobile_update_btn)
                    .addComponent(m_cancel_btn))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_cancel_btnActionPerformed
        callback.onEmergencyContactUpdateClosed(ecn1, ecc1, ecn2, ecc2);
        this.dispose();
    }//GEN-LAST:event_m_cancel_btnActionPerformed

    private void mobile_update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobile_update_btnActionPerformed

        if (ecid2 == "null") {
            ecid2 = null;
        }
        if (ecid1 == "null") {
            ecid2 = null;
        }

        if (!newMobileTextfield.getText().isBlank() && !newMobileTextfield1.getText().isBlank()) {
            if (model.MobileFormatValidation.isValidMobile(newMobileTextfield1.getText())) {

                int isAgreed = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update This Emergency Contact One?", "Conformation", JOptionPane.YES_NO_OPTION);

                if (isAgreed == JOptionPane.YES_OPTION) {
                    String newMobile = newMobileTextfield1.getText();
                    String newName = newMobileTextfield.getText();

                    try {

                        if (ecid1 != null) {
                            MySQL.executeIUD("UPDATE `" + tableName + "` SET `name`='" + newName + "',`contact`='" + newMobile + "' "
                                    + "WHERE `" + tableName + "`.`" + tableFk + "`='" + id + "' "
                                    + "AND `" + tableName + "`.`" + tablePk + "`='" + ecid1 + "'");

                        } else {
                            MySQL.executeIUD("INSERT INTO `" + tableName + "`(`name`,`contact`,`" + tableFk + "`) "
                                    + "VALUES('" + newName + "','" + newMobile + "','" + id + "')");
                        }

                        JOptionPane.showMessageDialog(this, "Emergency Contact One Update Success !", "Success", JOptionPane.INFORMATION_MESSAGE);

                        isStillUpdating = false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                JOptionPane.showMessageDialog(this, "Emergency Contact One Has A Invalid Number Format !", "Invalid Format !", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (!newMobileTextfield2.getText().isBlank() && !newMobileTextfield3.getText().isBlank()) {
            if (model.MobileFormatValidation.isValidMobile(newMobileTextfield3.getText())) {

                int isAgreed = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update This Emergency Contact Two ?", "Conformation", JOptionPane.YES_NO_OPTION);

                if (isAgreed == JOptionPane.YES_OPTION) {
                    String newMobile = newMobileTextfield3.getText();
                    String newName = newMobileTextfield2.getText();

                    try {
                        if (ecid2 != null) {
                            MySQL.executeIUD("UPDATE `" + tableName + "` SET `name`='" + newName + "',`contact`='" + newMobile + "' "
                                    + "WHERE `" + tableName + "`.`" + tableFk + "`='" + id + "' "
                                    + "AND `" + tableName + "`.`" + tablePk + "`='" + ecid2 + "'");

                        } else {
                            MySQL.executeIUD("INSERT INTO `" + tableName + "`(`name`,`contact`,`" + tableFk + "`) "
                                    + "VALUES('" + newName + "','" + newMobile + "','" + id + "')");
                        }

                        JOptionPane.showMessageDialog(this, "Emergency Contact Two Update Success !", "Success", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                JOptionPane.showMessageDialog(this, "Emergency Contact Two Has A Invalid Number Format !", "Invalid Format !", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Insert Emergency Contact Details First", "Missing Data", JOptionPane.WARNING_MESSAGE);
        }

        if (!isStillUpdating) {
            callback.onEmergencyContactUpdateClosed(ecn1, ecc1, ecn2, ecc2);
            this.dispose();
        }

    }//GEN-LAST:event_mobile_update_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JButton m_cancel_btn;
    private javax.swing.JButton mobile_update_btn;
    private javax.swing.JTextField newMobileTextfield;
    private javax.swing.JTextField newMobileTextfield1;
    private javax.swing.JTextField newMobileTextfield2;
    private javax.swing.JTextField newMobileTextfield3;
    private javax.swing.JLabel omb_lbl;
    private javax.swing.JLabel omb_lbl1;
    private javax.swing.JLabel omb_lbl2;
    private javax.swing.JLabel omb_lbl3;
    private javax.swing.JLabel uid_lbl;
    // End of variables declaration//GEN-END:variables
}

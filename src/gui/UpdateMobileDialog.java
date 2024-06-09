package gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.MySQL;
import model.UpdateCallback;

public class UpdateMobileDialog extends javax.swing.JDialog {

    private UpdateCallback callback;
    private String id;
    private String mobile;
    private String table;
    private String tableName;
    private String tablePk;

    public UpdateMobileDialog(java.awt.Frame parent, boolean modal, String id, String mobile, Integer table, UpdateCallback callback) {
        super(parent, modal);
        initComponents();
        this.callback = callback;
        setBg(id, mobile, table);
        dbName();

    }

    private void setBg(String id, String mobile, Integer table) {
        this.id = id;
        this.mobile = mobile;
        this.table = String.valueOf(table);
        uid_lbl.setText(id);
        omb_lbl.setText(mobile);

        icon.setIcon(new ImageIcon(new model.Icon().getIcon()));
    }

    private void dbName() {
        
        switch (table) {
            case "1":
                //administrator

                tableName = "administrator";
                tablePk = "administrator_id";
                break;
            case "2":
                //teacher

                tableName = "teacher";
                tablePk = "teacher_id";
                break;
            case "3":
                //student

                tableName = "student";
                tablePk = "student_id";
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Update Mobile Number");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
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
        jLabel4.setText("Old Mobile Number :");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("New Mobile Number :");

        newMobileTextfield.setForeground(new java.awt.Color(255, 255, 255));
        newMobileTextfield.setText(" ");

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
                        .addComponent(uid_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mobile_update_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(m_cancel_btn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(omb_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(newMobileTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(43, 43, 43))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(uid_lbl))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newMobileTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(omb_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_cancel_btnActionPerformed
        callback.onMobileUpdateClosed(mobile);
        this.dispose();
    }//GEN-LAST:event_m_cancel_btnActionPerformed

    private void mobile_update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobile_update_btnActionPerformed

        if (!newMobileTextfield.getText().isBlank()) {
            if (model.MobileFormatValidation.isValidMobile(newMobileTextfield.getText())) {

                int isAgreed = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Update This Mobile Number ?", "Conformation", JOptionPane.YES_NO_OPTION);

                if (isAgreed == JOptionPane.YES_OPTION) {
                    String newMobile = newMobileTextfield.getText();

                    try {
                        MySQL.executeIUD("UPDATE `" + tableName + "` SET `mobile`='" + newMobile + "' WHERE `" + tableName + "`.`" + tablePk + "`='" + id + "'");

                        JOptionPane.showMessageDialog(this, "Mobile Number Update Success !", "Success", JOptionPane.INFORMATION_MESSAGE);

                        callback.onMobileUpdateClosed(newMobile);
                        this.dispose();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                JOptionPane.showMessageDialog(this, "New Mobile Number Has A Invalid Number Format !", "Invalid Format !", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Insert New Mobile Number First", "Missing Data", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_mobile_update_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton m_cancel_btn;
    private javax.swing.JButton mobile_update_btn;
    private javax.swing.JTextField newMobileTextfield;
    private javax.swing.JLabel omb_lbl;
    private javax.swing.JLabel uid_lbl;
    // End of variables declaration//GEN-END:variables
}

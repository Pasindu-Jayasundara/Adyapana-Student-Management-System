package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Dashboard extends javax.swing.JFrame {

    public Dashboard(ResultSet userData) {
        initComponents();
        setUpBg(userData);
    }

    private void setUpBg(ResultSet userData) {

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/monitor.png")));
        setTitle("Dashboard | Adyapana Institute");

        welcomePanel.setVisible(true);
        registerPanel.setVisible(false);
        paymentPanel.setVisible(false);
        attendancePanel.setVisible(false);
        managementPanel.setVisible(false);

        instituteDashboardIcon_lbl.setIcon(new ImageIcon(new model.Icon().getIcon()));

        try {
            department_lbl.setText(userData.getString("administrator_department.name"));
            accountOwnerName_lbl.setText(userData.getString("surname.surname") + " " + userData.getString("first_name") + " " + userData.getString("last_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        leftMenuPanel = new javax.swing.JPanel();
        registrationMenuBtn = new javax.swing.JButton();
        paymentMenuBtn = new javax.swing.JButton();
        attendanceMenuBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        managementMenuBtn = new javax.swing.JButton();
        logOutBtn = new javax.swing.JButton();
        rpmbtn = new javax.swing.JButton();
        changingPanel = new javax.swing.JPanel();
        registerPanel = new javax.swing.JPanel();
        registration1 = new gui.Registration();
        paymentPanel = new javax.swing.JPanel();
        payment1 = new gui.Payment();
        attendancePanel = new javax.swing.JPanel();
        attendance1 = new gui.Attendance();
        managementPanel = new javax.swing.JPanel();
        management2 = new gui.Management();
        welcomePanel = new javax.swing.JPanel();
        welcomeDashboard2 = new gui.WelcomeDashboard();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        instituteName1 = new javax.swing.JLabel();
        accountOwnerName_lbl = new javax.swing.JLabel();
        instituteDashboardIcon_lbl = new javax.swing.JLabel();
        department_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 0, 204));
        kGradientPanel1.setkStartColor(new java.awt.Color(110, 9, 110));

        leftMenuPanel.setOpaque(false);

        registrationMenuBtn.setBackground(new java.awt.Color(0, 0, 0));
        registrationMenuBtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        registrationMenuBtn.setForeground(new java.awt.Color(255, 255, 255));
        registrationMenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/registration.png"))); // NOI18N
        registrationMenuBtn.setText("Registration");
        registrationMenuBtn.setBorder(null);
        registrationMenuBtn.setContentAreaFilled(false);
        registrationMenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registrationMenuBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        registrationMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrationMenuBtnActionPerformed(evt);
            }
        });

        paymentMenuBtn.setBackground(new java.awt.Color(0, 0, 0));
        paymentMenuBtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        paymentMenuBtn.setForeground(new java.awt.Color(255, 255, 255));
        paymentMenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/credit-card.png"))); // NOI18N
        paymentMenuBtn.setText("Payment");
        paymentMenuBtn.setBorder(null);
        paymentMenuBtn.setContentAreaFilled(false);
        paymentMenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        paymentMenuBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        paymentMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentMenuBtnActionPerformed(evt);
            }
        });

        attendanceMenuBtn.setBackground(new java.awt.Color(0, 0, 0));
        attendanceMenuBtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        attendanceMenuBtn.setForeground(new java.awt.Color(255, 255, 255));
        attendanceMenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/immigration.png"))); // NOI18N
        attendanceMenuBtn.setText("Attendance");
        attendanceMenuBtn.setBorder(null);
        attendanceMenuBtn.setContentAreaFilled(false);
        attendanceMenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attendanceMenuBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        attendanceMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendanceMenuBtnActionPerformed(evt);
            }
        });

        managementMenuBtn.setBackground(new java.awt.Color(0, 0, 0));
        managementMenuBtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        managementMenuBtn.setForeground(new java.awt.Color(255, 255, 255));
        managementMenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/time.png"))); // NOI18N
        managementMenuBtn.setText("Management");
        managementMenuBtn.setBorder(null);
        managementMenuBtn.setContentAreaFilled(false);
        managementMenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        managementMenuBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        managementMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managementMenuBtnActionPerformed(evt);
            }
        });

        logOutBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        logOutBtn.setForeground(new java.awt.Color(255, 255, 255));
        logOutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/switch.png"))); // NOI18N
        logOutBtn.setText("Log Out");
        logOutBtn.setContentAreaFilled(false);
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        rpmbtn.setBackground(new java.awt.Color(0, 0, 0));
        rpmbtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        rpmbtn.setForeground(new java.awt.Color(255, 255, 255));
        rpmbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/business-report.png"))); // NOI18N
        rpmbtn.setText("Reports");
        rpmbtn.setBorder(null);
        rpmbtn.setContentAreaFilled(false);
        rpmbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rpmbtn.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        rpmbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rpmbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftMenuPanelLayout = new javax.swing.GroupLayout(leftMenuPanel);
        leftMenuPanel.setLayout(leftMenuPanelLayout);
        leftMenuPanelLayout.setHorizontalGroup(
            leftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(leftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(paymentMenuBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registrationMenuBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(attendanceMenuBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(managementMenuBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rpmbtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(leftMenuPanelLayout.createSequentialGroup()
                .addComponent(logOutBtn)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        leftMenuPanelLayout.setVerticalGroup(
            leftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuPanelLayout.createSequentialGroup()
                .addGroup(leftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(leftMenuPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(registrationMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(paymentMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(attendanceMenuBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(managementMenuBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rpmbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logOutBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(leftMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(leftMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        changingPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout registerPanelLayout = new javax.swing.GroupLayout(registerPanel);
        registerPanel.setLayout(registerPanelLayout);
        registerPanelLayout.setHorizontalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(registration1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        registerPanelLayout.setVerticalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addComponent(registration1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        changingPanel.add(registerPanel, "card2");

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(payment1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addComponent(payment1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        changingPanel.add(paymentPanel, "card3");

        javax.swing.GroupLayout attendancePanelLayout = new javax.swing.GroupLayout(attendancePanel);
        attendancePanel.setLayout(attendancePanelLayout);
        attendancePanelLayout.setHorizontalGroup(
            attendancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(attendance1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        attendancePanelLayout.setVerticalGroup(
            attendancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attendancePanelLayout.createSequentialGroup()
                .addComponent(attendance1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        changingPanel.add(attendancePanel, "card4");

        javax.swing.GroupLayout managementPanelLayout = new javax.swing.GroupLayout(managementPanel);
        managementPanel.setLayout(managementPanelLayout);
        managementPanelLayout.setHorizontalGroup(
            managementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(management2, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        managementPanelLayout.setVerticalGroup(
            managementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managementPanelLayout.createSequentialGroup()
                .addComponent(management2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        changingPanel.add(managementPanel, "card5");

        javax.swing.GroupLayout welcomePanelLayout = new javax.swing.GroupLayout(welcomePanel);
        welcomePanel.setLayout(welcomePanelLayout);
        welcomePanelLayout.setHorizontalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(welcomeDashboard2, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        welcomePanelLayout.setVerticalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(welcomeDashboard2, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );

        changingPanel.add(welcomePanel, "card6");

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 0, 0));
        kGradientPanel2.setkStartColor(new java.awt.Color(0, 0, 0));

        instituteName1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        instituteName1.setForeground(new java.awt.Color(255, 255, 255));
        instituteName1.setText("Adyapana Institute");

        accountOwnerName_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        accountOwnerName_lbl.setForeground(new java.awt.Color(255, 255, 255));
        accountOwnerName_lbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        accountOwnerName_lbl.setText("  ");

        instituteDashboardIcon_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        instituteDashboardIcon_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/letter-a (1).png"))); // NOI18N

        department_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        department_lbl.setForeground(new java.awt.Color(255, 255, 255));
        department_lbl.setText("Administrator");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(instituteDashboardIcon_lbl)
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(instituteName1)
                    .addComponent(department_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accountOwnerName_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(instituteDashboardIcon_lbl)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addComponent(instituteName1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(department_lbl))))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(accountOwnerName_lbl)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changingPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void attendanceMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceMenuBtnActionPerformed

        welcomePanel.setVisible(false);
        registerPanel.setVisible(false);
        paymentPanel.setVisible(false);
        managementPanel.setVisible(false);
        attendancePanel.setVisible(true);

        attendanceMenuBtn.setForeground(new Color(32, 250, 69));
        registrationMenuBtn.setForeground(Color.white);
        paymentMenuBtn.setForeground(Color.white);
        managementMenuBtn.setForeground(Color.white);
        rpmbtn.setForeground(Color.white);

    }//GEN-LAST:event_attendanceMenuBtnActionPerformed

    private void paymentMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentMenuBtnActionPerformed

        welcomePanel.setVisible(false);
        registerPanel.setVisible(false);
        attendancePanel.setVisible(false);
        managementPanel.setVisible(false);
        paymentPanel.setVisible(true);

        paymentMenuBtn.setForeground(new Color(32, 250, 69));
        registrationMenuBtn.setForeground(Color.white);
        attendanceMenuBtn.setForeground(Color.white);
        managementMenuBtn.setForeground(Color.white);
        rpmbtn.setForeground(Color.white);

    }//GEN-LAST:event_paymentMenuBtnActionPerformed

    private void registrationMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationMenuBtnActionPerformed

        welcomePanel.setVisible(false);
        paymentPanel.setVisible(false);
        attendancePanel.setVisible(false);
        managementPanel.setVisible(false);
        registerPanel.setVisible(true);

        registrationMenuBtn.setForeground(new Color(32, 250, 69));
        paymentMenuBtn.setForeground(Color.white);
        attendanceMenuBtn.setForeground(Color.white);
        managementMenuBtn.setForeground(Color.white);
        rpmbtn.setForeground(Color.white);

    }//GEN-LAST:event_registrationMenuBtnActionPerformed

    private void managementMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managementMenuBtnActionPerformed

        welcomePanel.setVisible(false);
        paymentPanel.setVisible(false);
        attendancePanel.setVisible(false);
        attendancePanel.setVisible(false);
        managementPanel.setVisible(true);

        managementMenuBtn.setForeground(new Color(32, 250, 69));
        paymentMenuBtn.setForeground(Color.white);
        attendanceMenuBtn.setForeground(Color.white);
        registrationMenuBtn.setForeground(Color.white);
        rpmbtn.setForeground(Color.white);

    }//GEN-LAST:event_managementMenuBtnActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed

        int userChoice = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Logout ?", "Confirm Logging Out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userChoice == JOptionPane.YES_OPTION) {
            this.dispose();
            new LogIn().setVisible(true);
        }
    }//GEN-LAST:event_logOutBtnActionPerformed

    private void rpmbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rpmbtnActionPerformed

        rpmbtn.setForeground(new Color(32, 250, 69));
        paymentMenuBtn.setForeground(Color.white);
        attendanceMenuBtn.setForeground(Color.white);
        registrationMenuBtn.setForeground(Color.white);
        managementMenuBtn.setForeground(Color.white);
        new GenerateReports(this, true).setVisible(true);
        rpmbtn.setForeground(Color.white);
    }//GEN-LAST:event_rpmbtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountOwnerName_lbl;
    private gui.Attendance attendance1;
    private javax.swing.JButton attendanceMenuBtn;
    private javax.swing.JPanel attendancePanel;
    private javax.swing.JPanel changingPanel;
    private javax.swing.JLabel department_lbl;
    private javax.swing.JLabel instituteDashboardIcon_lbl;
    private javax.swing.JLabel instituteName1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JPanel leftMenuPanel;
    private javax.swing.JButton logOutBtn;
    private gui.Management management2;
    private javax.swing.JButton managementMenuBtn;
    private javax.swing.JPanel managementPanel;
    private gui.Payment payment1;
    private javax.swing.JButton paymentMenuBtn;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JPanel registerPanel;
    private gui.Registration registration1;
    private javax.swing.JButton registrationMenuBtn;
    private javax.swing.JButton rpmbtn;
    private gui.WelcomeDashboard welcomeDashboard2;
    private javax.swing.JPanel welcomePanel;
    // End of variables declaration//GEN-END:variables

}

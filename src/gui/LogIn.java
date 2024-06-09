package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import model.CheckInternetConnection;
import model.EmailFormatValidation;
import model.Icon;
import model.MySQL;
import model.SendEmail;

public class LogIn extends javax.swing.JFrame {

    public LogIn() {
        initComponents();
        setUpBG();
    }

    private void setUpBG() {

        bgImageBlurPanel.setBackground(new Color(0, 0, 0, 50));
        loginFormPanel.setBackground(new Color(225, 225, 225, 100));
        usernameTextField.setBorder(new EmptyBorder(2, 15, 2, 5));
        passwordPasswordField.setBorder(new EmptyBorder(2, 15, 2, 5));

        this.setIconImage(new Icon().getIcon());
        this.setTitle("Adyapana Institute Login");

        instituteLogo_lbl.setIcon(new ImageIcon(new Icon().getIcon()));

        usernameTextField.setText("");
        passwordPasswordField.setText("");
        usernameTextField.grabFocus();

    }

    public void logIn() {

        String username = usernameTextField.getText();
        String password = String.valueOf(passwordPasswordField.getPassword());

        if (username.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Username", "Invalid Username", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
        } else if (!EmailFormatValidation.isValidEmail(username)) {
            JOptionPane.showMessageDialog(this, "Invalid Username Format", "Invalid Username", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
        } else if (password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Password", "Invalid Password", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
        } else if (passwordPasswordField.getPassword().length < 8) {
            JOptionPane.showMessageDialog(this, "Password Length Should Be More Than 8 Characters", "Invalid Password", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
        } else {

            try {
                String query = "SELECT * FROM `administrator` "
                        + "INNER JOIN `surname` ON `surname`.`surname_id`=`administrator`.`surname_surname_id` "
                        + "INNER JOIN `account_type` ON `account_type`.`account_type_id`=`administrator`.`account_type_account_type_id` "
                        + "INNER JOIN `administrator_department` ON `administrator_department`.`administrator_department_id`=`administrator`.`administrator_department_administrator_department_id` "
                        + "WHERE `administrator`.`email`='" + username + "' "
                        + "AND `administrator`.`password`='" + password + "' "
                        + "AND `administrator`.`status_status_id`='1'";
                ResultSet resultset = MySQL.executeSearch(query);

                int rowCount = 0;
                if (resultset.last()) {
                    rowCount = resultset.getRow();
                    resultset.beforeFirst();
                }

                if (rowCount == 1) {

                    if (resultset.next()) {

                        if (resultset.getString("recovery").equals("2")) {

                            this.dispose();
                            new Dashboard(resultset).setVisible(true);

                        } else if (resultset.getString("recovery").equals("1")) {

                            String newPassword = JOptionPane.showInputDialog(this, "Enter New Password", "Reset Password", JOptionPane.QUESTION_MESSAGE);
                            if (newPassword.length() < 8) {
                                JOptionPane.showMessageDialog(this, "Password Length Should Be More Than 8 Characters", "Invalid Password", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
                            } else {

                                MySQL.executeIUD("UPDATE `administrator` SET `administrator`.`password`='" + newPassword + "' , "
                                        + "`administrator`.`recovery`='2' WHERE `administrator`.`administrator_id`='" + resultset.getString("administrator_id") + "'");
                                JOptionPane.showMessageDialog(this, "Password Update Success ! Login With Your New Credentials", "Password Updated", JOptionPane.INFORMATION_MESSAGE);

                                usernameTextField.setText("");
                                passwordPasswordField.setText("");
                                usernameTextField.grabFocus();

                                if (CheckInternetConnection.isInternetAvailable()) { //connection avaliable
                                    SendEmail.send(username, "Update Credentials", "Your Login Credentials Have Been Successfully Updated");
                                }

                            }

                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Coundn't Find Your Account", "Account Not Found", JOptionPane.ERROR_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/error.png"))));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginImagePanel = new javax.swing.JPanel();
        bgImageBlurPanel = new javax.swing.JPanel();
        loginFormPanel = new javax.swing.JPanel();
        username_lbl = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordPasswordField = new javax.swing.JPasswordField();
        password_lbl = new javax.swing.JLabel();
        login_btn = new javax.swing.JButton();
        headerPanel = new javax.swing.JPanel();
        instituteName_lbl = new javax.swing.JLabel();
        instituteLogo_lbl = new javax.swing.JLabel();
        forgotPassword_lbl = new javax.swing.JLabel();
        bgImg_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);

        loginImagePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginFormPanel.setBackground(java.awt.Color.white);

        username_lbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        username_lbl.setForeground(new java.awt.Color(255, 255, 255));
        username_lbl.setText("Username");

        usernameTextField.setBackground(new java.awt.Color(0, 0, 0));
        usernameTextField.setForeground(new java.awt.Color(255, 255, 255));
        usernameTextField.setText("  ");
        usernameTextField.setBorder(null);
        usernameTextField.setOpaque(true);

        passwordPasswordField.setBackground(new java.awt.Color(0, 0, 0));
        passwordPasswordField.setForeground(new java.awt.Color(255, 255, 255));
        passwordPasswordField.setBorder(null);
        passwordPasswordField.setOpaque(true);

        password_lbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        password_lbl.setForeground(new java.awt.Color(255, 255, 255));
        password_lbl.setText("Password");

        login_btn.setBackground(new java.awt.Color(0, 102, 102));
        login_btn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        login_btn.setForeground(new java.awt.Color(255, 255, 255));
        login_btn.setText("Log In");
        login_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_btnActionPerformed(evt);
            }
        });
        login_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                login_btnKeyReleased(evt);
            }
        });

        headerPanel.setBackground(new java.awt.Color(0, 0, 0));

        instituteName_lbl.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        instituteName_lbl.setForeground(new java.awt.Color(255, 255, 255));
        instituteName_lbl.setText("ADYAPANA INSTITUTE");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(instituteLogo_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(instituteName_lbl)
                .addGap(102, 102, 102))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(instituteLogo_lbl)
                    .addComponent(instituteName_lbl))
                .addGap(22, 22, 22))
        );

        forgotPassword_lbl.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        forgotPassword_lbl.setForeground(new java.awt.Color(255, 255, 255));
        forgotPassword_lbl.setText("Forgot Password");
        forgotPassword_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotPassword_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout loginFormPanelLayout = new javax.swing.GroupLayout(loginFormPanel);
        loginFormPanel.setLayout(loginFormPanelLayout);
        loginFormPanelLayout.setHorizontalGroup(
            loginFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginFormPanelLayout.createSequentialGroup()
                .addGroup(loginFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(loginFormPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(forgotPassword_lbl))
                    .addGroup(loginFormPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(loginFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordPasswordField)
                            .addComponent(login_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(password_lbl)
                            .addComponent(username_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(70, 70, 70))
        );
        loginFormPanelLayout.setVerticalGroup(
            loginFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginFormPanelLayout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(username_lbl)
                .addGap(10, 10, 10)
                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(password_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forgotPassword_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(login_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        javax.swing.GroupLayout bgImageBlurPanelLayout = new javax.swing.GroupLayout(bgImageBlurPanel);
        bgImageBlurPanel.setLayout(bgImageBlurPanelLayout);
        bgImageBlurPanelLayout.setHorizontalGroup(
            bgImageBlurPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgImageBlurPanelLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(loginFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(244, Short.MAX_VALUE))
        );
        bgImageBlurPanelLayout.setVerticalGroup(
            bgImageBlurPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgImageBlurPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(loginFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        loginImagePanel.add(bgImageBlurPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 550));

        bgImg_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/20230711004220_[fpdl.in]_graduation-greeting-background-illustration-ai-generativexa_118124-26312_large.jpg"))); // NOI18N
        loginImagePanel.add(bgImg_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void login_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_btnActionPerformed

        logIn();

    }//GEN-LAST:event_login_btnActionPerformed

    private void forgotPassword_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotPassword_lblMouseClicked

        if (CheckInternetConnection.isInternetAvailable()) {

            String username = usernameTextField.getText();
            if (username.isBlank()) {
                JOptionPane.showMessageDialog(this, "Please Enter Your Username", "Invalid Username", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
            } else if (!EmailFormatValidation.isValidEmail(username)) {
                JOptionPane.showMessageDialog(this, "Invalid Username Format", "Invalid Username", JOptionPane.WARNING_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/warning.png"))));
            } else {

                forgotPassword_lbl.enableInputMethods(false);

                try {
                    String query = "SELECT * FROM `administrator` "
                            + "WHERE `administrator`.`email`='" + username + "' "
                            + "AND `administrator`.`status_status_id`='1'";
                    ResultSet resultset = MySQL.executeSearch(query);

                    int rowCount = 0;
                    if (resultset.last()) {
                        rowCount = resultset.getRow();
                        resultset.beforeFirst();
                    }

                    if (rowCount == 1) {

                        String code = model.UniqId.generate();

                        MySQL.executeIUD("UPDATE `administrator` SET `administrator`.`password`='" + code + "' "
                                + ",`administrator`.`recovery`='1' WHERE `administrator`.`email`='" + username + "' "
                                + "AND `administrator`.`status_status_id`='1'");

                        String status = SendEmail.send(username, "Recover Forgot Password", "Your Temporary Password is '" + code + "'");

                        forgotPassword_lbl.enableInputMethods(true);

                        if (status.equals("1")) { //sending success
                            JOptionPane.showMessageDialog(this, "Recovery Password Hass Been Sent Your Account", "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, status, "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {

                        forgotPassword_lbl.enableInputMethods(true);
                        JOptionPane.showMessageDialog(this, "Coundn't Find Your Account", "Account Not Found", JOptionPane.ERROR_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/error.png"))));

                    }

                } catch (Exception e) {
                    forgotPassword_lbl.enableInputMethods(true);
                    e.printStackTrace();
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "You Need Have Active Internet Connection For Use This Service", "Internet Connection Not Avaliable", JOptionPane.ERROR_MESSAGE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/error.png"))));
        }
    }//GEN-LAST:event_forgotPassword_lblMouseClicked

    private void login_btnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_login_btnKeyReleased

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            logIn();
        }

    }//GEN-LAST:event_login_btnKeyReleased

    public static void main(String args[]) {

        FlatDarkLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgImageBlurPanel;
    private javax.swing.JLabel bgImg_lbl;
    private javax.swing.JLabel forgotPassword_lbl;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel instituteLogo_lbl;
    private javax.swing.JLabel instituteName_lbl;
    private javax.swing.JPanel loginFormPanel;
    private javax.swing.JPanel loginImagePanel;
    private javax.swing.JButton login_btn;
    private javax.swing.JPasswordField passwordPasswordField;
    private javax.swing.JLabel password_lbl;
    private javax.swing.JTextField usernameTextField;
    private javax.swing.JLabel username_lbl;
    // End of variables declaration//GEN-END:variables
}

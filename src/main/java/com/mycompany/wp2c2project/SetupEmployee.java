/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

interface SetupEmployeeCallback {

    void onSetupEmployeeFinished();
}

/**
 *
 * @author jejer
 */
public class SetupEmployee extends javax.swing.JFrame {

    /**
     * Creates new form registerEmployee
     */
    private String id = "0";
    private String name = "none";
    private String department = "none";

    public SetupEmployee(SummaryHome summaryHome) {
        initComponents();

        id = summaryHome.id;
        name = summaryHome.name;
        department = summaryHome.department;

        nameLabel.setText(name);
    }

    private SetupEmployeeCallback callback;

    public void setCallback(SetupEmployeeCallback callback) {
        this.callback = callback;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rateField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tinField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        philHealthField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sssField = new javax.swing.JTextField();
        pagibigField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        taxStatusField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        shiftStartField = new javax.swing.JTextField();
        shiftEndField = new javax.swing.JTextField();
        skipBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nameLabel.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        nameLabel.setText("Name");

        jLabel2.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel2.setText("Rate");

        rateField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        rateField.setText("570.00");

        jLabel1.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel1.setText("Enter details");

        jLabel3.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel3.setText("TIN");

        tinField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        tinField.setText("000-000-000");

        jLabel4.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel4.setText("Phil Health No.");

        philHealthField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        philHealthField.setText("000000000000");

        jLabel5.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel5.setText("SSS No.");

        sssField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        sssField.setText("00-0000000-0");

        pagibigField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        pagibigField.setText("000000000000");

        jLabel6.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel6.setText("Tax Status");

        taxStatusField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        taxStatusField.setText("ME0");

        jLabel7.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel7.setText("Pag-ibig No.");

        saveBtn.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel8.setText("New employee found");

        jLabel9.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel9.setText("Shift start");

        jLabel10.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jLabel10.setText("Shift end");

        shiftStartField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        shiftStartField.setText("07:00");

        shiftEndField.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        shiftEndField.setText("16:00");

        skipBtn.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        skipBtn.setText("Skip");
        skipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addComponent(jLabel1)
                        .addComponent(nameLabel)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(philHealthField)
                        .addComponent(sssField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(rateField)
                        .addComponent(tinField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)
                        .addComponent(pagibigField)
                        .addComponent(taxStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(shiftStartField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(shiftEndField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(skipBtn)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shiftStartField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shiftEndField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(philHealthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sssField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagibigField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taxStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(skipBtn))
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        String rate = "0";
        String shiftStart = "00:00";
        String shiftEnd = "00:00";
        String tin = "0";
        String philHealth = "0";
        String sss = "0";
        String pagibig = "0";
        String taxStatus = "none";

        //!! need checking !!
        if (!rateField.getText().isEmpty()) {
            rate = rateField.getText();
        }
        if (!shiftStartField.getText().isEmpty()) {
            shiftStart = shiftStartField.getText();
        }
        if (!shiftEndField.getText().isEmpty()) {
            shiftEnd = shiftEndField.getText();
        }
        if (!tinField.getText().isEmpty()) {
            tin = tinField.getText();
        }
        if (!philHealthField.getText().isEmpty()) {
            philHealth = philHealthField.getText();
        }
        if (!sssField.getText().isEmpty()) {
            sss = sssField.getText();
        }
        if (!pagibigField.getText().isEmpty()) {
            pagibig = pagibigField.getText();
        }
        if (!taxStatusField.getText().isEmpty()) {
            taxStatus = taxStatusField.getText();
        }

        Connection sgconn = Main.connectSG();
        PreparedStatement st;
        try {
            st = (PreparedStatement) sgconn.prepareStatement(
                    "INSERT INTO `employee`"
                    + "(`id`, `name`, `department`, `rate`, `shiftStart`, `shiftEnd`,`tin`, `philHealth`, `sss`, `pagibig`, `taxStatus`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, id);
            st.setString(2, name);
            st.setString(3, department);
            st.setString(4, rate);
            st.setString(5, shiftStart);
            st.setString(6, shiftEnd);
            st.setString(7, tin);
            st.setString(8, philHealth);
            st.setString(9, sss);
            st.setString(10, pagibig);
            st.setString(11, taxStatus);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SetupEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (callback != null) {
            callback.onSetupEmployeeFinished();
        }

        dispose();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void skipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipBtnActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Confirm skipping registering all new employees?", "Confirm", JOptionPane.WARNING_MESSAGE);
        if (result == 0) {
            dispose();
        }
    }//GEN-LAST:event_skipBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        FlatLightLaf.setup();
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SummaryHome summaryHome = new SummaryHome();
                new SetupEmployee(summaryHome).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JLabel nameLabel;
    public javax.swing.JTextField pagibigField;
    public javax.swing.JTextField philHealthField;
    public javax.swing.JTextField rateField;
    private javax.swing.JButton saveBtn;
    public javax.swing.JTextField shiftEndField;
    public javax.swing.JTextField shiftStartField;
    private javax.swing.JButton skipBtn;
    public javax.swing.JTextField sssField;
    public javax.swing.JTextField taxStatusField;
    public javax.swing.JTextField tinField;
    // End of variables declaration//GEN-END:variables
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micro
 */
public class Biometrics extends javax.swing.JFrame {

    /**
     * Creates new form Biometrics
     */
    public Biometrics() {
        initComponents();
    }
    
//    public void date() {
//        DateTimeFormatter date = DateTimeFormatter .ofPattern("MMM-dd-yyyy");
//        LocalDateTime now = LocalDateTime.now();
//        Attendance deyt = new Attendance();
//        deyt.Date.setText(date.format(now));
//        deyt.setVisible(true);
//    }
    
    public void timein() throws ParseException {
        DateTimeFormatter timein = DateTimeFormatter .ofPattern("hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        Attendance timeIn = new Attendance();
        timeIn.TimeIn.setText(timein.format(now));
        timeIn.setVisible(true);
    }
    
    public void timeout() throws ParseException {
        DateTimeFormatter timeout = DateTimeFormatter .ofPattern("hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        Attendance timeOut = new Attendance();
        Attendance prevframe = new Attendance();
        timeOut.TimeOut.setText(timeout.format(now));
        prevframe.setVisible(false);
        timeOut.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        BiometricsPreview = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TimeIn = new java.awt.Button();
        TimeOut = new java.awt.Button();
        Register = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(160, 233, 255));

        BiometricsPreview.setBackground(new java.awt.Color(255, 255, 255));
        BiometricsPreview.setAlignmentX(0.0F);
        BiometricsPreview.setAlignmentY(0.0F);

        javax.swing.GroupLayout BiometricsPreviewLayout = new javax.swing.GroupLayout(BiometricsPreview);
        BiometricsPreview.setLayout(BiometricsPreviewLayout);
        BiometricsPreviewLayout.setHorizontalGroup(
            BiometricsPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );
        BiometricsPreviewLayout.setVerticalGroup(
            BiometricsPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Biometrics");

        TimeIn.setBackground(new java.awt.Color(79, 152, 202));
        TimeIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TimeIn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        TimeIn.setForeground(new java.awt.Color(255, 255, 255));
        TimeIn.setLabel("Time In");
        TimeIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeInActionPerformed(evt);
            }
        });

        TimeOut.setBackground(new java.awt.Color(79, 152, 202));
        TimeOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TimeOut.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        TimeOut.setForeground(new java.awt.Color(255, 255, 255));
        TimeOut.setLabel("Time Out");
        TimeOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeOutActionPerformed(evt);
            }
        });

        Register.setBackground(new java.awt.Color(79, 152, 202));
        Register.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Register.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        Register.setForeground(new java.awt.Color(255, 255, 255));
        Register.setLabel("Register");
        Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TimeIn, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel1))
                    .addComponent(BiometricsPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(BiometricsPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(TimeIn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        TimeOut.getAccessibleContext().setAccessibleName("RegisterButton");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TimeOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeOutActionPerformed
        try {
            // TODO add your handling code here:
            timeout();
        } catch (ParseException ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TimeOutActionPerformed

    private void TimeInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeInActionPerformed
        try {
            // TODO add your handling code here:
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
//        String dd = sdf.format(date);
//        
//        Attendance deyt = new Attendance();
//        deyt.Date.setText(dd);
//
//        date();
timein();
        } catch (ParseException ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TimeInActionPerformed

    private void RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Biometrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Biometrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Biometrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Biometrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Biometrics().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BiometricsPreview;
    private java.awt.Button Register;
    private java.awt.Button TimeIn;
    private java.awt.Button TimeOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}

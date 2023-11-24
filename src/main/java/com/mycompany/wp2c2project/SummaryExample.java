/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import java.text.ParseException;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author jejer
 */
public class SummaryExample extends javax.swing.JFrame {

    DefaultTableModel model;

    /**
     * Creates new form SummaryExample
     */
    public SummaryExample() {
        initComponents();

        Attendance attendance;
        try {
            attendance = new Attendance();
        } catch (ParseException ex) {
            System.err.println("Error creating Attendance instance: " + ex.getMessage());
            return;
        }
        //table model
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

//        String name = attendance.empName.getText();
        String name = "Jerwin"; //sample name
        float rate = (float) 1007.86;
        float rpH = rate / 8;

//        String timeInStr = attendance.timeIn.getText();
//        String timeOutStr = attendance.timeOut.getText();
        String timeInStr = "8:30"; //sample time in
        String timeOutStr = "8:30"; //sample time out

        //Time in & time out
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("PT"));
        Date timeIn, timeOut;
        try {
            timeIn = dateFormat.parse(timeInStr);
            timeOut = dateFormat.parse(timeOutStr);
        } catch (ParseException ex) {
            System.err.println("Error parsing time values: " + ex.getMessage());
            return;
        }

        long timeInMill = timeIn.getTime();
        long timeOutMill = timeOut.getTime();

        // Calculate hours and minutes
        int timeInHr = (int) (timeInMill / (60 * 60 * 1000));
        int timeInMin = (int) ((timeInMill / (60 * 1000)) % 60);
        int timeOutHr = (int) (timeOutMill / (60 * 60 * 1000));
        int timeOutMin = (int) ((timeOutMill / (60 * 1000)) % 60);

        float hours = timeOutHr - timeInHr + (float) (timeOutMin - timeInMin) / 60;
        float days = hours / 8;

        //get shift start time from table in ViewRatesDepartments
        ViewRatesDepartments rates = new ViewRatesDepartments();
        String shiftIntae = (String) rates.Accounting.getModel().getValueAt(0, 2);
        System.out.println(shiftIntae);
        
        float late = 0;
        try {
            Date thresholdTime = dateFormat.parse(shiftIntae);
            if (timeIn.after(thresholdTime)) {
                late = (float) (timeInMill - thresholdTime.getTime()) / (60 * 1000);
            }
        } catch (ParseException ex) {
            System.err.println("Error parsing time values: " + ex.getMessage());
        }
        float lateAmt = late * 2 / 60 * rpH;
        
        float ot = 0;
        try {
            Date thresholdTime = dateFormat.parse("17:00");
            if (timeOut.after(thresholdTime)) {
                ot = (float) (timeOutMill - thresholdTime.getTime()) / (60 * 60 * 1000);
            }
        } catch (ParseException ex) {
            System.err.println("Error parsing time values: " + ex.getMessage());
        }
        float otAmt = ot * rpH;

        //night diff
        float nd = 0;
        try {
            Date thresholdStart = dateFormat.parse("22:00");
            Date thresholdEnd = dateFormat.parse("06:00");
            Date midnight = dateFormat.parse("00:00");
            int hr24 = 24 * 60 * 60 * 1000;

            if (timeIn.after(thresholdStart) || (timeIn.after(midnight) && timeOut.before(thresholdEnd)) || (timeIn.after(thresholdStart) && timeOut.before(midnight)) || timeOut.before(thresholdEnd)) {
                long timeInMax = timeInMill;
                long timeOutMnm = timeOutMill;

                if (timeIn.before(midnight)) {
                    timeInMax = Math.max(timeInMill, 22 * 60 * 60 * 1000);
                }
                if (timeOut.after(midnight)) {
                    timeOutMnm = Math.min(timeOutMill, 6 * 60 * 60 * 1000);
                }

                //if timeOut after 24:00    
                if (timeIn.after(timeOut)) {
                    nd = (hr24 + timeOutMnm - timeInMax) / (60 * 60 * 1000);
                } else {
                    nd = (timeOutMnm - timeInMax) / (60 * 60 * 1000);
                }
            }
        } catch (ParseException ex) {
            System.err.println("Error parsing time values: " + ex.getMessage());
        }
        float ndAmt = .10f * rpH * nd;
        
        float OD = 0;
        float TD = 0;
        float AA = 0;
        
        float rWage = rpH * hours - lateAmt + ndAmt;
        float gross = rWage + ot + nd;
        float NP = gross - OD + TD + AA;
                
        model.addRow(new Object[]{
            name,
            rate,
            days,
            late + " (" + lateAmt + ")",
            rWage,
            ot + " (" + otAmt + ")",
            nd + " (" + ndAmt + ")", 0, 0, 0, 0,
            gross, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, OD, TD, AA, NP, });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Rate", "Days Worked", "Late (Min)", "Regular Wage", "Overtime", "Night Diff.", "Special Holiday/Sunday", "Special Holiday/Sunday (Overtime)", "Legal Holiday", "Legal Holiday (Overtime)", "Gross Income", "SSS Contribution (Regular)", "SSS Contribution (MPF)", "PhilHealth", "W/tax", "SSS (Loan-S)", "SSS (Loan-S)", "Pag-ibig Contribution", "EFUND", "Pag-ibig (Loan-S)", "Pag-ibig (Loan-C)", "Other Deductions", "Total Deductions", "Adjustment Allowance", "Net Pay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jButton3.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Inter", 1, 24)); // NOI18N
        jLabel2.setText("October 6-20");

        jLabel3.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jLabel3.setText("2023");

        jButton4.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton4.setText("Edit deductions & allowance");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(81, 81, 81)
                        .addComponent(jButton4)))
                .addGap(669, 669, 669))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SummaryHome frame = new SummaryHome();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(SummaryExample.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SummaryExample.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SummaryExample.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SummaryExample.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SummaryExample().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

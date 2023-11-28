/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jejer
 */
public class Summary extends javax.swing.JFrame {

    /**
     * Creates new form Summary
     *
     */
    Connection sgconn = Main.connectSG();
    PreparedStatement st;
    ResultSet resultSet;

    String period;

    public Summary(SummaryHome summaryHome) {
        initComponents();
    }

    public void showSummary(String periodP) {
        try {
            period = periodP;
            System.out.println(period);

            periodLb.setText(period);

            st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `summary` WHERE `period` = ?");
            st.setString(1, period);
            resultSet = st.executeQuery();
            while (resultSet.next()) {
                //from summary database
                int empId = resultSet.getInt("empId");
                float dayTot = resultSet.getFloat("dayTot");
                float lateTot = resultSet.getFloat("lateTot");
                float lateAmt = resultSet.getFloat("lateAmt");
                float regWage = resultSet.getFloat("regWage");
                float otTot = resultSet.getFloat("otTot");
                float otAmt = resultSet.getFloat("otAmt");
                float ndTot = resultSet.getFloat("ndTot");
                float ndAmt = resultSet.getFloat("ndAmt");
                float spcTot = resultSet.getFloat("spcTot");
                float spcAmt = resultSet.getFloat("spcAmt");
                float spcOtTot = resultSet.getFloat("spcOtTot");
                float spcOtAmt = resultSet.getFloat("spcOtAmt");
                float legTot = resultSet.getFloat("legTot");
                float legAmt = resultSet.getFloat("legAmt");
                float gross = resultSet.getFloat("gross");
                float sssReg = resultSet.getFloat("sssReg");
                float sssMpf = resultSet.getFloat("sssMpf");
                float phealth = resultSet.getFloat("phealth");
                float wtax = resultSet.getFloat("wtax");
                float sssLoanS = resultSet.getFloat("sssLoanS");
                float sssLoanC = resultSet.getFloat("sssLoanC");
                float pagibigCont = resultSet.getFloat("pagibigCont");
                float efund = resultSet.getFloat("efund");
                float pagibigLoanS = resultSet.getFloat("pagibigLoanS");
                float pagibigLoanC = resultSet.getFloat("pagibigLoanC");
                float otherDedt = resultSet.getFloat("otherDedt");
                float dedtTot = resultSet.getFloat("dedtTot");
                float allowance = resultSet.getFloat("allowance");
                float netPay = resultSet.getFloat("netPay");

                //from employee database
                st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `employee` WHERE `empId` = ?");
                st.setInt(1, empId);
                ResultSet employeeResultSet = st.executeQuery();
                employeeResultSet.next();
                String department = employeeResultSet.getString("department");
                String name = employeeResultSet.getString("name");
                Float rate = employeeResultSet.getFloat("rate");

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[]{
                    empId,
                    department,
                    name,
                    rate,
                    dayTot,
                    lateTot + " (" + lateAmt + ")",
                    regWage,
                    otTot + " (" + otAmt + ")",
                    ndTot + " (" + ndAmt + ")",
                    spcTot + " (" + spcAmt + ")",
                    spcOtTot + " (" + spcOtAmt + ")",
                    legTot + " (" + legAmt + ")",
                    gross,
                    sssReg,
                    sssMpf,
                    phealth,
                    wtax,
                    sssLoanS,
                    sssLoanC,
                    pagibigCont,
                    efund,
                    pagibigLoanS,
                    pagibigLoanC,
                    otherDedt,
                    dedtTot,
                    allowance,
                    netPay,});

                System.out.println("table updated empId:" + empId);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Summary.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        periodLb = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Department", "Name", "Rate", "Days Worked", "Late (Min)", "Regular Wage", "Overtime", "Night Diff.", "Special Holiday/Sunday", "Special Holiday/Sunday (Overtime)", "Legal Holiday", "Gross Income", "SSS Contribution (Regular)", "SSS Contribution (MPF)", "PhilHealth", "W/tax", "SSS (Loan-S)", "SSS (Loan-S)", "Pag-ibig Contribution", "EFUND", "Pag-ibig (Loan-S)", "Pag-ibig (Loan-C)", "Other Deductions", "Total Deductions", "Adjustment Allowance", "Net Pay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        periodLb.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        periodLb.setText("Period");

        jLabel3.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel3.setText("Payroll");

        jButton1.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(periodLb)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(periodLb, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SummaryHome summaryHome = new SummaryHome();
        summaryHome.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Summary.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Summary.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Summary.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Summary.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SummaryHome summaryHome = new SummaryHome();
                new Summary(summaryHome).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel periodLb;
    // End of variables declaration//GEN-END:variables
}

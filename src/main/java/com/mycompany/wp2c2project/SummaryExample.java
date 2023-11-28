/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.util.TimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jejer
 */
public class SummaryExample extends javax.swing.JFrame {

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
        DefaultTableModel model = (DefaultTableModel) table.getModel();

//        String name = attendance.empName.getText();
        String name = "Jerwin"; //sample name
        float rate = (float) 1007.86;
        float rpH = rate / 8;

//        String timeInStr = attendance.timeIn.getText();
//        String timeOutStr = attendance.timeOut.getText();
        String timeInStr = "7:00"; //sample time in
        String timeOutStr = "12:30"; //sample time out

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

        //calculate day
        float hours = timeOutHr - timeInHr + (float) (timeOutMin - timeInMin) / 60;
        float days = hours / 9;
        
        //rounded time in and out
        String roundedTimeIn = "";
        String roundedTimeOut = "";
        if (timeInMin < 30) {
            roundedTimeIn = String.format("%02d:00", timeInHr);
        } else {
            roundedTimeIn = String.format("%02d:00", timeInHr + 1);
        }

        if (timeOutMin < 30) {
            roundedTimeOut = String.format("%02d:00", timeOutHr);
        } else {
            roundedTimeOut = String.format("%02d:00", timeOutHr + 1);
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");
        Date roundedTimeInDate, roundedTimeOutDate;
        try {
            roundedTimeInDate = dateTimeFormat.parse(roundedTimeIn);
            roundedTimeOutDate = dateTimeFormat.parse(roundedTimeOut);
        } catch (ParseException ex) {
            Logger.getLogger(SummaryExample.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        long roundedDayInMillis = roundedTimeOutDate.getTime() - roundedTimeInDate.getTime();
        int roundedDayHours = (int) (roundedDayInMillis / (60 * 60 * 1000));
        int roundedDayMinutes = (int) ((roundedDayInMillis / (60 * 1000)) % 60); 
        
        System.out.println("Rounded Time In: " + roundedTimeIn);
        System.out.println("Rounded Time Out: " + roundedTimeOut);
        System.out.println("Duration in Hours: " + timeInHr + "Minutes: " + timeInMin);
        
        //get shift start time from table in ViewRatesDepartments
        ViewRatesDepartments rates = new ViewRatesDepartments();

        String shiftStart = (String) rates.employeeTable.getModel().getValueAt(0, 4);
        String shiftEnd = (String) rates.employeeTable.getModel().getValueAt(0, 5);

        float late = 0;
        try {
            Date thresholdTime = dateFormat.parse(shiftStart);
            if (timeIn.after(thresholdTime)) {
                late = (float) (timeInMill - thresholdTime.getTime()) / (60 * 1000);
            }
        } catch (ParseException ex) {
            System.err.println("Error parsing time values: " + ex.getMessage());
        }
        float lateAmt = late * 2 / 60 * rpH;

        /*
        dateType 0 = normal day
        dateType 1 = spl hol / sun
        dateType 2 = leg hol
         */
        int dateType = 0;
        try {
            Connection connection = Main.connectSG();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM time_card");
            if (resultSet.next()) {
                dateType = resultSet.getInt("dateType");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        if (dateType == 1) {
            rpH = (float) (rpH * 1.30);
        } else if (dateType == 2) {
            rpH = rpH * 2;
        }
        
        //time card flexibility khok khok
        try {
            Connection conn = DriverManager.getConnection(Main.SG_URL, Main.USER, Main.PASS);
            Statement stmt = conn.createStatement();
            String sql1 = "UPDATE time_card SET day = 1 WHERE dateType != 0";
            String sql2 = "UPDATE time_card SET day = 1 WHERE timeIn != '' AND timeOut = ''";
            float spcHrs = 0;
            float spcOT = 0;
            if (hours >= 8) {
                spcHrs = 8;
                spcOT = hours - 8;
            } else {
                spcHrs = hours;
            }
            String sql3 = "UPDATE time_card SET spc = " + spcHrs + ", day = 0 WHERE dateType = 1 AND timeIn != ''";
            String sql4 = "UPDATE time_card SET spcOt = " + spcOT + ", day = 0 WHERE dateType = 1 AND timeIn != ''";
            String sql5 = "UPDATE time_card SET leg = " + spcHrs + ", day = 0 WHERE dateType = 2 AND timeIn != ''";
            
            int rowsAffected6 = 0;
            int rowsAffected7 = 0;
            if (roundedDayHours >=9) {
                String sql6 = "UPDATE time_card SET day = 1 WHERE timeIn != ''";
                rowsAffected6 = stmt.executeUpdate(sql6);
            } else {
                String sql7 = "UPDATE time_card SET day = " + days;
                rowsAffected7 = stmt.executeUpdate(sql7);
            }
            
            int rowsAffected1 = stmt.executeUpdate(sql1);
            int rowsAffected2 = stmt.executeUpdate(sql2);
            int rowsAffected3 = stmt.executeUpdate(sql3);
            int rowsAffected4 = stmt.executeUpdate(sql4);
            int rowsAffected5 = stmt.executeUpdate(sql5);
            
            System.out.println("Rows Affected1: " + rowsAffected1);
            System.out.println("Rows Affected2: " + rowsAffected2);
            System.out.println("Rows Affected3 " + rowsAffected3);
            System.out.println("Rows Affected3 " + rowsAffected4);
            System.out.println("Rows Affected5 " + rowsAffected5);
            System.out.println("Rows Affected6 " + rowsAffected6);
            System.out.println("Rows Affected7 " + rowsAffected7);
            System.out.println("Update Completed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //calculate overtime
        float ot = 0;
        try {
            Date thresholdTime = dateFormat.parse(shiftEnd);
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
        float ndAmt = (float) (nd * (rpH * 1.10));

        //lagyan ko lang linya hehe ======================================================================
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
            nd + " (" + ndAmt + ")",
            0, //spc hol/sun
            0, //spc hol/sun ot
            0, //leg hol
            gross,
            0, //sss regular
            0, //sss mpf
            0, //philhealth
            0, //w/tax
            0, //sss loan-s
            0, //sss loan-c
            0, //pagibig contribution
            0, //efund
            0, //pagibig loan-s
            0, //pagibig loan-c
            OD,
            TD,
            AA,
            NP,});
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
        table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Rate", "Days Worked", "Late (Min)", "Regular Wage", "Overtime", "Night Diff.", "Special Holiday/Sunday", "Special Holiday/Sunday (Overtime)", "Legal Holiday", "Gross Income", "SSS Contribution (Regular)", "SSS Contribution (MPF)", "PhilHealth", "W/tax", "SSS (Loan-S)", "SSS (Loan-S)", "Pag-ibig Contribution", "EFUND", "Pag-ibig (Loan-S)", "Pag-ibig (Loan-C)", "Other Deductions", "Total Deductions", "Adjustment Allowance", "Net Pay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

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

        jButton1.setText("Export");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

           FileOutputStream excelFOU = null;
    BufferedOutputStream excelBOU = null;
    XSSFWorkbook excelJTableExporter = null;

    JFileChooser ExcelFileChooser = new JFileChooser("C:\\Users\\Jon Gleur Tan\\OneDrive\\Desktop\\ExportExcel");
    FileNameExtensionFilter extend = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");

    ExcelFileChooser.setDialogTitle("Save As");

    ExcelFileChooser.setFileFilter(extend);
    int excelchooser = ExcelFileChooser.showSaveDialog(null);

    if (excelchooser == JFileChooser.APPROVE_OPTION) {

        try {
            excelJTableExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = excelJTableExporter.createSheet("Payslip Employees");

            for (int i = 0; i < table.getRowCount(); i++) {
                XSSFRow excelRow = excelSheet.createRow(i);

                for (int j = 0; j < table.getColumnCount(); j++) {
                    XSSFCell excelCell = excelRow.createCell(j);

                    Object value = table.getValueAt(i, j);
                    if (value != null) {
                        excelCell.setCellValue(value.toString());
                    } else {
                        excelCell.setCellValue(""); // Handle null values
                    }
                }
            }

            excelFOU = new FileOutputStream(ExcelFileChooser.getSelectedFile() + ".xlsx");
            excelBOU = new BufferedOutputStream(excelFOU);
            excelJTableExporter.write(excelBOU);
            JOptionPane.showMessageDialog(null, "File Exported Successfully");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "File not found exception: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IO exception: " + ex.getMessage());
        } finally {
            try {
                if (excelBOU != null) {
                    excelBOU.close();
                }
                if (excelFOU != null) {
                    excelFOU.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error closing streams: " + ex.getMessage());
            }
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

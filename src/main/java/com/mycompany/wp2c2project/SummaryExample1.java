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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.table.DefaultTableModel;
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

/**
 *
 * @author jejer
 */
public class SummaryExample1 extends javax.swing.JFrame {

    /**
     * Creates new form SummaryExample
     */
    public SummaryExample1() {
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
        String timeInStr = "8:30"; //sample time in
        String timeOutStr = "17:30"; //sample time out

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

        System.out.println(timeIn);

        long timeInMill = timeIn.getTime();
        long timeOutMill = timeOut.getTime();

        // Calculate hours and minutes
        int timeInHr = (int) (timeInMill / (60 * 60 * 1000));
        int timeInMin = (int) ((timeInMill / (60 * 1000)) % 60);
        int timeOutHr = (int) (timeOutMill / (60 * 60 * 1000));
        int timeOutMin = (int) ((timeOutMill / (60 * 1000)) % 60);

        //calculate day
        float hours = timeOutHr - timeInHr + (float) (timeOutMin - timeInMin) / 60;
        float days = hours / 8;

        //get shift start time from table in ViewRatesDepartments
        ViewRatesDepartments rates = new ViewRatesDepartments();
        String shiftStart = (String) rates.employeeTable.getModel().getValueAt(0, 2);
        String shiftEnd = (String) rates.employeeTable.getModel().getValueAt(0, 3);

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

    private ResultSet fetchDataFromDatabase() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // Example SQL query. Replace it with your actual query.
        String sql = "SELECT Department, Rate, ShiftStart, ShiftEnd, Status, TIN, PhilHealth, SSS, PagIbig, TaxStatus FROM your_table_name";
        PreparedStatement statement = connection.prepareStatement(sql);

        return statement.executeQuery();
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
        ResultSet resultSet = null;

        JFileChooser ExcelFileChooser = new JFileChooser("C:\\Users\\Jon Gleur Tan\\OneDrive\\Desktop\\ExportExcel");
        FileNameExtensionFilter extend = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");

        ExcelFileChooser.setDialogTitle("Save As");

        ExcelFileChooser.setFileFilter(extend);
        int excelchooser = ExcelFileChooser.showSaveDialog(null);

        if (excelchooser == JFileChooser.APPROVE_OPTION) {
            try {
                // Fetch data from the database
                resultSet = fetchDataFromDatabase();

                // Create the Excel workbook and sheet
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("Payslip Employees");

                // Export data from the JTable
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                int colCount = model.getColumnCount();

                for (int i = 0; i < rowCount; i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);

                    for (int j = 0; j < colCount; j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }

                // Move to the next row for data from the database
                int rowIndex = rowCount;

                // Export data from the ResultSet
                while (resultSet.next()) {
                    XSSFRow excelRow = excelSheet.createRow(rowIndex++);
                    excelRow.createCell(0).setCellValue(resultSet.getString("Department"));
                    excelRow.createCell(1).setCellValue(resultSet.getString("Rate"));
                    excelRow.createCell(2).setCellValue(resultSet.getString("ShiftStart"));
                    excelRow.createCell(3).setCellValue(resultSet.getString("ShiftEnd"));
                    excelRow.createCell(4).setCellValue(resultSet.getString("Status"));
                    excelRow.createCell(5).setCellValue(resultSet.getString("TIN"));
                    excelRow.createCell(6).setCellValue(resultSet.getString("PhilHealth"));
                    excelRow.createCell(7).setCellValue(resultSet.getString("SSS"));
                    excelRow.createCell(8).setCellValue(resultSet.getString("PagIbig"));
                    excelRow.createCell(9).setCellValue(resultSet.getString("TaxStatus"));
                }

                // Write to the Excel file
                excelFOU = new FileOutputStream(ExcelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "File Exported Successfully");

            } catch (SQLException | FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
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
            java.util.logging.Logger.getLogger(SummaryExample1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SummaryExample1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SummaryExample1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SummaryExample1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SummaryExample1().setVisible(true);
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

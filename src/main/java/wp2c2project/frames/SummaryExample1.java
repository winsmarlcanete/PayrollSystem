/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import wp2c2project.classes.Main;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.BufferedOutputStream;
import java.io.File;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
        ViewEmployees rates = new ViewEmployees();
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

    XSSFSheet sheet;
    CellStyle cellStyle;

    private void insertCell(Row rowP, int columnP, String value) {
        Cell cell = rowP.createCell(columnP);
        if (cell != null) {
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
            System.out.println("Cell set at (" + rowP + ", " + columnP + "): " + value);
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
        table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Name", "Rate", "Days Worked", "Late (Min)", "Regular Wage", "Overtime", "Night Diff.", "Special Holiday/Sunday", "Special Holiday/Sunday (Overtime)", "Legal Holiday", "Gross Income", "SSS Contribution (Regular)", "SSS Contribution (MPF)", "PhilHealth", "W/tax", "SSS (Loan-S)", "SSS (Loan-S)", "Pag-ibig Contribution", "EFUND", "Pag-ibig (Loan-S)", "Pag-ibig (Loan-C)", "Other Deductions", "Total Deductions", "Adjustment Allowance", "Net Pay"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
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

        Connection sgconn = Main.connectSG();
        PreparedStatement st;
        ResultSet resultSet;
        XSSFWorkbook wb = new XSSFWorkbook();

        try {
            // Fetch data from the database
            st = sgconn.prepareStatement("SELECT * FROM `summary` WHERE `period` = ?");
            st.setString(1, "2023-10-20~2023-11-05");
            resultSet = st.executeQuery();

            // Debug prints
            if (!resultSet.next()) {
                System.out.println("No data found in the ResultSet for the specified period.");
                return; // or handle it accordingly
            }

            // Create the sheet
            XSSFSheet sheet = wb.createSheet("Payslip Employees");
            XSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            int rowNum = 0;
            Row rowR;

            do {
                // from summary database
                int empId = resultSet.getInt("empId");
                String dayTot = resultSet.getString("dayTot");
                String lateTot = resultSet.getString("lateTot");
                String lateAmt = resultSet.getString("lateAmt");
                String regWage = resultSet.getString("regWage");
                String otTot = resultSet.getString("otTot");
                String otAmt = resultSet.getString("otAmt");
                String ndTot = resultSet.getString("ndTot");
                String ndAmt = resultSet.getString("ndAmt");
                String spcTot = resultSet.getString("spcTot");
                String spcAmt = resultSet.getString("spcAmt");
                String spcOtTot = resultSet.getString("spcOtTot");
                String spcOtAmt = resultSet.getString("spcOtAmt");
                String legTot = resultSet.getString("legTot");
                String legAmt = resultSet.getString("legAmt");
                String gross = resultSet.getString("gross");
                String sssReg = resultSet.getString("sssReg");
                String sssMpf = resultSet.getString("sssMpf");
                String phealth = resultSet.getString("phealth");
                String wtax = resultSet.getString("wtax");
                String sssLoanS = resultSet.getString("sssLoanS");
                String sssLoanC = resultSet.getString("sssLoanC");
                String pagibigCont = resultSet.getString("pagibigCont");
                String efund = resultSet.getString("efund");
                String pagibigLoanS = resultSet.getString("pagibigLoanS");
                String pagibigLoanC = resultSet.getString("pagibigLoanC");
                String otherDedt = resultSet.getString("otherDedt");
                String dedtTot = resultSet.getString("dedtTot");
                String allowance = resultSet.getString("allowance");
                String netPay = resultSet.getString("netPay");

                // Fetch additional data from the employee database
                st = sgconn.prepareStatement("SELECT * FROM `employee` WHERE `empId` = ?");
                st.setInt(1, empId);
                ResultSet employeeResultSet = st.executeQuery();

                // Debug prints
                if (!employeeResultSet.next()) {
                    System.out.println("No employee data found for empId: " + empId);
                    continue; // skip to the next iteration
                }

                String department = employeeResultSet.getString("department");
                String name = employeeResultSet.getString("name");
                String rate = employeeResultSet.getString("rate");
                String status = employeeResultSet.getString("status");
                String tin = employeeResultSet.getString("tin");
                String philHealth = employeeResultSet.getString("philHealth");
                String sss = employeeResultSet.getString("sss");
                String pagibig = employeeResultSet.getString("pagibig");
                String taxStatus = employeeResultSet.getString("taxStatus");

                // Set up the sheet and write data
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Department");
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
                insertCell(rowR, 2, "Payroll");
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 6));
                insertCell(rowR, 4, "Pay Period");
                insertCell(rowR, 7, "Pay Date");
                insertCell(rowR, 8, "Tax Status");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, department);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
                insertCell(rowR, 2, status);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 6));
                insertCell(rowR, 4, "2023-10-20~2023-11-05");
                insertCell(rowR, 7, "Nov. 10, 2022"); // date now
                insertCell(rowR, 8, taxStatus);

                rowNum++;

                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Employee Name:");
                insertCell(rowR, 2, "Emp.ID");
                insertCell(rowR, 3, "Daily Rate:");
                insertCell(rowR, 4, "TIN:");
                insertCell(rowR, 5, "Phil. Health No.");
                insertCell(rowR, 6, "SSS No.");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;

                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, name);
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, rate);
                insertCell(rowR, 4, tin);
                insertCell(rowR, 5, philHealth);
                insertCell(rowR, 6, sss);
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;

                rowR = sheet.createRow(rowNum);

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Days Worked");
                insertCell(rowR, 2, dayTot);
                insertCell(rowR, 3, regWage);
                insertCell(rowR, 4, "SSS Cont.:");
                insertCell(rowR, 5, sssReg);
                insertCell(rowR, 6, "Allowance: ");
                insertCell(rowR, 7, "-");
                insertCell(rowR, 8, " ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Late in Mins:");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, " ");
                insertCell(rowR, 4, "P.Health Cont.:");
                insertCell(rowR, 5, phealth);
                insertCell(rowR, 6, "SSS prem. Adj.");
                insertCell(rowR, 7, "-");
                insertCell(rowR, 8, " ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Basic Pay");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, regWage);
                insertCell(rowR, 4, "W/Tax");
                insertCell(rowR, 5, " ");
                insertCell(rowR, 6, " ");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "OT Regular");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, otTot);
                insertCell(rowR, 4, "Pag-Ibig");
                insertCell(rowR, 5, pagibigCont);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 6, 7));
                insertCell(rowR, 6, "SSS Loan Payments");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Sun./spl./hol.");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, spcTot);
                insertCell(rowR, 4, "Vale: ");
                insertCell(rowR, 5, " ");
                insertCell(rowR, 6, " ");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Leg Holiday");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, legTot);
                insertCell(rowR, 4, "Comp. Loan");
                insertCell(rowR, 5, " ");
                insertCell(rowR, 6, "Salary Loan");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, "Received by: ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "OT Holiday");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, otTot);
                insertCell(rowR, 4, "SGC EFund");
                insertCell(rowR, 5, efund);
                insertCell(rowR, 6, "Calamity Loan");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;
                rowR = sheet.createRow(rowNum);
                insertCell(rowR, 1, "Night Diff'l");
                insertCell(rowR, 2, " ");
                insertCell(rowR, 3, ndTot);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 5));
                insertCell(rowR, 4, "Other Deductions:");
                insertCell(rowR, 6, "Stock Loan");
                insertCell(rowR, 7, " ");
                insertCell(rowR, 8, " ");

                rowNum++;

                rowR = sheet.createRow(rowNum);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));
                insertCell(rowR, 1, "GROSS INCOME:");
                insertCell(rowR, 3, gross);
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 5));
                insertCell(rowR, 4, "TOTAL DEDUCTIONS:");
                insertCell(rowR, 6, " ");
                insertCell(rowR, 7, dedtTot);
                insertCell(rowR, 8, netPay);

                rowNum++;
                rowR = sheet.createRow(rowNum);

                sheet.setColumnWidth(1, 20 * 256);
                sheet.setColumnWidth(2, 20 * 256);
                sheet.setColumnWidth(3, 20 * 256);
                sheet.setColumnWidth(4, 25 * 256);
                sheet.setColumnWidth(5, 25 * 256);
                sheet.setColumnWidth(6, 25 * 256);
                sheet.setColumnWidth(7, 20 * 256);
                sheet.setColumnWidth(8, 20 * 256);

                rowNum += 2;

            } while (resultSet.next());

            // Write to the Excel file
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileOutputStream out = new FileOutputStream(file + ".xlsx");
                wb.write(out);
                out.close();
                JOptionPane.showMessageDialog(null, "File Exported Successfully");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(SummaryExample1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

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

    private void insertCell(Row rowR, int i, Float rate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void insertCell(Row rowR, int i, int philHealth) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

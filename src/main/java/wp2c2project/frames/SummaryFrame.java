/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import java.awt.Toolkit;
import wp2c2project.classes.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import wp2c2project.classes.Summary;

/**
 *
 * @author jejer
 */
public class SummaryFrame extends javax.swing.JFrame {

    /**
     * Creates new form Summary
     *
     */
    Connection sgconn = Main.connectSG();
    PreparedStatement st;
    ResultSet resultSet;

    String period;
    int empId;

    public SummaryFrame() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/wp2c2_logo.png")));
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
                float rph = resultSet.getFloat("rph");
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
                    rate + " (" + rph + ")",
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
            Logger.getLogger(SummaryFrame.class.getName()).log(Level.SEVERE, null, ex);
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

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        periodLb = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        nameLb = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WP2C2 Payroll");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        periodLb.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        periodLb.setText("Period");

        jLabel3.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel3.setText("Payroll");

        jButton1.setBackground(new java.awt.Color(232, 115, 26));
        jButton1.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton3.setText("Edit time card");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        nameLb.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        nameLb.setText("Select an employee");

        jButton4.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton4.setText("Export");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nameLb))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(749, 749, 749)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(periodLb, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(periodLb, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLb, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = jTable1.getRowCount();
        for (int i = row - 1; i >= 0; i--) {
            try {
                empId = (int) jTable1.getValueAt(i, 0);
                float gross = Float.parseFloat(jTable1.getValueAt(i, 12).toString());
                float sssReg = Float.parseFloat(jTable1.getValueAt(i, 13).toString());
                float sssMpf = Float.parseFloat(jTable1.getValueAt(i, 14).toString());
                float pHealth = Float.parseFloat(jTable1.getValueAt(i, 15).toString());
                float wtax = Float.parseFloat(jTable1.getValueAt(i, 16).toString());
                float sssLoanS = Float.parseFloat(jTable1.getValueAt(i, 17).toString());
                float sssLoanC = Float.parseFloat(jTable1.getValueAt(i, 18).toString());
                float pagibigCont = Float.parseFloat(jTable1.getValueAt(i, 19).toString());
                float efund = Float.parseFloat(jTable1.getValueAt(i, 20).toString());
                float pagibigLoanS = Float.parseFloat(jTable1.getValueAt(i, 21).toString());
                float pagibigLoanC = Float.parseFloat(jTable1.getValueAt(i, 22).toString());
                float otherDedt = Float.parseFloat(jTable1.getValueAt(i, 23).toString());
                float allowance = Float.parseFloat(jTable1.getValueAt(i, 25).toString());

                float dedtTot = sssMpf + sssMpf + pHealth + wtax + sssLoanS + sssLoanC + pagibigCont + efund + pagibigLoanS + pagibigLoanC + otherDedt;
                float netPay = gross - dedtTot + allowance;

                jTable1.setValueAt(dedtTot, i, 24);
                jTable1.setValueAt(netPay, i, 26);

                st = (PreparedStatement) sgconn.prepareStatement(
                        "UPDATE `summary` SET "
                        + "`sssReg` = ?, "
                        + "`sssMpf` = ?, "
                        + "`pHealth` = ?, "
                        + "`wtax` = ?, "
                        + "`sssLoanS` = ?, "
                        + "`sssLoanC` = ?, "
                        + "`pagibigCont` = ?, "
                        + "`efund` = ?, "
                        + "`pagibigLoanS` = ?, "
                        + "`pagibigLoanC` = ?, "
                        + "`otherDedt` = ?, "
                        + "`allowance` = ?, "
                        + "`dedtTot` = ?, "
                        + "`netPay` = ? "
                        + "WHERE `empId` = ?"
                );
                st.setFloat(1, sssReg);
                st.setFloat(2, sssMpf);
                st.setFloat(3, pHealth);
                st.setFloat(4, wtax);
                st.setFloat(5, sssLoanS);
                st.setFloat(6, sssLoanC);
                st.setFloat(7, pagibigCont);
                st.setFloat(8, efund);
                st.setFloat(9, pagibigLoanS);
                st.setFloat(10, pagibigLoanC);
                st.setFloat(11, otherDedt);
                st.setFloat(12, allowance);
                st.setFloat(13, dedtTot);
                st.setFloat(14, netPay);
                st.setInt(15, empId);
                st.executeUpdate();

                // update summary time calculation
                PreparedStatement summaryStatement = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `summary`");
                ResultSet summaryResultSet = summaryStatement.executeQuery();
                while (summaryResultSet.next()) {
                    int empId = summaryResultSet.getInt("empId");
                    Summary summary = new Summary(0, empId, null, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                    summary.calcTime();
                    summary.updateTime();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SummaryFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SummaryHome summaryHome = new SummaryHome();
        summaryHome.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        IndividualTimeCard itcFrame = new IndividualTimeCard(period, empIdSelected);
        itcFrame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    CellStyle cellStyle;

    private void insertCell(Row rowP, int columnP, String value) {
        Cell cell = rowP.createCell(columnP);
        if (cell != null) {
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
            System.out.println("Cell set at (" + rowP + ", " + columnP + "): " + value);
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        XSSFWorkbook wb = new XSSFWorkbook();

        try {
            // Fetch data from the database
            st = sgconn.prepareStatement("SELECT * FROM `summary` WHERE `period` = ?");
            st.setString(1, period);
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
                insertCell(rowR, 4, period);
                insertCell(rowR, 7, "Dec. 04, 2023"); // date now
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
    }//GEN-LAST:event_jButton4ActionPerformed

    int empIdSelected;
    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        String name = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2);
        empIdSelected = (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        System.out.println("table click success");
        nameLb.setText(name);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jTable1MousePressed

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
            java.util.logging.Logger.getLogger(SummaryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SummaryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SummaryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SummaryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SummaryFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel nameLb;
    private javax.swing.JLabel periodLb;
    // End of variables declaration//GEN-END:variables
}

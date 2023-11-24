/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author jejer
 */
public class SummaryHome extends javax.swing.JFrame implements SetupEmployeeCallback {

    /**
     * Creates new form Summary
     */
    public SummaryHome() {
        initComponents();
    }

    Sheet sheet = null;
    Workbook wb = null;
    ResultSet resultSet;
    Connection sgconn = Main.connectSG();
    PreparedStatement st;

    @Override
    public void onSetupEmployeeFinished() {
        readTime();
    }

    private String readCell(int rowP, int columnP) {
        Row row = sheet.getRow(rowP);
        Cell cell = row.getCell(columnP);
        String data = null;

        if (cell == null) {
            JOptionPane.showMessageDialog(this, "Please check if the file is correct", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                case NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // Handle as date and format as time
                        java.util.Date dateCell = cell.getDateCellValue();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateCell);
                        calendar.add(Calendar.MINUTE, 1);
                        dateCell = calendar.getTime();
                        String timeFormat = "HH:mm";
                        data = new java.text.SimpleDateFormat(timeFormat).format(dateCell);
                    } else {
                        // Handle as numeric
                        data = String.valueOf(cell.getNumericCellValue());
                    }
                }
                case STRING ->
                    data = cell.getStringCellValue();
            }
        }
        return data;
    }

    String name;
    String id;
    String department;

    int infoCol1 = 9;
    int infoCol2 = 1;
    int readSheet = 2;

    private void readEmployeeInfo() {
        sheet = wb.getSheetAt(readSheet);

        name = readCell(3, infoCol1);
        id = readCell(4, infoCol1);
        department = readCell(3, infoCol2);

        if (Double.parseDouble(id) < 1000 && sheet != null) {
            if (sgconn != null) {
                try {
                    //find if record exists
                    st = sgconn.prepareStatement("SELECT EXISTS(SELECT * FROM `employee` WHERE `id` = ?)");
                    st.setString(1, id);
                    resultSet = st.executeQuery();

                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        //transfer data to SetupEmployee.java to register
                        SetupEmployee setupEmployeeFrame = new SetupEmployee(this);
                        setupEmployeeFrame.setCallback(this);
                        setupEmployeeFrame.setVisible(true);
                    } else {
                        readTime();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login_Jon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "There were errors connecting to the database. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Time card uploaded successfully", "Success", JOptionPane.PLAIN_MESSAGE);
        }
    }

    String date;
    String dateType = "0";
    String timeIn;
    String timeOut = null;
    String shiftStart;
    String shiftEnd;

    int dateCol = 0;
    int timeInCol = 1;
    int timeOutColStart = 12;
    int timeOutColEnd = 3;

    //time card 1
    //date cells 12,0 - 42, 0
    //timeIn cells 12,1 - 42, 1
    //timeOut cells 12,12 - 42, 3
    //time card 2 row + 15
    //time card 3 row + 15
    private void readTime() {
        try {
            for (int i = 12; i <= 42; i++) {
                date = readCell(i, dateCol);

                if (date != null) {
                    //check first if date data doesnt exist in the data base yet
                    int idCvt = (int) Double.parseDouble(id);
                    st = (PreparedStatement) sgconn.prepareStatement("SELECT COUNT(*) FROM `time_card` WHERE `id` = ? AND `date` = ?");
                    st.setInt(1, idCvt);
                    st.setString(2, date);
                    resultSet = st.executeQuery();
                    resultSet.next();
                    int rowCount = resultSet.getInt(1);

                    if (rowCount == 0 && date != null) {
                        //timeIn
                        timeIn = readCell(i, timeInCol);
                        if (timeIn == null) {
                            timeIn = "none";
                        }

                        //timeOut
                        timeOut = null;
                        for (int j = timeOutColStart; j >= timeOutColEnd && (timeOut == null || "".equals(timeOut)); j--) {
                            timeOut = readCell(i, j);
                        }
                        if (timeOut == null) {
                            timeOut = "none";
                        }

                        //shiftStart
                        st = (PreparedStatement) sgconn.prepareStatement("SELECT shiftStart FROM employee WHERE id = ?;");
                        st.setInt(1, idCvt);
                        resultSet = st.executeQuery();
                        resultSet.next();
                        shiftStart = resultSet.getString("shiftStart");
                        System.out.println("getting shiftStart success");

                        //shiftEnd
                        st = (PreparedStatement) sgconn.prepareStatement("SELECT shiftEnd FROM employee WHERE id = ?;");
                        st.setInt(1, idCvt);
                        resultSet = st.executeQuery();
                        resultSet.next();
                        shiftEnd = resultSet.getString("shiftEnd");
                        System.out.println("getting shiftEnd success");

                        //for debugging
                        System.out.println("id: " + idCvt);
                        System.out.println("date: " + date);
                        System.out.println("timeIn: " + timeIn);

                        //insert to table
                        st = (PreparedStatement) sgconn.prepareStatement(
                                "INSERT INTO `time_card`"
                                + "(`id`, `date`, `dateType`, `timeIn`, `timeOut`, `shiftStart`,`shiftEnd`) "
                                + "VALUES (?,?,?,?,?,?,?)"
                        );
                        st.setString(1, id);
                        st.setString(2, date);
                        st.setString(3, dateType);
                        st.setString(4, timeIn);
                        st.setString(5, timeOut);
                        st.setString(6, shiftStart);
                        st.setString(7, shiftEnd);
                        st.executeUpdate();
                        System.out.println("insert data success");
                    } else {
                        System.out.println("date data already exists");
                    }
                }
                System.out.println("date is null");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login_Jon.class.getName()).log(Level.SEVERE, null, ex);
        }
        iterateEmpyInfoCols();
        readEmployeeInfo();
    }

    private void iterateEmpyInfoCols() {
        infoCol1 += 15;
        infoCol2 += 15;
        if (infoCol1 > 39) {
            infoCol1 = 9;
            infoCol2 = 1;
            readSheet++;
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

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        fileName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jButton1.setText("Upload time card");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Earlier summaries");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("October 6-20");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Inter", 1, 24)); // NOI18N
        jLabel2.setText("Summary");

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        fileName.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        fileName.setText("No file selected");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fileName)))
                .addContainerGap(365, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SummaryExample frame = new SummaryExample();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AdminHome frame = new AdminHome();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //upload excel
        JFileChooser excelFileChooser = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel Files", "xls");
        excelFileChooser.setFileFilter(fnef);
        excelFileChooser.showOpenDialog(null);
        File excelFile = excelFileChooser.getSelectedFile();
        String filename = excelFile.getName();
        fileName.setText(filename);

        //read excel
        String filePath = excelFile.getAbsolutePath();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            wb = new HSSFWorkbook(fis);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SummaryHome.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(SummaryHome.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //check if file is right
        if (wb != null) {
            sheet = wb.getSheetAt(2);
        }
        if (!"Employee Attendance Table".equals(readCell(0, 11))) {
            JOptionPane.showMessageDialog(this, "Please check if the file is correct", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {

            //initiate read employee info loop
            readEmployeeInfo();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the FlatLaf look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatLightLaf.setup();
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SummaryHome().setVisible(true);
            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

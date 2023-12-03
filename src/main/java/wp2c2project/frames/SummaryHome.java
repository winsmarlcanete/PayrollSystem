/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
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

import wp2c2project.classes.Employee;
import wp2c2project.classes.Timecard;

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

    Connection sgconn = Main.connectSG();
    ResultSet resultSet;
    PreparedStatement st;

    Sheet sheet = null;
    Workbook wb = null;

    @Override
    public void onSetupEmployeeFinished() {
        readTime();
    }

    private String readCell(int rowP, int columnP) {
        Row row = sheet.getRow(rowP);
        Cell cell = row != null ? row.getCell(columnP) : null;
        String data = null;

        if (cell != null) {
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
    int id;
    String department;
    String period;

    int infoCol1 = 9;
    int infoCol2 = 1;
    int readSheet = 2;

    private void readEmployeeInfo() {
        sheet = wb.getSheetAt(readSheet);

        name = readCell(3, infoCol1) != null ? readCell(3, infoCol1) : "none";
        id = (int) Float.parseFloat(readCell(4, infoCol1) != null ? readCell(4, infoCol1) : "1001");
        department = readCell(3, infoCol2) != null ? readCell(3, infoCol2) : "none";

        System.out.println("id: " + id);

        if (id < 1000 && sheet != null) {
            if (sgconn != null) {
                try {
                    //find if record exists
                    st = sgconn.prepareStatement("SELECT EXISTS(SELECT * FROM `employee` WHERE `empId` = ?)");
                    st.setInt(1, id);
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
            //JOptionPane.showMessageDialog(this, "Time card uploaded successfully!", "Success", JOptionPane.PLAIN_MESSAGE);
            Summary summaryFrame = new Summary(this);
            summaryFrame.showSummary(period);
            summaryFrame.setVisible(true);
            dispose();
        }
    }

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
                String date;
                int dateType = 0;
                String shiftStart;
                String shiftEnd;
                String timeIn;
                String timeOut = null;

                date = readCell(i, dateCol);

                if (date != null) {
                    st = (PreparedStatement) sgconn.prepareStatement("SELECT COUNT(*) FROM `time_card` WHERE `empId` = ? AND `date` = ?");
                    st.setInt(1, id);
                    st.setString(2, date);
                    resultSet = st.executeQuery();
                    resultSet.next();
                    int rowCount = resultSet.getInt(1);

                    System.out.println("date: " + date);

                    //check first if date data doesnt exist in the database yet
                    if (rowCount == 0) {
                        // !!get time in/out & shift start/end!!
                        //get timeIn/Out
                        timeIn = readCell(i, timeInCol);
                        for (int j = timeOutColStart; j >= timeOutColEnd && (timeOut == null || "".equals(timeOut)); j--) {
                            timeOut = readCell(i, j);
                        }
                        timeIn = timeIn != null ? timeIn : "";
                        timeOut = timeOut != null ? timeOut : "";

                        //get shift start & end
                        st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM employee WHERE empId = ?;");
                        st.setInt(1, id);
                        resultSet = st.executeQuery();
                        resultSet.next();
                        shiftStart = resultSet.getString("shiftStart");
                        shiftEnd = resultSet.getString("shiftEnd");
                        System.out.println("getting shift success");

                        //for debugging
                        System.out.println("timeIn: " + timeIn);

                        //set dateType = 1 if date is sunday
                        String[] dateArr = date.split(" ");
                        String dayOfWeek = dateArr[1];
                        dateType = dayOfWeek.equalsIgnoreCase("Su") ? 1 : 0;

                        // !!calculation for attendance!!
                        float day = 0;
                        float ot = 0;
                        float late = 0;
                        float nd = 0;

                        //check if there is timeIn before doing attendance calculation
                        if (!"".equals(timeIn) && !"".equals(timeOut)) {
                            //Time in & time out
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            dateFormat.setTimeZone(TimeZone.getTimeZone("PT"));

                            Date timeInDate = dateFormat.parse(timeIn);
                            Date timeOutDate = dateFormat.parse(timeOut);
                            Date shiftStartThreshold = dateFormat.parse(shiftStart);
                            Date shiftEndThreshold = dateFormat.parse(shiftEnd);

                            Date startOverlap;
                            Date endOverlap;

                            //convert to hours and minutes
                            long timeInMill = timeInDate.getTime();
                            long timeOutMill = timeOutDate.getTime();

                            //calculate day (needs proper rounding off logic)
                            if (dateType == 0) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(shiftStartThreshold);
                                calendar.add(Calendar.MINUTE, 30);
                                Date shiftStartLateThreshold = calendar.getTime();

                                startOverlap = timeInDate.after(shiftStartLateThreshold) ? timeInDate : shiftStartThreshold;
                                endOverlap = timeOutDate.before(shiftEndThreshold) ? timeOutDate : shiftEndThreshold;

                                long durationMillis = endOverlap.getTime() - startOverlap.getTime();
                                float durationHours = (float) durationMillis / (60 * 60 * 1000);
                                durationHours = durationHours < 0 ? +24 : durationHours;
                                day = durationHours / 9;
                            } else {
                                day = 1;
                            }

                            //calculate overtime (✅ working omg)
                            if (timeOutDate.after(shiftEndThreshold)) {
                                ot = roundToNearestHalf((float) (timeOutMill - shiftEndThreshold.getTime()) / (60 * 60 * 1000));
                            }

                            //calculate late
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(shiftStartThreshold);
                            calendar.add(Calendar.MINUTE, 5);
                            Date thresholdTimeLate = calendar.getTime();
                            if (timeInDate.after(thresholdTimeLate)) {
                                late = roundToNearestHalf((float) (timeInMill - shiftStartThreshold.getTime()) / (60 * 1000)) * 2;
                            }

                            //calculate nd
                            Date ndStartTreshold = dateFormat.parse("22:00");
                            Date ndEndTreshold = dateFormat.parse("06:00");
                            if (timeInDate.after(timeOutDate)) { //time crossing midnight
                                startOverlap = timeInDate.after(ndStartTreshold) ? timeInDate : ndStartTreshold;
                                endOverlap = timeOutDate.before(ndEndTreshold) ? timeOutDate : ndEndTreshold;
                                nd = setDurationHours(startOverlap, endOverlap);
                            } else { //time not crossing midnight
                                if (timeOutDate.after(ndStartTreshold)) {
                                    startOverlap = timeInDate.after(ndStartTreshold) ? timeInDate : ndStartTreshold;
                                    nd = setDurationHours(startOverlap, timeOutDate);
                                }
                                if (timeInDate.before(ndEndTreshold)) {
                                    endOverlap = timeOutDate.before(ndEndTreshold) ? timeOutDate : ndEndTreshold;
                                    nd = setDurationHours(timeInDate, endOverlap);
                                }
                            }
                        }

                        // !!insert to time card database table!!
                        st = (PreparedStatement) sgconn.prepareStatement(
                                "INSERT INTO `time_card`("
                                + "`empId`, "
                                + "`date`, "
                                + "`dateType`, "
                                + "`timeIn`, "
                                + "`timeOut`, "
                                + "`shiftStart`,"
                                + "`shiftEnd`, "
                                + "`day`, "
                                + "`late`, "
                                + "`ot`, "
                                + "`nd` "
                                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?)"
                        );
                        st.setInt(1, id);
                        st.setString(2, date);
                        st.setInt(3, dateType);
                        st.setString(4, timeIn);
                        st.setString(5, timeOut);
                        st.setString(6, shiftStart);
                        st.setString(7, shiftEnd);
                        st.setFloat(8, day);
                        st.setFloat(9, late);
                        st.setFloat(10, ot);
                        st.setFloat(11, nd);
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
        } catch (ParseException ex) {
            Logger.getLogger(SummaryHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        //iterate columns
        infoCol1 += 15;
        infoCol2 += 15;

        dateCol += 15;
        timeInCol += 15;
        timeOutColStart += 15;
        timeOutColEnd += 15;
        if (infoCol1 > 39) {
            infoCol1 = 9;
            infoCol2 = 1;

            dateCol = 0;
            timeInCol = 1;
            timeOutColStart = 12;
            timeOutColEnd = 3;
            readSheet++;
        }
        getTotal();
        readEmployeeInfo();
    }

    private static float roundToNearestHalf(float number) {
        return (float) (Math.floor(number * 2) / 2.0);
    }

    private static float setDurationHours(Date startOverlap, Date endOverlap) {
        long durationMillis = endOverlap.getTime() - startOverlap.getTime();
        float durationHours = (float) durationMillis / (60 * 60 * 1000);
        durationHours = durationHours < 0 ? +24 : durationHours;
        return durationHours;
    }

    private void getTotal() {
        try {
            st = (PreparedStatement) sgconn.prepareStatement("SELECT COUNT(*) FROM `summary` WHERE `empId` = ? AND `period` = ?");
            st.setInt(1, id);
            st.setString(2, period);
            resultSet = st.executeQuery();
            resultSet.next();

            //check if payroll period already exists before proceeding
            if (resultSet.getInt(1) == 0) {
                float dayTot = 0;
                float lateTot = 0;
                float otTot = 0;
                float ndTot = 0;
                float spcTot = 0;
                float spcOtTot = 0;
                float legTot = 0;

                //calculate total
                st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `time_card` WHERE `empId` = ?");
                st.setInt(1, id);
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    dayTot += resultSet.getFloat("day");
                    lateTot += resultSet.getFloat("late");
                    otTot += resultSet.getFloat("ot");
                    ndTot += resultSet.getFloat("nd");
                    spcTot += resultSet.getFloat("spc");
                    spcOtTot += resultSet.getFloat("spcOt");
                    legTot += resultSet.getFloat("leg");
                }

                System.out.println("dayTot: " + dayTot);
                System.out.println("lateTot: " + lateTot);
                System.out.println("otTot: " + otTot);
                System.out.println("ndTot: " + ndTot);
                System.out.println("spcTot: " + spcTot);
                System.out.println("spcOtTot: " + spcOtTot);
                System.out.println("legTot: " + legTot);

                //calculate rates
                st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `employee` WHERE `empId` = ?");
                st.setInt(1, id);
                resultSet = st.executeQuery();
                resultSet.next();
                float rate = resultSet.getFloat("rate");
                float rph = rate / 8;

                //amt calculation
                st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `rate` WHERE `id` = ?");
                Main main = new Main();
                float otRate = main.selectRateData(2);
                float ndRate = main.selectRateData(3);
                float spcRate = main.selectRateData(4);
                float spcOtRate = main.selectRateData(5);
                float legRate = main.selectRateData(6);

                float lateAmt = rph / 60 * lateTot;
                float regWage = rate * dayTot - lateTot;

                float otAmt = rph * otRate * otTot;
                float ndAmt = rph * ndRate * ndTot;
                float spcAmt = rph * spcRate * spcTot;
                float spcOtAmt = rph * spcOtRate * spcOtTot;
                float legAmt = rph * legRate * legTot;
                float gross = regWage + otAmt + ndAmt + spcAmt + spcOtAmt + legAmt;

                float netPay = gross;

                //insert to summary database    
                st = (PreparedStatement) sgconn.prepareStatement(
                        "INSERT INTO `summary`("
                        + "`empId`, "
                        + "`period`, "
                        + "`rph`, "
                        + "`dayTot`, "
                        + "`lateTot`, "
                        + "`otTot`, "
                        + "`ndTot`, "
                        + "`spcTot`, "
                        + "`spcOtTot`, "
                        + "`legTot`, "
                        + "`lateAmt`, "
                        + "`otAmt`, "
                        + "`ndAmt`, "
                        + "`spcAmt`, "
                        + "`spcOtAmt`, "
                        + "`legAmt`, "
                        + "`regWage`, "
                        + "`gross`, "
                        + "`netPay` "
                        + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                );
                st.setInt(1, id);
                st.setString(2, period);
                st.setFloat(3, rph);
                st.setFloat(4, dayTot);
                st.setFloat(5, lateTot);
                st.setFloat(6, otTot);
                st.setFloat(7, ndTot);
                st.setFloat(8, spcTot);
                st.setFloat(9, spcOtTot);
                st.setFloat(10, legTot);
                st.setFloat(11, lateAmt);
                st.setFloat(12, otAmt);
                st.setFloat(13, ndAmt);
                st.setFloat(14, spcAmt);
                st.setFloat(15, spcOtAmt);
                st.setFloat(16, legAmt);
                st.setFloat(17, regWage);
                st.setFloat(18, gross);
                st.setFloat(19, netPay);
                st.executeUpdate();
            } else {
                System.out.println("summary period already exists: " + period);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SummaryHome.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton2.setText("Uploaded summary");
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
        Summary summaryFrame = new Summary(this);
        summaryFrame.showSummary("2023-10-20~2023-11-05"); //placeholder
        summaryFrame.setVisible(true);
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
        if ("Employee Attendance Table".equals(readCell(0, 11))) {
            period = readCell(4, 1);
            readEmployeeInfo();
        } else {
            JOptionPane.showMessageDialog(this, "Please check if the file is correct", "Error", JOptionPane.INFORMATION_MESSAGE);
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

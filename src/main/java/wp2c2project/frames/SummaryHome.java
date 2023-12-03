/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import wp2c2project.classes.Main;
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import wp2c2project.classes.Summary;
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
        try {
            initComponents();

            //show uploaded summaries
            Set<String> uniquePeriods = new HashSet<>();

            st = sgconn.prepareStatement("SELECT * FROM `summary`");
            resultSet = st.executeQuery();
            while (resultSet.next()) {
                period = resultSet.getString("period");
                if (!uniquePeriods.contains(period)) {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.addRow(new Object[]{period});
                    uniquePeriods.add(period);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SummaryHome.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            SummaryFrame summaryFrame = new SummaryFrame();
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
                int dateType;
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
                        // !!setup time in/out, shift start/end & dateType!!
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

                        //set dateType = 1 if date is sunday
                        String[] dateArr = date.split(" ");
                        String dayOfWeek = dateArr[1];
                        dateType = dayOfWeek.equalsIgnoreCase("Su") ? 1 : 0;

                        //for debugging
                        System.out.println("timeIn: " + timeIn);

                        // !!calculation for attendance!!
                        Timecard timecard = new Timecard(0, id, date, dateType, shiftStart, shiftEnd, timeIn, timeOut, 0, 0, 0, 0, 0, 0, 0);
                        timecard.calcAttendance();

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
                                + "`nd`, "
                                + "`period` "
                                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
                        );
                        st.setInt(1, id);
                        st.setString(2, date);
                        st.setInt(3, dateType);
                        st.setString(4, timeIn);
                        st.setString(5, timeOut);
                        st.setString(6, shiftStart);
                        st.setString(7, shiftEnd);
                        st.setFloat(8, timecard.getDay());
                        st.setFloat(9, timecard.getLate());
                        st.setFloat(10, timecard.getOt());
                        st.setFloat(11, timecard.getNd());
                        st.setString(12, period);
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

    private void getTotal() {
        try {
            st = (PreparedStatement) sgconn.prepareStatement("SELECT COUNT(*) FROM `summary` WHERE `empId` = ? AND `period` = ?");
            st.setInt(1, id);
            st.setString(2, period);
            resultSet = st.executeQuery();
            resultSet.next();

            //check if payroll period already exists before proceeding
            if (resultSet.getInt(1) == 0) {
                //calculate total
                Summary summary = new Summary(0, id, period, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                summary.calcTime();

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
                st.setFloat(3, summary.getRph());
                st.setFloat(4, summary.getDayTot());
                st.setFloat(5, summary.getLateTot());
                st.setFloat(6, summary.getOtTot());
                st.setFloat(7, summary.getNdTot());
                st.setFloat(8, summary.getSpcTot());
                st.setFloat(9, summary.getSpcOtTot());
                st.setFloat(10, summary.getLegTot());
                st.setFloat(11, summary.getLateAmt());
                st.setFloat(12, summary.getOtAmt());
                st.setFloat(13, summary.getNdAmt());
                st.setFloat(14, summary.getSpcAmt());
                st.setFloat(15, summary.getSpcOtAmt());
                st.setFloat(16, summary.getLegAmt());
                st.setFloat(17, summary.getRegWage());
                st.setFloat(18, summary.getGross());
                st.setFloat(19, summary.getNetPay());
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

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        fileName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WP2C2 Payroll");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(232, 115, 26));
        jButton1.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Upload time card");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        jLabel1.setText("Uploaded summaries");
        jLabel1.setToolTipText("");

        jButton3.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        jButton3.setText("Settings");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        fileName.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        fileName.setText("No file selected");

        jTable1.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Period"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(fileName))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ViewEmployees frame = new ViewEmployees();
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
        if ("Employee Attendance Table".equals(readCell(0, 11))) { // if file is correct, then do this
            period = readCell(4, 1);
            readEmployeeInfo();
        } else {
            JOptionPane.showMessageDialog(this, "Please check if the file is correct time card file", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        SummaryFrame summaryFrame = new SummaryFrame();
        summaryFrame.showSummary((String) jTable1.getValueAt(jTable1.getSelectedRow(), 0)); //placeholder
        summaryFrame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jTable1MousePressed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

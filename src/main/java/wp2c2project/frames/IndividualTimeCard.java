/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import wp2c2project.classes.Main;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import wp2c2project.classes.Summary;
import wp2c2project.classes.Timecard;

/**
 *
 * @author jejer
 */
public class IndividualTimeCard extends javax.swing.JFrame {

    /**
     * Creates new form IndividualTimeCard
     */
    ResultSet resultSet;
    Connection sgconn = Main.connectSG();
    PreparedStatement st;

    //employee details
    int empId;
    String empName;
    String dept;
    String period;
    String date;

    //time card
    //total
    float dayTot;
    float lateTot;
    float otTot;
    float ndTot;
    float spcTot;
    float spcOtTot;
    float legTot;

    public IndividualTimeCard(String period, int empId) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/wp2c2_logo.png")));

        this.period = period;
        this.empId = empId;
        periodLb.setText(period);

        try {
            insertTable();
            insertTotal();
        } catch (SQLException ex) {
            Logger.getLogger(IndividualTimeCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertTotal() throws SQLException {
        st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM summary WHERE empId = ?");
        st.setInt(1, empId);
        resultSet = st.executeQuery();
        resultSet.next();
        dayTot = resultSet.getFloat("dayTot");
        lateTot = resultSet.getFloat("lateTot");
        otTot = resultSet.getFloat("otTot");
        ndTot = resultSet.getFloat("ndTot");
        spcTot = resultSet.getFloat("spcTot");
        spcOtTot = resultSet.getFloat("spcOtTot");
        legTot = resultSet.getFloat("legTot");

        dayTotLb.setText(String.valueOf(dayTot));
        lateTotLb.setText(String.valueOf(lateTot));
        otTotLb.setText(String.valueOf(otTot));
        ndTotLb.setText(String.valueOf(ndTot));
        spcTotLb.setText(String.valueOf(spcTot));
        spcOtTotLb.setText(String.valueOf(spcOtTot));
        legTotLb.setText(String.valueOf(legTot));
    }

    private void insertTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) idvlTCTable.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        System.out.println(empId);

        //get employee name and department
        st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM employee WHERE empId = ?");
        st.setInt(1, empId);
        resultSet = st.executeQuery();
        resultSet.next();
        empName = resultSet.getString("name");
        dept = resultSet.getString("department");
        nameLabel.setText(empName);
        deptLabel.setText(dept);

        //add row to table
        st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM time_card WHERE empId = ? AND period = ?");
        st.setInt(1, empId);
        st.setString(2, period);
        resultSet = st.executeQuery();
        while (resultSet.next()) {
            int dateId = resultSet.getInt("dateId");
            String date = resultSet.getString("date");
            int dateType = resultSet.getInt("dateType");
            String shiftStart = resultSet.getString("shiftStart");
            String shiftEnd = resultSet.getString("shiftEnd");
            String timeIn = resultSet.getString("timeIn");
            String timeOut = resultSet.getString("timeOut");
            day = resultSet.getFloat("day");
            late = resultSet.getFloat("late");
            ot = resultSet.getFloat("ot");
            nd = resultSet.getFloat("nd");
            spc = resultSet.getFloat("spc");
            spcOt = resultSet.getFloat("spcOt");
            leg = resultSet.getFloat("leg");

            model.addRow(new Object[]{
                dateId,
                date,
                dateType,
                shiftStart,
                shiftEnd,
                timeIn,
                timeOut,
                day,
                late,
                ot,
                nd,
                spc,
                spcOt,
                leg,});

            System.out.println("getting dateId " + dateId + " success");
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
        nameLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        idvlTCTable = new javax.swing.JTable();
        periodLb = new javax.swing.JLabel();
        deptLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        periodLabel1 = new javax.swing.JLabel();
        periodLabel2 = new javax.swing.JLabel();
        periodLabel3 = new javax.swing.JLabel();
        periodLabel4 = new javax.swing.JLabel();
        periodLabel5 = new javax.swing.JLabel();
        periodLabel6 = new javax.swing.JLabel();
        periodLabel7 = new javax.swing.JLabel();
        periodLabel8 = new javax.swing.JLabel();
        spcOtTotLb = new javax.swing.JLabel();
        legTotLb = new javax.swing.JLabel();
        spcTotLb = new javax.swing.JLabel();
        ndTotLb = new javax.swing.JLabel();
        otTotLb = new javax.swing.JLabel();
        lateTotLb = new javax.swing.JLabel();
        dayTotLb = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        nameLabel.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        nameLabel.setText("Name");

        idvlTCTable.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        idvlTCTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date ID", "Date", "Type", "Shift Start", "Shift End", "Time In", "Time Out", "Day", "Late", "Overtime", "Night Differential", "Special Holiday/Sunday", "Special Holiday/Sunday Overtime", "Legal Holiday"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        idvlTCTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(idvlTCTable);

        periodLb.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLb.setText("Payroll period");

        deptLabel.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        deptLabel.setText("Department");

        jButton1.setBackground(new java.awt.Color(232, 115, 26));
        jButton1.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Update");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        periodLabel1.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        periodLabel1.setText("Total");

        periodLabel2.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel2.setText("Days");

        periodLabel3.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel3.setText("Late");

        periodLabel4.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel4.setText("Overtime");

        periodLabel5.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel5.setText("Night Differential");

        periodLabel6.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel6.setText("Special Holiday/Sunday");

        periodLabel7.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel7.setText("Special Holiday/Sunday Overtime");

        periodLabel8.setFont(new java.awt.Font("Inter Medium", 0, 12)); // NOI18N
        periodLabel8.setText("Legal Holiday");

        spcOtTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        spcOtTotLb.setText("0");

        legTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        legTotLb.setText("0");

        spcTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        spcTotLb.setText("0");

        ndTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        ndTotLb.setText("0");

        otTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        otTotLb.setText("0");

        lateTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        lateTotLb.setText("0");

        dayTotLb.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        dayTotLb.setText("0");

        jButton2.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton2.setText("Back");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(periodLabel1)
                            .addComponent(periodLabel2)
                            .addComponent(periodLabel3)
                            .addComponent(periodLabel4)
                            .addComponent(periodLabel5)
                            .addComponent(periodLabel6)
                            .addComponent(periodLabel8)
                            .addComponent(periodLabel7))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(legTotLb)
                                    .addComponent(spcOtTotLb))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spcTotLb)
                                    .addComponent(ndTotLb)
                                    .addComponent(otTotLb)
                                    .addComponent(lateTotLb)
                                    .addComponent(dayTotLb))
                                .addGap(418, 418, 418)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(deptLabel)
                                        .addComponent(periodLb)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(periodLb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deptLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(periodLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(dayTotLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lateTotLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(otTotLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ndTotLb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spcTotLb)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(periodLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spcOtTotLb, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(periodLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(legTotLb, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public Object GetData(int row_index, int col_index) {
        return idvlTCTable.getModel().getValueAt(row_index, col_index);
    }

    int dateId;
    int dateType;
    String shiftStart;
    String shiftEnd;
    String timeIn;
    String timeOut;

    float day;
    float late;
    float ot;
    float nd;
    float spc;
    float spcOt;
    float leg;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) idvlTCTable.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dateId = (int) GetData(i, 0);
                Object data = GetData(i, 2);
                if (data instanceof Integer) {
                    // If the data is an Integer, use it directly
                    dateType = (int) data;
                } else if (data instanceof String string) {
                    // If the data is a String, parse it to an Integer
                    dateType = Integer.parseInt(string);
                }

                shiftStart = String.valueOf(GetData(i, 3));
                shiftEnd = String.valueOf(GetData(i, 4));
                timeIn = String.valueOf(GetData(i, 5));
                timeOut = String.valueOf(GetData(i, 6));

                // if dateType is edited to holiday
                /**
                 * if (dateType >= 1 && 2 >= dateType) { st =
                 * (PreparedStatement) sgconn.prepareStatement( "UPDATE
                 * `time_card` SET " + "`dateType` = ? " + "WHERE `date` = ? AND
                 * `period` = ?" ); st.setInt(1, dateType); st.setString(2,
                 * date); st.setString(3, period); st.executeUpdate(); }
                 */
                // !!calculation for attendance!!
                Timecard timecard = new Timecard(dateId, 0, null, dateType, shiftStart, shiftEnd, timeIn, timeOut, 0, 0, 0, 0, 0, 0, 0);
                timecard.calcAttendance();

                // !!update to time card database table!!
                st = (PreparedStatement) sgconn.prepareStatement(
                        "UPDATE `time_card` SET "
                        + "`dateType` = ?, "
                        + "`timeIn` = ?, "
                        + "`timeOut` = ?, "
                        + "`shiftStart` = ?,"
                        + "`shiftEnd` = ?, "
                        + "`day` = ?, "
                        + "`late` = ?, "
                        + "`ot` = ?, "
                        + "`nd` = ?, "
                        + "`spc` = ?, "
                        + "`spcOt` = ?, "
                        + "`leg` = ? "
                        + " WHERE dateId = ?"
                );
                st.setInt(1, dateType);
                st.setString(2, timeIn);
                st.setString(3, timeOut);
                st.setString(4, shiftStart);
                st.setString(5, shiftEnd);
                st.setFloat(6, timecard.getDay());
                st.setFloat(7, timecard.getLate());
                st.setFloat(8, timecard.getOt());
                st.setFloat(9, timecard.getNd());
                st.setFloat(10, timecard.getSpc());
                st.setFloat(11, timecard.getSpcOt());
                st.setFloat(12, timecard.getLeg());
                st.setInt(13, dateId);
                st.executeUpdate();
            }

            PreparedStatement summaryStatement = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `summary`");
            ResultSet summaryResultSet = summaryStatement.executeQuery();
            while (summaryResultSet.next()) {
                int empId = summaryResultSet.getInt("empId");
                Summary summary = new Summary(0, empId, null, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                summary.calcTime();
                summary.updateTime();
            }

            insertTable();
            insertTotal();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(IndividualTimeCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SummaryFrame summary = new SummaryFrame();
        summary.showSummary(period); //placeholder
        summary.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IndividualTimeCard(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel dayTotLb;
    public javax.swing.JLabel deptLabel;
    private javax.swing.JTable idvlTCTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lateTotLb;
    public javax.swing.JLabel legTotLb;
    public javax.swing.JLabel nameLabel;
    public javax.swing.JLabel ndTotLb;
    public javax.swing.JLabel otTotLb;
    public javax.swing.JLabel periodLabel1;
    public javax.swing.JLabel periodLabel2;
    public javax.swing.JLabel periodLabel3;
    public javax.swing.JLabel periodLabel4;
    public javax.swing.JLabel periodLabel5;
    public javax.swing.JLabel periodLabel6;
    public javax.swing.JLabel periodLabel7;
    public javax.swing.JLabel periodLabel8;
    public javax.swing.JLabel periodLb;
    public javax.swing.JLabel spcOtTotLb;
    public javax.swing.JLabel spcTotLb;
    // End of variables declaration//GEN-END:variables
}

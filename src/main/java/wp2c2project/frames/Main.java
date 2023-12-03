/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package wp2c2project.frames;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Windows
 */
public class Main {

    static final String SG_URL = "jdbc:mysql://localhost/wp2c2_payroll";
    static final String SG_URL_SETUP = "jdbc:mysql://localhost";
    static final String USER = "root";
    static final String PASS = "";

    Connection sgconn = connectSG();
    ResultSet resultSet;
    PreparedStatement st;

    public void setupDatabase() {
        try {
            String databaseName = "wp2c2_payroll";

            Class.forName("com.mysql.jdbc.Driver");
            sgconn = DriverManager.getConnection(SG_URL_SETUP, USER, PASS);

            if (sgconn != null) {
                try {
                    Statement statement = sgconn.createStatement();

                    //employee
                    statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
                    statement.executeUpdate("USE " + databaseName);
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS employee ("
                            + "empId INT PRIMARY KEY, "
                            + "name VARCHAR(20) NOT NULL, "
                            + "department VARCHAR(20) NOT NULL, "
                            + "rate FLOAT NOT NULL, "
                            + "shiftStart VARCHAR(5) NOT NULL, "
                            + "shiftEnd VARCHAR(5) NOT NULL, "
                            + "status VARCHAR(20) NOT NULL, "
                            + "tin VARCHAR(20) NOT NULL, "
                            + "philHealth INT(20) NOT NULL, "
                            + "sss VARCHAR(20) NOT NULL, "
                            + "pagibig INT(20) NOT NULL, "
                            + "taxStatus VARCHAR(10) NOT NULL "
                            + ")"
                    );

                    //time card
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS time_card ("
                            + "dateId INT PRIMARY KEY AUTO_INCREMENT, "
                            + "empId INT NOT NULL, "
                            + "date VARCHAR(5) NOT NULL, "
                            + "dateType INT NOT NULL, "
                            + "shiftStart VARCHAR(5) NOT NULL, "
                            + "shiftEnd VARCHAR(5) NOT NULL, "
                            + "timeIn VARCHAR(5) NOT NULL, "
                            + "timeOut VARCHAR(5) NOT NULL, "
                            + "day FLOAT NOT NULL, "
                            + "late FLOAT NOT NULL, "
                            + "ot FLOAT NOT NULL, "
                            + "nd FLOAT NOT NULL, "
                            + "spc FLOAT NOT NULL DEFAULT '0', "
                            + "spcOt FLOAT NOT NULL DEFAULT '0', "
                            + "leg FLOAT NOT NULL DEFAULT '0' "
                            + ")"
                    );

                    //summary
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS `summary` ("
                            + "`sumId` INT PRIMARY KEY AUTO_INCREMENT, "
                            + "`empId` int  (11) NOT NULL, "
                            + "`period` varchar(50) NOT NULL, "
                            + "`rph` float NOT NULL, "
                            + "`dayTot` float NOT NULL, "
                            + "`lateTot` float NOT NULL, "
                            + "`otTot` float NOT NULL, "
                            + "`ndTot` float NOT NULL, "
                            + "`spcTot` float NOT NULL, "
                            + "`spcOtTot` float NOT NULL, "
                            + "`legTot` float NOT NULL, "
                            + "`lateAmt` float NOT NULL, "
                            + "`otAmt` float NOT NULL, "
                            + "`ndAmt` float NOT NULL, "
                            + "`spcAmt` float NOT NULL, "
                            + "`spcOtAmt` float NOT NULL, "
                            + "`legAmt` float NOT NULL, "
                            + "`regWage` float NOT NULL, "
                            + "`gross` float NOT NULL, "
                            + "`sssReg` float NOT NULL DEFAULT '0', "
                            + "`sssMpf` float NOT NULL DEFAULT '0', "
                            + "`phealth` float NOT NULL DEFAULT '0', "
                            + "`wtax` float NOT NULL DEFAULT '0', "
                            + "`sssLoanS` float NOT NULL DEFAULT '0', "
                            + "`sssLoanC` float NOT NULL DEFAULT '0', "
                            + "`pagibigCont` float NOT NULL DEFAULT '0', "
                            + "`efund` float NOT NULL DEFAULT '0', "
                            + "`pagibigLoanS` float NOT NULL DEFAULT '0', "
                            + "`pagibigLoanC` float NOT NULL DEFAULT '0', "
                            + "`otherDedt` float NOT NULL DEFAULT '0', "
                            + "`dedtTot` float NOT NULL DEFAULT '0', "
                            + "`allowance` float NOT NULL DEFAULT '0', "
                            + "`netPay` float NOT NULL "
                            + ")"
                    );

                    //user
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS user ("
                            + "id INT PRIMARY KEY AUTO_INCREMENT, "
                            + "name VARCHAR(25) NOT NULL, "
                            + "username VARCHAR(25) NOT NULL, "
                            + "`cell no.` INT(25) NOT NULL, "
                            + "email VARCHAR(25) NOT NULL, "
                            + "pass VARCHAR(25) NOT NULL, "
                            + "pass2 VARCHAR(25) NOT NULL "
                            + ")"
                    );

                    //rates
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS rates ("
                            + "id INT PRIMARY KEY AUTO_INCREMENT, "
                            + "rateValue FLOAT NOT NULL "
                            + ")"
                    );
                    insertRateData(2.0f);
                    insertRateData(1.25f);
                    insertRateData(0.1f);
                    insertRateData(1.3f);
                    insertRateData(1.69f);
                    insertRateData(1f);

                    System.out.println("Database setup successful");
                } catch (SQLException ex) {
                    Logger.getLogger(Login_Jon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There were errors connecting to the database. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertRateData(float rateValue) throws SQLException {
        String insertDataQuery = "INSERT INTO rates (rateValue) VALUES (?)";

        try (PreparedStatement statement = sgconn.prepareStatement(insertDataQuery)) {
            statement.setFloat(1, rateValue);

            statement.executeUpdate();
        }
    }

    public float selectRateData(int rateId) throws SQLException {
        String insertDataQuery = "SELECT * FROM `rates` WHERE `id` = ?";
        try (PreparedStatement st = sgconn.prepareStatement(insertDataQuery)) {
            st.setInt(1, rateId);
            resultSet = st.executeQuery();
            resultSet.next();
            return resultSet.getFloat("rateValue");
        }
    }

    public static Connection connectSG() {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(SG_URL, USER, PASS);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("There were errors connecting to the database");
            return null;
        }
    }

    public void calculateSummary() throws SQLException {
        PreparedStatement summaryStatement = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `summary`");
        ResultSet summaryResultSet = summaryStatement.executeQuery();
        while (summaryResultSet.next()) {
            int sumId = summaryResultSet.getInt("sumId");
            int empId = summaryResultSet.getInt("empId");
            float lateAmt = summaryResultSet.getFloat("lateAmt");
            float regWage = summaryResultSet.getFloat("regWage");
            float otAmt = summaryResultSet.getFloat("otAmt");
            float ndAmt = summaryResultSet.getFloat("ndAmt");
            float spcAmt = summaryResultSet.getFloat("spcAmt");
            float spcOtAmt = summaryResultSet.getFloat("spcOtAmt");
            float legAmt = summaryResultSet.getFloat("legAmt");
            float gross = summaryResultSet.getFloat("gross");
            float sssReg = summaryResultSet.getFloat("sssReg");
            float sssMpf = summaryResultSet.getFloat("sssMpf");
            float phealth = summaryResultSet.getFloat("phealth");
            float wtax = summaryResultSet.getFloat("wtax");
            float sssLoanS = summaryResultSet.getFloat("sssLoanS");
            float sssLoanC = summaryResultSet.getFloat("sssLoanC");
            float pagibigCont = summaryResultSet.getFloat("pagibigCont");
            float efund = summaryResultSet.getFloat("efund");
            float pagibigLoanS = summaryResultSet.getFloat("pagibigLoanS");
            float pagibigLoanC = summaryResultSet.getFloat("pagibigLoanC");
            float otherDedt = summaryResultSet.getFloat("otherDedt");
            float dedtTot = summaryResultSet.getFloat("dedtTot");
            float allowance = summaryResultSet.getFloat("allowance");
            float netPay = summaryResultSet.getFloat("netPay");

            float dayTot = 0;
            float lateTot = 0;
            float otTot = 0;
            float ndTot = 0;
            float spcTot = 0;
            float spcOtTot = 0;
            float legTot = 0;

            st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `time_card` WHERE `empId` = ?");
            st.setInt(1, empId);
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

            st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `employee` WHERE `empId` = ?");
            st.setInt(1, empId);
            resultSet = st.executeQuery();
            resultSet.next();
            float rate = resultSet.getFloat("rate");

            st = (PreparedStatement) sgconn.prepareStatement("SELECT * FROM `rate` WHERE `id` = ?");
            //float lateRate = selectRateData(1);
            float otRate = selectRateData(2);
            float ndRate = selectRateData(3);
            float spcRate = selectRateData(4);
            float spcOtRate = selectRateData(5);
            float legRate = selectRateData(6);

            float rph = rate / 8;
            //lateAmt = rph / 60 * lateTot;
            //regWage = rate * dayTot - lateTot;
            otAmt = rph * otRate * otTot;
            ndAmt = rph * ndRate * ndTot;
            spcAmt = rph * spcRate * spcTot;
            spcOtAmt = rph * spcOtRate * spcOtTot;
            legAmt = rph * legRate * legTot;
            gross = regWage + otAmt + ndAmt + spcAmt + spcOtAmt + legAmt;

            netPay = gross - dedtTot + allowance;

            //update summary database with corresponding sumId    
            st = (PreparedStatement) sgconn.prepareStatement(
                    "UPDATE `summary` SET "
                    //+ "`rph` = ?, "
                    + "`dayTot` = ?, "
                    + "`lateTot` = ?, "
                    + "`otTot` = ?, "
                    + "`ndTot` = ?, "
                    + "`spcTot` = ?, "
                    + "`spcOtTot` = ?, "
                    + "`legTot` = ?, "
                    + "`lateAmt` = ?, "
                    + "`otAmt` = ?, "
                    + "`ndAmt` = ?, "
                    + "`spcAmt` = ?, "
                    + "`spcOtAmt` = ?, "
                    + "`legAmt` = ?, "
                    //+ "`regWage` = ?, "
                    + "`gross` = ?, "
                    + "`netPay` = ? "
                    + " WHERE empId = ?"
            );

            st.setFloat(1, dayTot);
            st.setFloat(2, lateTot);
            st.setFloat(3, otTot);
            st.setFloat(4, ndTot);
            st.setFloat(5, spcTot);
            st.setFloat(6, spcOtTot);
            st.setFloat(7, legTot);
            st.setFloat(8, lateAmt);
            st.setFloat(9, otAmt);
            st.setFloat(10, ndAmt);
            st.setFloat(11, spcAmt);
            st.setFloat(12, spcOtAmt);
            st.setFloat(13, legAmt);
            st.setFloat(14, gross);
            st.setFloat(15, netPay);
            st.setFloat(16, empId);
            st.executeUpdate();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setupDatabase();
        FlatLightLaf.setup();

        AdminHome vr = new AdminHome();
        vr.setVisible(true);
    }
}

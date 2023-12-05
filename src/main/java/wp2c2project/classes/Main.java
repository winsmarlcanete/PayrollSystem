/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package wp2c2project.classes;

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
import wp2c2project.frames.Login_Jarbox;
import wp2c2project.frames.Login_Jon;

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
                            + "leg FLOAT NOT NULL DEFAULT '0', "
                            + "`period` varchar(50) NOT NULL "
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

                    Login_Jarbox login = new Login_Jarbox();
                    login.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Login_Jon.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There were errors connecting to the database. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "There were errors connecting to the database. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertRateData(float rateValue) throws SQLException {
        String insertDataQuery = "INSERT INTO rates (rateValue) VALUES (?)";

        try (PreparedStatement statement = sgconn.prepareStatement(insertDataQuery)) {
            statement.setFloat(1, rateValue);

            statement.executeUpdate();
        }
    }

    public static Connection connectSG() {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(SG_URL, USER, PASS);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "There are problems with your connection. Please try again.", "Connection error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setupDatabase();
        FlatLightLaf.setup();
    }
}

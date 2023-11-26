/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.wp2c2project;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public void setupDatabase() {
        try {
            String databaseName = "wp2c2_payroll";
            Class.forName("com.mysql.jdbc.Driver");
            Connection sgconn = DriverManager.getConnection(SG_URL_SETUP, USER, PASS);

            if (sgconn != null) {
                try {
                    Statement statement = sgconn.createStatement();
                    statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
                    statement.executeUpdate("USE " + databaseName);
                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS employee ("
                            + "id INT PRIMARY KEY, "
                            + "name VARCHAR(20) NOT NULL, "
                            + "department VARCHAR(20) NOT NULL, "
                            + "rate DOUBLE NOT NULL, "
                            + "shiftStart VARCHAR(5) NOT NULL, "
                            + "shiftEnd VARCHAR(5) NOT NULL, "
                            + "tin VARCHAR(20) NOT NULL, "
                            + "philHealth INT(20) NOT NULL, "
                            + "sss VARCHAR(20) NOT NULL, "
                            + "pagibig INT(20) NOT NULL, "
                            + "taxStatus VARCHAR(10) NOT NULL "
                            + ")");

                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS time_card ("
                            + "idDate INT PRIMARY KEY AUTO_INCREMENT, "
                            + "id INT NOT NULL, "
                            + "date VARCHAR(5) NOT NULL, "
                            + "dateType INT NOT NULL, "
                            + "timeIn VARCHAR(5) NOT NULL, "
                            + "timeOut VARCHAR(5) NOT NULL, "
                            + "shiftStart VARCHAR(5) NOT NULL, "
                            + "shiftEnd VARCHAR(5) NOT NULL "
                            + ")");

                    statement.executeUpdate(
                            "CREATE TABLE IF NOT EXISTS user ("
                            + "id INT PRIMARY KEY AUTO_INCREMENT, "
                            + "name VARCHAR(25) NOT NULL, "
                            + "username VARCHAR(25) NOT NULL, "
                            + "`cell no.` INT(25) NOT NULL, "
                            + "email VARCHAR(25) NOT NULL, "
                            + "pass VARCHAR(25) NOT NULL, "
                            + "pass2 VARCHAR(25) NOT NULL "
                            + ")");
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

    public static Connection connectSG() {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(SG_URL, USER, PASS);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("There were errors connecting to Synergy Graffix");
            return null;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setupDatabase();

        AdminHome vr = new AdminHome();
        vr.setVisible(true);
        
//        AdminHome frame = new AdminHome();
//        frame.setVisible(true);
    }
}

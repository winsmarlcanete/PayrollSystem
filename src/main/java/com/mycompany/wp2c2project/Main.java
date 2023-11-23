/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.wp2c2project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows
 */
public class Main {

    public static void main(String[] args) {
        //set up database
        String databaseName = "wp2c2_payroll";

        Connection sgconn = SynergyGrafix.connectSG();

        if (sgconn != null) {
            try {
                //create database
                Statement statement = sgconn.createStatement();
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
                statement.executeUpdate("USE " + databaseName);
                   statement.executeUpdate("CREATE TABLE IF NOT EXISTS employee ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(20) NOT NULL, "
                        + "department VARCHAR(20) NOT NULL, "
                        + "rate INT NOT NULL, "
                        + "shiftStart VARCHAR(5) NOT NULL, "
                        + "shiftEnd VARCHAR(5) NOT NULL, "
                        + "tin VARCHAR(20) NOT NULL, "
                        + "philHealth INT(20) NOT NULL, "
                        + "sss VARCHAR(20) NOT NULL, "
                        + "pagibig INT(20) NOT NULL, "
                        + "taxStatus VARCHAR(10) NOT NULL "
                        + ")");
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS user ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "name VARCHAR(25) NOT NULL, "
                        + "username VARCHAR(25) NOT NULL, "
                        + "`cell no.` INT(25) NOT NULL, "
                        + "email VARCHAR(25) NOT NULL, "
                        + "pass VARCHAR(25) NOT NULL, "
                        + "pass2 VARCHAR(25) NOT NULL "
                        + ")");
            } catch (SQLException ex) {
                Logger.getLogger(Login_Jon.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(this, "There were errors connecting to the database. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Login_Jon ea = new Login_Jon();
        ea.setVisible(true);

    }
}

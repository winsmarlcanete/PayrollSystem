/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wp2c2project;
import java.sql.*;

/**
 *
 * @author Jon Gleur Tan
 */
public class SynergyGraffix {
       static final String SG_URL = "jdbc:mysql://localhost/demo";
       static final String USER = "root";
       static final String PASS = "";
       public static Connection connectSG(){
           Connection conn = null;
           try{
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection(SG_URL,USER,PASS);
               return conn;
           }   
           catch(Exception ex){
               System.out.println("There were errors connecting to Snergy Graffix");
               return null;
           }
       }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nniks
 */
public class SQL {
    Connection connection;

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:D:/SQL/AlarmClock.db");
            System.out.println("opened database successfully");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException exc) {
            System.out.println("error database successfully");
        }
    }

    SQL(){
        connect();
    }

   ArrayList<OBJECT> get_story(){
        ArrayList<OBJECT> array = new ArrayList<>();
        try {
            if(connection != null){
                Statement stat = connection.createStatement();
                ResultSet res = stat.executeQuery("select * from Table_AlarmClock");
                
                while(res.next()){
                    array.add(new OBJECT(
                    res.getInt("ID_client"),
                    res.getInt("Hour"),
                    res.getInt("Minute"),
                    res.getInt("Second"),
                    res.getString("Description")
                    )); 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
   
    public void add_alarm_to_dbase(OBJECT m){
        if(connection != null){
            try { 
                PreparedStatement pstat = connection.prepareStatement("INSERT INTO Table_AlarmClock(ID_client, Hour, Minute, Second, Description) VALUES(?, ?, ?, ?, ?)");
                pstat.setInt(1, m.get_ID());   
                pstat.setInt(2, m.get_hour());   
                pstat.setInt(3, m.get_minute());   
                pstat.setInt(4, m.get_second());   
                pstat.setString(5,m.get_description());
                pstat.executeUpdate();
                
            } catch (SQLException ex) { 
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

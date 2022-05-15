/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java;
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
public class DBase {
    Connection connection;

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:D:/SQL/AlarmClock.db");
            System.out.println("opened database successfully");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException exc) {
            System.out.println("error database successfully");
        }
    }

    DBase(){
        connect();
    }

   ArrayList<Message> get_story(){
        ArrayList<Message> array = new ArrayList<>();
        try {
            if(connection != null){
                Statement stat = connection.createStatement();
                ResultSet res = stat.executeQuery("select * from Table_AlarmClock");
                
                while(res.next()){
                    Message msg = new Message();
                    //msg.set_KEY(res.getInt("ID"));
                    msg.set_ID(res.getInt("ID_client"));
                    msg.set_hour(res.getInt("Hour"));
                    msg.set_minute(res.getInt("Minute"));
                    msg.set_second(res.getInt("Second"));
                    msg.set_description(res.getString("Description"));
                    array.add(msg); 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }
   
    public void add_alarm_to_dbase(Message m){
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
                Logger.getLogger(DBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void del_alarm_from_dbase(Message _del){
        if(connection != null){
            try {
                String delete_alarm = "ID_client=" + _del.get_ID()
                        + " AND Hour=" + _del.get_hour()
                        + " AND Minute=" + _del.get_minute()
                        + " AND Second=" + _del.get_second()
                        ;
                System.out.println("delll " + delete_alarm);
//                PreparedStatement pstat = connection.prepareStatement("DELETE FROM Table_AlarmClock WHERE ID=? ");  
//                pstat.setInt(1, _del.get_KEY());
//                pstat.executeUpdate();
                PreparedStatement pstat = connection.prepareStatement("DELETE FROM Table_AlarmClock WHERE " 
                        + delete_alarm
                        + " AND Description=?"
                );  
                pstat.setString(1, _del.get_description());
                pstat.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(DBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java_2;

import Watch.Watch;
import Watch.Run;
import com.google.gson.Gson;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nniks
 */
public class Server {
    private int clients = 0;
    private int port = 3192;
    InetAddress ip;
    ServerSocket ss;
    Gson gson = new Gson();
    Watch timer = new Watch();
    SQL base = new SQL();
    Run iterator = new Run(timer);
    private int next_time;
    OBJECT next_alarm = new OBJECT(0,0,0,0,"");
    ArrayList<OBJECT> story = new ArrayList<>();
    ArrayList<ServerClient> all_clients = new ArrayList<>();
    
    public int get_next_time(){ return next_time; }
    public void set_next_time(int _next_time) { next_time = _next_time; }
    public void set_time(int[] _time) throws Exception { 
        timer.set_time(_time[0], _time[1], _time[2]); 
    }
    public int[] get_time(){ return iterator.get_time(); }
    public void run() { iterator.run(); }
    public int get_second(){return iterator.get_second(); }
    public int get_hour(){return iterator.get_hour(); }
    public int get_minute(){return iterator.get_minute(); }
    
    public String time_string(){
        String s = "";
        if(timer.get_hour() < 10){
            s += "0";
        }
        s += timer.get_hour() + ":";
        if(timer.get_minute() < 10){
            s += "0";
        }
        s += timer.get_minute() + ":";
        if(timer.get_second() < 10){
            s += "0";
        }
        s += timer.get_second(); 
        return s;
    }
    
    public void add_alarm(OBJECT m){
        base.add_alarm_to_dbase(m);
        story.add(m);
        for(ServerClient client : all_clients){
            client.send_alarms();
        }
    }

    public Server(){
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Thread thread = new Thread(() -> {
            try {
                ss = new ServerSocket(port, 0, ip);
                System.out.println("Сервер запущен");
                    while(true){
                     Socket cs = ss.accept();
                     clients++;
                     ServerClient client = new ServerClient(cs, this, clients);
                     all_clients.add(client);
                    }
            
            } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }
}

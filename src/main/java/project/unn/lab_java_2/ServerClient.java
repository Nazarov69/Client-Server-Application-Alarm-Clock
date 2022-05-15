/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java_2;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nniks
 */
public class ServerClient {
    int ID;
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    Server server;
    Gson gson = new Gson();
    OBJECT res = new OBJECT(0,0,0,0,"");
    
    public ServerClient(Socket _cs, Server _server, int _ID){ 
        ID = _ID;
        cs = _cs; 
        server = _server;

        try {
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Thread thread = new Thread(() ->{
            try {
                dis = new DataInputStream(cs.getInputStream());
                while(true){
                        String input = dis.readUTF();
                        OBJECT object = gson.fromJson(input, OBJECT.class);
                        server.add_alarm(object);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
        
        send_story();
    }

    public void send_story(){
        OBJECT object = new OBJECT(ID, 0, 0, 0, "");
        object.get_descriptions().addAll(server.story);
        
        try {
            dos.writeUTF(gson.toJson(object));
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void send_alarms(){
        OBJECT object = new OBJECT(ID, 0, 0, 0, "");
        object.get_descriptions().addAll(server.story);
        try {
            dos.writeUTF(gson.toJson(object));
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void send_alarm_and_time(OBJECT next, int[] time){
        try {
            dos.writeUTF(gson.toJson(new OBJECT(-2, time[0], time[1], time[2], "")));
            dos.writeUTF(gson.toJson(new OBJECT(-1, next.get_hour(), next.get_minute(), next.get_second(), next.get_description())));
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

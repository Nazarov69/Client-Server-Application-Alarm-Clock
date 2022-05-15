/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java;
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
public class Client {
    int ID;
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    Gson gson = new Gson();
    Server server;
    Thread thread_msg;
    Message res = new Message();
    public Client(Socket _cs, Server _server, int _ID){ 
        ID = _ID;
        cs = _cs; 
        server = _server;

        System.out.println("add client " + ID);
        try {
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        thread_msg = new Thread(() ->{
            try {
                dis = new DataInputStream(cs.getInputStream());
                while(true){
                        String obj = dis.readUTF();
                        Message msg = gson.fromJson(obj, Message.class);
                        String str2 = gson.toJson(msg);
                        System.out.println("2" + str2);
                        server.add_alarm(msg);
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_msg.start();
        
        send_story();
    }

    public void send_story(){
        Message msg = new Message();
        msg.set_ID(ID);
        msg.set_hour(0);
        msg.set_minute(0);
        msg.set_second(0);
        msg.set_description("");
        msg.get_descriptions().addAll(server.story);
        
        String send = gson.toJson(msg);
        System.out.println("3" + send);
        try {
            dos.writeUTF(send);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void send_alarms(){
        Message m = new Message();
        m.get_descriptions().addAll(server.story);
        
        String send = gson.toJson(m);
        System.out.println("3.1 " + send);
        try {
            dos.writeUTF(send);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void send_alarms_and_time(Message prev, Message next, int[] time){
        Message tm = new Message();
        tm.set_ID(-3);
        tm.set_hour(time[0]);
        tm.set_minute(time[1]);
        tm.set_second(time[2]);
        tm.set_description("");
        String t = gson.toJson(tm);
        
        Message prv = new Message();
        prv.set_ID(-2);
        prv.set_hour(prev.get_hour());
        prv.set_minute(prev.get_minute());
        prv.set_second(prev.get_second());
        prv.set_description(prev.get_description());
        String p = gson.toJson(prv);
        
        Message nxt = new Message();
        nxt.set_ID(-1);
        nxt.set_hour(next.get_hour());
        nxt.set_minute(next.get_minute());
        nxt.set_second(next.get_second());
        nxt.set_description(next.get_description());
        String n = gson.toJson(nxt);
        
        try {
            dos.writeUTF(t);
            dos.writeUTF(p);
            dos.writeUTF(n);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}

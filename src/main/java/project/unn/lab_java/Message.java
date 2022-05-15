/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.unn.lab_java;
import java.util.ArrayList;

/**
 *
 * @author nniks
 */
public class Message {
    public ArrayList<Message> descriptions = new ArrayList<>(); 
    String description = "";
    int[] time;
    int ID = 0;
    int hour = 0;
    int minute = 0;
    int second = 0;
    public void set_hour(int _hour) { hour = _hour; }
    public int get_hour() { return hour; }
    public void set_minute(int _minute) { minute = _minute; }
    public int get_minute() { return minute; }
    public void set_second(int _second) { second = _second; }
    public int get_second() { return second; }
    public ArrayList<Message> get_descriptions(){ return descriptions; }
    public String get_description(){ return description; }
    public void set_descriptions(Message _description){ descriptions.add(_description); }
    public void set_description(String _description){ description = _description; }
    public void set_time(int[] _time) { time = _time; }
    public int[] get_time() {return time;} 
    public int get_ID(){ return ID; }
    public void set_ID(int _ID){ ID = _ID; }
    public String time_string(){
        String output = ""; 
        if(hour < 10){
            output += "0";
        }
        output += hour + ":";
        if(minute < 10){
            output += "0";
        }
        output += minute + ":";
        if(second < 10){
        output += "0";
        }
        output += second + " " + description + "\n"; 
        return output;
    }
    public String to_string(){
        String str = "";
        str += "Message{ Id = " + ID + "\n";
        str += "Hour = " + hour + "\n";
        str += "Minute = " + minute + "\n";
        str += "Second = " + second + "\n";
        str += "Description = " + description + "}\n";
        return str;
    }
    
}

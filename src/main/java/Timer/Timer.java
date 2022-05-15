/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Timer;
/**
 *
 * @author nniks
 */
public class Timer implements TimerInterface{
    protected int hour = 0;
    protected int minute = 0;
    protected int second = 0;
    
    @Override
    public void set_hour(int _hour) throws Exception{
        if(_hour >= 0 && _hour <= 24){
            hour = _hour;
        }else{ throw new Exception("error time"); }
    }
    
    @Override
    public void set_minute(int _minute) throws Exception{
        if(_minute >= 0 && _minute <= 60){
            minute = _minute;
        }else{ throw new Exception("error time"); }
    }
    
    @Override
    public void set_second(int _second) throws Exception{
        if(_second >= 0 && _second <= 60){
            second = _second;
        }else{ throw new Exception("error time"); }
    }
    
    @Override
    public void set_time(int _hour, int _minute, int _second) throws Exception{
        set_hour(_hour);
        set_minute(_minute);
        set_second(_second);
    }
    
    @Override
    public void add_time(int _hour, int _minute, int _second) throws Exception{
        set_hour((hour + _hour + (minute + _minute + (second + _second) / 60) / 60) % 24);
        set_minute((minute + _minute + (second + _second) / 60) % 60);
        set_second((second + _second ) % 60);
    }
    
    @Override
    public int get_hour() { return hour; }
    
    @Override
    public int get_minute() { return minute; }
    
    @Override
    public int get_second() { return second; }
    
    @Override
    public int[] get_time(){
        return new int[]{hour, minute, second};
    }
    
    public void cout(){
        System.out.println("Текущее время ");
        if(hour < 10){
            System.out.print("0");
        }
        System.out.print(hour + ":");
        if(minute < 10){
            System.out.print("0");
        }
        System.out.print(minute + ":");
        if(second < 10){
            System.out.print("0");
        }
        System.out.println(second);   
    }
}

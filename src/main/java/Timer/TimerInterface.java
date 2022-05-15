/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Timer;
/**
 *
 * @author nniks
 */
public interface TimerInterface {
    public void set_hour(int _hour) throws Exception;
    public void set_minute(int _minute) throws Exception;
    public void set_second(int _second) throws Exception;
    public void set_time(int _hour, int _minute, int _second) throws Exception;
    public void add_time(int _hour, int _minute, int _second) throws Exception;
    public int get_hour();
    public int get_minute();
    public int get_second();
    public int[] get_time();
}

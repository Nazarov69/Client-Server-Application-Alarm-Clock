/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Timer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nniks
 */
public class TimerIterator extends javax.swing.JPanel{
    Timer timer;
    boolean run;
    boolean pause;
    Thread thread;
    
    public TimerIterator(Timer _timer){ timer = _timer; }
    
    public int[] get_time(){
        int[] values = {timer.hour, timer.minute, timer.second};
        return values;
    }
    
    public int get_hour(){ return timer.hour; }
    public int get_minute(){ return timer.minute; }
    public int get_second() { return timer.second; }
    
    public void pause() { 
        pause = true;
    }
    
    public boolean is_run() { return run; }
    
    public void stop() {
        run = false;
        if(thread != null){
            thread.interrupt();
            thread = null;
            pause = false;
            reset_time();
        }
    }
    
    public void reset_time() {
        try {
            timer.set_time(0 ,0, 0);
        } catch (Exception exc) {}
    }
    
    public void run(){
        if(thread == null){
            thread = new Thread(() -> {
                run = true;
                pause = false;
                try {
                    while(run){
                        if(pause){
                            synchronized (timer){
                                timer.wait();
                            }
                            pause = false;
                        }
                        Thread.sleep(1000);
                        timer.add_time(0, 0, 1);
                        //timer.cout();
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(TimerIterator.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            thread.start();
        }else { 
            cont();
        }
    }
    
    public void cont() {
        run = true;
        synchronized (timer) {
            timer.notifyAll();
        }
    }
    
}

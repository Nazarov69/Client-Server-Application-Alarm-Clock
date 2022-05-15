/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Watch;

/**
 *
 * @author nniks
 */
public class Run {
    Watch timer;
    boolean run;
    boolean pause;
    Thread thread;
    
    public Run(Watch _timer){ timer = _timer; }
    
    public int[] get_time(){
        int[] values = {timer.hour, timer.minute, timer.second};
        return values;
    }
    
    public int get_hour(){ return timer.hour; }
    public int get_minute(){ return timer.minute; }
    public int get_second() { return timer.second; }

    public void run(){
        if(thread == null){
            thread = new Thread(() -> {
                run = true;
                pause = false;
                try {
                    while(run){
                        Thread.sleep(1000);
                        timer.add_time(0, 0, 1);
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(TimerIterator.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            thread.start();
        }
    }
}

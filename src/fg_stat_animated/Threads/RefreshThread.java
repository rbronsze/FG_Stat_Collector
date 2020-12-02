/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.Timer;
import fg_stat_animated.view.MainFrame;

/**
 *
 * @author Polack
 */
public class RefreshThread extends Thread{
    
    public void run(MainFrame f){
        
        while(true){
            while(true){

                Timer.wait(16);
                f.repaint();
                f.revalidate();
            }
        }
        
    }
    
}

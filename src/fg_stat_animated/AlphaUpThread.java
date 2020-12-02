/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class AlphaUpThread extends Thread{
    
    private boolean bool;
    private AlphaImage img;
    
    public AlphaUpThread(AlphaImage img, boolean bool){
        
        this.img = img;
        this.bool = bool;
        
    }
    
    public void run(){
            
        while(img.getOpacity() < 0.40f && bool){

            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(AlphaUpThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            img.setOpacity(img.getOpacity()+ 0.01f);

        }

    }
    
}

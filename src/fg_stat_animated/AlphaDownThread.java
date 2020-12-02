/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import fg_stat_animated.view.GamesPanel;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class AlphaDownThread extends Thread{

    private AlphaImage img;
    private boolean bool;
    
    AlphaDownThread(AlphaImage img, boolean bool) {
        this.img = img;
        this.bool = bool;
    }
        
    public void run(){

        while(img.getOpacity() > 0.01f && !bool){

            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            img.setOpacity(img.getOpacity()- 0.01f);

        }

        if(!bool)
            img.setOpacity(0.00f);

    }
    
    
}

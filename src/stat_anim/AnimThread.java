/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class AnimThread extends Thread{
    
    private StatPanel p;
    private int wait = 100;
    private int nb = -1;
    private boolean isUp = false;
    
    public AnimThread(StatPanel p){
        
        this.p = p;
        
    }
    
    public void run(){
        
        while(true){
            
            try {
                sleep(wait);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(p.isSessionActive()){
                if(isUp){
                    if(p.isPreviousSession() || p.isModified()){
                        scrollDown(500);
                    }
                }else{
                    scrollUp();
                }
            }else{
                nb = 0;
                if(isUp){
                    scrollDown(100);
                }
            }
            
        }    
        
    }
    
    private void scrollUp(){
        
        p.setTextLbl(nb);
        
        int y = p.getV();
        
        while(y > 0){
            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.setV(y);
            
            y--;
            
        }
        p.setTextVisible(true);
        wait = 4000;
        isUp = true;
        
    }
    
    private void scrollDown(int nb){
        
        p.setTextVisible(false);
        
        int y = p.getV();
        
        while(y <= 35){
            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            p.setV(y);
            
            y++;
        }
        
        wait = nb;
        isUp = false;
        setNb();
            
    }
    
    private void setNb(){
        
        if(p.isPreviousSession()){
            if(p.isModified()){
                nb = 0;
                p.setModified(false);
            }else{
                if(nb == 3){
                    nb = 0;
                }else{
                    nb++;
                }
            }
        }else{
            nb = 0;
            p.setModified(false);
        }
    }
    
}

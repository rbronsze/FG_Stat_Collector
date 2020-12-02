/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.Timer;

/**
 *
 * @author Polack
 */
public class FadeThread extends Thread{
    
    private AlphaImage img;
    private float destOpacity; 
    private float deltaOpacity;
    private boolean isin;
    
    public FadeThread(AlphaImage img, float destOpacity, float deltaOpacity, boolean isin){
        
        this.img = img;
        this.destOpacity = destOpacity;
        this.deltaOpacity = deltaOpacity;
        this.isin = isin;
        
    }
    
    public void run(){
        
        while(img.getOpacity() != destOpacity){
            Timer.wait(16);
            if(isin){
                fadeIn();
            }else{
                fadeOut();
            }
            
        }
    }
    
    private void fadeIn(){
        if(img.getOpacity() < destOpacity - deltaOpacity){
            img.setOpacity(img.getOpacity() + deltaOpacity);
        }else{
            img.setOpacity(destOpacity);
        }
    }

    private void fadeOut(){
        if(img.getOpacity() > destOpacity + deltaOpacity){
            img.setOpacity(img.getOpacity() - deltaOpacity);
        }else{
            img.setOpacity(destOpacity);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.Timer;
import java.util.ArrayList;

/**
 *
 * @author Polack
 */
public class FadeAllThread extends Thread{
    
    private ArrayList<AlphaImage> img;
    private float destOpacity; 
    private float deltaOpacity;
    private boolean isin;
    
    public FadeAllThread(ArrayList<AlphaImage> img, float destOpacity, float deltaOpacity, boolean isin){
        
        this.img = img;
        this.destOpacity = destOpacity;
        this.deltaOpacity = deltaOpacity;
        this.isin = isin;
        
    }
    
    public void run(){
        
        while(img.get(0).getOpacity() != destOpacity){
            Timer.wait(16);
            if(isin){
                for(int i = 0; i < img.size(); i++)
                    fadeIn(i);
            }else{
                for(int i = 0; i < img.size(); i++)
                    fadeOut(i);
            }
        }
        
    }
    
    private void fadeIn(int nb){
        if(img.get(nb).getOpacity() < destOpacity - deltaOpacity){
            img.get(nb).setOpacity(img.get(nb).getOpacity() + deltaOpacity);
        }else{
            img.get(nb).setOpacity(destOpacity);
        }
    }

    private void fadeOut(int nb){
        if(img.get(nb).getOpacity() > destOpacity + deltaOpacity){
            img.get(nb).setOpacity(img.get(nb).getOpacity() - deltaOpacity);
        }else{
            img.get(nb).setOpacity(destOpacity);
        }
    }
}

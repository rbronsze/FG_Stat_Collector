/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.ItemComponents;
import fg_stat_animated.Timer;
import fg_stat_animated.controler.Controler;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Polack
 */
public class RandomThread extends Thread{
    
    private ArrayList<ItemComponents> fadeOutImg, randomImg;
    private Controler c;
    
    public RandomThread(Controler c, ArrayList<ItemComponents> fadeOutImg, ArrayList<ItemComponents> randomImg){
        
        this.fadeOutImg = fadeOutImg;
        this.randomImg = randomImg;
        this.c = c;
        
    }
    
    public void run(){
        
        while(fadeOutImg.get(0).getLogo().getOpacity() != 0.0f){
            Timer.wait(16);
            for(int character = 0; character < fadeOutImg.size(); character++){
                fadeOutImg.get(character).getLogo().setOpacity(fadeOut(fadeOutImg.get(character).getLogo().getOpacity(), 0.0f, 0.016f));
            }
            c.getF().repaint();
            c.getF().revalidate();
        }
        Timer.wait(100);
        
        Point[] start = new Point[randomImg.size()];
        double deltaX[] = new double[randomImg.size()];
        double deltaY[] = new double[randomImg.size()];
        double deltaR = -1*(0.167 - 0.5)/60.0;
        
        for(int i = 0; i < randomImg.size(); i++){
            
            start[i] = new Point(randomImg.get(i).getLogo().getX(), randomImg.get(i).getLogo().getY());
            deltaX[i] = -1*(randomImg.get(i).getLogo().getX() - randomImg.get(i).getLabel().getX())/60.0;
            deltaY[i] = -1*(randomImg.get(i).getLogo().getY() - randomImg.get(i).getLabel().getY())/60.0;
            
        }
        
        for(int time = 0; time < 60; time++){
            Timer.wait(16);
            for(int i = 0; i < randomImg.size(); i++){
                if(randomImg.get(i).getLogo().getX() != randomImg.get(i).getLabel().getX()){
                    randomImg.get(i).getLogo().setX((int)((deltaX[i]*(time+1)) + start[i].getX()));
                }
                if(randomImg.get(i).getLogo().getY() != randomImg.get(i).getLabel().getY()){
                    randomImg.get(i).getLogo().setY((int)((deltaY[i]*(time+1)) + start[i].getY()));
                }    
                randomImg.get(i).getLogo().setRatio(deltaR*(time+1) + 0.167);
                
            }
            c.getF().repaint();
            c.getF().revalidate();
        }
        
    }
    
    private float fadeOut(float opacity, float destOpacity, float deltaOpacity){
        
        float f = 0.0f;
        if(opacity > destOpacity + deltaOpacity){
            f = opacity - deltaOpacity;
        }else{
            f = destOpacity;
        }
        
        return f;
    }
    
    
}

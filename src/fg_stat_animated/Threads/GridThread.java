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
public class GridThread extends Thread{
    
    private ArrayList<ItemComponents> fadeInImg, randomImg;
    private Controler c;
    
    public GridThread(Controler c, ArrayList<ItemComponents> fadeInImg, ArrayList<ItemComponents> randomImg){
        
        this.fadeInImg = fadeInImg;
        this.randomImg = randomImg;
        this.c = c;
        
    }
    
    public void run(){
        
        Point[] start = new Point[randomImg.size()];
        double deltaX[] = new double[randomImg.size()];
        double deltaY[] = new double[randomImg.size()];
        double deltaR = -1*(0.5 - 0.167)/60.0;
        
        for(int i = 0; i < randomImg.size(); i++){
            
            start[i] = new Point(randomImg.get(i).getLogo().getX(), randomImg.get(i).getLogo().getY());
            deltaX[i] = -1*(randomImg.get(i).getLogo().getX() - fadeInImg.get(c.getM().getRandomList().get(i)).getLogo().getX())/60.0;
            deltaY[i] = -1*(randomImg.get(i).getLogo().getY() - fadeInImg.get(c.getM().getRandomList().get(i)).getLogo().getY())/60.0;
            
        }
        
        for(int time = 0; time < 60; time++){
            Timer.wait(16);
            for(int i = 0; i < randomImg.size(); i++){
                if(randomImg.get(i).getLogo().getX() != fadeInImg.get(c.getM().getRandomList().get(i)).getLogo().getX()){
                    randomImg.get(i).getLogo().setX((int)((deltaX[i]*(time+1)) + start[i].getX()));
                }
                if(randomImg.get(i).getLogo().getY() != fadeInImg.get(c.getM().getRandomList().get(i)).getLogo().getY()){
                    randomImg.get(i).getLogo().setY((int)((deltaY[i]*(time+1)) + start[i].getY()));
                }
                randomImg.get(i).getLogo().setRatio(deltaR*(time+1) + 0.5);
            }
            c.getF().repaint();
            c.getF().revalidate();
        }
        Timer.wait(100);
        
        while(fadeInImg.get(0).getLogo().getOpacity() != 1.0f){
            Timer.wait(16);
            for(int character = 0; character < fadeInImg.size(); character++){
                fadeInImg.get(character).getLogo().setOpacity(fadeIn(fadeInImg.get(character).getLogo().getOpacity(), 1.0f, 0.016f));
            }
            c.getF().repaint();
            c.getF().revalidate();
        }
        
        c.getM().resetRandom();
        
    }
    
    private float fadeIn(float opacity, float destOpacity, float deltaOpacity){
        
        float f = 0.0f;
        if(opacity < destOpacity - deltaOpacity){
            f = opacity + deltaOpacity;
        }else{
            f = destOpacity;
        }
        
        return f;
    }
    
    
}

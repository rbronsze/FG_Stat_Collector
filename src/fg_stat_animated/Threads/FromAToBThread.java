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

/**
 *
 * @author Polack
 */
public class FromAToBThread extends Thread{
    
    private Controler c;
    private ItemComponents movingItem, item;
    private Point a, b;
    private double ratioA, ratioB;
    private boolean canceled;
    
    public FromAToBThread(Controler c, ItemComponents movingItem, ItemComponents item, Point a, Point b, double ratioA, double ratioB, boolean canceled){
        
        this.c = c;
        this.movingItem = movingItem;
        this.item = item;
        this.a = a;
        this.b = b;
        this.ratioA = ratioA;
        this.ratioB = ratioB;
        this.canceled = canceled;
        
    }
    
    public void run(){
        
        double deltaX = -1*(a.getX()-b.getX())/60.0;
        double deltaY = -1*(a.getY()-b.getY())/60.0;
        double deltaR = -1*(ratioA - ratioB)/60.0;
        int count = 1;

        while(movingItem.getLogo().getX() != b.getX() || movingItem.getLogo().getY() != b.getY()){

            Timer.wait(16);

            if(movingItem.getLogo().getX() != b.getX())
                movingItem.getLogo().setX((int)((deltaX*count) + a.getX()));
            
            if(movingItem.getLogo().getY() != b.getY())
                movingItem.getLogo().setY((int)((deltaY*count) + a.getY()));
            
            movingItem.getLogo().setRatio(deltaR*count + ratioA);

            count++;

            c.getF().repaint();
            c.getF().revalidate();
        }

        if(canceled){
            movingItem.getLogo().setOpacity(0.0f);
            item.getLogo().setOpacity(1.0f);
        }
            
    }
    
}

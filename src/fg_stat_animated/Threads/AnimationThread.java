/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.Timer;
import fg_stat_animated.controler.Controler;
import fg_stat_animated.view.MatchPanel;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Polack
 */
public class AnimationThread extends Thread{
    
    private MatchPanel pane;
    
    public AnimationThread(MatchPanel pane){
        
        this.pane = pane;
        
    }
    
    protected void moveList(ArrayList<ItemComponents> movingList, ArrayList<ItemComponents> pointB, ArrayList<Boolean> switchAfter){
        
        ArrayList<Point> pointA = new ArrayList<Point>();
        ArrayList<Double> ratioA = new ArrayList<Double>();
        ArrayList<Double> deltaX = new ArrayList<Double>();
        ArrayList<Double> deltaY = new ArrayList<Double>();
        ArrayList<Double> deltaRatio = new ArrayList<Double>();
        
        
        for(int i = 0; i < movingList.size(); i++){
            pointA.add(new Point(movingList.get(i).getLogo().getX(), movingList.get(i).getLogo().getY()));
            ratioA.add(movingList.get(i).getLogo().getRatio());
            deltaX.add(-1*(pointA.get(i).getX()-pointB.get(i).getLabel().getX())/60.0);
            deltaY.add(-1*(pointA.get(i).getY()-pointB.get(i).getLabel().getY())/60.0);
            deltaRatio.add(-1*(ratioA.get(i) - pointB.get(i).getLogo().getRatio())/60.0);
        }
        
        for(int time = 0; time < 60; time++){
            
            Timer.wait(16);
            for(int i = 0; i < movingList.size(); i++){
                //setX
                if(movingList.get(i).getLogo().getX() != pointB.get(i).getLabel().getX()){
                    movingList.get(i).getLogo().setX((int)((deltaX.get(i)*(time+1)) + pointA.get(i).getX()));
                }
                //setY
                if(movingList.get(i).getLogo().getY() != pointB.get(i).getLabel().getY()){
                    movingList.get(i).getLogo().setY((int)((deltaY.get(i)*(time+1)) + pointA.get(i).getY()));
                }
                //setRatio
                movingList.get(i).getLogo().setRatio(deltaRatio.get(i)*(time+1) + ratioA.get(i));
            }
            refresh();
        }
        
        pointA.clear();
        ratioA.clear();
        deltaX.clear();
        deltaY.clear();
        deltaRatio.clear();
        
        pointA = null;
        ratioA = null;
        deltaX = null;
        deltaY = null;
        deltaRatio = null;
        
        
    }
    
    protected void switchList(ArrayList<ItemComponents> movingList, ArrayList<ItemComponents> pointB, ArrayList<Boolean> switchAfter){
        for(int i = 0; i < movingList.size(); i++){
            if(switchAfter.get(i)){
                movingList.get(i).getLogo().setOpacity(0.0f);
                pointB.get(i).getLogo().setOpacity(1.0f);
            }
        }
        refresh();
    }
    
    protected void fadeList(ArrayList<AlphaImage> list, ArrayList<Boolean> in){
        
        for(int time = 0; time < 60; time++){
            
            Timer.wait(16);
            for(int character = 0; character < list.size(); character++){
                list.get(character).setOpacity(fade(list.get(character).getOpacity(), 1.0f, 0.016f, in.get(character)));
            }
            refresh();
        }
        
    }
    
    protected float fade(float opacity, float destOpacity, float deltaOpacity, boolean in){
        
        float f = 0.0f;
        
        if(in){
            if(opacity < destOpacity - deltaOpacity){
                f = opacity + deltaOpacity;
            }else{
                f = destOpacity;
            }
        }else{
            if(opacity > destOpacity + deltaOpacity){
                f = opacity - deltaOpacity;
            }else{
                f = destOpacity;
            }
        }
        
        return f;
    }
    
    private void refresh(){
        
        pane.repaint();
        pane.revalidate();
        
    }
}

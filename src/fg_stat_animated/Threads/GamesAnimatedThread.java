/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.GameComponents;
import fg_stat_animated.Timer;
import fg_stat_animated.model.Model;
import fg_stat_animated.view.GamesPanel;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class GamesAnimatedThread extends Thread{
    
    private ArrayList<GameComponents> gamesList;
    private Model m;
    
    public GamesAnimatedThread(ArrayList<GameComponents> gamesList, Model m){
        
        this.gamesList = gamesList;
        this.m = m;
        
    }
    
    public void run(){

        fadeLogoIn();
        running();
        end();
    }

    private void fadeLogoIn(){

        while(gamesList.get(0).getLogo().getOpacity() != 1.0f){    
            Timer.wait(16);
            for(int games = 0; games < gamesList.size(); games++){
                fadeIn(gamesList.get(games).getLogo(), 1.0f, 0.01f);
            }
        }
    }

    private void running(){
        while(m.getCurrentGame() == -1){
            Timer.wait(16);

            for(int games = 0; games < gamesList.size(); games++){
                if(gamesList.get(games).getLabel().isOn()){
                    fadeIn(gamesList.get(games).getBackground(), 0.5f, 0.01f);
                }else{
                    fadeOut(gamesList.get(games).getBackground(), 0.0f, 0.01f);
                }
            }
        }    
    }

    private void end(){

        int nb = m.getCurrentGame();
        int xPos = gamesList.get(nb).getLabel().getX();
        int yPos = gamesList.get(nb).getLabel().getY();
        
        double deltaX = -1*(xPos-540)/60.0;
        double deltaY = -1*(yPos)/60.0;
        double deltaR = -1*(0.5 - 0.334)/60.0;
        int count = 1;

        while(gamesList.get(nb).getLabel().getX() != 540 && gamesList.get(nb).getLabel().getY() != 0){
                try {
                    sleep(16);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                for(int games = 0; games < m.getGameList().size(); games++){

                    if(nb == games){
                        int x = (int)(deltaX*count) + xPos;
                        int y = (int)(deltaY*count) + yPos;
                        double r = deltaR*count + 0.5;
                        gamesList.get(nb).getLabel().setLocation(x, y);
                        gamesList.get(games).getLogo().setRatio(r);
                        fadeIn(gamesList.get(games).getBackground(), 1.0f, 0.016f);

                    }else{
                        fadeOut(gamesList.get(games).getBackground(), 0.0f, 0.016f);
                        fadeOut(gamesList.get(games).getLogo(), 0.0f, 0.016f);
                    }

                }
                count++;
        }

    }

    private void fadeIn(AlphaImage alpha, float maxOpacity, float addOpacity){
        if(alpha.getOpacity() < maxOpacity - 0.01f){
            alpha.setOpacity(alpha.getOpacity() + addOpacity);
        }else{
            alpha.setOpacity(maxOpacity);
        }
    }

    private void fadeOut(AlphaImage alpha, float minOpacity, float addOpacity){
        if(alpha.getOpacity() > minOpacity + 0.01f){
            alpha.setOpacity(alpha.getOpacity() - addOpacity);
        }else{
            alpha.setOpacity(minOpacity);
        }
    }
}

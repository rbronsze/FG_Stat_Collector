/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import fg_stat_animated.session.Session;
import java.util.ArrayList;

/**
 *
 * @author Polack
 */
public class SessionResults {
    
    private int currentWin, lastWin, bestWin, totalWin;
    private int currentMatch, lastMatch, bestMatch, totalMatch;
    
    public SessionResults() {
        
        this.currentWin = 0;
        this.currentMatch = 0;
        this.lastWin = 0;
        this.lastMatch = 0;
        this.bestWin = 0;
        this.bestMatch = 0;
        this.totalWin = 0;
        this.totalMatch = 0;
        
    }
    
    public void resetResults(ArrayList<Session> previousSession){
        
        this.currentWin = 0;
        this.currentMatch = 0;
        
        if(previousSession.size() > 0){
            
            this.lastWin = 0;
            this.lastMatch = 0;
            this.bestWin = 0;
            this.bestMatch = 0;
            this.totalWin = 0;
            this.totalMatch = 0;
            
            for(int s = 0; s < previousSession.size(); s++){
                
                int win = previousSession.get(s).getNbWin();
                int match = previousSession.get(s).getNbFight();
                
                this.totalWin = this.totalWin + win;
                this.totalMatch = this.totalMatch + match;
                
                if(win > this.bestWin && match >= this.bestMatch && this.getWinPercent(win, match) > this.getWinPercent(bestWin, bestMatch)){
                    this.bestWin = win;
                    this.bestMatch = match;
                }
                
                if(s == previousSession.size()-1){
                    this.lastWin = win;
                    this.lastMatch = match;
                }
            }
        }else{
            this.lastWin = -1;
            this.lastMatch = -1;
            this.bestWin = -1;
            this.bestMatch = -1;
            this.totalWin = -1;
            this.totalMatch = -1;
        }
        
    }
    
    public int[] getCurrent(){
        
        int[] current = new int[3];
        
        current[0] = this.getWinPercent(this.currentWin, this.currentMatch);
        current[1] = this.currentWin;
        current[2] = this.currentMatch;
        
        return current;
        
    }
    
    public int[] getLast(){
        
        int[] last = new int[3];
        
        last[0] = this.getWinPercent(this.lastWin, this.lastMatch);
        last[1] = this.lastWin;
        last[2] = this.lastMatch;
        
        return last;
        
    }
    
    public int[] getBest(){
        
        int[] best = new int[3];
        
        if(this.currentWin > this.bestWin && this.currentMatch >= this.bestMatch && this.getWinPercent(this.currentWin, this.currentMatch) > this.getWinPercent(bestWin, bestMatch)){
            best = this.getCurrent();
        }else{
            best[0] = this.getWinPercent(this.bestWin, this.bestMatch);
            best[1] = this.bestWin;
            best[2] = this.bestMatch;
        }
        
        return best;
        
    }
    
    public int[] getTotal(){
        
        int[] total = new int[3];
        
        total[0] = this.getWinPercent(this.totalWin + this.currentWin, this.totalMatch + this.currentMatch);
        total[1] = this.totalWin + this.currentWin;
        total[2] = this.totalMatch + this.currentMatch;
        
        return total;
        
    }

    public int getLastMatch() {
        return lastMatch;
    }
    
    /////////////
    // Setters //
    /////////////

    public void setCurrentWin(int currentWin) {
        this.currentWin = currentWin;
    }

    public void setCurrentMatch(int currentMatch) {
        this.currentMatch = currentMatch;
    }
    
    public void displayInfo(){
        
        System.out.println("Current: " + getWinPercent(this.currentWin, this.currentMatch) + " ("+ this.currentWin + " / " + this.currentMatch + ")");
        
        if(this.lastMatch > 0){
            System.out.println("Last: " + getWinPercent(this.lastWin, this.lastMatch) + " ("+ this.lastWin + " / " + this.lastMatch + ")");
            System.out.println("Best: " + getWinPercent(this.bestWin, this.bestMatch) + " ("+ this.bestWin + " / " + this.bestMatch + ")");
            System.out.println("Total: " + getWinPercent(this.totalWin, this.totalMatch) + " ("+ this.totalWin + " / " + this.totalMatch + ")");
        }
        
    }
    
    public int getWinPercent(int win, int match){
        
        int nb = 0;
        if(match > 0){
            nb = (win*100)/match;
        
            if((win*100)%match != 0){
                nb++;
            }
        }
        
        return nb;
        
    }
    
}

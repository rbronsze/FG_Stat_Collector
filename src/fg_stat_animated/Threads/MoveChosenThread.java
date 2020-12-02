/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.Threads;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.view.MatchPanel;
import java.util.ArrayList;

/**
 *
 * @author Polack
 */
public class MoveChosenThread extends AnimationThread{
    
    ArrayList<ItemComponents> movingItemList;
    ArrayList<ItemComponents> pointBList;
    ArrayList<Boolean> switchList;
    ArrayList<AlphaImage> fadingItemList;
    ArrayList<Boolean> fadeInList;
    boolean playerReady;
                                    
    public MoveChosenThread(MatchPanel pane, 
                            ArrayList<ItemComponents> movingItemList,
                            ArrayList<Boolean> switchList,
                            ArrayList<ItemComponents> pointBList,
                            ArrayList<AlphaImage> fadingItemList,
                            ArrayList<Boolean> fadeInList,
                            boolean playerReady){
        super(pane);
        this.movingItemList = movingItemList;
        this.pointBList = pointBList;
        this.switchList = switchList;
        this.fadingItemList = fadingItemList;
        this.fadeInList = fadeInList;
        this.playerReady = playerReady;
        
    }
    
    public void run(){
        
/*        
  
        Move Chosen Character
        if(isPlayerReady)
        
            if(RandomMode)
                Move Random characters to grid
                Switch not chosen random characters
                Fade Grid Characters
        
            Fade Corner & Random Button
            
*/
        moveList(movingItemList, pointBList, switchList);
        switchList(movingItemList, pointBList, switchList);
        
        if(playerReady)
            fadeList(fadingItemList, fadeInList);
        

    }
    
}

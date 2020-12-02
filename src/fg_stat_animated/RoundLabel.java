/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import javax.swing.JLabel;

/**
 *
 * @author Polack
 */
public class RoundLabel extends JLabel{
    
    private int nb, player;
    
    public RoundLabel(int nb, int player){
        
        this.nb = nb;
        this.player = player;
        
    }

    public int getNb() {
        return nb;
    }

    public int getPlayer() {
        return player;
    }
    
}

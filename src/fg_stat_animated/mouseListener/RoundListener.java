/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.mouseListener;

import fg_stat_animated.ItemComponents;
import fg_stat_animated.LogoLabel;
import fg_stat_animated.controler.Controler;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Polack
 */
public class RoundListener implements MouseListener{

    private Controler c;
    private int player;
    
    public RoundListener(Controler c, int player){
        
        this.c = c;
        this.player = player;
    
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int round = ((LogoLabel)e.getSource()).getNb();
        c.controlRound(round, player);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

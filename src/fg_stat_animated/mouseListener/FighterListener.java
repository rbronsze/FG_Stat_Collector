/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.mouseListener;

import fg_stat_animated.LogoLabel;
import fg_stat_animated.controler.Controler;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class FighterListener implements MouseListener{

    private Controler c;
    private int player;
    
    public FighterListener(Controler c, int player){
        
        this.c = c;
        this.player = player;
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getClickCount() == 2){
            System.out.println("Prout");
            int fighter = ((LogoLabel)e.getSource()).getNb();
            try {
                c.controlFighter(fighter, player);
            } catch (IOException ex) {
                Logger.getLogger(FighterListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
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

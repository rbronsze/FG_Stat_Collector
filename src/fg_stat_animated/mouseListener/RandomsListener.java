/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.mouseListener;

import fg_stat_animated.controler.Controler;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Polack
 */
public class RandomsListener implements MouseListener{

    private Controler c;
    private int nb;
    
    public RandomsListener(Controler c, int nb){
        this.c = c;
        this.nb = nb;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getClickCount() == 2){
            if(!c.getM().isRandomMode()){
                c.controlRandomCharacters(nb);
            }else{
                c.controlGrid(nb);
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
        if(!c.getM().isRandomMode())
            c.controlEnteredRandoms("images/random/" + nb );
        else
            c.controlEnteredRandoms("images/grid");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!c.getM().isRandomMode())
            c.controlExitedRandoms("images/random/" + nb );
        else
            c.controlExitedRandoms("images/grid");
    }
    
}

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
public class LogoLabel extends JLabel{
    
    private boolean isOn = false;
    private int nb;
    
    public LogoLabel(int nb){
        
        super();
        this.nb = nb;
        
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
    
}

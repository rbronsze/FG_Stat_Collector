/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.view;

import javax.swing.JPanel;

/**
 *
 * @author Polack
 */
public class MotherPanel extends JPanel{
    
    protected boolean refreshed = true;
    
    public MotherPanel(){
        super(null);
    }

    public boolean isRefreshed() {
        return refreshed;
    }

    public void setRefreshed(boolean refreshed) {
        this.refreshed = refreshed;
    }
    
    
}

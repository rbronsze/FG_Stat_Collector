/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import fg_stat_animated.controler.Controler;
import fg_stat_animated.model.Model;
import fg_stat_animated.view.MainFrame;
import java.io.IOException;

/**
 *
 * @author Polack
 */
public class FG_stat_animated {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Model m = new Model();
        Controler c = new Controler(m);
        MainFrame f = new MainFrame(m, c);
        
        c.setView(f);
        
    }
    
}

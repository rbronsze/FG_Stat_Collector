/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.view;

import fg_stat_animated.Timer;
import fg_stat_animated.controler.Controler;
import fg_stat_animated.model.Model;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Polack
 */
public class MainFrame extends JFrame{
    
    private Model m;
    private Controler c;
    
    private JPanel pane;
    private GamesPanel gp;
    private P1Panel p1p;
    
    public MainFrame(Model m, Controler c) throws IOException{
        
        this.m = m;
        this.c = c;
        //gp = new GamesPanel(m, c);
        pane = new MatchPanel(m, c);
        this.getContentPane().add(pane);
        this.setSize(1280,720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("FG Stat Animated");
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.setVisible(true);
        
//        RepaintThread t = new RepaintThread();
//        t.start();
    }

    public JPanel getPane() {
        return pane;
    }
    
    private class RepaintThread extends Thread{

        public void run(){
            
            while(true){
                Timer.wait(16);
                repaint();
                revalidate();
            }
        }
    }
    
}

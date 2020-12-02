/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import fg_stat_animated.model.Model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Polack
 */
public class StatFrame extends JFrame{
    
    private boolean isActive = true;
    private Model m;
    private StatPanel p;
    
    public StatFrame(Model m) throws IOException{
        
        p = new StatPanel(m);
        
        setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.getContentPane().add(p);
        this.setLocation(/*706*/ 0, 1044);
        this.setSize(507,35);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Characters Stats");
        this.setBackground(Color.BLUE);
        this.setResizable(false);
        
        this.setVisible(true);
        //this.addKeyListener(this);
        
        RepaintThread t = new RepaintThread(this);
        t.start();
        
    }

    public StatPanel getP() {
        return p;
    }

    
    private class RepaintThread extends Thread{
        
        private StatFrame f;
        
        public RepaintThread(StatFrame f){
            
            this.f = f;
            
        }
        
        public void run(){
            
            while(isActive){
                
                try {
                    sleep(16);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StatFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                f.repaint();
                f.revalidate();
                     
            }
            
        }
        
    }
    
    
    
}

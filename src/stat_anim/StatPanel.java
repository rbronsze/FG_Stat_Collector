/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat_anim;

import fg_stat_animated.model.Model;
import stat_anim.AnimThread;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Polack
 */
public class StatPanel extends JPanel{
 
    private Model m;
    private AnimThread t;
    private Image img;
    private int nb = 0; 
    private int v = 36;
    private boolean sessionActive = false, previousSession = false, modified = false;
    private JLabel sessionLbl, percentLbl, nbLbl; 
    private String[] text = {"Current Session: ", "Last Session: ", "Best Session: ", "Total: "};
    private SessionResults sessionResult = new SessionResults();

    public StatPanel(Model m) throws IOException{

        this.m = m;
        img = ImageIO.read(new File("stat2.png"));

        sessionLbl = new JLabel(); 
        percentLbl = new JLabel();  
        nbLbl = new JLabel(); 
        
        sessionLbl.setFont(new Font("Serif", Font.PLAIN, 22));
        percentLbl.setFont(new Font("Serif", Font.PLAIN, 22));
        nbLbl.setFont(new Font("Serif", Font.PLAIN, 22));
        
        sessionLbl.setForeground(Color.white);
        percentLbl.setForeground(Color.white);
        nbLbl.setForeground(Color.white);

        setTextVisible(false);
        
        this.add(sessionLbl);
        this.add(percentLbl);
        this.add(nbLbl);
        
        this.setBackground(Color.BLUE);

        t = new AnimThread(this);
        t.start();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(img, 0, v, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public SessionResults getSessionResult() {
        return sessionResult;
    }

    public AnimThread getT() {
        return t;
    }

    public int getNb() {
        return nb;
    }

    public int getV() {
        return v;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }

    public boolean isPreviousSession() {
        return previousSession;
    }

    public boolean isModified() {
        return modified;
    }

    public JLabel getSessionLbl() {
        return sessionLbl;
    }

    public JLabel getPercentLbl() {
        return percentLbl;
    }

    public JLabel getNbLbl() {
        return nbLbl;
    }

    public String[] getText() {
        return text;
    }

    public void setV(int v) {
        this.v = v;
    }

    public void setSessionActive(boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    public void setPreviousSession(boolean previousSession) {
        this.previousSession = previousSession;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
    public void setTextVisible(boolean b){
        
        sessionLbl.setVisible(b);
        percentLbl.setVisible(b);
        nbLbl.setVisible(b);
        
    }

    public void setTextLbl(int nb) {
        
        this.sessionLbl.setText(this.text[nb]);
        
        int[] info = {0,0,0};
        
        switch(nb){
            
            case 0:
                info = sessionResult.getCurrent();
                break;
            case 1:
                info = sessionResult.getLast();
                break;
            case 2:
                info = sessionResult.getBest();
                break;    
            case 3:
                info = sessionResult.getTotal();
                break;
        }
        
        this.percentLbl.setText(info[0] + "%");
        this.percentLbl.setForeground(getColor(info[2], info[0]));
        
        this.nbLbl.setText("(" + info[1] + "/" + info[2] + ")");
        
    }
    
    private Color getColor(int nbFight, int percent){
        
        if(nbFight > 0){
            if(percent > 50){
                return Color.GREEN;
            }
            if(percent < 50){
                return Color.RED;
            }
        }
        
        return Color.YELLOW;
        
    }

}

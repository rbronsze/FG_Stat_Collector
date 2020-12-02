/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.view;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.GameComponents;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.controler.Controler;
import fg_stat_animated.model.Model;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Polack
 */
public class P1Panel extends JPanel implements MouseListener{
    
    private Model m;
    private Controler c;
    
    private int maxrow = 0, maxcol = 0;
    
    private AlphaImage background; 
    private ItemComponents logo, randomImg;
    private ArrayList<ItemComponents> charactersList;
    
    private boolean random = false;
    
    public P1Panel(Model m, Controler c){
        
//        super(null);
//        this.m = m;
//        this.c = c;
//        
//        System.out.println("games/" + m.getGameList().get(m.getCurrentGame()).getName());
//        background = new AlphaImage(new File("games/" + m.getGameList().get(m.getCurrentGame()).getName() + "/background.png"), 1.0f, 1.0);
//        logo = new ItemComponents(540, 0, 0.334, m.getGameList().get(m.getCurrentGame()).getName() + "/logo.png");
//        
//        charactersList = new ArrayList<ItemComponents>();
//        setCells(2);
//        
//        int row = 0, col = 0;
//        
//        while(row*maxcol+col < m.getCharacterList().size()){
//            
//            int character = row*maxcol+col;
//            String name = m.getGameList().get(m.getCurrentGame()).getName() + "/characters/" + m.getCharacterList().get(character);
//            charactersList.add(new ItemComponents(row, col, maxrow, maxcol, name));
//            this.add(charactersList.get(character).getLabel());
//            charactersList.get(character).getLabel().addMouseListener(this);
//            
//        }
        
    }
    
                        ///////////////////////////////
                        // Paint Component Fonctions //
                        ///////////////////////////////
    
    @Override
    public void paintComponent(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        paintImage(g2d, background, 0, 0, 1.0);
        paintImage(g2d, logo.getLogo(), 540, 0, logo.getLogo().getRatio());
        
        for(int character = 0; character < charactersList.size(); character++){
            paintImage(g2d, 
                       charactersList.get(character).getLogo(), 
                       charactersList.get(character).getLabel().getX(), 
                       charactersList.get(character).getLabel().getY(), 
                       charactersList.get(character).getLogo().getRatio());
        }
        
        Toolkit.getDefaultToolkit().sync();
        
    }
    
    private void paintImage(Graphics2D g2d, AlphaImage img, int x, int y, double ratio){
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, img.getOpacity())); 
        g2d.drawImage(img.getImg(), x, y, (int)(img.getImg().getWidth()*ratio), (int)(img.getImg().getHeight()*ratio), this);
        
    }
    
    private void setCells(int defaultRows){
        
        // A refaire
        int characters = m.getCharacterList().size();
        
        if((characters/defaultRows > defaultRows+1) && (characters/defaultRows < 11)){
            
            maxrow = defaultRows;
            maxcol = characters/defaultRows;
            if(characters%defaultRows != 0)
                maxrow++;
            
        }else{
            int nb = defaultRows+1;
            setCells(nb);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

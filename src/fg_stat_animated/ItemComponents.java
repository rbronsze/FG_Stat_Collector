/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Polack
 */
public class ItemComponents {
    
    private AlphaImage logo;
    private LogoLabel label;
//    int x, y;
    
    public ItemComponents(int row, int col, int maxrow, int maxcol, float opacity, double ratio, String name){
        
        BufferedImage img = null;
        try {
             img = ImageIO.read(new File("games/" + name + "/off.png"));
        } catch (IOException ex) {
            Logger.getLogger(ItemComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        Point p = getPosition(row, col, maxrow, maxcol, 0.167, img.getWidth(), img.getHeight());
        setItems(img, row*maxcol+col, opacity, ratio, p.x, p.y);
        
//        label = new LogoLabel(row*maxcol+col);
//        label.setSize((int)(logo.getImg().getWidth()*ratio), (int)(logo.getImg().getHeight()*ratio));
//        label.setLocation(p);
//        logo = new AlphaImage(img, opacity, ratio, label.getX(), label.getY());
        
    }
    
    public ItemComponents(int x, int y, float opacity, double ratio, String name, int slot){
        
        BufferedImage img = null;
        try {
             img = ImageIO.read(new File(name));
        } catch (IOException ex) {
            Logger.getLogger(ItemComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setItems(img, slot, opacity, ratio, x, y);
        
//        label = new LogoLabel(slot);
//        label.setSize((int)(logo.getImg().getWidth()*ratio), (int)(logo.getImg().getHeight()*ratio));
//        label.setLocation(x, y);
//        logo = new AlphaImage(img, opacity, ratio, label.getX(), label.getY());
    }
    
    public ItemComponents(int x, int y, float opacity, double ratio, int slot){
        
        BufferedImage img = null;
        try {
             img = ImageIO.read(new File("null.png"));
        } catch (IOException ex) {
            Logger.getLogger(ItemComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setItems(img, slot, opacity, ratio, x, y);
        
//        logo = new AlphaImage(img, opacity, ratio, x , y);
//        label = new LogoLabel(slot);
//        label.setSize((int)(logo.getImg().getWidth()*ratio), (int)(logo.getImg().getHeight()*ratio));
    }
    
    private void setItems(BufferedImage img, int nb, float opacity, double ratio, int x, int y){
        
        //setItems(img, nb, opacity, ratio, x, y);
        
        label = new LogoLabel(nb);
        label.setSize((int)(img.getWidth()*ratio), (int)(img.getHeight()*ratio));
        label.setLocation(x, y);
        logo = new AlphaImage(img, opacity, ratio, label.getX(), label.getY());
        
    }
    
    private Point getPosition(int row, int col, int maxrow, int maxcol, double maxSizeRatio, int width, int height){
        
        int widthMargin = (1280 - ((int)(width*maxSizeRatio))*maxcol)/2;
        int heightMargin = 680 - (int)((height*maxSizeRatio)*maxrow);
        
        int xPos = widthMargin+(((int)(width*maxSizeRatio))*col);
        int yPos = heightMargin +(((int)(height*maxSizeRatio))*row);
        
        Point p = new Point(xPos,yPos);
//        x = (int)p.getX();
//        y = (int)p.getY();
        return p;
        
    }
    
    public AlphaImage getLogo() {
        return logo;
    }

    public LogoLabel getLabel() {
        return label;
    }
    
    public void setImg(BufferedImage img){
        logo.setImg(img);
        //label.setSize((int)(logo.getImg().getWidth()*logo.getRatio()), (int)(logo.getImg().getHeight()*logo.getRatio()));
    }
    
}

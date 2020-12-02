/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author Polack
 */
public class GameComponents{
    
    private AlphaImage background, logo;
    private LogoLabel label;
    
    public GameComponents(int row, int col, int maxrow, int maxcol, String name){
        
        background = new AlphaImage(new File("games/" + name + "/background.png"), 0.0f, 1.0);
        logo = new AlphaImage(new File("games/" + name + "/logo.png"), 0.0f, 0.5);
        label = new LogoLabel(row*maxcol+col);
        
        label.setSize(logo.getImg().getWidth(), logo.getImg().getHeight());
        label.setLocation(getPosition(row, col, maxrow, maxcol, 0.5, logo.getImg().getWidth(), logo.getImg().getHeight()));
        
    }
    
    private Point getPosition(int row, int col, int maxrow, int maxcol, double maxSizeRatio, int width, int height){
        
        int widthMargin = (1280 - ((int)(width*maxSizeRatio))*maxcol)/(maxcol+1);
        int heightMargin = (720 - ((int)(height*maxSizeRatio))*maxrow)/(maxrow+1);
        
        Point p = new Point((widthMargin*(col+1))+(((int)(width*maxSizeRatio))*col),(heightMargin*(row+1))+(((int)(height*maxSizeRatio))*row));
        
        return p;
        
    }
    
    public AlphaImage getBackground() {
        return background;
    }

    public AlphaImage getLogo() {
        return logo;
    }

    public LogoLabel getLabel() {
        return label;
    }
    
}

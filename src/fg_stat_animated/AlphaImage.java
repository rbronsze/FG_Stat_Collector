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
public class AlphaImage {
    
    private BufferedImage img = null;
    private double ratio;
    private float opacity;
    private int x, y;
    
    public AlphaImage(File file, float opacity, double ratio){
        
        if(file != null){
            try {
                this.img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(AlphaImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.opacity = opacity;
        this.ratio = ratio;
        this.x = 2000;
        this.y = 0;
        
    }

    AlphaImage(BufferedImage img, float opacity, double ratio, int x, int y) {
        this.img = img;
        this.opacity = opacity;
        this.ratio = ratio;
        this.x = x;
        this.y = y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

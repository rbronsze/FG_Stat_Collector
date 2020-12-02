/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.view;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.GameComponents;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.LogoLabel;
import fg_stat_animated.controler.Controler;
import fg_stat_animated.model.Model;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;


/**
 *
 * @author Polack
 */
public class GamesPanel extends JPanel implements MouseListener{
    
    private Model m;
    private Controler c;
    
    
//    private int maxrow = 1, maxcol = 1;
    
    // Games (State 0) //
    
    private ArrayList<GameComponents> gamesList;
    private ArrayList<ItemComponents> charactersList;
    private ItemComponents randomImg;
    
    
    public GamesPanel(Model m, Controler c) throws IOException{
        
        super(null);
        
        this.m = m;
        this.c = c;
        
        setGames();
        
    }
    
                        ///////////////////////////////
                        // Paint Component Fonctions //
                        ///////////////////////////////
    
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
        
        paintGames(g2d);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void paintGames(Graphics2D g2d){
        for(int i = 0; i < m.getGameList().size(); i++){
            paintImage(g2d, gamesList.get(i).getBackground(), 0, 0, 1.0);
        }
        
        for(int i = 0; i < m.getGameList().size(); i++){
            paintImage(g2d, gamesList.get(i).getLogo(), gamesList.get(i).getLabel().getX(), gamesList.get(i).getLabel().getY(), gamesList.get(i).getLogo().getRatio());
        }
    }
    
    private void paintP1(Graphics2D g2d, int game){
        paintImage(g2d, gamesList.get(game).getBackground(), 0, 0, 1.0);
        paintImage(g2d, gamesList.get(game).getLogo(), 540, 0, gamesList.get(game).getLogo().getRatio());
        for(int i = 0; i < m.getCharacterList().size(); i++){
            paintImage(g2d, 
                       charactersList.get(i).getLogo(), 
                       charactersList.get(i).getLabel().getX(), 
                       charactersList.get(i).getLabel().getY(), 
                       charactersList.get(i).getLogo().getRatio());
        }
        
    }
    
    private void paintImage(Graphics2D g2d, AlphaImage img, int x, int y, double ratio){
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, img.getOpacity())); 
        g2d.drawImage(img.getImg(), x, y, (int)(img.getImg().getWidth()*ratio), (int)(img.getImg().getHeight()*ratio), this);
        
    }
    
                        ////////////
                        // Others //
                        ////////////
    
    public void setGames(){
        gamesList = new ArrayList<GameComponents>();
        
        int cells[] = setGamesCells();
        
        for(int row = 0; row < cells[0]; row++){
            for(int col = 0; col < cells[1]; col++){
                
                int game = row*cells[1]+col;
                String name = m.getGameList().get(game).getName();
                
                gamesList.add(new GameComponents(row, col, cells[0], cells[1], name));
                this.add(gamesList.get(game).getLabel());
                gamesList.get(game).getLabel().addMouseListener(this);
                
            }
        }
        c.startGAT(gamesList);
    }
    
    private int[] setGamesCells(){
        
        int cells[] = new int[2];
        int w = (int)(600*0.5), h = (int)(360*0.5);
        int games = m.getGameList().size();
        int maxCol = 1280/w;
        int maxRow = 720/h;
        
        if(games <= maxCol){
            cells[1] = games;
            cells[0] = 1;
        }else{
            cells[1] = maxCol;
            cells[0] = games/maxCol + 1;
        }
        
        return cells;
        
    }
    
    public void setP1(){
//        charactersList = new ArrayList<ItemComponents>();
//        int cells[] = setCharactersCells(2);
//        System.out.println(cells[0]);
//        System.out.println(cells[1]);
//        int row = 0, col = 0;
//        
//        while(row*cells[1]+col < m.getCharacterList().size()){
//            
//            int character = row*cells[1]+col;
//            String name = m.getGameList().get(m.getCurrentGame()).getName() + "/characters/" + m.getCharacterList().get(character);
//            charactersList.add(new ItemComponents(row, col, cells[0], cells[1], name));
//            this.add(charactersList.get(character).getLabel());
//            
//            col++;
//            if(col == cells[1]){
//                row++;
//                col = 0;
//            }
//        }
    }
    
    private int[] setCharactersCells(int defaultRows){
        
        int cells[] = new int[2];
        int characters = m.getCharacterList().size();
        if((characters/defaultRows > defaultRows+1) && (characters/defaultRows < 11)){
            System.out.println("Prout");
            cells[0] = defaultRows;
            cells[1] = characters/defaultRows;
            if(characters%defaultRows != 0)
                cells[0]++;
            
        }else{
            int nb = defaultRows+1;
            cells = setCharactersCells(nb);
        }
        
        return cells;
    }
    
    
    
                        ////////////////////
                        // Mouse Listener //
                        ////////////////////
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton()==1){
            
            for(int games = 0; games < m.getGameList().size(); games++){
                
                gamesList.get(games).getLabel().setVisible(false);
                gamesList.get(games).getLabel().removeMouseListener(this);
                
            }
            this.removeAll();
            int nb = ((LogoLabel)e.getSource()).getNb();
            
            c.controlChosenGame(nb);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        ((LogoLabel)e.getSource()).setIsOn(true);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
        ((LogoLabel)e.getSource()).setIsOn(false);
        
    }
    
                        /////////////
                        // Threads //
                        /////////////
    
//    private class AlphaThread extends Thread{
//        
//        public void run(){
//            
//            while(true){
//                
//                switch(state){
//                    
//                    case 0:
//                        fadeLogoIn();
//                        running();
//                        end();
//                        break;
//                    
//                }
//            }
//        }
//        
//        private void fadeLogoIn(){
//        
//            while(gamesList.get(0).getLogo().getOpacity() != 1.0f){    
//                Timer.wait(16);
//
//                for(int games = 0; games < m.getGameList().size(); games++){
//                    fadeIn(gamesList.get(games).getLogo(), 1.0f, 0.01f);
//                }
//            }
//        }
//        
//        private void running(){
//            while(m.getCurrentGame() == -1){
//                Timer.wait(16);
//                
//                for(int games = 0; games < m.getGameList().size(); games++){
//                    if(gamesList.get(games).getLabel().isOn()){
//                        fadeIn(gamesList.get(games).getBackground(), 0.5f, 0.01f);
//                    }else{
//                        fadeOut(gamesList.get(games).getBackground(), 0.0f, 0.01f);
//                    }
//                }
//            }    
//        }
//        
//        private void end(){
//            
//            double deltaX = -1*(xPos-540)/60.0;
//            double deltaY = -1*(yPos)/60.0;
//            double deltaR = -1*(maxSizeRatio - minSizeRatio)/60.0;
//            int count = 1;
//            
//            while(gamesList.get(nb).getLabel().getX() != 540 && gamesList.get(nb).getLabel().getY() != 0){
//                    try {
//                        sleep(16);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(GamesPanel.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    for(int games = 0; games < m.getGameList().size(); games++){
//
//                        if(nb == games){
//                            int x = (int)(deltaX*count) + xPos;
//                            int y = (int)(deltaY*count) + yPos;
//                            double r = deltaR*count + maxSizeRatio;
//                            gamesList.get(nb).getLabel().setLocation(x, y);
//                            gamesList.get(games).getLogo().setRatio(r);
//                            fadeIn(gamesList.get(games).getBackground(), 1.0f, 0.016f);
//
//                        }else{
//                            fadeOut(gamesList.get(games).getBackground(), 0.0f, 0.016f);
//                            fadeOut(gamesList.get(games).getLogo(), 0.0f, 0.016f);
//                        }
//
//                    }
//                    count++;
//            }
//            
//        }
//        
//        private void fadeIn(AlphaImage alpha, float maxOpacity, float addOpacity){
//            if(alpha.getOpacity() < maxOpacity - 0.01f){
//                alpha.setOpacity(alpha.getOpacity() + addOpacity);
//            }else{
//                alpha.setOpacity(maxOpacity);
//            }
//        }
//        
//        private void fadeOut(AlphaImage alpha, float minOpacity, float addOpacity){
//            if(alpha.getOpacity() > minOpacity + 0.01f){
//                alpha.setOpacity(alpha.getOpacity() - addOpacity);
//            }else{
//                alpha.setOpacity(minOpacity);
//            }
//        }
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.controler;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.GameComponents;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.Threads.FadeAllThread;
import fg_stat_animated.Threads.FadeThread;
import fg_stat_animated.Threads.FromAToBThread;
import fg_stat_animated.Threads.GamesAnimatedThread;
import fg_stat_animated.Threads.GridThread;
import fg_stat_animated.Threads.MoveChosenThread;
import fg_stat_animated.Threads.RandomThread;
import fg_stat_animated.model.Model;
import fg_stat_animated.view.MainFrame;
import fg_stat_animated.view.MatchPanel;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Polack
 */
public class Controler {
    
    private MainFrame f;
    private Model m;
    
    public Controler(Model m){
        
        this.m = m;
        
    }

    public MainFrame getF() {
        return f;
    }

    public Model getM() {
        return m;
    }
    
    public void setView(MainFrame f) {
        this.f = f;
    }
    
    public void startGAT(ArrayList<GameComponents> gamesList){
        Thread at = new GamesAnimatedThread(gamesList, m);
        at.start();
    }
    
    public void controlChosenGame(int game){
        
//        if(game >= 0 && game < m.getGameList().size()){
//            
//            // TODO: "Choose P1" panel
//            
//            try {
//                m.setCurrentGame(game);
//            } catch (ParserConfigurationException ex) {
//                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SAXException ex) {
//                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            f.getGp().setP1();
//        }
        
    }

    public void controlChosenCharacter(int character) {
        
        if(!m.isPlayerReady(0)){
            setSlot(character, 0);
            
        }else if(!m.isPlayerReady(1)){
            setSlot(character, 1);
        }
        
    }
    
    private void setSlot(int character, int player){
        
        if(!m.isAlreadyChosen(character, player)){
            
            int slot = 0;
            while(m.getFightersTab()[slot][player].getFighter() != -1){
                slot++;
            }
            m.setFighter(character, slot, player);
//
//            ArrayList<ItemComponents> movingList = new ArrayList<ItemComponents>();
//            ArrayList<Boolean> switchList = new ArrayList<Boolean>();
//            ArrayList<ItemComponents> pointBList = new ArrayList<ItemComponents>();
//            ArrayList<AlphaImage> fadingList = new ArrayList<AlphaImage>();
//            ArrayList<Boolean> fadeInList = new ArrayList<Boolean>();
//
//            if(m.isPlayerReady(player)){ // If team is complete
//                // remove random button label
//                ((MatchPanel)f.getPane()).removeRandomButtonLabel();
//                
//                if(m.isRandomMode()){ // If random mode
//                    // add Grid Label
//                    ((MatchPanel)f.getPane()).addGridLabel();
//                    // remove ALL random label
//                    ((MatchPanel)f.getPane()).removeRandomListLabel();
//                    
//                    for(int i = 0; i < m.getRandomList().size(); i++){
//                        // add random items to movingList
//                        movingList.add(((MatchPanel)f.getPane()).getRandomList().get(i));
//                        // add pointBs
//                        int nb = ((MatchPanel)f.getPane()).getRandomList().get(i).getLabel().getNb();
//                        pointBList.add(((MatchPanel)f.getPane()).getCharactersList().get(nb));
//                    }
//                }else{ // If NOT random mode
//                    if(player == 1){ // Player 2
//                        // add Match label
//                        ((MatchPanel)f.getPane()).addMatchLabel();
//                        // add them to fading list
//                        ArrayList<ItemComponents> list = ((MatchPanel)f.getPane()).getMatchItems();
//                        for(int i = 0; i < list.size(); i++){
//                            fadingList.add(list.get(i).getLogo());
//                            fadeInList.add(true);
//                        }
//                    }
//                }
//                // add switch and corner to fadingList
//                fadingList.add(((MatchPanel)f.getPane()).getRandom().getLogo());
//                fadeInList.add(false);
//                fadingList.add(((MatchPanel)f.getPane()).getPbg()[player]);
//                fadeInList.add(true);
//                
//            }
////            else{ // If team is NOT complete
////                if(m.isRandomMode()){
////                    // remove Random label
////                    ((MatchPanel)f.getPane()).removeRandomLabel(m.getNbFromRandom(character));
////                }
////                // Switch chosen and Fighter images
////                setFighterPosition(slot, player, character, 1.0f);
////            }
//            // Switch chosen and Fighter images
//            setFighterPosition(slot, player, character, 1.0f);
//            // add Fighter to movingList
//            movingList.add(((MatchPanel)f.getPane()).getCharacters()[slot][player].getCharacter());
//            switchList.add(false);
//            pointBList.add(((MatchPanel)f.getPane()).getCharacters()[slot][player].getCharacter());
//
//            MoveChosenThread mct = new MoveChosenThread(((MatchPanel)f.getPane()),
//                                                         movingList,
//                                                         switchList,
//                                                         pointBList,
//                                                         fadingList,
//                                                         fadeInList,
//                                                         m.isPlayerReady(player));
//            
//            mct.start();
            
            setFighterImage(((MatchPanel)f.getPane()).getCharacters()[slot][player].getCharacter(), ((MatchPanel)f.getPane()).getCharactersList().get(character), 1.0f);

            FromAToBThread mt = new FromAToBThread(this, 
                                                   ((MatchPanel)f.getPane()).getCharacters()[slot][player].getCharacter(),
                                                   ((MatchPanel)f.getPane()).getCharactersList().get(character),
                                                   ((MatchPanel)f.getPane()).getCharactersList().get(character).getLabel().getLocation(),
                                                   getBPoint(character, player),
                                                   ((MatchPanel)f.getPane()).getCharactersList().get(character).getLogo().getRatio(),
                                                   0.5,
                                                   false);
            mt.start();

            // Corners 
            if(m.isPlayerReady(player)){
                FadeThread ft = new FadeThread(((MatchPanel)f.getPane()).getPbg()[player], 1.0f, 0.016f, true);
                ft.start();
            }

            // fade out random button
            if(m.isPlayerReady(0) && ((MatchPanel)f.getPane()).getRandom().getLogo().getOpacity() == 1.0f){
                ((MatchPanel)f.getPane()).removeRandomButtonLabel();
                FadeThread randomFT = new FadeThread(((MatchPanel)f.getPane()).getRandom().getLogo(), 0.0f, 0.017f, false);
                randomFT.start();
            }

            // fade in match button
            if(m.isPlayerReady(1)){
                ((MatchPanel)f.getPane()).addMatchLabel();
                FadeAllThread matchFT = new FadeAllThread(((MatchPanel)f.getPane()).getMatchImage(), 1.0f, 0.017f, true);
                matchFT.start();
            }

            this.f.repaint();
            this.f.revalidate();

            if(m.isPlayerReady(player) && player == 0)
                controlStartSession(false);

        }
    }
    
    private void setFighterPosition(int slot, int player, int character, float opacity){
        
        ItemComponents fighter, imgPosition;
        fighter = ((MatchPanel)f.getPane()).getCharacters()[slot][player].getCharacter();
        
        if(m.isRandomMode()){
            imgPosition = ((MatchPanel)f.getPane()).getRandomList().get(m.getNbFromRandom(character));
        }else{
            imgPosition = ((MatchPanel)f.getPane()).getCharactersList().get(character);
        }
        fighter.setImg(imgPosition.getLogo().getImg());       
        fighter.getLogo().setX(imgPosition.getLabel().getX());
        fighter.getLogo().setY(imgPosition.getLabel().getY());
        fighter.getLogo().setRatio(imgPosition.getLogo().getRatio());
        fighter.getLogo().setOpacity(opacity);
        
    }
    
    private void setFighterImage(ItemComponents fighter, ItemComponents character, float opacity){
        
        fighter.setImg(character.getLogo().getImg());       
        fighter.getLogo().setX(character.getLabel().getX());
        fighter.getLogo().setY(character.getLabel().getY());
        fighter.getLogo().setOpacity(opacity);
        
    }
    
    public void controlStartSession(boolean b){
        if(m.isPlayerReady(0)){
            m.setSession(b);
        }
    }
    
    public void controlRound(int round, int player) {
        m.setRound(round, player);
        try {
            ((MatchPanel)f.getPane()).getRounds()[round][player].getLogo().setImg(ImageIO.read(new File(m.getPath() + "/rounds/" + m.getRounds()[round][player] + ".png")));
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.f.repaint();
        this.f.revalidate();
    }

    public void controlSystem(int system, int character, int player) {
        
        int mode = m.getFightersTab()[character][player].getSystem()[system] + 1;
        
        if(mode == m.getGameList().get(m.getCurrentGame()).getNbMode()){
            mode = 0;
        }
        m.setSystem(mode, system, character, player);
        try {
            ((MatchPanel)f.getPane()).getCharacters()[character][player].getSystems()[system].getLogo().setImg(ImageIO.read(new File(m.getPath() + "/system/" + system + "/" + mode + ".png")));
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.f.repaint();
        this.f.revalidate();
    }

    public void controlFighter(int fighter, int player) throws IOException {
        
        int character = m.getFightersTab()[fighter][player].getFighter();
        
        if(player == 0){
            if(!m.isPlayerReady(1)){
                m.getFightersTab()[fighter][player].setFighter(-1);
                m.getSf().getP().setSessionActive(false);
                m.resetSession();
            }
        }else{
            m.getFightersTab()[fighter][player].setFighter(-1);
        }
        
        //m.displayChosen();
        
        FromAToBThread mt = new FromAToBThread(this, 
                                               ((MatchPanel)f.getPane()).getCharacters()[fighter][player].getCharacter(),
                                               ((MatchPanel)f.getPane()).getCharactersList().get(character),
                                               ((MatchPanel)f.getPane()).getCharacters()[fighter][player].getCharacter().getLabel().getLocation(),
                                               ((MatchPanel)f.getPane()).getCharactersList().get(character).getLabel().getLocation(),
                                               ((MatchPanel)f.getPane()).getCharacters()[fighter][player].getCharacter().getLogo().getRatio(),
                                               0.167, 
                                               true);
        mt.start();
        
        FadeThread cornerFT = new FadeThread(((MatchPanel)f.getPane()).getPbg()[player], 0.0f, 0.016f, false);
        cornerFT.start();
        
        if(!m.isPlayerReady(0) && ((MatchPanel)f.getPane()).getRandom().getLogo().getOpacity() == 0.0f){
            ((MatchPanel)f.getPane()).addRandomButtonLabel();
            FadeThread randomFT = new FadeThread(((MatchPanel)f.getPane()).getRandom().getLogo(), 1.0f, 0.016f, true);
            randomFT.start();
        }
        if(!m.isPlayerReady(1)){
            ((MatchPanel)f.getPane()).removeMatchLabel();
//            ArrayList<AlphaImage> list = new ArrayList<AlphaImage>();
//            for(int p = 0; p < 2; p++){
//                for(int r = 0; r < m.getRounds().length; r++){
//                    list.add(((MatchPanel)f.getPane()).getRounds()[r][p].getLogo());
//                }
//                for(int c = 0; c < m.getFightersTab().length; c++){
//                    for(int s = 0; s < m.getFightersTab()[0][0].getSystem().length; s++){
//                        list.add(((MatchPanel)f.getPane()).getCharacters()[c][p].getSystems()[s].getLogo());
//                    }
//                }
//            }
//            list.add(((MatchPanel)f.getPane()).getSave().getLogo());
            FadeAllThread matchFT = new FadeAllThread(((MatchPanel)f.getPane()).getMatchImage(), 0.0f, 0.017f, false);
            matchFT.start();
        }
        
            
        
    }

    public void controlSave() {
        if(m.isPlayerReady(0) && m.isPlayerReady(1)){
            
            int winRoundP1 = 0, winRoundP2 = 0;
            boolean isFirstRoundsOk = true;

            for(int i = 0; i < m.getRounds().length; i++){
                if(m.getRounds()[i][0] == true)
                    winRoundP1++;

                if(m.getRounds()[i][1] == true)
                    winRoundP2++;

                if(m.getRounds()[i][0] == false && m.getRounds()[i][1] == false && i < (m.getRounds().length/2)+1)
                    isFirstRoundsOk = false;

            }
            
            if(isFirstRoundsOk && ((winRoundP1 == m.getRounds().length/2+1) || (winRoundP2 == m.getRounds().length/2+1))){
                
                m.addMatchToSession();
                
                for(int player = 0; player < 2; player++){
                    for(int round = 0; round < m.getGameList().get(m.getCurrentGame()).getNbRound(); round++){
                        m.setRound(round, player, false);
                        try {
                            ((MatchPanel)f.getPane()).getRounds()[round][player].getLogo().setImg(ImageIO.read(new File(m.getPath() + "/rounds/" + m.getRounds()[round][player] + ".png")));
                        } catch (IOException ex) {
                            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }
            
            
        }
    }

    public void controlReset() {
        if(m.isPlayerReady(0))
            m.setSession(true);
    }

    public void controlRanked(boolean b) {
        m.setIsRanked(b);
        controlReset();
    }

    public void controlEnteredCharacter(int nb) {
        try {
            ((MatchPanel)f.getPane()).getCharactersList().get(nb).getLogo().setImg(ImageIO.read(new File(m.getPath() + "/characters/" + m.getCharacterList().get(nb) + "/on.png")));
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.f.repaint();
        this.f.revalidate();
    }

    public void controlExitedCharacter(int nb) {
        try {
            ((MatchPanel)f.getPane()).getCharactersList().get(nb).getLogo().setImg(ImageIO.read(new File(m.getPath() + "/characters/" + m.getCharacterList().get(nb) + "/off.png")));
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.f.repaint();
        this.f.revalidate();
    }
    
    private Point getBPoint(int character, int player){
        
        Point b = new Point(0,0);
        if(player == 1){
            b = new Point(1280 - (int)(659*0.5),0);
            ((MatchPanel)f.getPane()).getCharactersList().get(character).getLogo().setOpacity(0.0f);
        }
        return b;
    }

    public void controlEnteredRandoms(String imagesrandoms) {
        System.out.println(imagesrandoms + "/on.png");
        setImage(((MatchPanel)f.getPane()).getRandom().getLogo(), imagesrandoms + "/on.png");
        this.f.repaint();
        this.f.revalidate();
    }

    public void controlExitedRandoms(String imagesrandoms) {
        setImage(((MatchPanel)f.getPane()).getRandom().getLogo(), imagesrandoms + "/off.png");
        this.f.repaint();
        this.f.revalidate();
    }
    
    private void setImage(AlphaImage alpha, String path){
        try {
            alpha.setImg(ImageIO.read(new File(path)));
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void controlRandomCharacters(int nb) {
        
        // TODO: Change random button
        setImage(((MatchPanel)f.getPane()).getRandom().getLogo(), "images/grid/on.png");
        
        ((MatchPanel)f.getPane()).removeGridLabel();
        ((MatchPanel)f.getPane()).addRandomListLabel();
        
        m.setRandomFighters(nb);
        
        // Set Random characters to 0 opacity
        for(int i = 0; i < m.getRandomList().size(); i++){
            ((MatchPanel)f.getPane()).getCharactersList().get(m.getRandomList().get(i)).getLogo().setOpacity(0.0f);
            ((MatchPanel)f.getPane()).getRandomList().get(i).getLogo().setImg(((MatchPanel)f.getPane()).getCharactersList().get(m.getRandomList().get(i)).getLogo().getImg());
            ((MatchPanel)f.getPane()).getRandomList().get(i).getLogo().setRatio(((MatchPanel)f.getPane()).getCharactersList().get(m.getRandomList().get(i)).getLogo().getRatio());
            ((MatchPanel)f.getPane()).getRandomList().get(i).getLogo().setX(((MatchPanel)f.getPane()).getCharactersList().get(m.getRandomList().get(i)).getLogo().getX());
            ((MatchPanel)f.getPane()).getRandomList().get(i).getLogo().setY(((MatchPanel)f.getPane()).getCharactersList().get(m.getRandomList().get(i)).getLogo().getY());
            ((MatchPanel)f.getPane()).getRandomList().get(i).getLogo().setOpacity(1.0f);
        }
        
        RandomThread rt = new RandomThread(this, ((MatchPanel)f.getPane()).getCharactersList(), ((MatchPanel)f.getPane()).getRandomList());
        rt.start();
    }

    public void controlGrid(int nb) {
        
        
        setImage(((MatchPanel)f.getPane()).getRandom().getLogo(), "images/random/" + nb + "/on.png");
        
        ((MatchPanel)f.getPane()).removeRandomListLabel();
        ((MatchPanel)f.getPane()).addGridLabel();
        
        GridThread gt = new GridThread(this, ((MatchPanel)f.getPane()).getCharactersList(), ((MatchPanel)f.getPane()).getRandomList());
        gt.start();
    }
    
    
    
}

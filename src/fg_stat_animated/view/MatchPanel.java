/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.view;

import fg_stat_animated.AlphaImage;
import fg_stat_animated.CharacterComponents;
import fg_stat_animated.ItemComponents;
import fg_stat_animated.controler.Controler;
import fg_stat_animated.model.Model;
import fg_stat_animated.mouseListener.CharacterListener;
import fg_stat_animated.mouseListener.FighterListener;
import fg_stat_animated.mouseListener.RandomsListener;
import fg_stat_animated.mouseListener.RoundListener;
import fg_stat_animated.mouseListener.SaveListener;
import fg_stat_animated.mouseListener.SystemListener;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author Polack
 */
public class MatchPanel extends JPanel implements MouseListener{
    
    private Model m;
    private Controler c;
    
    private AlphaImage background; 
    private AlphaImage pbg[];
    private ItemComponents logo, random, save;
    private ArrayList<ItemComponents> charactersList;
    
    private ArrayList<ItemComponents> randomList;
    private CharacterComponents[][] characters;
    private ItemComponents[][] rounds;
    
    public MatchPanel(Model m, Controler c){
        
        super(null);
        
        this.m = m;
        this.c = c;
        
        int currentGame = m.getCurrentGame();
        
        // background
        background = new AlphaImage(new File("games/" + m.getGameList().get(currentGame).getName() + "/background.png"), 1.0f, 1.0);
        
        // Player Corner
        pbg = new AlphaImage[2];
        
        pbg[0] = new AlphaImage(new File("p1bg.png"), 0.0f, 1.0);
        pbg[1] = new AlphaImage(new File("p2bg.png"), 0.0f, 1.0);
        
        // Logo
        logo = new ItemComponents(540, 0, 1.0f, 0.334, "games/" + m.getGameList().get(currentGame).getName() + "/logo.png", -1);
        
        // Random Characters
        
        randomList = new ArrayList<ItemComponents>();
        
        randomList.add(new ItemComponents(145, 62, 0.0f, 0.5, -1));
        randomList.add(new ItemComponents(805, 62, 0.0f, 0.5, -1));
        randomList.add(new ItemComponents(475, 268, 0.0f, 0.5, -1));
        randomList.add(new ItemComponents(145, 474, 0.0f, 0.5, -1));
        randomList.add(new ItemComponents(805, 474, 0.0f, 0.5, -1));
        
        // Characters
        charactersList = new ArrayList<ItemComponents>();
        int cells[] = setCharactersCells(2);
        int row = 0, col = 0;
        
        while(row*cells[1]+col < m.getCharacterList().size()){
            
            int character = row*cells[1]+col;
            String name = m.getGameList().get(m.getCurrentGame()).getName() + "/characters/" + m.getCharacterList().get(character);
            charactersList.add(new ItemComponents(row, col, cells[0], cells[1], 1.0f, 0.167, name));
            this.add(charactersList.get(character).getLabel());
            charactersList.get(character).getLabel().addMouseListener(new CharacterListener(c));
            
            col++;
            if(col == cells[1]){
                row++;
                col = 0;
            }
        }
        
        // Random
        double r = charactersList.get(0).getLogo().getRatio();
        random = new ItemComponents(585, 
                                    199,
                                    1.0f,
                                    0.167, 
                                    "images/random/5/off.png",
                                    -1);
        this.add(random.getLabel());
        random.getLabel().addMouseListener(new RandomsListener(c, 5));
        
        // Chosen characters
        characters = new CharacterComponents[m.getGameList().get(currentGame).getNbCharacters()][2];
        
        double ratio = 0.5;
        if(m.getGameList().get(currentGame).getNbCharacters() == 3)
            ratio = 0.25;
        
        for(int character = 0; character < characters.length; character++){
            
            double w = 659*ratio;
            int nbSystem = m.getGameList().get(currentGame).getNbSystem();
            characters[character][0] = new CharacterComponents(nbSystem, (int)(character*w), 0, character, 0.0f, ratio, m.getPath());
            characters[character][1] = new CharacterComponents(nbSystem, (int)(1280 - (character+1)*w), 0, character, 0.0f, ratio, m.getPath());
            
            this.add(characters[character][0].getCharacter().getLabel());
            characters[character][0].getCharacter().getLabel().addMouseListener(new FighterListener(c, 0));
            
            this.add(characters[character][1].getCharacter().getLabel());
            characters[character][1].getCharacter().getLabel().addMouseListener(new FighterListener(c, 1));
            
            for(int system = 0; system < nbSystem; system++){
                //this.add(characters[character][0].getSystems()[system].getLabel());
                characters[character][0].getSystems()[system].getLabel().addMouseListener(new SystemListener(c, character, 0));
                //this.add(characters[character][1].getSystems()[system].getLabel());
                characters[character][1].getSystems()[system].getLabel().addMouseListener(new SystemListener(c, character, 1));
            }
            
        }
        
        rounds = new ItemComponents[m.getGameList().get(currentGame).getNbRound()][2];
        
        for(int slot = 0; slot < rounds.length; slot++){
            int xMargin = (int)(logo.getLogo().getImg().getWidth()*logo.getLogo().getRatio()/2);
            rounds[slot][0] = new ItemComponents(1280/2 - xMargin - 50, 
                                                 5 + slot*45,
                                                 0.0f,
                                                 1.0, 
                                                 m.getPath() + "/rounds/" + m.getRounds()[slot][0] + ".png", 
                                                 slot);
            //this.add(rounds[slot][0].getLabel());
            rounds[slot][0].getLabel().addMouseListener(new RoundListener(c, 0));
            rounds[slot][1] = new ItemComponents(1280/2 + xMargin + 10, 
                                                 5 + slot*45,
                                                 0.0f, 
                                                 1.0, 
                                                 m.getPath() + "/rounds/" + m.getRounds()[slot][0] + ".png", 
                                                 slot);
            //this.add(rounds[slot][1].getLabel());
            rounds[slot][1].getLabel().addMouseListener(new RoundListener(c, 1));
        }
        
        save = new ItemComponents(1280/2 - 40,
                                  rounds[rounds.length-1][0].getLabel().getY() + (int)(rounds[rounds.length-1][0].getLogo().getImg().getHeight()*rounds[rounds.length-1][0].getLogo().getRatio()),
                                  0.0f,
                                  1.0,
                                  m.getPath() + "/save.png",
                                  -1);
        //this.add(save.getLabel());
        save.getLabel().addMouseListener(new SaveListener(c));
        
        this.addMouseListener(this);
        
    }
    
                            /////////////
                            // Getters //
                            /////////////

    public ArrayList<ItemComponents> getMatchItems(){
        
        ArrayList<ItemComponents> list = new ArrayList<ItemComponents>();
        for(int p = 0; p < 2; p++){
            for(int r = 0; r < m.getRounds().length; r++){
                list.add(this.rounds[r][p]);
            }
            for(int c = 0; c < m.getFightersTab().length; c++){
                for(int s = 0; s < m.getFightersTab()[0][0].getSystem().length; s++){
                    list.add(this.characters[c][p].getSystems()[s]);
                }
            }
        }
        list.add(save);
        return list;
    }
    
    public ArrayList<AlphaImage> getMatchImage(){
        
        ArrayList<AlphaImage> list = new ArrayList<AlphaImage>();
        for(int p = 0; p < 2; p++){
            for(int r = 0; r < m.getRounds().length; r++){
                list.add(this.rounds[r][p].getLogo());
            }
            for(int c = 0; c < m.getFightersTab().length; c++){
                for(int s = 0; s < m.getFightersTab()[0][0].getSystem().length; s++){
                    list.add(this.characters[c][p].getSystems()[s].getLogo());
                }
            }
        }
        list.add(save.getLogo());
        return list;
    }
    
    public AlphaImage[] getPbg() {
        return pbg;
    }

    public CharacterComponents[][] getCharacters() {
        return characters;
    }

    public ItemComponents[][] getRounds() {
        return rounds;
    }
    
    public ItemComponents getRandom() {
        return random;
    }

    public ArrayList<ItemComponents> getCharactersList() {
        return charactersList;
    }

    public ItemComponents getSave() {
        return save;
    }

    public ArrayList<ItemComponents> getRandomList() {
        return randomList;
    }

    public Model getM() {
        return m;
    }
    
                            /////////////
                            // Setters //
                            /////////////
    
    private int[] setCharactersCells(int defaultRows){
        
        int cells[] = new int[2];
        int characters = m.getCharacterList().size();
        if((characters/defaultRows > defaultRows+1) && (characters/defaultRows < 11)){
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
    
                            ////////////
                            // Others //
                            ////////////
    
    public void addRandomLabel(int nb){
        
        this.add(this.randomList.get(nb).getLabel());
        
    }
    
    public void removeRandomLabel(int nb){
        
        this.remove(this.randomList.get(nb).getLabel());
        
    }
    
    public void addRandomListLabel(){
        for(int i = 0; i < this.randomList.size(); i++){
            this.add(this.randomList.get(i).getLabel());
        }
    }
    
    public void removeRandomListLabel(){
        for(int i = 0; i < this.randomList.size(); i++){
            this.remove(this.randomList.get(i).getLabel());
        }
    }
    
    public void addRandomButtonLabel(){
        
        this.add(this.random.getLabel());
        
    }
    
    public void removeRandomButtonLabel(){
        
        this.remove(this.random.getLabel());
        
    }
    
    public void addMatchLabel(){
        for(int player = 0; player < 2; player++){
            for(int round = 0; round < m.getRounds().length; round++){
                this.add(this.getRounds()[round][player].getLabel());
            }
            for(int character = 0; character < m.getFightersTab().length; character++){
                for(int system = 0; system < m.getFightersTab()[0][0].getSystem().length; system++){
                    this.add(this.getCharacters()[character][player].getSystems()[system].getLabel());
                }
            }
        }
        
        this.add(this.getSave().getLabel());
    }
    
    public void removeMatchLabel(){
        for(int player = 0; player < 2; player++){
            for(int round = 0; round < m.getRounds().length; round++){
                this.remove(this.getRounds()[round][player].getLabel());
            }
            for(int character = 0; character < m.getFightersTab().length; character++){
                for(int system = 0; system < m.getFightersTab()[0][0].getSystem().length; system++){
                    this.remove(this.getCharacters()[character][player].getSystems()[system].getLabel());
                }
            }
        }
        this.remove(this.getSave().getLabel());
    }
    
    public void addGridLabel(){
        
        for(int ch = 0; ch < this.charactersList.size(); ch++){
            this.add(this.charactersList.get(ch).getLabel());
        }
        
    }
    public void removeGridLabel(){
        
        for(int ch = 0; ch < this.charactersList.size(); ch++){
            this.remove(this.charactersList.get(ch).getLabel());
        }
        
    }
    
    public void addCharacterLabel(int character){
        
        this.add(this.charactersList.get(character).getLabel());
        
    }
    
    public void removeCharacterLabel(int character){
        
        this.remove(this.charactersList.get(character).getLabel());
        
    }
    
                            ///////////////////////////////
                            // Paint Component Fonctions //
                            ///////////////////////////////
    
    @Override
    public void paintComponent(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        // Background
        paintImage(g2d, background, 0, 0);
        
        // Player corner
        for(int player = 0; player < 2; player++){
            paintImage(g2d, pbg[player], 0, 0);
        }
        
        // Logo
        paintImage(g2d, logo.getLogo(), logo.getLogo().getX(), logo.getLogo().getY());
        
        // Characters
        for(int character = 0; character < charactersList.size(); character++){
            paintImage(g2d, 
                       charactersList.get(character).getLogo(), 
                       charactersList.get(character).getLogo().getX(), 
                       charactersList.get(character).getLogo().getY());
        }
            
        // Random
        paintImage(g2d, random.getLogo(), random.getLogo().getX(), random.getLogo().getY());
        
        for(int i = 0; i < randomList.size(); i++){
            
            paintImage(g2d,
                    randomList.get(i).getLogo(),
                    randomList.get(i).getLogo().getX(),
                    randomList.get(i).getLogo().getY());
                       
        }
        
        // Chosen Characters
        for(int player = 0; player < 2; player++){
            
            // Rounds
            for(int round = 0; round < rounds.length; round++){
                paintImage(g2d, 
                   rounds[round][player].getLogo(), 
                   rounds[round][player].getLogo().getX(), 
                   rounds[round][player].getLogo().getY());
            }
            
            for(int character = 0; character < characters.length; character++){
                
                // Team
                //if(m.getFightersTab()[character][player].getFighter() != -1){
                paintImage(g2d, 
                   characters[character][player].getCharacter().getLogo(), 
                   characters[character][player].getCharacter().getLogo().getX(), 
                   characters[character][player].getCharacter().getLogo().getY());

                // Systems
                for(int system = 0; system < m.getGameList().get(m.getCurrentGame()).getNbSystem(); system++){
                    paintImage(g2d, 
                           characters[character][player].getSystems()[system].getLogo(), 
                           characters[character][player].getSystems()[system].getLogo().getX(), 
                           characters[character][player].getSystems()[system].getLogo().getY());
                    }
                //}
            }
        }
        
        // Save
        paintImage(g2d, 
           save.getLogo(),
           save.getLogo().getX(),
           save.getLogo().getY());
        
        Toolkit.getDefaultToolkit().sync();
        
    }
    
    private void paintImage(Graphics2D g2d, AlphaImage img, int x, int y){
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, img.getOpacity())); 
        g2d.drawImage(img.getImg(), x, y, (int)(img.getImg().getWidth()*img.getRatio()), (int)(img.getImg().getHeight()*img.getRatio()), this);
        
    }

    ////////////////////////
    // Mouse Interactions //
    ////////////////////////
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            PopUpMenu popup = new PopUpMenu();
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class PopUpMenu extends JPopupMenu{
        
        private JMenuItem resetMI, rankedMI;;
        
        public PopUpMenu(){
            
            resetMI = new JMenuItem("Reset Session");
            if(m.isRanked())
                rankedMI = new JMenuItem("Ranked (set to Casual)");
            else
                rankedMI = new JMenuItem("Casual (set to Ranked)");
            
            add(resetMI);
            add(rankedMI);
            
            resetMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.controlReset();
                }
                
            });
            
            rankedMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                   //c.controlRemove(player, fighter);
                   if(m.isRanked()){
                       c.controlRanked(false);
                   }else{
                       c.controlRanked(true);
                   }
                }
            });
            
        }
        
    }
    
}

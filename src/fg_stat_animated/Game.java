/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated;

/**
 *
 * @author Polack
 */
public class Game {
    
    protected String name;
    //protected ArrayList<Character> charactersList;
    protected int nbRound, nbSystem, nbMode;
    
    public Game(String name, String nbRound, String nbSystem, String nbMode){
        this.name = name;
        this.nbRound = Integer.parseInt(nbRound);
        this.nbSystem = Integer.parseInt(nbSystem);
        this.nbMode = Integer.parseInt(nbMode);
        
    }

    public String getName() {
        return name;
    }

    public int getNbRound() {
        return nbRound;
    }

    public int getNbSystem() {
        return nbSystem;
    }

    public int getNbMode() {
        return nbMode;
    }
    
    public int getNbCharacters(){
        
        if(this.nbRound == 1)
            return 3;
        else
            return 1;
        
    }
    
}

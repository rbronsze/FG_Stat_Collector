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
public class CharacterComponents {
    
    private ItemComponents character;
    private ItemComponents[] systems;
    
    public CharacterComponents(int nbSystem, int x, int y, int slot, float opacity, double ratio, String game){
        
        systems = new ItemComponents[nbSystem]; 
        character = new ItemComponents(x, y, opacity, ratio, slot);
        
        int margin = (int)(((659*ratio) - (140*ratio*nbSystem))/2);
        for(int system = 0; system < nbSystem; system++){
            systems[system] = new ItemComponents(x + margin + (int)(140*ratio*system), 
                                                 (int)(411*ratio), 
                                                 opacity,
                                                 ratio, 
                                                 game + "/system/" + system + "/0.png", 
                                                 system);
        }
        
    }

    public ItemComponents getCharacter(){
        return character;
    }

    public ItemComponents[] getSystems(){
        return systems;
    }
    
    
}

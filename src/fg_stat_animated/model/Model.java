/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fg_stat_animated.model;

import fg_stat_animated.session.Fighter;
import fg_stat_animated.Game;
import fg_stat_animated.session.Match;
import fg_stat_animated.session.Session;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import stat_anim.StatFrame;

/**
 *
 * @author Polack
 */
public class Model {
    
    private ArrayList<Game> gameList;
    private ArrayList<String> characterList;
    private ArrayList<Integer> pickChancesList;
    private ArrayList<Integer> randomList = null;
    
    private int currentGame = -1;
    private boolean isRanked = true;
    private String mode[] = {"Casual", "Ranked"};
    
    private Fighter[][] fightersTab;
    private boolean[][] rounds;
    
    private Session currentSession;
    private ArrayList<Session> previousSession;
    
    private StatFrame sf;
    
    public Model(){
        
        this.gameList = new ArrayList<Game>();
        try {
            openXmlGameFile("games/games.xml");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            setCurrentGame(0);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sf = new StatFrame(this);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /////////////
    // Getters //
    /////////////
    
    public boolean isRandomMode(){
        
        if(this.randomList == null)
            return false;
            
        return true;
    }
    
    public boolean isAlreadyChosen(int character, int player){
        
        for(int fighter = 0; fighter < this.fightersTab.length; fighter++){
            if(character == this.fightersTab[fighter][player].getFighter()){
                return true;
            }
        }
        
        return false;
        
    }
    
    public boolean isSameCharacter(int nb, ArrayList<Integer> l){
        
        for(int i = 0; i < l.size(); i++){
            if(nb == l.get(i)){
                return true;
            }
        }
        
        return false;
    }

    public boolean isRanked() {
        return isRanked;
    }
    
    public int getNbFromRandom(int character){
        
        int nb = 0;
        
        for(int i = 0; i < this.randomList.size(); i++){
            if(character == this.randomList.get(i)){
                return i;
            }
        }
        
        return nb;
    }
    
    public StatFrame getSf(){
        
        return sf;
        
    }

    public boolean isPlayerReady(int player) {
        boolean b = true;
        
        for(int i = 0; i < fightersTab.length; i++){
            if(fightersTab[i][player].getFighter() == -1){
                b = false;
            }
        }
        return b;
    }

    public ArrayList<Integer> getRandomList() {
        return randomList;
    }
    
    public Fighter[][] getFightersTab() {
        return fightersTab;
    }

    public boolean[][] getRounds() {
        return rounds;
    }
    
    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<String> getCharacterList() {
        return characterList;
    }

    public ArrayList<Integer> getPickChancesList() {
        return pickChancesList;
    }

    public int getCurrentGame() {
        return currentGame;
    }
    
    public String getPath(){
        return "games/" + this.gameList.get(currentGame).getName();
    }
    
    private ArrayList<String> getFightersName(){
        
        ArrayList<String> list =  new ArrayList<String>();
        
        for(int i = 0; i < this.fightersTab.length; i++){
            list.add(this.characterList.get(this.fightersTab[i][0].getFighter()));
            
        }
        return list;
        
    }
    
    /////////////
    // Setters //
    /////////////

    public void setIsRanked(boolean isRanked) {
        this.isRanked = isRanked;
    }
    
    
    public void setSession(boolean modified){
        
        this.currentSession = new Session(this.gameList.get(this.currentGame).getName(), getFightersName(), isRanked ? mode[1] : mode[0]);
        this.previousSession = new ArrayList<Session>();
        int nbFiles = this.currentSession.getNbFiles();
        if(nbFiles > 0){
            for(int i = 0; i < nbFiles; i++){
                this.previousSession.add(new Session(this.gameList.get(this.currentGame).getName(), 
                                                         getFightersName(), 
                                                         i,
                                                         this.gameList.get(this.currentGame).getNbCharacters(),
                                                         this.gameList.get(this.currentGame).getNbMode(),
                                                         this.gameList.get(this.currentGame).getNbRound(),
                                                         isRanked ? mode[1] : mode[0]));
            }
        }
        
        this.sf.getP().getSessionResult().resetResults(previousSession);
        this.sf.getP().setSessionActive(true);
        this.sf.getP().setModified(modified);
        boolean b = this.previousSession.size() > 0;
        System.out.println(this.previousSession.size() > 0);
        this.sf.getP().setPreviousSession(b);
        
        
    }

    public void setRound(int round, int player) {
        this.rounds[round][player] = !this.rounds[round][player];
    }
    
    public void setRound(int round, int player, boolean b) {
        
        this.rounds[round][player] = b;
        
    }

    public void setSystem(int mode, int system, int character, int player) {
        this.fightersTab[character][player].setSystem(system, mode);
    }
    

    public void setCurrentGame(int currentGame) throws ParserConfigurationException, IOException, SAXException {
        this.currentGame = currentGame;
        
        if(this.currentGame != -1){
            
            int maxCharacter = this.gameList.get(currentGame).getNbCharacters();
            fightersTab = new Fighter[maxCharacter][2];
            for(int character = 0; character < maxCharacter; character++){
                for(int player = 0; player < 2; player++){
                    fightersTab[character][player] = new Fighter(this.gameList.get(currentGame).getNbSystem());
                }
            }
            this.rounds = setRoundsTabs(this.gameList.get(currentGame).getNbRound());
            this.characterList = new ArrayList<String>();
            this.pickChancesList = new ArrayList<Integer>();
            openXmlCharactersFile(this.getPath() + "/characters.xml");
            setPickChances();
            
        }
    }
    
    public void setFighter(int character, int slot, int player){
        
        try {
            fightersTab[slot][player].setFighter(character);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean[][] setRoundsTabs(int nbRounds){
        
        boolean tab[][] = new boolean[nbRounds][2];
        
        for(int i = 0; i < nbRounds; i++){
            for(int j = 0; j < 2; j++){
                tab[i][j] = false;
            }
        }
        
        return tab;
        
    }
    
    private void setPickChances(){
        
        int nb = pickChancesList.size()/2;
        boolean b = false;
        
        for(int i = 0; i < pickChancesList.size(); i++){
            if(pickChancesList.get(i) == -1){
                pickChancesList.set(i, nb);
                b = true;
            }
        }
        
        if(b){
            try {
                writeCharacterXmlFile();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setRandomFighters(int nbRandom){
        
        ArrayList<Integer> pickList = null;
        randomList = new ArrayList<Integer>();
        pickList = new ArrayList<Integer>();
        for(int nbFighter = 0; nbFighter < this.pickChancesList.size(); nbFighter++){
            for(int nb = 0; nb < this.pickChancesList.get(nbFighter); nb++){
                pickList.add(nbFighter);
            }
        }
        
        while(randomList.size() < nbRandom){
            
            int randomFighter = ThreadLocalRandom.current().nextInt(0, pickList.size());
            if(!isSameCharacter(pickList.get(randomFighter), randomList)){
                randomList.add(pickList.get(randomFighter));
            }
            
        }
        
    }
    
    ///////////
    // OTHER //
    ///////////
    
    public void resetRandom(){
        
        this.randomList.clear();
        this.randomList = null;
        
    }
    
    public void addMatchToSession(){
        
        this.currentSession.getMatchList().add(new Match(LocalDateTime.now(), this.fightersTab, this.rounds));
        try {
            this.currentSession.writeSessionFile();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.sf.getP().getSessionResult().setCurrentWin(this.currentSession.getNbWin());
        this.sf.getP().getSessionResult().setCurrentMatch(this.currentSession.getNbFight());
        this.sf.getP().setModified(true);
        
        
    }
    
    public void resetSession(){
        
        this.currentSession = null;
        this.previousSession = null;
        
    }
    
    public void displayChosen(){
        
        for(int i = 0; i < this.fightersTab.length; i++)
            System.out.println("P1: " + fightersTab[i][0].getFighter() + " / P2: " + fightersTab[i][1].getFighter());
        
    }
    ////////////////
    // XML READER //
    ////////////////
    
    private void openXmlCharactersFile(String filePath) throws ParserConfigurationException, IOException, SAXException{
        
        File fileXML = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
               
        NodeList gameNodeList = xml.getElementsByTagName("Character");
			
	for (int temp = 0; temp < gameNodeList.getLength(); temp++) {

            Node gameNode = gameNodeList.item(temp);

            if (gameNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) gameNode;
                this.characterList.add(eElement.getAttribute("name"));
                this.pickChancesList.add(Integer.parseInt(eElement.getAttribute("pick_chances")));
            }
	}
    }
    
    private void openXmlGameFile(String filePath) throws ParserConfigurationException, IOException, SAXException{
        
        File fileXML = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
               
        NodeList gameNodeList = xml.getElementsByTagName("Game");
			

	for (int temp = 0; temp < gameNodeList.getLength(); temp++) {

            Node gameNode = gameNodeList.item(temp);

            if (gameNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) gameNode;
                this.gameList.add(new Game(eElement.getAttribute("name"), 
                                           eElement.getAttribute("nb_round"), 
                                           eElement.getAttribute("nb_system"), 
                                           eElement.getAttribute("nb_mode_per_system")));
            }
	}
    }    

    ////////////////
    // XML WRITER //
    ////////////////
    
    private void writeCharacterXmlFile() throws ParserConfigurationException, TransformerConfigurationException, TransformerException{
        
        String xmlFilePath = "games/" + this.gameList.get(this.currentGame).getName() + "/characters.xml";
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Characters");
        
        document.appendChild(root);
        
        for(int i = 0; i < this.pickChancesList.size(); i++){
            Element character = document.createElement("Character");
            
            Attr name = document.createAttribute("name");
            Attr pickChances = document.createAttribute("pick_chances");
            
            name.setValue(this.characterList.get(i));
            pickChances.setValue(Integer.toString(this.pickChancesList.get(i)));
            
            character.setAttributeNode(name);
            character.setAttributeNode(pickChances);
            
            root.appendChild(character);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        
        transformer.transform(domSource, streamResult);

        
    }    
    
}

package cz.mendelu.catan.game;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.cards.BiggestArmyCard;
import cz.mendelu.catan.cards.LongestRoadCard;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.player.Player;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public class Game implements Serializable {
    public static final int MAX_NUMBER_OF_PLAYERS  = 4;
    public static final int NUMBER_OF_STARTING_ROUNDS  = 2;
    public static final int NUMBER_OF_POINTS_FOR_VICTORY  = 10;

    private int currentlyPlayingIndex;
    private GamingBoard gamingBoard;
    private List<Player> players;
    private Player currentlyPlayingPlayer;

    //Game statistics
    private int turnCounter;
    private int fullRoundCounter;
    private int biggestArmy;

    public Game(){
        this.gamingBoard = new GamingBoard();
        this.players = new ArrayList<>();
        this.currentlyPlayingPlayer = null;
        this.currentlyPlayingIndex = 0;
        this.turnCounter = 1;
        this.fullRoundCounter = 1;
    };

    public Game(List<Player> players){
        this.gamingBoard = new GamingBoard();
        this.players = players;
        this.currentlyPlayingPlayer = players.get(0);
        this.currentlyPlayingIndex = 0;
        this.turnCounter = 0;
        this.fullRoundCounter = 1;
    }

    /**
     * Metoda pro ukončení kola stávajícího hráče a začátek kola dalšího na řadě
     *
     * @author xmusil5
     */
    public void nextTurn(){
        currentlyPlayingPlayer.onTurnEnded();
        if (currentlyPlayingIndex >= (players.size() - 1)){
            currentlyPlayingIndex = 0;
            fullRoundCounter++;
        } else {
            currentlyPlayingIndex++;
        }
        this.gamingBoard.getCardDeck().assignBiggestArmyCardToRightPlayer(this.players);
        this.gamingBoard.getCardDeck().assignLongestRoadCardToRightPlayer(this.players, this.gamingBoard);
        currentlyPlayingPlayer = players.get(currentlyPlayingIndex);
        turnCounter++;
    }

    /**
     * Metoda vygeneruje náhodná čísla jako imitace hodu dvou kostek a započne proceduru přidělování surovin hráčům.
     *
     * @author xmusil5
     */
    public int rollDices(){
        Random resultGenerator = new Random();
        int diceRoll1 = resultGenerator.nextInt(6) + 1;
        int diceRoll2 = resultGenerator.nextInt(6) + 1;
        this.gamingBoard.dicesHaveBeenRolled(diceRoll1 + diceRoll2);
        return diceRoll1 + diceRoll2;
    }

    /**
     * Metoda sebere hráčům půlku karet (pokud jich mají více než je limit pro padnutí sedmičky) a vrátí je do banku
     *
     * @param players hráči, kterým se budou karty odebírat (právě hrajícímu hráči se neodeberou)
     * @version etapa 5
     */
    public void sevenRolled(List<Player> players){
        int cardTolerance = 7;
        String names = "";
        for (var player : players){
            if (!player.equals(this.currentlyPlayingPlayer)){
                int playersNumberOfResourceCards = player.getNumberOfResourceCards();
                if (playersNumberOfResourceCards > cardTolerance){
                    int cardsToSurrender = (playersNumberOfResourceCards % 2 == 0) ? playersNumberOfResourceCards/2 : (playersNumberOfResourceCards/2) - 1;
                    List<ResourceCard> resourceCards = player.surrenderRandomResourceCards(cardsToSurrender);
                    if (resourceCards != null){
                        this.gamingBoard.getCardDeck().addResourceCards(resourceCards);
                        names += player.getName() + ", ";
                    }
                }
            }
        }
        if (!names.isBlank()){
            CatanLogger.getCatanLogger().addLog("Hracum: " + names + "byla odebrana pulka karet a vracena do banku");
        }
    }

    /**
     * Metoda zjistí, jestli je mezi hráči někdo, kdo dosáhl herního limitu pro vítěztví.
     *
     * @return hráč, který vyhrál hru (pokud žádný není, vrátí se null)
     * @author xmusil5
     */
    public Player getVictoriousPlayer(){
        for (var p : this.players){
            if (p.getScore() >= NUMBER_OF_POINTS_FOR_VICTORY){
                return p;
            }
        }
        return null;
    }

    public Player getPlayerByName(String name){
        for (var p : this.players){
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public GamingBoard getGamingBoard(){
        return this.gamingBoard;
    }

    public CardDeck getCardDeck(){
        return this.gamingBoard.getCardDeck();
    }

    public Player getCurrentlyPlayingPlayer() {
        return currentlyPlayingPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        if (players != null) {
            this.players = players;
            this.currentlyPlayingIndex = 0;
            this.currentlyPlayingPlayer = this.players.get(0);
        }
    }

    public Integer getTurnCounter() {
        return this.turnCounter;
    }

    public void setTurnCounter (int turns) {this.turnCounter = turns;}

    public void setGamingBoard(GamingBoard gamingBoard) {
        this.gamingBoard = gamingBoard;
    }

    /**
     *  Metoda vracející hráče s kartou největší armády
     *
     * @author xtrneny1
     */
    public Player getPlayerWithBiggestArmyCard(){
        Player player = null;
        for (var p : this.players){
            if (p.getBiggestArmyCard() != null){
                player = p;
                break;
            }
        }
        return player;
    }

    /**
     * Metoda vrací hráče, který právě drží kartu nejdelší cesty
     *
     * @author xmusil5
     */
    public Player getPlayerWithLongestRoadCard(){
        Player player = null;
        for (var p : this.players){
            if (p.getLongestRoadCard() != null){
                player = p;
                break;
            }
        }
        return player;
    }

    public Integer getNumberOfMostUsedKnights () {
        int counter = 0;

        for (var player: players) {
            if (player.getKnightsUsed() > counter) {
                counter = player.getKnightsUsed();
            }
        }

        return counter;
    }

    public int getFullRoundCounter() {
        return fullRoundCounter;
    }
}
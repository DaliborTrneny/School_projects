package cz.mendelu.catan.player;

import cz.mendelu.catan.cards.BiggestArmyCard;
import cz.mendelu.catan.cards.LongestRoadCard;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.cards.actioncards.Knight;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.gamingboard.Path;
import cz.mendelu.catan.gamingboard.Tile;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Road;
import cz.mendelu.catan.player.pieces.Village;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class Player implements Serializable {
    //Tyto promenne jsou pouzity pro vypocitavani hracova skore za postavene vesnice a mesta
    private int numberOfStartingVillagePieces;
    private int numberOfStartingCityPieces;

    private String name;
    private Color color;
    private GamingBoard gamingBoard;

    private int knightsUsed;
    private int victoryPointCardsUsed;
    private int maxRoadLength;
    private boolean playedActionCardThisTurn;
    /**
     * @author xtrneny1
     * @version etapa 3
     */
    private Set<ResourceCard> resourceCards;
    private List<ActionCard> actionCards;
    private List<Road> roadPieces;
    private List<Village> villagePieces;
    private List<City> cityPieces;

    private LongestRoadCard longestRoadCard;
    private BiggestArmyCard biggestArmyCard;

    private ResourceType buyingBuffer;
    private ResourceType sellingBuffer;

    public Player(String name, Color color, GamingBoard gamingBoard) {
        this.name = name;
        this.color = color;
        this.gamingBoard = gamingBoard;
        this.knightsUsed = 0;
        this.victoryPointCardsUsed = 0;
        playedActionCardThisTurn = false;
        this.roadPieces = new ArrayList<Road>();
        this.resourceCards = new HashSet<>();
        this.actionCards = new ArrayList<ActionCard>();
        this.villagePieces = new ArrayList<Village>();
        this.cityPieces = new ArrayList<City>();
    }

    /**
     * Metoda vrací tvrzení, jestli je porovnávaný objekt rovný této instanci hráče
     *
     * @param obj
     * @return objekt je/není rovný tomuto
     * @author xmusil5
     * @version etapa 3
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Player){
            Player player = (Player) obj;
            if (knightsUsed == player.knightsUsed && maxRoadLength == player.maxRoadLength && name.equals(player.name) &&
                color.equals(player.color) && resourceCards.equals(player.resourceCards) && actionCards.equals(player.actionCards) &&
                roadPieces.equals(player.roadPieces) && villagePieces.equals(player.villagePieces) && cityPieces.equals(player.cityPieces) &&
                longestRoadCard.equals(player.longestRoadCard) && biggestArmyCard.equals(player.biggestArmyCard)){
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda vrací hashovací kód instance
     *
     * @return hashovací kód instance
     * @author xmusil5
     * @version etapa 3
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, color, knightsUsed, maxRoadLength, resourceCards, actionCards, roadPieces, villagePieces, cityPieces, longestRoadCard, biggestArmyCard);
    }

    public void addResourceCard(ResourceCard resourceCard){
        resourceCard.setOwner(this);
        resourceCards.add(resourceCard);
    }

    /**
     * Metoda vrací počet hráčových bodů v závislosti na herních pravidlech a aktuálním hráčově stavu.
     *
     * @return počet bodů hráče
     * @version etapa 5
     */
    public int getScore() {
        int villagePoints = numberOfStartingVillagePieces - villagePieces.size();
        int cityPoints = 2* (numberOfStartingCityPieces - cityPieces.size());
        int biggestArmyPoints = (this.biggestArmyCard == null) ? 0 : 2;
        int longestRoadPoints = (this.longestRoadCard == null) ? 0 : 2;

        return villagePoints + cityPoints + biggestArmyPoints + longestRoadPoints + victoryPointCardsUsed;
    }

    /**
     * V této metodě se definuje, co se má s hráčem stát na konci svého tahu
     *
     * @version etapa 5
     */
    public void onTurnEnded(){
        this.clearTradingBuffers();
        this.playedActionCardThisTurn = false;
    }

    /**
     * Metoda, při které je vyložena akční karta z hráčových akčních karet.
     *
     * @param actionCard - akční karta, která bude vyložena
     * @author xtrneny1
     * @version etapa 2
     */
    public void useActionCard (ActionCard actionCard){
        if (actionCard != null) {
            if (actionCards.size() > 0) {

                    actionCard.proceedAction(this, null);
                    this.actionCards.remove(actionCard);

            } else {
                throw new IllegalStateException("Player " + this.name + " tried to play action card, which is not in his hand");
            }
        } else {
            throw new NullPointerException("Player " + this.name + " tried to play action card with null parameter");
        }
    }

    /**
     * Metoda umožnující potvrdit obchod mezi dvěma hráči
     *
     * @param offering - nabízející hráč
     * @param offer - karty surovin, které hráč nabízí
     * @param demand - karty surovin, které přijímající hráč prodává
     * @author xtrneny1
     * @version etapa 2
     */
    public void acceptTrade (Player offering, Set<ResourceCard> offer, Set<ResourceCard> demand) {
        if (offering != null && offer != null && demand != null) {
            if (this.checkDemand(demand)) {
                offering.resourceCards.addAll(demand);
                this.resourceCards.addAll(offer);
                offering.resourceCards.removeAll(offer);
                this.resourceCards.removeAll(demand);
            } else { Logger.getGlobal().warning("acceptTrade - player doesn't have enough resources to accept the trade"); }
        } else { Logger.getGlobal().warning("acceptTrade - null parameter"); }
    }

    public boolean buyActionCard(){
        boolean result = false;
        if (this.hasResourceCards(ResourceType.getResourceTypesForBuyingActionCard())) {
            result = gamingBoard.getCardDeck().sellActionCard(this);
        }else {
            Logger.getGlobal().warning("buyActionCard - player does not have enough resources");
            CatanLogger.getCatanLogger().addLog("Pro koupi akcni karty nemas dostatek surovin");
        }
        return result;
    }

    /**
     * Metoda kontrolující, zdali má vůbec karty na potvrzení navrženého obchodu
     *
     * @param demand - karty surovin, které hráč nabízí
     * @author xtrneny1
     * @version etapa 3
     */
    public boolean checkDemand (Set<ResourceCard> demand) {
        boolean output = false;
        int counterOfBricks = 0;
        int counterOfWool = 0;
        int counterOfLumber = 0;
        int counterOfOre = 0;
        int counterOfGrain = 0;

        for (var card : demand) {
            if (card.getResourceType() == ResourceType.BRICK) {
                counterOfBricks++;
            }
            if (card.getResourceType() == ResourceType.WOOL) {
                counterOfWool++;
            }
            if (card.getResourceType() == ResourceType.LUMBER) {
                counterOfLumber++;
            }
            if (card.getResourceType() == ResourceType.ORE) {
                counterOfOre++;
            }
            if (card.getResourceType() == ResourceType.GRAIN) {
                counterOfGrain++;
            }
        }

        if (this.getNumberOfResourceCardsByType(ResourceType.BRICK) >= counterOfBricks) {
            if (this.getNumberOfResourceCardsByType(ResourceType.WOOL) >= counterOfWool) {
                if (this.getNumberOfResourceCardsByType(ResourceType.LUMBER) >= counterOfLumber) {
                    if (this.getNumberOfResourceCardsByType(ResourceType.ORE) >= counterOfOre) {
                        if (this.getNumberOfResourceCardsByType(ResourceType.GRAIN) >= counterOfGrain) {
                            output = true;
                        }
                    }
                }
            }
        }


        return output;
    }

    /**
     * Metodou hrac premisti lupice na novy hexagon
     *
     * @param tile hexagon, kam chce hrac lupice premistit
     * @version etapa 5
     */
    public boolean moveBandit(Tile tile){
        boolean result = false;
        if (tile != null){
            if (!tile.getHasBandit()){
                this.gamingBoard.moveBandit(tile);
                result = true;
            } else {
                CatanLogger.getCatanLogger().addLog("Lupice nemuzes premistit na stejne pole");
            }
        }
        return result;
    }

    /**
     * Metoda najde a vrátí všechny hráčovy karty daného typu
     *
     * @param resourceType typ karet, které se hledají
     * @return všechny karty daného typu v hráčově ruce
     *
     * @author xmusil5
     */
    public Set<ResourceCard> getAllResourceCardsOfType(ResourceType resourceType){
        Set<ResourceCard> resourceCards = new HashSet<>();
        for (var rc : this.resourceCards){
            if (rc.getResourceType().equals(resourceType)) {
                resourceCards.add(rc);
            }
        }
        return resourceCards;
    }

    /**
     * Metoda odstraní všechny specifikované karty z hráčovy ruky
     *
     * @param resourceCards karty, které se mají odstranit
     * @author xmusil5
     */
    public void removeResourceCards(Set<ResourceCard> resourceCards){
        if (!resourceCards.isEmpty()){
            this.resourceCards.removeAll(resourceCards);
        }
    }

    public void addResourceCards(Set<ResourceCard> resourceCards){
        if (resourceCards != null){
            for (var c : resourceCards){
                this.addResourceCard(c);
            }
        }
    }

    public void playKnightCard(ActionCard knightCard){
        this.knightsUsed ++;
        this.playedActionCardThisTurn=true;
        this.actionCards.remove(knightCard);
    }

    public void playVictoryPointCard(ActionCard victoryPointCard){
        this.victoryPointCardsUsed++;
        this.playedActionCardThisTurn=true;
        this.actionCards.remove(victoryPointCard);
    }

    public void playRoadBuildingCard(ActionCard roadBuildingCard){
        this.playedActionCardThisTurn=true;
        this.actionCards.remove(roadBuildingCard);
    }

    public void playInventionCard(ActionCard inventionCard){
        this.playedActionCardThisTurn=true;
        this.actionCards.remove(inventionCard);
    }

    public void playMonopolCard(ActionCard monopolCard){
        this.playedActionCardThisTurn=true;
        this.actionCards.remove(monopolCard);
    }



    /**
     * Metoda zjistí, jestli má uživatel karty specifikovaných typů surovin.
     *
     * @param resourceTypes typy surovin, u kterých zjišťujeme, jestli je uživatel má (mohou se opakovat, ošetřeny jsou i počty karet)
     * @return uživatel karty specifikovaných surovin má/nemá
     * @author xmusil5
     * @version etapa 3
     */
    public boolean hasResourceCards(List<ResourceType> resourceTypes){
        boolean allResourceCardsPresent = true;
        List<ResourceCard> temp = new ArrayList<>();
        for (int i = 0; i<resourceTypes.size(); i++){
            boolean found = false;
            for(var card : this.resourceCards){
                if (card.getResourceType() == resourceTypes.get(i) && !temp.contains(card)){
                    found = true;
                    temp.add(card);
                    break;
                }
            }
            if (!found){
                allResourceCardsPresent = false;
                break;
            }
        }
        return allResourceCardsPresent;
    }

    /**
     * Metoda odebere uživateli karty surovin specifikovaných typů (pokud je má)
     *
     * @param resourceTypes typy surovin, které se mají uživateli odebrat
     * @return seznam karet, které byly odebrány
     * @author xmusil5
     * @version etapa 3
     */
    public List<ResourceCard> surrenderResourceCardsByTypes(List<ResourceType> resourceTypes){
        List<ResourceCard> cards = new ArrayList<>();
        if (hasResourceCards(resourceTypes)){
            for (var resourceType : resourceTypes){
                ResourceCard card = getResourceCardByType(resourceType);
                if (card != null) {
                    card.setOwner(null);
                    cards.add(card);
                    this.resourceCards.remove(card);
                }
            }
        }
        if (!cards.isEmpty()){
            return cards;
        }else{
            return null;
        }
    }

    /**
     * Metoda vrací kartu suroviny se specifikovaným typem z hráčových karet, pokud ji má
     *
     * @param resourceType typ suroviny, který má vracená karta mít
     * @return karta se specifikovaným typem suroviny
     * @author xmusil5
     * @version etapa 3
     */
    public ResourceCard getResourceCardByType(ResourceType resourceType){
        ResourceCard resourceCard = null;
        for (var card : this.resourceCards){
            if (card.getResourceType() == resourceType){
                resourceCard = card;
                break;
            }
        }
        return resourceCard;
    }

    /**
     * Metoda se pokusí ukrást specifikovanému hráči dané množství náhodných karet surovin
     *
     * @param victim hráč, kterému se karty kradou
     * @param numberOfResourceCards počet karet, které se mají ukrást
     * @return kraty byly/nebyly úspěšně ukradeny
     * @version etapa 5
     */
    public boolean stealResourceCards(Player victim, int numberOfResourceCards){
        boolean result = false;
        if (!victim.equals(this)){
            List<ResourceCard> newCards = victim.surrenderRandomResourceCards(numberOfResourceCards);
            if (newCards != null){
                for (var c : newCards){
                    this.addResourceCard(c);
                }
                result = true;
            }
        } else { CatanLogger.getCatanLogger().addLog("Karty muzes krast jen jinym hracum"); }
        return result;
    }

    /**
     * Metoda vrátí specifikovaný počet hráčových karet surovin (s náhodným typem) pokud je má. Tyto karty jsou hráči odebrány
     *
     * @param numberOfCards počet karet, které má hráč obětovat
     * @return seznam obětovaných karet
     * @version etapa 5
     */
    public List<ResourceCard> surrenderRandomResourceCards(int numberOfCards){
        List<ResourceCard> surrenderedResourceCards = null;
        if (numberOfCards <= this.resourceCards.size()) {
            surrenderedResourceCards = new ArrayList<>();
            for (int i = 0; i<numberOfCards; i++){
                ResourceCard resourceCard = getRandomResourceCard();
                if (resourceCard != null){
                    resourceCard.setOwner(null);
                    surrenderedResourceCards.add(resourceCard);
                    this.resourceCards.remove(resourceCard);
                }
            }
        } else{
            Logger.getGlobal().warning("surrenderRandomResourceCards - player can not surrender more resource cards than he has");
            CatanLogger.getCatanLogger().addLog("Hrac " + this.name + " nemuze odevzdat vice karet surovin, nez ma v ruce");
        }
        return surrenderedResourceCards;
    }

    public ResourceCard getRandomResourceCard(){
        if (this.resourceCards.size() > 0) {
            Random random = new Random();
            int cardIndex = random.nextInt(this.resourceCards.size());
            ResourceCard[] cards = this.resourceCards.toArray(new ResourceCard[resourceCards.size()]);
            return cards[cardIndex];
        } else {
            return null;
        }
    }

    public int getNumberOfCityPieces(){
        return this.cityPieces.size();
    }

    public int getNumberOfActionCards(){
        return this.actionCards.size();
    }

    public void addActionCard(ActionCard actionCard) {
        if (actionCard != null) {
            this.actionCards.add(actionCard);
        }
    }

    /**
     * Metoda vezme hráči figurku města
     *
     * @return figurka města, pokud hráč ještě nějaké má
     * @author xmusil5
     * @version etapa 3
     */
    public City getCityPiece(){
        City city = null;
        if (!this.cityPieces.isEmpty()){
            city = this.cityPieces.get(0);
        }
        return city;
    }

    /**
     * Metoda odebere hráči figurku vesnice
     *
     * @return figurka vesnice, která má být odebrána
     * @author xtrneny1
     * @version etapa 3
     */
    public Village getVillagePiece(){
        Village village = null;
        if (!this.villagePieces.isEmpty()){
            village = this.villagePieces.get(0);
        }
        return village;
    }

    public boolean hasPlayedActionCardThisTurn(){
        return this.playedActionCardThisTurn;
    }

    public void removeVillagePiece(Village village){
        if (village != null && this.villagePieces.contains(village)){
            this.villagePieces.remove(village);
        }
    }

    public void addVillagePiece(Village villagePiece) {
        if (villagePiece != null) {
            villagePiece.setOwner(this);
            villagePieces.add(villagePiece);
        }
    }

    public void addCityPiece(City cityPiece) {
        if (cityPiece != null) {
            this.cityPieces.add(cityPiece);
        }
    }

    public int getNumberOfResourceCards(){
        return this.resourceCards.size();
    }

    public int getNumberOfResourceCardsByType(ResourceType resourceType){
        int counter = 0;
        for (var resourceCard : this.resourceCards) {
            if (resourceCard.getResourceType() == resourceType) {
                counter++;
            }
        }

        return counter;
    }

    public int getNumberOfActionCardsByType(ActionCard actionCardType){
        int counter = 0;
        for (var actionCard : this.actionCards) {
            if (actionCard instanceof Invention) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Metoda vrací počet akčních kartet z ruky hráče podle typu.
     *
     * @param classes druh akční karty
     * @return počet akčních karet daného typu
     * @author xtrneny1
     */
    public int countActionCardsByClass (Class classes) {
        int counter = 0;
        for (var card : this.actionCards){
            if (card.getClass() == classes){
                counter++;
            }
        }

        return counter;
    }

    public List<ActionCard> getActionCardListByClass (Class classes) {
        List<ActionCard> actionCardsOutput = new ArrayList<>();
        for (var card : this.actionCards) {
            if (card.getClass() == classes) {
                actionCardsOutput.add(card);
            }
        }
        return actionCardsOutput;
    }
    /**
     * Metoda promaže dočasnou úschovnu pro typy karet k obchodu.
     *
     * @version etapa 5
     */
    public void clearTradingBuffers(){
        this.buyingBuffer = null;
        this.sellingBuffer = null;
    }

    public void removeCityPiece(City city){
        if (city!= null && this.cityPieces.contains(city)){
            this.cityPieces.remove(city);
        }
    }

    public Road getRoadPiece(){
        Road road= null;
        if (!this.roadPieces.isEmpty()){
            road = this.roadPieces.get(0);
        }
        return road;
    }

    public void removeRoadPiece(Road road){
        if (road != null && this.roadPieces.contains(road)){
            this.roadPieces.remove(road);
        }
    }

    public void removeActionCard(ActionCard card){
        if (card != null && this.actionCards.contains(card)){
            this.actionCards.remove(card);
        }
    }

    public String getTextNumberOfKnights () { return Integer.toString(knightsUsed); }

    public void tradeWithAnotherPlayer (Player player) { throw new UnsupportedOperationException(); }

    public int getKnightsUsed () { return this.knightsUsed; }

    // FIXME testovaci metody
    public void setKnightsUsed (int number) { knightsUsed = number; }

    public List<ActionCard> getActionCards () {
        List<ActionCard> actionCards = this.actionCards;
        return actionCards;
    }

    public String getName() {
        return name;
    }

    public String getTextScore() {
        return String.valueOf(getScore()) + " / 10";
    }

    public Color getColor() {
        return color;
    }

    public List<Village> getVillagePieces() {
        return villagePieces;
    }

    public void setVillagePieces(List<Village> villagePieces) {
        if (villagePieces != null){
            this.numberOfStartingVillagePieces = villagePieces.size();
            this.villagePieces = villagePieces;
        }
    }

    public List<City> getCityPieces() {
        return cityPieces;
    }

    public void setCityPieces(List<City> cityPieces) {
        if (cityPieces != null){
            this.numberOfStartingCityPieces = cityPieces.size();
            this.cityPieces = cityPieces;
        }
    }

    public List<Road> getRoadPieces() {
        return roadPieces;
    }

    public void setRoadPieces(List<Road> roadPieces) {
        if (roadPieces != null) {
            this.roadPieces = roadPieces;
        }
    }

    public int getNumberOfVillagePieces(){
        return this.villagePieces.size();
    }

    public int getNumberOfRoadPieces(){
        return this.roadPieces.size();
    }


    public ResourceType getBuyingBuffer() {
        return buyingBuffer;
    }

    public void setBuyingBuffer(ResourceType buyingBuffer) {
        this.buyingBuffer = buyingBuffer;
    }

    public ResourceType getSellingBuffer() {
        return sellingBuffer;
    }

    public void setSellingBuffer(ResourceType sellingBuffer) {
        this.sellingBuffer = sellingBuffer;
    }

    public LongestRoadCard getLongestRoadCard() {
        return longestRoadCard;
    }

    public void setLongestRoadCard(LongestRoadCard longestRoadCard) {
        if (longestRoadCard != null) longestRoadCard.setOwner(this);
        this.longestRoadCard = longestRoadCard;
    }

    public BiggestArmyCard getBiggestArmyCard() {
        return biggestArmyCard;
    }

    public void setBiggestArmyCard(BiggestArmyCard biggestArmyCard) {
        if (biggestArmyCard != null) biggestArmyCard.setOwner(this);
        this.biggestArmyCard = biggestArmyCard;
    }
}

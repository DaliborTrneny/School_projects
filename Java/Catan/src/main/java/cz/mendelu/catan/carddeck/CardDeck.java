package cz.mendelu.catan.carddeck;

import cz.mendelu.catan.cards.BiggestArmyCard;
import cz.mendelu.catan.cards.LongestRoadCard;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class CardDeck implements Serializable {
    private static final int BANK_TRADE_RATE = 4;
    private static final int BIGGEST_ARMY_LIMIT = 3;
    private static final int LONGEST_ROAD_LIMIT = 5;

    private List<ResourceCard> resourceCards;
    private List<ActionCard> actionCards;

    private LongestRoadCard longestRoadCard;
    private BiggestArmyCard biggestArmyCard;

    public CardDeck(){
        this.resourceCards = new ArrayList<>();
        this.actionCards = new ArrayList<>();
    }

    public CardDeck(List<ResourceCard> resourceCards, List<ActionCard> actionCards, BiggestArmyCard biggestArmyCard, LongestRoadCard longestRoadCard){
        this.resourceCards = resourceCards;
        this.actionCards = actionCards;
        this.biggestArmyCard = biggestArmyCard;
        this.longestRoadCard = longestRoadCard;
    }

    /**
     * Metoda přidá specifikované karty do balíčku karet
     *
     * @param cards karty, které mají být přidány
     * @author xmusil5
     */
    public void addResourceCards(List<ResourceCard> cards){
        if (cards != null && !cards.isEmpty()){
            for (var card : cards){
                if (!this.resourceCards.contains(card)) {
                    card.setOwner(null);
                    this.resourceCards.add(card);
                }
            }
        }
    }

    /**
     * Metoda pro darování karty daného typu hráči
     * Hráči je z banku karet surovin darována karta specifikované suroviny
     *
     * @param player - hráč, kterému je karta darována
     * @param resourceType - typ suroviny, která se má darovat
     * @author xmusil5
     * @version etapa 3
     */
    public void giveResourceCardsToPlayerByType (int numberOfCards, Player player, ResourceType resourceType) {
        for (int i = 0; i<numberOfCards; i++){
            ResourceCard resourceCard = getResourceCardByType(resourceType);
            if (resourceCard != null) {
                player.addResourceCard(resourceCard);
                this.removeResourceCard(resourceCard);
            }
        }
    }

    /**
     * Metoda pro nákup akční karty
     *
     * @param player - hráč, který hodlá koupit akční kartu
     * @return projej proběhl/neproběhl v pořádku
     * @author xtrneny1
     * @version etapa 2
     */
    public boolean sellActionCard(Player player) {
        boolean result = false;
        if (player != null && actionCards != null) {

            if (actionCards.size() > 0) {
                ActionCard ac = this.chooseRandomActionCard();
                if (ac != null) {
                    this.addResourceCards(player.surrenderResourceCardsByTypes(ResourceType.getResourceTypesForBuyingActionCard()));
                    ac.setOwner(player);
                    player.addActionCard(ac);
                    this.actionCards.remove(ac);
                    result = true;
                }
            }

        } else { Logger.getGlobal().warning("sellActionCard - null parameter"); }
        return result;
    }

    public int availableActionCards () {return this.actionCards.size();}

    public void addActionCardToList (ActionCard actionCard) {
        if (actionCard != null) {
            if (!actionCards.contains(actionCard)) {
                this.actionCards.add(actionCard);
            }else { Logger.getGlobal().warning("addActionCardToList - Card deck already contains this card"); }
        }else { Logger.getGlobal().warning("addActionCardToList - null parameter"); }
    }

    /**
     * Metoda přiřadí kartu největší armády hráči ve specifikovaném seznamu, který má aktuálně vyloženo nejvíce rytířů.
     * Přiřazení proběhne pouze pokud bylo překročeno minimum pro získání karty.
     *
     * @param gamePlayers - hráči, u kterých se karta největší armády přiřazuje
     * @version etapa 5
     */
    public void assignBiggestArmyCardToRightPlayer(List<Player> gamePlayers){
        //zjisti se maximalni pocet vylozenych ryriru mezi hraci
        int mostKnights = getKnightsUsedMax(gamePlayers);
        if (mostKnights >= BIGGEST_ARMY_LIMIT) {
            List<Player> playersWithMostKnights = new ArrayList<>();
            //zjisti se, jestli je vice hracu s timto maximem
            for (var player : gamePlayers) {
                if (player.getKnightsUsed() == mostKnights) {
                    playersWithMostKnights.add(player);
                }
            }
            //pokud ano, neresi se dale nic (nekdo dohnal hrace, ktery kartu nejvetsi armady prave ma, ta mu ale zustava)
            if (playersWithMostKnights.size() == 1) {
                //pokud je hrac s maximem prave jeden, musi se mu priradit karta nejvetsi armady
                Player playerWithBiggestArmy = playersWithMostKnights.get(0);
                //pokud hrac kartu nejvetsi armady uz ma, dale se neresi, jinak se provede nasledujici kod
                if (playerWithBiggestArmy.getBiggestArmyCard() == null){
                    if (this.biggestArmyCard != null){
                        //pokud karta se karta nejvetsi armady nachazi v banku (jeste nebyla nikdy pridelena), priradi se hraci
                        playerWithBiggestArmy.setBiggestArmyCard(this.biggestArmyCard);
                        this.biggestArmyCard = null;
                    } else{
                        //pokud mel kartu nejvetsi armady predtim jiny hrac, vezme se mu a priradi novemu hraci s nejvice vylozenymi rytiri
                        BiggestArmyCard biggestArmyCard = retrieveBiggestArmyCard(gamePlayers);
                        playerWithBiggestArmy.setBiggestArmyCard(biggestArmyCard);
                    }
                }
            }
        }
    }

    /**
     * Metoda přiřadí kartu největší armády hráči ve specifikovaném seznamu, který má aktuálně vyloženo nejvíce rytířů.
     * Přiřazení proběhne pouze pokud bylo překročeno minimum pro získání karty.
     *
     * @param gamePlayers - hráči, u kterých se karta největší armády přiřazuje
     * @version etapa 5
     */
    public void assignLongestRoadCardToRightPlayer(List<Player> gamePlayers, GamingBoard gamingBoard){
        //zjisti se nejdelsi cesta hracu
        int longestRoad =0;
        List<Player> playersWithLongestRoad = new ArrayList<>();
        for (var player : gamePlayers){
            int thisLongestRoad = gamingBoard.getLongestRoad(player);
            if (thisLongestRoad > longestRoad){
                playersWithLongestRoad.clear();
                playersWithLongestRoad.add(player);
                longestRoad = thisLongestRoad;
            } else if (thisLongestRoad == longestRoad){
                playersWithLongestRoad.add(player);
            }
        }
        if (longestRoad >= LONGEST_ROAD_LIMIT) {
            //pokud je hracu s nejdelsi cestou vice, neresi se dale nic (nekdo dohnal hrace, ktery kartu nejdelsi cesty prave ma, ta mu ale zustava)
            if (playersWithLongestRoad.size() == 1) {
                //pokud je hrac s maximem prave jeden, musi se mu priradit karta nejdelsi cesty
                Player playerWithLongestRoad = playersWithLongestRoad.get(0);
                //pokud hrac kartu nejdelsi cesty uz ma, dale se neresi, jinak se provede nasledujici kod
                if (playerWithLongestRoad.getLongestRoadCard() == null){
                    if (this.longestRoadCard != null){
                        //pokud se karta nejdelsi cesty nachazi v banku (jeste nebyla nikdy pridelena), priradi se hraci
                        playerWithLongestRoad.setLongestRoadCard(this.longestRoadCard);
                        this.longestRoadCard = null;
                    } else{
                        //pokud mel kartu nejdelsi cesty predtim jiny hrac, vezme se mu a priradi novemu hraci s nejvice vylozenymi rytiri
                        LongestRoadCard longestRoadCard = retrieveLongestRoadCard(gamePlayers);
                        playerWithLongestRoad.setLongestRoadCard(longestRoadCard);
                    }
                }
            }
        }
        //pokud hracovi nekdo prestrihnul cestu a on tak ztratil kartu nejdelsi cesty
        else if (this.longestRoadCard == null){
            this.longestRoadCard = retrieveLongestRoadCard(gamePlayers);
            this.longestRoadCard.setOwner(null);
        }
    }

    private int getKnightsUsedMax(List<Player> players){
        int mostKnights = 0;
        for (var player : players){
            if (player.getKnightsUsed() > mostKnights){
                mostKnights = player.getKnightsUsed();
            }
        }
        return mostKnights;
    }

    private BiggestArmyCard retrieveBiggestArmyCard(List<Player> players){
        BiggestArmyCard biggestArmyCard = null;
        for (var player : players){
            if (player.getBiggestArmyCard() != null){
                biggestArmyCard = player.getBiggestArmyCard();
                player.setBiggestArmyCard(null);
                break;
            }
        }
        return biggestArmyCard;
    }

    private LongestRoadCard retrieveLongestRoadCard(List<Player> players){
        LongestRoadCard longestRoadCard = null;
        for (var player : players){
            if (player.getLongestRoadCard() != null){
                longestRoadCard = player.getLongestRoadCard();
                player.setLongestRoadCard(null);
                break;
            }
        }
        return longestRoadCard;
    }

    /**
     * Metoda pro volbu náhodné akční karty z herního balíčku
     *
     * @author xtrneny1
     * @version etapa 2
     */
    public ActionCard chooseRandomActionCard (){
        if (actionCards != null) {
            if (actionCards.size() > 0) {
                Random random = new Random();
                int randomNumber = random.nextInt(actionCards.size());

                return actionCards.get(randomNumber);
            } else {
                Logger.getGlobal().warning("chooseRandomActionCard - not enough cards in ActionCard deck");
                CatanLogger.getCatanLogger().addLog("V banku jiz nejsou zadne dalsi akcni karty");
            }
        } else {
            throw new IllegalStateException("Action card list can not be null during the game");
        }

        return null;
    }

    /**
     * Metoda provede směnu karet s hráčem za základní kurz banku.
     *
     * @param player hráč, který chce s bankem obchodovat.
     * @param requiredType typ suroviny, kterou chce z banku hráč koupit.
     * @param payment typ suroviny, kterou hráč nabízí jako platbu.
     *
     * @version etapa 5
     */
    public boolean bankTradeWithPlayer(Player player, ResourceType requiredType, ResourceType payment){
        if (requiredType != payment) {
            if (getResourceCardByType(requiredType) != null) {
                List<ResourceType> types = new ArrayList<>();
                for (int i = 0; i < BANK_TRADE_RATE; i++) {
                    types.add(payment);
                }

                if (player.hasResourceCards(types)) {
                    List<ResourceCard> surrenderedCards = player.surrenderResourceCardsByTypes(types);
                    this.addResourceCards(surrenderedCards);
                    this.giveResourceCardsToPlayerByType(1, player, requiredType);
                    player.clearTradingBuffers();
                    return true;
                } else {
                    Logger.getGlobal().warning("CardDeck=>bankTradeWithPlayer - Player doesn't have enough cards to trade");
                    CatanLogger.getCatanLogger().addLog("Nemas dostatek surovin pro obchod s bankem");
                }

            } else {
                Logger.getGlobal().warning("CardDeck=>bankTradeWithPlayer - Bank has no more cards of this type");
                CatanLogger.getCatanLogger().addLog("V banku jiz dosly karty tohoto typu");
            }
        } else {
            Logger.getGlobal().warning("CardDeck=>bankTradeWithPlayer - Can not trade cards of the same type");
            CatanLogger.getCatanLogger().addLog("S bankem muzes smenovat pouze suroviny ruzneho typu");
        }
        return false;
    }

    /**
     * Metoda vrátí první kartu specifikované suroviny z balíčku karet surovin
     *
     * @param resourceType typ karty suroviny, která má být vrácena
     * @return karta specifikované suroviny (pokud v balíčku nějaká zůstává)
     *
     * @version etapa 5
     */
    public ResourceCard getResourceCardByType(ResourceType resourceType){
        for (var resourceCard : this.resourceCards){
            if (resourceCard.getResourceType().equals(resourceType)){
                return resourceCard;
            }
        }
        if (!resourceType.equals(ResourceType.EMPTY)) {
            CatanLogger.getCatanLogger().addLog("V banku jiz dosly karty suroviny: " + ResourceType.getResourceTypeString(resourceType));
        }
        return null;
    }

    /**
     * Metoda odstraní specifikovanou kartu suroviny z balíčku
     *
     * @param resourceCard karta, která má být odstraněna
     * @return karta byla/nebyla odstraněna
     *
     * @version etapa 5
     */
    public boolean removeResourceCard(ResourceCard resourceCard){
        if (this.resourceCards.contains(resourceCard)){
            this.resourceCards.remove(resourceCard);
            return true;
        } else{
            return false;
        }
    }

    public List<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    public List<ActionCard> getActionCards() {
        return actionCards;
    }

    public LongestRoadCard getLongestRoadCard() {
        return longestRoadCard;
    }

    public void setLongestRoadCard(LongestRoadCard longestRoadCard) {
        this.longestRoadCard = longestRoadCard;
    }

    public BiggestArmyCard getBiggestArmyCard() {
        return biggestArmyCard;
    }

    public void setBiggestArmyCard(BiggestArmyCard biggestArmyCard) {
        this.biggestArmyCard = biggestArmyCard;
    }
}

package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.Settlement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PortCrossroad extends Crossroad{
    //number of resource cards player has to pay for the trade
    protected int requiredNumberOfCards;
    //types of resourceCards this port offers to sell
    protected List<ResourceType> offeringTypes;
    //types of resourceCards this port accepts as payment
    protected List<ResourceType> acceptedTypes;

    public PortCrossroad(int coordinate, Settlement playerSettlement, GamingBoard gamingBoard, int requiredNumberOfCards, List<ResourceType> offeringTypes, List<ResourceType> acceptedTypes) {
        super(coordinate, playerSettlement, gamingBoard);
        if (requiredNumberOfCards >= 1) {
            if (offeringTypes.size()>0 && acceptedTypes.size()>0) {
                this.requiredNumberOfCards = requiredNumberOfCards;
                this.offeringTypes = offeringTypes;
                this.acceptedTypes = acceptedTypes;
            }
            else {
                throw new IllegalArgumentException("PortCroosroad must have at least one offering resource type and one accepting resource type");
            }
        } else {
            throw new IllegalArgumentException("PortCrossroad must require possitive number of cards");
        }
    }

    /**
     * Metoda provede směnu karet surovin s hráčem dle kurzu a nabídky tohoto přístavu.
     *
     * @param player hráč, který chce s přístavem obchodovat
     * @param requiredType typ suroviny, kterou chce z přístavu hráč koupit
     * @param payment typ suroviny, který hráč nabízí
     *
     */
    public boolean tradeWithPlayer(Player player, ResourceType requiredType, ResourceType payment){
        if (this.playerSettlement != null) {
            if (player.equals(this.playerSettlement.getOwner())) {
                if (this.offeringTypes.contains(requiredType)){
                    if (this.acceptedTypes.contains(payment)){
                        if (requiredType != payment) {

                            List<ResourceType> types = new ArrayList<>();
                            for (int i = 0; i < this.requiredNumberOfCards; i++) {
                                types.add(payment);
                            }
                            if (player.hasResourceCards(types)) {
                                List<ResourceCard> surrenderedCards = player.surrenderResourceCardsByTypes(types);
                                this.getCardDeck().addResourceCards(surrenderedCards);
                                this.getCardDeck().giveResourceCardsToPlayerByType(1, player, requiredType);
                                player.clearTradingBuffers();
                                return true;
                            } else {
                                Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Player doesn't have enough cards to trade");
                                CatanLogger.getCatanLogger().addLog("Pro tento obchod nemas dostatek surovin");
                            }

                        }else {
                            Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Can not trade cards of the same type");
                            CatanLogger.getCatanLogger().addLog("Muzes obchodovat jen karty ruznych typu");
                        }
                    } else {
                        Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Player wanted to pay with cards this port doesn't accept");
                        CatanLogger.getCatanLogger().addLog("Pristav neprijma tuto surovinu jako platidlo");
                    }
                } else {
                    Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Port doesn't offer required type");
                    CatanLogger.getCatanLogger().addLog("Pristav tuto surovinu nenabizi");
                }
            } else {
                Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Player wanting to trade doesn't own this port's sellement");
                CatanLogger.getCatanLogger().addLog("Pro obchod musis vlastnit vesnici na tomto pristavu");
            }
        } else {
            Logger.getGlobal().warning("PortCrossroad=>tradeWithPlayer - Port has no settlement");
            CatanLogger.getCatanLogger().addLog("Pro obchod musis na pristavu postavit vesnici");
        }
        return false;
    }

    public int getRequiredNumberOfCards() {
        return requiredNumberOfCards;
    }

    public void setRequiredNumberOfCards(int requiredNumberOfCards) {
        this.requiredNumberOfCards = requiredNumberOfCards;
    }

    public List<ResourceType> getOfferingTypes() {
        return offeringTypes;
    }

    public void setOfferingTypes(List<ResourceType> offeringTypes) {
        this.offeringTypes = offeringTypes;
    }

    public List<ResourceType> getAcceptedTypes() {
        return acceptedTypes;
    }

    public void setAcceptedTypes(List<ResourceType> acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }
}

package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.containers.MonopolContainer;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;

import java.util.List;
import java.util.Set;

public class Monopol extends DevelopmentCard{

    public Monopol(Player owner) {
        super(owner);
    }

    /**
     * Metoda obere všechny hráče (kromě hráče, který tuto kartu zahrál) o karty surovin daného typu a dá je hráčovi jenž zahrál tuto kartu
     *
     * @param playerPlayingThisCard hráč, který kartu zahrál
     * @param object dodatečný kontejner s objekty
     * @author xmusil5
     */
    @Override
    public void proceedAction(Player playerPlayingThisCard, Object object) {
        if (object instanceof MonopolContainer){
            MonopolContainer container = (MonopolContainer) object;
            ResourceType resourceType = container.getResourceType();
            List<Player> players = container.getPlayers();
            for (var p : players){
                if (!p.equals(playerPlayingThisCard)){
                    Set<ResourceCard> newCards= p.getAllResourceCardsOfType(resourceType);
                    if (!newCards.isEmpty()){
                        p.removeResourceCards(newCards);
                        playerPlayingThisCard.addResourceCards(newCards);
                    }
                }
            }
            playerPlayingThisCard.playMonopolCard(this);
            CatanLogger.getCatanLogger().addLog(playerPlayingThisCard.getName() + " zahral kartu monopol a okradl vsechny o karty typu "
                    + ResourceType.getResourceTypeString(resourceType));
        } else {
            throw new IllegalArgumentException("When calling proceed action in Monopol, second parameter must be of type MonopolContainer");
        }
    }
}

package cz.mendelu.catan.cards.actioncards.containers;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;

import java.util.List;

public class MonopolContainer {
    private ResourceType resourceType;
    private List<Player> players;

    public MonopolContainer(ResourceType resourceType, List<Player> players){
        this.resourceType = resourceType;
        this.players = players;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public List<Player> getPlayers() {
        return players;
    }
}

package cz.mendelu.catan.cards;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;

public class ResourceCard extends Card {
    private ResourceType resourceType;

    public ResourceCard(Player owner, ResourceType resourceType) {
        super(owner);
        this.resourceType = resourceType;
    }


    public ResourceType getResourceType() {
        return resourceType;
    }
}

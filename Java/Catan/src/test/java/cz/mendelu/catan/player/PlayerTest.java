package cz.mendelu.catan.player;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.VictoryPoint;
import cz.mendelu.catan.game.ResourceType;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void useActionCard() {
        // setup
        Player player = new Player("user", Color.green);
        ActionCard addPoint = new VictoryPoint(player, "AddPoint");
        player.addActionCard(addPoint);

        // when
        player.useActionCard(addPoint);

        // then
        assertEquals(0, player.getNumberOfActionCards());
        assertEquals(1, player.getScore());
    }

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void acceptTrade() {
        // setup
        Player playerOffering = new Player("Offer", Color.green);
        Player playerAccepting = new Player("Accept", Color.red);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.LUMBER);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.ORE);
        ResourceCard resourceCard3 = new ResourceCard(ResourceType.BRICK);
        ResourceCard resourceCard4 = new ResourceCard(ResourceType.BRICK);

        playerOffering.addResourceCard(resourceCard1);
        playerOffering.addResourceCard(resourceCard2);
        playerAccepting.addResourceCard(resourceCard3);
        playerAccepting.addResourceCard(resourceCard4);
        Set<ResourceCard> offer = new HashSet<>();
        offer.add(resourceCard1);
        offer.add(resourceCard2);

        Set <ResourceCard> demand = new HashSet<>();
        demand.add(resourceCard3);
        demand.add(resourceCard4);

        // when
        playerAccepting.acceptTrade(playerOffering, offer, demand);

        // then
        assertEquals(2, playerOffering.getNumberOfResourceCardsByType(ResourceType.BRICK));
        assertEquals(1, playerAccepting.getNumberOfResourceCardsByType(ResourceType.LUMBER));
        assertEquals(1, playerAccepting.getNumberOfResourceCardsByType(ResourceType.ORE));
    }
}
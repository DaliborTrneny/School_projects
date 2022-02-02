package cz.mendelu.catan.game;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.VictoryPoint;
import cz.mendelu.catan.player.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void buyActionCard() {
        // setup
        Player player = new Player("buyer", Color.green);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.WOOL);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.ORE);
        ResourceCard resourceCard3 = new ResourceCard(ResourceType.GRAIN);
        Game game = new Game();
        ActionCard addPoint = new VictoryPoint(null, "AddPoint");
        game.getCardDeck().addActionCardToList(addPoint);
        player.addResourceCard(resourceCard1);
        player.addResourceCard(resourceCard2);
        player.addResourceCard(resourceCard3);

        // when
        game.getCardDeck().sellActionCard(player);

        //then
        assertEquals(1, player.getNumberOfActionCards());
        assertEquals(0, game.getCardDeck().availableActionCards());
        assertEquals(player, addPoint.getOwner());
        assertEquals(0, game.getCardDeck().availableActionCards());
        assertEquals(0, player.getNumberOfResourceCards());
    }

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void buyActionCard_notEnoughResources() {
        // setup
        Player player = new Player("buyer", Color.green);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.WOOL);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.ORE);
        Game game = new Game();
        ActionCard addPoint = new VictoryPoint(null, "AddPoint");
        game.getCardDeck().addActionCardToList(addPoint);
        player.addResourceCard(resourceCard1);
        player.addResourceCard(resourceCard2);

        // when
        game.getCardDeck().sellActionCard(player);

        //then
        assertEquals(0, player.getNumberOfActionCards());
        assertEquals(1, game.getCardDeck().availableActionCards());
        assertEquals(2, player.getNumberOfResourceCards());
    }
}
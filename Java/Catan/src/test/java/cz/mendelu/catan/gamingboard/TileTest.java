package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Village;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void giveYield() {
        //setup
        Player player1 = new Player("player1", Color.red);
        Player player2 = new Player("player2", Color.blue);
        City city = new City(player1);
        Village village = new Village(player2);
        List<ResourceCard> resourceCards = new ArrayList<ResourceCard>();
        for (int i = 0; i<3; i++){
            resourceCards.add(new ResourceCard(ResourceType.LUMBER));
        }
        GamingBoard gamingBoard = new GamingBoard(null, null, null, null);
        gamingBoard.setCardDeck(new CardDeck(resourceCards, null));
        Map<TileTipDirection, Crossroad> crossroads = new HashMap<>();
        crossroads.put(TileTipDirection.NORTH, (new Crossroad(1, village, false)));
        crossroads.put(TileTipDirection.NORTH_EAST,(new Crossroad(2, null, true)) );
        crossroads.put(TileTipDirection.SOUTH_EAST,(new Crossroad(3, null, true)));
        crossroads.put(TileTipDirection.SOUTH,(new Crossroad(4, null, true)));
        crossroads.put(TileTipDirection.SOUTH_WEST,(new Crossroad(5, city, false)));
        crossroads.put(TileTipDirection.NORTH_WEST,(new Crossroad(6, null, true)));
        Tile tile = new Tile(15, 6, gamingBoard, crossroads, false, ResourceType.LUMBER);
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        gamingBoard.setTiles(tiles);
        //when
        tile.giveYield();
        //then
        assertEquals(2, player1.getNumberOfResourceCards());
        assertEquals(1, player2.getNumberOfResourceCards());
        assertEquals(0, resourceCards.size());
    }

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void giveYield_onTileWithBandit() {
        //setup
        Player player = new Player("player", Color.RED);
        Village village = new Village(player);
        List<ResourceCard> resourceCards = new ArrayList<ResourceCard>();
        resourceCards.add(new ResourceCard(ResourceType.BRICK));
        GamingBoard gamingBoard = new GamingBoard(null, null, null, null);
        gamingBoard.setCardDeck(new CardDeck(resourceCards, null));
        Map<TileTipDirection, Crossroad> crossroads = new HashMap<>();
        crossroads.put(TileTipDirection.NORTH,(new Crossroad(1, null, true)));
        crossroads.put(TileTipDirection.NORTH_EAST,(new Crossroad(2, null, true)));
        crossroads.put(TileTipDirection.SOUTH_EAST,(new Crossroad(3, village, false)));
        crossroads.put(TileTipDirection.SOUTH,(new Crossroad(4, null, true)));
        crossroads.put(TileTipDirection.SOUTH_WEST,(new Crossroad(5, null, true)));
        crossroads.put(TileTipDirection.NORTH_WEST,(new Crossroad(6, null, true)));
        Tile tile = new Tile(1, 2, gamingBoard, crossroads, true, ResourceType.BRICK);
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        gamingBoard.setTiles(tiles);
        //when
        tile.giveYield();
        //then
        assertEquals(0, player.getNumberOfResourceCards());
        assertEquals(1, resourceCards.size());
    }
}
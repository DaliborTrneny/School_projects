package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Village;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GamingBoardTest {

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void moveBandit() {
        //setup
        GamingBoard gamingBoard = new GamingBoard();
        Tile tile1 = new Tile(1, 2, gamingBoard, null, true, ResourceType.BRICK);
        Tile tile2 = new Tile(13, 8, gamingBoard,null, false, ResourceType.WOOL);
        Tile tile3 = new Tile(9, 11, gamingBoard,null, false, ResourceType.ORE);
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        gamingBoard.setTiles(tiles);
        //when
        gamingBoard.moveBandit(tile2);
        //then
        assertFalse(tile1.getHasBandit());
        assertTrue(tile2.getHasBandit());
        assertFalse(tile3.getHasBandit());
    }

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void upgradeVillageToCity() {
        //setup
        Player player = new Player("player1", Color.red);
        ResourceCard resource1 = new ResourceCard(ResourceType.GRAIN);
        ResourceCard resource2 = new ResourceCard(ResourceType.GRAIN);
        ResourceCard resource3 = new ResourceCard(ResourceType.ORE);
        ResourceCard resource4 = new ResourceCard(ResourceType.ORE);
        ResourceCard resource5 = new ResourceCard(ResourceType.ORE);
        player.addResourceCard(resource1);
        player.addResourceCard(resource2);
        player.addResourceCard(resource3);
        player.addResourceCard(resource4);
        player.addResourceCard(resource5);

        Village village = new Village(player);
        City city = new City(player);
        player.addCityPiece(city);
        Crossroad crossroad = new Crossroad(12, village, false);
        GamingBoard gamingBoard = new GamingBoard();
        Set<Crossroad> crossroads = new HashSet<>();
        crossroads.add(crossroad);
        gamingBoard.setCrossroads(crossroads);
        //when
        gamingBoard.upgradeVillageToCity(player, crossroad);
        //then
        assertEquals(0, player.getNumberOfCityPieces());
        assertEquals(1, player.getVillagePieces().size());
        assertEquals(0, player.getNumberOfResourceCards());
        assertSame(city, crossroad.getPlayerSettlement());
    }

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void upgradeVillageToCity_notEnoughResources() {
        //setup
        Player player = new Player("player1", Color.red);
        Village village = new Village(player);
        ResourceCard resource1 = new ResourceCard(ResourceType.GRAIN);
        player.addResourceCard(resource1);

        City city = new City(player);
        player.addCityPiece(city);
        Crossroad crossroad = new Crossroad(12, village, false);
        GamingBoard gamingBoard = new GamingBoard();
        Set<Crossroad> crossroads = new HashSet<>();
        crossroads.add(crossroad);
        gamingBoard.setCrossroads(crossroads);
        //when
        gamingBoard.upgradeVillageToCity(player, crossroad);
        //then
        assertEquals(1, player.getNumberOfCityPieces());
        assertEquals(0, player.getVillagePieces().size());
        assertEquals(1, player.getNumberOfResourceCards());
        assertSame(village, crossroad.getPlayerSettlement());
    }

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void buildVillage() {
        // setup
        Player player = new Player("builder", Color.green);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.LUMBER);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.BRICK);
        ResourceCard resourceCard3 = new ResourceCard(ResourceType.GRAIN);
        ResourceCard resourceCard4 = new ResourceCard(ResourceType.WOOL);
        Village village = new Village(player);
        GamingBoard gamingBoard = new GamingBoard();
        Set<Crossroad> crossroads = new HashSet<>();
        Crossroad crossroad = new Crossroad(5, null, true);

        player.addResourceCard(resourceCard1);
        player.addResourceCard(resourceCard2);
        player.addResourceCard(resourceCard3);
        player.addResourceCard(resourceCard4);
        player.addVillagePiece(village);
        crossroads.add(crossroad);
        gamingBoard.setCrossroads(crossroads);

        // when
        gamingBoard.buildVillage(player, crossroad);

        // then
        assertEquals(0, player.getVillagePieces().size());
        assertSame(village, crossroad.getPlayerSettlement());
    }

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void buildVillage_otherPlayersSettlement() {
        // setup
        Player player = new Player("builder", Color.green);
        Player player2 = new Player("settler", Color.green);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.LUMBER);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.BRICK);
        ResourceCard resourceCard3 = new ResourceCard(ResourceType.GRAIN);
        ResourceCard resourceCard4 = new ResourceCard(ResourceType.WOOL);
        Village village = new Village(player);
        Village village2 = new Village(player2);
        GamingBoard gamingBoard = new GamingBoard();
        Set<Crossroad> crossroads = new HashSet<>();
        Crossroad crossroad = new Crossroad(5, village2, false);

        player.addResourceCard(resourceCard1);
        player.addResourceCard(resourceCard2);
        player.addResourceCard(resourceCard3);
        player.addResourceCard(resourceCard4);
        player.addVillagePiece(village);
        crossroads.add(crossroad);
        gamingBoard.setCrossroads(crossroads);

        // when
        gamingBoard.buildVillage(player, crossroad);

        // then
        assertEquals(1, player.getVillagePieces().size());
        assertSame(player2, village2.getOwner());
    }

    /**
     * @author xtrneny1
     * @version etapa 2
     */
    @Test
    void buildVillage_notEnoughResources() {
        // setup
        Player player = new Player("builder", Color.green);
        Village village = new Village(player);
        ResourceCard resourceCard1 = new ResourceCard(ResourceType.LUMBER);
        ResourceCard resourceCard2 = new ResourceCard(ResourceType.BRICK);
        ResourceCard resourceCard3 = new ResourceCard(ResourceType.GRAIN);

        GamingBoard gamingBoard = new GamingBoard();
        Set<Crossroad> crossroads = new HashSet<>();
        Crossroad crossroad = new Crossroad(5, null, false);

        player.addResourceCard(resourceCard1);
        player.addResourceCard(resourceCard2);
        player.addResourceCard(resourceCard3);

        player.addVillagePiece(village);
        crossroads.add(crossroad);
        gamingBoard.setCrossroads(crossroads);

        // when
        gamingBoard.buildVillage(player, crossroad);

        // then
        assertEquals(1, player.getVillagePieces().size());
        assertEquals(3, player.getNumberOfResourceCards());
    }
}
package cz.mendelu.catan.game.gamebuilder;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.Crossroad;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.gamingboard.Path;
import cz.mendelu.catan.gamingboard.Tile;
import cz.mendelu.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultPresetBuilder extends GameBuilder{

    @Override
    public void buildGame(List<String> playerNames, List<Color> playerColors) {
        Game game = new Game();

        GamingBoard gamingBoard = new GamingBoard();
        CardDeck cardDeck = generateCardDeck();
        Set<Path> paths = generatePaths(cardDeck);
        Set<Crossroad> crossroads = generateCrossroads(gamingBoard);
        List<Player> players = generatePlayers(playerNames, playerColors, gamingBoard);
        if (cardDeck != null && paths != null && crossroads != null && players != null){
            gamingBoard.setCardDeck(cardDeck);
            gamingBoard.setPaths(paths);
            gamingBoard.setCrossroads(crossroads);
            game.setGamingBoard(gamingBoard);
            connectPathsWithCrossroads(gamingBoard);

            List<Tile> tiles = generateTiles(null, gamingBoard);
            setCrossroadsToTiles(tiles, gamingBoard);
            gamingBoard.setTiles(tiles);

            game.setGamingBoard(gamingBoard);
            game.setPlayers(players);
            this.game = game;
        }
    }

    @Override
    public List<Tile> generateTiles(List<ResourceType> types, GamingBoard gamingBoard) {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(1, 10, gamingBoard, false, ResourceType.ORE));
        tiles.add(new Tile(2, 2, gamingBoard, false, ResourceType.WOOL));
        tiles.add(new Tile(3, 9, gamingBoard, false, ResourceType.LUMBER));
        tiles.add(new Tile(4, 12, gamingBoard, false, ResourceType.GRAIN));
        tiles.add(new Tile(5, 6, gamingBoard, false, ResourceType.BRICK));
        tiles.add(new Tile(6, 4, gamingBoard, false, ResourceType.WOOL));
        tiles.add(new Tile(7, 10, gamingBoard, false, ResourceType.BRICK));
        tiles.add(new Tile(8, 9, gamingBoard, false, ResourceType.GRAIN));
        tiles.add(new Tile(9, 11, gamingBoard, false, ResourceType.LUMBER));
        tiles.add(new Tile(10, 0, gamingBoard, true, ResourceType.EMPTY));
        tiles.add(new Tile(11, 3, gamingBoard, false, ResourceType.LUMBER));
        tiles.add(new Tile(12, 8, gamingBoard, false, ResourceType.ORE));
        tiles.add(new Tile(13, 8, gamingBoard, false, ResourceType.LUMBER));
        tiles.add(new Tile(14, 3, gamingBoard, false, ResourceType.ORE));
        tiles.add(new Tile(15, 4, gamingBoard, false, ResourceType.GRAIN));
        tiles.add(new Tile(16, 5, gamingBoard, false, ResourceType.WOOL));
        tiles.add(new Tile(17, 5, gamingBoard, false, ResourceType.BRICK));
        tiles.add(new Tile(18, 6, gamingBoard, false, ResourceType.GRAIN));
        tiles.add(new Tile(19, 11, gamingBoard, false, ResourceType.WOOL));
        return tiles;
    }
}

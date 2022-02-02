package cz.mendelu.catan.game.gamebuilder;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.gamingboard.Tile;

import java.awt.*;
import java.util.List;

public class CustomBuilder extends GameBuilder {
    @Override
    public void buildGame(List<String> playerNames, List<Color> playerColors) {

    }

    @Override
    public List<Tile> generateTiles(List<ResourceType> types, GamingBoard gamingBoard) {
        return null;
    }
}

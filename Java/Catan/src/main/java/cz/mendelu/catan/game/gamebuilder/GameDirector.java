package cz.mendelu.catan.game.gamebuilder;

import cz.mendelu.catan.game.Game;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class GameDirector implements Serializable {
    private GameBuilder builder;

    public GameDirector(GameBuilder builder){
        this.builder = builder;
    }

    public Game createNewGame(List<String> playerNames, List<Color> playerColors){
        builder.buildGame(playerNames, playerColors);
        return builder.getGame();
    }

    public GameBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(GameBuilder builder) {
        this.builder = builder;
    }
}

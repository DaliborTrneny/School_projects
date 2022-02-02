package cz.mendelu.catan.greenfoot.gamemode.menu;

import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.creatingGame.GameModeCreatingGame;
import cz.mendelu.catan.greenfoot.gamemode.joinGame.GameModeJoinGame;
import cz.mendelu.catan.greenfoot.gamemode.selectingSave.GameModeSelectingSave;
import cz.mendelu.catan.greenfoot.gamemode.startServer.GameModeStartServer;
import cz.mendelu.catan.greenfoot.gamemode.statsMode.GameModeStats;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;

import java.util.ArrayList;
import java.util.List;

public class GameModeMenu extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";


    public GameModeMenu () {
        allActors = new ArrayList<>();
        allActors.addAll(generateUIButtons());
    }

    // BUTTONS
    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();
        ButtonActor startNewGame = new ButtonActor("images/menu/menuButtons/newGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeCreatingGame());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2);
        buttonActors.add(startNewGame);

        ButtonActor hostNewgame = new ButtonActor("images/menu/menuButtons/hostNewGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeStartServer());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 100);
        buttonActors.add(hostNewgame);

        ButtonActor joinGame = new ButtonActor("images/menu/menuButtons/joinGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeJoinGame());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 200);
        buttonActors.add(joinGame);

        ButtonActor loadGame = new ButtonActor("images/menu/menuButtons/loadGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeSelectingSave(1));
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 300);
        buttonActors.add(loadGame);

        ButtonActor gameStats = new ButtonActor("images/menu/menuButtons/stats.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeStats());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 400);
        buttonActors.add(gameStats);

        return buttonActors;
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public String getBackgroundPath() {
        return MENU_BACKGROUND;
    }
}

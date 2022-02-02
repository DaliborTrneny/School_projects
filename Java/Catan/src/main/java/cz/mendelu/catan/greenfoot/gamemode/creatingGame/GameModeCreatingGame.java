package cz.mendelu.catan.greenfoot.gamemode.creatingGame;

import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.setupGame.GameModeSetupGame;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModeCreatingGame extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";

    public GameModeCreatingGame () {
        allActors = new ArrayList<>();
        allActors.addAll(generateUIButtons());
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();
        ButtonActor presetGame = new ButtonActor("images/menu/menuButtons/presetGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeSetupGame());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2);
        buttonActors.add(presetGame);

        ButtonActor ownGame = new ButtonActor("images/menu/menuButtons/startOwnGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                System.out.println("ownGame");
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 100);
        buttonActors.add(ownGame);

        ButtonActor backToMainMenu = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 450);
        buttonActors.add(backToMainMenu);

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

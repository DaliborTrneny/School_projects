package cz.mendelu.catan.greenfoot.gamemode.setupGame;

import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.selectingSave.GameModeSelectingSave;
import cz.mendelu.catan.greenfoot.gamemode.setupPlayers.GameModeSetupPlayers;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;

public class GameModeSetupGame extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";
    private int numberOfPlayers;
    private PlaceHolderActor playersCounter;

    public GameModeSetupGame () {
        allActors = new ArrayList<>();
        allActors.addAll(generateUIButtons());
        generateObjects();
    }

    @Override
    public void update(Object data) {
        playersCounter.setImage(new GreenfootImage(Integer.toString(numberOfPlayers), 75, GAME_WHITE, TRANSPARENT));
    }

    private void generateObjects () {
        allActors.add(playersCounter = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 + 100, null));
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();

        ButtonActor backToMainMenu = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 450);
        buttonActors.add(backToMainMenu);

        ButtonActor playersSettings = new ButtonActor("images/menu/menuButtons/nextMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeSetupPlayers(numberOfPlayers));
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 350);
        buttonActors.add(playersSettings);

        ButtonActor arrowLeft = new ButtonActor("images/menu/menuButtons/arrowLeft.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (numberOfPlayers == 1) {
                    numberOfPlayers = 4;
                } else {
                    numberOfPlayers--;
                }
            }
        }, GAME_WIDTH/2 - 125, GAME_HEIGHT/2 + 100);
        buttonActors.add(arrowLeft);

        ButtonActor arrowRight = new ButtonActor("images/menu/menuButtons/arrowRight.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (numberOfPlayers == 4) {
                    numberOfPlayers = 1;
                } else {
                    numberOfPlayers++;
                }
            }
        }, GAME_WIDTH/2 + 125, GAME_HEIGHT/2 + 100);
        buttonActors.add(arrowRight);

        return buttonActors;
    }

    @Override
    public String getBackgroundPath() {
        return MENU_BACKGROUND;
    }
}

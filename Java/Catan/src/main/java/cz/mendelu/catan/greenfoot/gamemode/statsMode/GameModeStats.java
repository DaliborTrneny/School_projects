package cz.mendelu.catan.greenfoot.gamemode.statsMode;

import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import cz.mendelu.catan.iooperations.CatanFileHandler;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;

public class GameModeStats extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";

    private PlaceHolderActor longestGame;
    private PlaceHolderActor biggestArmy;
    CatanFileHandler catanFileHandler = new CatanFileHandler();
    List<Integer> gameStats = catanFileHandler.loadUniqueStats();

    public GameModeStats () {
        allActors = new ArrayList<>();
        allActors.addAll(generateUIButtons());
        generateObjects();
    }

    private void generateObjects () {
        allActors.add(longestGame = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 - 75, null));
        allActors.add(biggestArmy = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 - 25, null));
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

        return buttonActors;
    }

    @Override
    public void update(Object data) {
        longestGame.setImage(new GreenfootImage("Nejdelsi hra: " + gameStats.get(0) + " tahu", 40, GAME_WHITE, TRANSPARENT));
        biggestArmy.setImage(new GreenfootImage("Nejvetsi pocet vylozenych rytiru: " + gameStats.get(1), 40, GAME_WHITE, TRANSPARENT));
    }

    @Override
    public String getBackgroundPath() {
        return MENU_BACKGROUND;
    }
}

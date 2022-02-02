package cz.mendelu.catan.greenfoot.gamemode.setupPlayers;

import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.setupGame.GameModeSetupGame;
import cz.mendelu.catan.greenfoot.gamemode.setupPlayers.playerActors.PlayerColorActor;
import cz.mendelu.catan.greenfoot.gamemode.setupPlayers.playerActors.PlayerNameActor;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.player.Player;
import greenfoot.Greenfoot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameModeSetupPlayers extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";
    private int numberOfPlayers;
    private List<String> players;
    private List<Color> playerColors;
    private List<Color> availableColors = fillAvailableColorList();

    public GameModeSetupPlayers (int number) {
        this.numberOfPlayers = number;
        allActors = new ArrayList<>();
        generateNameActors();
        generateColorActors();
        allActors.addAll(generateUIButtons());
        players = new ArrayList<>();
        playerColors = new ArrayList<>();
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();

        ButtonActor backToSetupNumberOfPlayers = new ButtonActor("images/menu/menuButtons/playersNumberSetting.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeSetupGame());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 350);
        buttonActors.add(backToSetupNumberOfPlayers);

        ButtonActor backToMainMenu = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 450);
        buttonActors.add(backToMainMenu);

        ButtonActor createNewGame = new ButtonActor("images/menu/menuButtons/createNewGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                players = getPlayersNamesFromActors();
                playerColors = getPlayerColorsFromActors();
                gameWorld.switchGameMode(new GameModePlaying(players, playerColors));
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 250);
        buttonActors.add(createNewGame);

        return buttonActors;
    }

    public void generateNameActors() {
        for (int i = 0; i < numberOfPlayers; i++) {
            allActors.add(new PlayerNameActor("Player Name " + (i+1), GAME_WIDTH / 2 - 30, GAME_HEIGHT / 2 - 80 + (i * 60)));
        }
    }

    public void generateColorActors() {
        for (int i = 0; i < numberOfPlayers; i++) {
            allActors.add(new PlayerColorActor(availableColors.get(i), GAME_WIDTH / 2 + 130, GAME_HEIGHT / 2 - 80 + (i * 60)));
        }
    }
    
    public List<String> getPlayersNamesFromActors () {
        List<String> namesInput = new ArrayList<>();

        for (var actor: allActors) {
            if (actor.getClass() == PlayerNameActor.class) {
                namesInput.add(((PlayerNameActor) actor).getPlayerName());
            }
        }

        return namesInput;
    }

    public List<Color> getPlayerColorsFromActors () {
        List<Color> colorsInput = new ArrayList<>();

        for (var actor: allActors) {
            if (actor.getClass() == PlayerColorActor.class) {
                colorsInput.add(((PlayerColorActor) actor).getPlayerColor());
            }
        }

        return colorsInput;
    }

    public List<Color> fillAvailableColorList () {
        List<Color> temp = new ArrayList<>();

        // COLORS
        temp.add(Color.BLUE);
        temp.add(Color.RED);
        temp.add(Color.PINK);
        temp.add(Color.GREEN);

        Collections.shuffle(temp);

        return temp;
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public String getBackgroundPath() {
        return MENU_BACKGROUND;
    }

}

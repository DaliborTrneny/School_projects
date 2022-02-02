package cz.mendelu.catan.greenfoot.gamemode.startServer;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.gamebuilder.DefaultPresetBuilder;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.creatingGame.GameModeCreatingGame;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import cz.mendelu.catan.networking.CatanServer;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GameModeStartServer extends GameMode {
    private static final String HOST_SERVER_BACKGROUND = "images/menu/menu_background.png";

    private int numberOfPlayers;
    private int serverPort;
    private CatanServer server;
    private Thread serverThread;

    private PlaceHolderActor info;
    private PlaceHolderActor numberOfPortDisplay;
    private PlaceHolderActor numberOfPlayersDisplay;


    public GameModeStartServer () {
        this.numberOfPlayers = -1;
        this.serverPort = -1;
        allActors = new ArrayList<>();
        allActors.addAll(generateObjects());
        allActors.addAll(generateUIButtons());
    }





    public List<ActorWithAccesibleGameWorld> generateObjects(){
        List<ActorWithAccesibleGameWorld> actors = new ArrayList<>();

        info = new PlaceHolderActor(GAME_WIDTH/2, 370, null);
        info.setImage(new GreenfootImage("", 40, GAME_WHITE, TRANSPARENT));
        actors.add(info);

        numberOfPlayersDisplay = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2, null);
        numberOfPlayersDisplay.setImage(new GreenfootImage("Pocet hracu: ", 40, GAME_WHITE, TRANSPARENT));
        actors.add(numberOfPlayersDisplay);

        numberOfPortDisplay = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 + 200, null);
        numberOfPortDisplay.setImage(new GreenfootImage("Cislo portu: ", 40, GAME_WHITE, TRANSPARENT));
        actors.add(numberOfPortDisplay);

        return actors;
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();
        ButtonActor startServerButton = new ButtonActor("images/menu/menuButtons/newGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (numberOfPlayers > 0 && numberOfPlayers <= Game.MAX_NUMBER_OF_PLAYERS && serverPort > 0 && serverPort <= 65535){
                    server = new CatanServer(serverPort, numberOfPlayers, new DefaultPresetBuilder());
                    startServerThread();
                    try {
                        info.setImage(new GreenfootImage("Server spusten na IP: " + InetAddress.getLocalHost().getHostAddress() + ", port: " + serverPort, 40, GAME_WHITE, TRANSPARENT));
                    } catch (UnknownHostException e) {
                        info.setImage(new GreenfootImage("Server spusten na zadanem portu a IP adrese vaseho PC", 40, GAME_WHITE, TRANSPARENT));
                    }

                } else if (numberOfPlayers <= 0 || numberOfPlayers >= Game.MAX_NUMBER_OF_PLAYERS){
                    info.setImage(new GreenfootImage("Tento pocet hracu neni povolen", 40, GAME_WHITE, TRANSPARENT));
                } else {
                    info.setImage(new GreenfootImage("Tento port neni platny", 40, GAME_WHITE, TRANSPARENT));
                }

            }
        }, GAME_WIDTH / 2, GAME_HEIGHT - 150);
        buttonActors.add(startServerButton);

        ButtonActor backToMainMenuButton = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT - 75);
        buttonActors.add(backToMainMenuButton);

        ButtonActor customizeNumberOfPlayersButton = new ButtonActor("images/buttons/smallCustomizeButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                String nOfPlayers = Greenfoot.ask("Zadejte pocet hracu (3-4): ");
                int players = Integer.parseInt(nOfPlayers);
                numberOfPlayers = players;
                numberOfPlayersDisplay.setImage(new GreenfootImage("Pocet hracu: " + String.valueOf(numberOfPlayers), 40, GAME_WHITE, TRANSPARENT));
            }
        }, GAME_WIDTH/2 + 150, GAME_HEIGHT/2);
        buttonActors.add(customizeNumberOfPlayersButton);

        ButtonActor customizePortNumber = new ButtonActor("images/buttons/smallCustomizeButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                String portN = Greenfoot.ask("Zadejte cislo portu (1 - 65 535): ");
                int port = Integer.parseInt(portN);
                serverPort = port;
                numberOfPortDisplay.setImage(new GreenfootImage("Cislo portu: " + String.valueOf(serverPort), 40, GAME_WHITE, TRANSPARENT));
            }
        }, GAME_WIDTH/2 + 150, GAME_HEIGHT/2 + 200);
        buttonActors.add(customizePortNumber);


        return buttonActors;
    }




    private void startServerThread(){
        serverThread = new Thread(server);
        serverThread.start();
    }



        @Override
    public void update(Object data) {

    }

    @Override
    public String getBackgroundPath() {
        return HOST_SERVER_BACKGROUND;
    }
}

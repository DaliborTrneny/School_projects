package cz.mendelu.catan.greenfoot.gamemode.joinGame;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import cz.mendelu.catan.networking.CatanClient;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.List;

import static cz.mendelu.catan.greenfoot.gamemode.GameMode.*;

public class GameModeJoinGame extends GameMode {
    private static final String HOST_SERVER_BACKGROUND = "images/menu/menu_background.png";

    private String serverIP;
    private int serverPort;
    private CatanClient catanClient;

    private String chosenName;

    private Thread clientThread;

    private PlaceHolderActor info;
    private PlaceHolderActor nameDisplay;
    private PlaceHolderActor serverIPDisplay;
    private PlaceHolderActor serverPortDisplay;



    //For now just storing gameworld from button onClick listener for switching gameMode to playing on server game startup
    private GameWorld gameWorldReference;

    public GameModeJoinGame () {
        this.chosenName = "";
        this.serverIP = "";
        this.serverPort = -1;
        allActors = new ArrayList<>();
        allActors.addAll(generateObjects());
        allActors.addAll(generateUIButtons());
    }

    private CatanClient.CatanClientEventListener generateCatanClientEventListener() {
        CatanClient.CatanClientEventListener eventListener = new CatanClient.CatanClientEventListener() {
            @Override
            public void onConnectedToServer() {
                info.setImage(new GreenfootImage("Ceka se na pripojeni ostatnich hracu", 40, GAME_WHITE, TRANSPARENT));
            }

            @Override
            public void onFailedToConnectToServer() {
                info.setImage(new GreenfootImage("K serveru se nepodarilo pripojit", 40, GAME_WHITE, TRANSPARENT));
            }

            @Override
            public void onGameStarted(GameWorldState state, Game game) {
                gameWorldReference.switchGameMode(new GameModePlaying(catanClient, state, game));
            }

            @Override
            public void onGameUpdated(GameWorldState state, Game game) {

            }

            @Override
            public void onGameEnded(GameWorldState state, Game game) {
                //nothing for now
            }
        };
        return eventListener;
    }



    public List<ActorWithAccesibleGameWorld> generateObjects(){
        List<ActorWithAccesibleGameWorld> actors = new ArrayList<>();

        info = new PlaceHolderActor(GAME_WIDTH/2, 370, null);
        info.setImage(new GreenfootImage("", 40, GAME_WHITE, TRANSPARENT));
        actors.add(info);

        nameDisplay = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2, null);
        nameDisplay.setImage(new GreenfootImage("Tvoje jmeno: ", 40, GAME_WHITE, TRANSPARENT));
        actors.add(nameDisplay);

        serverIPDisplay = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 + 50, null);
        serverIPDisplay.setImage(new GreenfootImage("IP adresa serveru: ", 40, GAME_WHITE, TRANSPARENT));
        actors.add(serverIPDisplay);

        serverPortDisplay = new PlaceHolderActor(GAME_WIDTH/2, GAME_HEIGHT/2 + 100, null);
        serverPortDisplay.setImage(new GreenfootImage("Port serveru: ", 40, GAME_WHITE, TRANSPARENT));
        actors.add(serverPortDisplay);

        return actors;
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();
        ButtonActor joinGameButton = new ButtonActor("images/menu/menuButtons/joinGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorldReference = gameWorld;


                if (!chosenName.isBlank() && !serverIP.isBlank() && serverPort > 0 && serverPort <= 65535){
                    catanClient = new CatanClient(chosenName, serverIP, serverPort);
                    catanClient.setEventListener(generateCatanClientEventListener());
                    startClientThread();
                } else if (chosenName.isBlank()){
                    info.setImage(new GreenfootImage("Vypln validni jmeno", 40, GAME_WHITE, TRANSPARENT));
                } else if (serverIP.isBlank()){
                    info.setImage(new GreenfootImage("Tato IP adresa neni platna", 40, GAME_WHITE, TRANSPARENT));
                } else {
                    info.setImage(new GreenfootImage("Tento port neni platny", 40, GAME_WHITE, TRANSPARENT));
                }

            }
        }, GAME_WIDTH / 2, GAME_HEIGHT - 150);
        buttonActors.add(joinGameButton);

        ButtonActor backToMainMenuButton = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT - 75);
        buttonActors.add(backToMainMenuButton);

        ButtonActor customizeNameButton = new ButtonActor("images/buttons/smallCustomizeButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                String name = Greenfoot.ask("Zadej sve jmeno: ");
                chosenName = name;
                nameDisplay.setImage(new GreenfootImage("Tvoje jmeno: " + chosenName, 40, GAME_WHITE, TRANSPARENT));
            }
        }, GAME_WIDTH/2 + 260, GAME_HEIGHT/2);
        buttonActors.add(customizeNameButton);

        ButtonActor customizeServerIPButton = new ButtonActor("images/buttons/smallCustomizeButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                String ip = Greenfoot.ask("Zadej platnou IP adresu serveru: ");
                serverIP = ip;
                serverIPDisplay.setImage(new GreenfootImage("IP adresa serveru: " + serverIP, 40, GAME_WHITE, TRANSPARENT));
            }
        }, GAME_WIDTH/2 + 260, GAME_HEIGHT/2 + 50);
        buttonActors.add(customizeServerIPButton);

        ButtonActor customizeServerPortButton = new ButtonActor("images/buttons/smallCustomizeButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                String portString = Greenfoot.ask("Zadej port serveru: ");
                if (!portString.isBlank()) {
                    int port = Integer.parseInt(portString);
                    serverPort = port;
                }
                serverPortDisplay.setImage(new GreenfootImage("Port serveru: " + serverPort, 40, GAME_WHITE, TRANSPARENT));
            }
        }, GAME_WIDTH/2 + 260, GAME_HEIGHT/2 + 100);
        buttonActors.add(customizeServerPortButton);


        return buttonActors;
    }




    private void startClientThread(){
        clientThread = new Thread(catanClient);
        clientThread.start();
    }



    @Override
    public void update(Object data) {

    }

    @Override
    public String getBackgroundPath() {
        return HOST_SERVER_BACKGROUND;
    }
}

package cz.mendelu.catan.greenfoot.gamemode.playing;

import cz.mendelu.catan.cards.actioncards.*;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.game.gamebuilder.DefaultPresetBuilder;
import cz.mendelu.catan.game.gamebuilder.GameDirector;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.*;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks.*;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.counteractors.*;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import cz.mendelu.catan.greenfoot.helpers.PopupWindowGenerator;
import cz.mendelu.catan.networking.CatanClient;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModePlaying extends GameMode {
    private static final String PLAYING_BACKGROUND = "images/game/gameBoard.jpg";
    //List of states, which if the game is currently in, the buttons are enabled
    private final List<GameWorldState> BUTTONS_ENABLED_STATES = Arrays.asList(GameWorldState.RUNNING);
    //List of states, which if the game is currently in, action can not be aborted (by abort action button)
    private final List<GameWorldState> ACTION_UNABORTABLE_STATES = Arrays.asList(GameWorldState.ROLLING_DICES, GameWorldState.MOVING_BANDIT, GameWorldState.STARTUP_BUILD_ROAD,
            GameWorldState.STARTUP_BUILD_VILLAGE, GameWorldState.STARTUP_NEXT_TURN, GameWorldState.STEALING_RESOURCE_CARDS,
            GameWorldState.ROAD_BUILDING_CARD_PLAYED, GameWorldState.INVENTION_CARD_PLAYED, GameWorldState.MONOPOL_CARD_PLAYED, GameWorldState.GAME_WON);


    private Game gameInstance;
    private GameWorldState gameState;
    private GameDirector director;

    //IN CASE OF MULTIPLAYER GAME
    private CatanClient catanClient;
    private Player thisSessionPlayer;

    //BANDIT ACTOR NEEDS TO BE ALLOCATED IN ADVANCE
    BanditActor banditActor;


    //SINGLEPLAYER CONSTRUCTOR FOR LOADED GAMES
    public GameModePlaying(Game gameInstance, GameWorldState gameState){
        this.gameInstance = gameInstance;
        this.gameState = gameState;

        allActors = new ArrayList<>();
        generateObjects();
        allActors.addAll(generateUIButtons());
    }

    //MULTIPLAYER CONSTRUCTOR
    public GameModePlaying(CatanClient client, GameWorldState gameState, Game gameInstance){
        this.gameInstance = gameInstance;
        this.gameState = gameState;

        this.thisSessionPlayer = gameInstance.getPlayerByName(client.getName());
        this.catanClient = client;
        this.catanClient.setEventListener(generateCatanClientEventListener());
        allActors = new ArrayList<>();

        generateObjects();
        allActors.addAll(generateUIButtons());
    }

    //SINGLEPLAYER CONSTRUCTOR FOR NEW GAMES
    public GameModePlaying (List<String> playersInput, List<Color> colorsInput) {
        this.director = new GameDirector(new DefaultPresetBuilder());
        this.gameInstance = director.createNewGame(playersInput, colorsInput);
        allActors = new ArrayList<>();

        generateObjects();
        allActors.addAll(generateUIButtons());
        this.gameState = (GameWorldState.STARTUP_BUILD_VILLAGE);
    }

    private CatanClient.CatanClientEventListener generateCatanClientEventListener(){
        CatanClient.CatanClientEventListener eventListener = new CatanClient.CatanClientEventListener() {
            @Override
            public void onConnectedToServer() {
                //nothing for now
            }

            @Override
            public void onFailedToConnectToServer() {
                CatanLogger.getCatanLogger().addLog("Nastaly chyby pri komunikaci se serverem");
            }

            @Override
            public void onGameStarted(GameWorldState state, Game game) {
                //nothing for now
            }

            @Override
            public void onGameUpdated(GameWorldState state, Game game) {
                gameState = state;
                gameInstance = game;
                thisSessionPlayer = gameInstance.getPlayerByName(catanClient.getName());
            }

            @Override
            public void onGameEnded(GameWorldState state, Game game) {
                //nothing for now
            }
        };
        return eventListener;
    }

    @Override
    public void update(Object data) {
        //does nothing right now, actors all update themselfs
    }

    private void generateObjects(){
        //DISPLAYING THE NAME OF THIS CLIENT PLAYER IN MULTIPLAYER (DOESNT SHOW UP IN GAME ON ONE PC)
        if (isThisGameOnline()) {
            PlaceHolderActor playerName =  new PlaceHolderActor(330, 35, null);
            playerName.setImage(new GreenfootImage(thisSessionPlayer.getName(), 45, GAME_GREY, TRANSPARENT));
            allActors.add(playerName);
        }
        //NAME OF CURRENTLY PLAYING PLAYER
        allActors.add(new PlaceHolderActor(980, 57, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(gameInstance.getCurrentlyPlayingPlayer().getName(), 40, GAME_WHITE, TRANSPARENT));
            }
        }));
        //SCORE OF PLAYER
        allActors.add(new PlaceHolderActor(144, 317, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getTextScore(), 25, GAME_GREY, TRANSPARENT));
            }
        }));
        //NUMBER OF PLAYED KNIGHT CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(214, 343, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getTextNumberOfKnights(), 25, GAME_GREY, TRANSPARENT));;
            }
        }));

        //DISPLAYING THE NAME OF PLAYER WITH BIGGEST ARMY CARD
        allActors.add(new PlaceHolderActor(1475, 725, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                String playerWithBiggestArmy = (gameInstance.getPlayerWithBiggestArmyCard() == null) ? "" : gameInstance.getPlayerWithBiggestArmyCard().getName();
                thisActor.setImage(new GreenfootImage(playerWithBiggestArmy, 25, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING THE NAME OF PLAYER WITH LONGEST ROAD CARD
        allActors.add(new PlaceHolderActor(1700, 725, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                String playerWithLongestRoad = (gameInstance.getPlayerWithLongestRoadCard() == null) ? "" : gameInstance.getPlayerWithLongestRoadCard().getName();
                thisActor.setImage(new GreenfootImage(playerWithLongestRoad, 25, GAME_GREY, TRANSPARENT));
            }
        }));

        //DISPLAYING PLAYER'S NUMBER OF ROAD PIECES
        allActors.add(new PlaceHolderActor(315, 129, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfRoadPieces() + "x", 25, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING PLAYER'S NUMBER OF VILLAGE PIECES
        allActors.add(new PlaceHolderActor(315, 164, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfVillagePieces() + "x", 25, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING PLAYER'S NUMBER OF CITY PIECES
        allActors.add(new PlaceHolderActor(315, 198, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfCityPieces() + "x", 25, GAME_GREY, TRANSPARENT));
            }
        }));

        //DISPLAYING THE LAST RESULT OF ROLLING DICES
        PlaceHolderActor diceRollResult = new PlaceHolderActor(1210, 958,null);
        diceRollResult.setImage(new GreenfootImage("", 0, TRANSPARENT, TRANSPARENT));
        allActors.add(diceRollResult);

        //DISPLAYING GRAIN RESOURCE CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(105, 460, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfResourceCardsByType(ResourceType.GRAIN) + "x", 30, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING LUMBER RESOURCE CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(255, 460, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfResourceCardsByType(ResourceType.LUMBER) + "x", 30, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING BRICK RESOURCE CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(105, 510, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfResourceCardsByType(ResourceType.BRICK) + "x", 30, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING WOOL RESOURCE CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(255, 510, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfResourceCardsByType(ResourceType.WOOL) + "x", 30, GAME_GREY, TRANSPARENT));
            }
        }));
        //DISPLAYING ORE RESOURCE CARDS OF PLAYER
        allActors.add(new PlaceHolderActor(105, 556, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                thisActor.setImage(new GreenfootImage(getReferencePlayer().getNumberOfResourceCardsByType(ResourceType.ORE) + "x", 30, GAME_GREY, TRANSPARENT));
            }
        }));

        //CALLING ROLL DICES METHOD IN GAME
        allActors.add(new DiceActor(new DiceActor.DicesOnClickListener() {
            @Override
            public void onDicesRolled(int dicesRolledResult) {
                diceRollResult.setImage(new GreenfootImage(String.valueOf(dicesRolledResult), 60, GAME_WHITE, TRANSPARENT));
                if (dicesRolledResult == 7){
                    gameInstance.sevenRolled(gameInstance.getPlayers());
                }

            }
        },1120, 960));

        //CALLING NEXT TURN METHOD IN GAME
        allActors.add(new NextTurnActor(new NextTurnActor.NextTurnOnClickListener() {
            @Override
            public void onNextTurn() {
                diceRollResult.setImage(new GreenfootImage("", 60, TRANSPARENT, TRANSPARENT));
                sendUpdateToServer();
            }
        }, 660, 960));

        //DISPLAYING CURRENT STATE OF THE GAME
        allActors.add(new PlaceHolderActor(1590, 950, new PlaceHolderActor.PlaceHolderEventListener() {
            @Override
            public void onUpdate(PlaceHolderActor thisActor) {
                String stateString = GameWorldState.getGameWolrdStateString(gameState);
                if (isCurrentPlayersTurn()){
                    thisActor.setImage(new GreenfootImage(stateString, 40, GAME_GREY, TRANSPARENT));
                } else{
                    thisActor.setImage(new GreenfootImage("", 40, GAME_GREY, TRANSPARENT));
                }
            }
        }));

        //RESOURCE TYPE ACTORS ACTING AS A CLICKABLE FOR CALLING CERTAIN GAME METHODS
        allActors.add(new ResourceTypeActor(ResourceType.GRAIN, 50, 455));
        allActors.add(new ResourceTypeActor(ResourceType.LUMBER, 182, 455));
        allActors.add(new ResourceTypeActor(ResourceType.BRICK, 50, 508));
        allActors.add(new ResourceTypeActor(ResourceType.WOOL, 182, 508));
        allActors.add(new ResourceTypeActor(ResourceType.ORE, 50, 555));

        //ACTORS REPRESENTING CURRENT PLAYERS COLLECTIONS OF ACTION CARDS (PLAYER CAN USE THEM WITH THESE ACTORS)
        allActors.add(new InventionCardDeckActor(gameInstance.getCurrentlyPlayingPlayer().getActionCardListByClass(Invention.class), 85, 671));
        allActors.add(new RoadBuildingCardDeckActor(gameInstance.getCurrentlyPlayingPlayer().getActionCardListByClass(RoadBuilding.class), 215, 671));
        allActors.add(new MonopolCardDeckActor(gameInstance.getCurrentlyPlayingPlayer().getActionCardListByClass(Monopol.class), 345, 671));
        allActors.add(new KnightCardDeckActor(gameInstance.getCurrentlyPlayingPlayer().getActionCardListByClass(Knight.class), 85, 882));
        allActors.add(new VictoryPointCardDeckActor(gameInstance.getCurrentlyPlayingPlayer().getActionCardListByClass(VictoryPoint.class), 215, 882));

        //COUNTERS FOR CURRENT PLAYER NUMBER OF INDIVIDUAL TYPES OF ACTION CARDS
        allActors.add(new CounterActorInvention(gameInstance.getCurrentlyPlayingPlayer(), 23, GAME_GREY, TRANSPARENT, 133, 597));
        allActors.add(new CounterActorMonopol(gameInstance.getCurrentlyPlayingPlayer(), 23, GAME_GREY, TRANSPARENT, 392, 597));
        allActors.add(new CounterActorRoadBuilding(gameInstance.getCurrentlyPlayingPlayer(), 23, GAME_GREY, TRANSPARENT, 262, 597));
        allActors.add(new CounterActorKnight(gameInstance.getCurrentlyPlayingPlayer(), 23, GAME_GREY, TRANSPARENT, 133, 809));
        allActors.add(new CounterActorVictoryPoint(gameInstance.getCurrentlyPlayingPlayer(), 23, GAME_GREY, TRANSPARENT, 263, 809));

        //ACTOR DISPLAYING CURRENT GAME LOG (FOR EXAMPLE ERROR MESSAGES)
        allActors.add(new LogActor(GAME_WIDTH/2, 20));

        GamingBoardActorsGenerator actorsGenerator = new GamingBoardActorsGenerator(this.gameInstance.getGamingBoard());

        //GENERATING AND ADDING TILE ACTORS
        List<TileActor> tileActors = actorsGenerator.getTileActorsWithPosition(new TileActor.TileActorOnClickListener() {
            @Override
            public void onBanditLocationSet(TileActor tileActor) {
                banditActor.moveToAnotherTile(tileActor);
            }
        });
        //GENERATING AND ADDING CROSSROAD ACTORS
        List<CrossroadActor> crossroadActors = actorsGenerator.getCrossroadActorsWithPositions();
        //GENERATING AND ADDING PATH ACTORS
        List<PathActor> pathActors = actorsGenerator.getPathActorsWithPositions();
        //GENERATING AND ADDING TOKEN ACTORS
        List<PlaceHolderActor> tokenActors = actorsGenerator.generateTokenActorsForTiles(tileActors);

        allActors.addAll(tileActors);
        allActors.addAll(pathActors);
        allActors.addAll(crossroadActors);
        allActors.addAll(tokenActors);

        //PRIDANI LUPICE DO ZACATKU HRY NA POZICI POUSTNIHO HEXAGONU
        banditActor = new BanditActor(tileActors, GAME_WIDTH/2, GAME_HEIGHT/2);
        allActors.add(banditActor);

        addPlayerActors();
    }

    public List<ButtonActor> generateUIButtons(){
        List<ButtonActor> buttonActors = new ArrayList<>();
        ButtonActor menuButton = new ButtonActor("images/menu/menuButtons/menu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                new PopupWindowGenerator(gameWorld).menuPopupWindow();
            }
        }, 1700, 40);
        buttonActors.add(menuButton);
        ButtonActor buildRoadButton = new ButtonActor("images/buttons/buildIcon.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()){
                    if (gameInstance.getCurrentlyPlayingPlayer().hasResourceCards(ResourceType.getResourceTypesForBuildingRoad())) {
                        setGameState(GameWorldState.BUILDING_ROAD);
                    } else { CatanLogger.getCatanLogger().addLog("Pro stavbu silnice nemas dostatek surovin"); }
                }
            }
        }, 51, 130);
        buttonActors.add(buildRoadButton);

        ButtonActor buildVillageButton = new ButtonActor("images/buttons/buildIcon.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()){
                    if (gameInstance.getCurrentlyPlayingPlayer().hasResourceCards(ResourceType.getResourceTypesForBuildingVillage())) {
                        setGameState(GameWorldState.BUILDING_VILLAGE);
                    } else { CatanLogger.getCatanLogger().addLog("Pro stavbu vesnice nemas dostatek surovin"); }
                }

            }
        }, 51, 162);
        buttonActors.add(buildVillageButton);

        ButtonActor buildCityButton = new ButtonActor("images/buttons/buildIcon.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()){
                    if(gameInstance.getCurrentlyPlayingPlayer().hasResourceCards(ResourceType.getResourceTypesForUpgradingVillage())) {
                        setGameState(GameWorldState.BUILDING_CITY);
                    } else { CatanLogger.getCatanLogger().addLog("Pro stavbu mesta nemas dostatek surovin"); }
                }
            }
        }, 51, 197);
        buttonActors.add(buildCityButton);

        ButtonActor buyCardButton = new ButtonActor("images/buttons/buyCardIcon.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()){
                    if (gameInstance.getCurrentlyPlayingPlayer().hasResourceCards(ResourceType.getResourceTypesForBuyingActionCard())){
                        if(gameInstance.getCurrentlyPlayingPlayer().buyActionCard()){
                            CatanLogger.getCatanLogger().addLog(getGameInstance().getCurrentlyPlayingPlayer().getName() + " si koupil akcni kartu");
                        }
                    } else { CatanLogger.getCatanLogger().addLog("Pro koupi akcni karty nemas dostatek surovin"); }
                }
            }
        }, 55, 231);
        buttonActors.add(buyCardButton);

        ButtonActor tradeWithBankButton = new ButtonActor("images/buttons/tradeWithBank.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()) {
                    PopupWindowGenerator pwg = new PopupWindowGenerator(gameWorld);
                    pwg.tradeWithBankPopupWindow();
                }
            }
        }, 1422, 343);
        buttonActors.add(tradeWithBankButton);

        ButtonActor tradeWithPortButton = new ButtonActor("images/buttons/tradeWithPort.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()) {
                    setGameState(GameWorldState.TRADING_WITH_PORT);
                }
            }
        }, 1510, 343);
        buttonActors.add(tradeWithPortButton);

        ButtonActor tradeWithPlayerButton = new ButtonActor("images/buttons/tradeWithPlayer.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()) {
                    setGameState(GameWorldState.TRADING_WITH_PLAYER);
                }
            }
        }, 1598, 343);
        buttonActors.add(tradeWithPlayerButton);

        ButtonActor useActionCard = new ButtonActor("images/buttons/useActionCardButton.jpg", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (buttonsEnabled()) {
                    if (!getGameInstance().getCurrentlyPlayingPlayer().hasPlayedActionCardThisTurn()) {
                        setGameState(GameWorldState.ACTION_CARD_SELECTING);
                    } else{
                        CatanLogger.getCatanLogger().addLog("Behem jednoho kola muzes zahrat jen jednu akcni kartu");
                    }
                }
            }
        }, 340, 880);
        buttonActors.add(useActionCard);

        ButtonActor abortActionButton = new ButtonActor("images/buttons/abortActionButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (!stateUnabortable() && isCurrentPlayersTurn()) {
                    gameState = GameWorldState.RUNNING;
                }
            }
        }, GAME_WIDTH/2, 960);

        buttonActors.add(abortActionButton);

        return buttonActors;
    }

    public void addPlayerActors() {
        for (int i = gameInstance.getPlayers().size() - 1; i >= 0; i--) {
            allActors.add(new PlayerActor(gameInstance.getPlayers().get(i), 1585, 558 - (i * 33)));
        }
    }

    public GameWorldState getGameState() {
        return gameState;
    }

    public void setGameState(GameWorldState newState) {
        this.gameState = newState;

        //ON EACH CHANGE OF STATE, UPDATE TO OTHER PLAYERS IS SENT (IF THIS GAME IS MULTIPLAYER)
        if (isThisGameOnline()) sendUpdateToServer();
    }


    public Game getGameInstance() {
        return gameInstance;
    }

    /**
     * Metoda zjistí, jetstli je aktuální hra multiplayerová, nebo jestli se jedná o hru více hráčů na jednom PC.
     *
     * @return aktuální hra je/není multiplayer
     * @version etapa 5
     */
    public boolean isThisGameOnline(){
        if (this.catanClient != null){
            return true;
        }
        return false;
    }

    /**
     * V případě multiplayerové hry metoda zjistí, jestli ja aktuálně daný hráč (klient) na tahu.
     * Pokud se nejedná o multiplayer hru (hra více hráčů na jednom počítači), vrací vždy true (na instanci klienta nezáleží)
     *
     * @return aktuální hráč je/není na tahu
     * @version etapa 5
     */
    public boolean isCurrentPlayersTurn(){
        if (isThisGameOnline()){
            if (this.thisSessionPlayer.equals(gameInstance.getCurrentlyPlayingPlayer())){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public String getBackgroundPath() {
        return PLAYING_BACKGROUND;
    }

    /**
     * Metoda zjistí, jestli se hra aktuálně nachází ve stavu, kdy jsou obecná tlačítka povolena (aktivni)
     *
     * @return současný stav blokuje/neblokuje tlačítka
     * @version etapa 5
     */
    public boolean buttonsEnabled() {
        if (!isThisGameOnline()) {
             return (BUTTONS_ENABLED_STATES.contains(this.gameState)) ? true : false;
        } else{
            //buttons clickable only if state allows it, and also if the player of this client instance has his turn
            return BUTTONS_ENABLED_STATES.contains(this.gameState) && isCurrentPlayersTurn();
        }
    }

    /**
     * Metoda zjistí, jestli se hra aktuálně nachází ve stavu, který nejde zrušit tlačítkem "Zrušit akci".
     * Jinými slovy musí se vykonat specifická, nepřeskočitelná akce, po které hra může pokračovat.
     *
     * @return současný stav blokuje/neblokuje tlačítka
     * @version etapa 5
     */
    public boolean stateUnabortable() {
        boolean result = (ACTION_UNABORTABLE_STATES.contains(this.gameState)) ? true : false;
        return  result;
    }


    //NETWORKING
    public void sendUpdateToServer(){
        if (this.catanClient != null){
            this.catanClient.sendGameUpdateToServer(getGameState(), gameInstance);
        }
    }

    /**
     * Metoda slouží pro získání referenčního hráče pro UI, dle kterého se budou např. vykreslovat počítatele karet, nahrávat CardDecky atp.
     * V případě Multiplayer hry to bude aktuální klient (hráč).
     * V případě hry více hráčů na jednom PC se bude vracet vždy hráč, který je aktuálně na tahu.
     *
     * @return hráč, dle kterého se mají zobrazovat počítadla, načítat CardDecky atp.
     * @version etapa 5
     */
    public Player getReferencePlayer() {
        if (isThisGameOnline()){
            return thisSessionPlayer;
        }else{
            return gameInstance.getCurrentlyPlayingPlayer();
        }
    }

    public Player getThisSessionPlayer() {
        return thisSessionPlayer;
    }
}

package cz.mendelu.catan.greenfoot.helpers;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.GamingBoard;
import cz.mendelu.catan.gamingboard.PortCrossroad;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.iooperations.CatanFileHandler;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopupWindowGenerator {
    private GameWorld gameWorld;

    public PopupWindowGenerator(GameWorld gameWorld){
        if (gameWorld != null){
            this.gameWorld = gameWorld;
        } else{
            throw new IllegalArgumentException("GameWorld reference can not be null");
        }
    }


    /**
     * Metoda pro vytvoření customizovatelného vyskakovacího okna
     *
     * @param label název vyskakovacího okna, který se zobrazí v horní části
     * @param label seznam předem definovaných prvků, které mají být v okně vykresleny
     *
     * @author xmusil5
     * @version etapa 4
     */
    private void showPopupWindow(String label, List<ActorWithAccesibleGameWorld> topLevelActors, boolean switchStateToPupup){
        if (topLevelActors!=null) {
            List<ActorWithAccesibleGameWorld> actorsToShow = new ArrayList<>();
            actorsToShow.add(new PopupWindowActor(gameWorld.getWidth() / 2, gameWorld.getHeight() / 2));
            PlaceHolderActor labelActor = new PlaceHolderActor(gameWorld.getWidth() / 2, gameWorld.getHeight() / 2 - PopupWindowActor.LABEL_OFFSET_UP, null);
            labelActor.setImage(new GreenfootImage(label, 30, GameMode.GAME_WHITE, GameMode.TRANSPARENT));
            actorsToShow.add(labelActor);

            ButtonActor cancelButtonActor = new ButtonActor("images/buttons/cancelButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
                @Override
                public void onClick(GameWorld gameWorld) {
                    gameWorld.getCurrentGameMode().removeActors(actorsToShow);
                    //vymazani trading bufferu u hrace (pro trading popup okna)
                    gameWorld.getGame().getCurrentlyPlayingPlayer().clearTradingBuffers();
                    //odebrani vsech objektu a vraceni hry do stavu running
                    for (var actor : actorsToShow) {
                        if (actor != null) {
                            gameWorld.removeObject(actor);
                        }
                    }
                    if (switchStateToPupup) gameWorld.setState(GameWorldState.RUNNING);
                }
            }, gameWorld.getWidth()/2, gameWorld.getHeight()/2 + PopupWindowActor.LABEL_OFFSET_UP);
            actorsToShow.add(cancelButtonActor);
            actorsToShow.addAll(topLevelActors);

            //Actori noveho popup okna se pridavaji i do celkoveho seznamu actoru v aktualnim GameModePlaying, aby pri odchodu do hlavniho menu mohli byt smazani
            gameWorld.getCurrentGameMode().addActors(actorsToShow);
            for (var actor : actorsToShow) {
                if (actor != null) {
                    gameWorld.addObject(actor, actor.getHorizontalCoordinate(), actor.getVerticalCoordinate());
                }
            }
            if (switchStateToPupup) gameWorld.setState(GameWorldState.POPUP_WINDOW);
        }
    }

    /**
     * Metoda vytvoří vyskakovací okno značící konec hry, kde zobrazí vítězného hráče
     *
     * @author xmusil5
     */
    public void victoryPopupWindow(Player victoriousPlayer){
        if (victoriousPlayer != null) {
            List<ActorWithAccesibleGameWorld> actorsToShow = new ArrayList<>();
            actorsToShow.add(new PopupWindowActor(gameWorld.getWidth() / 2, gameWorld.getHeight() / 2));

            PlaceHolderActor labelActor = new PlaceHolderActor(gameWorld.getWidth() / 2, gameWorld.getHeight() / 2 - PopupWindowActor.LABEL_OFFSET_UP, null);
            labelActor.setImage(new GreenfootImage("Konec hry", 30, GameMode.GAME_WHITE, GameMode.TRANSPARENT));
            actorsToShow.add(labelActor);

            PlaceHolderActor victoriousPlayerActor = new PlaceHolderActor(gameWorld.getWidth() / 2, gameWorld.getHeight() / 2, null);
            String victoryStatement = victoriousPlayer.getName() + " je vitezem teto hry";
            victoriousPlayerActor.setImage(new GreenfootImage(victoryStatement, 50, GameMode.GAME_GREY, GameMode.TRANSPARENT));
            actorsToShow.add(victoriousPlayerActor);

            //Actori noveho popup okna se pridavaji i do celkoveho seznamu actoru v aktualnim GameModePlaying, aby pri odchodu do hlavniho menu mohli byt smazani
            gameWorld.getCurrentGameMode().addActors(actorsToShow);
            for (var actor : actorsToShow) {
                if (actor != null) {
                    gameWorld.addObject(actor, actor.getHorizontalCoordinate(), actor.getVerticalCoordinate());
                }
            }
        }
    }


    /**
     * Metoda vytvoří vyskakovací okno pro menu hry
     *
     * @author xmusil5
     * @version etapa 4
     */
    public void menuPopupWindow(){
        String label = "Menu";
        List<ActorWithAccesibleGameWorld> actors = new ArrayList<>();
        actors.add(new ButtonActor("images/menu/menuButtons/backToMainMenuSmall.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, gameWorld.getWidth()/2, 420));
        actors.add(new ButtonActor("images/menu/menuButtons/saveGame.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                CatanFileHandler handler = new CatanFileHandler();
                //osetreni proti ukladani hry ve stavu popup window - popup window by se pri startu hry nemelo jak vykreslit
                GameWorldState savedState = (gameWorld.getState() == GameWorldState.POPUP_WINDOW) ? GameWorldState.RUNNING : gameWorld.getState();

                handler.saveGame(gameWorld.getGame(), savedState);
                CatanLogger.getCatanLogger().addLog("Hra byla uspesne ulozena");
            }
        }, gameWorld.getWidth()/2, 520));
        showPopupWindow(label, actors, false);
    }

    /**
     * Metoda zobrazí vyskakovací okno pro obchod s bankem
     *
     * @version etapa 5
     */
    public void tradeWithBankPopupWindow(){
        Player player = gameWorld.getGame().getCurrentlyPlayingPlayer();
        List<ActorWithAccesibleGameWorld> actors = new ArrayList<>();
        int bothColumnYStartOffset = -100;
        int verticalDifferenceBetweenActors = 50;
        int popupWindowCenterX = gameWorld.getWidth()/2;
        int popupWindowCenterY = gameWorld.getHeight()/2;

        PlaceHolderActor offeringTypesLabel = new PlaceHolderActor( popupWindowCenterX - PopupWindowActor.POPUP_WINDOW_WIDTH/3, popupWindowCenterY + bothColumnYStartOffset, null);
        offeringTypesLabel.setImage(new GreenfootImage("Nakup: ", 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));

        PlaceHolderActor acceptedTypesLabel = new PlaceHolderActor( popupWindowCenterX + PopupWindowActor.POPUP_WINDOW_WIDTH/3, popupWindowCenterY + bothColumnYStartOffset, null);
        acceptedTypesLabel.setImage(new GreenfootImage("Prodej: ", 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));

        PlaceHolderActor resultLabel = new PlaceHolderActor( popupWindowCenterX, popupWindowCenterY + bothColumnYStartOffset, null);
        resultLabel.setImage(new GreenfootImage(" ", 1, GameMode.TRANSPARENT, GameMode.TRANSPARENT));

        actors.add(offeringTypesLabel);
        actors.add(acceptedTypesLabel);
        actors.add(resultLabel);

        //generuje tlacitka pro nabizene typy
        List<ResourceType> allTradeableResourceTypes = ResourceType.getAllTradeAbleResourceTypes();
        for(int i = 0; i<allTradeableResourceTypes.size(); i++){
            ResourceType resourceType = allTradeableResourceTypes.get(i);
            String imagePath = ResourceType.getImagePathByResourceType(resourceType);
            if (!imagePath.isBlank()) {
                int x = popupWindowCenterX - PopupWindowActor.POPUP_WINDOW_WIDTH/3;
                int y = popupWindowCenterY + (bothColumnYStartOffset+verticalDifferenceBetweenActors) + i*verticalDifferenceBetweenActors;
                actors.add(new ButtonActor(imagePath, new ButtonActor.GreenfootButtonOnClickListener() {
                    @Override
                    public void onClick(GameWorld gameWorld) {
                        String resourceTypeString = ResourceType.getResourceTypeString(resourceType);
                        offeringTypesLabel.setImage(new GreenfootImage("Nakup: " + resourceTypeString, 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));
                        player.setBuyingBuffer(resourceType);
                    }
                }, x, y));
            } else{throw new NullPointerException("Image of resource type" + resourceType.toString() + "does not exist");}
        }
        //zobrazuje tlacitka pro typy k platbe
        for(int i = 0; i<allTradeableResourceTypes.size(); i++){
            ResourceType resourceType = allTradeableResourceTypes.get(i);
            String imagePath = ResourceType.getImagePathByResourceType(resourceType);
            if (!imagePath.isBlank()) {
                int x = popupWindowCenterX + PopupWindowActor.POPUP_WINDOW_WIDTH/3;
                int y = popupWindowCenterY + (bothColumnYStartOffset+verticalDifferenceBetweenActors) + i*verticalDifferenceBetweenActors;
                actors.add(new ButtonActor(imagePath, new ButtonActor.GreenfootButtonOnClickListener() {
                    @Override
                    public void onClick(GameWorld gameWorld) {
                        String resourceTypeString = ResourceType.getResourceTypeString(resourceType);
                        acceptedTypesLabel.setImage(new GreenfootImage("Prodej: " + resourceTypeString, 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));
                        player.setSellingBuffer(resourceType);
                    }
                }, x, y));
            } else{throw new NullPointerException("Image of resource type" + resourceType.toString() + "does not exist");}
        }

        actors.add(new ButtonActor("images/buttons/confirmButton.png", new ButtonActor.GreenfootButtonOnClickListener(){
            @Override
            public void onClick(GameWorld gameWorld) {
                CardDeck cardDeck = gameWorld.getGame().getGamingBoard().getCardDeck();
                Player currentPlayer = gameWorld.getGame().getCurrentlyPlayingPlayer();
                if (currentPlayer.getBuyingBuffer() != null && currentPlayer.getSellingBuffer() != null){
                    if (cardDeck.bankTradeWithPlayer(currentPlayer, currentPlayer.getBuyingBuffer(), currentPlayer.getSellingBuffer())){
                        resultLabel.setImage(new GreenfootImage("Obchod uspesne uzavren", 30, Color.GREEN, GameMode.TRANSPARENT));
                    } else {
                        resultLabel.setImage(new GreenfootImage("Obchod nelze provest", 30, Color.RED, GameMode.TRANSPARENT));
                    }
                }
            }
        }, popupWindowCenterX, popupWindowCenterY + 2*PopupWindowActor.LABEL_OFFSET_UP/3));

        showPopupWindow("Obchod s bankem 4:1", actors, true);
    }

    /**
     * Metoda zobrazí vyskakovací okno pro obchod s přístavem
     *
     * @param portCrossroad přístavová křižovatka, na které má proběhnout obchod
     *
     * @author xmusil5
     * @version etapa 4
     */
    public void tradeWithPortPopupWindow(PortCrossroad portCrossroad){
        if (portCrossroad != null) {
            Player player = gameWorld.getGame().getCurrentlyPlayingPlayer();
            List<ActorWithAccesibleGameWorld> actors = new ArrayList<>();
            int bothColumnYStartOffset = -100;
            int verticalDifferenceBetweenActors = 50;
            int popupWindowCenterX = gameWorld.getWidth() / 2;
            int popupWindowCenterY = gameWorld.getHeight() / 2;

            PlaceHolderActor offeringTypesLabel = new PlaceHolderActor(popupWindowCenterX - PopupWindowActor.POPUP_WINDOW_WIDTH / 3, popupWindowCenterY + bothColumnYStartOffset, null);
            offeringTypesLabel.setImage(new GreenfootImage("Nakup: ", 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));

            PlaceHolderActor acceptedTypesLabel = new PlaceHolderActor(popupWindowCenterX + PopupWindowActor.POPUP_WINDOW_WIDTH / 3, popupWindowCenterY + bothColumnYStartOffset, null);
            acceptedTypesLabel.setImage(new GreenfootImage("Prodej: ", 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));

            PlaceHolderActor resultLabel = new PlaceHolderActor(popupWindowCenterX, popupWindowCenterY + bothColumnYStartOffset, null);
            resultLabel.setImage(new GreenfootImage(" ", 1, GameMode.TRANSPARENT, GameMode.TRANSPARENT));

            actors.add(offeringTypesLabel);
            actors.add(acceptedTypesLabel);
            actors.add(resultLabel);

            //generuje tlacitka pro nabizene typy
            for (int i = 0; i < portCrossroad.getOfferingTypes().size(); i++) {
                ResourceType rt = portCrossroad.getOfferingTypes().get(i);
                String imagePath = ResourceType.getImagePathByResourceType(rt);
                if (!imagePath.isBlank()) {
                    int x = popupWindowCenterX - PopupWindowActor.POPUP_WINDOW_WIDTH / 3;
                    int y = popupWindowCenterY + (bothColumnYStartOffset + verticalDifferenceBetweenActors) + i * verticalDifferenceBetweenActors;
                    actors.add(new ButtonActor(imagePath, new ButtonActor.GreenfootButtonOnClickListener() {
                        @Override
                        public void onClick(GameWorld gameWorld) {
                            String resourceTypeString = ResourceType.getResourceTypeString(rt);
                            offeringTypesLabel.setImage(new GreenfootImage("Nakup: " + resourceTypeString, 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));
                            player.setBuyingBuffer(rt);
                        }
                    }, x, y));
                }
            }
            //zobrazuje tlacitka pro typy k platbe
            for (int i = 0; i < portCrossroad.getAcceptedTypes().size(); i++) {
                ResourceType rt = portCrossroad.getAcceptedTypes().get(i);
                String imagePath = ResourceType.getImagePathByResourceType(rt);
                if (!imagePath.isBlank()) {
                    int x = popupWindowCenterX + PopupWindowActor.POPUP_WINDOW_WIDTH / 3;
                    int y = popupWindowCenterY + (bothColumnYStartOffset + verticalDifferenceBetweenActors) + i * verticalDifferenceBetweenActors;
                    actors.add(new ButtonActor(imagePath, new ButtonActor.GreenfootButtonOnClickListener() {
                        @Override
                        public void onClick(GameWorld gameWorld) {
                            String resourceTypeString = ResourceType.getResourceTypeString(rt);
                            acceptedTypesLabel.setImage(new GreenfootImage("Prodej: " + resourceTypeString, 30, GameMode.GAME_GREY, GameMode.TRANSPARENT));
                            player.setSellingBuffer(rt);
                        }
                    }, x, y));
                }
            }

            actors.add(new ButtonActor("images/buttons/confirmButton.png", new ButtonActor.GreenfootButtonOnClickListener() {
                @Override
                public void onClick(GameWorld gameWorld) {
                    if (player.getBuyingBuffer() != null && player.getSellingBuffer() != null) {
                        boolean result = portCrossroad.tradeWithPlayer(player, player.getBuyingBuffer(), player.getSellingBuffer());
                        if (result) {
                            resultLabel.setImage(new GreenfootImage("Obchod uspesne uzavren", 30, Color.GREEN, GameMode.TRANSPARENT));
                        } else {
                            resultLabel.setImage(new GreenfootImage("Obchod nelze provest", 30, Color.RED, GameMode.TRANSPARENT));
                        }
                    }
                }
            }, popupWindowCenterX, popupWindowCenterY + 2 * PopupWindowActor.LABEL_OFFSET_UP / 3));

            showPopupWindow("Obchod s pristavem " + portCrossroad.getRequiredNumberOfCards() + ":1", actors, true);
        }
    }
}

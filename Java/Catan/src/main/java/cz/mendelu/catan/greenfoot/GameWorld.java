package cz.mendelu.catan.greenfoot;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.*;
import cz.mendelu.catan.greenfoot.helpers.PopupWindowGenerator;
import cz.mendelu.catan.iooperations.CatanFileHandler;
import cz.mendelu.catan.player.Player;
import greenfoot.World;

import java.util.List;

public class GameWorld extends World {

    GameMode currentGameMode;


    public GameWorld(){
        this(new GameModeMenu());
    }

    public GameWorld(GameMode gameMode) {
        super(GameMode.GAME_WIDTH, GameMode.GAME_HEIGHT, 1);
        this.currentGameMode = gameMode;
        setBackground(gameMode.getBackgroundPath());
        addAllActors(this.currentGameMode.getAllActors());

    }

    @Override
    public void act() {
        super.act();
        update();
    }

    private void update() {
        currentGameMode.update(null);
        if (getCurrentGameMode() instanceof GameModePlaying) checkIfGameWon();
    }

    public void switchGameMode(GameMode gameMode){
        if (gameMode != null){
            removeAllActors(this.currentGameMode.getAllActors());
            this.currentGameMode = gameMode;
            setBackground(this.currentGameMode.getBackgroundPath());
            addAllActors(this.currentGameMode.getAllActors());
        }
    }

    private void addAllActors(List<ActorWithAccesibleGameWorld> actors){
        for (var actor : actors){
            addObject(actor, actor.getHorizontalCoordinate(), actor.getVerticalCoordinate());
        }
    }

    private void removeAllActors(List<ActorWithAccesibleGameWorld> actors){
        for (var actor : actors){
            removeObject(actor);
        }
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }





    //NASLEDUJICI METODY JSOU URCENY POUZE PRO GAMEMODEPLAYING. RIZIKO JEJICH PROVOLAVANI V JINYCH GAMEMODECH JE OSETRENO

    public GameWorldState getState() {
        if (this.currentGameMode instanceof GameModePlaying){
            return ((GameModePlaying)currentGameMode).getGameState();
        } else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }

    /**
     * Metoda slouží jako kontrola u akci-provádějících aktorů pro multiplayerovou hru - pokud aktuální hráč není na tahu,nemůže provést akci.
     * Pokud se nejedná o multiplayerovou hru, vrací vždy true - oprávnění ovládacích prvků se z důvodů pouze jednoho interaktivního okna nějak neřeší.
     *
     * @return hráč může/nemůže provést akci
     * @version etapa 5
     */
    public boolean playerPolicyAllowsProceedingAction() {
        if (this.currentGameMode instanceof GameModePlaying){
            GameModePlaying gameModePlaying = (GameModePlaying) getCurrentGameMode();
            if (gameModePlaying.isThisGameOnline()){
                if (gameModePlaying.isCurrentPlayersTurn()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return true;
            }
        } else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }

    public Player getReferencePlayer() {
        if (this.currentGameMode instanceof GameModePlaying){
            GameModePlaying gameModePlaying = (GameModePlaying) getCurrentGameMode();
            return gameModePlaying.getReferencePlayer();
        } else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }

    public void setState(GameWorldState newState) {
        if (this.currentGameMode instanceof GameModePlaying){
            ((GameModePlaying)currentGameMode).setGameState(newState);
        } else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }

    public Game getGame() {
        if (this.currentGameMode instanceof GameModePlaying){
            return ((GameModePlaying)currentGameMode).getGameInstance();
        } else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }

    private void checkIfGameWon(){
        if (this.currentGameMode instanceof GameModePlaying) {
            if (getGame().getVictoriousPlayer() != null) {
                if (!getState().equals(GameWorldState.GAME_WON)) {
                    setState(GameWorldState.GAME_WON);
                    new PopupWindowGenerator(this).victoryPopupWindow(getGame().getVictoriousPlayer());
                    CatanFileHandler handler = new CatanFileHandler();
                    handler.saveGameStats(this.getGame());
                }
            }
        }else{
            throw new IllegalStateException("GameModePlaying dependent method is being called, but current GameMode is not GameModePlaying");
        }
    }
}

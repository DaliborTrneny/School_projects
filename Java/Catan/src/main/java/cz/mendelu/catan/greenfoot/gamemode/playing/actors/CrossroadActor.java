package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.gamingboard.Crossroad;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import greenfoot.Greenfoot;

import java.awt.*;

/**
 * Reprezentace křižovatky v GUI hry
 *
 * @author xmusil5
 * @version etapa 4
 */
public class CrossroadActor extends ActorWithAccesibleGameWorld {
    private static final String PLAYER_SETTLEMENT_BACKGROUND_PREFIX = "images/gameSets/";
    private static final String PLAYER_VILLAGE_BACKGROUND_SUFFIX = "/village.png";
    private static final String PLAYER_CITY_BACKGROUND_SUFFIX = "/city.png";
    protected Crossroad crossroad;

    public CrossroadActor(Crossroad crossroad, int x, int y){
        super(x,y);
        this.crossroad = crossroad;
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            Player currentPlayer = getGameWorld().getGame().getCurrentlyPlayingPlayer();
            switch (getGameWorld().getState()){
                case BUILDING_VILLAGE:
                    this.crossroad.buildVillage(currentPlayer);
                    getGameWorld().setState(GameWorldState.RUNNING);
                    break;
                case BUILDING_CITY:
                    this.crossroad.upgradeVillageToCity(currentPlayer);
                    getGameWorld().setState(GameWorldState.RUNNING);
                    break;
                case STARTUP_BUILD_VILLAGE:
                    boolean giveResources = getGameWorld().getGame().getFullRoundCounter() == Game.NUMBER_OF_STARTING_ROUNDS;
                    if(this.crossroad.buildVillageOnStartup(getGameWorld().getGame().getCurrentlyPlayingPlayer(), giveResources)) {
                        getGameWorld().setState(GameWorldState.STARTUP_BUILD_ROAD);
                    }
                    break;
            }
        }
        update();
    }

    protected void update() {
        this.crossroad = getGameWorld().getGame().getGamingBoard().getCrossroadByCoordinate(this.crossroad.getCoordinate());
        if (this.crossroad.getPlayerSettlement() == null){
            this.setImage("images/actors/readyToBuildCrossroad.png");
        } else if (this.crossroad.getPlayerSettlement() != null){
            Player currentPlayer = this.crossroad.getPlayerSettlement().getOwner();
            setSettlementBackgroundPlayerColor(currentPlayer.getColor());
        }
    }

    protected void setSettlementBackgroundPlayerColor(Color color){
        if (color != null) {
            String folder = "";
            String settlement = "";
            if (Color.RED.equals(color)) {
                folder = "redPlayer";
            } else if (Color.GREEN.equals(color)) {
                folder = "greenPlayer";
            } else if (Color.BLUE.equals(color)) {
                folder = "bluePlayer";
            } else if (Color.PINK.equals(color)) {
                folder = "pinkPlayer";
            } else {
                throw new IllegalArgumentException(color.toString() + "is not a supported player color");
            }
            if (this.crossroad.getPlayerSettlement() instanceof City){
                settlement = PLAYER_CITY_BACKGROUND_SUFFIX;
            } else{
                settlement = PLAYER_VILLAGE_BACKGROUND_SUFFIX;
            }
            this.setImage(PLAYER_SETTLEMENT_BACKGROUND_PREFIX + folder + settlement);
        } else {
            throw new NullPointerException("Color can not be null");
        }
    }

}

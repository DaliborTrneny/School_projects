package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.PortCrossroad;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import cz.mendelu.catan.greenfoot.helpers.PopupWindowActor;
import cz.mendelu.catan.greenfoot.helpers.PopupWindowGenerator;
import cz.mendelu.catan.player.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentace přístavu v GUI hry
 * @author xmusil5
 * @version etapa 4
 */
public class PortCrossroadActor extends CrossroadActor {

    public PortCrossroadActor(PortCrossroad portCrossroad, int x, int y){
        super(portCrossroad, x, y);
        this.setImage("images/actors/readyToBuildCrossroad.png");
    }

    @Override
    public void act() {
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
                case TRADING_WITH_PORT:
                    if (this.crossroad instanceof PortCrossroad){
                        PopupWindowGenerator pwg = new PopupWindowGenerator(getGameWorld());
                        pwg.tradeWithPortPopupWindow((PortCrossroad) this.crossroad);
                    }
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
}

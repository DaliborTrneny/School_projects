package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.player.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.*;

/**
 * @author xtrneny1
 * @version etapa 4
 */
public class PlayerActor extends ActorWithAccesibleGameWorld {
    private Player player;

    public PlayerActor(Player player, int x, int y){
        super(x,y);
        this.player = player;
    }


    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case TRADING_WITH_PLAYER:
                    /*
                    getGameWorld().getGame().getCurrentlyPlayingPlayer().tradeWithAnotherPlayer(this.player);
                    getGameWorld().setState(GameWorldState.RUNNING);
                     */
                    break;
                case STEALING_RESOURCE_CARDS:
                    if (getGameWorld().getGame().getGamingBoard().getTileWithBandit().hasPlayerSettlementOf(this.player)){
                        if(getGameWorld().getGame().getCurrentlyPlayingPlayer().stealResourceCards(this.player, 1)){
                            getGameWorld().setState(GameWorldState.RUNNING);
                            CatanLogger.getCatanLogger().addLog("Hrac " + this.player.getName() + " byl oloupen");
                        }
                    } else { CatanLogger.getCatanLogger().addLog("Kartu muzes ukrast jen hracum, kteri maji osadu na poli s lupicem"); }
            }
        }
    }

    private void update(){
        this.player = getGameWorld().getGame().getPlayerByName(this.player.getName());
        this.setImage(new GreenfootImage(player.getName() + " " + player.getScore() + " / " + Game.NUMBER_OF_POINTS_FOR_VICTORY,
                28, this.player.getColor(), new Color(0, 0, 0, 0)));
    }
}

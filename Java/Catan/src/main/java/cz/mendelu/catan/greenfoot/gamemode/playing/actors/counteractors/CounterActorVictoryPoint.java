package cz.mendelu.catan.greenfoot.gamemode.playing.actors.counteractors;

import cz.mendelu.catan.cards.actioncards.VictoryPoint;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;

/**
 * Počítadlo karet výherní bod
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class CounterActorVictoryPoint extends CounterActor {

    private int counterGame;

    public CounterActorVictoryPoint(Player player, int size, Color fontColor, Color backgroundColor, int x, int y) {
        super(player, size, fontColor, backgroundColor, x, y);
        this.setImage(new GreenfootImage(Integer.toString(counterGame), size, fontColor, backgroundColor));
    }

    @Override
    public void act() {
        update();
        if (counterGame != getGameWorld().getReferencePlayer().countActionCardsByClass(VictoryPoint.class)) {
            counterGame = getGameWorld().getReferencePlayer().countActionCardsByClass(VictoryPoint.class);
            updateCounter(counterGame);
        }
    }
}

package cz.mendelu.catan.greenfoot.gamemode.playing.actors.counteractors;

import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;

/**
 * Počítadlo karet vynálezu
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class CounterActorInvention extends CounterActor {

    private int counterGame;

    public CounterActorInvention(Player player, int size, Color fontColor, Color backgroundColor, int x, int y) {
        super(player, size, fontColor, backgroundColor, x, y);
        this.setImage(new GreenfootImage(Integer.toString(counterGame), size, fontColor, backgroundColor));
    }

    @Override
    public void act() {
        update();
        if (counterGame != getGameWorld().getReferencePlayer().countActionCardsByClass(Invention.class)) {
            counterGame = getGameWorld().getReferencePlayer().countActionCardsByClass(Invention.class);
            updateCounter(counterGame);
        }
    }
}

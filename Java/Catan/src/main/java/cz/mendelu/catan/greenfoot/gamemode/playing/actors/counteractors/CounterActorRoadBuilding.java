package cz.mendelu.catan.greenfoot.gamemode.playing.actors.counteractors;

import cz.mendelu.catan.cards.actioncards.RoadBuilding;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;

/**
 * Počítadlo karet stavba cest
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class CounterActorRoadBuilding extends CounterActor {

    private int counterGame;

    public CounterActorRoadBuilding(Player player, int size, Color fontColor, Color backgroundColor, int x, int y) {
        super(player, size, fontColor, backgroundColor, x, y);
        this.setImage(new GreenfootImage(Integer.toString(counterGame), size, fontColor, backgroundColor));
    }

    @Override
    public void act() {
        update();
        if (counterGame != getGameWorld().getReferencePlayer().countActionCardsByClass(RoadBuilding.class)) {
            counterGame = getGameWorld().getReferencePlayer().countActionCardsByClass(RoadBuilding.class);
            updateCounter(counterGame);
        }
    }
}
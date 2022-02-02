package cz.mendelu.catan.greenfoot.gamemode.playing.actors.counteractors;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.player.Player;
import greenfoot.GreenfootImage;

import java.awt.*;

public abstract class CounterActor extends ActorWithAccesibleGameWorld {
    Player player;
    int size;
    Color fontColor;
    Color backgroundColor;

    public CounterActor(Player player, int size, Color fontColor, Color backgroundColor, int x, int y) {
        super(x,y);
        this.player = player;
        this.size = size;
        this.fontColor = fontColor;
        this.backgroundColor = backgroundColor;
    }

    public void updateCounter (int num) {
        setImage(new GreenfootImage(Integer.toString(num), size, fontColor, backgroundColor));
    }

    public void update(){
        this.player = getGameWorld().getGame().getPlayerByName(this.player.getName());
    }

}

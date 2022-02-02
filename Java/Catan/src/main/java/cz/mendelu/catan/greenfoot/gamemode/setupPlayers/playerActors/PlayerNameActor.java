package cz.mendelu.catan.greenfoot.gamemode.setupPlayers.playerActors;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.iooperations.CatanFileHandler;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.*;
import java.util.List;

public class PlayerNameActor extends ActorWithAccesibleGameWorld {
    private String playerName;

    public PlayerNameActor(String name, int x, int y) {
        super(x, y);
        this.playerName = name;

        setImage(new GreenfootImage(playerName, 42, new Color(246, 246, 246), new Color(0, 0, 0, 0)));
    }

    public void update () {
        setImage(new GreenfootImage(playerName, 42, new Color(246, 246, 246), new Color(0, 0, 0, 0)));
    }

    public String getPlayerName () { return this.playerName; }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            playerName = Greenfoot.ask("Type here player name: (max. 11 letters)");
            if (playerName.length() > 11) {
                playerName = "Player name";
                playerName = Greenfoot.ask("Try it once more: (max. 11 letters)");
                while (playerName.length() > 11) {
                    playerName = Greenfoot.ask("Try it once more: (max. 11 letters)");
                }
            }

            update();
        }
    }
}

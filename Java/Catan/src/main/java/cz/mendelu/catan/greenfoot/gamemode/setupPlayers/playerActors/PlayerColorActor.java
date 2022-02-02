package cz.mendelu.catan.greenfoot.gamemode.setupPlayers.playerActors;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.*;

public class PlayerColorActor extends ActorWithAccesibleGameWorld {
    private Color playerColor;

    public PlayerColorActor(Color color, int x, int y) {
        super(x, y);
        this.playerColor = color;

        setImage(new GreenfootImage(setImageByColor()));
    }

    public Color getPlayerColor () { return this.playerColor; }

    public String setImageByColor () {
        String path;
        if (playerColor == Color.RED) {
            path = "images/menu/menuButtons/colorSetIndicators/redColorIndicator.png";
        } else if (playerColor == Color.BLUE) {
            path = "images/menu/menuButtons/colorSetIndicators/blueColorIndicator.png";
        } else if (playerColor == Color.PINK) {
            path = "images/menu/menuButtons/colorSetIndicators/pinkColorIndicator.png";
        } else {
            path = "images/menu/menuButtons/colorSetIndicators/greenColorIndicator.png";
        }

        return path;
    }
}

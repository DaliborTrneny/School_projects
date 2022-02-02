package cz.mendelu.catan.greenfoot.gamemode.selectingSave.saveactor;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.iooperations.CatanFileHandler;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class saveActor extends ActorWithAccesibleGameWorld {
    String saveName;
    CatanFileHandler catanFileHandler;

    public saveActor (String saveName, int x, int y) {
        super(x, y);
        this.saveName = saveName;

        this.setImage(new GreenfootImage(saveName, 25, new Color(246, 246, 246), new Color(0, 0, 0, 0)));
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            catanFileHandler = new CatanFileHandler();
            List<Object> gameSettings = catanFileHandler.loadGame(saveName);

            getGameWorld().switchGameMode(new GameModePlaying((Game) gameSettings.get(0), (GameWorldState) gameSettings.get(1)));
        }
    }
}

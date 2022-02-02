package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import greenfoot.GreenfootImage;

import java.awt.*;

public class LogActor extends ActorWithAccesibleGameWorld {
    private static final int TTL = 190;
    private static final int TEXT_SIZE = 30;
    private static final Color TEXT_COLOR = Color.ORANGE;

    String lastLog;
    int timeCountDown;
    boolean isShowing;

    public LogActor(int x, int y) {
        super(x, y);
        lastLog = "";
        timeCountDown = TTL;
        isShowing = false;
        this.setImage(new GreenfootImage(lastLog, TEXT_SIZE, TEXT_COLOR, GameMode.TRANSPARENT));
    }

    @Override
    public void act() {
        super.act();
        update();
    }

    private void update() {
        String currentLog = CatanLogger.getCatanLogger().getLastLog();
        if (!currentLog.equals(lastLog)){
            isShowing = true;
            this.setImage(new GreenfootImage(currentLog, TEXT_SIZE, TEXT_COLOR, GameMode.TRANSPARENT));
            lastLog = currentLog;
            timeCountDown = TTL;
        }
        showLog();
    }

    private void showLog(){
        if (isShowing){
            if (timeCountDown>0){
                timeCountDown--;
            } else{
                isShowing = false;
                this.lastLog = "";
                CatanLogger.getCatanLogger().addLog(lastLog);
                this.setImage(new GreenfootImage("", TEXT_SIZE, TEXT_COLOR, GameMode.TRANSPARENT));
            }
        }
    }
}

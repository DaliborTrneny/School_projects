package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;

import java.util.List;

public class BanditActor extends ActorWithAccesibleGameWorld {
    private static final int BANDIT_Y_OFFSET = -40;

    private List<TileActor> tileActors;

    public BanditActor(List<TileActor> tileActors, int x, int y){
        super(x,y + BANDIT_Y_OFFSET);
        this.tileActors = tileActors;
        this.setImage("images/actors/tiles/bandit.png");
    }

    public void moveToAnotherTile(TileActor tileActor){
        int x = tileActor.getHorizontalCoordinate();
        int y = tileActor.getVerticalCoordinate() + BANDIT_Y_OFFSET;
        this.setLocation(x, y);
    }

    @Override
    public void act() {
        super.act();
        update();
    }

    private void update() {
        for (var tileActor : tileActors){
            if (tileActor.getTile().getHasBandit()){
                moveToAnotherTile(tileActor);
            }
        }
    }
}

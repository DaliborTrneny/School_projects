package cz.mendelu.catan.greenfoot.gamemode;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.PlayerActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    public static final Color GAME_WHITE = new Color(246, 246, 246);
    public static final Color GAME_GREY = new Color(77, 77, 77);
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final int GAME_WIDTH = 1808;
    public static final int GAME_HEIGHT = 1017;



    protected List<ActorWithAccesibleGameWorld> allActors;


    public abstract void update(Object data);

    public abstract String getBackgroundPath();

    public List<ActorWithAccesibleGameWorld> getAllActors() {
        return allActors;
    }

    public void addActors(List<ActorWithAccesibleGameWorld> actors) {
        this.allActors.addAll(actors);
    }

    public void removeActors(List<ActorWithAccesibleGameWorld> actors) {
        this.allActors.removeAll(actors);
    }

    public List<PlayerActor> getAllPlayerActors () {
        List<PlayerActor> output = new ArrayList<>();

        for (var actor : this.allActors){
            if (actor.getClass() == PlayerActor.class){
                output.add((PlayerActor) actor);
            }
        }

        return output;
    }
}

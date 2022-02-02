package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.gamingboard.Tile;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;

public class DiceActor extends ActorWithAccesibleGameWorld {
    DicesOnClickListener listener;

    public DiceActor(DicesOnClickListener listener, int x, int y) {
        super(x, y);
        this.listener = listener;
        this.setImage("images/game/dice.png");
    }

    public interface DicesOnClickListener{
        void onDicesRolled(int dicesRolledResult);
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()){
            switch (getGameWorld().getState()){
                case ROLLING_DICES:
                    int dicesRolledResult = getGameWorld().getGame().rollDices();
                    if (dicesRolledResult != 7){
                        listener.onDicesRolled(dicesRolledResult);
                        getGameWorld().setState(GameWorldState.RUNNING);
                    } else{
                        listener.onDicesRolled(dicesRolledResult);
                        getGameWorld().setState(GameWorldState.MOVING_BANDIT);
                    }
                    break;
            }
        }
    }
}

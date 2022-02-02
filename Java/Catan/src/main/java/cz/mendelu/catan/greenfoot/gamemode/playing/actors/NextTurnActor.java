package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;

public class NextTurnActor extends ActorWithAccesibleGameWorld {
    NextTurnOnClickListener listener;

    public NextTurnActor(NextTurnOnClickListener listener, int x, int y) {
        super(x, y);
        this.listener = listener;
        this.setImage("images/buttons/endTurnButton.png");
    }

    public interface NextTurnOnClickListener{
        void onNextTurn();
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()){
            switch (getGameWorld().getState()){
                case RUNNING:
                    getGameWorld().getGame().nextTurn();
                    getGameWorld().setState(GameWorldState.ROLLING_DICES);

                    listener.onNextTurn();
                    break;
                case STARTUP_NEXT_TURN:
                    getGameWorld().getGame().nextTurn();
                    if (getGameWorld().getGame().getFullRoundCounter() <= Game.NUMBER_OF_STARTING_ROUNDS){
                        getGameWorld().setState(GameWorldState.STARTUP_BUILD_VILLAGE);
                    } else {
                        getGameWorld().setState(GameWorldState.ROLLING_DICES);
                    }
                    listener.onNextTurn();
            }
        }
    }
}

package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Knight;
import cz.mendelu.catan.cards.actioncards.Monopol;
import cz.mendelu.catan.cards.actioncards.VictoryPoint;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;

/**
 * Reprezentace akční karty Vítezný bod, pomocí které bude moct být karta vyložena
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class VictoryPointCardDeckActor extends CardDeckActor{

    public VictoryPointCardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(actionCards, x, y);
        this.setImage(new GreenfootImage("images/actionCardImages/victoryPointCard.png"));
    }

    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case ACTION_CARD_SELECTING:
                    if (getVictoryPointCard() != null) {
                        ActionCard temp = getVictoryPointCard();
                        temp.proceedAction(getGameWorld().getGame().getCurrentlyPlayingPlayer(), null);
                        actionCards.remove(temp);
                        getGameWorld().setState(GameWorldState.RUNNING);
                    } else {
                        System.out.println("Magore, na to nemáš dost karet");
                    }
                    break;
            }
        }
    }

    public void update () {
        this.actionCards = getGameWorld().getReferencePlayer().getActionCardListByClass(VictoryPoint.class);
    }

    private ActionCard getVictoryPointCard(){
        if (this.actionCards != null && !this.actionCards.isEmpty()){
            if (this.actionCards.get(0) instanceof VictoryPoint){
                return actionCards.get(0);
            }
        }
        return null;
    }

    @Override
    public List<ActionCard> getCardsByType() {
        return null;
    }
}
package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.cards.actioncards.Knight;
import cz.mendelu.catan.cards.actioncards.VictoryPoint;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;

/**
 * Reprezentace akční karty Rytíř, pomocí které bude moct být karta vyložena
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class KnightCardDeckActor extends CardDeckActor{

    public KnightCardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(actionCards, x, y);
        this.setImage(new GreenfootImage("images/actionCardImages/knightCard.png"));
    }

    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case ACTION_CARD_SELECTING:
                    if (getKnightCard() != null) {
                        ActionCard temp = getKnightCard();
                        temp.proceedAction(getGameWorld().getGame().getCurrentlyPlayingPlayer(), null);
                        actionCards.remove(temp);
                        getGameWorld().getGame().getCardDeck().assignBiggestArmyCardToRightPlayer(getGameWorld().getGame().getPlayers());
                        getGameWorld().setState(GameWorldState.MOVING_BANDIT);
                    } else {
                        System.out.println("Magore, na to nemáš dost karet");
                    }
                    break;
            }
        }
    }

    public void update () {
        this.actionCards = getGameWorld().getReferencePlayer().getActionCardListByClass(Knight.class);
    }

    private ActionCard getKnightCard(){
        if (this.actionCards != null && !this.actionCards.isEmpty()){
            if (this.actionCards.get(0) instanceof Knight){
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
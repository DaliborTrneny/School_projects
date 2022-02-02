package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.ResourceTypeActor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

/**
 * Reprezentace akční karty Vynález, pomocí které bude moct být karta vyložena
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class InventionCardDeckActor extends CardDeckActor{
    private static final int FREE_RESOURCE_CARD_LIMIT = 2;

    private int freeResourceCardCounter;

    public InventionCardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(actionCards, x, y);
        this.setImage(new GreenfootImage("images/actionCardImages/inventionCard.png"));
        this.freeResourceCardCounter = 0;
    }

    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case ACTION_CARD_SELECTING:
                    if (getInventionCard() != null){
                        ActionCard inventionCard = getInventionCard();
                        inventionCard.proceedAction(getGameWorld().getGame().getCurrentlyPlayingPlayer(), null);
                        this.actionCards.remove(inventionCard);
                        getGameWorld().setState(GameWorldState.INVENTION_CARD_PLAYED);
                    }
                    break;
            }
        }
        if (getGameWorld().getState().equals(GameWorldState.INVENTION_CARD_PLAYED) && getGameWorld().playerPolicyAllowsProceedingAction()){
            gatherSelectedResourceTypes();
        }
    }

    public void update () {
        this.actionCards = getGameWorld().getReferencePlayer().getActionCardListByClass(Invention.class);
    }

    private void gatherSelectedResourceTypes() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo != null && mouseInfo.getActor() != null) {
            if (Greenfoot.mouseClicked(mouseInfo.getActor())) {
                if (mouseInfo.getActor() instanceof ResourceTypeActor) {
                    ResourceType type = ((ResourceTypeActor) mouseInfo.getActor()).getResourceType();
                    getGameWorld().getGame().getCardDeck().giveResourceCardsToPlayerByType(1, getGameWorld().getGame().getCurrentlyPlayingPlayer(), type);
                    freeResourceCardCounter ++;
                    if (freeResourceCardCounter == FREE_RESOURCE_CARD_LIMIT){
                        freeResourceCardCounter = 0;
                        getGameWorld().setState(GameWorldState.RUNNING);
                    }
                }
            }
        }
    }

    private ActionCard getInventionCard(){
        if (this.actionCards != null && !this.actionCards.isEmpty()){
            if (this.actionCards.get(0) instanceof Invention){
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

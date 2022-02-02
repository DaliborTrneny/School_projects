package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.Invention;
import cz.mendelu.catan.cards.actioncards.Monopol;
import cz.mendelu.catan.cards.actioncards.RoadBuilding;
import cz.mendelu.catan.cards.actioncards.containers.MonopolContainer;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.ResourceTypeActor;
import cz.mendelu.catan.player.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

/**
 * Reprezentace akční karty Monopol, pomocí které bude moct být karta vyložena
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class MonopolCardDeckActor extends CardDeckActor{

    public MonopolCardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(actionCards, x, y);
        this.setImage(new GreenfootImage("images/actionCardImages/monopolCard.png"));
    }

    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case ACTION_CARD_SELECTING:
                    if (getMonopolCard() != null) {
                        getGameWorld().setState(GameWorldState.MONOPOL_CARD_PLAYED);
                    }
                    break;
            }
        }
        if (getGameWorld().getState().equals(GameWorldState.MONOPOL_CARD_PLAYED)){
            selectResourceType();
        }
    }

    private ActionCard getMonopolCard(){
        if (this.actionCards != null && !this.actionCards.isEmpty()){
            if (this.actionCards.get(0) instanceof Monopol){
                return actionCards.get(0);
            }
        }
        return null;
    }

    public void update () {
        this.actionCards = getGameWorld().getReferencePlayer().getActionCardListByClass(Monopol.class);
    }

    private void selectResourceType() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo != null && mouseInfo.getActor() != null) {
            if (Greenfoot.mouseClicked(mouseInfo.getActor())) {
                if (mouseInfo.getActor() instanceof ResourceTypeActor) {
                    ResourceType type = ((ResourceTypeActor) mouseInfo.getActor()).getResourceType();
                    List<Player> players = getGameWorld().getGame().getPlayers();
                    ActionCard monopolCard = getMonopolCard();
                    monopolCard.proceedAction(getGameWorld().getGame().getCurrentlyPlayingPlayer(), new MonopolContainer(type, players));
                    this.actionCards.remove(monopolCard);
                    getGameWorld().setState(GameWorldState.RUNNING);
                }
            }
        }
    }

    @Override
    public List<ActionCard> getCardsByType() {
        return null;
    }
}

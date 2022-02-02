package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;

public class Invention extends DevelopmentCard{

    public Invention(Player owner) {
        super(owner);
    }

    @Override
    public void proceedAction(Player playerPlayingThisCard, Object object) {
        playerPlayingThisCard.playInventionCard(this);
    }

}

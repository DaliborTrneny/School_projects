package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.player.Player;

public class Knight extends ActionCard{

    public Knight(Player owner) {
        super(owner);
    }

    @Override
    public void proceedAction(Player playerPlayingThisCard, Object object) {
        if (playerPlayingThisCard != null){
            playerPlayingThisCard.playKnightCard(this);
        }
    }
}

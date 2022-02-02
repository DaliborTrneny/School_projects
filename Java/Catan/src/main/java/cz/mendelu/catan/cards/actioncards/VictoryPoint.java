package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.player.Player;

public class VictoryPoint extends ActionCard{


    public VictoryPoint(Player owner) {
        super(owner);
    }

    public void proceedAction (Player playerPlayingThisCard, Object object) {
        playerPlayingThisCard.playVictoryPointCard(this);
    };
}

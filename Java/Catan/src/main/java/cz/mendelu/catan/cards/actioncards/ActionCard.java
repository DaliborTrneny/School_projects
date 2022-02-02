package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.cards.Card;
import cz.mendelu.catan.player.Player;

public abstract class ActionCard extends Card {

    public ActionCard(Player owner) {
        super(owner);
    }

    public abstract void proceedAction (Player playerPlayingThisCard, Object object);
}

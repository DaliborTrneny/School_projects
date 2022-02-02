package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;

import java.util.List;

public abstract class CardDeckActor extends ActorWithAccesibleGameWorld {
    List<ActionCard> actionCards;

    public CardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(x, y);
        this.actionCards = actionCards;
    }

    public abstract List<ActionCard> getCardsByType();

    public ActionCard getRandomActionCard () {
        List<ActionCard> actionCards = getCardsByType();
        return actionCards.get(0);
    }
}

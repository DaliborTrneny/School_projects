package cz.mendelu.catan.cards.actioncards;

import cz.mendelu.catan.gamingboard.Path;
import cz.mendelu.catan.player.Player;

import java.util.List;

public class RoadBuilding extends DevelopmentCard{

    public RoadBuilding(Player owner) {
        super(owner);
    }

    @Override
    public void proceedAction(Player playerPlayingThisCard, Object object) {
        playerPlayingThisCard.playRoadBuildingCard(this);
    }
}

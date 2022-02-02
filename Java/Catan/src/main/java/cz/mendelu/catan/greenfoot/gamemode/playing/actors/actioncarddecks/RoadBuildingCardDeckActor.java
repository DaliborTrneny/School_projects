package cz.mendelu.catan.greenfoot.gamemode.playing.actors.actioncarddecks;

import cz.mendelu.catan.cards.actioncards.ActionCard;
import cz.mendelu.catan.cards.actioncards.RoadBuilding;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.gamingboard.Path;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.PathActor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

/**
 * Reprezentace akční karty Stavba silnic, pomocí které bude moct být karta vyložena
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class RoadBuildingCardDeckActor extends CardDeckActor{
    private static final int ROADS_BUILT_FOR_FREE = 2;

    int pathsBuildLimit;

    public RoadBuildingCardDeckActor(List<ActionCard> actionCards, int x, int y) {
        super(actionCards, x, y);
        this.setImage(new GreenfootImage("images/actionCardImages/roadBuildingCard.png"));
        pathsBuildLimit = 0;
    }

    @Override
    public void act() {
        super.act();
        update();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case ACTION_CARD_SELECTING:
                    if (getRoadBuildingCard() != null){
                        if (getGameWorld().getGame().getCurrentlyPlayingPlayer().getNumberOfRoadPieces() >= ROADS_BUILT_FOR_FREE) {
                            ActionCard roadBuildingCard = getRoadBuildingCard();
                            roadBuildingCard.proceedAction(getGameWorld().getGame().getCurrentlyPlayingPlayer(), null);
                            this.actionCards.remove(roadBuildingCard);
                            getGameWorld().setState(GameWorldState.ROAD_BUILDING_CARD_PLAYED);
                        } else { CatanLogger.getCatanLogger().addLog("Pro zahrani karty staveni silnice nemas dostatek figurek"); }
                    }
                    break;
            }
        }
        if (getGameWorld().getState().equals(GameWorldState.ROAD_BUILDING_CARD_PLAYED)){
            gatherSelectedPaths();
        }
    }

    private ActionCard getRoadBuildingCard(){
        RoadBuilding card = null;
        if (this.actionCards != null && !this.actionCards.isEmpty()){
            if (this.actionCards.get(0) instanceof RoadBuilding){
                return actionCards.get(0);
            }
        }
        return card;
    }

    private void gatherSelectedPaths() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo != null && mouseInfo.getActor() != null) {
            if (Greenfoot.mouseClicked(mouseInfo.getActor())) {
                if (mouseInfo.getActor() instanceof PathActor) {
                    PathActor actor = ((PathActor) mouseInfo.getActor());
                    Path p = actor.getPath();
                    if (p.buildRoadForFree(getGameWorld().getGame().getCurrentlyPlayingPlayer())) {
                        pathsBuildLimit++;
                    }
                    if (pathsBuildLimit == ROADS_BUILT_FOR_FREE){
                        pathsBuildLimit = 0;
                        getGameWorld().getGame().getCardDeck().assignLongestRoadCardToRightPlayer(getGameWorld().getGame().getPlayers(), getGameWorld().getGame().getGamingBoard());
                        getGameWorld().setState(GameWorldState.RUNNING);
                    }
                }
            }
        }
    }


    public void update () {
        this.actionCards = getGameWorld().getReferencePlayer().getActionCardListByClass(RoadBuilding.class);
    }

    @Override
    public List<ActionCard> getCardsByType() {
        return null;
    }
}
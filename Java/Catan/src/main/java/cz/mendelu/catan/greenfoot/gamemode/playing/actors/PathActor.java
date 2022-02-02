package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.gamingboard.Path;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;

import java.awt.*;

/**
 *
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class PathActor extends ActorWithAccesibleGameWorld {
    private static final String PLAYER_ROAD_BACKGROUND_PREFIX = "images/gameSets/";
    private static final String PLAYER_ROAD_BACKGROUND_SUFFIX = "/road.png";
    private Path path;
    private int rotation;

    public PathActor(Path path, int x, int y, int rotation) {
        super(x,y);
        this.path = path;
        this.setRotation(rotation);
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case BUILDING_ROAD:
                    if(this.path.buildRoad(getGameWorld().getGame().getCurrentlyPlayingPlayer())){
                        getGameWorld().getGame().getCardDeck().assignLongestRoadCardToRightPlayer(getGameWorld().getGame().getPlayers(), getGameWorld().getGame().getGamingBoard());
                    }
                    getGameWorld().setState(GameWorldState.RUNNING);
                    break;
                case STARTUP_BUILD_ROAD:
                    if(this.path.buildRoadForFree(getGameWorld().getGame().getCurrentlyPlayingPlayer())) {
                        getGameWorld().setState(GameWorldState.STARTUP_NEXT_TURN);
                    }
                    break;
            }
        }
        update();
    }

    private void setRoadBackgroundByPlayerColor(Color color){
        if (color != null) {
            String folder = "";
            if (Color.RED.equals(color)) {
                folder = "redPlayer";
            } else if (Color.GREEN.equals(color)) {
                folder = "greenPlayer";
            } else if (Color.BLUE.equals(color)) {
                folder = "bluePlayer";
            } else if (Color.PINK.equals(color)) {
                folder = "pinkPlayer";
            } else {
                throw new IllegalArgumentException(color.toString() + "is not a supported player color");
            }
            this.setImage(PLAYER_ROAD_BACKGROUND_PREFIX + folder + PLAYER_ROAD_BACKGROUND_SUFFIX);
        } else {
            throw new NullPointerException("Color can not be null");
        }
    }


    private void update() {
        this.path = getGameWorld().getGame().getGamingBoard().getPathByCoordinate(this.path.getCoordinate());
        if (this.path.getPlayerRoad() == null){
            this.setImage("images/actors/readyToBuildPath.png");

            /*if (this.rotation == 0) {
                this.setImage("images/actors/readyToBuildPath.png");
            } else if (this.rotation < 0) {
                this.setImage("images/actors/readyToBuildPathRight.png");
            } else {
                this.setImage("images/actors/readyToBuildPathLeft.png");
            }*/

        } else if (this.path.getPlayerRoad() != null){
            setRoadBackgroundByPlayerColor(this.path.getPlayerRoad().getOwner().getColor());
        }
    }

    public Path getPath() {
        return path;
    }
}

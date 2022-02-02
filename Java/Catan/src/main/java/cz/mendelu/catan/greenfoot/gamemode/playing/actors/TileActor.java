package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.gamingboard.Tile;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import greenfoot.Greenfoot;

/**
 * Reprezentace hexagonu v GUI hry
 *
 * @author xmusil5
 * @version etapa 4
 */
public class TileActor extends ActorWithAccesibleGameWorld {
    private Tile tile;
    private TileActorOnClickListener listener;

    public TileActor(Tile tile, TileActorOnClickListener listener, int x, int y){
        super(x,y);
        this.tile = tile;
        this.listener = listener;
        setTileBackground();
    }

    public interface TileActorOnClickListener{
        void onBanditLocationSet(TileActor tileActor);
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()){
            switch (getGameWorld().getState()){
                case MOVING_BANDIT:
                    if (getGameWorld().getGame().getCurrentlyPlayingPlayer().moveBandit(this.tile)){
                        if (this.tile.hasSettlementsOfOtherPlayers(getGameWorld().getGame().getCurrentlyPlayingPlayer())){
                            getGameWorld().setState(GameWorldState.STEALING_RESOURCE_CARDS);
                        } else{
                            getGameWorld().setState(GameWorldState.RUNNING);
                        }
                    }
                    break;
            }
        }
        if (this.tile.getHasBandit()){
            listener.onBanditLocationSet(this);
        }
        updateTileFromGame();
    }

    private void updateTileFromGame(){
        this.tile = getGameWorld().getGame().getGamingBoard().getTileByCoordinate(this.tile.getCoordinate());
    }



    /**
     * Metoda nastaví obrázek hexagonu dle typu suroviny, kterou poskytuje
     * @author xmusil5
     * @version etapa 4
     */
    private void setTileBackground(){
        switch (this.tile.getResourceType()){
            case LUMBER:{
                setImage("images/actors/tiles/lumberTile.png");
                break;
            }
            case WOOL:{
                setImage("images/actors/tiles/woolTile.png");
                break;
            }
            case ORE:{
                setImage("images/actors/tiles/oreTile.png");
                break;
            }
            case GRAIN:{
                setImage("images/actors/tiles/grainTile.png");
                break;
            }
            case BRICK:{
                setImage("images/actors/tiles/brickTile.png");
                break;
            }
            case EMPTY:{
                setImage("images/actors/tiles/emptyTile.png");
                break;
            }
        }
    }

    public Tile getTile() {
        return tile;
    }
}

package cz.mendelu.catan.player.pieces;

import cz.mendelu.catan.player.Player;

import java.io.Serializable;

public abstract class Settlement implements Serializable {
    protected Player owner;
    protected int resourceCardsGenerated;

    public Player getOwner() {
        return this.owner;
    }

    public int getResourceCardsGenerated() {
        return resourceCardsGenerated;
    }

    public void setOwner(Player owner){
        this.owner=owner;
    }
}

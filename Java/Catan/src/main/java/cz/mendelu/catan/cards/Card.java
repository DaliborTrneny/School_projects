package cz.mendelu.catan.cards;

import cz.mendelu.catan.player.Player;

import java.io.Serializable;

public abstract class Card implements Serializable {
    protected Player owner;

    public Card(Player owner){
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

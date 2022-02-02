package cz.mendelu.catan.player.pieces;

import cz.mendelu.catan.player.Player;

import java.io.Serializable;

public class Road implements Serializable {
    protected Player owner;

    public Road(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}

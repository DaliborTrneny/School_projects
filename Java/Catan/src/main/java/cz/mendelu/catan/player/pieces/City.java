package cz.mendelu.catan.player.pieces;

import cz.mendelu.catan.player.Player;

public class City extends Settlement{

    public City(Player owner) {
        this.owner = owner;
        this.resourceCardsGenerated = 2;
    }
}

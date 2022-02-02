package cz.mendelu.catan.player.pieces;

import cz.mendelu.catan.player.Player;

public class Village extends Settlement{

    public Village(Player owner) {
        this.owner = owner;
        this.resourceCardsGenerated = 1;
    }
}

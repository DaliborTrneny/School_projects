package cz.mendelu.catan.gamingboard;

import java.io.Serializable;

public enum TileTipDirection implements Serializable {
    NORTH,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST;

    public static TileTipDirection[] getAllDirections(){
        TileTipDirection[] directions = {TileTipDirection.NORTH, TileTipDirection.NORTH_EAST, TileTipDirection.SOUTH_EAST, TileTipDirection.SOUTH,
                TileTipDirection.SOUTH_WEST, TileTipDirection.NORTH_WEST};
        return directions;
    }
}


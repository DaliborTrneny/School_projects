package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.Road;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class Path implements Serializable {
    private int coordinate;
    private List<Crossroad> neighboringCrossroads;
    private Road playerRoad;
    protected CardDeck cardDeck;

    public Path(int coordinate, CardDeck cardDeck){
        this.coordinate = coordinate;
        this.cardDeck = cardDeck;
    }

    /**
     * Implementace equals a hashCode
     *
     * @author xtrneny1
     * @version etapa 3
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null ) { return false; }
        if (o instanceof Path) {
            Path test = (Path) o;
            if (test.coordinate == this.coordinate && test.neighboringCrossroads == this.neighboringCrossroads && test.playerRoad == this.playerRoad) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, neighboringCrossroads, playerRoad);
    }

    public List<Crossroad> getNeighboringCrossroads() {
        return neighboringCrossroads;
    }

    public void setNeighboringCrossroads(List<Crossroad> neighboringCrossroads) {
        if (neighboringCrossroads.size() <= 2) {
            this.neighboringCrossroads = neighboringCrossroads;
        } else {
            throw new IllegalArgumentException("Can not have more than two neighboring crossroads in one path");
        }
    }

    /**
     * Metoda postaví hráčovu silnici, pokud to pravidla hry umožnují.
     *
     * @author xtrneny1
     */
    public boolean buildRoad (Player builder) {
        if (builder != null) {
            if (builder.hasResourceCards(ResourceType.getResourceTypesForBuildingRoad())) {
                if (this.getPlayerRoad() == null) {
                    if (builder.getNumberOfRoadPieces() > 0) {
                        if (roadBuildable(builder)) {

                            this.cardDeck.addResourceCards(builder.surrenderResourceCardsByTypes((ResourceType.getResourceTypesForBuildingRoad())));
                            Road road = builder.getRoadPiece();
                            this.playerRoad = road;
                            builder.removeRoadPiece(road);
                            return true;

                        } else {
                            Logger.getGlobal().warning("buildRoad - path doesn't have necessary connections");
                            CatanLogger.getCatanLogger().addLog("Na teto ceste nemuzes postavit silnici, viz herni pravidla");
                        }
                    } else {
                        Logger.getGlobal().warning("buildRoad - builder doesn't have enough road pieces");
                        CatanLogger.getCatanLogger().addLog("Nemas uz zadne figurky silnice");
                    }
                } else {
                    Logger.getGlobal().warning("buildRoad - path is already occupied");
                    CatanLogger.getCatanLogger().addLog("Na teto ceste uz byla silnice postavena");
                }
            } else {
                Logger.getGlobal().warning("buildRoad - builder does not have enough resources");
                CatanLogger.getCatanLogger().addLog("Nemas dostatek surovin pro stavbu silnice");
            }
        } else { Logger.getGlobal().warning("buildRoad - null parameter"); }
        return false;
    }

    public Crossroad getCrossroadOnSecondEnd(Crossroad crossroad){
        for (var c : this.neighboringCrossroads){
            if (!c.equals(crossroad)){
                return c;
            }
        }
        return null;
    }

    /**
     * Metoda postaví hráčovu silnici bez nákladů (pro začátek hry a zahrání karty stavění cesty).
     *
     * @version etapa 5
     */
    public boolean buildRoadForFree(Player builder) {
        if (builder != null) {
            if (this.getPlayerRoad() == null) {
                if (builder.getNumberOfRoadPieces() > 0) {
                    if (roadBuildable(builder)) {

                        Road road = builder.getRoadPiece();
                        this.playerRoad = road;
                        builder.removeRoadPiece(road);
                        return true;

                    } else {
                        Logger.getGlobal().warning("buildRoad -game rules forbid building road here");
                        CatanLogger.getCatanLogger().addLog("Na teto ceste nemuzes postavit silnici, viz herni pravidla");
                    }
                } else {
                    Logger.getGlobal().warning("buildRoad - builder doesn't have enough road pieces");
                    CatanLogger.getCatanLogger().addLog("Nemas uz zadne figurky silnice");
                }
            } else {
                Logger.getGlobal().warning("buildRoad - path is already occupied");
                CatanLogger.getCatanLogger().addLog("Na teto ceste uz byla silnice postavena");
            }
        } else { Logger.getGlobal().warning("buildRoad - null parameter"); }
        return false;
    }

    /**
     * Metoda zkontroluje, jestli je možné na této cestě vybudovat silnici v souladu s herními pravidly.
     *
     * @param builder hráč, který chce silnici postavit
     * @return silnici lze/nelze postavit
     * @author xmuil5
     */
    public boolean roadBuildable(Player builder){
        boolean hasNoRoad = this.playerRoad == null;
        boolean noEnemySettlements = true;
        boolean pathOrSettlementConnected = false;
        for (var crossroad : this.neighboringCrossroads){
            if (crossroad.getPlayerSettlement() != null) {
                if (crossroad.getPlayerSettlement().getOwner().equals(builder)) {
                    pathOrSettlementConnected = true;
                } else {
                    noEnemySettlements = false;
                }
            }
            Set<Path>paths = crossroad.getNeighboringPaths();
            for (var path : paths){
                if (path.getPlayerRoad() != null){
                    if (path.getPlayerRoad().getOwner().equals(builder)){
                        pathOrSettlementConnected = true;
                    }
                }
            }
        }
        return noEnemySettlements && pathOrSettlementConnected && hasNoRoad;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public Road getPlayerRoad() {
        return playerRoad;
    }

    public void setPlayerRoad(Road playerRoad) {
        this.playerRoad = playerRoad;
    }


}

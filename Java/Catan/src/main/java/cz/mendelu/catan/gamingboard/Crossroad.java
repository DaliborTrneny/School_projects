package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Settlement;
import cz.mendelu.catan.player.pieces.Village;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class Crossroad implements Serializable {
    protected int coordinate;
    protected Settlement playerSettlement;
    protected GamingBoard gamingBoard;



    public Crossroad(int coordinate, Settlement playerSettlement, GamingBoard gamingBoard) {
        this.coordinate = coordinate;
        this.playerSettlement = playerSettlement;
        this.gamingBoard = gamingBoard;
    }

    public Crossroad(int coordinate, GamingBoard gamingBoard) {
        this.coordinate = coordinate;
        this.gamingBoard = gamingBoard;
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
        if (o == null) { return false; }
        if (o instanceof Crossroad) {
            Crossroad test = (Crossroad) o;
            if (test.coordinate == this.coordinate && test.playerSettlement == this.playerSettlement) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, playerSettlement);
    }

    /**
     * Implementace toString
     *
     * @author xtrneny1
     * @version etapa 3
     */
    @Override
    public String toString() {
        return "Crossroad{" +
                "coordinate=" + coordinate +
                ", playerSettlement=" + playerSettlement +
                '}';
    }

    public Settlement getPlayerSettlement() {
        return playerSettlement;
    }

    public void setPlayerSettlement (Settlement settlement) {
        this.playerSettlement = settlement;
    }

    /**
     * Metoda zjistí a vrátí cesty, které sousedí se touto křižovatkou
     *
     * @author xmusil5
     */
    public Set<Path> getNeighboringPaths(){
        Set<Path> neighboringPaths = new HashSet<>();
        for (var path : this.gamingBoard.getPaths()){
            for (var crossroad : path.getNeighboringCrossroads()){
                if (crossroad.equals(this)){
                    neighboringPaths.add(path);
                    break;
                }
            }
        }
        return neighboringPaths;
    }

    /**
     * Metoda zjistí a vrátí cesty, které sousedí se touto křižovatkou a mají na sobě cestu specifikovaného hráče
     *
     * @author xmusil5
     */
    public Set<Path> getNeighboringPathsWithPlayerRoad(Player player){
        Set<Path> neighboringPaths = new HashSet<>();
        for (var path : this.gamingBoard.getPaths()){
            if (path.getPlayerRoad() != null && path.getPlayerRoad().getOwner().equals(player)) {
                for (var crossroad : path.getNeighboringCrossroads()) {
                    if (crossroad.equals(this)) {
                        neighboringPaths.add(path);
                        break;
                    }
                }
            }
        }
        return neighboringPaths;
    }

    /**
     * Metoda zjistí a vrátí křižovatky, které sousedí se touto křižovatkou
     *
     * @version etapa 5
     */
    public Set<Crossroad> getNeighboringCrossroads(){
        Set<Crossroad> neighboringCrossroads = new HashSet<>();
        for (var path : this.getNeighboringPaths()){
            for (var crossroad : path.getNeighboringCrossroads()){
                if (!(crossroad.equals(this))){
                    neighboringCrossroads.add(crossroad);
                    break;
                }
            }
        }
        return neighboringCrossroads;
    }

    /**
     * Metoda pro povýšení vesnice na město.
     * Tato metoda povýší hráčovu vesnici na město. Figurku vesnice vrátí hráči pro další použití.
     *
     * @param owner je hráč, který provádí upgrade
     * @author xmusil5
     * @version etapa 2
     */
    public boolean upgradeVillageToCity(Player owner){
        if (owner != null) {
            if (owner.hasResourceCards(ResourceType.getResourceTypesForUpgradingVillage())) {
                if (this.playerSettlement != null) {
                    if (playerSettlement.getOwner() == owner) {
                        if (playerSettlement instanceof Village) {
                            if (owner.getNumberOfCityPieces() > 0) {

                                this.getCardDeck().addResourceCards(owner.surrenderResourceCardsByTypes(ResourceType.getResourceTypesForUpgradingVillage()));
                                owner.addVillagePiece((Village) this.playerSettlement);
                                City city = owner.getCityPiece();
                                this.playerSettlement = city;
                                owner.removeCityPiece(city);
                                return true;

                            } else {
                                Logger.getGlobal().warning("UpgradeVillageToCity - owner has no more city pieces");
                                CatanLogger.getCatanLogger().addLog("Nemas uz zadne figurky mesta");
                            }
                        }else {
                            Logger.getGlobal().warning("UpgradeVillageToCity - settlement is not a village");
                            CatanLogger.getCatanLogger().addLog("Mesto muzes postavit pouze z existujici vesnice");
                        }
                    }else {
                        Logger.getGlobal().warning("UpgradeVillageToCity - settlement belongs to another player");
                        CatanLogger.getCatanLogger().addLog("Tato vesnice ti nepatri");
                    }
                }else {
                    Logger.getGlobal().warning("UpgradeVillageToCity - crossroad has no settlement");
                    CatanLogger.getCatanLogger().addLog("Mesto muzes postavit pouze z existujici vesnice");
                }
            }else {
                Logger.getGlobal().warning("UpgradeVillageToCity - owner does not have enough resources");
                CatanLogger.getCatanLogger().addLog("Nemas dostatek surovin pro stavbu mesta");
            }
        }else {Logger.getGlobal().warning("UpgradeVillageToCity - null parameter");}
        return false;
    }

    /**
     * Metoda pro stavbu vesnice
     * Hráči je z dostupných figurek vesnic jedna odebrána a na určeném crossroadu je vybudována vesnice
     *
     * @param builder - hráč, který hodlá postavit vesnici
     * @author xtrneny1
     * @version etapa 2
     */
    public boolean buildVillage (Player builder) {
        if (builder != null) {
            if (builder.hasResourceCards(ResourceType.getResourceTypesForBuildingVillage())) {
                if (this.playerSettlement == null) {
                    if (builder.getNumberOfVillagePieces() > 0) {
                        if (this.villageBuildable(builder)) {

                            this.getCardDeck().addResourceCards(builder.surrenderResourceCardsByTypes((ResourceType.getResourceTypesForBuildingVillage())));
                            Village vill = builder.getVillagePiece();
                            this.playerSettlement = vill;
                            builder.removeVillagePiece(vill);
                            return true;

                        } else {
                            Logger.getGlobal().warning("buildVillage - game rules forbid building village here");
                            CatanLogger.getCatanLogger().addLog("Na teto krizovatce nemuzes postavit vesnici, viz herni pravidla");
                        }
                    } else {
                        Logger.getGlobal().warning("buildVillage - builder doesn't have enough village pieces");
                        CatanLogger.getCatanLogger().addLog("Nemas uz zadne figurky vesnice");
                    }
                } else {
                    Logger.getGlobal().warning("buildVillage - crossroad is already occupied");
                    CatanLogger.getCatanLogger().addLog("Tato krizovatka je jiz zastavena");
                }
            } else {
                Logger.getGlobal().warning("buildVillage - builder does not have enough resources");
                CatanLogger.getCatanLogger().addLog("Nemas dostatek surovin pro stavbu vesnice");
            }
        } else { Logger.getGlobal().warning("buildVillage - null parameter"); }
        return false;
    }

    /**
     * Metoda pro stavbu vesnice při počátečních kolech hry, kdy hráči staví bez nákladů.
     *
     * @param builder - hráč, který hodlá postavit vesnici
     * @version etapa 5
     */
    public boolean buildVillageOnStartup (Player builder, boolean giveResources) {
        if (builder != null) {
            if (this.playerSettlement == null) {
                if (builder.getNumberOfVillagePieces() > 0) {
                    if (!hasNeighboringSettlements()) {

                        Village vill = builder.getVillagePiece();
                        this.playerSettlement = vill;
                        builder.removeVillagePiece(vill);
                        if (giveResources) {
                            List<Tile> tiles = this.gamingBoard.getTilesContainingCrossroad(this);
                            for (var tile : tiles) {
                                ResourceType rt = tile.getResourceType();
                                this.gamingBoard.getCardDeck().giveResourceCardsToPlayerByType(1, builder, rt);
                            }
                        }
                        return true;

                    } else {
                        Logger.getGlobal().warning("buildVillageOnStartup - game rules forbid building village here");
                        CatanLogger.getCatanLogger().addLog("Na teto krizovatce nemuzes postavit vesnici, viz herni pravidla");
                    }
                } else {
                    Logger.getGlobal().warning("buildVillageOnStartup - builder doesn't have enough village pieces");
                    CatanLogger.getCatanLogger().addLog("Nemas uz zadne figurky vesnice");
                }
            } else {
                Logger.getGlobal().warning("buildVillageOnStartup - crossroad is already occupied");
                CatanLogger.getCatanLogger().addLog("Tato krizovatka je jiz zastavena");
            }
        } else { Logger.getGlobal().warning("buildVillageOnStartup - null parameter"); }
        return false;
    }

    /**
     * Metoda zkontroluje, jestli je možné na této křižovatce vybudovat novou vesnici v souladu s herními pravidly
     *
     * @param builder hráč, který chce vesnici postavit
     * @return vesnici lze/nelze postavit
     * @version etapa 5
     */
    private boolean villageBuildable(Player builder){
        return hasPlayerRoadConnected(builder) && !hasNeighboringSettlements();
    }

    /**
     * Metoda zjistí, jestli je k této křižovatce připojena silnice daného hráče.
     *
     * @param player hráč, jehož silnice se hledá
     * @return hráč má/nemá připojenou silnici
     * @version etapa 5
     */
    private boolean hasPlayerRoadConnected(Player player){
        boolean roadConnected = false;
        for (var path : this.getNeighboringPaths()){
            if (path.getPlayerRoad() != null){
                if (path.getPlayerRoad().getOwner().equals(player)){
                    roadConnected = true;
                    break;
                }
            }
        }
        return roadConnected;
    }

    /**
     * Metoda zjistí, jestli je alespon na jedné ze sousedících křižoatek vesnice/město
     *
     * @return alespon na jedné sousedicí křižovatce se nachází vesnice/město
     * @version etapa 5
     */
    private boolean hasNeighboringSettlements(){
        boolean hasNeighboringSettlement = false;
        for (var crossroad : this.getNeighboringCrossroads()){
            if (crossroad.getPlayerSettlement() != null){
                hasNeighboringSettlement = true;
                break;
            }
        }
        return hasNeighboringSettlement;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public CardDeck getCardDeck() {
        return gamingBoard.getCardDeck();
    }
}

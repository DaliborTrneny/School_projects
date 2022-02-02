package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Village;

import java.io.Serializable;
import java.util.*;

public class Tile implements Serializable {
    private int coordinate;
    private int token;
    private GamingBoard parentGamingBoard;
    /**
     * @author xmusil5
     * @version etapa 3
     */
    private Map<TileTipDirection, Crossroad> tipCrossroads;
    private boolean hasBandit;
    private ResourceType resourceType;

    public Tile(int coordinate, int token, GamingBoard parentGamingBoard, boolean hasBandit, ResourceType resourceType){
        this.coordinate = coordinate;
        this.token = token;
        this.parentGamingBoard = parentGamingBoard;
        this.tipCrossroads = tipCrossroads;
        this.hasBandit = hasBandit;
        this.resourceType = resourceType;
    }

    /**
     * Metoda vrací tvrzení, jestli je porovnávaný objekt rovný této instanci hexagonu.
     *
     * @param obj
     * @return objekt je/není rovný tomuto
     * @author xmusil5
     * @version etapa 3
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Tile){
            Tile tile = (Tile) obj;
            if (this.coordinate == tile.coordinate && this.token == tile.token && this.parentGamingBoard.equals(tile.parentGamingBoard) &&
                this.tipCrossroads.equals(tile.tipCrossroads) && this.hasBandit == tile.hasBandit && this.resourceType == tile.resourceType){
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda vrací hashovací kód instance
     *
     * @return hashovací kód instance
     * @author xmusil5
     * @version etapa 3
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinate, token, parentGamingBoard, tipCrossroads, hasBandit, resourceType);
    }

    /**
     * Metoda vrací řetězcovou reprezentaci tohoto hexagonu
     *
     * @return řetězcová reprezentace hexagonu
     * @author xmusil5
     * @version etapa 3
     */
    @Override
    public String toString() {
        /*
        String crossroads = "[ ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.NORTH).toString() + ", ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.NORTH_EAST).toString() + ", ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.SOUTH_EAST).toString() + ", ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.SOUTH).toString() + ", ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.SOUTH_WEST).toString() + ", ";
        crossroads+= this.tipCrossroads.get(TileTipDirection.NORTH_WEST).toString() + " ]";
        */
        return "Tile{" +
                "coordinate = " + coordinate +
                ", token = " + token +
                ", parentGamingBoard = " + parentGamingBoard +
                //", tipCrossroads = " + crossroads +
                ", hasBandit = " + hasBandit +
                ", resourceType = " + resourceType +
                '}';
    }

    /**
     * Metoda pro rozdání karet surovin hráčům.
     * Metoda rozdá karty surovin hráčům s městem či vesnicí sousedící s hexagonem.
     *
     * @author xmusil5
     * @version etapa 2
     */
    public void giveYield(){
        if (!hasBandit && resourceType!=ResourceType.EMPTY) {
            TileTipDirection[] directions = TileTipDirection.getAllDirections();
            for (var crossroadDirection : directions) {
                var crossroad = tipCrossroads.get(crossroadDirection);
                if (crossroad.getPlayerSettlement() != null) {
                    if (crossroad.getPlayerSettlement() instanceof Village){
                        parentGamingBoard.getCardDeck().giveResourceCardsToPlayerByType(1, crossroad.getPlayerSettlement().getOwner(), this.resourceType);
                    }
                    else if (crossroad.getPlayerSettlement() instanceof City){
                        parentGamingBoard.getCardDeck().giveResourceCardsToPlayerByType(2, crossroad.getPlayerSettlement().getOwner(), this.resourceType);
                    }
                }
            }
        }
    }

    /**
     * Metoda zjistí, jestli má hexagon na některé ze svých křižovatek vesnici/město, které nepatří hráči v parametru.
     *
     * @param player hráč, který metodu provolal
     * @return hexagon má/nemá na alespon jedné ze svých křižovatek vesnici/město jiného hráče
     * @version etapa 5
     */
    public boolean hasSettlementsOfOtherPlayers(Player player){
        TileTipDirection directions[] = TileTipDirection.getAllDirections();
        for (var direction : directions){
            Crossroad crossroad = this.tipCrossroads.get(direction);
            if (crossroad.getPlayerSettlement() != null){
                if (!crossroad.getPlayerSettlement().getOwner().equals(player)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metoda zjistí, jestli má hexagon na některé ze svých křižovatek vesnici/město specifikovaného hráče.
     *
     * @param player hráč, jehož vesnice/město se hledá
     * @return hexagon má/nemá na alespon jedné ze svých křižovatek vesnici/město specifikovaného hráče
     * @version etapa 5
     */
    public boolean hasPlayerSettlementOf(Player player){
        TileTipDirection directions[] = TileTipDirection.getAllDirections();
        for (var direction : directions){
            Crossroad crossroad = this.tipCrossroads.get(direction);
            if (crossroad.getPlayerSettlement() != null){
                if (crossroad.getPlayerSettlement().getOwner().equals(player)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsCrossroad(Crossroad crossroad){
        boolean result = (this.tipCrossroads.containsValue(crossroad)) ? true : false;
        return result;
    }

    public boolean getHasBandit() {
        return hasBandit;
    }

    public void setHasBandit(boolean hasBandit) {
        this.hasBandit = hasBandit;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setTipCrossroads(Map<TileTipDirection, Crossroad> tipCrossroads) {
        this.tipCrossroads = tipCrossroads;
    }

    public Crossroad getTipCrossroadByDirection(TileTipDirection direction){
       return this.tipCrossroads.get(direction);
    }

    public int getCoordinate() {
        return coordinate;
    }

    public int getToken() {
        return token;
    }
}

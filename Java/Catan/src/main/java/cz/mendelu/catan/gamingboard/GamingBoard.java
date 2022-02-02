package cz.mendelu.catan.gamingboard;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Settlement;
import cz.mendelu.catan.player.pieces.Village;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class GamingBoard implements Serializable {
    private List<Tile> tiles;
    private Set<Path> paths;
    /**
     * @author xmusil5
     * @version etapa 3
     */
    private Set<Crossroad> crossroads;
    private CardDeck cardDeck;

    public GamingBoard(){
        this.tiles = new ArrayList<>();
        this.paths = new HashSet<>();
        this.crossroads = new HashSet<>();
        this.cardDeck = new CardDeck();
    };

    public GamingBoard(List<Tile> tiles, Set<Path> paths, Set<Crossroad> crossroads){
        this.tiles = tiles;
        this.paths = paths;
        this.crossroads = crossroads;
    };

    /**
     * Metoda pro přesunutí lupiče.
     * Provoláním této metody hráč přesune lupiče z aktuální pozica na specifikovaný hexagon.
     *
     * @param tile je hexagon, na který chce hráč lupiče přemístít.
     * @author xmusil5
     * @version etapa 2
     */
    public void moveBandit(Tile tile){
        if (tile != null){
            if (tiles.contains(tile)) {
                for (var t : tiles) {
                    if (t.getHasBandit()) {
                        t.setHasBandit(false);
                    }
                }
                tile.setHasBandit(true);
            }
        }
    }

    /**
     * Metoda pro nalezení hexagonu, na kterém je právě lupič.
     *
     * @return hexagon, na kterém se právě nachází lupič
     * @version etapa 5
     */
    public Tile getTileWithBandit(){
        Tile tileWithBandit = null;
        for (var tile : this.tiles){
            if (tile.getHasBandit()){
                tileWithBandit = tile;
            }
        }
        if (tileWithBandit == null){
            throw new IllegalStateException("Tile with bandit hasn't been found. At any moment, there has to be one tile with bandit");
        }
        return tileWithBandit;
    }

    public int getLongestRoad(Player player){
        Set<Crossroad> deadEndCrossroads = getDeadEndCrossroadsOfPlayer(player);
        Set<Path> visitedPaths = new HashSet<>();
        int maxRoadLength = 0;
        for (var DEC : this.crossroads){
            int thisMax = goThroughPaths(0, DEC, visitedPaths, player);
            maxRoadLength = (thisMax>maxRoadLength) ? thisMax : maxRoadLength;
            visitedPaths = new HashSet<>();
        }
        return maxRoadLength;
    }

    private int goThroughPaths(int currentMax, Crossroad currentCrossroad, Set<Path> visitedPaths, Player player){
        if (currentCrossroad.getNeighboringPathsWithPlayerRoad(player).size()==0){
            return currentMax;
        }
        int pathMax = currentMax;
        for (var path : currentCrossroad.getNeighboringPathsWithPlayerRoad(player)){
            boolean otherSettlementBlockingWay = false;
            Settlement otherEndSettlement = path.getCrossroadOnSecondEnd(currentCrossroad).getPlayerSettlement();
            if (otherEndSettlement != null){
                if (otherEndSettlement.getOwner() != null){
                    if (!otherEndSettlement.getOwner().equals(player)){
                        otherSettlementBlockingWay = true;
                    }
                }
            }
            if (!visitedPaths.contains(path) && !otherSettlementBlockingWay) {
                visitedPaths.add(path);
                int side = goThroughPaths(currentMax+1, path.getCrossroadOnSecondEnd(currentCrossroad), visitedPaths, player);
                pathMax = Math.max(side, pathMax);
            }
        }
        return pathMax;
    }

    private Set<Crossroad> getDeadEndCrossroadsOfPlayer(Player player){
        Set<Crossroad> crossroads = new HashSet<>();
        for (var c : this.crossroads){
            if (c.getNeighboringPathsWithPlayerRoad(player).size() == 1){
                crossroads.add(c);
            }
        }
        return crossroads;
    }

    /**
     * Metoda pro vyhodnocení výsledků po hodu kostkami
     * V závislosti na padlém čísle se provede akce
     *
     * @param rolledToken číslo, které padlo na kostkách
     * @author xmusil5
     */
    public void dicesHaveBeenRolled(int rolledToken){
        if (rolledToken >= 2 && rolledToken <= 12 && rolledToken != 7){
            List<Tile> tiles = getTilesByToken(rolledToken);
            for (var tile : tiles){
                tile.giveYield();
            }
        }
        else if (rolledToken == 7){

        }
        else {
            throw new IllegalArgumentException("Dices can only roll token of value 2 - 12");
        }
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }


    public void setCrossroads(Set<Crossroad> crossroads) {
        if (crossroads != null && (this.crossroads == null || this.crossroads.isEmpty())) {
            this.crossroads = crossroads;
        }
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(CardDeck cardDeck) {
        this.cardDeck = cardDeck;
    }

    /**
     * Metoda vrací křižovatku herní desky podle zadané souřadnice
     *
     * @param coordinate souřadnice hledané křižovatky
     * @author xmusil5
     */
    public Crossroad getCrossroadByCoordinate(int coordinate){
        for (var c : this.crossroads){
            if (c.getCoordinate() == coordinate){
                return c;
            }
        }
        return null;
    }


    /**
     * Metoda vrací cestu herní desky podle zadané souřadnice
     *
     * @param coordinate souřadnice hledané cesty
     * @author xmusil5
     */
    public Path getPathByCoordinate(int coordinate){
        for (var p : this.paths){
            if (p.getCoordinate() == coordinate){
                return p;
            }
        }
        return null;
    }

    /**
     * Metoda vrací hexagon herní desky podle zadané souřadnice
     *
     * @param coordinate souřadnice hledaného hexagonu
     * @return hexagon s odpovídající souřadnicí
     *
     * @author xmusil5
     */
    public Tile getTileByCoordinate(int coordinate){
        for (var t : this.tiles){
            if (t.getCoordinate() == coordinate){
                return t;
            }
        }
        return null;
    }

    /**
     * Metoda vrací seznam hexagonů, které na jednom ze svých cípů mají specifikovanou křižovatku.
     *
     * @param crossroad křižovatka, podle které se vyhledává
     * @return seznam hexagonů maující křižovatku na jednom ze svých cípů
     * @version etapa 5
     */
    public List<Tile> getTilesContainingCrossroad(Crossroad crossroad){
        List<Tile> tiles = new ArrayList<>();
        for (var tile : this.tiles){
            if (tile.containsCrossroad(crossroad)){
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * Metoda vrací seznam hexagonů s daným tokenem (číslo korespondující s hodem kostky)
     *
     * @param token token hledaných hexagonů
     * @return seznam hexagonů s odpovídajícím tokenem
     * @author xmusil5
     */
    public List<Tile> getTilesByToken(int token){
        List<Tile> tiles = new ArrayList<>();
        for (var c : this.tiles){
            if (c.getToken() == token){
                tiles.add(c);
            }
        }
        return tiles;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }

    public Set<Crossroad> getCrossroads() {
        return crossroads;
    }


}

package cz.mendelu.catan.game.gamebuilder;

import cz.mendelu.catan.carddeck.CardDeck;
import cz.mendelu.catan.cards.BiggestArmyCard;
import cz.mendelu.catan.cards.LongestRoadCard;
import cz.mendelu.catan.cards.ResourceCard;
import cz.mendelu.catan.cards.actioncards.*;
import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.*;
import cz.mendelu.catan.player.Player;
import cz.mendelu.catan.player.pieces.City;
import cz.mendelu.catan.player.pieces.Road;
import cz.mendelu.catan.player.pieces.Village;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public abstract class GameBuilder implements Serializable {
    protected Game game;

    //CONSTANTS
    public static final int NUMBER_OF_PATHS = 72;
    public static final int NUMBER_OF_CROSSROADS = 54;

    public static final int NUMBER_OF_LUMBER_CARDS = 19;
    public static final int NUMBER_OF_WOOL_CARDS = 19;
    public static final int NUMBER_OF_BRICK_CARDS = 19;
    public static final int NUMBER_OF_GRAIN_CARDS = 19;
    public static final int NUMBER_OF_ORE_CARDS = 19;

    public static final int NUMBER_OF_INVENTION_CARDS = 2;
    public static final int NUMBER_OF_ROADBUILDING_CARDS = 2;
    public static final int NUMBER_OF_MONOPOL_CARDS = 2;
    public static final int NUMBER_OF_KNIGHT_CARDS = 14;
    public static final int NUMBER_OF_VICTORY_CARDS = 5;

    public static final int NUMBER_OF_VILLAGE_PIECES = 5;
    public static final int NUMBER_OF_CITY_PIECES = 4;
    public static final int NUMBER_OF_ROAD_PIECES = 15;


    public Game getGame(){
        if (this.game != null){
            return this.game;
        } else {
            throw new NullPointerException("GameBuilder - Game has not been built");
        }

    }

    public abstract void buildGame(List<String> playerNames, List<Color> playerColors);


    public CardDeck generateCardDeck(){
        List<ResourceCard> resourceCards = new ArrayList<>();
        List<ActionCard> actionCards = new ArrayList<>();
        //RESOURCE CARDS
        for (int i = 0; i<NUMBER_OF_LUMBER_CARDS; i++){
            resourceCards.add(new ResourceCard(null, ResourceType.LUMBER));
        }
        for (int i = 0; i<NUMBER_OF_WOOL_CARDS; i++){
            resourceCards.add(new ResourceCard(null, ResourceType.WOOL));
        }
        for (int i = 0; i<NUMBER_OF_BRICK_CARDS; i++){
            resourceCards.add(new ResourceCard(null, ResourceType.BRICK));
        }
        for (int i = 0; i<NUMBER_OF_GRAIN_CARDS; i++){
            resourceCards.add(new ResourceCard(null, ResourceType.GRAIN));
        }
        for (int i = 0; i<NUMBER_OF_ORE_CARDS; i++){
            resourceCards.add(new ResourceCard(null, ResourceType.ORE));
        }

        //ACTION CARDS
        for (int i = 0; i<NUMBER_OF_INVENTION_CARDS; i++){
            actionCards.add(new Invention(null));
        }
        for (int i = 0; i<NUMBER_OF_ROADBUILDING_CARDS; i++){
            actionCards.add(new RoadBuilding(null));
        }
        for (int i = 0; i<NUMBER_OF_MONOPOL_CARDS; i++){
            actionCards.add(new Monopol(null));
        }
        for (int i = 0; i<NUMBER_OF_KNIGHT_CARDS; i++){
            actionCards.add(new Knight(null));
        }
        for (int i = 0; i<NUMBER_OF_VICTORY_CARDS; i++){
            actionCards.add(new VictoryPoint(null));
        }

        //SPECIAL CARDS
        BiggestArmyCard biggestArmyCard = new BiggestArmyCard();
        LongestRoadCard longestRoadCard = new LongestRoadCard();

        return new CardDeck(resourceCards, actionCards, biggestArmyCard, longestRoadCard);
    }

    protected Set<Crossroad> generateCrossroads(GamingBoard gamingBoard){
        Set<Crossroad> crossroads = new HashSet<>();
        for (int i = 1; i<=NUMBER_OF_CROSSROADS; i++){
            if (i == 1 || i == 4){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 3,
                        ResourceType.getAllTradeAbleResourceTypes(), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 2 || i == 6){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 2,
                        Arrays.asList(ResourceType.GRAIN), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 11 || i == 16){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 2,
                        Arrays.asList(ResourceType.ORE), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 27 || i == 33){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 3,
                        ResourceType.getAllTradeAbleResourceTypes(), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 43 || i == 47){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 2,
                        Arrays.asList(ResourceType.WOOL), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 50 || i == 53){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 3,
                        ResourceType.getAllTradeAbleResourceTypes(), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 48 || i == 52){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 3,
                        ResourceType.getAllTradeAbleResourceTypes(), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 34 || i == 39){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 2,
                        Arrays.asList(ResourceType.BRICK), ResourceType.getAllTradeAbleResourceTypes()));
            } else if (i == 12 || i == 17){
                crossroads.add(new PortCrossroad(i, null, gamingBoard, 2,
                        Arrays.asList(ResourceType.LUMBER), ResourceType.getAllTradeAbleResourceTypes()));
            } else{
                crossroads.add(new Crossroad(i, gamingBoard));
            }

        }
        return crossroads;
    }

    public Set<Path> generatePaths(CardDeck cardDeck){
        Set<Path> paths= new HashSet<>();
        for (int i = 1; i<= NUMBER_OF_PATHS; i++){
            paths.add(new Path(i, cardDeck));
        }
        return paths;
    }

    /**
     * Metoda pro vytvo??en?? hr??????.
     * Tato metoda vygeneruje instance hr?????? pro novou hru.
     *
     * @param playerNames jm??na nov??ch hr??????
     * @param playerColors barvy nov??ch hr?????? (po??ad?? se shoduje s po??ad??m jmen)
     * @return seznam instanc?? hr?????? do nov?? vytvo??en?? hry
     *
     * @author xmusil5
     * @version etapa 2
     */
    public List<Player> generatePlayers(List<String> playerNames, List<Color> playerColors, GamingBoard gamingBoard) {
        List<Player> generatedPlayers = new ArrayList<>();
        if (playerNames != null && playerColors != null){
            if (playerNames.size() == playerColors.size()){

                if (playerNames.size() > 0 && playerNames.size() <= Game.MAX_NUMBER_OF_PLAYERS){
                    for (int i = 0; i<playerNames.size(); i++){
                        Player generatedPlayer = new Player(playerNames.get(i), playerColors.get(i), gamingBoard);
                        generatedPlayer.setVillagePieces(generateVillagePieces(generatedPlayer));
                        generatedPlayer.setCityPieces(generateCityPieces(generatedPlayer));
                        generatedPlayer.setRoadPieces(generateRoadPieces(generatedPlayer));
                        generatedPlayers.add(generatedPlayer);
                    }

                }else {Logger.getGlobal().warning("GeneratePlayers - number of players not supported");}
            }else {throw new IndexOutOfBoundsException("GeneratePlayers - number of names and colors not matching");}
        }else {Logger.getGlobal().warning("GeneratePlayers - null parameter");}
        if (generatedPlayers.size() == playerNames.size()){
            return generatedPlayers;
        }else {
            return null;
        }
    }

    private List<Village> generateVillagePieces(Player owner){
        List<Village> villagePieces = new ArrayList<>();
        for (int i = 0; i<NUMBER_OF_VILLAGE_PIECES; i++){
            villagePieces.add(new Village(owner));
        }
        return villagePieces;
    }

    private List<City> generateCityPieces(Player owner){
        List<City> cityPieces = new ArrayList<>();
        for (int i = 0; i<NUMBER_OF_CITY_PIECES; i++){
            cityPieces.add(new City(owner));
        }
        return cityPieces;
    }

    private List<Road> generateRoadPieces(Player owner){
        List<Road> roadPieces = new ArrayList<>();
        for (int i = 0; i<NUMBER_OF_ROAD_PIECES; i++){
            roadPieces.add(new Road(owner));
        }
        return roadPieces;
    }

    /**
     * Metoda nastavuj??c?? pot??ebn?? relace mezi k??i??ovatkami a cestami
     *
     * @param gamingBoard hrac?? deska s ji?? vytvo??en??mi k??i??ovatkami a cestami
     *
     * @author xmusil5
     */
    //Pro napojen?? se n??m nepoda??ilo naj??t ani vymyslet jeden plnohodnotn?? a efektivn?? algoritmus.
    //Nastaven?? prob??h?? pomoc?? dvou algoritm?? - jeden pro cesty spojuj??c?? k??i??ovatky horizont??ln?? (na sikmo) a druh?? pro cesty spojuj??c?? k??i??ovatky horizont??ln??
    //Jeliko?? je tato ????st hrac?? desky st??l?? a nem??nn?? (ji?? z podstaty Catanu), nastavuj?? se propojen?? ru??n?? v t??to metod??.
    //Sou??adnice jsou pops??ny na obr??zku v images/game/coordinate.jpg
    public final void connectPathsWithCrossroads(GamingBoard gamingBoard){
        //NASTAVENI VERTIKALNICH CEST PO RADACH
        //RADA 2
        setVerticalCrossroadLine(7, 10, -3, 1, gamingBoard);
        //RADA 4
        setVerticalCrossroadLine(19, 23, -7, -2, gamingBoard);
        //RADA 6
        setVerticalCrossroadLine(34, 39, -12, -6, gamingBoard);
        //RADA 8
        setVerticalCrossroadLine(50, 54, -16, -11, gamingBoard);
        //RADA 10
        setVerticalCrossroadLine(63, 66, -19, -15, gamingBoard);

        //NASTAVENI HORIZONTALNICH (SIKMYCH CEST) PO RADACH
        //RADA 1
        setHorizontalPathLine(1, 1, 6, 3, 4, gamingBoard);
        //RADA 3
        setHorizontalPathLine(8, 11, 18, 4, 5, gamingBoard);
        //RADA 5
        setHorizontalPathLine(17, 24, 33, 5, 6, gamingBoard);
        //RADA 7
        setHorizontalPathLine(34, 40, 49, -6, -5, gamingBoard);
        //RADA 9
        setHorizontalPathLine(44, 55, 62, -5, -4, gamingBoard);
        //RADA 11
        setHorizontalPathLine(52, 67, 72, -4, -3, gamingBoard);
    }

    /**
     * Parci??ln?? algoritmus pro p??id??len?? k??i??ovatek k cest??m v horizont??ln??ch ??ad??ch cest (cesty co spojuj?? horizont??ln?? 2 k??i??ovatky)
     *
     * @param firstTipCrossroadCoordinate sou??adnice pvn?? k??i??ovatky z vrchol?? t??to ??ady
     * @param firstPathCoordinate sou??adnice prvn?? cesty v ??ad??
     * @param lastPathCoordinate sou??adnice posledn?? cesty v ??ad??
     * @param leftCrossroadIncrement o kolik se zm??n?? sou??adnice k??i??ovatky vlevo od c??pu
     * @param rightCrossroadIncrement o kolik se zm??n?? sou??adnice k??i??ovatky vpravo od c??pu
     * @param gamingBoard hrac?? deska s ji?? vytvo??en??mi k??i??ovatkami a cestami
     * @author xmusil5
     */
    private final void setHorizontalPathLine(int firstTipCrossroadCoordinate, int firstPathCoordinate, int lastPathCoordinate,
                                       int leftCrossroadIncrement, int rightCrossroadIncrement, GamingBoard gamingBoard){
        int checker;
        //pokud ??ada za????n?? sudou sou??adn??c??
        if (firstPathCoordinate%2 == 0) {
            checker = 0;
        }
        //pokud ??ada za????n?? lichou sou??adn??c??
        else{
            checker = 1;
        }

        int currentTipCrossroadIndex = firstTipCrossroadCoordinate;
        for (int i = firstPathCoordinate; i<= lastPathCoordinate; i++){
            Crossroad upperCrossroad = gamingBoard.getCrossroadByCoordinate(currentTipCrossroadIndex);
            Crossroad lowerCrossroad;
            if (i % 2 == checker) {
                lowerCrossroad = gamingBoard.getCrossroadByCoordinate(currentTipCrossroadIndex + leftCrossroadIncrement);
                gamingBoard.getPathByCoordinate(i).setNeighboringCrossroads(Arrays.asList(upperCrossroad, lowerCrossroad));
            } else {
                lowerCrossroad = gamingBoard.getCrossroadByCoordinate(currentTipCrossroadIndex + rightCrossroadIncrement);
                gamingBoard.getPathByCoordinate(i).setNeighboringCrossroads(Arrays.asList(upperCrossroad, lowerCrossroad));
                currentTipCrossroadIndex++;
            }
        }
    }

    /**
     * Parci??ln?? algoritmus pro p??id??len?? k??i??ovatek k cest??m ve vertik??ln??ch ??ad??ch cest (cesty co spojuj?? vertik??ln?? 2 k??i??ovatky)
     *
     * @param firstPathCoordinate sou??adnice prvn?? cesty v ??ad??
     * @param lastPathCoordinate sou??adnice posledn?? cesty v ??ad??
     * @param upperCrossroadIncrement o kolik se zm??n?? sou??adnice k??i??ovatky nahoru od cesty
     * @param lowerCrossroadIncrement o kolik se zm??n?? sou??adnice k??i??ovatky dol?? od cesty
     * @param gamingBoard hrac?? deska s ji?? vytvo??en??mi k??i??ovatkami a cestami
     * @author xmusil5
     */
    private final void setVerticalCrossroadLine(int firstPathCoordinate, int lastPathCoordinate,
                                          int upperCrossroadIncrement, int lowerCrossroadIncrement, GamingBoard gamingBoard){
        for (int i = firstPathCoordinate; i<= lastPathCoordinate; i++){
            Crossroad crossroad1 = gamingBoard.getCrossroadByCoordinate(i + upperCrossroadIncrement);
            Crossroad crossroad2 = gamingBoard.getCrossroadByCoordinate(i + lowerCrossroadIncrement);
            gamingBoard.getPathByCoordinate(i).setNeighboringCrossroads(Arrays.asList(crossroad1, crossroad2));
        }
    }

    public abstract List<Tile> generateTiles(List<ResourceType>types, GamingBoard gamingBoard);

    public void setCrossroadsToTiles(List<Tile> tiles, GamingBoard gamingBoard){
        List<Tile> lineOne = tiles.subList(0, 3);
        List<Tile> lineTwo = tiles.subList(3, 7);
        List<Tile> lineThree = tiles.subList(7, 12);
        List<Tile> lineFour = tiles.subList(12, 16);
        List<Tile> lineFive = tiles.subList(16, 19);

        setCrossroadsToTileLine(lineOne, gamingBoard, 1, 4, 8, 13);
        setCrossroadsToTileLine(lineTwo, gamingBoard, 8, 12, 17, 23);
        setCrossroadsToTileLine(lineThree, gamingBoard, 17, 22, 28, 34);
        setCrossroadsToTileLine(lineFour, gamingBoard, 29, 34, 39, 44);
        setCrossroadsToTileLine(lineFive, gamingBoard, 40, 44, 48, 52);
    }

    /**
     * Algoritmus pro p??id??len?? k??i??ovatek hexagon??m na p????slusn?? c??py v jedn?? ??ad??
     *
     * @param tileLine ??ada hexagon??
     * @param firstTopLineCoordinate sou??adnice prvn?? k??i??ovatky v nejvyss??ch c??pech hexagon??
     * @param firstUpperCentralLineCoordinate sou??adnice prvn?? k??i??ovatky v druh??ch nejvyss??ch c??pech hexagon??
     * @param firstLowerCentralLineCoordinate sou??adnice prvn?? k??i??ovatky v druh??ch nejspodn??js??ch c??pech hexagon??
     * @param gamingBoard hrac?? deska s ji?? vytvo??en??mi k??i??ovatkami a cestami
     * @param firstTopLineCoordinate sou??adnice prvn?? k??i??ovatky v nejspodn??js??ch c??pech hexagon??
     *
     * @author xmusil5
     */
    private void setCrossroadsToTileLine(List<Tile> tileLine, GamingBoard gamingBoard, int firstTopLineCoordinate,
                                      int firstUpperCentralLineCoordinate, int firstLowerCentralLineCoordinate, int firstBottomLineCoordinate){
        for (int i = 0; i<tileLine.size(); i++){
            Map<TileTipDirection, Crossroad> crossroads = new HashMap<>();
            crossroads.put(TileTipDirection.NORTH, gamingBoard.getCrossroadByCoordinate(firstTopLineCoordinate + i));

            crossroads.put(TileTipDirection.NORTH_WEST, gamingBoard.getCrossroadByCoordinate(firstUpperCentralLineCoordinate + i));
            crossroads.put(TileTipDirection.NORTH_EAST, gamingBoard.getCrossroadByCoordinate(firstUpperCentralLineCoordinate + i + 1));

            crossroads.put(TileTipDirection.SOUTH_WEST, gamingBoard.getCrossroadByCoordinate(firstLowerCentralLineCoordinate + i));
            crossroads.put(TileTipDirection.SOUTH_EAST, gamingBoard.getCrossroadByCoordinate(firstLowerCentralLineCoordinate + i + 1));

            crossroads.put(TileTipDirection.SOUTH, gamingBoard.getCrossroadByCoordinate(firstBottomLineCoordinate + i));

            tileLine.get(i).setTipCrossroads(crossroads);
        }
    }
}

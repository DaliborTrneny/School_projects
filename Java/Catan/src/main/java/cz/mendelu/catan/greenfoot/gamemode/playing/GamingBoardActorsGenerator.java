package cz.mendelu.catan.greenfoot.gamemode.playing;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.gamingboard.*;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.CrossroadActor;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.PathActor;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.PortCrossroadActor;
import cz.mendelu.catan.greenfoot.gamemode.playing.actors.TileActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;

import java.util.ArrayList;
import java.util.List;

public class GamingBoardActorsGenerator {
    private GamingBoard gamingBoard;

    private static final int FIRST_TILE_X = 778;
    private static final int FIRST_TILE_Y = 293;
    private static final int HORIZONTAL_TILE_DISTANCE = 125;
    private static final int VERTICAL_TILE_DISTANCE = 107;
    private static final int NEXT_TILE_LINE_HORIZONTAL_OFFSET = 62;
    private static final int TILE_EDGE_LENGTH = 70;

    private static final int HORIZONTAL_PATH_DISTANCE_HORIZONTAL_LONG = 64;
    private static final int HORIZONTAL_PATH_DISTANCE_HORIZONTAL_SHORT = 61;
    private static final int HORIZONTAL_PATH_DISTANCE_VERTICAL = 125;

    private static final int ROTATION_ANGLE = 60;

    //O KOLIK POSUNOUT TOKEN OPROTI STREDU HEXAGONU NA OSE Y
    private static final int TOKEN_TO_TILE_OFFSET = 0;
    private static final String TOKEN_IMAGE_FILE_BASE = "images/actors/tiles/tokens/";

    //POCTY HEXAGONU NA JEDNOTLIVYCH RADCICH HRACI DESKY
    private static final int[] LINE_TILE_COUNT = {3, 4, 5, 4, 3};
    //POCTY KRIZOVATEK NA JEDNOTLIVYCH RADCICH HRACI DESKY
    private static final int[] LINE_CROSSROAD_COUNT = {3, 4, 4, 5, 5, 6, 6, 5, 5, 4, 4, 3};
    //POCTY CEST NA JEDNOTLIVYCH RADCICH HRACI DESKY
    private static final int[] LINE_PATH_COUNT = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};

    public GamingBoardActorsGenerator(GamingBoard gamingBoard){
        this.gamingBoard = gamingBoard;
    }

    /**
     * Algoritmus pro přidělení souřadnic hexagonům v GUI
     *
     * @return seznam hexagon actorů s přidělenými souřadnicemi
     *
     * @author xmusil5
     * @version etapa 4
     */
    public List<TileActor> getTileActorsWithPosition(TileActor.TileActorOnClickListener listener){
        List<TileActor> tileActors = new ArrayList<>();
        int mostLeftX = FIRST_TILE_X + NEXT_TILE_LINE_HORIZONTAL_OFFSET;
        int lineLenghtMaximum = 0;


        for(int i = 0; i<LINE_TILE_COUNT.length; i++){
            if (LINE_TILE_COUNT[i]>lineLenghtMaximum) {
                mostLeftX -= NEXT_TILE_LINE_HORIZONTAL_OFFSET;
                for (int j = 0; j < LINE_TILE_COUNT[i]; j++) {
                    int x = mostLeftX + j*HORIZONTAL_TILE_DISTANCE;
                    int y = FIRST_TILE_Y + i*VERTICAL_TILE_DISTANCE;
                    tileActors.add(new TileActor(gamingBoard.getTileByCoordinate(getSumUpToIndex(i, LINE_TILE_COUNT) + j + 1),listener, x, y));
                }
                lineLenghtMaximum = LINE_TILE_COUNT[i];
            }
            else {
                mostLeftX += NEXT_TILE_LINE_HORIZONTAL_OFFSET;
                for (int j = 0; j < LINE_TILE_COUNT[i]; j++) {
                    int x = mostLeftX + j*HORIZONTAL_TILE_DISTANCE;
                    int y = FIRST_TILE_Y + i*VERTICAL_TILE_DISTANCE;
                    tileActors.add(new TileActor(gamingBoard.getTileByCoordinate(getSumUpToIndex(i, LINE_TILE_COUNT) + j + 1), listener, x, y));
                }
            }
        }
        return tileActors;
    }

    /**
     * Metoda generuje tokeny pro hexagony (hodnotu vrhu kosty, se kterou korespondují).
     *
     * @return seznam token actorů pro všechny hexagony
     *
     * @version etapa 5
     */
    public List<PlaceHolderActor> generateTokenActorsForTiles(List<TileActor> tileActors){
        List<PlaceHolderActor> placeHolderActors = new ArrayList<>();
        for (var tileActor : tileActors){
            if (tileActor.getTile().getResourceType() != ResourceType.EMPTY) {
                int x = tileActor.getHorizontalCoordinate();
                int y = tileActor.getVerticalCoordinate() + TOKEN_TO_TILE_OFFSET;
                String imagePath = getTokenImagePathByValue(tileActor.getTile().getToken());
                PlaceHolderActor pha = new PlaceHolderActor(x, y, null);
                pha.setImage(imagePath);
                placeHolderActors.add(pha);
            }
        }
        return placeHolderActors;
    }

    private String getTokenImagePathByValue(int tokenValue) {
        return TOKEN_IMAGE_FILE_BASE + String.valueOf(tokenValue) + ".png";
    }

    /**
     * Metoda vrátí počet objektů předešlých řádcích
     *
     * @param upToIndex do jakého indexu (kromě) se má počet brát
     * @author xmusil5
     * @version etapa 4
     */
    private int getSumUpToIndex(int upToIndex, int[] arr){
        int sum = 0;
        for (int i = 0; i < upToIndex; i++){
            sum += arr[i];
        }
        return sum;
    }

    /**
     * Algoritmus pro přidělení souřadnic křižovatkám v GUI
     *
     * @return seznam křižovatkových actorů s přidělenými souřadnicemi
     *
     * @author xmusil5
     * @version etapa 4
     */
    public List<CrossroadActor> getCrossroadActorsWithPositions(){
        List<CrossroadActor> crossroadActors = new ArrayList<>();
        for (int i = 1; i<=LINE_CROSSROAD_COUNT.length; i++) {
            switch (i){
                case 1:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 778, 224);
                    break;
                case 2:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 716, 258);
                    break;
                case 3:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 716, 329);
                    break;
                case 4:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 653, 365);
                    break;
                case 5:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 653, 436);
                    break;
                case 6:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 591, 472);
                    break;
                case 7:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 591, 544);
                    break;
                case 8:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 654, 580);
                    break;
                case 9:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 654, 651);
                    break;
                case 10:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 716, 687);
                    break;
                case 11:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 716, 759);
                    break;
                case 12:
                    addLineOfCrossroadActorsToList(crossroadActors, getSumUpToIndex(i-1, LINE_CROSSROAD_COUNT)+1,
                            getSumUpToIndex(i, LINE_CROSSROAD_COUNT), 779, 794);
                    break;
            }
        }
        return crossroadActors;
    }

    /**
     * Algoritmus pro přidělení souřadnic cestám v GUI
     *
     * @return seznam path actorů s přidělenými souřadnicemi a rotací
     *
     * @author xmusil5
     * @version etapa 4
     */
    public List<PathActor> getPathActorsWithPositions(){
        List<PathActor> pathActors = new ArrayList<>();
        for (int i = 1; i<=LINE_PATH_COUNT.length; i++) {
            switch (i){
                case 1:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 748, 239, true);
                    break;
                case 2:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 716, 293, false);
                    break;
                case 3:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 684, 347, true);
                    break;
                case 4:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 654, 400, false);
                    break;
                case 5:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 623, 454, true);
                    break;
                case 6:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 591, 508, false);
                    break;
                case 7:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 623, 563, true);
                    break;
                case 8:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 654, 616, false);
                    break;
                case 9:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 685, 669, true);
                    break;
                case 10:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 716, 723, false);
                    break;
                case 11:
                    addLineOfPathActorsToList(pathActors, getSumUpToIndex(i-1, LINE_PATH_COUNT)+1,
                            getSumUpToIndex(i, LINE_PATH_COUNT), 748, 777, true);
                    break;
            }
        }
        return pathActors;
    }

    private void addLineOfCrossroadActorsToList(List<CrossroadActor> crossroadActors, int startingCoordinate, int endingCoordinate, int baseX, int baseY){
        int currentX = baseX;
        for (int i = startingCoordinate; i<=endingCoordinate; i++){
            Crossroad crossroad = gamingBoard.getCrossroadByCoordinate(i);
            if (crossroad instanceof PortCrossroad){
                crossroadActors.add(new PortCrossroadActor((PortCrossroad) crossroad, currentX, baseY));
            } else{
                crossroadActors.add(new CrossroadActor(crossroad, currentX, baseY));
            }
            currentX += HORIZONTAL_TILE_DISTANCE;
        }
    }

    private void addLineOfPathActorsToList(List<PathActor> pathActors, int startingCoordinate, int endingCoordinate, int baseX, int baseY, boolean rotate){
        int currentX = baseX;

        //In case of horizontal path line (with rotation), this will set the first rotation depending if line is above or below the middle of the gaming board
        int sumOfPaths = getSumUpToIndex(LINE_PATH_COUNT.length-1, LINE_PATH_COUNT);
        boolean switcher = (startingCoordinate > sumOfPaths/2) ? false : true;
        //Cycling between short and long x offset (again for horizontal path lines)
        boolean shorterDistance = true;

        for (int i = startingCoordinate; i<=endingCoordinate; i++){
            Path path = gamingBoard.getPathByCoordinate(i);
            if (rotate){
                if (switcher){
                    pathActors.add(new PathActor(this.gamingBoard.getPathByCoordinate(i), currentX, baseY, ROTATION_ANGLE));
                } else{
                    pathActors.add(new PathActor(this.gamingBoard.getPathByCoordinate(i), currentX, baseY, - ROTATION_ANGLE));
                }
                currentX += (shorterDistance) ? HORIZONTAL_PATH_DISTANCE_HORIZONTAL_SHORT : HORIZONTAL_PATH_DISTANCE_HORIZONTAL_LONG;
                switcher = !switcher;
                shorterDistance = !shorterDistance;
            } else{
                pathActors.add(new PathActor(this.gamingBoard.getPathByCoordinate(i), currentX, baseY, 0));
                currentX += HORIZONTAL_PATH_DISTANCE_VERTICAL;
            }
        }
    }

}

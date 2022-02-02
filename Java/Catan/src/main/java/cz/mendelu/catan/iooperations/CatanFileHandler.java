package cz.mendelu.catan.iooperations;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.gamebuilder.DefaultPresetBuilder;
import cz.mendelu.catan.game.gamebuilder.GameBuilder;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.player.Player;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CatanFileHandler {
    private static final String SAVED_GAMES_PATH = "saves/";
    private DateTimeFormatter dateTimeFormatter;

    public CatanFileHandler(){
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
    }

    /**
     * Metoda uloží předanou hru a stav herního světa do binárního souboru, který je možné
     * v budoucnu načíst a ve hře pokračovat.
     *
     * @author xmusil5
     * @version etapa 4
     */
    public void saveGame(Game game, GameWorldState state){
        if (game != null && state != null){
            String filePath = getNewSavedGamePath();
            try(FileOutputStream fos = new FileOutputStream(filePath)){

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(game);
                oos.writeObject(state);

                oos.close();

            } catch (FileNotFoundException e) {
                Logger.getGlobal().warning("CatanFileWriter=>saveGame - " + filePath + "is not a valid path.");
                e.printStackTrace();
            } catch (IOException e) {
                Logger.getGlobal().warning("CatanFileWriter=>saveGame - Could not write the file with path: " + filePath);
                e.printStackTrace();
            }

        } else {Logger.getGlobal().warning("CatanFileWriter=>saveGame - Game or GameWorld state are null");}
    }

    //TEST
    public static void main(String[] args) {
        DefaultPresetBuilder defaultPresetBuilder = new DefaultPresetBuilder();
        defaultPresetBuilder.buildGame(Arrays.asList("Misa", "Dalik", "Lenka"), Arrays.asList(Color.GREEN, Color.BLUE, Color.MAGENTA));

        Game game = defaultPresetBuilder.getGame();
        game.setTurnCounter(15);
        game.getPlayers().get(0).setKnightsUsed(3);
        game.getPlayers().get(2).setKnightsUsed(5);
        CatanFileHandler catanFileHandler = new CatanFileHandler();

        catanFileHandler.saveGameStats(game);

        catanFileHandler.saveGame(game, GameWorldState.RUNNING);

        /*CatanFileHandler catanFileHandler = new CatanFileHandler();

        List<Object> test = catanFileHandler.loadGame("04-12-2021 13-47");*/
    }

    /**
     * Metoda sloužící k načtení herního světa a jejího stavu z binárního souboru
     *
     * @author xtrneny1
     * @version etapa 4
     */
    public List<Object> loadGame(String filename) {
        List<Object> gameSettings = new ArrayList<>();
        String filePath = SAVED_GAMES_PATH + filename;
        try(FileInputStream fi = new FileInputStream(filePath)) {

            ObjectInputStream ois = new ObjectInputStream(fi);

            Game game = (Game)ois.readObject();
            GameWorldState state = (GameWorldState)ois.readObject();

            ois.close();

            gameSettings.add(game);
            gameSettings.add(state);

        } catch (FileNotFoundException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - " + filePath + "is not a valid path.");
            e.printStackTrace();
        } catch (IOException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - Could not load the file with path: " + filePath);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - Class of the -object game / object state- does not exist");
            e.printStackTrace();
        }

        return gameSettings;
    }

    /**
     * Metoda sloužící k zapisování vybraných herních statistik např. Trvání nejdelší hry (počet kol)
     *
     * @author xtrneny1
     * @version etapa 4
     */
    public void saveGameStats(Game game){
        // Herní statistiky
        int longestGame = 0;
        int biggestArmy = 0;

        // Nejdříve je potřeba načíst aktuální nejlepší statistiky
        File tempFile = new File("stats/uniqueGameStats");
        boolean exists = tempFile.exists();

        if (exists) {
            try(FileInputStream fi = new FileInputStream("stats/uniqueGameStats")) {

                ObjectInputStream ois = new ObjectInputStream(fi);

                longestGame = (Integer) ois.readObject();
                biggestArmy = (Integer) ois.readObject();

                ois.close();

            } catch (FileNotFoundException e) {
                Logger.getGlobal().warning("CatanFileLoader=>saveGameStats - stats/uniqueGameStats is not a valid path.");
                e.printStackTrace();
            } catch (IOException e) {
                Logger.getGlobal().warning("CatanFileLoader=>saveGameStats - Could not write the file with path: stats/uniqueGameStats");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                Logger.getGlobal().warning("CatanFileLoader=>saveGameStats - Game is null");
                e.printStackTrace();
            }
        }

        if (game != null){
            try(PrintWriter writer = new PrintWriter("stats/uniqueGameStats")) {
                writer.flush();
            } catch (FileNotFoundException e) {
                Logger.getGlobal().warning("CatanFileWriter=>saveGameStats - stats/uniqueGameStats is not a valid path.");
                e.printStackTrace();
            }

            try(FileOutputStream fos = new FileOutputStream("stats/uniqueGameStats")){

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                if (longestGame >= game.getTurnCounter()) {
                    oos.writeObject(longestGame);
                } else {
                    oos.writeObject(game.getTurnCounter());
                }

                if (biggestArmy >= game.getNumberOfMostUsedKnights()) {
                    oos.writeObject(biggestArmy);
                } else {
                    oos.writeObject(game.getNumberOfMostUsedKnights());
                }

                oos.close();

            } catch (FileNotFoundException e) {
                Logger.getGlobal().warning("CatanFileWriter=>saveGameStats - stats/uniqueGameStats.bin is not a valid path.");
                e.printStackTrace();
            } catch (IOException e) {
                Logger.getGlobal().warning("CatanFileWriter=>saveGameStats - Could not write the file with path: stats/uniqueGameStats.bin");
                e.printStackTrace();
            }

        } else {Logger.getGlobal().warning("CatanFileWriter=>saveGameStats - Game is null");}
    }

    /**
     * Metoda pro načtení aktuálních žebříčků top statistik
     *
     * @author xmusil5
     * @version etapa 4
     */
    public List<Integer> loadUniqueStats() {
        List<Integer> stats = new ArrayList<>();

        int longestGame = 0;
        int biggestArmy = 0;

        String filePath = "stats/uniqueGameStats";
        try(FileInputStream fi = new FileInputStream(filePath)) {

            ObjectInputStream ois = new ObjectInputStream(fi);

            longestGame = (Integer)ois.readObject();
            biggestArmy = (Integer)ois.readObject();

            ois.close();

            stats.add(longestGame);
            stats.add(biggestArmy);


        } catch (FileNotFoundException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - " + filePath + "is not a valid path.");
            e.printStackTrace();
        } catch (IOException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - Could not load the file with path: " + filePath);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.getGlobal().warning("CatanFileLoader=>loadGame - Class of the -object game / object state- does not exist");
            e.printStackTrace();
        }

        return stats;
    }

    /**
     * Metoda vygeneruje název pro soubor nově uložené hry z aktuálního data a času.
     *
     * @author xmusil5
     * @version etapa 4
     */
    public String getNewSavedGamePath(){
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeString = dateTimeFormatter.format(dateTime).toString();
        return SAVED_GAMES_PATH + dateTimeString;
    }



    public String getSavedGamesPath() {
        return SAVED_GAMES_PATH;
    }

}

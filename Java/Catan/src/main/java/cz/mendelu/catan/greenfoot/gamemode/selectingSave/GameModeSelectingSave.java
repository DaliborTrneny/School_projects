package cz.mendelu.catan.greenfoot.gamemode.selectingSave;

import cz.mendelu.catan.game.CatanLogger;
import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.greenfoot.GameWorld;
import cz.mendelu.catan.greenfoot.gamemode.GameMode;
import cz.mendelu.catan.greenfoot.gamemode.menu.GameModeMenu;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameModePlaying;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;
import cz.mendelu.catan.greenfoot.gamemode.selectingSave.saveactor.saveActor;
import cz.mendelu.catan.greenfoot.helpers.ButtonActor;
import cz.mendelu.catan.greenfoot.helpers.PlaceHolderActor;
import greenfoot.GreenfootImage;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameModeSelectingSave extends GameMode {
    private static final String MENU_BACKGROUND = "images/menu/menu_background.png";
    private int numberOfFiles;
    private List<String> fileNames = new ArrayList<>();
    private int numberOfPages;
    private int indexOfActualPage;
    private PlaceHolderActor counterFile;
    private PlaceHolderActor counterPage;


    public GameModeSelectingSave (int savePage) {
        allActors = new ArrayList<>();
        numberOfFiles = getNumberOfFilesInSavesDir();
        numberOfPages = getNumberOfPages().get(0);
        indexOfActualPage = savePage;
        generateObjects();
        allActors.addAll(generateSaveActors());
        allActors.addAll(generateUIButtons());
    }

    @Override
    public void update(Object data) {
        counterFile.setImage(new GreenfootImage("Pocet ulozenych her: " + numberOfFiles, 40, GAME_WHITE, TRANSPARENT));
        counterPage.setImage(new GreenfootImage(indexOfActualPage + " / " + numberOfPages, 40, GAME_WHITE, TRANSPARENT));
    }

    private void generateObjects () {
        allActors.add(counterFile = new PlaceHolderActor(GAME_WIDTH / 2, GAME_HEIGHT / 2 - 75, null));
        allActors.add(counterPage = new PlaceHolderActor(GAME_WIDTH / 2, GAME_HEIGHT / 2 + 358, null));
    }

    private List<saveActor> generateSaveActors () {
        List<saveActor> fileSaveActors = new ArrayList<>();
        int reductionCounter = 0;

        // FIXME Vyredukovat posun po y ose, pri dalsich strankach
        // FIXME Doresit update nebo generaci novych aktoru
        if (getNumberOfPages().get(1) == 0) {
            for (int i = 7 * (indexOfActualPage - 1); i < 7 * indexOfActualPage; i++) {
                fileSaveActors.add(new saveActor(fileNames.get(i), GAME_WIDTH/2, GAME_HEIGHT/2 - 15 + (reductionCounter * 50)));
                reductionCounter++;
            }
        } else {
            if (indexOfActualPage == numberOfPages) {
                for (int i = 7 * (indexOfActualPage - 1); i < numberOfFiles; i++) {
                    fileSaveActors.add(new saveActor(fileNames.get(i), GAME_WIDTH/2, GAME_HEIGHT/2 - 15 + (reductionCounter * 50)));
                    reductionCounter++;
                }
            } else {
                for (int i = 7 * (indexOfActualPage - 1); i < 7 * indexOfActualPage; i++) {
                    fileSaveActors.add(new saveActor(fileNames.get(i), GAME_WIDTH/2, GAME_HEIGHT/2 - 15 + (reductionCounter * 50)));
                    reductionCounter++;
                }
            }
        }

        return fileSaveActors;
    }

    public List<ButtonActor> generateUIButtons() {
        List<ButtonActor> buttonActors = new ArrayList<>();

        ButtonActor backToMainMenu = new ButtonActor("images/menu/menuButtons/backToMainMenu.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                gameWorld.switchGameMode(new GameModeMenu());
            }
        }, GAME_WIDTH/2, GAME_HEIGHT/2 + 450);
        buttonActors.add(backToMainMenu);

        ButtonActor arrowLeft = new ButtonActor("images/menu/menuButtons/arrowLeft.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (indexOfActualPage == 1) {
                    indexOfActualPage = numberOfPages;
                    gameWorld.switchGameMode(new GameModeSelectingSave(indexOfActualPage));
                } else {
                    indexOfActualPage--;
                    gameWorld.switchGameMode(new GameModeSelectingSave(indexOfActualPage));
                }
            }
        }, GAME_WIDTH/2 - 100, GAME_HEIGHT/2 + 360);
        buttonActors.add(arrowLeft);

        ButtonActor arrowRight = new ButtonActor("images/menu/menuButtons/arrowRight.png", new ButtonActor.GreenfootButtonOnClickListener() {
            @Override
            public void onClick(GameWorld gameWorld) {
                if (indexOfActualPage == numberOfPages) {
                    indexOfActualPage = 1;
                    gameWorld.switchGameMode(new GameModeSelectingSave(indexOfActualPage));
                } else {
                    indexOfActualPage++;
                    gameWorld.switchGameMode(new GameModeSelectingSave(indexOfActualPage));
                }
            }
        }, GAME_WIDTH/2 + 100, GAME_HEIGHT/2 + 360);
        buttonActors.add(arrowRight);

        return buttonActors;
    }

    @Override
    public String getBackgroundPath() {
        return MENU_BACKGROUND;
    }

    public int getNumberOfFilesInSavesDir () {
        int counterOfFiles = 0;
        String path = "saves";
        File file = new File(path);

        String str[] = file.list();
        for (String s: str) {
            File fls = new File(file, s);
            if (fls.isFile()) {
                counterOfFiles++;
                this.fileNames.add(fls.getName());
            }
        }

        return counterOfFiles;
    }

    public List<Integer> getNumberOfPages () {
        List<Integer> pageSettings = new ArrayList<>();
        int counterOfPages = 1;
        int residue = numberOfFiles;
        while (residue > 7) {
            residue -= 7;
            counterOfPages++;
        }

        pageSettings.add(counterOfPages);
        pageSettings.add(residue);

        return pageSettings;
    }
}

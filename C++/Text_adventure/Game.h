//
// Created by nikol on 19.11.2020.
//

#ifndef PROJEKT_GAME_H
#define PROJEKT_GAME_H

#include <fstream>
#include "Map.h"
#include "Characters/Hero.h"
#include "StoryTeller.h"

class Game {
    Map* m_map;
    Hero* m_hero;
    StoryTeller* m_story;
    static int m_difficulty;
public:
    Game();
    void startGame();
private:
    void chooseDifficulty();
    void setupGame();
    void printStart();
    void mapControl();
    void printActionMenu();
    void chooseAction();
    char getPlayerInput();
    void printFromFile(std::string fileLocation);
    void printHelp();
    void pauseMenu();
    void gameEnding();
public:
    static int getDifficulty();
    ~Game();
};


#endif //PROJEKT_GAME_H

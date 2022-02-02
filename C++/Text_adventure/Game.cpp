//
// Created by nikol on 19.11.2020.
//

#include "Game.h"

int Game::m_difficulty = 0;

Game::Game(){
}

void Game::startGame(){
    std::cout << "Main menu\n";
    std::cout << "\t[1] Start game\n";
    std::cout << "\t[2] Print help\n";
    std::cout << "\t[3] Exit game\n";
    int choice;

    std::cin >> choice;
    if (choice == 1){
        chooseDifficulty();
        setupGame();
        printStart();
        mapControl();
    } else if (choice == 2){
        printHelp();
        startGame();
    } else if (choice == 3){
        exit(0);
    } else {
        std::cout << "That is not an option\n";
        startGame();
    }
}

void Game::chooseDifficulty(){
    std::cout << "Choose difficulty:\n";
    std::cout << "\t[1] Easy\n";
    std::cout << "\t[2] Normal\n";
    std::cout << "\t[3] Hard\n";

    int choice;
    std::cin >> choice;
    if (choice < 1 or choice > 3){
        std::cout << "That is not an option\n";
        chooseDifficulty();
    }
    m_difficulty = choice;
}

void Game::setupGame(){
    m_hero = new Hero;
    m_map = new Map;
    m_story = new StoryTeller;
}

void Game::printStart() {
    m_story->printChapterOne();
    std::string heroName;
    std::cout << "Tell me, what is your name?\n";
    std::cin.ignore();
    getline(std::cin,heroName);
    //std::cin >> heroName;
    m_hero->setHeroName(heroName);
    std::cout << "\nDid you say " << m_hero->getName() << "? What a wonderful name!\n";

    heroAttributes attributes = m_story->chooseHeroAttributes();
    m_hero->setAllHeroAttributes(attributes.strength, attributes.agility, attributes.charisma);
    m_hero->checkInteractions();
}

char Game::getPlayerInput(){
    char playerInput;
    std::cin >> playerInput;
    return playerInput;
}

void Game::mapControl(){
    while (m_map->getCurrentLocationIndex() < m_map->getLocations().size()-1) {
        if (m_map->getCurrentLocationIndex() != 0 and !m_map->getVisited()) {
            m_story->printChapter(m_map->getCurrentLocationIndex() + 1);
            m_map->setVisited();
        }
        m_map->printLocationInfo();
        printActionMenu();
        m_map->printSideLocations();
    }
    gameEnding();
}

void Game::gameEnding(){
    m_story->printChapter(8);
    Enemy* lich = new Enemy(10);
    if (!m_hero->fightEnemy(lich)){
        printFromFile("../TextFiles/Death.txt");
    }
    printFromFile("../TextFiles/Epilog.txt");
}

void Game::printActionMenu(){
    while (m_map->getTileType() != tileType::exit) {
        m_hero->printBasicInfo("location");
        m_map->printLocation();
        if (m_map->getEnemy() != nullptr){
            std::cout << "You have been attacked! \n";
            if (!m_hero->fightEnemy(m_map->getEnemy())){
                printFromFile("../TextFiles/Death.txt");
                exit(0);
            }
            m_map->removeEnemy();
        } else {
            std::cout << "What do you want to do? \n";
            std::cout << "\t[1] Move on tile \n";
            if (m_map->getChest() != nullptr){
                std::cout << "\t[2] Open chest ";
                if (m_map->getChest()->getLocked()){
                    std::cout << "- Locked - Key " << m_map->getChest()->getID() << "\n";
                } else {
                    std::cout << "- Unlocked\n";
                }
            }
            if (m_map->getFriendlyCharacter() != nullptr){
                std::cout << "\t[2] Interact witch NPC \n";
            }
            std::cout << "\t[H] Print hero statistics \n";
            std::cout << "\t[I] Manage inventory \n";
            std::cout << "\t[S] Skill Tree \n";
            std::cout << "\t[M] Print World Map \n";
            std::cout << "\t[P] Pause game \n";
            chooseAction();
        }
    }
}

void Game::chooseAction(){
    char playerInput = getPlayerInput();
    if (playerInput == '1'){
        m_map->printTileSides();
    } else if (playerInput == '2' and m_map->getChest() != nullptr){
        bool emptyChest = m_hero->unlockChest(m_map->getChest());
        if (emptyChest) {
            m_map->removeChest();
        }
    } else if (playerInput == '2' and m_map->getFriendlyCharacter() != nullptr) {
        m_hero->makeInteraction(m_map->getFriendlyCharacter());
    } else if (playerInput == 'H') {
        m_hero->printAllInfo();
    } else if (playerInput == 'I'){
        m_hero->manageInventory();
    } else if (playerInput == 'M') {
        printFromFile("../TextFiles/Map.txt");
    } else if (playerInput == 'P'){
        pauseMenu();
    } else if (playerInput == 'S'){
        printFromFile("../TextFiles/SkillTree.txt");
    } else {
        std::cout << "That is not an option\n";
    }
}

int Game::getDifficulty(){
    return m_difficulty;
}

void Game::printFromFile(std::string fileLocation){
    std::string line;
    std::ifstream input (fileLocation);
    if(input.is_open()) {
        while(!input.eof()) {
            std::getline(input, line);
            std::cout << line << std::endl;
        }
        input.close();
    }
}

void Game::pauseMenu(){
    std::cout << "Pause menu\n";
    std::cout << "\t[1] Resume\n";
    std::cout << "\t[2] Print help\n";
    std::cout << "\t[3] Exit game\n";
    int choice;

    std::cin >> choice;
    if (choice == 1){
        return;
    } else if (choice == 2){
        printHelp();
        pauseMenu();
    } else if (choice == 3){
        std::cout << "Thank you for playing" << std::endl;
        exit(0);
    } else {
        std::cout << "That is not an option\n";
        pauseMenu();
    }
}

void Game::printHelp(){
    std::cout << "Help menu\n";
    std::cout << "For what help do you search?" << std::endl;
    std::cout << "\t[1] Prologue \n";
    std::cout << "\t[2] Action menu \n";
    std::cout << "\t[3] Location map \n";
    std::cout << "\t[4] Fighting system \n";

    char playerInput = getPlayerInput();
    if (playerInput == '1') {
        printFromFile("../TextFiles/Help/Prologue.txt");
    } else if (playerInput == '2') {
        printFromFile("../TextFiles/Help/ActionMenu.txt");
    } else if (playerInput == '3') {
        printFromFile("../TextFiles/Help/LocationMap.txt");
    } else if (playerInput == '4') {
        printFromFile("../TextFiles/Help/FightingSystem.txt");
    } else {
        printHelp();
    }
}

Game::~Game(){
    delete m_map;
    delete m_hero;
    delete m_story;
}
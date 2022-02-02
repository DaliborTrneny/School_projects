//
// Created by nikol on 19.11.2020.
//

#include "Location.h"

Location::Location(std::string name) {
    m_name = name;
    m_currentTileCoor.x = 0;
    m_currentTileCoor.y = 0;
    m_visited = false;
}

sideLocations Location::getSideLocations(){
    return m_sideLocations;
}

void Location::setTiles(std::vector<std::vector<Tile*>> tiles){
    m_tiles = tiles;
}

void Location::setSideLocations(sideLocations sides){
    m_sideLocations = sides;
}

void Location::resetTileCoordinates(){
    m_currentTileCoor.x = 0;
    m_currentTileCoor.y = 0;
}

void Location::setVisited(){
    m_visited = true;
}

bool Location::getVisited(){
    return m_visited;
}

void Location::printInfo(){
    std::cout << m_name << std::endl;
}

void Location::printTiles(){
    bool printHero = false;
    for (int i = 0; i < m_tiles.size(); i++){ // auto = std::vector<Tile*>
        for (int j = 0; j < m_tiles.at(0).size(); j++){
            if (m_currentTileCoor.x == i and m_currentTileCoor.y == j)
                printHero = true;
            m_tiles.at(i).at(j)->printTile(printHero);
            printHero = false;
            std::cout << " ";
        }
        std::cout << std::endl;
    }
}

void Location::printTileSides(){
    possibleMove move;
    if (m_currentTileCoor.x > 0){
        if (m_tiles.at(m_currentTileCoor.x-1).at(m_currentTileCoor.y)->getTileType() != tileType::unAccessible) {
            std::cout << "You can go [U]p\n";
            move.north = true;
        }
    }
    if (m_currentTileCoor.y < m_tiles.at(0).size()-1){
        if (m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y+1)->getTileType() != tileType::unAccessible) {
            std::cout << "You can go [R]ight\n";
            move.east = true;
        }
    }
    if (m_currentTileCoor.x < m_tiles.size()-1){
        if (m_tiles.at(m_currentTileCoor.x+1).at(m_currentTileCoor.y)->getTileType() != tileType::unAccessible) {
            std::cout << "You can go [D]own\n";
            move.south = true;
        }
    }
    if (m_currentTileCoor.y > 0){
        if (m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y-1)->getTileType() != tileType::unAccessible){
        std::cout << "You can go [L]eft\n";
        move.west = true;
        }
    }
    switchTile(move);
}

void Location::switchTile(possibleMove move){
    char playerOption;
    std::cin >> playerOption;

    if (playerOption == 'U' and move.north){
        m_currentTileCoor.x--;
    } else if (playerOption == 'R' and move.east){
        m_currentTileCoor.y++;
    } else if (playerOption == 'D' and move.south){
        m_currentTileCoor.x++;
    } else if (playerOption == 'L' and move.west){
        m_currentTileCoor.y--;
    } else {
        std::cout << "You cant go there\n";
    }
}

std::string Location::getLocationName(){
    return m_name;
}

tileType Location::getTileType(){
    return m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->getTileType();
}

Enemy* Location::getEnemy(){
    return m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->getEnemy();
}

Chest* Location::getChest(){
    return m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->getChest();
}

FriendlyCharacter* Location::getFriendlyCharacter(){
    return m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->getFriendlyCharacter();
}

void Location::removeChest(){
    m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->removeChest();
}

void Location::removeEnemy(){
    m_tiles.at(m_currentTileCoor.x).at(m_currentTileCoor.y)->removeEnemy();
}
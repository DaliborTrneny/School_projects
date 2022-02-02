//
// Created by nikol on 19.11.2020.
//

#include "Map.h"

Map::Map(){
    m_currentLocationIndex = 0;
    createMap();
}

void Map::createMap(){
    LocationDirector* director = new LocationDirector(new HutLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new ForestLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new TownLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new BogLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new WoodsLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new CavernLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new TombLocationBuilder);
    m_locations.push_back(director->createLocation());
    director->setLocationBuilder(new NecroLocationBuilder);
    m_locations.push_back(director->createLocation());
}

int Map::getCurrentLocationIndex(){
    return m_currentLocationIndex;
}

std::vector<Location*> Map::getLocations(){
    return m_locations;
}

void Map::printLocationInfo(){
    std::cout << "You are now in location: ";
    m_locations.at(m_currentLocationIndex)->printInfo();
}

void Map::printSideLocations(){
    std::cout << "You can go to: \n";
    sideLocations sides = m_locations.at(m_currentLocationIndex)->getSideLocations();
    possibleMove move;
    if (sides.north >= 0){
        std::cout << "\t[N]orth - " << m_locations.at(sides.north)->getLocationName() << std::endl;
        move.north = true;
    }
    if (sides.east >= 0) {
        std::cout << "\t[E]ast - " << m_locations.at(sides.east)->getLocationName() << std::endl;
        move.east = true;
    }
    if (sides.south >= 0) {
        std::cout << "\t[S]outh - " << m_locations.at(sides.south)->getLocationName() << std::endl;
        move.south = true;
    }
    if (sides.west >= 0) {
        std::cout << "\t[W]est - " << m_locations.at(sides.west)->getLocationName() << std::endl;
        move.west = true;
    }
    switchLocation(move);
}

void Map::switchLocation(possibleMove move){
    char playerOption;
    std::cin >> playerOption;
    sideLocations sides = m_locations.at(m_currentLocationIndex)->getSideLocations();

    if (playerOption == 'N' and move.north){
        m_currentLocationIndex = sides.north;
    } else if (playerOption == 'E' and move.east){
        m_currentLocationIndex = sides.east;
    } else if (playerOption == 'S' and move.south){
        m_currentLocationIndex = sides.south;
    } else if (playerOption == 'W' and move.west){
        m_currentLocationIndex = sides.west;
    } else {
        std::cout << "You cant go there\n";
    }
    m_locations.at(m_currentLocationIndex)->resetTileCoordinates();
}

void Map::printLocation(){
    m_locations.at(m_currentLocationIndex)->printTiles();
}

void Map::printTileSides(){
    m_locations.at(m_currentLocationIndex)->printTileSides();
}

void Map::setVisited(){
    m_locations.at(m_currentLocationIndex)->setVisited();
}

bool Map::getVisited(){
    return m_locations.at(m_currentLocationIndex)->getVisited();
}

tileType Map::getTileType(){
    return m_locations.at(m_currentLocationIndex)->getTileType();
}

Enemy* Map::getEnemy(){
    return m_locations.at(m_currentLocationIndex)->getEnemy();
}

Chest* Map::getChest(){
    return m_locations.at(m_currentLocationIndex)->getChest();
}

FriendlyCharacter* Map::getFriendlyCharacter(){
    return m_locations.at(m_currentLocationIndex)->getFriendlyCharacter();
}

void Map::removeChest(){
    m_locations.at(m_currentLocationIndex)->removeChest();
}

void Map::removeEnemy(){
    m_locations.at(m_currentLocationIndex)->removeEnemy();
}

Map::~Map(){
    for (auto &location:m_locations){
        delete location;
    }
}
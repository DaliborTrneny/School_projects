//
// Created by nikol on 19.11.2020.
//

#ifndef PROJEKT_MAP_H
#define PROJEKT_MAP_H

#include <vector>
#include "LocationDirector.h"

class Map {
    std::vector<Location*> m_locations;
    int m_currentLocationIndex;
public:
    Map();
    void createMap();
    int getCurrentLocationIndex();
    std::vector<Location*> getLocations();
    void printLocationInfo();
    void printSideLocations();
    void switchLocation(possibleMove move);
    void printLocation();
    void printTileSides();
    void setVisited();
    bool getVisited();
    tileType getTileType();
    Enemy* getEnemy();
    Chest* getChest();
    FriendlyCharacter* getFriendlyCharacter();
    void removeChest();
    void removeEnemy();
    ~Map();
};


#endif //PROJEKT_MAP_H

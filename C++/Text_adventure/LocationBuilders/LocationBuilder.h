//
// Created by nikol on 02.12.2020.
//

#ifndef MAIN_CPP_LOCATIONBUILDER_H
#define MAIN_CPP_LOCATIONBUILDER_H

#include "../Location.h"

const int noDirection = -1;

class LocationBuilder {
protected:
    Location* m_location;
    std::vector<std::vector<Tile*>> m_tiles;
public:
    LocationBuilder();
    void virtual createLocation() = 0;
    void virtual setEnemies() = 0;
    void virtual setChests() = 0;
    void virtual setFriendlyCharacters() = 0;
    void virtual createSideLocations() = 0;
    void setTiles();
    Location* getLocation();
};


#endif //MAIN_CPP_LOCATIONBUILDER_H

//
// Created by dalib on 06.12.2020.
//

#ifndef MAIN_CPP_TOMBLOCATIONBUILDER_H
#define MAIN_CPP_TOMBLOCATIONBUILDER_H

#include "LocationBuilder.h"

class TombLocationBuilder: public LocationBuilder {
public:
    TombLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_TOMBLOCATIONBUILDER_H

//
// Created by nikol on 02.12.2020.
//

#ifndef MAIN_CPP_FORESTLOCATIONBUILDER_H
#define MAIN_CPP_FORESTLOCATIONBUILDER_H

#include "LocationBuilder.h"

class ForestLocationBuilder: public LocationBuilder {
public:
    ForestLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_FORESTLOCATIONBUILDER_H

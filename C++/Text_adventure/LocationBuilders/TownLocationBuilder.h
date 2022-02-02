//
// Created by nikol on 02.12.2020.
//

#ifndef MAIN_CPP_TOWNLOCATIONBUILDER_H
#define MAIN_CPP_TOWNLOCATIONBUILDER_H

#include "LocationBuilder.h"

class TownLocationBuilder: public LocationBuilder {
public:
    TownLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_TOWNLOCATIONBUILDER_H

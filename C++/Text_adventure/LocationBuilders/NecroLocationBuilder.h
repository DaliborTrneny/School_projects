//
// Created by dalib on 06.12.2020.
//

#ifndef MAIN_CPP_NECROLOCATIONBUILDER_H
#define MAIN_CPP_NECROLOCATIONBUILDER_H

#include "LocationBuilder.h"

class NecroLocationBuilder: public LocationBuilder {
public:
    NecroLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_NECROLOCATIONBUILDER_H

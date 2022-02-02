//
// Created by dalib on 06.12.2020.
//

#ifndef MAIN_CPP_BOGLOCATIONBUILDER_H
#define MAIN_CPP_BOGLOCATIONBUILDER_H

#include "LocationBuilder.h"

class BogLocationBuilder: public LocationBuilder {
public:
    BogLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_BOGLOCATIONBUILDER_H

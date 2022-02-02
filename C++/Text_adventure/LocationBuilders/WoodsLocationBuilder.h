//
// Created by dalib on 06.12.2020.
//

#ifndef MAIN_CPP_WOODSLOCATIONBUILDER_H
#define MAIN_CPP_WOODSLOCATIONBUILDER_H

#include "LocationBuilder.h"

class WoodsLocationBuilder: public LocationBuilder {
public:
    WoodsLocationBuilder();
    void createLocation();
    void setEnemies();
    void setChests();
    void setFriendlyCharacters();
    void createSideLocations();
};


#endif //MAIN_CPP_WOODSLOCATIONBUILDER_H

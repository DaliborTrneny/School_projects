//
// Created by dalib on 06.12.2020.
//

#include "NecroLocationBuilder.h"

NecroLocationBuilder::NecroLocationBuilder(){
}

void NecroLocationBuilder::createLocation(){
    m_location = new Location("Dead Man's End");
}

void NecroLocationBuilder::setEnemies(){
}

void NecroLocationBuilder::setChests(){
}

void NecroLocationBuilder::setFriendlyCharacters(){
}

void NecroLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({noDirection, noDirection, 6, noDirection});
}
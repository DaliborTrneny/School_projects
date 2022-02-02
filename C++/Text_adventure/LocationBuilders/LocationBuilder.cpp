//
// Created by nikol on 02.12.2020.
//

#include "LocationBuilder.h"

LocationBuilder::LocationBuilder(){
    m_location = nullptr;
}

Location* LocationBuilder::getLocation(){
    return m_location;
}

void LocationBuilder::setTiles(){
    m_location->setTiles(m_tiles);
}
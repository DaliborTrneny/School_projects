//
// Created by nikol on 02.12.2020.
//

#include "LocationDirector.h"

LocationDirector::LocationDirector(LocationBuilder* builder){
    m_builder = builder;
}

void LocationDirector::setLocationBuilder(LocationBuilder* builder){
    m_builder = builder;
}

Location* LocationDirector::createLocation(){
    m_builder->createLocation();
    m_builder->setEnemies();
    m_builder->setChests();
    m_builder->setFriendlyCharacters();
    m_builder->createSideLocations();
    m_builder->setTiles();
    return m_builder->getLocation();
}
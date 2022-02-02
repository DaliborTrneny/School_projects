//
// Created by dalib on 06.12.2020.
//

#include "CavernLocationBuilder.h"

CavernLocationBuilder::CavernLocationBuilder(){
}

void CavernLocationBuilder::createLocation(){
    m_location = new Location("Bandit's Cavern");

    std::vector<Tile*> row1, row2, row3;
    row1.push_back(new CavernTile());
    row1.push_back(new CavernTile());
    row1.push_back(new CavernTile());
    row1.push_back(new CavernTile());
    row1.push_back(new CavernTile());
    row1.push_back(new ExitTile());

    row2.push_back(new CavernTile());
    row2.push_back(new CavernTile());
    row2.push_back(new CavernTile());
    row2.push_back(new TownTile());
    row2.push_back(new TownTile());
    row2.push_back(new TownTile());


    row3.push_back(new ForestTile());
    row3.push_back(new CavernTile());
    row3.push_back(new CavernTile());
    row3.push_back(new WaterTile());
    row3.push_back(new WaterTile());
    row3.push_back(new WaterTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
}

void CavernLocationBuilder::setEnemies(){
    m_tiles.at(0).at(2)->setEnemy(new Enemy(3));
    m_tiles.at(0).at(4)->setEnemy(new Enemy(4));
    m_tiles.at(1).at(2)->setEnemy(new Enemy(3));
    m_tiles.at(1).at(4)->setEnemy(new Enemy(3));
    m_tiles.at(2).at(1)->setEnemy(new Enemy(4));
}

void CavernLocationBuilder::setChests(){
    m_tiles.at(2).at(2)->setChest(new Chest(new Armor("Ursine Armor", 45, 33), true));
}

void CavernLocationBuilder::setFriendlyCharacters(){
}

void CavernLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({6, noDirection, noDirection, 4});
}
//
// Created by nikol on 02.12.2020.
//

#include "ForestLocationBuilder.h"

ForestLocationBuilder::ForestLocationBuilder(){
}

void ForestLocationBuilder::createLocation(){
    m_location = new Location("Whispering Forest");

    std::vector<Tile*> row1, row2, row3, row4;
    row1.push_back(new ForestTile());
    row1.push_back(new ForestTile());
    row1.push_back(new WaterTile());
    row1.push_back(new ExitTile());

    row2.push_back(new ForestTile());
    row2.push_back(new ForestTile());
    row2.push_back(new WaterTile());
    row2.push_back(new ForestTile());

    row3.push_back(new ForestTile());
    row3.push_back(new ForestTile());
    row3.push_back(new ForestTile());
    row3.push_back(new ForestTile());

    row4.push_back(new ForestTile());
    row4.push_back(new ForestTile());
    row4.push_back(new WaterTile());
    row4.push_back(new ForestTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
    m_tiles.push_back(row4);
}

void ForestLocationBuilder::setEnemies(){
    m_tiles.at(2).at(2)->setEnemy(new Enemy(2));
    m_tiles.at(2).at(0)->setEnemy(new Enemy(1));
}

void ForestLocationBuilder::setChests(){
    m_tiles.at(3).at(0)->setChest(new Chest(new Armor("Leather Armor", 10, 10), true));
    m_tiles.at(3).at(3)->setChest(new Chest(new Potion("Small Healing Potion", 12, 15), false));
}

void ForestLocationBuilder::setFriendlyCharacters(){
}

void ForestLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({noDirection, 3, 2, 0});
}
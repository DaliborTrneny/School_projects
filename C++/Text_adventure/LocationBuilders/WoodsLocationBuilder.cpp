//
// Created by dalib on 06.12.2020.
//

#include "WoodsLocationBuilder.h"

WoodsLocationBuilder::WoodsLocationBuilder(){
}

void WoodsLocationBuilder::createLocation(){
    m_location = new Location("Hollow Woods");

    std::vector<Tile*> row1, row2, row3, row4;
    row1.push_back(new ForestTile());
    row1.push_back(new TownTile());
    row1.push_back(new ForestTile());
    row1.push_back(new ForestTile());

    row2.push_back(new ForestTile());
    row2.push_back(new TownTile());
    row2.push_back(new ForestTile());
    row2.push_back(new ExitTile());

    row3.push_back(new ForestTile());
    row3.push_back(new WaterTile());
    row3.push_back(new ForestTile());
    row3.push_back(new ForestTile());

    row4.push_back(new ForestTile());
    row4.push_back(new ForestTile());
    row4.push_back(new ForestTile());
    row4.push_back(new ForestTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
    m_tiles.push_back(row4);
}

void WoodsLocationBuilder::setEnemies(){
    m_tiles.at(2).at(0)->setEnemy(new Enemy(1));
    m_tiles.at(3).at(1)->setEnemy(new Enemy(1));
    m_tiles.at(0).at(2)->setEnemy(new Enemy(5));
}

void WoodsLocationBuilder::setChests(){
    m_tiles.at(0).at(3)->setChest(new Chest(new Weapon("Ancient Bow", 35, 40), false));
    m_tiles.at(3).at(0)->setChest(new Chest(new Potion("Medium Healing Potion", 20, 30), false));
}

void WoodsLocationBuilder::setFriendlyCharacters(){
}

void WoodsLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({3, 5, noDirection, 2});
}
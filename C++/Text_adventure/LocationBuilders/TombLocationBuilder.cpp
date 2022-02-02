//
// Created by dalib on 06.12.2020.
//

#include "TombLocationBuilder.h"

TombLocationBuilder::TombLocationBuilder(){
}

void TombLocationBuilder::createLocation(){
    m_location = new Location("Ancient Tomb");

    std::vector<Tile*> row1, row2, row3, row4;
    row1.push_back(new TombTile());
    row1.push_back(new FireTile());
    row1.push_back(new TombTile());
    row1.push_back(new ExitTile());
    row1.push_back(new TombTile());

    row2.push_back(new TombTile());
    row2.push_back(new TombTile());
    row2.push_back(new TombTile());
    row2.push_back(new TombTile());
    row2.push_back(new TombTile());

    row3.push_back(new TombTile());
    row3.push_back(new TombTile());
    row3.push_back(new TownTile());
    row3.push_back(new TownTile());
    row3.push_back(new TownTile());

    row4.push_back(new TombTile());
    row4.push_back(new TombTile());
    row4.push_back(new TombTile());
    row4.push_back(new TombTile());
    row4.push_back(new TombTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
    m_tiles.push_back(row4);
}

void TombLocationBuilder::setEnemies(){
    m_tiles.at(0).at(2)->setEnemy(new Enemy(6));
    m_tiles.at(0).at(4)->setEnemy(new Enemy(5));
    m_tiles.at(1).at(2)->setEnemy(new Enemy(4));
    m_tiles.at(1).at(3)->setEnemy(new Enemy(6));
    m_tiles.at(2).at(1)->setEnemy(new Enemy(4));
    m_tiles.at(3).at(1)->setEnemy(new Enemy(4));
}

void TombLocationBuilder::setChests(){
    m_tiles.at(3).at(3)->setChest(new Chest(new Weapon("Holy Fucking Sword", 60, 52), true));
    m_tiles.at(3).at(0)->setChest(new Chest(new Potion("Medium Healing Potion", 20, 30), false));
}

void TombLocationBuilder::setFriendlyCharacters(){
    FriendlyCharacter* witchAmaryllis = new FriendlyCharacter("Amaryllis The Witch", 25, 7, 15, 120);
    Inventory* amaryllisInventory = new Inventory();
    amaryllisInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    amaryllisInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    amaryllisInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    amaryllisInventory->addKey(new Key(35, 4));
    witchAmaryllis->setInventory(amaryllisInventory);

    m_tiles.at(3).at(4)->setFriendlyCharacter(witchAmaryllis);
}

void TombLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({7, noDirection, 5, 3});
}
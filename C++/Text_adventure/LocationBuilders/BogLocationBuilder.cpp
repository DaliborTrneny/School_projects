//
// Created by dalib on 06.12.2020.
//

#include "BogLocationBuilder.h"

BogLocationBuilder::BogLocationBuilder(){
}

void BogLocationBuilder::createLocation(){
    m_location = new Location("Twisted Bog");

    std::vector<Tile*> row1, row2, row3, row4, row5;
    row1.push_back(new ForestTile());
    row1.push_back(new BogTile());
    row1.push_back(new BogTile());
    row1.push_back(new ForestTile());
    row1.push_back(new BogTile());

    row2.push_back(new BogTile());
    row2.push_back(new BogTile());
    row2.push_back(new BogTile());
    row2.push_back(new BogTile());
    row2.push_back(new BogTile());

    row3.push_back(new BogTile());
    row3.push_back(new WaterTile());
    row3.push_back(new WaterTile());
    row3.push_back(new WaterTile());
    row3.push_back(new BogTile());

    row4.push_back(new BogTile());
    row4.push_back(new BogTile());
    row4.push_back(new BogTile());
    row4.push_back(new BogTile());
    row4.push_back(new BogTile());

    row5.push_back(new BogTile());
    row5.push_back(new WaterTile());
    row5.push_back(new ExitTile());
    row5.push_back(new BogTile());
    row5.push_back(new BogTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
    m_tiles.push_back(row4);
    m_tiles.push_back(row5);
}

void BogLocationBuilder::setEnemies(){
    m_tiles.at(0).at(3)->setEnemy(new Enemy(2));
    m_tiles.at(1).at(4)->setEnemy(new Enemy(2));
    m_tiles.at(1).at(2)->setEnemy(new Enemy(1));
    m_tiles.at(2).at(0)->setEnemy(new Enemy(1));
    m_tiles.at(3).at(4)->setEnemy(new Enemy(1));
    m_tiles.at(4).at(0)->setEnemy(new Enemy(1));
    m_tiles.at(4).at(3)->setEnemy(new Enemy(1));
}

void BogLocationBuilder::setChests(){
    m_tiles.at(0).at(4)->setChest(new Chest(new Potion("Large Healing Potion", 50, 65), true));
}

void BogLocationBuilder::setFriendlyCharacters(){
    FriendlyCharacter* weasel = new FriendlyCharacter("Allyssa the Weasel", 3, 7, 5, 200);
    Inventory* weaselInventory = new Inventory();
    weaselInventory->addArmor(new Armor("Thief Leather Armor", 27, 26));
    weaselInventory->addWeapon(new Weapon("Thief Dagger", 12, 17));
    weaselInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    weaselInventory->addKey(new Key(35, 2));
    weasel->setInventory(weaselInventory);

    m_tiles.at(4).at(4)->setFriendlyCharacter(weasel);
}

void BogLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({noDirection, noDirection, 4, 1});
}
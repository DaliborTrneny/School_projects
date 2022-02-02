//
// Created by nikol on 03.12.2020.
//

#include "HutLocationBuilder.h"

HutLocationBuilder::HutLocationBuilder(){
}

void HutLocationBuilder::createLocation(){
    m_location = new Location("The Old Man's Hut");

    std::vector<Tile*> row1, row2, row3;
    row1.push_back(new HutTile());
    row1.push_back(new HutTile());
    row1.push_back(new HutTile());

    row2.push_back(new HutTile());
    row2.push_back(new FireTile());
    row2.push_back(new ExitTile());

    row3.push_back(new HutTile());
    row3.push_back(new HutTile());
    row3.push_back(new HutTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
}

void HutLocationBuilder::setEnemies(){
    m_tiles.at(2).at(2)->setEnemy(new Enemy(1));
}

void HutLocationBuilder::setChests(){
    m_tiles.at(0).at(2)->setChest(new Chest(new Weapon("Broken Sword", 7, 10), false));
    //m_tiles.at(1).at(0)->setChest(new Chest(new Potion("Small HP", 5, 15), true));
}

void HutLocationBuilder::setFriendlyCharacters(){
    FriendlyCharacter* oldMan = new FriendlyCharacter("Viggo", 5, 0, 10, 50);
    Inventory* oldManInventory = new Inventory();
    oldManInventory->addWeapon(new Weapon("Wolf axe", 78, 45));
    oldManInventory->addArmor(new Armor("Wolf armor", 60, 45));
    oldManInventory->addKey(new Key(8, 1));
    oldManInventory->addPotion(new Potion("Small Healing Potion", 0, 15));
    oldManInventory->addPotion(new Potion("Small Healing Potion", 0, 15));
    oldMan->setInventory(oldManInventory);

    m_tiles.at(2).at(0)->setFriendlyCharacter(oldMan);
}

void HutLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({noDirection, 1, noDirection, noDirection});
}
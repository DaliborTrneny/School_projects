//
// Created by nikol on 02.12.2020.
//

#include "TownLocationBuilder.h"

TownLocationBuilder::TownLocationBuilder(){
}

void TownLocationBuilder::createLocation(){
    m_location = new Location("Town");

    std::vector<Tile*> row1, row2, row3, row4;
    row1.push_back(new TownTile());
    row1.push_back(new TownTile());
    row1.push_back(new TownTile());
    row1.push_back(new WaterTile());

    row2.push_back(new TownTile());
    row2.push_back(new TownTile());
    row2.push_back(new TownTile());
    row2.push_back(new WaterTile());

    row3.push_back(new TownTile());
    row3.push_back(new TownTile());
    row3.push_back(new TownTile());
    row3.push_back(new ExitTile());

    row4.push_back(new TownTile());
    row4.push_back(new TownTile());
    row4.push_back(new TownTile());
    row4.push_back(new TownTile());

    m_tiles.push_back(row1);
    m_tiles.push_back(row2);
    m_tiles.push_back(row3);
    m_tiles.push_back(row4);
}

void TownLocationBuilder::setEnemies(){
}

void TownLocationBuilder::setChests(){
}

void TownLocationBuilder::setFriendlyCharacters(){
    FriendlyCharacter* smith = new FriendlyCharacter("Oswald", 5, 12, 8, 250);
    Inventory* smithInventory = new Inventory();
    smithInventory->addArmor(new Armor("Chainmail", 20, 15));
    smithInventory->addArmor(new Armor("Plate armor", 30, 25));
    smithInventory->addArmor(new Armor("Royal Knight armor", 35, 28));
    smithInventory->addArmor(new Armor("Leather armor with plates", 45, 37));
    smithInventory->addArmor(new Armor("Thorn mail", 40, 32));
    smithInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    smith->setInventory(smithInventory);

    m_tiles.at(2).at(0)->setFriendlyCharacter(smith);

    FriendlyCharacter* weaponSmith = new FriendlyCharacter("Radulfr", 5, 8, 3, 100);
    Inventory* weaponSmithInventory = new Inventory();
    weaponSmithInventory->addWeapon(new Weapon("Short sword", 15, 15));
    weaponSmithInventory->addWeapon(new Weapon("Long sword", 19, 20));
    weaponSmithInventory->addWeapon(new Weapon("Mace", 25, 24));
    weaponSmithInventory->addWeapon(new Weapon("Spear", 30, 29));
    weaponSmithInventory->addWeapon(new Weapon("Falchion", 40, 35));
    weaponSmithInventory->addWeapon(new Weapon("Knight Straight Sword", 43, 38));
    weaponSmithInventory->addWeapon(new Weapon("Claymore", 47, 40));
    weaponSmithInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    weaponSmith->setInventory(weaponSmithInventory);

    m_tiles.at(3).at(1)->setFriendlyCharacter(weaponSmith);

    FriendlyCharacter* alchemist = new FriendlyCharacter("Yoana", 15, 4, 7, 100);
    Inventory* alchemistInventory = new Inventory();
    alchemistInventory->addPotion(new Potion("Small Healing Potion", 12, 15));
    alchemistInventory->addPotion(new Potion("Small Healing Potion", 12, 15));
    alchemistInventory->addPotion(new Potion("Small Healing Potion", 12, 15));
    alchemistInventory->addPotion(new Potion("Small Healing Potion", 12, 15));
    alchemistInventory->addPotion(new Potion("Small Healing Potion", 12, 15));
    alchemistInventory->addPotion(new Potion("Medium Healing Potion", 20, 30));
    alchemistInventory->addPotion(new Potion("Medium Healing Potion", 20, 30));
    alchemistInventory->addPotion(new Potion("Medium Healing Potion", 20, 30));
    alchemistInventory->addPotion(new Potion("Large Healing Potion", 50, 65));
    alchemistInventory->addPotion(new Potion("Large Healing Potion", 45, 65));
    alchemistInventory->addKey(new Key(35, 3));
    alchemist->setInventory(alchemistInventory);

    m_tiles.at(0).at(2)->setFriendlyCharacter(alchemist);
}

void TownLocationBuilder::createSideLocations(){
    //north, east, south, west;
    m_location->setSideLocations({1, 4, noDirection, noDirection});
}
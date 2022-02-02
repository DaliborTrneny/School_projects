//
// Created by nikol on 06.12.2020.
//

#ifndef MAIN_CPP_INVENTORY_H
#define MAIN_CPP_INVENTORY_H

#include <vector>

#include "Weapon.h"
#include "Armor.h"
#include "Potion.h"
#include "Key.h"

class Inventory {
    std::vector<Weapon*> m_weapons;
    std::vector<Armor*> m_armors;
    std::vector<Potion*> m_potions;
    std::vector<Key*> m_keys;
public:
    Inventory();
    void addWeapon(Weapon* weapon);
    void addArmor(Armor* armor);
    void addPotion(Potion* potion);
    void addKey(Key* key);
    Weapon* getWeapon(int choice);
    Armor* getArmor(int choice);
    Potion* getPotion(int choice);
    Key* getKey(int choice);
    Key* getKeyToUnlock(int chestID);
    void discardItem(int choice, itemType typeOfItem);
    void printInventory();
    template <class T>
    void printInventoryItems(T items);
    ~Inventory();
private:
    template <class T>
    void discardItem(T &items, int choice);
};


#endif //MAIN_CPP_INVENTORY_H

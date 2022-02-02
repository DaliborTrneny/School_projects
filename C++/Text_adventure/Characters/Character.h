//
// Created by nikol on 07.12.2020.
//

#ifndef MAIN_CPP_CHARACTER_H
#define MAIN_CPP_CHARACTER_H

#include <iostream>
#include "Inventory.h"

class Character {
protected:
    std::string m_name;
    int m_strength;
    int m_agility;
    int m_charisma;
    int m_coins;
    Inventory* m_inventory;
public:
    Character();
    int getStrength();
    int getAgility();
    int getCharisma();
    int getCoins();
    std::string getName();
    void printInventory();
    Inventory* getInventory();
    ~Character();
};


#endif //MAIN_CPP_CHARACTER_H

//
// Created by nikol on 04.12.2020.
//

#ifndef MAIN_CPP_ARMOR_H
#define MAIN_CPP_ARMOR_H

#include "Item.h"

class Armor: public Item {
    int m_armorBonus;
public:
    Armor(std::string name, int price, int armorBonus);
    int getArmorBonus();
    void printInfo();
};


#endif //MAIN_CPP_ARMOR_H

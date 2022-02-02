//
// Created by nikol on 23.12.2020.
//

#ifndef MAIN_CPP_KEY_H
#define MAIN_CPP_KEY_H

#include "Item.h"

class Key: public Item {
    int m_chestID;
public:
    Key(int price, int chestID);
    int getChestID();
    void printInfo();
};


#endif //MAIN_CPP_KEY_H

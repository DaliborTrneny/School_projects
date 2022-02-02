//
// Created by nikol on 07.12.2020.
//

#ifndef MAIN_CPP_FRIENDLYCHARACTER_H
#define MAIN_CPP_FRIENDLYCHARACTER_H

#include "Character.h"

class FriendlyCharacter: public Character {
public:
    FriendlyCharacter(std::string name, int strength, int agility, int charisma, int coins);
    void setInventory(Inventory* inventory);
    void removeCoins();
};


#endif //MAIN_CPP_FRIENDLYCHARACTER_H

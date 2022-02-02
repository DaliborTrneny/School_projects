//
// Created by nikol on 04.12.2020.
//

#ifndef MAIN_CPP_CHEST_H
#define MAIN_CPP_CHEST_H

#include "Weapon.h"
#include "Armor.h"
#include "Potion.h"

class Chest {
    Weapon* m_weapon;
    Armor* m_armor;
    Potion* m_potion;
    static int s_numberOfLockedChests;
    int m_id;
    bool m_locked;

    Chest(Weapon* weapon, Armor* armor, Potion* potion, bool locked);
public:
    Chest(Weapon* weapon, bool locked);
    Chest(Armor* armor, bool locked);
    Chest(Potion* potion, bool locked);
    Weapon* getWeapon();
    Armor* getArmor();
    Potion* getPotion();
    int getID();
    bool getLocked();
    void unlock();
    void removeContent();
    ~Chest();
};


#endif //MAIN_CPP_CHEST_H

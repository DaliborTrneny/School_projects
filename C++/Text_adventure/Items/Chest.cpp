//
// Created by nikol on 04.12.2020.
//

#include "Chest.h"

int Chest::s_numberOfLockedChests = 0;

Chest::Chest(Weapon* weapon, Armor* armor, Potion* potion, bool locked){
    m_weapon = weapon;
    m_armor = armor;
    m_potion = potion;
    m_locked = locked;
    if (m_locked){
        m_id = ++s_numberOfLockedChests;
    } else {
        m_id = 0;
    }
}

Chest::Chest(Weapon* weapon, bool locked): Chest(weapon, nullptr, nullptr, locked){
}

Chest::Chest(Armor* armor, bool locked): Chest(nullptr, armor, nullptr, locked){
}

Chest::Chest(Potion* potion, bool locked): Chest(nullptr, nullptr, potion, locked){
}

Weapon* Chest::getWeapon(){
    return m_weapon;
}

Armor* Chest::getArmor(){
    return m_armor;
}

Potion* Chest::getPotion(){
    return m_potion;
}

int Chest::getID(){
    return m_id;
}

bool Chest::getLocked(){
    return m_locked;
}

void Chest::unlock(){
    m_locked = false;
}

void Chest::removeContent(){
    if (m_armor != nullptr){
        m_armor = nullptr;
    } else if (m_weapon != nullptr){
        m_weapon = nullptr;
    } else if (m_potion != nullptr){
        m_potion = nullptr;
    }
}

Chest::~Chest(){
    if (m_weapon != nullptr){
        delete m_weapon;
    }
    if (m_armor != nullptr){
        delete m_armor;
    }
    if (m_potion != nullptr){
        delete m_potion;
    }
}
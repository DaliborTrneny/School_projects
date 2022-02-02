//
// Created by nikol on 07.12.2020.
//

#include "Character.h"

Character::Character() {
    m_inventory = new Inventory();
}

int Character::getStrength(){
    return m_strength;
}

int Character::getAgility(){
    return m_agility;
}

int Character::getCharisma(){
    return m_charisma;
}

int Character::getCoins(){
    return m_coins;
}

std::string Character::getName(){
    return m_name;
}

void Character::printInventory(){
    m_inventory->printInventory();
}

Inventory* Character::getInventory(){
    return m_inventory;
}

Character::~Character(){
    delete m_inventory;
}
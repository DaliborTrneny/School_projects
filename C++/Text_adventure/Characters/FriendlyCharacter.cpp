//
// Created by nikol on 07.12.2020.
//

#include "FriendlyCharacter.h"

FriendlyCharacter::FriendlyCharacter(std::string name, int strength, int agility, int charisma, int coins){
    m_name = name;
    m_strength = strength;
    m_agility = agility;
    m_charisma = charisma;
    m_coins = coins;
    m_inventory = new Inventory();
}

void FriendlyCharacter::setInventory(Inventory* inventory){
    m_inventory = inventory;
}

void FriendlyCharacter::removeCoins(){
    m_coins = 0;
}
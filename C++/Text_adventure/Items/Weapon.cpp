//
// Created by nikol on 04.12.2020.
//

#include "Weapon.h"

Weapon::Weapon(std::string name, int price, int damageBonus): Item(name, price, itemType::weapon){
    m_damageBonus = damageBonus;
}

int Weapon::getDamageBonus(){
    return m_damageBonus;
}

void Weapon::printInfo(){
    std::cout << "\tName: " << m_name << "\n";
    std::cout << "\tDamage: " << m_damageBonus << "\n";
    std::cout << "\tPrice: " << m_price << " C\n";
}
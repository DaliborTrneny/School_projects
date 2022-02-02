//
// Created by nikol on 04.12.2020.
//

#include "Potion.h"

Potion::Potion(std::string name, int price, int healthBonus): Item(name, price, itemType::potion){
    m_healthBonus = healthBonus;
}

int Potion::getHealthBonus(){
    return m_healthBonus;
}

void Potion::printInfo(){
    std::cout << "\tName: " << m_name << "\n";
    std::cout << "\tHP regenerate: " << m_healthBonus << "\n";
    std::cout << "\tPrice: " << m_price << " C\n";
}
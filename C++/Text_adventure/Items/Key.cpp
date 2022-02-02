//
// Created by nikol on 23.12.2020.
//

#include "Key.h"

Key::Key(int price, int chestID): Item("Key", price, itemType::key){
    m_chestID = chestID;
}

int Key::getChestID(){
    return m_chestID;
}

void Key::printInfo(){
    std::cout << "\tName: " << m_name << " " << m_chestID << "\n";
    std::cout << "\tPrice: " << m_price << " C\n";
}
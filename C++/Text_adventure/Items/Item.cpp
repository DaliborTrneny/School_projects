//
// Created by nikol on 04.12.2020.
//

#include "Item.h"

Item::Item(std::string name, int price, itemType typeOfItem){
    m_name = name;
    m_price = price;
    m_itemType = typeOfItem;
}

std::string Item::getName(){
    return m_name;
}

int Item::getPrice(){
    return m_price;
}

itemType Item::getItemType(){
    return m_itemType;
}
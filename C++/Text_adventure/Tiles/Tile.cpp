//
// Created by nikol on 24.11.2020.
//

#include "Tile.h"

Tile::Tile(tileType type){
    m_type = type;
    m_enemy = nullptr;
    m_chest = nullptr;
    m_friendlyCharacter = nullptr;
}

void Tile::printCharacter(std::string tileSymbol, bool printHero){
    if (printHero){
        std::cout << "**";
    } else if (m_enemy != nullptr) {
        std::cout << "E" << m_enemy->getEnemyLevel();
    } else if (m_friendlyCharacter != nullptr){
        std::cout << "FR";
    } else if (m_chest != nullptr){
        std::cout << "CH";
    } else {
        std::cout << tileSymbol;
    }
}

tileType Tile::getTileType(){
    return m_type;
}

void Tile::setEnemy(Enemy* enemy){
    m_enemy = enemy;
}

void Tile::setChest(Chest* chest){
    m_chest = chest;
}

void Tile::setFriendlyCharacter(FriendlyCharacter* friendlyCharacter){
    m_friendlyCharacter = friendlyCharacter;
}

Enemy* Tile::getEnemy(){
    return m_enemy;
}

Chest* Tile::getChest(){
    return m_chest;
}

FriendlyCharacter* Tile::getFriendlyCharacter(){
    return m_friendlyCharacter;
}

void Tile::removeChest(){
    m_chest->removeContent();
    delete m_chest;
    m_chest = nullptr;
}

void Tile::removeEnemy(){
    delete m_enemy;
    m_enemy = nullptr;
}

Tile::~Tile(){
    if (m_enemy != nullptr){
        delete m_enemy;
    }
    if (m_chest != nullptr){
        delete m_chest;
    }
    if (m_friendlyCharacter != nullptr){
        delete m_friendlyCharacter;
    }
}
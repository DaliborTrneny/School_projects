//
// Created by nikol on 24.11.2020.
//

#ifndef MAIN_CPP_TILE_H
#define MAIN_CPP_TILE_H

#include <iostream>
#include "../Characters/Enemy.h"
#include "../Characters/FriendlyCharacter.h"
#include "Chest.h"

enum class tileType{
    accessible, unAccessible, exit
};

class Tile {
protected:
    Enemy* m_enemy;
    Chest* m_chest;
    FriendlyCharacter* m_friendlyCharacter;
    tileType m_type;
public:
    Tile(tileType type);
    virtual void printTile(bool printHero) = 0;
    void printCharacter(std::string tileSymbol, bool printHero);
    tileType getTileType();
    void setEnemy(Enemy* enemy);
    void setChest(Chest* chest);
    void setFriendlyCharacter(FriendlyCharacter* friendlyCharacter);
    Enemy* getEnemy();
    Chest* getChest();
    FriendlyCharacter* getFriendlyCharacter();
    void removeChest();
    void removeEnemy();
    ~Tile();
};


#endif //MAIN_CPP_TILE_H

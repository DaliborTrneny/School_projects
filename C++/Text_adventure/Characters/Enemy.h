//
// Created by dalib on 28.11.2020.
//

#ifndef PROJEKT_ENEMY_H
#define PROJEKT_ENEMY_H

#include <iostream>

class Enemy {
    int m_level;
    int m_physicalDamage;
    int m_health;
public:
    Enemy(int level);
    void setPhysicalDamage();
    int getEnemyLevel();
    int getPhysicalDamage();
    void takeDamage(int howMuch);
    int getHealth();
    int getXpReward();
    int getCoinsReward();
};


#endif //PROJEKT_ENEMY_H

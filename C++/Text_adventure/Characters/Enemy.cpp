//
// Created by dalib on 28.11.2020.
//

#include "Enemy.h"
#include "../Game.h"

Enemy::Enemy(int level){
    m_level = level;
    m_health = 100 + (m_level * 10);
    setPhysicalDamage();
}

void Enemy::setPhysicalDamage(){
    switch (Game::getDifficulty()) {
        case 1: m_physicalDamage = 5 + (3 * m_level);
                break;
        case 2: m_physicalDamage = 9 + (4 * m_level);
                break;
        case 3: m_physicalDamage = 14 + (5 * m_level);
                break;
    }
}

int Enemy::getEnemyLevel(){
    return m_level;
}

int Enemy::getPhysicalDamage(){
    return m_physicalDamage;
}

void Enemy::takeDamage(int howMuch){
    m_health -= howMuch;
}

int Enemy::getHealth(){
    return m_health;
}

int Enemy::getXpReward(){
    return 35 + (10 * m_level);
}

int Enemy::getCoinsReward(){
    return 15 + (2 * m_level);
}
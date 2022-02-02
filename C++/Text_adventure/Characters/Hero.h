//
// Created by nikol on 19.11.2020.
//

#ifndef PROJEKT_HERO_H
#define PROJEKT_HERO_H

#include <iostream>
#include <vector>
#include <iomanip>

#include "Character.h"
#include "FriendlyCharacter.h"
#include "Chest.h"
#include "Enemy.h"

struct LearntInteraction{
    bool buy = false;
    bool sell = false;
    bool stealCoin = false;
    bool flirt = false;
};

class Interaction;

class Hero: public Character {
    std::vector<Interaction*> m_interactions;
    int m_health;
    int m_level;
    int m_xp;
    int m_maxXp;
    int m_stamina;
    int m_physicalDamage;
    Weapon* m_equippedWeapon;
    Armor* m_equippedArmor;
    LearntInteraction learntInteraction;
public:
    Hero();
    void printAllInfo();
    void printBasicInfo(std::string infoType);
    void setHeroName(std::string name);
    void addCoins(int howMany);
    int getHealth();
    int getPhysicalDamage();
    void addStrength(int howMuch);
    float takeDamage(int howMuch);
    void setAllHeroAttributes(int bonusStrength, int bonusAgility, int bonusCharisma);
    void makeInteraction(FriendlyCharacter* friendlyCharacter);
    void learnInteraction(Interaction* interaction);
    void manageInventory();
    bool unlockChest(Chest* chest);
    bool inspectChest(Chest* chest);
    bool fightEnemy(Enemy* enemy);
    void restToGainStamina(Enemy* enemy);
    void drinkPotion(Potion* potion);
    void gainXp(int xp);
    void levelUp();
    void checkInteractions();
    ~Hero();
private:
    void attackEnemy(Enemy* enemy, std::string typeOfAttack);
    void printManageInventory();
    void equipWeapon(int choice);
    void equipArmor(int choice);
};


#endif //PROJEKT_HERO_H

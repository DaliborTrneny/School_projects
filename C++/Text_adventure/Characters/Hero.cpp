//
// Created by nikol on 19.11.2020.
//

#include "Hero.h"
#include "Buy.h"
#include "Sell.h"
#include "StealCoins.h"
#include "Flirt.h"

Hero::Hero(){
    m_name = "";
    m_health = 100;
    m_level = 1;
    m_xp = 0;
    m_maxXp = 100;
    m_stamina = 100;
    m_physicalDamage = 5;
    m_agility = 1;
    m_strength = 1;
    m_charisma = 1;
    m_coins = 0;
    m_equippedWeapon = nullptr;
    m_equippedArmor = nullptr;
}

void Hero::printAllInfo(){
    std::cout << "\n";
    std::string separator = "--------";
    std::cout << separator << m_name << separator << "\n";
    std::cout << "HP: " << m_health << "/100\n";
    std::cout << "Level: " << m_level << "\n";
    std::cout << "XP: " << m_xp << "/" << m_maxXp << "\n";
    std::cout << "Physical damage: " << getPhysicalDamage() << "\n";
    std::cout << "Strength: " << m_strength << "\n";
    std::cout << "Agility: " << m_agility << "\n";
    std::cout << "Charisma: " << m_charisma << "\n";
    std::cout << "Coins: " << m_coins << "\n";
    if (m_equippedWeapon != nullptr){
        std::cout << "Equipped weapon:\n";
        m_equippedWeapon->printInfo();
    } else {
        std::cout << "No equipped weapon\n";
    }
    if (m_equippedArmor != nullptr){
        std::cout << "Equipped armor:\n";
        m_equippedArmor->printInfo();
    } else {
        std::cout << "No equipped armor\n";
    }
    std::cout << std::setw(m_name.length()+(2*separator.length())+1) << std::setfill('-') << "\n";
    std::cout << "Press enter to continue...\n";
    std::cin.ignore();
    std::cin.get();
}

void Hero::printBasicInfo(std::string infoType){
    std::cout << "\n";
    std::string separator = "--------";
    std::cout << separator << m_name << separator << "\n";
    std::cout << "HP: " << m_health << "/100\n";
    std::cout << "Physical damage: " << getPhysicalDamage() << "\n";
    if (infoType == "location") {
        std::cout << "Coins: " << m_coins << "\n";
    } else if (infoType == "combat") {
        std::cout << "Stamina: " << m_stamina << "\n";
    }
    std::cout << std::setw(m_name.length()+(2*separator.length())+1) << std::setfill('-') << "\n";
}

void Hero::setHeroName(std::string name){
    m_name = name;
}

void Hero::addCoins(int howMany){
    m_coins += howMany;
}

int Hero::getHealth(){
    return m_health;
}

int Hero::getPhysicalDamage() {
    if(m_equippedWeapon != nullptr){
        return m_physicalDamage + (m_strength * 5) + m_equippedWeapon->getDamageBonus();
    } else {
        return m_physicalDamage + (m_strength * 5);
    }
}

void Hero::addStrength(int howMuch){
    m_strength += howMuch;
}

float Hero::takeDamage(int howMuch){
    float damage;
    if (m_equippedArmor != nullptr) {
        damage = howMuch - (m_equippedArmor->getArmorBonus() / 3);
        if (damage < 5) {
            damage = 5;
        }
        m_health -= damage;
    } else {
        damage = howMuch;
        m_health -= damage;
    }
    return damage;
}

void Hero::setAllHeroAttributes(int bonusStrength, int bonusAgility, int bonusCharisma){
    m_strength += bonusStrength;
    m_agility += bonusAgility;
    m_charisma += bonusCharisma;
}

void Hero::makeInteraction(FriendlyCharacter* friendlyCharacter){
    std::cout << "Hi my name is " << friendlyCharacter->getName() << "\n";
    int playerInput ;
    do {
        std::cout << "Choose interaction:\n";
        for (int i = 0; i < m_interactions.size(); i++){
            std::cout << "\t[" << i+1 << "] " << m_interactions.at(i)->getName() << "\n";
        }
        std::cout << "\t[" << m_interactions.size()+1 << "] " << "Go back" << "\n";

        std::cin >> playerInput;
        playerInput--;
        if (playerInput < m_interactions.size()) {
            m_interactions.at(playerInput)->makeInteraction(this, friendlyCharacter);
        }
    } while (playerInput != m_interactions.size());
}

void Hero::learnInteraction(Interaction* interaction){
    m_interactions.push_back(interaction);
}

void Hero::printManageInventory(){
    m_inventory->printInventory();
    std::cout << "\t[1] Equip weapon\n";
    std::cout << "\t[2] Equip armor\n";
    std::cout << "\t[3] Drink potion\n";
    std::cout << "\t[4] Discard weapon\n";
    std::cout << "\t[5] Discard armor\n";
    std::cout << "\t[6] Discard potion\n";
    std::cout << "\t[7] Go back\n";
}

void Hero::manageInventory(){
    int choice;
    char playerInput;
    do {
        printManageInventory();
        std::cin >> playerInput;
        if (playerInput >= '1' and playerInput <= '6') {
            std::cout << "Which one?\n";
            std::cin >> choice;
            if (playerInput == '1') {
                equipWeapon(choice);
            } else if (playerInput == '2') {
                equipArmor(choice);
            } else if (playerInput == '3') {
                drinkPotion(m_inventory->getPotion(choice));
            } else if (playerInput == '4') {
                m_inventory->discardItem(choice, itemType::weapon);
            } else if (playerInput == '5') {
                m_inventory->discardItem(choice, itemType::armor);
            } else if (playerInput == '6') {
                m_inventory->discardItem(choice, itemType::potion);
            }
        } else if (playerInput > '7') {
            std::cout << "That is not an option\n";
        }
    } while (playerInput != '7');
}

void Hero::equipWeapon(int choice){
    Weapon* weapon = m_inventory->getWeapon(choice);
    if (weapon != nullptr){
        if (m_equippedWeapon != nullptr){
            m_inventory->addWeapon(m_equippedWeapon);
        }
        m_equippedWeapon = weapon;
    }
}

void Hero::equipArmor(int choice){
    Armor *armor = m_inventory->getArmor(choice);
    if (armor != nullptr) {
        if (m_equippedArmor != nullptr) {
            m_inventory->addArmor(m_equippedArmor);
        }
        m_equippedArmor = armor;
    }
}

bool Hero::unlockChest(Chest* chest){
    if (chest->getLocked()){
        std::cout << "Chest is locked\n";
        if (m_inventory->getKeyToUnlock(chest->getID()) != nullptr){
            std::cout << "You have key to unlock the chest\n";
            chest->unlock();
            return inspectChest(chest);
        } else {
            std::cout << "You dont have key to unlock the chest\n";
            return false;
        }
    } else {
         return inspectChest(chest);
    }
}

bool Hero::inspectChest(Chest* chest){
    Item *item;
    std::cout << "You have found: \n";
    if (chest->getWeapon() != nullptr) {
        item = chest->getWeapon();
    } else if (chest->getArmor() != nullptr) {
        item = chest->getArmor();
    } else if (chest->getPotion() != nullptr) {
        item = chest->getPotion();
    }
    item->printInfo();
    std::cout << "Would you like to take it?\n\t[1] Yes\n\t[2] No\n";
    char playerInput;
    std::cin >> playerInput;
    if (playerInput == '1') {
        std::cout << "You have taken " << item->getName() << " from chest\n";
        if (item->getItemType() == itemType::weapon) {
            m_inventory->addWeapon(chest->getWeapon());
        } else if (item->getItemType() == itemType::armor) {
            m_inventory->addArmor(chest->getArmor());
        } else if (item->getItemType() == itemType::potion) {
            m_inventory->addPotion(chest->getPotion());
        }
        return true;
    } else {
        return false;
    }
}

bool Hero::fightEnemy(Enemy* enemy){
    char choice = 0;
    while (getHealth() > 0 and enemy->getHealth() > 0){
        printBasicInfo("combat");
        std::cout << "Enemy hp: " << enemy->getHealth() << std::endl;
        std::cout << "What do you want to do?" << std::endl;
        std::cout << "\t[1] Light Attack\n";
        std::cout << "\t[2] Heavy Attack\n";
        std::cout << "\t[3] Rest\n";
        std::cout << "\t[I] Manage inventory\n";
        std::cin >> choice;
        if (choice == '1') {
            attackEnemy(enemy, "light");
        } else if(choice == '2') {
            attackEnemy(enemy, "heavy");
        } else if (choice == '3') {
            restToGainStamina(enemy);
        } else if (choice == 'I'){
            manageInventory();
        }
    }
    if (m_health <= 0) {
        return 0;
    } else {
        std::cout << "Enemy is dead. You gain: " << enemy->getXpReward() << " XP\n";
        gainXp(enemy->getXpReward());
        std::cout << "Your reward is: " << enemy->getCoinsReward() << " Coins\n";
        addCoins(enemy->getCoinsReward());
    }
    m_stamina = 100;
    if (m_health < 50){
        m_health = 50;
    }
    return 1;
}

void Hero::attackEnemy(Enemy* enemy, std::string typeOfAttack) {
    int heroDamage;
    float staminaToReduce;
    if (typeOfAttack == "light"){
        heroDamage = getPhysicalDamage();
        staminaToReduce = 30 - (3 * m_agility);
    } else if (typeOfAttack == "heavy"){
        heroDamage = getPhysicalDamage() * 1.5;
        staminaToReduce = 65 - (3 * m_agility);
    }
    if (staminaToReduce < 5){
        staminaToReduce = 5;
    }
    if (m_stamina >= staminaToReduce) {
        enemy->takeDamage(heroDamage);
        m_stamina -= staminaToReduce;
        std::cout << "You dealt: " << heroDamage << " damage\n";
        std::cout << "You suffered: " << takeDamage(enemy->getPhysicalDamage()) << " damage\n";
        std::cout << "You lost: " << staminaToReduce << " stamina\n";
    } else {
        std::cout << "You dont have enough stamina to attack\n";
    }
}

void Hero::restToGainStamina(Enemy* enemy){
    std::cout << "You suffered: " << takeDamage(enemy->getPhysicalDamage()) << " damage\n";
    m_stamina += 50;
    if (m_stamina > 100){
        m_stamina = 100;
    }
}

void Hero::drinkPotion(Potion* potion){
    if (potion != nullptr) {
        std::cout << "You are drinking: " << potion->getName() << "\n";
        if ((m_health + potion->getHealthBonus() <= 100)) {
            m_health += potion->getHealthBonus();
        } else {
            m_health = 100;
        }
    }
}

void Hero::gainXp(int xp){
    m_xp += xp;
    while (m_xp >= m_maxXp){
        m_xp -= m_maxXp;
        levelUp();
    }
}

void Hero::levelUp(){
    m_maxXp += 5 + (3 * m_level);
    m_level++;
    int choice;
    std::cout << "You have leveled up!\nWhat do you want to upgrade?\n";
    std::cout << "\t[1] Strength\n";
    std::cout << "\t[2] Agility\n";
    std::cout << "\t[3] Charisma\n";
    std::cin >> choice;
    switch (choice) {
        case 1:
            m_strength++;
            break;
        case 2:
            m_agility++;
            break;
        case 3:
            m_charisma++;
            break;
        default:
            levelUp();
    }
    checkInteractions();
}

void Hero::checkInteractions(){
    if (m_charisma >= 1 and !learntInteraction.buy){
        learnInteraction(new Buy);
        learntInteraction.buy = true;
        std::cout << "You have learnt new interaction: Buy items\n";
    }
    if (m_agility >= 5 and !learntInteraction.stealCoin){
        learnInteraction(new StealCoins);
        learntInteraction.stealCoin = true;
        std::cout << "You have learnt new interaction: Steal Coins\n";
    }
    if (m_charisma >= 3 and !learntInteraction.sell){
        learnInteraction(new Sell);
        learntInteraction.sell = true;
        std::cout << "You have learnt new interaction: Sell items\n";
    }
    if (m_charisma >= 4 and !learntInteraction.flirt){
        learnInteraction(new Flirt);
        learntInteraction.flirt = true;
        std::cout << "You have learnt new interaction: Flirt\n";
    }
}

Hero::~Hero(){
    if (m_equippedArmor != nullptr){
        delete m_equippedArmor;
    }
    if (m_equippedWeapon != nullptr){
        delete m_equippedWeapon;
    }
    for (auto &interaction: m_interactions){
        if (interaction != nullptr){
            delete interaction;
        }
    }
}

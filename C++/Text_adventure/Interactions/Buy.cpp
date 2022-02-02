//
// Created by dalib on 11.12.2020.
//

#include "Buy.h"

Buy::Buy(): Interaction("Buy"){
}

void Buy::makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "You have: " << hero->getCoins() << " C\n";
    std::cout << "+-----------SHOP-----------+\n";
    friendlyCharacter->printInventory();
    std::cout << "What do you want to buy?" << std::endl;
    std::cout << "\t[1] Weapon" << std::endl;
    std::cout << "\t[2] Armor" << std::endl;
    std::cout << "\t[3] Potion" << std::endl;
    std::cout << "\t[4] Key" << std::endl;
    std::cout << "\t[5] Go back" << std::endl;
    std::cin >> choice;
    if (choice == 1) {
        buyWeapon(hero, friendlyCharacter);
    } else if (choice == 2) {
        buyArmor(hero, friendlyCharacter);
    } else if (choice == 3) {
        buyPotion(hero, friendlyCharacter);
    } else if (choice == 4) {
        buyKey(hero, friendlyCharacter);
    } else if (choice == 5){
        return;
    } else {
        makeInteraction(hero, friendlyCharacter);
    }
}

void Buy::buyWeapon(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "Which one?" <<std::endl;
    std::cin >> choice;

    Weapon* weapon = friendlyCharacter->getInventory()->getWeapon(choice);
    if (weapon != nullptr) {
        if (weapon->getPrice() <= hero->getCoins()) {
            std::cout << "You bought: " << weapon->getName() << "\n";
            hero->getInventory()->addWeapon(weapon);
            hero->addCoins(-weapon->getPrice());
        } else {
            std::cout << "You dont have enough coins\n";
            friendlyCharacter->getInventory()->addWeapon(weapon);
        }
    }
}

void Buy::buyArmor(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "Which one?" <<std::endl;
    std::cin >> choice;

    Armor* armor = friendlyCharacter->getInventory()->getArmor(choice);
    if (armor != nullptr){
        if (armor->getPrice() <= hero->getCoins()) {
            std::cout << "You bought: " << armor->getName() << "\n";
            hero->getInventory()->addArmor(armor);
            hero->addCoins(-armor->getPrice());
        } else {
            std::cout << "You dont have enough coins\n";
            friendlyCharacter->getInventory()->addArmor(armor);
        }
    }
}

void Buy::buyKey(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "Which one?" <<std::endl;
    std::cin >> choice;

    Key* key = friendlyCharacter->getInventory()->getKey(choice);
    if (key != nullptr){
        if (key->getPrice() <= hero->getCoins()) {
            std::cout << "You bought: " << key->getName() << "\n";
            hero->getInventory()->addKey(key);
            hero->addCoins(-key->getPrice());
        } else {
            std::cout << "You dont have enough coins\n";
            friendlyCharacter->getInventory()->addKey(key);
        }
    }
}

void Buy::buyPotion(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "Which one?" <<std::endl;
    std::cin >> choice;

    Potion* potion = friendlyCharacter->getInventory()->getPotion(choice);
    if (potion != nullptr){
        if (potion->getPrice() <= hero->getCoins()) {
            std::cout << "You bought: " << potion->getName() << "\n";
            hero->getInventory()->addPotion(potion);
            hero->addCoins(-potion->getPrice());
        } else {
            std::cout << "You dont have enough coins\n";
            friendlyCharacter->getInventory()->addPotion(potion);
        }
    }
}
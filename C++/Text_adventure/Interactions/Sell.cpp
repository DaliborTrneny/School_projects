//
// Created by nikol on 22.12.2020.
//

#include "Sell.h"

Sell::Sell(): Interaction("Sell"){
}

void Sell::makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter){
    int choice;
    std::cout << "You have: " << hero->getCoins() << " C\n";
    std::cout << "+--------INVENTORY---------+\n";
    hero->printInventory();
    std::cout << "What do you want to sell?" << std::endl;
    std::cout << "\t[1] Weapon" << std::endl;
    std::cout << "\t[2] Armor" << std::endl;
    std::cout << "\t[3] Go back" << std::endl;
    std::cin >> choice;
    if (choice == 1) {
        sellWeapon(hero, friendlyCharacter);
    } else if (choice == 2) {
        sellArmor(hero, friendlyCharacter);
    } else if (choice == 3){
        return;
    } else {
        makeInteraction(hero, friendlyCharacter);
    }
}

void Sell::sellWeapon(Hero *hero, FriendlyCharacter *friendlyCharacter){
    int choice;
    std::cout << "Which one?\n";
    std::cin >> choice;

    Weapon* weapon = hero->getInventory()->getWeapon(choice);
    if (weapon != nullptr) {
        std::cout << "You sold: " << weapon->getName() << "\n";
        friendlyCharacter->getInventory()->addWeapon(weapon);
        hero->addCoins(weapon->getPrice());
    }
}

void Sell::sellArmor(Hero *hero, FriendlyCharacter *friendlyCharacter){
    int choice;
    std::cout << "Which one?\n";
    std::cin >> choice;

    Armor* armor = hero->getInventory()->getArmor(choice);
    if (armor != nullptr){
        std::cout << "You sold: " << armor->getName() << "\n";
        friendlyCharacter->getInventory()->addArmor(armor);
        hero->addCoins(armor->getPrice());
    }
}
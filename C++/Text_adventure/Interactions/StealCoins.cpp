//
// Created by nikol on 07.12.2020.
//

#include "StealCoins.h"

StealCoins::StealCoins(): Interaction("Steal coins"){
}

void StealCoins::makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter){
    if (hero->getAgility() > friendlyCharacter->getAgility()){
        if (friendlyCharacter->getCoins() > 0){
            hero->addCoins(friendlyCharacter->getCoins());
            std::cout << "You have stolen " << friendlyCharacter->getCoins() << " coins from " << friendlyCharacter->getName() << "\n";
            friendlyCharacter->removeCoins();
        } else {
            std::cout << friendlyCharacter->getName() << " does not have any coins :(\n";
        }
    } else {
        std::cout << "Hey you cant steal from me! Step Back!\n";
    }
}
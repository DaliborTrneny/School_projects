//
// Created by nikol on 19.01.2021.
//

#include "Flirt.h"

Flirt::Flirt(): Interaction("Flirt"){
}

void Flirt::makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter){
    if (hero->getCharisma() > friendlyCharacter->getCharisma()){
        std::cout << "You banged " << friendlyCharacter->getName() << " NICE\n";
        hero->addStrength(friendlyCharacter->getCharisma() / 4);
        std::cout << "You have gained: " << friendlyCharacter->getCharisma() / 4 << "of strength\n";
    } else {
        std::cout << "I really like you, but I would rather be friend with you\n";
    }
}
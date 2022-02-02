//
// Created by dalib on 11.12.2020.
//

#ifndef MAIN_CPP_BUY_H
#define MAIN_CPP_BUY_H

#include "Interaction.h"

class Buy: public Interaction {
public:
    Buy();
    void makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void buyWeapon(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void buyArmor(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void buyKey(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void buyPotion(Hero* hero, FriendlyCharacter* friendlyCharacter);
};


#endif //MAIN_CPP_BUY_H

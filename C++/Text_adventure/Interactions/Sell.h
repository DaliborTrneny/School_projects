//
// Created by nikol on 22.12.2020.
//

#ifndef MAIN_CPP_SELL_H
#define MAIN_CPP_SELL_H

#include "Interaction.h"

class Sell: public Interaction {
public:
    Sell();
    void makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void sellWeapon(Hero* hero, FriendlyCharacter* friendlyCharacter);
    void sellArmor(Hero* hero, FriendlyCharacter* friendlyCharacter);
};


#endif //MAIN_CPP_SELL_H

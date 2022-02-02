//
// Created by nikol on 07.12.2020.
//

#ifndef MAIN_CPP_STEALCOINS_H
#define MAIN_CPP_STEALCOINS_H

#include "Interaction.h"

class StealCoins: public Interaction {
public:
    StealCoins();
    void makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter);
};


#endif //MAIN_CPP_STEALCOINS_H

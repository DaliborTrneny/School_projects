//
// Created by nikol on 19.01.2021.
//

#ifndef MAIN_CPP_FLIRT_H
#define MAIN_CPP_FLIRT_H

#include "Interaction.h"

class Flirt: public Interaction {
public:
    Flirt();
    void makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter);
};


#endif //MAIN_CPP_FLIRT_H

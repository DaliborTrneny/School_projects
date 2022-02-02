//
// Created by nikol on 07.12.2020.
//

#ifndef MAIN_CPP_INTERACTION_H
#define MAIN_CPP_INTERACTION_H

#include <iostream>
#include "../Characters/Hero.h"
#include "../Characters/FriendlyCharacter.h"

class Interaction {
protected:
    std::string m_name;
public:
    Interaction(std::string name);
    virtual void makeInteraction(Hero* hero, FriendlyCharacter* friendlyCharacter) = 0;
    std::string getName();
};


#endif //MAIN_CPP_INTERACTION_H

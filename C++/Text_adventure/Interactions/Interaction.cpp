//
// Created by nikol on 07.12.2020.
//

#include "Interaction.h"

Interaction::Interaction(std::string name){
    m_name = name;
}

std::string Interaction::getName(){
    return m_name;
}
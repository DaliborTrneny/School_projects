//
// Created by nikol on 02.12.2020.
//

#ifndef MAIN_CPP_LOCATIONDIRECTOR_H
#define MAIN_CPP_LOCATIONDIRECTOR_H

#include "ForestLocationBuilder.h"
#include "TownLocationBuilder.h"
#include "HutLocationBuilder.h"
#include "BogLocationBuilder.h"
#include "WoodsLocationBuilder.h"
#include "CavernLocationBuilder.h"
#include "NecroLocationBuilder.h"
#include "TombLocationBuilder.h"

class LocationDirector {
    LocationBuilder* m_builder;
public:
    LocationDirector(LocationBuilder* builder);
    void setLocationBuilder(LocationBuilder* builder);
    Location* createLocation();
};


#endif //MAIN_CPP_LOCATIONDIRECTOR_H

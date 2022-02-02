//
// Created by nikol on 15.12.2020.
//

#include "WaterTile.h"

WaterTile::WaterTile(): Tile(tileType::unAccessible){
}

void WaterTile::printTile(bool printHero){
    std::cout << "~~";
    Tile::printCharacter("~~", printHero);
    std::cout << "~~";
}
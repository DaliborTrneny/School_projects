//
// Created by dalib on 19.01.2021.
//

#include "FireTile.h"

FireTile::FireTile(): Tile(tileType::unAccessible){
}

void FireTile::printTile(bool printHero){
    std::cout << "_{";
    Tile::printCharacter("##", printHero);
    std::cout << "}_";
}
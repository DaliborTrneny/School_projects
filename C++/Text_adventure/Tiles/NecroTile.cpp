//
// Created by dalib on 06.12.2020.
//

#include "NecroTile.h"

NecroTile::NecroTile(): Tile(tileType::accessible){
}

void NecroTile::printTile(bool printHero){
    std::cout << "._";
    Tile::printCharacter("__", printHero);
    std::cout << "_.";
}
//
// Created by dalib on 06.12.2020.
//

#include "TombTile.h"

TombTile::TombTile(): Tile(tileType::accessible){
}

void TombTile::printTile(bool printHero){
    std::cout << "i_";
    Tile::printCharacter("__", printHero);
    std::cout << "_i";
}
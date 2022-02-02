//
// Created by dalib on 06.12.2020.
//

#include "CavernTile.h"

CavernTile::CavernTile(): Tile(tileType::accessible){
}

void CavernTile::printTile(bool printHero){
    std::cout << "^^";
    Tile::printCharacter("__", printHero);
    std::cout << "^^";
}
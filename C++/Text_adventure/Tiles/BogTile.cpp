//
// Created by dalib on 06.12.2020.
//

#include "BogTile.h"

BogTile::BogTile(): Tile(tileType::accessible){
}

void BogTile::printTile(bool printHero){
    std::cout << "_;";
    Tile::printCharacter("._", printHero);
    std::cout << ".~";
}
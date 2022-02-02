//
// Created by nikol on 24.11.2020.
//

#include "TownTile.h"

TownTile::TownTile(): Tile(tileType::accessible){
}

void TownTile::printTile(bool printHero){
    std::cout << "__";
    Tile::printCharacter("__", printHero);
    std::cout << "__";
}
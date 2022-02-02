//
// Created by nikol on 24.11.2020.
//

#include "ForestTile.h"

ForestTile::ForestTile(): Tile(tileType::accessible){
}

void ForestTile::printTile(bool printHero){
    std::cout << "/\\";
    Tile::printCharacter("/\\", printHero);
    std::cout << "/\\";
}
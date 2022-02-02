//
// Created by nikol on 20.12.2020.
//

#include "ExitTile.h"

ExitTile::ExitTile(): Tile(tileType::exit){
}

void ExitTile::printTile(bool printHero){
    std::cout << "|E";
    Tile::printCharacter("XI", printHero);
    std::cout << "T|";
}
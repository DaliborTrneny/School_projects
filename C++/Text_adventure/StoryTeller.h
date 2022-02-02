//
// Created by dalib on 03.12.2020.
//

#ifndef MAIN_CPP_STORYTELLER_H
#define MAIN_CPP_STORYTELLER_H

#include <iostream>

struct heroAttributes {
    int strength;
    int agility;
    int charisma;
};

class StoryTeller {
public:
    StoryTeller();
    char getPlayerDialogInput(char min, char max, std::string errorText);
    heroAttributes chooseHeroAttributes();
    void printChapter(int chapterNumber);
    void printChapterOne();
    void printChapterTwo();
    void printChapterThree();
    void printChapterFour();
    void printChapterFive();
    void printChapterSix();
    void printChapterSeven();
    void printChapterEight();

};


#endif //MAIN_CPP_STORYTELLER_H

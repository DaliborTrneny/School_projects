//
// Created by dalib on 03.12.2020.
//

#include "StoryTeller.h"

StoryTeller::StoryTeller(){
}

char StoryTeller::getPlayerDialogInput(char min, char max, std::string errorText){
    char playerInput;
    std::cin >> playerInput;
    while (playerInput < min || playerInput > max) {
        std::cout << errorText << std::endl;
        std::cin >> playerInput;
    }
    return playerInput;
}

heroAttributes StoryTeller::chooseHeroAttributes(){
    heroAttributes attributes;
    attributes.strength = 0; attributes.agility = 0; attributes.charisma = 0;
    char dialogOption;
    std::cout << "I got a few questions for you.\n";
    std::cout << "Where are you from?\n\n";
    std::cout << "[1] I am from North, i do not know why but I have everything blurred...\n";
    std::cout << "[2] I am from West, i do not know why but I have everything blurred...\n";
    std::cout << "[3] I am from East, i do not know why but I have everything blurred...\n";

    dialogOption = getPlayerDialogInput('1', '3', "That is not answering my question");

    if(dialogOption == '1') {
        std::cout << "Oh, so you are norseman. You seemed to me kinda muscular...\n";
        attributes.strength++;
    } else if (dialogOption == '2') {
        std::cout << "Oh, so you are from West. Western people are very agile and good in sneaking...\n";
        attributes.agility++;
    } else if (dialogOption == '3'){
        std::cout << "Oh, so you are from East. Eastern people are talkative.\n";
        attributes.charisma++;
    }

    std::cout << "\n";
    std::cout << "I see that you are trying to remember. What is your profession?\n\n";
    std::cout << "[1] I am travelling merchant. I used to have a lot of friends in many towns\n";
    std::cout << "[2] I think that I was a royal guard\n";
    std::cout << "[3] I do not like to admit, but i was thief. People hired and I accepted...\n";

    dialogOption = getPlayerDialogInput('1', '3', "That is not answering my question");

    if(dialogOption == '1') {
        std::cout << "That is what I thought. That would explain your personality and temperament.\n";
        attributes.charisma += 2;
    } else if (dialogOption == '2') {
        std::cout << "That would explain that crest on your armor.\n";
        attributes.strength += 2;
    } else if (dialogOption == '3') {
        std::cout << "Cape and leather armor, that explain a lot, but do not worry I do not judge you.\n";
        std::cout << "Living in this world is harder than before...\n";
        attributes.agility += 2;
    }

    std::cout << "\n";
    std::cout << "You must be hungry.\n";
    std::cout << "The old man offered you vegetable stew.\n";
    std::cout << "Anyway, my last question is, how would you dealt with combat situation?\n\n";
    std::cout << "[1] I would not go for face-to-face fight. I would strike from the shadows.\n";
    std::cout << "[2] I hate fights, I always considered myself as peaceful being, even in this cruel world.\n";
    std::cout << "[3] I love the adrenaline in my blood, I would use my full strength to crush my enemy..\n";

    dialogOption = getPlayerDialogInput('1', '3', "That is not answering my question");

    if(dialogOption == '1') {
        std::cout << "I agree, when you catch your enemy off-guard. It makes your fight a lot easier." << std::endl;
        attributes.agility++;
    } else if (dialogOption == '2') {
        std::cout << "Nowdays is hard to not fight. Sometimes you just have to. But your answer is not wrong." << std::endl;
        attributes.charisma++;
    } else if (dialogOption == '3') {
        std::cout << "I see, that you are really sure about yourself. And that is not in fact wrong, but its double sided edge" << std::endl;
        attributes.strength++;
    }
    return attributes;
}

void StoryTeller::printChapter(int chapterNumber){
    switch (chapterNumber){
        case 1: printChapterOne();
                break;
        case 2: printChapterTwo();
                break;
        case 3: printChapterThree();
                break;
        case 4: printChapterFour();
                break;
        case 5: printChapterFive();
                break;
        case 6: printChapterSix();
                break;
        case 7: printChapterSeven();
                break;
        case 8: printChapterEight();
                break;
    }
}

void StoryTeller::printChapterOne(){
    std::cout << "\n";
    std::cout << "Chapter One: The Old Man's Hut\n";
    std::cout << "You are coming back to your senses. You find yourself in a some kind of hut. There is firepit in the middle of room.\n";
    std::cout << "You look to the right and there is person. He looks very old sitting next to the fire. He looked at you and said:\n";

    std::cout << "\n";
    std::cout << "You? You are finally awake? I am glad that you made it through. For a moment i thought that I lost you...\n";
    std::cout << "I do not want to brag, but I have saved your life. I found you in the river. You were cold as ice.\n";
    std::cout << "Now, when you are conscious.\n";
}

void StoryTeller::printChapterTwo() {
    std::cout << "\n";
    std::cout << "Chapter Two: Whispering Woods" << std::endl;
    std::cout << "It's not even half of the day and you are standing in front of Whispering Woods." << std::endl;
    std::cout << "Old Man has told you a lot about it, he mentioned massacre." << std::endl;
    std::cout << "Army pillaging village. They didn't take any prisoners, they were here just for slaughter." << std::endl;
    std::cout << "On the remains of the village a forest grew up, which is strange in some way." << std::endl;
    std::cout << "I heard many rumors about people, who travelled through and they heard wailing, moaning and crying, which doesn't belong to human." << std::endl;
    std::cout << "And now you are standing right in front..." << std::endl;
}

void StoryTeller::printChapterThree(){
    std::cout << "\n";
    std::cout << "Chapter Three: Crimson Village" << std::endl;
    std::cout << "You are entering Crimson Village, if we can call it village." << std::endl;
    std::cout << "What you see can be with difficulty compared with village or settlement" << std::endl;
    std::cout << "All you see is a couple of ramshackle buildings, which have roofs covered with moss and grass." << std::endl;
    std::cout << "There is nothing that would please human eye. There is only misery, mud and rotting things..." << std::endl;

}

void StoryTeller::printChapterFour(){
    std::cout << "\n";
    std::cout << "Chapter Four: Twisted Bog" << std::endl;
    std::cout << "Bog..Swamp, the last place where you wanted to be. " << std::endl;
    std::cout << "The worst aren't those little frogs, neither snails or other vermin. The worst is fog. Human eye cannot see through." << std::endl;
    std::cout << "But I warn you, in the bog are many creatures, who can see, and they are not happy about newcomer..." << std::endl;
    std::cout << "Watch your back..." << std::endl;
}

void StoryTeller::printChapterFive(){
    std::cout << "\n";
    std::cout << "Chapter Five: Hollow Woods" << std::endl;
    std::cout << "For a moment you were thinking to yourself and forgot to watch out for journey" << std::endl;
    std::cout << "and now you are in the middle of Hollow Woods." << std::endl;
    std::cout << "At the first sight it looks as magical place in this rotten world of misery." << std::endl;
    std::cout << "Everything shine in every color and many of tress were replaced by big mushrooms." << std::endl;
    std::cout << "But do not get yourself fooled by beauty of this forest." << std::endl;
    std::cout << "Mushrooms, which are decorating this forest, are also disease of this forest." << std::endl;
}

void StoryTeller::printChapterSix(){
    std::cout << "\n";
    std::cout << "Chapter Six: Bandit's Ambush" << std::endl;
    std::cout << "What were you thinking? Locals did not warn you? And that location name... Good luck with fight, you have been ambushed.";
    std::cout << "Good luck with that." << std::endl;
}

void StoryTeller::printChapterSeven(){
    std::cout << "\n";
    std::cout << "Chapter Seven: Ancient Tomb" << std::endl;
    std::cout << "You have never seen something like this. Long halls with pillars, which were decorated with torches," << std::endl;
    std::cout << "lighten up with cyan fire." << std::endl;
    std::cout << "Almost as if someone live here, but why would be this place named tomb? " << std::endl;
}

void StoryTeller::printChapterEight(){
    std::cout << "\n";
    std::cout << "Chapter Eight: Dead Man's End" << std::endl;
    std::cout << "You are entering unknown part of Ancient Tomb. Same feel, but still slightly different." << std::endl;
    std::cout << "Crimson carpet, throne and library. Everything covered by spider cobwebs." << std::endl;
    std::cout << "You see that somebody is sitting on the throne, he looks dead." << std::endl;
    std::cout << "You made few steps forward, you are nearly right in front of him. That cruel laugh." << std::endl;
    std::cout << "It resonate across the whole tomb. That dead body, is now standing right in front of you." << std::endl;
    std::cout << "No flesh, just bones, crown and his eyes blue as ice." << std::endl;
    std::cout << "It's Lich." << std::endl;
    std::cout << "Human, which spend his whole life searching for immortality and he found it, but at what cost." << std::endl;
    std::cout << "He is no longer human being." << std::endl;
    std::cout << "This is end..." << std::endl;
}


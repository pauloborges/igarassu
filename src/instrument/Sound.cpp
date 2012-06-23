#include "Sound.h"

Sound::Sound(std::string fileName)
{
    player.loadSound(fileName);
}

Sound::~Sound() {
    player.unloadSound();
}

void Sound::play() {
    player.play();
}

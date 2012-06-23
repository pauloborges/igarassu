#pragma once
#include "ofSoundPlayer.h"

class Sound
{
public:
    Sound(std::string fileName);
    virtual ~Sound();
    
    void play();
    //void stop();
    //void close();

private:
    ofSoundPlayer player;
};

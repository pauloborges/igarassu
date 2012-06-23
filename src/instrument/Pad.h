#pragma once
#include <string>
#include "Sound.h"

class Pad
{
private:
    int id;
    std::string name;
    
    Sound *sound;
    
public:
    Pad(Sound *sound = 0);
    virtual ~Pad();
    
    void attachSound(Sound *sound);
    
    void trigger(void);
    //void draw(Painter painter);
    
    static const int DEFAULT_INTENSITY_THRESHOLD = 200;
    static const int DEFAULT_SILENCE_WINDOW = 5;
};

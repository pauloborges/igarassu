#include "Pad.h"

Pad::Pad(Sound *sound)
    : sound(sound)
{
}

Pad::~Pad()
{
    if (this->sound)
        delete this->sound;
}

void Pad::attachSound(Sound *sound)
{
    if (this->sound)
        delete this->sound;
    
    this->sound = sound;
}

void Pad::trigger(void)
{
    this->sound->play();
}

#pragma once

#include "Hardware.h"
#include "../instrument/Instrument.h"

class Keyboard : public Hardware
{
private:
    std::map<int, int> mapping;
    
public:
    Keyboard();
    virtual ~Keyboard();
    
    virtual void update();
    
    void addMapping(char key, int pad);
};

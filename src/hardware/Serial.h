#pragma once

#include <climits>
#include <cstring>
#include "ofSerial.h"
#include "Hardware.h"
#include "../instrument/Instrument.h"

class Serial : public Hardware
{
private:
    ofSerial serial;
    
    std::string buffer;
    int bufferLimit;
    
public:
    Serial(int deviceNumber = 0, int baud = 9600);
    virtual ~Serial();
    
    virtual void update();
    
    void bufferUntil(int c);
};

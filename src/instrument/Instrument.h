#pragma once

#include <string>
#include <vector>
#include <algorithm>
#include "ofMain.h"
#include "Pad.h"
#include "../hardware/Keyboard.h"
#include "../hardware/Serial.h"

class Instrument
{
private:
    std::string name;
    
    std::vector<Pad*> pads;
    std::vector<Hardware*> hardwares;

public:
    Instrument(std::string name);
    virtual ~Instrument();
    
    static Instrument *loadInstrument();
    
    void attachPad(Pad *pad);
    
    void installHardware(Hardware *hardware);
    void uninstallHardware(Hardware *hardware);
    void updateHardwares(int hardwareType);
    
    void routeTrigger(int padId);
};

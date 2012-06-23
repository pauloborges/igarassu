#pragma once

#include "ofMain.h"
#include "instrument/Instrument.h"

class IgarassuApp : public ofBaseApp
{
public:
    IgarassuApp();
    virtual ~IgarassuApp();
    
    void setup();
    void update();
    
    void keyPressed(int key);
    void keyReleased(int key);
    void mouseMoved(int x, int y);
    void mouseDragged(int x, int y, int button);
    void mousePressed(int x, int y, int button);
    void mouseReleased();
    
    void handleSerial();
    
private:
    Instrument *instrument;
};

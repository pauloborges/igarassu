#pragma once

#include <map>
//#include "../instrument/Instrument.h"

class Instrument;

class Hardware
{
public:
    // Hardware types
    static const int HW_TYPE_KEYBOARD = 0x1;
    static const int HW_TYPE_MOUSE = 0x2;
    static const int HW_TYPE_SERIAL = 0x3;

    // Keyboard states
    static const int KEYBOARD_IDLE = 0x1;
    static const int KEYBOARD_PRESSED = 0x2;
    static const int KEYBOARD_RELEASED = 0x3;
    
    // Mouse states
    static const int MOUSE_IDLE = 0x1;
    static const int MOUSE_PRESSED = 0x2;
    static const int MOUSE_RELEASED = 0x4;
    static const int MOUSE_DRAGGED = 0x8;
    
    static const int MOUSE_LEFT_BUTTON = 0x0;
    static const int MOUSE_MIDDLE_BUTTON = 0x1;
    static const int MOUSE_RIGHT_BUTTON = 0x2;
    static const int MOUSE_NO_BUTTON = 0x4;
    
    static void updateKeyboardKey(int key);
    static void updateKeyboardState(int keyboardState);

    virtual void update() = 0;
    
    bool isType(int type);
    void setInstrument(Instrument *instrument);

protected:
    Hardware(int type);

    int type;
    Instrument *instrument;
    
    // Current keyboard state
    static int _key;// = 0;
    static int _keyboardState;// = KEYBOARD_IDLE;
    
    // Current mouse state
    static int _mouseX;// = 0;
    static int _mouseY;// = 0;
    static int _mouseButton;// = MOUSE_NO_BUTTON;
    static int _mouseState;// = MOUSE_IDLE;
};

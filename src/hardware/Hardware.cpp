#include "Hardware.h"
#include "../instrument/Instrument.h"

int Hardware::_key = 0;
int Hardware::_keyboardState = KEYBOARD_IDLE;
int Hardware::_mouseX = 0;
int Hardware::_mouseY = 0;
int Hardware::_mouseButton = MOUSE_NO_BUTTON;
int Hardware::_mouseState = MOUSE_IDLE;

Hardware::Hardware(int type)
    : type(type), instrument(0)
{}

bool Hardware::isType(int type)
{
    return this->type == type;
}

void Hardware::setInstrument(Instrument *instrument)
{
    if (this->instrument)
        this->instrument->uninstallHardware(this);
    
    this->instrument = instrument;
}

void Hardware::updateKeyboardKey(int key)
{
    Hardware::_key = key;
}

void Hardware::updateKeyboardState(int keyboardState)
{
    Hardware::_keyboardState = keyboardState;
}

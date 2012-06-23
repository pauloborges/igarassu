#include "Keyboard.h"

Keyboard::Keyboard()
    : Hardware(Hardware::HW_TYPE_KEYBOARD)
{
}

Keyboard::~Keyboard()
{
}

void Keyboard::update()
{
    if (Hardware::_keyboardState == KEYBOARD_PRESSED)
    {
        int key = Hardware::_key;
        if (this->mapping.find(key) != this->mapping.end())
        {
            int pad = this->mapping[key];
            this->instrument->routeTrigger(pad);
        }
    }
}

void Keyboard::addMapping(char key, int pad)
{
    this->mapping[key] = pad;
}

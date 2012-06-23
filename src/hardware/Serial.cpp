#include "Serial.h"

Serial::Serial(int deviceNumber, int baud)
    : Hardware(Hardware::HW_TYPE_SERIAL), bufferLimit('\n')
{
    this->serial.setup(deviceNumber, baud);
    this->serial.setVerbose(true);
}

Serial::~Serial()
{
    this->serial.close();
}

void Serial::update()
{
    int tmp;
    bool completed = false;
    
    // Read all available data
    while ((tmp = this->serial.readByte()) != OF_SERIAL_NO_DATA && !completed)
    {
        // If the last byte is equal to the buffer limit, ignore it
        if (tmp == this->bufferLimit)
            completed = true;
        
        // Otherwise, append it to the buffer
        else
            this->buffer.push_back(tmp);
    }
    
    if (completed)
    {
        int pad;
        stringstream ss(this->buffer);
        
        if (!(ss >> pad).fail())
            this->instrument->routeTrigger(pad);
        
        this->buffer.erase();
    }
}

void Serial::bufferUntil(int c)
{
    this->bufferLimit = c;
}

#include "Instrument.h"

Instrument::Instrument(std::string name)
    : name(name)
{
}

Instrument::~Instrument()
{
    // Delete all pads
    std::vector<Pad*>::iterator pit;
    for (pit = this->pads.begin(); pit != this->pads.end(); ++pit)
        delete *pit;
    
    // Delete all hardwares
    std::vector<Hardware*>::iterator hit;
    for (hit = this->hardwares.begin(); hit != this->hardwares.end(); ++hit)
        delete *hit;
}

Instrument *Instrument::loadInstrument()
{
    Instrument *instrument = new Instrument("Drum");
    instrument->attachPad(new Pad(new Sound("hihat.wav")));
    instrument->attachPad(new Pad(new Sound("snare.wav")));
    instrument->attachPad(new Pad(new Sound("tom.wav")));
    
    Keyboard *keyboard = new Keyboard();
    keyboard->addMapping('a', 2);
    keyboard->addMapping('s', 1);
    keyboard->addMapping('d', 0);
    instrument->installHardware(keyboard);
    
    Serial *serial = new Serial(0, 9600);
    instrument->installHardware(serial);
    
    return instrument;
}

void Instrument::attachPad(Pad *pad)
{
    if (!pad)
        return;
    
    std::vector<Pad*>::iterator it = 
        std::find(this->pads.begin(), this->pads.end(), pad);
    
    if (it != this->pads.end())
        return;
    
    this->pads.push_back(pad);
}

void Instrument::installHardware(Hardware *hardware)
{
    if (!hardware)
        return;
    
    std::vector<Hardware*>::iterator it = 
        std::find(this->hardwares.begin(), this->hardwares.end(), hardware);
    
    if (it != this->hardwares.end())
        return;
    
    // Insert hardware
    this->hardwares.push_back(hardware);
    hardware->setInstrument(this);
}

void Instrument::uninstallHardware(Hardware *hardware)
{
    std::remove(this->hardwares.begin(), this->hardwares.end(), hardware);
}

void Instrument::updateHardwares(int hardwareType)
{
    std::vector<Hardware*>::iterator it;
    for (it = this->hardwares.begin(); it != this->hardwares.end(); ++it)
    {
        Hardware *tmp = *it;
        if (tmp->isType(hardwareType))
            tmp->update();
    }
}

void Instrument::routeTrigger(int padId)
{
    this->pads[padId]->trigger();
}

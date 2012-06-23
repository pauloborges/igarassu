#include "IgarassuApp.h"

IgarassuApp::IgarassuApp()
{
}

IgarassuApp::~IgarassuApp()
{
    delete instrument;
}

void IgarassuApp::setup()
{
    this->instrument = Instrument::loadInstrument();
}

void IgarassuApp::update()
{
    this->instrument->updateHardwares(Hardware::HW_TYPE_SERIAL);
    
    // ...
}

void IgarassuApp::keyPressed(int key)
{
    Hardware::updateKeyboardKey(key);
    Hardware::updateKeyboardState(Hardware::KEYBOARD_PRESSED);
    
    this->instrument->updateHardwares(Hardware::HW_TYPE_KEYBOARD);
}

void IgarassuApp::keyReleased(int key)
{
    //Hardware::updateKeyboardKey(key);
    //Hardware::updateKeyboardState(Hardware::KEYBOARD_PRESSED);
    
    // FIXME essa lógica está errada, já que mais de uma tecla pode ser 
    // pressionada
}

void IgarassuApp::mouseMoved(int x, int y)
{
    // TODO
}

void IgarassuApp::mouseDragged(int x, int y, int button)
{
    // TODO
}

void IgarassuApp::mousePressed(int x, int y, int button)
{
    // TODO
}

void IgarassuApp::mouseReleased()
{
    // TODO
}

void IgarassuApp::handleSerial()
{
    /*char tmp = OF_SERIAL_NO_DATA;
    
    while( (tmp = serial.readByte()) != OF_SERIAL_NO_DATA )
    {
        resultado += tmp;
        if (tmp == '\n') {
            //tmp = OF_SERIAL_NO_DATA;
            break;
        }
    }
    
    cout << resultado << endl;
    if (tmp == '\n')
        resultado = "";*/
}

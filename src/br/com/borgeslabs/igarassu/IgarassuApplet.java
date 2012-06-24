package br.com.borgeslabs.igarassu;

import br.com.borgeslabs.igarassu.hardware.*;
import br.com.borgeslabs.igarassu.instrument.*;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.serial.Serial;

public class IgarassuApplet extends PApplet {
    private static final long serialVersionUID = 1L;

    Minim minim;
    Serial serial;
    Instrument instrument;

    public static void main(String args[]) {
        PApplet.main(new String[] { /*"--present",*/
                "br.com.borgeslabs.igarassu.IgarassuApplet" });
    }

    public void setup() {
        this.minim = new Minim(this);

        // Window setup
        this.size(800, 600);
        this.frameRate(60);
        
        // Serial setup
        if (Serial.list().length > 0) {
            this.serial = new Serial(this, Serial.list()[0], SerialConn.BAUD);
            this.serial.bufferUntil(SerialConn.ENCLOSING_MSG_CHAR);
        } else {
            println("WARNING!");
            println("Serial not found.");
            println();
        }
        
        // Placeholder instrument
        this.instrument = Instrument.loadInstrument(minim);
    }

    public void stop() {
        this.minim.stop();
        this.serial.stop();
        
        this.instrument.close();
    }

    public void draw() {
        background(0);
        stroke(255);
        
        /*
         * for (int i = 0; i < sample.bufferSize() - 1; i++) { float x1 = map(i,
         * 0, sample.bufferSize(), 0, width); float x2 = map(i+1, 0,
         * sample.bufferSize(), 0, width); line(x1, 50 - sample.left.get(i)*50,
         * x2, 50 - sample.left.get(i+1)*50); line(x1, 150 -
         * sample.right.get(i)*50, x2, 150 - sample.right.get(i+1)*50); }
         */
    }

    public void serialEvent(Serial serial) {
        SerialConn.updateState(serial.readStringUntil(SerialConn.ENCLOSING_MSG_CHAR));
        this.instrument.update(SerialConn.type);
    }

    public void keyPressed() {
        Keyboard.updateState(this.key, this.keyCode, this.keyPressed);
        this.instrument.update(Keyboard.type);
    }
    
    public void keyReleased() {
        Keyboard.updateState(this.key, this.keyCode, this.keyPressed);
        this.instrument.update(Keyboard.type);
    }
}

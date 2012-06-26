package br.com.borgeslabs.igarassu;

import br.com.borgeslabs.igarassu.hardware.*;
import br.com.borgeslabs.igarassu.instrument.*;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;

public class Igarassu extends PApplet {
    private static final long serialVersionUID = 1L;
    
    private PFont font;
    private Minim minim;
    private Instrument instrument;
    static private Serial serial;

    public static void main(String args[]) {
        PApplet.main(new String[] { /*"--present",*/
                "br.com.borgeslabs.igarassu.Igarassu" });
    }

    public void setup() {
        // Start sound
        this.minim = new Minim(this);
        
        // Window setup
        //this.size(this.screenWidth, this.screenHeight);
        this.size(800,600);
        this.frameRate(60);
        
        // Text setup
        this.font = this.loadFont("Calibri-25.vlw");
        this.textFont(this.font);
        
        // Serial setup
        if (Serial.list().length > 0) {
            serial = new Serial(this, Serial.list()[0], SerialConn.BAUD);
            serial.bufferUntil(SerialConn.ENCLOSING_MSG_CHAR);
        } else {
            println();
            println("=== IGARASSU ===");
            println("=== WARNING! Serial not found.");
            println();
        }
        
        // Placeholder instrument
        this.instrument = Instrument.loadInstrument(minim);
    }

    public void stop() {
        this.minim.stop();
        serial.stop();
        
        this.instrument.close();
    }

    public void draw() {
        background(0);
        
        this.instrument.draw(this);
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
    
    public void mouseClicked() {
        this.instrument.activateArea(this.width, this.height, this.mouseX, this.mouseY);
    }
    
    static public Serial serial() {
        return serial;
    }
}

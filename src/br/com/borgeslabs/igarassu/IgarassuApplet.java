package br.com.borgeslabs.igarassu;

import java.util.ArrayList;
import java.util.List;
import br.com.borgeslabs.igarassu.hardware.*;
import br.com.borgeslabs.igarassu.instrument.*;
import br.com.borgeslabs.igarassu.ui.Colors;
import br.com.borgeslabs.igarassu.ui.Style;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;

public class IgarassuApplet extends PApplet implements Painter {
    private static final long serialVersionUID = 1L;
    
    PFont font;
    Minim minim;
    Serial serial;
    Instrument instrument;
    
    List<Style> styles;

    public static void main(String args[]) {
        PApplet.main(new String[] { /*"--present",*/
                "br.com.borgeslabs.igarassu.IgarassuApplet" });
    }

    public void setup() {
        this.minim = new Minim(this);

        // Ui states setup
        this.styles = new ArrayList<Style>();
        this.pushUiStyle(new Style(Colors.BLACK, Colors.DEFAULT_STROKE_WEIGHT, Colors.WHITE));
        
        // Window setup
        this.size(800, 600);
        this.frameRate(60);
        
        // Text setup
        this.font = this.loadFont("Courier10PitchBT-Roman-18.vlw");
        this.textFont(this.font);
        
        // Serial setup
        if (Serial.list().length > 0) {
            this.serial = new Serial(this, Serial.list()[0], SerialConn.BAUD);
            this.serial.bufferUntil(SerialConn.ENCLOSING_MSG_CHAR);
        } else {
            println("=== IGARASSU ===");
            println("=== WARNING! Serial not found.");
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
    
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }
    
    public void pushUiStyle(Style style) {
        this.styles.add(style);
        this.applyUiStyle(style);
        
        //System.out.println("Números de styles: " + this.states.size());
    }
    
    public void popUiStyle() {
        int size = this.styles.size();
        
        // Can't remove the first state
        if (size > 1) {
            this.styles.remove(--size);
            this.applyUiStyle(this.styles.get(--size));
        }
        
        //System.out.println("Números de styles: " + this.states.size());
    }
    
    private void applyUiStyle(Style style) {
        int stroke = style.stroke;
        int weight = style.weight;
        int fill = style.fill;
        
        if (stroke != Colors.NO_STROKE)
            this.stroke(stroke);
        else
            this.noStroke();
        
        this.strokeWeight(weight);
        
        if (fill != Colors.NO_FILL)
            this.fill(fill);
        else
            this.noFill();
    }
    
    // FIXME ter que criar esse método sem poder usar o nome "map" porque é estático e Interfaces
    // frescam com métodos estáticos matou todo a beleza (já questionável) da solução.
    public float mapToInterval(float value, float istart, float istop, float ostart, float ostop) {
        return PApplet.map(value, istart, istop, ostart, ostop);
    }
}

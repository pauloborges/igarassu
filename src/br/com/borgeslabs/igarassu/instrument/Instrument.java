package br.com.borgeslabs.igarassu.instrument;

import java.util.List;
import java.util.Vector;
import ddf.minim.Minim;
import br.com.borgeslabs.igarassu.Painter;
import br.com.borgeslabs.igarassu.hardware.Hardware;
import br.com.borgeslabs.igarassu.hardware.Keyboard;
import br.com.borgeslabs.igarassu.hardware.SerialConn;
import br.com.borgeslabs.igarassu.ui.Colors;
import br.com.borgeslabs.igarassu.ui.Style;

/**
 * The <code>Instrument</code> class represents a virtual instrument with
 * {@link Pad}s.
 * 
 * @author Paulo Borges
 *
 */
public class Instrument {
    /** Instrument identifier */
    private String name;
    
    private List<Pad> pads;
    private List<Hardware> hardwares;

    /**
     * Creates an empty instrument. 
     * @param name
     *              the instrument's name for labeling purposes.
     */
    public Instrument(String name) {
        this.name = name;
        
        this.pads = new Vector<Pad>();
        this.hardwares = new Vector<Hardware>();
    }
    
    /**
     * Closes the instrument, finishing any resource it uses.
     */
    public void close() {
        for (Pad pad : this.pads) {
            pad.close();
        }
        
        for (Hardware hardware : this.hardwares) {
            hardware.close();
        }
    }
    
    public static Instrument loadInstrument(Minim minim) {
        Instrument instrument = new Instrument("Drum");
        
        instrument.addPad(new Pad("Tom1", new SampleSound(minim, "data/tom.wav")));
        instrument.addPad(new Pad("Caixa", new SampleSound(minim, "data/snare.wav")));
        instrument.addPad(new Pad("Chimbal", new SampleSound(minim, "data/hihat.wav")));
        
        Keyboard keyboard = new Keyboard();
        keyboard.addMapping('a', 0);
        keyboard.addMapping('s', 1);
        keyboard.addMapping('d', 2);
        instrument.addHardware(keyboard);
        
        SerialConn serial = new SerialConn();
        instrument.addHardware(serial);
        
        return instrument;
    }
    
    public String name() {
        return this.name;
    }
    
    public void addPad(Pad pad) {
        if (pad != null && !this.pads.contains(pad))
            this.pads.add(pad);
    }
    
    public void addHardware(Hardware hardware) {
        if (hardware != null && !this.hardwares.contains(hardware)) {
            this.hardwares.add(hardware);
            hardware.setInstrument(this);
        }
    }
    
    public void removeHardware(Hardware hardware) {
        if (this.hardwares.contains(hardware))
            this.hardwares.remove(hardware);
    }
    
    public void update(String hardwareType) {
        for (Hardware hardware : this.hardwares)
            if (hardware.type().equals(hardwareType))
                hardware.update();
    }
    
    public void triggerPad(int idPad) {
        this.pads.get(idPad).trigger();
    }
    
    public void draw(Painter painter) {
        // Desenha o grid onde serão armazenados os Pads.
        // [TODO translate]
        
        int width = painter.width();
        int height = painter.height();
        int numPads = this.pads.size();
        
        int widthDiv;
        if (numPads <= 2)
            widthDiv = 1;
        else if (numPads <= 6)
            widthDiv = 2;
        else
            widthDiv = 3;
        
        int heightDiv;
        if (numPads == 1)
            heightDiv = 1;
        else if (numPads <= 4)
            heightDiv = 2;
        else
            heightDiv = 3;
        
        int padWidth = width / widthDiv;
        int padHeight = height / heightDiv;
        
        // Bordas dos Pad's são grossas e brancas
        painter.pushUiStyle(new Style(Colors.WHITE, Colors.THICKER_STROKE_WEIGHT, Colors.NO_FILL));
        
        for (int x = 0; x < widthDiv; x++) {
            for (int y = 0; y < heightDiv; y++) {
                int ix = x * padWidth;
                int iy = y * padHeight;
                int fx = ix + padWidth;
                int fy = iy + padHeight;
                
                painter.rect(ix, iy, fx, fy);
                
                int index = x + y * widthDiv;
                
                if (index < numPads)
                    this.pads.get(index).draw(painter, ix, iy, fx, fy);
            }
        }
        
        painter.popUiStyle();
    }
}

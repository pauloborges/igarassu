package br.com.borgeslabs.igarassu.instrument;

import java.util.List;
import java.util.Vector;
import ddf.minim.Minim;
import processing.core.PApplet;
import br.com.borgeslabs.igarassu.hardware.Hardware;
import br.com.borgeslabs.igarassu.hardware.Keyboard;
import br.com.borgeslabs.igarassu.hardware.SerialConn;
import br.com.borgeslabs.igarassu.ui.Color;
import br.com.borgeslabs.igarassu.ui.StrokeWeight;

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

    private Pad selectedPad;

    /**
     * Creates an empty instrument.
     * 
     * @param name
     *            the instrument's name for labeling purposes.
     */
    public Instrument(String name) {
        this.name = name;

        this.pads = new Vector<Pad>();
        this.hardwares = new Vector<Hardware>();

        this.selectedPad = null;
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
        int padId = 0;

        Instrument instrument = new Instrument("Bateria");
        Keyboard keyboard = new Keyboard();

        instrument.addPad(new Pad(padId, "Tom", new SampleSound(minim,
                "data/set/tom2.wav"), instrument));
        keyboard.addMapping('l', padId);
        keyboard.addMapping('k', padId++);

        instrument.addPad(new Pad(padId, "Ataque", new SampleSound(minim,
                "data/set/crash1.wav"), instrument));
        keyboard.addMapping('o', padId++);

        instrument.addPad(new Pad(padId, "Chimbal aberto", new SampleSound(
                minim, "data/set/openhihat.wav"), instrument));
        keyboard.addMapping('u', padId++);

        instrument.addPad(new Pad(padId, "Chimbal fechado", new SampleSound(
                minim, "data/set/closedhihat.wav"), instrument));
        keyboard.addMapping('t', padId);
        keyboard.addMapping('y', padId++);

        instrument.addPad(new Pad(padId, "Caixa", new SampleSound(minim,
                "data/set/snare.wav"), instrument));
        keyboard.addMapping('d', padId);
        keyboard.addMapping('f', padId++);

        instrument.addPad(new Pad(padId, "Surdo", new SampleSound(minim,
                "data/set/tom3.wav"), instrument));
        keyboard.addMapping('g', padId);
        keyboard.addMapping('h', padId++);

        instrument.addPad(new Pad(padId, "Condução", new SampleSound(minim,
                "data/set/ridebell.wav"), instrument));
        keyboard.addMapping('p', padId);
        keyboard.addMapping('\'', padId++);

        instrument.addPad(new Pad(padId, "Bumbo", new SampleSound(minim,
                "data/set/kick.wav"), instrument));
        keyboard.addMapping('a', padId);
        keyboard.addMapping('s', padId++);

        keyboard.addControlMapping(Keyboard.LEFT_ARROW, Pad.DECREASE_WINDOW);
        keyboard.addControlMapping(Keyboard.RIGHT_ARROW, Pad.INCREASE_WINDOW);
        keyboard.addControlMapping(Keyboard.DOWN_ARROW, Pad.DECREASE_THRESHOLD);
        keyboard.addControlMapping(Keyboard.UP_ARROW, Pad.INCREASE_THRESHOLD);

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

    public void update(String hardwareType, int timestamp) {
        for (Hardware hardware : this.hardwares)
            if (hardware.type().equals(hardwareType))
                hardware.update(timestamp);
    }

    public void triggerPad(int idPad, int intensity, int timestamp) {
        this.pads.get(idPad).trigger(intensity, timestamp);
    }

    public void digitalTriggerPad(int idPad) {
        this.pads.get(idPad).digitalTrigger();
    }

    public void controlAction(int action) {
        if (this.selectedPad != null)
            this.selectedPad.controlAction(action);

        else
            for (Pad pad : this.pads)
                pad.controlAction(action);
    }

    /**
     * Calculate how the screen will be splitted to arrange the {@link Pad}s.
     * 
     * @return an array with two <code>int</code>s. The first <code>int</code>
     *         contains the number of horizontal divisions; and the second
     *         number contains the number of vertical divisions.
     */
    protected int[] calculateGrid() {
        int[] division = new int[2];
        int numPads = this.pads.size();

        if (numPads <= 2)
            division[0] = 1;
        else if (numPads <= 6)
            division[0] = 2;
        else
            division[0] = 3;

        if (numPads == 1)
            division[1] = 1;
        else if (numPads <= 4)
            division[1] = 2;
        else
            division[1] = 3;

        // FIXME Esse caso deve entrar no caso geral ai em cima nessa
        // configuração
        if (numPads == 8) {
            division[0] = 2;
            division[1] = 4;
        }

        return division;
    }

    public void draw(PApplet painter) {
        int[] div = this.calculateGrid();
        int widthDiv = div[0];
        int heightDiv = div[1];

        int padWidth = painter.width / widthDiv;
        int padHeight = painter.height / heightDiv;

        int numPads = this.pads.size();

        int sx = 0;
        int sy = 0;
        boolean thereIsSelectedPad = false;

        painter.pushStyle();
        painter.strokeWeight(StrokeWeight.THICK);
        painter.noFill();

        for (int x = 0; x < widthDiv; x++) {
            for (int y = 0; y < heightDiv; y++) {
                int ix = x * padWidth;
                int iy = y * padHeight;

                int index = x + y * widthDiv;

                painter.stroke(Color.WHITE);
                if (index < numPads) {
                    Pad pad = this.pads.get(index);
                    pad.draw(painter, ix, iy, padWidth, padHeight);

                    if (pad.isSelected()) {
                        thereIsSelectedPad = true;
                        sx = ix;
                        sy = iy;
                    }
                }

                painter.rect(ix, iy, padWidth, padHeight);
            }
        }

        if (thereIsSelectedPad) {
            painter.stroke(Color.RED);
            painter.rect(sx, sy, padWidth, padHeight);
        }

        painter.popStyle();
    }

    public void activateArea(int width, int height, int mouseX, int mouseY) {
        int[] div = this.calculateGrid();
        int padWidth = width / div[0];
        int padHeight = height / div[1];
        int index = mouseX / padWidth + (mouseY / padHeight) * div[0];

        if (this.pads.size() > index) {
            Pad pad = this.pads.get(index);

            if (this.selectedPad == null) {
                pad.select(true);
                this.selectedPad = pad;
            }

            else if (this.selectedPad == pad) {
                pad.select(false);
                this.selectedPad = null;
            }

            else {
                pad.select(true);
                this.selectedPad.select(false);
                this.selectedPad = pad;
            }
        }
    }
}

package br.com.borgeslabs.igarassu.instrument;

import processing.core.PApplet;
import br.com.borgeslabs.igarassu.ui.Color;
import br.com.borgeslabs.igarassu.ui.StrokeWeight;

public class Pad {
    /** Pad identifier */
    private String name;

    // TODO
    public static final int DEFAULT_INTENSITY_THRESHOLD = 200;
    private int intensityThreshold = DEFAULT_INTENSITY_THRESHOLD;

    // TODO
    public static final int DEFAULT_SILENCE_WINDOW = 5;
    private int silenceWindow;

    // TODO
    private Sound sound;
    
    private int ENERGY_BAR_WIDTH = 30;
    
    private boolean selected;

    public Pad(String name, Sound sound) {
        this.name = name;
        this.sound = sound;
        this.selected = false;
    }

    public void close() {
        this.sound.close();
    }

    public void trigger() {
        this.sound.play();
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void select(boolean state) {
        this.selected = state;
    }
    
    public String name() {
        return this.name;
    }

    /**
     * Draws the <code>Pad</code> in the screen.
     * 
     * @param painter drawing routines
     * @param x position in the screen
     * @param y position in the screen
     * @param width 
     * @param height
     */
    public void draw(PApplet painter, int x, int y, int width, int height) {
        painter.pushStyle();
        painter.stroke(Color.WHITE);
        painter.strokeWeight(StrokeWeight.DEFAULT);
        painter.fill(Color.WHITE);

        // Write the Pad's name in the top left corner
        painter.text(this.name, x + 10, y + 25);

        float[] buffer = this.sound.buffer();
        
        // Energy bar's x position
        float ex = x + (width - this.ENERGY_BAR_WIDTH) / 2;

        for (int i = 0; i < buffer.length - 1; i++) {
            float x1 = PApplet.map(i, 0, buffer.length-1, x, ex);
            float x2 = PApplet.map(i + 1, 0, buffer.length-1, x, ex);
            float y1 = PApplet.map(buffer[i], -1.0f, 1.0f, y, y+height);
            float y2 = PApplet.map(buffer[i + 1], -1.0f, 1.0f, y, y+height);

            painter.line(x1, y1, x2, y2);
        }

        painter.pushStyle();
        painter.stroke(Color.GRAY);
        painter.noFill();
        
        // Empty energy bar
        painter.rect(ex, y, this.ENERGY_BAR_WIDTH, height);

        painter.pushStyle();
        painter.noStroke();
        painter.fill(Color.GRAY);
        
        // Filled energy bar
        float ey = y + height * (1.0f - this.sound.energy());
        painter.rect(ex, ey, this.ENERGY_BAR_WIDTH, height * this.sound.energy());

        painter.popStyle();
        painter.popStyle();
        
        float gx = ex + this.ENERGY_BAR_WIDTH;
        
        // Write the threshold and window length in the second graph
        painter.textSize(15);
        painter.text("Th: " + this.intensityThreshold, gx + 10, y + 20);
        painter.text("Wn: " + this.silenceWindow, gx + 10, y + 35);
        
        painter.popStyle();
    }
}

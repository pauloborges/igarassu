package br.com.borgeslabs.igarassu.instrument;

import br.com.borgeslabs.igarassu.Painter;
import br.com.borgeslabs.igarassu.ui.Colors;
import br.com.borgeslabs.igarassu.ui.Style;

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
	
	public Pad(String name, Sound sound) {
	    this.name = name;
		this.sound = sound;
	}
	
	public void close() {
	    this.sound.close();
	}
	
	public void trigger() {
		this.sound.play();
	}
	
	/**
	 * Draws the <code>Pad</code> in the screen.
	 * @param painter
	 * @param ix
	 * @param iy
	 * @param fx
	 * @param fy
	 */
	public void draw(Painter painter, int ix, int iy, int fx, int fy) {
	    painter.pushUiStyle(new Style(Colors.WHITE, Colors.DEFAULT_STROKE_WEIGHT, Colors.WHITE));
	    
	        // Paint the Pad's name
	        painter.text(this.name, ix + 10, iy + 25);
	    
    	    float[] buffer = this.sound.buffer();
    	    
    	    // Energy bar posY and height
    	    int eh = (fy - iy) / 5;
    	    int ey = fy - eh;
    	    
    	    for (int i = 0; i < buffer.length - 1; i++) {
    	        float x1 = painter.mapToInterval(i, 0, buffer.length, ix, fx);
    	        float x2 = painter.mapToInterval(i+1, 0, buffer.length, ix, fx);
    	        float y1 = painter.mapToInterval(buffer[i], -1.0f, 1.0f, iy, ey);
    	        float y2 = painter.mapToInterval(buffer[i+1], -1.0f, 1.0f, iy, ey);
    	        
    	        painter.line(x1, y1, x2, y2);
    	    }
    	    
    	    painter.pushUiStyle(new Style(Colors.GRAY, Colors.DEFAULT_STROKE_WEIGHT, Colors.NO_FILL));
    	        painter.rect(ix, ey, (fx - ix), eh);
    	    
        	    painter.pushUiStyle(new Style(Colors.NO_STROKE, Colors.DEFAULT_STROKE_WEIGHT, Colors.GRAY));
        	        // Energy bar width
        	        float ew = (fx - ix) * this.sound.energy();
        	        painter.rect(ix, ey, ew, eh);
                painter.popUiStyle();
            painter.popUiStyle();
	    
        painter.popUiStyle();
	}
}

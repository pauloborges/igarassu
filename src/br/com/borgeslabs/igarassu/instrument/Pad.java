package br.com.borgeslabs.igarassu.instrument;

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
		this.sound = sound;
	}
	
	public void close() {
	    // TODO
	}
	
	public void trigger() {
		this.sound.play();
	}
}

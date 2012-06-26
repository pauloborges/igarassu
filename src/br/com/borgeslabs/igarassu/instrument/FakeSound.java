package br.com.borgeslabs.igarassu.instrument;

public class FakeSound implements Sound {

    public static final int BUFFER_SIZE = 2;
    
    private float[] buffer;
    
    public FakeSound() {
        this.buffer = new float[BUFFER_SIZE];
    }
    
    @Override
    public void play() {
        // Empty
    }

    @Override
    public void stop() {
        // Empty

    }

    @Override
    public void close() {
        // Empty

    }

    @Override
    public float[] buffer() {
        return buffer;
    }

    @Override
    public float energy() {
        return 0.0f;
    }

}

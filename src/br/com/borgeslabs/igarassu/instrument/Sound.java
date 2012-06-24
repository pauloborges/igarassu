package br.com.borgeslabs.igarassu.instrument;

public interface Sound {
    // Play the corresponding sound one time and stop.
    public void play();

    // If playing the sound, stop it.
    public void stop();

    // Release any releaseble resources.
    public void close();
}

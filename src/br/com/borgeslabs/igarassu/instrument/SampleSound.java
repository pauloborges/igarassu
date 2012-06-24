package br.com.borgeslabs.igarassu.instrument;

import ddf.minim.AudioSample;
import ddf.minim.Minim;

public class SampleSound implements Sound {
    // The buffer size of the sample.
    // The bigger the buffer, more slow is to start the playback.
    public static final int BUFFER_SIZE = 512;

    // Store the audio sample completely in memory
    AudioSample sample;

    public SampleSound(Minim minim, String fileName) {
        this.sample = minim.loadSample(fileName, BUFFER_SIZE);
    }

    @Override
    public void play() {
        this.sample.trigger();
    }

    @Override
    public void stop() {
        this.sample.stop();
    }

    @Override
    public void close() {
        this.sample.close();
    }

}

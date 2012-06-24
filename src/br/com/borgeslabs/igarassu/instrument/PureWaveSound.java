package br.com.borgeslabs.igarassu.instrument;

import java.util.Timer;
import java.util.TimerTask;

import ddf.minim.Minim;
import ddf.minim.signals.SineWave;
import ddf.minim.AudioOutput;

public class PureWaveSound implements Sound {
    // Default sample rate
    public static final int SAMPLE_RATE = 44100;

    // TODO
    public static final int BUFFER_SIZE = 256;

    // Playback duration in milliseconds
    public static final int PLAYBACK_DURATION = 100;

    // TODO
    SineWave wave;
    AudioOutput output;

    // TODO
    Timer timer;

    private class StopTask extends TimerTask {
        @Override
        public void run() {
            PureWaveSound.this.stop();
        }
    }

    public PureWaveSound(Minim minim, float frequency, float intensity) {
        // Create the sine wave
        this.wave = new SineWave(frequency, intensity, SAMPLE_RATE);

        // Create an output line, add the wave signal and silence it.
        this.output = minim.getLineOut(Minim.MONO, BUFFER_SIZE);
        this.output.addSignal(this.wave);
        // this.output.noSound();
        this.output.mute();
    }

    @Override
    public void play() {
        // this.output.sound();
        this.output.unmute();

        System.out.println("PureWaveSound.play()");

        if (this.timer != null)
            this.timer.cancel();
        this.timer = new Timer();
        this.timer.schedule(new StopTask(), PLAYBACK_DURATION);
    }

    @Override
    public void stop() {
        this.output.mute();

        System.out.println("PureWaveSound.stop()");
    }

    @Override
    public void close() {
        this.output.close();
    }
}

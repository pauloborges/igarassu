package br.com.borgeslabs.igarassu.instrument;

import java.util.Timer;
import java.util.TimerTask;

import ddf.minim.Minim;
import ddf.minim.signals.SineWave;
import ddf.minim.AudioOutput;

public class PureWaveSound implements Sound {
    /** Default sample rate */
    public static final int SAMPLE_RATE = 44100;

    public static final int BUFFER_SIZE = 512;

    /** Playback duration in milliseconds */
    public static final int PLAYBACK_DURATION = 100;

    SineWave wave;
    AudioOutput output;
    Timer timer;

    private class StopTask extends TimerTask {
        @Override
        public void run() {
            PureWaveSound.this.stop();
        }
    }

    public PureWaveSound(Minim minim, float frequency, float intensity) {
        // Creates the sine wave
        this.wave = new SineWave(frequency, intensity, SAMPLE_RATE);

        // Creates an output line, add the wave signal and silence it.
        this.output = minim.getLineOut(Minim.MONO, BUFFER_SIZE);
        this.output.addSignal(this.wave);
        this.output.noSound();
        //this.output.mute();
    }

    @Override
    public void play() {
        this.output.sound();
        //this.output.unmute();

        if (this.timer != null)
            this.timer.cancel();
        this.timer = new Timer();
        this.timer.schedule(new StopTask(), PLAYBACK_DURATION);
    }

    @Override
    public void stop() {
        this.output.noSound();
        //this.output.mute();
    }

    @Override
    public void close() {
        this.output.close();
    }

    @Override
    public float[] buffer() {
        return this.output.mix.toArray();
    }

    @Override
    public float energy() {
        return this.output.mix.level();
    }
}
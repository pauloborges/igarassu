package br.com.borgeslabs.igarassu.hardware;

import br.com.borgeslabs.igarassu.instrument.Instrument;

public abstract class Hardware {
    protected Instrument instrument;
    
    /**
     * Set the hardware's instrument for callback purposes.
     * @param instrument the instrument that possess this hardware.
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    
    public abstract String type();
    
    /**
     * TODO
     */
    public abstract void update();
    
    /**
     * TODO
     */
    public abstract void close();
}

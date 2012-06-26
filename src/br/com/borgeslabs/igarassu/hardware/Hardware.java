package br.com.borgeslabs.igarassu.hardware;

import br.com.borgeslabs.igarassu.instrument.Instrument;

/**
 * The <code>Hardware</code> class abstracts the user input via any
 * hardware, such {@link Keyboard} or {@link Serial}.
 * 
 * @author Paulo Borges
 */
public abstract class Hardware {
    /** 
     * The <code>Instrument</code> that possess this <code>Hardware</code>, 
     * made protected to provide access to subclasses.
     */
    protected Instrument instrument;
    
    /**
     * Set the hardware's instrument for callback purposes.
     * 
     * @param instrument
     *          the instrument that possess this hardware.
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    
    /**
     * A Getter for the <code>Hardware</code>'s type.
     * 
     * @return a unique <code>String</code> containing the type of
     * this <code>Hardware</code>.
     */
    public abstract String type();
    
    /**
     * Updates the <code>Hardware</code>, calling {@link #instrument}'s 
     * callbacks if necessary.
     */
    public abstract void update(int timestamp);
    
    /**
     * Closes the <code>Hardware</code>, releasing any releasable resources.
     */
    public abstract void close();
}

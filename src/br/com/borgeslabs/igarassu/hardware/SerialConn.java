package br.com.borgeslabs.igarassu.hardware;

import br.com.borgeslabs.igarassu.Igarassu;
import processing.serial.Serial;

public class SerialConn extends Hardware {
    public static final String type = "Serial";
    public static final int ENCLOSING_MSG_CHAR = '\n';
    public static final int BAUD = 57600;
    
    private static String _msg;
    private Serial serial;
    
    public SerialConn() {
        this.serial = Igarassu.serial();
    }
    
    @Override
    public String type() {
        return type;
    }
    
    @Override
    public void close() {
        // Empty
    }
    
    @Override
    public void update() {
        // FIXME
        int idPad = Integer.parseInt(_msg);
        this.instrument.triggerPad(idPad);
    }
    
    public static void updateState(String msg) {
        _msg = msg;
    }

}

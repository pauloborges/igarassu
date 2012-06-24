package br.com.borgeslabs.igarassu.hardware;

public class SerialConn extends Hardware {
    public static final String type = "Serial";
    public static final int ENCLOSING_MSG_CHAR = '\n';
    public static final int BAUD = 57600;
    
    private static String _msg;
    
    public SerialConn() {
        // Empty
    }
    
    @Override
    public String type() {
        return "Serial";
    }
    
    @Override
    public void close() {
        // Empty
    }
    
    @Override
    public void update() {
        // FIXME
        int idPad = Integer.parseInt(_msg);
        this.instrument.routeTrigger(idPad);
    }
    
    public static void updateState(String msg) {
        _msg = msg;
    }

}

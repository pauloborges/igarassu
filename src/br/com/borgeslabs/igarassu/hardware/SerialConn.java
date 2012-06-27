package br.com.borgeslabs.igarassu.hardware;

import processing.serial.Serial;

public class SerialConn extends Hardware {
    public static final String type = "Serial";
    public static final int ENCLOSING_MSG_CHAR = '\n';
    public static final int BAUD = 38400;

    private static String _msg;
    private static Serial _serial;

    public static void initSerial(Serial serial) {
        _serial = serial;
    }

    public SerialConn() {
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
    public void update(int timestamp) {
        // Parse the msg. The igarassu messaging protocol:
        // <PAD_NUM>,<INTENSITY>\n

        int comma = _msg.indexOf(',');
        int newline = _msg.indexOf(ENCLOSING_MSG_CHAR);

        if (comma == -1 || newline == -1)
            return;

        try {
            int idPad = Integer.parseInt(_msg.substring(0, comma));
            int intensity = Integer.parseInt(_msg.substring(comma + 1,
                    newline - 1));

            this.instrument.triggerPad(idPad, intensity, timestamp);
        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }
    }

    public static void updateState(String msg) {
        _msg = msg;
    }

}

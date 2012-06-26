package br.com.borgeslabs.igarassu.hardware;

import java.util.HashMap;
import java.util.Map;

public class Keyboard extends Hardware {
    public static final String type = "Keyboard";

    // Static state
    private static int _key;
    private static int _keyCode;
    private static boolean _keyPressed;

    private Map<Integer, Integer> mapping;
    private Map<Integer, Integer> controlMapping;

    public static final int LEFT_ARROW = 37;
    public static final int UP_ARROW = 38;
    public static final int RIGHT_ARROW = 39;
    public static final int DOWN_ARROW = 40;

    public Keyboard() {
        this.mapping = new HashMap<Integer, Integer>();
        this.controlMapping = new HashMap<Integer, Integer>();
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public void close() {
        // Empty
    }

    public void addMapping(int key, int idPad) {
        this.mapping.put(key, idPad);
    }

    public void addControlMapping(int key, int idPad) {
        this.controlMapping.put(key, idPad);
    }

    @Override
    public void update(int timestamp) {
        if (_keyPressed && this.mapping.containsKey(_key)) {
            int idPad = this.mapping.get(_key);
            this.instrument.digitalTriggerPad(idPad);
        }

        else if (_keyPressed && (this.controlMapping.containsKey(_keyCode))
                || this.controlMapping.containsKey(_key)) {
            int action = this.controlMapping.containsKey(_key) ? this.controlMapping
                    .get(_key) : this.controlMapping.get(_keyCode);

            this.instrument.controlAction(action);
        }
    }

    public static void updateState(int key, int keyCode, boolean keyPressed) {
        _key = key;
        _keyCode = keyCode;
        _keyPressed = keyPressed;
    }
}

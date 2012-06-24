package br.com.borgeslabs.igarassu.hardware;

import java.util.HashMap;
import java.util.Map;

import br.com.borgeslabs.igarassu.instrument.Instrument;

public class Keyboard extends Hardware {
    public static final String type = "Keyboard";
    
    // Static state
    private static int _key;
    private static int _keyCode;
    private static boolean _keyPressed;
    
    private Map<Integer, Integer> mapping;

    public Keyboard() {
        this.mapping = new HashMap<Integer, Integer>();
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
    
    @Override
    public void update() {
        if (_keyPressed && this.mapping.containsKey(_key)) {
            int idPad = this.mapping.get(_key);
            this.instrument.routeTrigger(idPad);
        }
    }
    
    public static void updateState(int key, int keyCode, boolean keyPressed) {
        _key = key;
        _keyCode = keyCode;
        _keyPressed = keyPressed;
    }
}

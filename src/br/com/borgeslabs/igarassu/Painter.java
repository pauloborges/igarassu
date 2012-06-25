package br.com.borgeslabs.igarassu;

import br.com.borgeslabs.igarassu.ui.Style;

public interface Painter {
    public int width();
    public int height();
    
    public void pushUiStyle(Style style);
    public void popUiStyle();
    
    public void stroke(int rgb);
    public void noStroke();
    public void fill(int rgb);
    public void noFill();
    
    public void text(String str, float x, float y);
    
    public void line(float x1, float y1, float x2, float y2);
    public void rect(float a, float b, float c, float d);
    
    public float mapToInterval(float value, float istart, float istop, float ostart, float ostop);
}

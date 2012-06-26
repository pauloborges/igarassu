package br.com.borgeslabs.igarassu.ui;

import processing.core.PApplet;

public class Graph {
    public static final int BUFFER_SIZE = 512; 
    
    private int[] buffer;
    private int windex;
    private int rindex;
    
    public static final int YSPACING = 5;
    
    public Graph() {
        this.buffer = new int[BUFFER_SIZE];
        this.windex = 0;
        this.rindex = 0;
    }
    
    public void addValue(int value) {
        this.buffer[this.windex++] = value;
        
        if (this.windex == this.buffer.length)
            this.windex = 0;
    }
    
    public void draw(PApplet painter, float x, float y, float width, float height, int threshold) {
        if (this.windex == this.rindex)
            return;
        
        painter.pushStyle();
        painter.stroke(Color.RED);
        
        float thresy = PApplet.map(threshold, 0, 1023, y+height-YSPACING, y+YSPACING);
        painter.line(x, thresy, x+width, thresy);
        
        painter.stroke(Color.YELLOW);
        
        //painter.rect(x, y, width, height);
        int fi = mod(this.windex - 2, this.buffer.length);
        for (int i = this.rindex; i != fi; i = (i + 1) % this.buffer.length) {
            float tmpx = (i >= rindex) ? (i - rindex) : (i + this.buffer.length - rindex);
            
            float x1 = PApplet.map(tmpx, 0, this.buffer.length-1, x, x+width);
            float y1 = PApplet.map(this.buffer[i], 0, 1023, y+height-YSPACING, y+YSPACING);
            float x2 = PApplet.map(tmpx+1, 0, this.buffer.length-1, x, x+width);
            float y2 = PApplet.map(this.buffer[i+1], 0, 1023, y+height-YSPACING, y+YSPACING);
            
            painter.line(x1, y1, x2, y2);
            
        }
        
        painter.popStyle();
    }
    
    static private int mod(int a, int b) {
        if (a >= 0)
            return a % b;
        else
            return (a + b) % b;
    }
}

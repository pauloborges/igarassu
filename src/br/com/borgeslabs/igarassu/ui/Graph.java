package br.com.borgeslabs.igarassu.ui;

import processing.core.PApplet;

public class Graph {
    public static final int BUFFER_SIZE = 512; // samples

    private int[] buffer;
    private int windex;
    private int rindex;

    public static final int YSPACING = 10; // pixels

    public Graph() {
        this.buffer = new int[BUFFER_SIZE];
        this.windex = 0;
        this.rindex = 0;
    }

    public void addValue(int value) {
        this.buffer[this.windex++] = value;

        if (this.windex == this.buffer.length)
            this.windex = 0;

        if ((this.windex + 1) % this.buffer.length == this.rindex)
            this.rindex = (this.rindex + 1) % this.buffer.length;
        // if (this.windex % this.buffer.length == this.rindex)
        // this.rindex = (this.rindex + 1) % this.buffer.length;
    }

    public void draw(PApplet painter, float x, float y, float width,
            float height, int threshold) {
        // if (this.windex == this.rindex)
        // return;

        painter.pushStyle();
        painter.stroke(Color.RED);

        // Do not paint above the energy bar vertical line
        x++;

        float thresY = PApplet.map(threshold, 0, 1023, y + height - YSPACING, y
                + YSPACING);
        painter.line(x, thresY, x + width, thresY);

        painter.stroke(Color.YELLOW);

        int fi = mod(this.windex - 2, this.buffer.length);
        for (int i = this.rindex; i != fi; i = (i + 1) % this.buffer.length) {
            int i2 = (i + 1) % this.buffer.length;
            float tmpX = (i >= rindex) ? (i - rindex)
                    : (i + this.buffer.length - rindex);

            float x1 = PApplet.map(tmpX, 0, this.buffer.length - 1, x - 1, x
                    + width);
            float y1 = PApplet.map(this.buffer[i], 0, 1023, y + height
                    - YSPACING, y + YSPACING);
            float x2 = PApplet.map(tmpX + 1, 0, this.buffer.length - 1, x - 1,
                    x + width);
            float y2 = PApplet.map(this.buffer[i2], 0, 1023, y + height
                    - YSPACING, y + YSPACING);

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

package me.logicologist.adventofcode2020.day17;

public class Tesseract {

    private final int w;
    private final int x;
    private final int y;
    private final int z;
    private boolean active;

    public Tesseract(int x, int y, int z, int w, boolean active) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public int w() {
        return w;
    }

    public Tesseract clone() {
        return new Tesseract(x, y, z, w, active);
    }
}

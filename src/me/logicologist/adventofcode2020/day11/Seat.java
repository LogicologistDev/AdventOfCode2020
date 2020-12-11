package me.logicologist.adventofcode2020.day11;

public class Seat {

    private final int x;
    private final int y;
    private String state;

    Seat(int x, int y, String state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public String getLocation() {
        return this.x + "," + this.y;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Seat clone() {
        return new Seat(this.x, this.y, this.state);
    }
}

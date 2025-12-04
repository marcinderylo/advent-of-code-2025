package com.github.marcinderylo.day1.secretentrance;

public class Dial {
    private int currentPosition;

    public Dial(int initialValue) {
        this.currentPosition = initialValue;
    }

    public int position() {
        return currentPosition;
    }

    public void rotateRight(int ticks) {
        rotate(ticks);
    }

    public void rotateLeft(int ticks) {
        rotate(-ticks);
    }

    private void rotate(int rotation) {
        int newPosition = currentPosition + rotation % 100;
        if (newPosition < 0) {
            newPosition += 100;
        }
        if (newPosition > 99) {
            newPosition -= 100;
        }
        currentPosition = newPosition;
    }
}

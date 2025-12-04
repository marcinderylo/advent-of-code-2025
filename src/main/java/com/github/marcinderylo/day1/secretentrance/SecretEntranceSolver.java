package com.github.marcinderylo.day1.secretentrance;

import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * You arrive at the secret entrance to the North Pole base ready to start decorating. Unfortunately, the password seems
 * to have been changed, so you can't get in. A document taped to the wall helpfully explains:
 * "Due to new security protocols, the password is locked in the safe below. Please see the attached document for the
 * new combination."
 * The safe has a dial with only an arrow on it; around the dial are the numbers 0 through 99 in order. As you turn the
 * dial, it makes a small click noise as it reaches each number. The attached document (your puzzle input) contains a
 * sequence of rotations, one per line, which tell you how to open the safe. A rotation starts with an L or R which
 * indicates whether the rotation should be to the left (toward lower numbers) or to the right (toward higher numbers).
 * Then, the rotation has a distance value which indicates how many clicks the dial should be rotated in that direction.
 * <p>
 * The dial starts by pointing at 50.
 * You could follow the instructions, but your recent required official North Pole secret entrance security training seminar taught you that the safe is actually a decoy. The actual password is the number of times the dial is left pointing at 0 after any rotation in the sequence.
 */
public class SecretEntranceSolver {

    public long solve(Stream<String> instructions) {
        Dial dial = new Dial(50);
        return instructions
                .map(this::stringToRotation)
                .mapToInt(rotation -> {
                    rotation.accept(dial);
                    return dial.position();
                })
                .filter(i -> i == 0)
                .count();
    }

    public long solveUsing0x434C49434B(Stream<String> instructions) {
        Dial dial = new Dial(50);
        return instructions
                .flatMap(this::tickByTickRotations)
                .mapToInt(rotation -> {
                    rotation.accept(dial);
                    return dial.position();
                })
                .filter(i -> i == 0)
                .count();
    }

    private Stream<Consumer<Dial>> tickByTickRotations(String instructionString) {
        int ticks = Integer.parseInt(instructionString.substring(1));
        Consumer<Dial> tickLeft = dial -> dial.rotateLeft(1);
        Consumer<Dial> tickRight = dial -> dial.rotateRight(1);
        return IntStream.range(0, ticks)
                .mapToObj(i -> instructionString.startsWith("L") ? tickLeft : tickRight);
    }

    private Consumer<Dial> stringToRotation(String instructionString) {
        if (instructionString.startsWith("L")) {
            var ticks = Integer.valueOf(instructionString.substring(1));
            return dial -> dial.rotateLeft(ticks);
        } else if (instructionString.startsWith("R")) {
            var ticks = Integer.valueOf(instructionString.substring(1));
            return dial -> dial.rotateRight(ticks);
        }
        throw new IllegalArgumentException("Invalid instruction: " + instructionString);
    }
}

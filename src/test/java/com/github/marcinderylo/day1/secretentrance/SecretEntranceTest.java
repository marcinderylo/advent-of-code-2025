package com.github.marcinderylo.day1.secretentrance;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretEntranceTest {
    // So, if the dial were pointing at 11, a rotation of R8 would cause the dial to point at 19.

    @Test
    void start_at_11_rotate_R8() {
        Dial dial = new Dial(11);
        dial.rotateRight(8);
        assertEquals(19, dial.position());
    }

    // After that, a rotation of L19 would cause it to point at 0.
    @Test
    void start_at_10_rotate_L19() {
        Dial dial = new Dial(19);
        dial.rotateLeft(19);
        assertEquals(0, dial.position());
    }

    // Because the dial is a circle, turning the dial left from 0 one click makes it point at 99.

    @Test
    void dial_is_a_circle_start_at_0_rotate_L1() {
        Dial dial = new Dial(0);
        dial.rotateLeft(1);
        assertEquals(99, dial.position());
    }

    // Similarly, turning the dial right from 99 one click makes it point at 0.

    @Test
    void dial_is_a_cirlcle_start_at_99_rotate_R1() {
        Dial dial = new Dial(99);
        dial.rotateRight(1);
        assertEquals(0, dial.position());
    }

    // So, if the dial were pointing at 5, a rotation of L10 would cause it to point at 95.

    @Test
    void dial_is_a_circle_start_at_5_rotate_L10() {
        Dial dial = new Dial(5);
        dial.rotateLeft(10);
        assertEquals(95, dial.position());
    }

    @Test
    void very_long_rotations_start_at_50_rotate_L200() {
        Dial dial = new Dial(42);
        dial.rotateLeft(200);
        assertEquals(42, dial.position());
    }

    @Test
    void very_long_rotations_start_at_42_rotate_R1000() {
        Dial dial = new Dial(42);
        dial.rotateRight(1000);
        assertEquals(42, dial.position());
    }

    /**
     * For example, suppose the attached document contained the following rotations:
     * The dial starts by pointing at 50.
     * The dial is rotated L68 to point at 82.
     * The dial is rotated L30 to point at 52.
     * The dial is rotated R48 to point at 0.
     * The dial is rotated L5 to point at 95.
     * The dial is rotated R60 to point at 55.
     * The dial is rotated L55 to point at 0.
     * The dial is rotated L1 to point at 99.
     * The dial is rotated L99 to point at 0.
     * The dial is rotated R14 to point at 14.
     * The dial is rotated L82 to point at 32.
     * Because the dial points at 0 a total of three times during this process, the password in this example is 3.
     */
    @Test
    void sample_from_description() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";
        var secretEntranceSolver = new SecretEntranceSolver();
        var solution = secretEntranceSolver.solve(Stream.of(input.split("\n")));
        assertEquals(3, solution);
    }

    /**
     * Following the same rotations as in the above example, the dial points at zero a few extra times during its rotations:
     * The dial starts by pointing at 50.
     * The dial is rotated L68 to point at 82; during this rotation, it points at 0 once.
     * The dial is rotated L30 to point at 52.
     * The dial is rotated R48 to point at 0.
     * The dial is rotated L5 to point at 95.
     * The dial is rotated R60 to point at 55; during this rotation, it points at 0 once.
     * The dial is rotated L55 to point at 0.
     * The dial is rotated L1 to point at 99.
     * The dial is rotated L99 to point at 0.
     * The dial is rotated R14 to point at 14.
     * The dial is rotated L82 to point at 32; during this rotation, it points at 0 once.
     */
    @Test
    void sample_from_description_using_0x434C49434B() {
        var input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";
        var secretEntranceSolver = new SecretEntranceSolver();
        var solution = secretEntranceSolver.solveUsing0x434C49434B(Stream.of(input.split("\n")));
        assertEquals(6, solution);
    }

    public static void main(String[] args) throws IOException {
        var solver = new SecretEntranceSolver();
        System.out.println("Solution for the part 1 of Secret Entrance task is: " + solver.solve(instructions()));
        System.out.println("Solution for the part 2 of Secret Entrance task is: " + solver.solveUsing0x434C49434B(instructions()));
    }

    private static Stream<String> instructions() throws IOException {
        return Files.lines(Path.of("src/test/resources/day1/input.txt"), StandardCharsets.UTF_8);
    }
}
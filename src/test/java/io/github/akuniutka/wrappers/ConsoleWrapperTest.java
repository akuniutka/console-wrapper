package io.github.akuniutka.wrappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Console Wrapper")
class ConsoleWrapperTest {
    @Test
    @DisplayName("Fails to feed null data")
    void testFeedWhenInputDataIsNull() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        Exception e = assertThrows(IllegalArgumentException.class, () -> wrapper.feed(null, () -> {}));
        assertEquals("Data for input stream cannot be null.", e.getMessage());
    }

    @Test
    @DisplayName("Restores default streams on exception")
    void testFeedWhenExceptionIsThrown() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        InputStream in = System.in;
        PrintStream out = System.out;
        PrintStream err = System.err;
        Exception e = assertThrows(RuntimeException.class, () -> wrapper.feed(inputData, () -> {
            throw new RuntimeException(inputData);
        }));
        assertEquals(inputData, e.getMessage());
        assertSame(in, System.in);
        assertSame(out, System.out);
        assertSame(err, System.err);
    }

    @Test
    @DisplayName("Feeds non-null data")
    void testFeedWhenInputDataIsNotNull() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        assertSame(wrapper, wrapper.feed(inputData, () -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(inputData, scanner.nextLine());
            assertFalse(scanner.hasNext());
        }));
    }

    @Test
    @DisplayName("Finds that actual output data differs from expected")
    void testOutputEqualsWhenOutputDiffers() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        String expected = "Bye!";
        String actual = "Hello!";
        try {
            wrapper.feed(inputData, () -> System.out.print(actual)).outputEquals(expected);
        } catch (AssertionError e) {
            assertEquals("expected: " + expected + " But was: " + actual, e.getMessage());
        }
    }

    @Test
    @DisplayName("Finds that actual output data equals to expected")
    void testOutputEqualsWhenOutputEquals() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        String expected = "Hello!";
        String actual = "Hello!";
        assertSame(wrapper, wrapper.feed(inputData, () -> System.out.print(actual)).outputEquals(expected));
    }

    @Test
    @DisplayName("Finds that actual error data differs from expected")
    void testErrorEqualsWhenOutputDiffers() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        String expected = "Bye!";
        String actual = "Hello!";
        try {
            wrapper.feed(inputData, () -> System.err.print(actual)).errorEquals(expected);
        } catch (AssertionError e) {
            assertEquals("expected: " + expected + " But was: " + actual, e.getMessage());
        }
    }

    @Test
    @DisplayName("Finds that actual error data equals to expected")
    void testErrorEqualsWhenOutputEquals() {
        ConsoleWrapper wrapper = new ConsoleWrapper();
        String inputData = "Hello, World!";
        String expected = "Hello!";
        String actual = "Hello!";
        assertSame(wrapper, wrapper.feed(inputData, () -> System.err.print(actual)).errorEquals(expected));
    }
}
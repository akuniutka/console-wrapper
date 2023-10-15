package dev.akuniutka.wrappers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleWrapper {
    private ByteArrayOutputStream wrapperOutputStream;
    private ByteArrayOutputStream wrapperErrorStream;

    public ConsoleWrapper feed(String inputData, Runnable runnable) {
        if (inputData == null) {
            throw new IllegalArgumentException("Data for input stream cannot be null.");
        }
        ByteArrayInputStream wrapperInputStream = new ByteArrayInputStream(inputData.getBytes());
        wrapperOutputStream = new ByteArrayOutputStream();
        wrapperErrorStream = new ByteArrayOutputStream();
        InputStream defaultInputStream = System.in;
        PrintStream defaultOutputStream = System.out;
        PrintStream defaultErrorStream = System.err;
        System.setIn(wrapperInputStream);
        System.setOut(new PrintStream(wrapperOutputStream));
        System.setErr(new PrintStream(wrapperErrorStream));
        runnable.run();
        System.setIn(defaultInputStream);
        System.setOut(defaultOutputStream);
        System.setErr(defaultErrorStream);
        return this;
    }

    public ConsoleWrapper outputEquals(String expected) throws AssertionError {
        String actual = wrapperOutputStream == null ? null : wrapperOutputStream.toString();
        if (actual == null || !actual.equals(expected)) {
            throw new AssertionError("expected: " + expected + " But was: " + actual);
        }
        return this;
    }

    public ConsoleWrapper errorEquals(String expected) {
        String actual = wrapperErrorStream == null ? null : wrapperErrorStream.toString();
        if (actual == null || !actual.equals(expected)) {
            throw new AssertionError("expected: " + expected + " But was: " + actual);
        }
        return this;
    }
}

package dev.akuniutka.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleOutputWrapper {
    private ByteArrayOutputStream wrapperOutputStream;
    private PrintStream systemOutputStream;

    public void start() {
        systemOutputStream = System.out;
        wrapperOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(wrapperOutputStream));
    }

    public String getData() {
        return wrapperOutputStream.toString();
    }

    public void stop() {
        System.setOut(systemOutputStream);
    }
}

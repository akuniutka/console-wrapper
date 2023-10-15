package dev.akuniutka.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleErrorWrapper {
    private ByteArrayOutputStream wrapperStream;
    private PrintStream systemErrorStream;

    public void start() {
        systemErrorStream = System.err;
        wrapperStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(wrapperStream));
    }

    public String getData() {
        return wrapperStream.toString();
    }

    public void stop() {
        System.setErr(systemErrorStream);
    }
}

package dev.akuniutka.wrappers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleInputWrapper {
    private ByteArrayInputStream wrapperStream;
    private InputStream systemInputStream;

    public ConsoleInputWrapper() {
    }

    public ConsoleInputWrapper(String data) {
        wrapperStream = new ByteArrayInputStream(data.getBytes());
    }

    public ConsoleInputWrapper(List<String> data) {
        wrapperStream = new ByteArrayInputStream(data.stream()
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes()
        );
    }

    public void setData(String data) {
        wrapperStream = new ByteArrayInputStream(data.getBytes());
    }

    public void setData(List<String> data) {
        wrapperStream = new ByteArrayInputStream(data.stream()
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes()
        );
    }

    public void start() {
        if (wrapperStream == null) {
            throw new RuntimeException("Data for input stream is not set.");
        }
        systemInputStream = System.in;
        System.setIn(wrapperStream);
    }

    public void stop() {
        System.setIn(systemInputStream);
        wrapperStream = null;
    }
}

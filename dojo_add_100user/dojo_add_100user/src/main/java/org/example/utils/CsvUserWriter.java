package org.example.utils;

import org.example.model.User;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes registered users to a CSV file, one line at a time,
 * flushing after every write so progress is never lost even if
 * the run is interrupted.
 */
public class CsvUserWriter implements Closeable {

    private final PrintWriter writer;

    public CsvUserWriter(String filePath) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filePath));
        writer.println("name,email,password");
        writer.flush();
    }

    public void write(User user) {
        writer.println(user.toString());
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}

package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
class MainTest {

    @Test
    void main() {
    }

    @Test
    void generatePassword() {
        Main main = new Main();
        PasswordProperties properties = new PasswordProperties();
        File file = new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);
        Path path = Paths.get(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);

        if (Files.exists(path)) {
            deleteFile(path);
        }
        properties.defineNew(file);
        properties.load(file);

        String password = main.generatePassword();

        assert(!password.isEmpty());
    }

    void deleteFile (Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("could not delete file, " + path, e);
        }
    }
}
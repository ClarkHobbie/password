package com.ltsllc.password;

import com.ltsllc.commons.util.ImprovedPaths;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class MainTest {

    @Test
    void main() {
        Main main = new Main();
        String[] args = new String[0];
        Main.main(args);

        assert (Main.password != null);
        assert (Character.isUpperCase(Main.password.password.charAt(0)));
    }

    @Test
    void generatePassword() {
        Main main = new Main();
        PasswordProperties properties = new PasswordProperties();
        File file = PasswordProperties.propertiesFile;
        Path path = ImprovedPaths.toPath(file);

        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("error deleting file, " + path, e);
            }
        }
        PasswordProperties.define(file);
        PasswordProperties.load();

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
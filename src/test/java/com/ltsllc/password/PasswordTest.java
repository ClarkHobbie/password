package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.io.File;

class PasswordTest {

    @Test
    void generate() {
        Password password = new Password();

        File file = PasswordProperties.propertiesFile;
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("could not delete file, " + file);
            }
        }

        PasswordProperties properties = new PasswordProperties();
        PasswordProperties.define(file);

        properties.setCandidatesString(NumberCharacter.NAME);
        properties.store(file);

        assert (properties  != null);

        Password.properties = properties;
        PasswordProperties.properties = properties;

        String passwordString = password.generate();

        NumberCharacter numberCharacter = new NumberCharacter();

        assert(numberCharacter.in(passwordString));
    }

    @Test
    void generatePassword() {
        File file = new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);
        if (file.exists())
            file.delete();

        PasswordProperties properties = new PasswordProperties();
        properties.define(file);

        properties.setCandidatesString(NumberCharacter.NAME);
        PasswordProperties.overWrite();

        Password password = new Password();
        String passwordString = password.generate();

        StringBuilder builder = new StringBuilder();

        NumberCharacter numberCharacter = new NumberCharacter();

        assert(numberCharacter.in(passwordString));
    }
}
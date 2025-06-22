package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.io.File;

class PasswordTest {

    @Test
    void generate() {
        File file = new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);
        if (file.exists())
            file.delete();

        PasswordProperties properties = new PasswordProperties();
        properties.defineNew(file);

        properties.setCandidatesString(NumberCharacter.NAME);
        properties.store(file);

        Password password = new Password();
        String passwordString = password.generate();

        StringBuilder builder = new StringBuilder();

        NumberCharacter numberCharacter = new NumberCharacter();

        assert(numberCharacter.contains(passwordString));
    }

    @Test
    void generatePassword() {
    }
}
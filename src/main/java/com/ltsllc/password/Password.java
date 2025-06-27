package com.ltsllc.password;

import java.io.File;

public class Password {
    public static PasswordProperties properties = new PasswordProperties();
    public static final String DEFAULT_PROPERTIES_FILE = "password.properties";

    public String generate () {
        File file = new File(properties.DEFAULT_PROPERTIES_FILE_NAME);
        if (!file.exists())
            properties.defineNew(file);

        properties.load(file);
        return generatePassword();
    }

    public String generatePassword() {
        Candidate[] candidates = Candidate.parse(properties.getCandidatesString());
        StringBuilder password = new StringBuilder();
        Bag<Candidate> bag = new Bag<Candidate>();

        for (Candidate candidate : candidates) {
            bag.add(candidate);
        }

        for (int i = 0; i < properties.getLength() - candidates.length; i++) {
            bag.add(new AnyLowerCaseCharacter());
        }

        while (bag.notEmpty()) {
            Candidate candidate = bag.next();
            password.append(candidate.generate());
        }

        return password.toString();
    }
}

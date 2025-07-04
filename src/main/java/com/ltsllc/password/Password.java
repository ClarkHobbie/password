package com.ltsllc.password;

import java.io.File;

/******************************************************************************
 * A class that generates random passwords, subject to constraints.
 */
public class Password {
    public static PasswordProperties properties = new PasswordProperties();
    public static final String DEFAULT_PROPERTIES_FILE = "password.properties";


    /**************************************************************************
     * Generate a new password.
     *
     * The method uses the properties from
     * {@link PasswordProperties#DEFAULT_PROPERTIES_FILE_NAME}, or it creates a
     * new file by that name and gives it the properties defined by
     * {@link PasswordProperties#setDefaultProperties()}.
     *
     * @see {@link PasswordProperties#setDefaultProperties()}
     * @return The generated password.
     */
    public String generate () {
        File file = PasswordProperties.propertiesFile;
        if (!file.exists()) {
            PasswordProperties.define(file);
        }

        properties.load();
        return generatePassword();
    }

    /**************************************************************************
     * Generate a new password.
     *
     * The method parses in the list of requirements given by
     * {@link PasswordProperties#getCandidatesString()} and adds
     * {@link AnyLowerCaseCharacter} subject to the
     * length given by {@link PasswordProperties#getLength()} and generates a
     * new, random password.
     *
     * @return The new password.
     */
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

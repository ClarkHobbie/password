package com.ltsllc.password;

import com.google.gson.Gson;

import java.io.*;

public class Main {
    public static void main() {
        //
        // load properties, if properties don't exist, create them
        //
        File file = new File("password.properties");
        StringBuilder stringBuilder = new StringBuilder();

        if (!file.exists()) {
            defineNewProperties(file);
        }
        loadProperties(file);

        Password password = new Password();

        //
         // Add a prefix of 1 uppercase character
        //
        UpperCaseCharacter character = new UpperCaseCharacter();

        stringBuilder.append(character.generate());

        //
         // add the rest of the password
        //
        stringBuilder.append(password.generate());

        System.out.println(stringBuilder.toString());
    }

    public String generatePassword() {
        String password = "";
        Password passwordGenerator = new Password();

        if (Password.properties == null) {
            Password.properties = new PasswordProperties();
            Password.properties.load(new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME));
        }

        return passwordGenerator.generate();
    }

    public static void defineNewProperties(File file) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open properties file for writing, " + file.getAbsolutePath());
        }
        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setLength(9);
        passwordProperties.setCandidatesString(Candidate.UPPERCASE_NAME + "," + Candidate.LOWERCASE_NAME + "," + Candidate.NUMBER_NAME + "," + Candidate.SYMBOL_NAME);
        Gson gson = new Gson();
        String text = gson.toJson(passwordProperties);

        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException("error writing file, " + file.getAbsolutePath());
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("could not close FilWriter for file, " + file, e);
        }
    }

    public static void loadProperties(File file) {
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("the properties file, " + file + ", was not found", e);
        }
        Gson gson = new Gson();
        PasswordProperties properties = gson.fromJson(fileReader, PasswordProperties.class);
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException("could not close FileReader for file, " +  file, e);
        }
        Password.properties = properties;
    }
}

package com.ltsllc.password;

import com.google.gson.Gson;

import java.io.*;

/**
 * A class that holds the properties that constrain how a password is
 * generated.
 *
 * The properties are
 * <table border="1" >
 *     <caption>Properties</caption>
 *     <tr>
 *         <th>name</th>
 *         <th>default value</th>
 *         <th>meaning</th>
 *     </tr>
 *     <tr>
 *         <td>length</td>
 *         <td>8</td>
 *         <td>The length of the password, in characters</td>
 *     </tr>
 *     <tr>
 *         <td>candidatesString</td>
 *         <td>LowerCase,Number,Symbol</td>
 *         <td>The components that must occur in the password.</td>
 *     </tr>
 * </table>
 */
public class PasswordProperties {
    public static final String DEFAULT_PROPERTIES_FILE_NAME = "password.properties";
    public static File propertiesFile = new File(DEFAULT_PROPERTIES_FILE_NAME);
    public static final String DEFAULT_CANDIDATE_STRING = LowerCaseCharacter.NAME
            + Candidate.NAME_SEPARATOR + NumberCharacter.NAME
            + Candidate.NAME_SEPARATOR + SymbolCharacter.NAME;
    public static PasswordProperties properties;

    int length;
    String CandidatesString;

    public static File getFile() {
        return propertiesFile;
    }

    public static void setFile(File file) {
        PasswordProperties.propertiesFile = file;
    }

    public String getCandidatesString() {
        return CandidatesString;
    }

    public void setCandidatesString(String candidatesString) {
        CandidatesString = candidatesString;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Store the properties in a file.
     *
     * This method will delete the file, if it exists, and then call
     * {@link #define()}.
     *
     * <p>
     * The properties are stored as a JSON object using Gson to create the
     * JSON.
     *
     * @param file The file to store the properties
     */
    public static void store (File file){
        Gson gson = new Gson();
        String text = gson.toJson(PasswordProperties.properties);
        FileWriter fileWriter;

        //
         // check if the caller wants to overwrite the file
        //
        if (file.exists())
        {
            file.delete();
            PasswordProperties.define();
        }

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open file, " + file + ", for writing",e);
        }

        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException("could not write properties to file, " + file, e);
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("could not close file. " + file,e);
        }
    }


    /**
     * Define a properties.
     * <p>
     * This method will create a new properties .  This method assumes that
     * the  does not exist and will behave unpredictably if it exists.  The
     * created will have the default properties assigned to it.
     *
     * @see PasswordProperties for the default poperties.
     * @param file The file to create.
     * @throws RuntimeException if there are errors opening the file, writing
     * the file, or closing the file.
     */
    public static void define(File file) {
        if (file.exists()) {
            throw new RuntimeException("the file, " + file + ", already exists");
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open properties  for writing, " + file);
        }

        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setDefaultProperties();
        properties = passwordProperties;
        Gson gson = new Gson();
        String text = gson.toJson(passwordProperties);

        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException("error writing , " + file);
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("could not close FilWriter for , " + file, e);
        }
    }

    /**
     * Define a new properties file given by the file
     * <p>
     * This is a convenience method equivalent to calling define(PasswordProperties.file)
     */
    public static void define () {
        define(propertiesFile);
    }

    /**
     * Load the properties from the file designated by PasswordProperties.file
     * <p>
     * Load the properties from the designated file.  The method assumes that
     * the designated file exists and will behave unpredictably,
     */
    public static void load(File file) {
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

    /**************************************************************************
     * Load the properties from the file defined by PasswordProperties.file
     *
     * A convenience method equivalent to load(PasswordProperties.file)
     */
    public static void load() {
        load(propertiesFile);
    }

    public static void store() {
        store(propertiesFile);
    }

    public static void overWrite() {
        overWrite(propertiesFile);
    }

    private static void overWrite(File file) {
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("could not delete file, " + file);
            }
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open properties  for writing, " + file);
        }

        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setDefaultProperties();
        properties = passwordProperties;
        Gson gson = new Gson();
        String text = gson.toJson(passwordProperties);

        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException("error writing , " + file);
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("could not close FilWriter for , " + file, e);
        }
    }


    public void setDefaultProperties() {
        setLength(8);
        setCandidatesString(LowerCaseCharacter.NAME + Candidate.NAME_SEPARATOR +
                NumberCharacter.NAME + Candidate.NAME_SEPARATOR +
                SymbolCharacter.NAME);
    }
}

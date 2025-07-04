package com.ltsllc.password;

import com.google.gson.Gson;

import java.io.*;

/******************************************************************************
 * The class that contains the main method.
 *
 * This is the main class for the password program.  To use it the user
 * calls {@link Main#main} and the class prints the password, constrained by
 * the values in password.properties  By default, the password will be
 * <ul>
 *     <li>9 characters long.</li>
 *     <li>Will contain an upper case character as its first character.</li>
 *     <li>Will contain a lower case character.</li>
 *     <li>Will contain a digit.</li>
 *     <li>The rest of the characters are taken from the set of all digits,
 *     symbols ([@#$%^&amp;*()]), and lower case letters.</li>
 * </ul>
 */
public class Main {
    /****************************************************************
     * prioritize a file
     *
     * This method looks in password.properties for configuration
     * data.  The configuration data takes the form of a JASON file
     * that controls the length and content of the password.
     *
     * It then generates a password based on those properties.
     *
     * If a properties file does not exist, this method will create one with
     * the properties defined as described in the
     * {@link PasswordProperties#setDefaultProperties()} method.
     *
     * <p>
     * Briefly the default properties are:
     * <UL>
     *     <LI>Always include a lower case character.</LI>
     *     <LI>Always include a digit.</LI>
     *     <LI>Always include a symbol taken from the set [!@#$%^&amp;*()]</LI>
     *     <LI>All other characters are draw from the sets of all digits,
     *     the set of all symbols (as defined above) and the set of all lower
     *     case characters.</LI>
     *     <LI>The length of the password is 8.</LI>
     *     <LI>The resulting password has an upper case character prepended to
     *     it making total length 9 characters.</LI>
     * </UL>
     */
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

    /**************************************************************************
     * Generate the password
     *
     * This method will also load password.properties.  The method then
     * generates the password.
     *
     * @return The generated password.
     */
    public String generatePassword() {
        String password = "";
        Password passwordGenerator = new Password();

        if (Password.properties == null) {
            Password.properties = new PasswordProperties();
            PasswordProperties.load();
        }

        return passwordGenerator.generate();
    }

    /**************************************************************************
     * Define a new properties file.
     *
     * This method will create a new properties file.  This method assumes that
     * the file does not exist and will behave unpredictably if it exists.  The
     * file created will have the default properties assigned to it.
     *
     * @see PasswordProperties for the default poperties.
     * @param file The file to create.
     * @throws RuntimeException if there are opening the file, writing the
     * file, or closing the file.
     */
    public static void defineNewProperties(File file) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open properties file for writing, " + file.getAbsolutePath());
        }
        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setDefaultProperties();
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

    /**************************************************************************
     * Load the properties file
     *
     * Load the properties from the designated file.  The method assumes that
     * the designated file exists and will behave unpredictably,
     *
     * @param file
     */
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

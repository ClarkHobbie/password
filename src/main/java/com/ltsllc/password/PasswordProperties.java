package com.ltsllc.password;

import com.google.gson.Gson;

import java.io.*;

public class PasswordProperties {
    public static final String DEFAULT_PROPERTIES_FILE_NAME = "password.properties";

    int length;
    String CandidatesString;


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

    public void load (File file) {
       if (!file.exists()) {
           defineNew(file);
       }

       loadProperties(file);
    }

    public void store (File file){
        Gson gson = new Gson();
        String text = gson.toJson(this);
        FileWriter fileWriter;

        //
         // check if the caller wants to overwrite the file
        //
        if (file.exists())
        {
            file.delete();
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

    public static void defineNew(File file) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("could not open properties file for writing, " + file.getAbsolutePath());
        }
        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setLength(8);
        passwordProperties.setCandidatesString(Candidate.LOWERCASE_NAME + "," + Candidate.NUMBER_NAME + "," + Candidate.SYMBOL_NAME);
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
            throw new RuntimeException("could not close FileWriter for properties file, " + file, e);

        }
    }



    public static void loadProperties(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            Gson gson = new Gson();
            PasswordProperties properties = gson.fromJson(fileReader, PasswordProperties.class);
            Password.properties = properties;
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException("could not close FileReader, " + file, e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("could not read properties file, " + file.toString());
        }
    }



}

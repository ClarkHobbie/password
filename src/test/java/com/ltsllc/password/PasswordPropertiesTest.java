package com.ltsllc.password;



import com.google.gson.Gson;
import com.ltsllc.commons.io.TextFile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class PasswordPropertiesTest {

//    @Test
//    void getCandidatesString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        PasswordProperties passwordProperties = new PasswordProperties();
//
//        stringBuilder.append(UpperCaseCharacter.NAME);
//        stringBuilder.append(Candidate.NAME_SEPARATOR);
//        stringBuilder.append(LowerCaseCharacter.NAME);
//        stringBuilder.append(Candidate.NAME_SEPARATOR);
//        stringBuilder.append(NumberCharacter.NAME);
//        stringBuilder.append(Candidate.NAME_SEPARATOR);
//        stringBuilder.append(SymbolCharacter.NAME);
//
//        String s1 = stringBuilder.toString();
//
//          passwordProperties.setCandidatesString(s1);

//        assert(s1.equalsIgnoreCase(passwordProperties.getCandidatesString()));
//    }

    @Test
    void load() {
        File file = PasswordProperties.propertiesFile;

        if (file.exists()) {
            file.delete();
        }
        PasswordProperties.define();
        PasswordProperties.load();
        PasswordProperties.store();

        assert (file.exists());
    }

    @Test
    void loadProperties() {
        prepare();
        File file = new File(getPropertiesFileName());
        String propertiesFileName = getPropertiesFileName();
        StringBuilder sb = new StringBuilder();
        PasswordProperties p = new PasswordProperties();

        try {
            sb.append(UpperCaseCharacter.NAME);
            p.setCandidatesString(sb.toString());
            try {
                p.store(file);
            } catch (RuntimeException e) {
                throw new RuntimeException("Could not store to file, " + propertiesFileName, e);
            }
            p.load(new File(propertiesFileName));

            assert (file.exists());
            assert (p.getCandidatesString().equalsIgnoreCase(UpperCaseCharacter.NAME));
        } finally {
            cleanup();
        }
    }

    String getPropertiesFileName() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("temp/");
        stringBuilder.append(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);

        return stringBuilder.toString();
    }

    void prepare () {
        Path path = Paths.get("temp");
        if (Files.exists(path)) {
            deletePath(path);
        }

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("could not create path, " + path, e);
        }
    }

    void cleanup () {
        Path path = Paths.get("temp");
        deletePath(path);
    }

    void deleteDirectory(Path path) {
        if (Files.isDirectory(path)) {
            try {
                Files.setAttribute(path, "dos:readonly", false);
            } catch (IOException e) {
                throw new RuntimeException("error making directory, " + path + "writable", e);
            }

            ArrayList<Path> entries = getPathList(path);

            for (Path entry : entries) {
                deletePath(entry);
            }



            boolean readonly = false;
            try {
                readonly = (boolean) Files.getAttribute(path, "dos:readonly");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (readonly)
                try {
                    Files.setAttribute(path, "dos:readonly", false);
                } catch (IOException e) {
                    throw new RuntimeException("error trying to make the directory, " + path + ", readonly", e);
                }

            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("could not delete directory, " + path, e);
            }
        }
    }

    ArrayList<Path> getPathList(Path path) {
        DirectoryStream<Path> directoryStream = null;
        ArrayList<Path> paths = new ArrayList<Path>();

        try {
            directoryStream = Files.newDirectoryStream(path);
        } catch (IOException e) {
            throw new RuntimeException("error opening DirectoryStream for directory " + path, e);
        }

        for (Path entry : directoryStream) {
            paths.add(entry);
        }

        try {
            directoryStream.close();
        } catch (IOException e) {
            throw new RuntimeException("error closing DirectorySteam for directory, " + path, e);
        }
        return paths;
    }

    void deleFile (Path path) {
        try {
            Files.setAttribute(path, "dos:readonly", false);
        } catch (IOException e) {
            throw new RuntimeException("error making file writeable for file, " + path, e);
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("could not delete file, " + path, e);
        }
    }


    void deletePath (Path path) {
        if (Files.isRegularFile(path)) {
            deleFile(path);
        } else if (Files.isDirectory(path)) {
            makeWriteable(path);
            deleteDirectory(path);
        } else {
            throw new RuntimeException("unknown file type: " + path);
        }

    }

    void makeWriteable(Path path) {
        if (Files.isRegularFile(path))
            makeFileWriteable(path);
        else if (Files.isDirectory(path))
            makeDirectoryWriteable(path);
    }

    void makeDirectoryWriteable(Path path) {
        ArrayList<Path> entries = getPathList(path);
        for (Path entry : entries) {
            makeWriteable(entry);
        }
    }

    void makeFileWriteable(Path path) {
        try {
            Files.setAttribute(path, "dos:readonly", false);
        } catch (IOException e) {
            throw new RuntimeException("could not make file writeable, " + path, e);
        }
    }


    @Test
    void store() {
        //
        // make sure store works
        //
        PasswordProperties passwordProperties = new PasswordProperties();
        passwordProperties.setDefaultProperties();
        PasswordProperties.store();

        File file = PasswordProperties.propertiesFile;

        assert (file.exists());
        TextFile textFile = new TextFile(PasswordProperties.propertiesFile);
        textFile.load();
        Gson gson = new Gson();
        passwordProperties = gson.fromJson(new InputStreamReader(textFile.getInputStream()), PasswordProperties.class);

        assert (passwordProperties.getCandidatesString().equalsIgnoreCase(PasswordProperties.DEFAULT_CANDIDATE_STRING));

    }



    @BeforeAll
    static void setUp () {
        File file = new File("test/test.txt");
        PasswordProperties.propertiesFile = file;

        Path path = ImprovedPaths.toPath("test");

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("could not create directory, " + file, e);
        }

        if (!PasswordProperties.propertiesFile.exists()) {
            PasswordProperties.define();
        }
    }

    @AfterAll
    static void tearDown () {
        File file = new File("test");

        if (file.isDirectory()) {
            deleteDirectory(file);
        }
    }

    static void deleteDirectory(File parent) {
        String[] strings = parent.list();
        for (String string : strings) {
            delete(new File(parent + File.pathSeparator + string));
        }
   }

   static void delete (File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else if (file.isFile()) {
                if (!file.delete()) {
                    throw new RuntimeException("could not delete file, " + file);
                }
            } else {
                throw new RuntimeException("impossible case");
            }
        }
   }
}
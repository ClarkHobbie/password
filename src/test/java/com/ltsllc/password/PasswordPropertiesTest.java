package com.ltsllc.password;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
        File file = new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);
        PasswordProperties passwordProperties = new PasswordProperties();

        file.delete();
        passwordProperties.load(file);
        assert (file.exists());
    }

    @Test
    void defineNew() {
        prepare();
        try {
            File file = new File(PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);
            PasswordProperties passwordProperties = new PasswordProperties();

            boolean result = file.delete();
            assert (!file.exists());
            passwordProperties.defineNew(file);
            assert(file.exists());
        } finally {
            cleanup();
        }
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
            p.loadProperties(new File(propertiesFileName));

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

    void createTempFile (File file) {
        file.mkdir();;
    }

    void makeDirectoryReadonly(File dir, Path path) {
        String[] strings = dir.list();

        for (int i = 0; i < strings.length; i++) {
            File file = new File(dir, strings[i]);
            file.setWritable(false);
        }

        try {
            Files.setAttribute(path, "dos:readonly", true);
        } catch (IOException e) {
            throw new RuntimeException("could not make directory, " + dir + ", readonly", e);
        }

        try {
            Object result = Files.getAttribute(path, "dos:readonly");
            result = result;
        } catch (IOException e) {
            throw new RuntimeException("error reading readonly state of file, " + path, e);
        }
    }

    @Test
    void store() {
        prepare();
        try {

            PasswordProperties passwordProperties = new PasswordProperties();
            passwordProperties.setCandidatesString(LowerCaseCharacter.NAME);

            //
            // test with a file that cannot be created
            //
            File dir = new File("temp");
            try {
                makeDirectoryReadonly(dir, Paths.get("temp"));
            } catch (RuntimeException e) {
                throw new RuntimeException("could not make directory, " + dir + ", readonly", e);
            }
            File file = null;
            // file = new File(dir, PasswordProperties.DEFAULT_PROPERTIES_FILE_NAME);


            String string = dir.getAbsolutePath();
            Path path = Paths.get("temp");

            file = new File("temp");
            boolean result = file.setReadOnly();


            try {
                passwordProperties.store(file);
            } catch (RuntimeException e) {
                throw new RuntimeException("could not store properties file, " + file, e);
            }

        } finally {
            cleanup();
        }
    }
}
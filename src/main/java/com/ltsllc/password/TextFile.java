package com.ltsllc.password;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextFile {
    protected Path path;
    protected List<String> text;

    public TextFile(String string) {
        File file = new File(string);
        setPath(file);
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setPath (File file) {
        Path path = ImprovedPaths.toPath(file);
        setPath(path);
    }

    public TextFile(File file) {
        path = ImprovedPaths.toPath(file);
    }

    public void setText (String[] newText) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (int index = 0; index < newText.length; index++) {
            arrayList.add(newText[index]);
        }

        setText(arrayList);
    }

    public void write() {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        File file = path.toFile();

        if (file.exists()) {
            file.delete();
        }

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("cannot open file for writing, file " + file, e);
        }

        bufferedWriter = new BufferedWriter(fileWriter);
        Iterator<String> iterator = text.iterator();;
        while (iterator.hasNext())
        {
            String line = iterator.next();
            try {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException("error writing file, " + file, e);
            }
        }

        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("error closing text file, " + file, e);
        }
    }


    public void delete() {
        try {
            if (Files.exists(path))
                Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("error deleting text file, " + path, e);
        }
    }

    public void load() {
        try {
            text = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("error reading file, " + path.toFile(),e);
        }
    }

    public InputStream getInputStream () {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (String string : text) {
            try {
                baos.write(string.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("error writing to ByteArrayOutputStream", e);
            }
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

    public Reader getReader() {
        return new InputStreamReader(getInputStream());
    }
}

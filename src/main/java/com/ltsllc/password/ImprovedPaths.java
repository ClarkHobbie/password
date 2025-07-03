package com.ltsllc.password;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/******************************************************************************
 * Provide some convenience methods for working with {@link java.nio.file.Path}s
 */
public class ImprovedPaths {
    /**************************************************************************
     * Create a {@link java.nio.file.Path} from a file name.
     *
     * @param fileName The file name.
     * @return The corresponding {@link java.nio.file.Path}
     */
    public static Path toPath(String fileName) {
        File file = new File(fileName);
        return toPath(file);
    }

    /**************************************************************************
     * Create a {@link java.nio.file.Path} from a {@link java.io.File}.
     *
     * @param file The {@link java.io.File}.
     * @return The corresponding {@link java.nio.file.Path}.
     */
    public static Path toPath(File file) {
        String pathString = file.getAbsolutePath();
        Path path = Paths.get(pathString);
        return path;
    }

    /**************************************************************************
     * Return the new {@link java.nio.file.Path} created from appending a
     *
     * Return a {@link java.nio.file.Path} resulting from appending a string to
     * a {@link java.nio.file.Path}.
     *
     * @param path The {@link java.nio.file.Path} to append to.
     * @param suffix The string to append.
     * @return The resulting {@link java.nio.file.Path}.
     */
    public static Path appendToPath(Path path, String suffix) {
        String string = path + suffix;
        Path tempPath = Path.of (string);
        return tempPath;
    }
}

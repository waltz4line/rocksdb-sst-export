package org.waltz4line.rocksdb.util;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getSstFiles(String directoryPath) throws Exception {
        return getSearchFile(directoryPath, "*.sst");
    }

    public static List<String> getSearchFile(String directoryPath, String extensionRegex) throws Exception {
        List<String> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), extensionRegex)) {
            for (Path entry : stream) {
                files.add(entry.toString());
            }
        }
        return files;
    }

}

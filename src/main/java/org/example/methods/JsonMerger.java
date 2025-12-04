package org.example.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JsonMerger {

    public static Map<String, Object> mergeJsonFilesToMap(String folder) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> finalMap = new HashMap<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            var dirURL = classLoader.getResource(folder);

            if (dirURL == null) {
                throw new IOException("Папка не найдена: " + folder);
            }

            Path dirPath = Paths.get(dirURL.toURI());

            try (Stream<Path> paths = Files.walk(dirPath)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json"))
                        .forEach(path -> {
                            try {
                                Map<String, Object> currentMap = mapper.readValue(
                                        path.toFile(),
                                        new TypeReference<Map<String, Object>>() {}
                                );
                                finalMap.putAll(currentMap);

                            } catch (IOException e) {
                                System.err.println("Ошибка при чтении файла " + path.getFileName());
                            }
                        });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalMap;
    }
}

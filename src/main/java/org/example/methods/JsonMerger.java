package org.example.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonMerger {

    public static Map<String, List<String>> mergeJsonFilesToMap(String folder) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> graph = new HashMap<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            var dirURL = classLoader.getResource(folder);

            if (dirURL == null) {
                throw new IOException("Folder not found: " + folder);
            }

            Path dirPath = Paths.get(dirURL.toURI());

            try (Stream<Path> paths = Files.walk(dirPath)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json"))
                        .forEach(path -> {
                            try {

                                // исходный вид JSON
                                Map<String, Map<String, Map<String, String>>> fileData =
                                        mapper.readValue(path.toFile(),
                                                new TypeReference<>() {});

                                // раскладываем в граф
                                for (var block : fileData.values()) {
                                    for (var node : block.entrySet()) {
                                        String from = node.getKey();

                                        // соседи – ключи вложенной карты
                                        List<String> neighbors =
                                                new ArrayList<>(node.getValue().keySet());

                                        graph.merge(from, neighbors, (oldV, newV) -> {
                                            oldV.addAll(newV);
                                            return oldV;
                                        });
                                    }
                                }

                            } catch (Exception e) {
                                System.err.println("Error reading " + path.getFileName());
                                e.printStackTrace();
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }
}

package org.example.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.FloorData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class JsonMerger {

    public static Map<String, FloorData> mergeJsonFilesByFloor(String folder) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, FloorData> floorsMap = new HashMap<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            var dirURL = classLoader.getResource(folder);

            if (dirURL == null) {
                throw new IOException("Folder not found: " + folder);
            }

            Path dirPath = Paths.get(dirURL.toURI());

            try (Stream<Path> paths = Files.walk(dirPath)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json") && p.toString().contains("floor"))
                        .forEach(path -> {
                            try {
                                Map<String, Object> fileData = mapper.readValue(path.toFile(),
                                        new TypeReference<>() {});

                                for (var floorEntry : fileData.entrySet()) {
                                    String floor = floorEntry.getKey();
                                    Object floorObj = floorEntry.getValue();
                                    if (!(floorObj instanceof Map<?, ?> floorMap)) {
                                        continue;
                                    }

                                    // Получаем или создаем FloorData для этажа
                                    FloorData floorData = floorsMap.getOrDefault(floor, new FloorData());
                                    if (floorData.nodes == null) {
                                        floorData.nodes = new ArrayList<>();
                                    }
                                    if (floorData.edges == null) {
                                        floorData.edges = new ArrayList<>();
                                    }

                                    // Попробуем считать width и height, если они есть
                                    Object widthObj = floorMap.get("width");
                                    if (widthObj instanceof Number w) {
                                        floorData.width = w.intValue();
                                    }

                                    Object heightObj = floorMap.get("height");
                                    if (heightObj instanceof Number h) {
                                        floorData.height = h.intValue();
                                    }

                                    // Соседи: ищем ключ "neighbors"
                                    Object nodesObj = floorMap.get("nodes");
                                    if (nodesObj instanceof List<?> nodesList) {
                                        for (var entry : nodesList) {
                                            if (entry instanceof Map<?, ?> nodeMap) {
                                                Map<String, Object> map = new HashMap<>();
                                                nodeMap.forEach((k,v) -> map.put(k.toString(), v));
                                                floorData.nodes.add(map);
                                            }
                                        }
                                    }

                                    Object edgesObj = floorMap.get("edges");
                                    if (edgesObj instanceof List<?> edgesList) {
                                        for (var entry : edgesList) {
                                            if (entry instanceof Map<?, ?> edgeMap) {
                                                Map<String, Object> map = new HashMap<>();
                                                edgeMap.forEach((k,v) -> map.put(k.toString(), v));
                                                floorData.edges.add(map);
                                            }
                                        }
                                    }

                                    floorsMap.put(floor, floorData);
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

        return floorsMap;
    }
}

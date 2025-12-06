package org.example.methods;

import java.util.*;

public class BreadthFirstSearch {

    private Map<String, List<String>> graph;
    private Map<String, Boolean> used = new HashMap<>();
    private Map<String, Integer> distance = new HashMap<>();
    private Map<String, String> parent = new HashMap<>();

    public BreadthFirstSearch() {
//        this.graph = JsonMerger.mergeJsonFilesByFloor("jsons");
    }

    public List<String> bfs(String start, String end) {
        Queue<String> queue = new ArrayDeque<>();

        queue.add(start);
        used.put(start, true);
        distance.put(start, 0);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            String v = queue.poll();

            if (v.equals(end)) break;

            for (String to : graph.getOrDefault(v, List.of())) {
                if (!used.getOrDefault(to, false)) {

                    used.put(to, true);
                    distance.put(to, distance.get(v) + 1);
                    parent.put(to, v);

                    queue.add(to);
                }
            }
        }

        return restorePath(start, end);
    }

    private List<String> restorePath(String start, String end) {
        List<String> path = new ArrayList<>();

        if (!parent.containsKey(end)) {
            System.out.println("Пути нет!");
            return path;
        }

        String cur = end;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }

        Collections.reverse(path);
        return path;
    }

}

package org.example.map;

import org.example.methods.BreadthFirstSearch;
import org.example.methods.JsonMerger;

import java.util.List;
import java.util.Map;

public class MappingFaculty {
    public List<String> searchPathBetweenPoints(String from, String to) {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        return bfs.bfs(from, to);
    }

    public List<String> outputFloorAuditorium(int floor) {
        Map<String, List<String>> cabinets = JsonMerger.mergeJsonFilesToMap("jsons");

        char floorChar = Character.forDigit(floor, 10);

        return cabinets.entrySet().stream()
                .filter(entry -> entry.getKey().length() > 1)
                .filter(entry -> entry.getKey().charAt(1) == floorChar)
                .map(Map.Entry::getKey)
                .toList();
    }
}

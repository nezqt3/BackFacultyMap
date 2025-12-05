package org.example.map;

import org.example.methods.BreadthFirstSearch;
import java.util.List;

public class MappingFaculty {
    public List<String> searchPathBetweenPoints(String from, String to) {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        return bfs.bfs(from, to);
    }
}

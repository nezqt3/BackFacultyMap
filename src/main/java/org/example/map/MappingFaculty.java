package org.example.map;

import org.example.data.FloorData;
import org.example.methods.BreadthFirstSearch;
import org.example.methods.JsonMerger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingFaculty {

    private Map<String, List<String>> cabinets = new HashMap<>();

    public List<String> searchPathBetweenPoints(String from, String to) {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        return bfs.bfs(from, to);
    }

    public void fillCabinetsList(Map<String, FloorData> cabinets) {
        for (var floor : cabinets.entrySet()) {
            List<String> cabs = new ArrayList<>();

            for (var edge : floor.getValue().edges) {

                String from = edge.get("from").toString();

                if (!cabs.contains(from)) cabs.add(from);
            }
            this.cabinets.put(floor.getKey().toString(), cabs);
        }
    }

    public List<Map<String, Object>> outputFloorAuditorium(int floor) {
        Map<String, FloorData> cabinets = JsonMerger.mergeJsonFilesByFloor("json_full_data");
        fillCabinetsList(cabinets);

        char floorChar = Character.forDigit(floor, 10);

        List<Map<String, Object>> result = new ArrayList<>();

        for (var floorEntry : cabinets.entrySet()) {
            FloorData floorData = floorEntry.getValue();

            for (Map<String, Object> node : floorData.nodes) {
                Object idObj = node.get("id");
                if (idObj == null) continue;

                String id = idObj.toString();

                if (id.length() > 1 && id.charAt(1) == floorChar) {
                    result.add(node);
                }
            }
        }

        return result;
    }

    public List<Map<String, Object>> outputFrameAuditorium(int frame) {
        Map<String, FloorData> cabinets = JsonMerger.mergeJsonFilesByFloor("json_full_data");
        fillCabinetsList(cabinets);

        char floorChar = Character.forDigit(frame, 10);

        List<Map<String, Object>> result = new ArrayList<>();

        for (var floorEntry : cabinets.entrySet()) {
            FloorData floorData = floorEntry.getValue();

            for (Map<String, Object> node : floorData.nodes) {
                Object idObj = node.get("id");
                if (idObj == null) continue;

                String id = idObj.toString();

                if (id.length() > 1 && id.charAt(0) == floorChar) {
                    result.add(node);
                }
            }
        }

        return result;
    }

    public List<Map<String, Object>> outputAllData() {
        Map<String, FloorData> cabinets = JsonMerger.mergeJsonFilesByFloor("json_full_data");
        List<Map<String, Object>> result = new ArrayList<>();

        for (var floorEntry : cabinets.entrySet()) {
            FloorData floorData = floorEntry.getValue();

            if (floorData.nodes != null) {
                result.addAll(floorData.nodes);
            }
        }

        return result;
    }
}

package org.example.map;

import org.example.methods.JsonMerger;

import java.util.HashMap;
import java.util.Map;

public class MappingFaculty {

    private Map<String, Object> mapData = new HashMap<>();

    public void searchPathBetweenPoints() {
        return;
    }

    public Map<String, Object> getMapData() {
        return this.mapData;
    }

    public void setMapData() {
        String directory = "jsons";
        JsonMerger jsonMerger = new JsonMerger();
        this.mapData = jsonMerger.mergeJsonFilesToMap(directory);
    }

    public static void main(String[] args) {
        MappingFaculty mappingFaculty = new MappingFaculty();
        mappingFaculty.setMapData();

        System.out.println(mappingFaculty.getMapData());

    }
}

package org.example.services.api;

import org.example.map.MappingFaculty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MappingFacultyPath {

    public List<Map<String, Map<String, Object>>> getPath(String from, String to) {
        MappingFaculty mappingFaculty = new MappingFaculty();
        return mappingFaculty.searchPathBetweenPoints(from, to);
    }

    public List<Map<String, Object>> getFloorAuditoriums(int floor) {
        MappingFaculty mappingFaculty = new MappingFaculty();
        return mappingFaculty.outputFloorAuditorium(floor);
    }

    public List<Map<String, Object>> getFrameAuditoriums(int frame) {
        MappingFaculty mappingFaculty = new MappingFaculty();
        return mappingFaculty.outputFrameAuditorium(frame);
    }

    public List<Map<String, Object>> getAllData() {
        MappingFaculty mappingFaculty = new MappingFaculty();
        return mappingFaculty.outputAllData();
    }

}
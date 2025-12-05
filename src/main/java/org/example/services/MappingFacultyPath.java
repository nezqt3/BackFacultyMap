package org.example.services;

import org.example.map.MappingFaculty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingFacultyPath {

    public List<String> getPath(String from, String to) {
        MappingFaculty mappingFaculty = new MappingFaculty();
        return mappingFaculty.searchPathBetweenPoints(from, to);
    }

}
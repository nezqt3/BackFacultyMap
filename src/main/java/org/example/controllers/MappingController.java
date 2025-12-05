package org.example.controllers;

import org.example.middleware.exceptions.NotEnoughArguments;
import org.example.services.MappingFacultyPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MappingController {

    private MappingFacultyPath mappingFacultyPath;

    public MappingController(MappingFacultyPath mappingFacultyPath) {
        this.mappingFacultyPath = mappingFacultyPath;
    }

    @GetMapping("/api/route")
    public Map<String, Object> getRoute(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name ="study_place", required = false) String studyPlace) {

        if (from == null || to == null || studyPlace == null) {
            throw new NotEnoughArguments(from, to, studyPlace);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);
        result.put("study_place", studyPlace);

        result.put("path", this.mappingFacultyPath.getPath(from, to));
        return result;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> data = new HashMap<>();
        data.put("response", "base response REST API");
        return data;
    }
}

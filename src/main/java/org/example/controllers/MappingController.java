package org.example.controllers;

import org.example.middleware.exceptions.NotEnoughArguments;
import org.example.services.api.MappingFacultyPath;
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
            @RequestParam(name ="study_place", required = false) String studyPlace
    ) {

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

    @GetMapping("/api/get_auditoriums/floors")
    public Map<String, Object> getFloorAuditoriums(
            @RequestParam(name = "floor", required = false) int floor,
            @RequestParam(name ="study_place", required = false) String studyPlace
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("floor", floor);
        result.put("study_place", studyPlace);

        result.put("auditoriums", this.mappingFacultyPath.getFloorAuditoriums(floor));
        return result;
    }

    @GetMapping("/api/get_auditoriums/frames")
    public Map<String, Object> getFrameAuditoriums(
            @RequestParam(name = "frame", required = false) int frame,
            @RequestParam(name ="study_place", required = false) String studyPlace
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("frame", frame);
        result.put("study_place", studyPlace);

        result.put("auditoriums", this.mappingFacultyPath.getFrameAuditoriums(frame));
        return result;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> data = new HashMap<>();
        data.put("response", "base response REST API");
        return data;
    }
}

package org.example.controllers;

import org.example.services.MappingFacultyPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MappingController {

    private MappingFacultyPath mappingFacultyPath;

    public MappingController(MappingFacultyPath mappingFacultyPath) {
        this.mappingFacultyPath = mappingFacultyPath;
    }

    @GetMapping("/route")
    public List<String> getRoute(
            @RequestParam(name = "from") String from,
            @RequestParam(name = "to") String to,
            @RequestParam(name ="study_place") String studyPlace) {
        return this.mappingFacultyPath.getPath(from, to);
    }

    @GetMapping("/")
    public String home() {
        return "Hello, Spring Boot server is running!";
    }
}

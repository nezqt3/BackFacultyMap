package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MappingController {

    @GetMapping("/route/{from}/{to}")
    public ArrayList<String> getRoute(@PathVariable String from, @PathVariable String to) {
        return new ArrayList<String>();
    }
}

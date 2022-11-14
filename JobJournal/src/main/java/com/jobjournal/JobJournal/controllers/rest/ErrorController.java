package com.jobjournal.JobJournal.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/error")
public class ErrorController {
    @GetMapping
    private String standardResponse() {
        return "Error.";
    }
}

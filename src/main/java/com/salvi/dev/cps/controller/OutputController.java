package com.salvi.dev.cps.controller;

import com.salvi.dev.cps.service.Output;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OutputController {

    @GetMapping("/output")
    public Output getOutput() {
        return new Output();
    }

    // New code block
    private static class Channel {
        // Channel implementation
    }

    private static class Metric {
        // Metric implementation
    }
}
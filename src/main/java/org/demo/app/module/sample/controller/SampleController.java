package org.demo.app.module.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/app")
    ResponseEntity<?> sample() {
        return ResponseEntity.ok("Hello Jenkins");
    }
}
